package org.slf4j.ext;

import org.slf4j.StructuredData;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 */
public class StructuredDataImpl implements StructuredData{
  //private static final long serialVersionUID = 0;

  private Map data = new HashMap();

  private String id;

  private String message;

  public StructuredDataImpl(final String id, final String msg) {
    this.id = id;
    this.message = msg;
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
   * @param format The format identifier. Ignored in this implementation.
   * @return The formatted String.
   */
  public String asString(String format) {
    StringBuilder builder = new StringBuilder();
    String id = getId();
    if (id == null) {

    }
    builder.append("[");
    builder.append(getId());
    Iterator iter = getData().entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      builder.append(" ");
      builder.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
    }
    builder.append("]");
    String msg = getMessage();
    if (msg != null) {
      builder.append(" ").append(msg);
    }
    return builder.toString();
  }

  public String toString() {
    return asString(null);
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
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (message != null ? message.hashCode() : 0);
    return result;
  }
}
