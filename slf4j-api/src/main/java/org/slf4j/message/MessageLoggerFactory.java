package org.slf4j.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * This class is essentially a wrapper around an
 * {@link org.slf4j.LoggerFactory} producing {@link MessageLogger} instances.
 *
 * <p>Contrary to {@link org.slf4j.LoggerFactory#getLogger(String)} method of
 * {@link org.slf4j.LoggerFactory}, if the underlying implementation is not
 * a MessageLogger each call to getLogger produces a new instance of MessageLogger.
 * This should not matter because a MessageLogger instance does not have any state
 * beyond that of the Logger instance it wraps.
 *
 * @author Ralph Goers
 */
public class MessageLoggerFactory {

  /**
   * Get a MessageLogger instance by name.
   *
   * @param name The logger name
   * @return A MessageLogger.
   */
  public static MessageLogger getLogger(String name) {
    return getLogger(name, null);
  }

  /**
   * Get a MessageLogger instance by name.
   *
   * @param name The logger name
   * @param fqcn The fully qualified class name.
   * @return A MessageLogger.
   */
  public static MessageLogger getLogger(String name, String fqcn) {
    Logger logger = LoggerFactory.getLogger(name);
    if (logger instanceof MessageLogger) {
      return (MessageLogger) logger;
    } else {
      return new MessageLoggerWrapper(logger, fqcn);
    }
  }

  /**
   * Get a new XLogger instance by class. The returned XLogger
   * will be named after the class.
   *
   * @param clazz The class name.
   * @return A MessageLogger.
   */

  public static MessageLogger getLogger(Class clazz) {
    return getLogger(clazz.getName(), null);
  }

  /**
   * Get a new XLogger instance by class. The returned XLogger
   * will be named after the class.
   *
   * @param clazz The class name.
   * @param fqcn The fully qualified class name.
   * @return A MessageLogger.
   */

  public static MessageLogger getLogger(Class clazz, String fqcn) {
    return getLogger(clazz.getName(), fqcn);
  }

}