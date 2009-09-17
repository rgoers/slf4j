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
  StructuredData.Id getId();

  /**
   * A message String that is associated with the data.
   *
   * @return The message String.
   */
  String getMessage();

  /**
   * Returns the data items and their values. Although the Map can contain Objects, the
   * toString() method of the objects will be called when the objects are formatted for
   * inclusion in log records. If the object is not serializable then the value of toString
   * will be used when serialization is required.
   *
   * @return The data item names (32 characters maximum) and their values.
   */
  Map getData();

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
   * Formats the structured data as described in RFC5424 including the data in the additional maps
   * if any are present.
   *
   * @param format           The format identifier.
   * @param id               The default SD-ID as described in RFC 5424. This value will be used if
   *                         not id is present in the StructuredData.
   * @param maps             Additional data to include.
   * @return The formatted String.
   */
  String asString(String format, StructuredData.Id id, Map[] maps);

  /**
   * The StructuredData identifier
   */
  public class Id implements Serializable {
    private static final long serialVersionUID = 9031746276396249990L;

    public static Id TIME_QUALITY = new Id("timeQuality", null,
        new String[]{"tzKnown", "isSynced", "syncAccuracy"});
    public static Id ORIGIN = new Id("origin", null,
        new String[]{"ip", "enterpriseId", "software", "swVersion"});
    public static Id META = new Id("meta", null,
        new String[]{"sequenceId", "sysUpTime", "language"});

    public static final int RESERVED = -1;

    private final String name;
    private final int enterpriseNumber;
    private final String[] required;
    private final String[] optional;

    protected Id(String name, String[] required, String[] optional) {
      int index = -1;
      if (name != null) {
        if (name.length() > 32) {
          throw new IllegalArgumentException("Length of id exceeds maximum of 32 characters: " + name);
        }
        index = name.indexOf("@");
      }

      if (index > 0) {
        this.name = name.substring(0, index);
        this.enterpriseNumber = Integer.parseInt(name.substring(index+1));
      } else {
        this.name = name;
        this.enterpriseNumber = RESERVED;
      }
      this.required = required;
      this.optional = optional;
    }

    /**
     * A Constructor that helps conformance to RFC 5424.
     *
     * @param name             The name portion of the id.
     * @param enterpriseNumber The enterprise number.
     * @param required         The list of keys that are required for this id.
     * @param optional         The list of keys that are optional for this id.
     */
    public Id(String name, int enterpriseNumber, String[] required, String[] optional) {
      if (name == null) {
        throw new IllegalArgumentException("No structured id name was supplied");
      }
      if (enterpriseNumber <= 0) {
        throw new IllegalArgumentException("No enterprise number was supplied");
      }
      this.name = name;
      this.enterpriseNumber = enterpriseNumber;
      String id = enterpriseNumber < 0 ? name : name + "@" + enterpriseNumber;
      if (id.length() > 32) {
        throw new IllegalArgumentException("Length of id exceeds maximum of 32 characters: " + id);
      }
      this.required = required;
      this.optional = optional;
    }

    public Id makeId(StructuredData.Id id) {
      if (id == null) {
        return this;
      }
      return makeId(id.getName(), id.getEnterpriseNumber());
    }

    public Id makeId(String defaultId, int enterpriseNumber) {
      String id;
      String[] req;
      String[] opt;
      if (enterpriseNumber <= 0) {
        return this;
      }
      if (this.name != null) {
        id = this.name;
        req = this.required;
        opt = this.optional;
      } else {
        id = defaultId;
        req = null;
        opt = null;
      }

      return new Id(id, enterpriseNumber, req, opt);
    }

    public String[] getRequired() {
      return required;
    }

    public String[] getOptional() {
      return optional;
    }

    public String getName() {
      return name;
    }

    public int getEnterpriseNumber() {
      return enterpriseNumber;
    }

    public boolean isReserved() {
      return enterpriseNumber <= 0;
    }

    public String toString() {
      return isReserved() ? name : name + "@" + enterpriseNumber;
    }
  }
}
