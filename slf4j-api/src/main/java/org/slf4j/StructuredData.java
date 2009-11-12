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
import java.io.Serializable;

/**
 * Interface to represent Structured Data. While structured data is defined by RFC 5424 for use
 * in Syslogs, it is a generally useful concept that can aid in writing data that can be easily
 * interprested.
 *
 * @author Ralph Goers
 */
public interface StructuredData extends Serializable {
  /**
   * Returns the type of data. This corresponds to the MSGID in RFC 5424.
   *
   * @return The message type.
   */
  String getType();

  /**
   * Returns the identifier of structured data. This corresponds to the SD-ID
   * in RFC 5424.
   *
   * @return The structured data id.
   */
  StructuredDataId getId();

  /**
   * A message String that is associated with the data.
   *
   * @return The message String.
   */
  String getMessage();

  /**
   * Returns an immutable Map of the data items and their values. In Java 5 this Map would
   * be specified as Map<String, String>.
   *
   * @return The data item names (32 characters maximum) and their values.
   */
  Map getData();

  /**
   * Add all the items from a Map to the data Map.
   * @param map The map to copy.
   */
  void putAll(Map map);

  /**
   * Add an item to the data Map.
   * @param key The name of the item.
   * @param value The value of the item.
   */
  void put(String key, String value);

  /**
   * Get a specific value from the data Map.
   * @param key The name of the item.
   * @return The value of the item.
   */
  String get(String key);

  /**
   * Remove an item from the data Map.
   * @param key The name of the item to remove.
   * @return The value of the item removed.
   */
  String remove(String key);

  /**
   * Clears the data map.
   */
  void clear();

  /**
   * Formats the structured data in the form [id key="value" ...] message as described in RFC 5424.
   *
   * @return The formatted String.
   */
  String asString();

  /**
   * Formats the structured data in the specified format. If the format is null or is something
   * the implementation does not support then the String will be
   * in the form [id key="value" ...] message as described in RFC 5424.
   *
   * @param format The format identifier.
   * @return The formatted String.
   */
  String asString(String format);

  /**
   * Formats the structured data as described in RFC5424.
   *
   * @param format           The format identifier.
   * @param id               The default SD-ID as described in RFC 5424. This value will be used if
   *                         not id is present in the StructuredData.
   * @return The formatted String.
   */
  String asString(String format, StructuredDataId id);
}
