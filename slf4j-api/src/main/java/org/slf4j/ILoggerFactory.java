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

package org.slf4j;


/**
 * <code>ILoggerFactory</code> instances manufacture {@link Logger}
 * instances by name.
 * 
 * <p>Most users retrieve {@link Logger} instances through the static
 * {@link LoggerFactory#getLogger(String)} method. An instance of of this
 * interface is bound internally with {@link LoggerFactory} class at 
 * compile time. Only developers of SLF4J conforming logging systems 
 * need to worry about this interface. 
 * 
 * <p>See the section <a href="http://slf4j.org/faq.html#3">Implementing 
 * the SLF4J API</a> in the FAQ for details on how to make your logging 
 * system conform to SLF4J.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public interface ILoggerFactory {
  
  /**
   * Return an appropriate {@link Logger} instance as specified by the
   * <code>name</code> parameter.
   * 
   * <p>Null-valued name arguments are considered invalid.
   *
   * <p>Certain extremely simple logging systems, e.g. NOP, may always
   * return the same logger instance regardless of the requested name.
   * 
   * @param name the name of the Logger to return
   */
  public Logger getLogger(String name);
}
