/*
 * Copyright (c) 2004-2007 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.slf4j.message;

import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

/**
 * An <b>optional</b> interface helping integration with logging systems capable of
 * extracting location information. This interface extends the original interface to
 * allow structred data and any parameters.
 *
 *
 * @author Ralph Goers
 * @since 1.5.11
 */
public interface MessageLogger extends LocationAwareLogger {

  /**
   * Logs Messages.
   * @param marker The Marker
   * @param fqcn The fully qualified class name of the <b>caller</b>
   * @param level The logging level
   * @param data The Message.
   * @param t A Throwable or null.
   */
  public void log(Marker marker, String fqcn, int level, Message data, Throwable t);

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param msg the message string to be logged
   */
  public void trace(Message msg);

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void trace(Message msg, Throwable t);

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  public void trace(Marker marker, Message msg);

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void trace(Marker marker, Message msg, Throwable t);

  /**
   * Log a message with the specific Marker at the DEBUG level.
   *
   * @param msg the message string to be logged
   */
  public void debug(Message msg);

  /**
   * Log a message with the specific Marker at the DEBUG level.
   *
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void debug(Message msg, Throwable t);

  /**
   * Log a message with the specific Marker at the DEBUG level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  public void debug(Marker marker, Message msg);

  /**
   * Log a message with the specific Marker at the DEBUG level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void debug(Marker marker, Message msg, Throwable t);

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param msg the message string to be logged
   */
  public void info(Message msg);

  /**
   * Log a message with the specific Marker at the INFO level.
   *
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void info(Message msg, Throwable t);

  /**
   * Log a message with the specific Marker at the INFO level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  public void info(Marker marker, Message msg);

  /**
   * Log a message with the specific Marker at the INFO level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void info(Marker marker, Message msg, Throwable t);

  /**
   * Log a message with the specific Marker at the WARN level.
   *
   * @param msg the message string to be logged
   */
  public void warn(Message msg);

  /**
   * Log a message with the specific Marker at the WARN level.
   *
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void warn(Message msg, Throwable t);

  /**
   * Log a message with the specific Marker at the WARN level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  public void warn(Marker marker, Message msg);

  /**
   * Log a message with the specific Marker at the WARN level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void warn(Marker marker, Message msg, Throwable t);

  /**
   * Log a message with the specific Marker at the ERROR level.
   *
   * @param msg the message string to be logged
   */
  public void error(Message msg);

  /**
   * Log a message with the specific Marker at the ERROR level.
   *
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void error(Message msg, Throwable t);

  /**
   * Log a message with the specific Marker at the ERROR level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  public void error(Marker marker, Message msg);

  /**
   * Log a message with the specific Marker at the ERROR level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   * @param t A Throwable or null.
   */
  public void error(Marker marker, Message msg, Throwable t);
}