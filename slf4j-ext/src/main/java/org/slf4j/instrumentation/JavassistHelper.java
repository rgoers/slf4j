package org.slf4j.instrumentation;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;

public class JavassistHelper {

  /**
   * Create a javaassist source snippet which either is empty (for anything
   * which does not return a value) or a explanatory text around the $_
   * javaassist return value variable.
   * 
   * @param method
   *          descriptor of method
   * @return source snippet
   * @throws NotFoundException
   */
  public static String returnValue(CtBehavior method) throws NotFoundException {

    String returnValue = "";
    if (methodReturnsValue(method)) {
      returnValue = " returns: \" + $_ + \".";
    }
    return returnValue;
  }

  /**
   * determine if the given method returns a value, and return true if so. false
   * otherwise.
   * 
   * @param method
   * @return
   * @throws NotFoundException
   */
  private static boolean methodReturnsValue(CtBehavior method)
      throws NotFoundException {

    if (method instanceof CtMethod == false) {
      return false;
    }

    CtClass returnType = ((CtMethod) method).getReturnType();
    String returnTypeName = returnType.getName();

    boolean isVoidMethod = "void".equals(returnTypeName);

    boolean methodReturnsValue = isVoidMethod == false;
    return methodReturnsValue;
  }

  /**
   * Return javaassist source snippet which lists all the parameters and their
   * values. If available the source names are extracted from the debug
   * information and used, otherwise just a number is shown.
   * 
   * @param method
   * @return
   * @throws NotFoundException
   */
  public static String getSignature(CtBehavior method) throws NotFoundException {

    CtClass parameterTypes[] = method.getParameterTypes();

    CodeAttribute codeAttribute = method.getMethodInfo().getCodeAttribute();

    LocalVariableAttribute locals = null;

    if (codeAttribute != null) {
      locals = (LocalVariableAttribute) codeAttribute
          .getAttribute("LocalVariableTable");
    }

    String methodName = method.getName();

    StringBuffer sb = new StringBuffer(methodName + "(\" ");
    for (int i = 0; i < parameterTypes.length; i++) {
      if (i > 0) {
        sb.append(" + \", \" ");
      }

      CtClass parameterType = parameterTypes[i];
      boolean isArray = parameterType.isArray();
      CtClass arrayOf = parameterType.getComponentType();
      if (isArray) {
        while (arrayOf.isArray()) {
          arrayOf = arrayOf.getComponentType();
        }
      }

      sb.append(" + \"");
      sb.append(parameterNameFor(method, locals, i));
      sb.append("\" + \"=");

      // use Arrays.asList() to render array of objects.
      if (isArray && !arrayOf.isPrimitive()) {
        sb.append("\"+ java.util.Arrays.asList($" + (i + 1) + ")");
      } else {
        sb.append("\"+ $" + (i + 1));
      }
    }
    sb.append("+\")");

    String signature = sb.toString();
    return signature;
  }

  /**
   * Determine the name of parameter with index i in the given method. Use the
   * locals attributes about local variables from the classfile.
   * 
   * @param method
   * @param locals
   * @param i
   * @return
   */
  static String parameterNameFor(CtBehavior method,
      LocalVariableAttribute locals, int i) {
    if (locals == null) {
      return Integer.toString(i + 1);
    }

    int modifiers = method.getModifiers();

    int j = i;

    if (Modifier.isSynchronized(modifiers)) {
      // skip object to synchronize upon.
      j++;
      // System.err.println("Synchronized");
    }
    if (Modifier.isStatic(modifiers) == false) {
      // skip "this"
      j++;
      // System.err.println("Instance");
    }
    String variableName = locals.variableName(j);
    if (variableName.equals("this")) {
      System.err.println("this returned as a parameter name for "
          + method.getName() + " index " + j + ", names are probably shifted.");
    }
    return variableName;
  }
}
