package org.slf4j.message;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

/**
 * A helper class wrapping an {@link org.slf4j.Logger}
 * instance preserving location information if the wrapped
 * instance supports it.
 *
 * @author Ralph Goers
 * @author Ceki G&uuml;lc&uuml;
 */
public class MessageLoggerWrapper implements MessageLogger {

  // To ensure consistency between two instances sharing the same name (homonyms)
  // a LoggerWrapper should not contain any state beyond
  // the Logger instance it wraps.
  // Note that 'instanceofLAL' directly depends on Logger.
  // fqcn depend on the caller, but its value would not be different
  // between successive invocations of a factory class

  protected final Logger logger;
  final String fqcn;
  // is this logger instance a LocationAwareLogger
  protected final boolean instanceofLAL;

  // is this logger instance an MessageLogger
  protected final boolean instanceofML;

  public MessageLoggerWrapper(Logger logger, String fqcn) {
    this.logger = logger;
    this.fqcn = (fqcn != null) ? fqcn : MessageLoggerWrapper.class.getName();
    if (logger instanceof LocationAwareLogger) {
      instanceofLAL = true;
      if (logger instanceof MessageLogger) {
        instanceofML = true;
      } else {
        instanceofML = false;
      }
    } else {
      instanceofLAL = false;
      instanceofML = false;
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isTraceEnabled() {
    return logger.isTraceEnabled();
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isTraceEnabled(Marker marker) {
    return logger.isTraceEnabled(marker);
  }

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param msg the message string to be logged
   */
  public void trace(Message msg) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, TRACE_INT, msg, null);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          TRACE_INT, msg.getFormattedMessage(), null);
    } else {
      logger.trace(msg.getFormattedMessage());
    }
  }

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void trace(Message msg, Throwable t) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, TRACE_INT, msg, t);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          TRACE_INT, msg.getFormattedMessage(), t);
    } else {
      logger.trace(msg.getFormattedMessage(), t);
    }
  }


  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  public void trace(Marker marker, Message msg) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(marker, fqcn, TRACE_INT, msg, null);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn,
          TRACE_INT, msg.getFormattedMessage(), null);
    } else {
      logger.trace(marker, msg.getFormattedMessage());
    }
  }

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void trace(Marker marker, Message msg, Throwable t) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, TRACE_INT, msg, t);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          TRACE_INT, msg.getFormattedMessage(), t);
    } else {
      logger.trace(msg.getFormattedMessage(), t);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String msg) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(null, fqcn, TRACE_INT, mesg, null);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn, TRACE_INT, msg, null);
    } else {
      logger.trace(msg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String format, Object arg) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, TRACE_INT, msg, t);
    }
    else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg);
      ((LocationAwareLogger) logger).log(null, fqcn,
          TRACE_INT, formattedMessage, null);
    } else {
      logger.trace(format, arg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String format, Object arg1, Object arg2) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg1, arg2});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, TRACE_INT, msg, t);
    }
    else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg1, arg2);
      ((LocationAwareLogger) logger).log(null, fqcn,
          TRACE_INT, formattedMessage, null);
    } else {
      logger.trace(format, arg1, arg2);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String format, Object[] argArray) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, argArray);
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, TRACE_INT, msg, t);
    }
    else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.arrayFormat(format, argArray);
      ((LocationAwareLogger) logger).log(null, fqcn,
          TRACE_INT, formattedMessage, null);
    } else {
      logger.trace(format, argArray);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String msg, Throwable t) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(null, fqcn, TRACE_INT, mesg, t);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn, TRACE_INT, msg, t);
    } else {
      logger.trace(msg, t);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String msg) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(marker, fqcn, TRACE_INT, mesg, null);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, TRACE_INT, msg, null);
    } else {
      logger.trace(marker, msg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String format, Object arg) {
    if (!logger.isTraceEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, TRACE_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg);
      ((LocationAwareLogger) logger).log(marker, fqcn,
          TRACE_INT, formattedMessage, null);
    } else {
      logger.trace(marker, format, arg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String format, Object arg1, Object arg2) {
    if (!logger.isTraceEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg1, arg2});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, TRACE_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg1, arg2);
      ((LocationAwareLogger) logger).log(marker, fqcn,
          TRACE_INT, formattedMessage, null);
    } else {
      logger.trace(marker, format, arg1, arg2);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String format, Object[] argArray) {
    if (!logger.isTraceEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, argArray);
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, TRACE_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.arrayFormat(format, argArray);
      ((LocationAwareLogger) logger).log(marker, fqcn,
          TRACE_INT, formattedMessage, null);
    } else {
      logger.trace(marker, format, argArray);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String msg, Throwable t) {
    if (!logger.isTraceEnabled())
      return;
    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(marker, fqcn, TRACE_INT, mesg, t);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, TRACE_INT, msg, t);
    } else {
      logger.trace(marker, msg, t);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isDebugEnabled(Marker marker) {
    return logger.isDebugEnabled(marker);
  }

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param msg the message string to be logged
   */
  public void debug(Message msg) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, DEBUG_INT, msg, null);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          DEBUG_INT, msg.getFormattedMessage(), null);
    } else {
      logger.debug(msg.getFormattedMessage());
    }
  }

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void debug(Message msg, Throwable t) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, DEBUG_INT, msg, t);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          DEBUG_INT, msg.getFormattedMessage(), t);
    } else {
      logger.debug(msg.getFormattedMessage(), t);
    }
  }


  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  public void debug(Marker marker, Message msg) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(marker, fqcn, DEBUG_INT, msg, null);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn,
          DEBUG_INT, msg.getFormattedMessage(), null);
    } else {
      logger.debug(marker, msg.getFormattedMessage());
    }
  }

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void debug(Marker marker, Message msg, Throwable t) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, DEBUG_INT, msg, t);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          DEBUG_INT, msg.getFormattedMessage(), t);
    } else {
      logger.debug(msg.getFormattedMessage(), t);
    }
  }
  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String msg) {
    if (!logger.isDebugEnabled())
      return;

    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(null, fqcn, DEBUG_INT, mesg, null);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn, DEBUG_INT, msg, null);
    } else {
      logger.debug(msg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String format, Object arg) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, DEBUG_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg);
      ((LocationAwareLogger) logger).log(null, fqcn, DEBUG_INT, formattedMessage, null);
    } else {
      logger.debug(format, arg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String format, Object arg1, Object arg2) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg1, arg2});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, DEBUG_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg1, arg2);
      ((LocationAwareLogger) logger).log(null, fqcn, DEBUG_INT, formattedMessage, null);
    } else {
      logger.debug(format, arg1, arg2);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String format, Object[] argArray) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, argArray);
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, DEBUG_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.arrayFormat(format, argArray);
      ((LocationAwareLogger) logger).log(null, fqcn, DEBUG_INT, formattedMessage, null);
    } else {
      logger.debug(format, argArray);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String msg, Throwable t) {
    if (!logger.isDebugEnabled())
      return;

    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(null, fqcn, DEBUG_INT, mesg, t);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn, DEBUG_INT, msg, t);
    } else {
      logger.debug(msg, t);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String msg) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(marker, fqcn, DEBUG_INT, mesg, null);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, DEBUG_INT, msg, null);
    } else {
      logger.debug(marker, msg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String format, Object arg) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(marker, fqcn, DEBUG_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg);
      ((LocationAwareLogger) logger).log(marker, fqcn, DEBUG_INT, formattedMessage, null);
    } else {
      logger.debug(marker, format, arg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String format, Object arg1, Object arg2) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg1, arg2});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(marker, fqcn, DEBUG_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg1, arg2);
      ((LocationAwareLogger) logger).log(marker, fqcn, DEBUG_INT, formattedMessage, null);
    } else {
      logger.debug(marker, format, arg1, arg2);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String format, Object[] argArray) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, argArray);
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(marker, fqcn, DEBUG_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.arrayFormat(format, argArray);
      ((LocationAwareLogger) logger).log(marker, fqcn, DEBUG_INT, formattedMessage, null);
    } else {
      logger.debug(marker, format, argArray);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String msg, Throwable t) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(marker, fqcn, DEBUG_INT, mesg, t);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, DEBUG_INT, msg, t);
    } else {
      logger.debug(marker, msg, t);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isInfoEnabled(Marker marker) {
    return logger.isInfoEnabled(marker);
  }

  /**
   * Log a message with the specific Marker at the INFO level.
   *
   * @param msg the message string to be logged
   */
  public void info(Message msg) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, INFO_INT, msg, null);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          INFO_INT, msg.getFormattedMessage(), null);
    } else {
      logger.info(msg.getFormattedMessage());
    }
  }

  /**
   * Log a message with the specific Marker at the INFO level.
   *
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void info(Message msg, Throwable t) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, INFO_INT, msg, t);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          INFO_INT, msg.getFormattedMessage(), t);
    } else {
      logger.info(msg.getFormattedMessage(), t);
    }
  }


  /**
   * Log a message with the specific Marker at the INFO level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  public void info(Marker marker, Message msg) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(marker, fqcn, INFO_INT, msg, null);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn,
          INFO_INT, msg.getFormattedMessage(), null);
    } else {
      logger.info(marker, msg.getFormattedMessage());
    }
  }

  /**
   * Log a message with the specific Marker at the INFO level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void info(Marker marker, Message msg, Throwable t) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, INFO_INT, msg, t);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          INFO_INT, msg.getFormattedMessage(), t);
    } else {
      logger.info(msg.getFormattedMessage(), t);
    }
  }
  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String msg) {
    if (!logger.isDebugEnabled())
      return;

    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(null, fqcn, INFO_INT, mesg, null);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn, INFO_INT, msg, null);
    } else {
      logger.info(msg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String format, Object arg) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, INFO_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg);
      ((LocationAwareLogger) logger).log(null, fqcn, INFO_INT, formattedMessage, null);
    } else {
      logger.info(format, arg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String format, Object arg1, Object arg2) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg1, arg2});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, INFO_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg1, arg2);
      ((LocationAwareLogger) logger).log(null, fqcn, INFO_INT, formattedMessage, null);
    } else {
      logger.info(format, arg1, arg2);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String format, Object[] argArray) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, argArray);
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, INFO_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.arrayFormat(format, argArray);
      ((LocationAwareLogger) logger).log(null, fqcn, INFO_INT, formattedMessage, null);
    } else {
      logger.info(format, argArray);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String msg, Throwable t) {
    if (!logger.isDebugEnabled())
      return;

    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(null, fqcn, INFO_INT, mesg, t);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn, INFO_INT, msg, t);
    } else {
      logger.info(msg, t);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String msg) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(marker, fqcn, INFO_INT, mesg, null);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, INFO_INT, msg, null);
    } else {
      logger.info(marker, msg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String format, Object arg) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(marker, fqcn, INFO_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg);
      ((LocationAwareLogger) logger).log(marker, fqcn, INFO_INT, formattedMessage, null);
    } else {
      logger.info(marker, format, arg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String format, Object arg1, Object arg2) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg1, arg2});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(marker, fqcn, INFO_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg1, arg2);
      ((LocationAwareLogger) logger).log(marker, fqcn, INFO_INT, formattedMessage, null);
    } else {
      logger.info(marker, format, arg1, arg2);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String format, Object[] argArray) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, argArray);
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(marker, fqcn, INFO_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.arrayFormat(format, argArray);
      ((LocationAwareLogger) logger).log(marker, fqcn, INFO_INT, formattedMessage, null);
    } else {
      logger.info(marker, format, argArray);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String msg, Throwable t) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(marker, fqcn, INFO_INT, mesg, t);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, INFO_INT, msg, t);
    } else {
      logger.info(marker, msg, t);
    }
  }

  public boolean isWarnEnabled() {
    return logger.isWarnEnabled();
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isWarnEnabled(Marker marker) {
    return logger.isWarnEnabled(marker);
  }

  /**
   * Log a message with the specific Marker at the WARN level.
   *
   * @param msg the message string to be logged
   */
  public void warn(Message msg) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, WARN_INT, msg, null);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          WARN_INT, msg.getFormattedMessage(), null);
    } else {
      logger.warn(msg.getFormattedMessage());
    }
  }

  /**
   * Log a message with the specific Marker at the WARN level.
   *
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void warn(Message msg, Throwable t) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, WARN_INT, msg, t);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          WARN_INT, msg.getFormattedMessage(), t);
    } else {
      logger.warn(msg.getFormattedMessage(), t);
    }
  }


  /**
   * Log a message with the specific Marker at the WARN level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  public void warn(Marker marker, Message msg) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(marker, fqcn, WARN_INT, msg, null);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn,
          WARN_INT, msg.getFormattedMessage(), null);
    } else {
      logger.warn(marker, msg.getFormattedMessage());
    }
  }

  /**
   * Log a message with the specific Marker at the WARN level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void warn(Marker marker, Message msg, Throwable t) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, WARN_INT, msg, t);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          WARN_INT, msg.getFormattedMessage(), t);
    } else {
      logger.warn(msg.getFormattedMessage(), t);
    }
  }
  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String msg) {
    if (!logger.isDebugEnabled())
      return;

    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(null, fqcn, WARN_INT, mesg, null);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn, WARN_INT, msg, null);
    } else {
      logger.warn(msg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String format, Object arg) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, WARN_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg);
      ((LocationAwareLogger) logger).log(null, fqcn, WARN_INT, formattedMessage, null);
    } else {
      logger.warn(format, arg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String format, Object arg1, Object arg2) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg1, arg2});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, WARN_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg1, arg2);
      ((LocationAwareLogger) logger).log(null, fqcn, WARN_INT, formattedMessage, null);
    } else {
      logger.warn(format, arg1, arg2);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String format, Object[] argArray) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, argArray);
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, WARN_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.arrayFormat(format, argArray);
      ((LocationAwareLogger) logger).log(null, fqcn, WARN_INT, formattedMessage, null);
    } else {
      logger.warn(format, argArray);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String msg, Throwable t) {
    if (!logger.isDebugEnabled())
      return;

    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(null, fqcn, WARN_INT, mesg, t);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn, WARN_INT, msg, t);
    } else {
      logger.warn(msg, t);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String msg) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(marker, fqcn, WARN_INT, mesg, null);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, WARN_INT, msg, null);
    } else {
      logger.warn(marker, msg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String format, Object arg) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(marker, fqcn, WARN_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg);
      ((LocationAwareLogger) logger).log(marker, fqcn, WARN_INT, formattedMessage, null);
    } else {
      logger.warn(marker, format, arg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String format, Object arg1, Object arg2) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg1, arg2});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(marker, fqcn, WARN_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg1, arg2);
      ((LocationAwareLogger) logger).log(marker, fqcn, WARN_INT, formattedMessage, null);
    } else {
      logger.warn(marker, format, arg1, arg2);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String format, Object[] argArray) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, argArray);
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(marker, fqcn, WARN_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.arrayFormat(format, argArray);
      ((LocationAwareLogger) logger).log(marker, fqcn, WARN_INT, formattedMessage, null);
    } else {
      logger.warn(marker, format, argArray);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String msg, Throwable t) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(marker, fqcn, WARN_INT, mesg, t);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, WARN_INT, msg, t);
    } else {
      logger.warn(marker, msg, t);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isErrorEnabled() {
    return logger.isErrorEnabled();
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isErrorEnabled(Marker marker) {
    return logger.isErrorEnabled(marker);
  }

  /**
   * Log a message with the specific Marker at the ERROR level.
   *
   * @param msg the message string to be logged
   */
  public void error(Message msg) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, ERROR_INT, msg, null);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          ERROR_INT, msg.getFormattedMessage(), null);
    } else {
      logger.error(msg.getFormattedMessage());
    }
  }

  /**
   * Log a message with the specific Marker at the ERROR level.
   *
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void error(Message msg, Throwable t) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, ERROR_INT, msg, t);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          ERROR_INT, msg.getFormattedMessage(), t);
    } else {
      logger.error(msg.getFormattedMessage(), t);
    }
  }


  /**
   * Log a message with the specific Marker at the ERROR level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  public void error(Marker marker, Message msg) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(marker, fqcn, ERROR_INT, msg, null);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn,
          ERROR_INT, msg.getFormattedMessage(), null);
    } else {
      logger.error(marker, msg.getFormattedMessage());
    }
  }

  /**
   * Log a message with the specific Marker at the ERROR level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void error(Marker marker, Message msg, Throwable t) {
    if (!logger.isTraceEnabled())
      return;

    if (instanceofML) {
      ((MessageLogger) logger).log(null, fqcn, ERROR_INT, msg, t);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn,
          ERROR_INT, msg.getFormattedMessage(), t);
    } else {
      logger.error(msg.getFormattedMessage(), t);
    }
  }
  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String msg) {
    if (!logger.isDebugEnabled())
      return;

    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(null, fqcn, ERROR_INT, mesg, null);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn, ERROR_INT, msg, null);
    } else {
      logger.error(msg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String format, Object arg) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, ERROR_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg);
      ((LocationAwareLogger) logger).log(null, fqcn, ERROR_INT, formattedMessage, null);
    } else {
      logger.error(format, arg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String format, Object arg1, Object arg2) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg1, arg2});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, ERROR_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg1, arg2);
      ((LocationAwareLogger) logger).log(null, fqcn, ERROR_INT, formattedMessage, null);
    } else {
      logger.error(format, arg1, arg2);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String format, Object[] argArray) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, argArray);
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(null, fqcn, ERROR_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.arrayFormat(format, argArray);
      ((LocationAwareLogger) logger).log(null, fqcn, ERROR_INT, formattedMessage, null);
    } else {
      logger.error(format, argArray);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String msg, Throwable t) {
    if (!logger.isDebugEnabled())
      return;

    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(null, fqcn, ERROR_INT, mesg, t);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(null, fqcn, ERROR_INT, msg, t);
    } else {
      logger.error(msg, t);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String msg) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(marker, fqcn, ERROR_INT, mesg, null);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, ERROR_INT, msg, null);
    } else {
      logger.error(marker, msg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String format, Object arg) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(marker, fqcn, ERROR_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg);
      ((LocationAwareLogger) logger).log(marker, fqcn, ERROR_INT, formattedMessage, null);
    } else {
      logger.error(marker, format, arg);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String format, Object arg1, Object arg2) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, new Object[] {arg1, arg2});
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(marker, fqcn, ERROR_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.format(format, arg1, arg2);
      ((LocationAwareLogger) logger).log(marker, fqcn, ERROR_INT, formattedMessage, null);
    } else {
      logger.error(marker, format, arg1, arg2);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String format, Object[] argArray) {
    if (!logger.isDebugEnabled())
      return;
    if (instanceofML) {
      ParameterizedMessage msg = new ParameterizedMessage(format, argArray);
      Throwable t = msg.getThrowable();
      ((MessageLogger) logger).log(marker, fqcn, ERROR_INT, msg, t);
    } else if (instanceofLAL) {
      String formattedMessage = MessageFormatter.arrayFormat(format, argArray);
      ((LocationAwareLogger) logger).log(marker, fqcn, ERROR_INT, formattedMessage, null);
    } else {
      logger.error(marker, format, argArray);
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String msg, Throwable t) {
    if (!logger.isTraceEnabled())
      return;
    if (instanceofML) {
      Message mesg = new SimpleMessage(msg);
      ((MessageLogger) logger).log(marker, fqcn, ERROR_INT, mesg, t);
    }
    else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, ERROR_INT, msg, t);
    } else {
      logger.error(marker, msg, t);
    }
  }

   /**
   * Logs Messages.
   * @param marker The Marker
   * @param fqcn The fully qualified class name of the <b>caller</b>
   * @param level The logging level
   * @param data The Message.
   * @param t A Throwable or null.
   */
  public void log(Marker marker, String fqcn, int level, Message data, Throwable t) {
    if (instanceofML) {
      ((MessageLogger) logger).log(marker, fqcn, level, data, t);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, level, data.getFormattedMessage(), t);
    } else {
      switch (level) {
        case DEBUG_INT:
          logger.debug(marker, data.getFormattedMessage(), t);
          break;
        case ERROR_INT:
          logger.error(marker, data.getFormattedMessage(), t);
          break;
        case INFO_INT:
          logger.info(marker, data.getFormattedMessage(), t);
          break;
        case WARN_INT:
          logger.warn(marker, data.getFormattedMessage(), t);
          break;
        case TRACE_INT:
          logger.trace(marker, data.getFormattedMessage(), t);
          break;
      }
    }
  }

  /**
   * Printing method with support for location information.
   *
   * @param marker
   * @param fqcn The fully qualified class name of the <b>caller</b>
   * @param level
   * @param message
   * @param t
   */
  public void log(Marker marker, String fqcn, int level, String message, Throwable t) {
     if (instanceofML) {
      Message msg = new SimpleMessage(message);
      ((MessageLogger) logger).log(marker, fqcn, level, msg, t);
    } else if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, level, message, t);
    } else {
      switch (level) {
        case DEBUG_INT:
          logger.debug(marker, message, t);
          break;
        case ERROR_INT:
          logger.error(marker, message, t);
          break;
        case INFO_INT:
          logger.info(marker, message, t);
          break;
        case WARN_INT:
          logger.warn(marker, message, t);
          break;
        case TRACE_INT:
          logger.trace(marker, message, t);
          break;
      }
    }
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public String getName() {
    return logger.getName();
  }
}