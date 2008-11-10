/**
 * 
 */
package org.slf4j.instrumentation;

import static org.slf4j.helpers.MessageFormatter.format;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

import org.slf4j.helpers.MessageFormatter;

/**
 * <p>
 * LogTransformer does the work of analyzing each class, and if appropriate add
 * log statements to each method to allow logging entry/exit.
 * </p>
 * <p>
 * This class is based on the article <a href="http://today.java.net/pub/a/today/2008/04/24/add-logging-at-class-load-time-with-instrumentation.html"
 * >Add Logging at Class Load Time with Java Instrumentation</a>.
 * </p>
 */
public class LogTransformer implements ClassFileTransformer {

  /**
   * Builder provides a flexible way of configuring some of many options on the
   * parent class instead of providing many constructors.
   * 
   * {@link http://rwhansen.blogspot.com/2007/07/theres-builder-pattern-that-joshua.html}
   * 
   */
  public static class Builder {

    /**
     * Build and return the LogTransformer corresponding to the options set in
     * this Builder.
     * 
     * @return
     */
    public LogTransformer build() {
      if (verbose) {
        System.err.println("Creating LogTransformer");
      }
      return new LogTransformer(this);
    }

    boolean addEntryExit;

    /**
     * Should each method log entry (with parameters) and exit (with parameters
     * and returnvalue)?
     * 
     * @param b
     *          value of flag
     * @return
     */
    public Builder addEntryExit(boolean b) {
      addEntryExit = b;
      return this;
    }

    boolean addVariableAssignment;

//    private Builder addVariableAssignment(boolean b) {
//      System.err.println("cannot currently log variable assignments.");
//      addVariableAssignment = b;
//      return this;
//    }

    boolean verbose;

    /**
     * Should LogTransformer be verbose in what it does? This currently list the
     * names of the classes being processed.
     * 
     * @param b
     * @return
     */
    public Builder verbose(boolean b) {
      verbose = b;
      return this;
    }

    String[] ignore = { "sun/", "java/", "javax/", "org/slf4j/",
        "ch/qos/logback/", "org/apache/log4j/", "apple/", "com/sun/" };

    public Builder ignore(String[] strings) {
      this.ignore = strings;
      return this;
    }

    private String level = "info";

    public Builder level(String level) {
      level = level.toLowerCase();
      if (level.equals("info") || level.equals("debug")
          || level.equals("trace")) {
        this.level = level;
      } else {
        if (verbose) {
          System.err.println("level not info/debug/trace : " + level);
        }
      }
      return this;
    }
  }

  private String level;
  private String levelEnabled;

  private LogTransformer(Builder builder) {
    String s = "WARNING: javassist not available on classpath for javaagent, log statements will not be added";
    try {
      if (Class.forName("javassist.ClassPool") == null) {
        System.err.println(s);
      }
    } catch (ClassNotFoundException e) {
      System.err.println(s);
    }
    
    this.addEntryExit = builder.addEntryExit;
//    this.addVariableAssignment = builder.addVariableAssignment;
    this.verbose = builder.verbose;
    this.ignore = builder.ignore;
    this.level = builder.level;
    this.levelEnabled = "is" + builder.level.substring(0, 1).toUpperCase()
        + builder.level.substring(1) + "Enabled";
  }

  private static final String _LOG = "_log";

  private boolean addEntryExit;
//  private boolean addVariableAssignment;
  private boolean verbose;
  private String[] ignore;

  public byte[] transform(ClassLoader loader, String className, Class<?> clazz,
      ProtectionDomain domain, byte[] bytes) {

    try {
      return transform0(className, clazz, bytes);
    } catch (Exception e) {
      System.err.println("Could not instrument " + className);
      e.printStackTrace();
      return bytes;
    }
  }

  /**
   * transform0 sees if the className starts with any of the namespaces to
   * ignore, if so it is returned unchanged. Otherwise it is processed by
   * doClass(...)
   * 
   * @param className
   * @param clazz
   * @param bytes
   * @return
   */
  private byte[] transform0(String className, Class<?> clazz, byte[] bytes) {
    for (int i = 0; i < ignore.length; i++) {
      if (className.startsWith(ignore[i])) {
        return bytes;
      }
    }
    if (verbose) {
      System.err.println("Processing " + className);
    }
    return doClass(className, clazz, bytes);
  }

  /**
   * doClass() process a single class by first creates a class description from
   * the byte codes. If it is a class (i.e. not an interface) the methods
   * defined have bodies, and a static final logger object is added with the
   * name of this class as an argument, and each method then gets processed with
   * doMethod(...) to have logger calls added.
   * 
   * @param name
   *          class name (slashes separate, not dots)
   * @param clazz
   * @param b
   * @return
   */
  private byte[] doClass(String name, Class<?> clazz, byte[] b) {
    ClassPool pool = ClassPool.getDefault();
    CtClass cl = null;
    try {
      cl = pool.makeClass(new ByteArrayInputStream(b));
      if (cl.isInterface() == false) {

        // We have to define the log variable.
        String pattern1 = "private static org.slf4j.Logger {};";
        String loggerDefinition = format(pattern1, _LOG);
        CtField field = CtField.make(loggerDefinition, cl);

        // and assign it the appropriate value.
        String pattern2 = "org.slf4j.LoggerFactory.getLogger({}.class);";
        String replace = name.replace('/', '.');
        String getLogger = format(pattern2, replace);

        cl.addField(field, getLogger);

        // then check every behaviour (which includes methods). We are only
        // interested in non-empty ones, as they have code.

        CtBehavior[] methods = cl.getDeclaredBehaviors();
        for (int i = 0; i < methods.length; i++) {
          if (methods[i].isEmpty() == false) {
            doMethod(methods[i]);
          }
        }
        b = cl.toBytecode();
      }
    } catch (Exception e) {
      System.err.println("Could not instrument " + name + ", " + e);
      e.printStackTrace(System.err);
    } finally {
      if (cl != null) {
        cl.detach();
      }
    }
    return b;
  }

  /**
   * process a single method - this means add entry/exit logging if requested.
   * It is only called for methods with a body.
   * 
   * @param method
   *          method to work on
   * @throws NotFoundException
   * @throws CannotCompileException
   */
  private void doMethod(CtBehavior method) throws NotFoundException,
      CannotCompileException {

    String signature = JavassistHelper.getSignature(method);
    String returnValue = JavassistHelper.returnValue(method);

    if (addEntryExit) {
      String messagePattern = "if ({}.{}()) {}.{}(\">> {}\");";
      Object[] arg1 = new Object[] { _LOG, levelEnabled, _LOG, level, signature };
      String before = MessageFormatter.arrayFormat(messagePattern, arg1);
      // System.out.println(before);
      method.insertBefore(before);

      String messagePattern2 = "if ({}.{}()) {}.{}(\"<< {}{}\");";
      Object[] arg2 = new Object[] { _LOG, levelEnabled, _LOG, level,
          signature, returnValue };
      String after = MessageFormatter.arrayFormat(messagePattern2, arg2);
      // System.out.println(after);
      method.insertAfter(after);
    }
  }
}