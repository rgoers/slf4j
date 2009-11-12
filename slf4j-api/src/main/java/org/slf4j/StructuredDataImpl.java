package org.slf4j;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;

/**
 *
 */
public class StructuredDataImpl implements StructuredData {
  private static final long serialVersionUID = 1703221292892071920L;

  public static final String FULL = "full";

  private Map data = new HashMap();

  private StructuredDataId id;

  private String message;

  private String type;

  public StructuredDataImpl(final String id, final String msg, final String type) {
    this.id = new StructuredDataId(id, null, null);
    this.message = msg;
    this.type = type;
  }

  public StructuredDataImpl(final StructuredDataId id, final String msg, final String type) {
    this.id = id;
    this.message = msg;
    this.type = type;
  }

  protected StructuredDataImpl() {

  }

  public StructuredDataId getId() {
    return id;
  }

  protected void setId(String id) {
    this.id = new StructuredDataId(id, null, null);
  }

  protected void setId(StructuredDataId id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  protected void setType(String type) {
    if (type.length() > 32) {
      throw new IllegalArgumentException("Structured data type exceeds maximum length of 32 characters: " + type);
    }
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  protected void setMessage(String msg) {
    this.message = msg;
  }

  public Map getData() {
    return Collections.unmodifiableMap(data);
  }

  public void clear() {
    data.clear();
  }

  public void put(String key, String value) {
    if (value == null) {
      throw new IllegalArgumentException("No value provided for key " + key);
    }
    if (value.length() > 32) {
      throw new IllegalArgumentException("Structured data values are limited to 32 characters. key: " + key +
        " value: " + value);
    }
    data.put(key, value);
  }

  public void putAll(Map map) {
    data.putAll(map);
  }

  public String get(String key) {
    return (String) data.get(key);
  }

  public String remove(String key) {
    return (String) data.remove(key);
  }

  /**
   * Format the Structured data as described in RFC 5424.
   * @return The formatted String.
   */
  public final String asString() {
    return asString(FULL, null);
  }

  /**
   * Format the Structured data as described in RFC 5424.
   * @param format The format identifier. Ignored in this implementation.
   * @return The formatted String.
   */
  public String asString(String format) {
    return asString(format, null);
  }
  /**
   * Format the Structured data as described in RFC 5424.
   * @param format "full" will include the type and message. null will return only the STRUCTURED-DATA as
   * described in RFC 5424
   * @param structuredDataId The SD-ID as described in RFC 5424. If null the value in the StructuredData
   * will be used.
   * @return The formatted String.
   */
  public final String asString(String format, StructuredDataId structuredDataId) {
    StringBuffer sb = new StringBuffer();
    boolean full = FULL.equals(format);
    if (full) {
      String type = getType();
      if (type == null) {
        return sb.toString();
      }
      sb.append(getType()).append(" ");
    }
    StructuredDataId id = getId();
    if (id != null) {
      id = id.makeId(structuredDataId);
    } else {
      id = structuredDataId;
    }
    if (id == null || id.getName() == null || getData().size() == 0) {
      return sb.toString();
    }
    sb.append("[");
    sb.append(id);
    appendMap(getData(), sb);
    sb.append("]");
    if (full) {
      String msg = getMessage();
      if (msg != null) {
        sb.append(" ").append(msg);
      }
    }
    return sb.toString();
  }

  private void appendMap(Map map, StringBuffer sb) {
    Iterator iter = map.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      sb.append(" ");
      sb.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
    }
  }

  public String toString() {
    return asString((String) null);
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    StructuredDataImpl that = (StructuredDataImpl) o;

    if (data != null ? !data.equals(that.data) : that.data != null) {
      return false;
    }
    if (type != null ? !type.equals(that.type) : that.type != null) {
      return false;
    }
    if (id != null ? !id.equals(that.id) : that.id != null) {
      return false;
    }
    if (message != null ? !message.equals(that.message) : that.message != null) {
      return false;
    }

    return true;
  }

  public int hashCode() {
    int result = data != null ? data.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (message != null ? message.hashCode() : 0);
    return result;
  }
}
