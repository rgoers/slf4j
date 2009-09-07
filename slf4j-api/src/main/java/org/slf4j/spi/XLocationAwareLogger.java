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

package org.slf4j.spi;

import org.slf4j.Marker;
import org.slf4j.StructuredData;

/**
 * An <b>optional</b> interface helping integration with logging systems capable of
 * extracting location information. This interface extends the original interface to
 * allow structred data and any parameters.
 *
 *
 * @author Ralph Goers
 * @since 1.5.9
 */
public interface XLocationAwareLogger extends LocationAwareLogger {

  /**
   * Printing method with support for location information.
   *
   * @param marker The Marker.
   * @param fqcn The fully qualified class name of the <b>caller</b>
   * @param level The logging level.
   * @param message The message to be logged.
   * @param argArray Any parameters or null.
   * @param t A Throwable or null.
   */
  public void log(Marker marker, String fqcn, int level, String message, Object[] argArray, Throwable t);

  /**
   * Logs Structured Data. If the underlying logging implementation does not support structured data then
   * the format string will be used as advice on how to format the structured data.
   * @param marker The Marker
   * @param fqcn The fully qualified class name of the <b>caller</b>
   * @param level The logging level
   * @param data The StructuredData.
   * @param format The format style or null to use the default.
   * @param t A Throwable or null.
   */
  public void log(Marker marker, String fqcn, int level, StructuredData data, String format, Throwable t);

}