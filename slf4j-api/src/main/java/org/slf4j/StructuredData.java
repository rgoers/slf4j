/*
 * Copyright (c) 2004-2009 QOS.ch All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS  IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.slf4j;

import java.util.Map;

/**
 * Interface to represent Structured Data. While structured data is defined by RFC 5424 for use
 * in Syslogs, it is a generally useful concept that can aid in writing data that can be easily
 * interprested.
 *
 * @author Ralph Goers
 */
public interface StructuredData
{
  /**
   * Returns the key that identifies the type of structured data.
   * @return The structured data id.
   */
  String getId();

  /**
   * A message String that is associated with the data.
   * @return The message String.
   */
  String getMessage();

  /**
   * Returns the data items and their values. Although the Map can contain Objects, the
   * toString() method of the objects will be called when the objects are formatted for
   * inclusion in log records. If the object is not serializable then the value of toString
   * will be used when serialization is required.
   * @return The data item names (32 characters maximum) and their values.
   */
  Map getData();

  /**
   * Formats the structured data in the specified format. If the format is null or is something
   * the implementation does not support then the String will be
   * in the form [id key="value" ...] message as described in RFC 5424.
   * @param format The format identifier.
   * @return The formatted String.
   */
  String asString(String format);
}
