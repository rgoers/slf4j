package org.slf4j.ext;

import org.slf4j.StructuredData;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 */
public class StructuredDataImpl implements StructuredData {
  public static final String FULL = "full";

  private Map data = new HashMap();

  private String id;

  private String message;

  private String type;

  public StructuredDataImpl(final String id, final String msg, final String type) {
    this.id = id;
    this.message = msg;
    this.type = type;
  }

  protected StructuredDataImpl() {

  }

  public String getId() {
    return id;
  }

  protected void setId(String id) {
    if (id.length() > 32) {
      throw new IllegalArgumentException("Structured data id exceeds maximum length of 32 characters: " + id);
    }
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
    return data;
  }

  /**
   * Format the Structured data as described in RFC 5424.
   * @return The formatted String.
   */
  public final String asString() {
    return asString(FULL, null, null);
  }

  /**
   * Format the Structured data as described in RFC 5424.
   * @param format The format identifier. Ignored in this implementation.
   * @return The formatted String.
   */
  public String asString(String format) {
    return asString(format, null, null);
  }
  /**
   * Format the Structured data as described in RFC 5424.
   * @param format "full" will include the type and message. null will return only the STRUCTURED-DATA as
   * described in RFC 5424
   * @param structuredDataId The SD-ID as described in RFC 5424. If null the value in the StructuredData
   * will be used.
   * @param maps Additional data to include.
   * @return The formatted String.
   */
  public final String asString(String format, String structuredDataId, Map[] maps) {
    StringBuffer sb = new StringBuffer();
    boolean full = FULL.equals(format);
    if (full) {
      String type = getType();
      if (type == null) {
        return sb.toString();
      }
      sb.append(getType()).append(" ");
    }
    String id = getId();
    if (id == null) {
      id = structuredDataId;
    }
    if (id == null) {
      return sb.toString();
    }
    sb.append("[");
    sb.append(id);
    appendMap(getData(), sb);
    if (maps != null) {
      for (int i = 0; i < maps.length; ++i) {
        appendMap(maps[i], sb);
      }
    }
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

  @Override
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

  @Override
  public int hashCode() {
    int result = data != null ? data.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (message != null ? message.hashCode() : 0);
    return result;
  }
}
