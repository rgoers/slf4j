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

package org.slf4j.ext;

import org.slf4j.StructuredDataImpl;
import org.slf4j.StructuredData;

import java.io.Serializable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.beans.ExceptionListener;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Base class for Event Data. Event Data contains data to be logged about an
 * event. Users may extend this class for each EventType they want to log.
 *
 * @author Ralph Goers
 * @deprecated Use StructuredDataImpl instead.
 */
public class EventData implements Serializable {

  private static final long serialVersionUID = 153270778642103985L;

  private StructuredEventData eventData = new StructuredEventData();
  public static final String EVENT_MESSAGE = "EventMessage";
  public static final String EVENT_TYPE = "EventType";
  public static final String EVENT_DATETIME = "EventDateTime";
  public static final String EVENT_ID = "EventId";
  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

  /**
   * Default Constructor
   */
  public EventData() {
  }

  /**
   * Constructor to create event data from a Map.
   *
   * @param map
   *          The event data.
   */
  public EventData(Map<String, Object> map) {
    Map<String, Object> tmp = new HashMap<String, Object>(map);
    String id = (String) tmp.remove(EVENT_ID);
    String msg = (String) tmp.remove(EVENT_MESSAGE);
    String type = (String) tmp.remove(EVENT_TYPE);
    eventData = new StructuredEventData(id, msg, type);
    eventData.putAll(tmp);
  }

  /**
   * Construct from a serialized form of the Map containing the RequestInfo
   * elements
   *
   * @param xml
   *          The serialized form of the RequestInfo Map.
   */
  @SuppressWarnings("unchecked")
  public EventData(String xml) {
    ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
    try {
      XMLDecoder decoder = new XMLDecoder(bais);
      Map<String, Object> map = (Map<String, Object>) decoder.readObject();
      String id = (String) map.remove(EVENT_ID);
      String msg = (String) map.remove(EVENT_MESSAGE);
      String type = (String) map.remove(EVENT_TYPE);
      this.eventData = new StructuredEventData(id, msg, type);
    } catch (Exception e) {
      throw new EventException("Error decoding " + xml, e);
    }
  }

  /**
   * Serialize all the EventData items into an XML representation.
   *
   * @return an XML String containing all the EventDAta items.
   */
  public String toXML() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.putAll(eventData.getData());
    map.put(EVENT_MESSAGE, eventData.getMessage());
    map.put(EVENT_TYPE, eventData.getType());
    map.put(EVENT_ID, eventData.getId().toString());
    return toXML(map);
  }

  /**
   * Serialize all the EventData items into an XML representation.
   *
   * @return an XML String containing all the EventDAta items.
   */
  public static String toXML(Map<String, Object> map) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      XMLEncoder encoder = new XMLEncoder(baos);
      encoder.setExceptionListener(new ExceptionListener() {
        public void exceptionThrown(Exception exception) {
          exception.printStackTrace();
        }
      });
      encoder.writeObject(map);
      encoder.close();
      return baos.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieve the event identifier.
   *
   * @return The event identifier
   */
  public String getEventId() {
    return eventData.getId().toString();
  }

  /**
   * Set the event identifier.
   *
   * @param eventId
   *          The event identifier.
   */
  public void setEventId(String eventId) {
    if (eventId == null) {
      throw new IllegalArgumentException("eventId cannot be null");
    }
    eventData.setId(eventId);
  }

  /**
   * Retrieve the message text associated with this event, if any.
   *
   * @return The message text associated with this event or null if there is
   *         none.
   */
  public String getMessage() {
    return eventData.getMessage();
  }

  /**
   * Set the message text associated with this event.
   *
   * @param message
   *          The message text.
   */
  public void setMessage(String message) {
    eventData.setMessage(message);
  }

  /**
   * Retrieve the date and time the event occurred.
   *
   * @return The Date associated with the event.
   */
  public Date getEventDateTime() {
    String eventDate = (String) eventData.getData().get(EVENT_DATETIME);
    if (eventDate == null) {
      return null;
    }
    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
    try {
      return format.parse(eventDate);
    } catch (ParseException pe) {
      return null;
    }
  }

  /**
   * Set the date and time the event occurred in case it is not the same as when
   * the event was logged.
   *
   * @param eventDateTime
   *          The event Date.
   */
  public void setEventDateTime(Date eventDateTime) {
    if (eventDateTime == null) {
      return;
    }
    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
    String date = format.format(eventDateTime);
    eventData.put(EVENT_DATETIME, date);
  }

  /**
   * Set the type of event that occurred.
   *
   * @param eventType
   *          The type of the event.
   */
  public void setEventType(String eventType) {
    eventData.setType(eventType);
  }

  /**
   * Retrieve the type of the event.
   *
   * @return The event type.
   */
  public String getEventType() {
    return eventData.getType();
  }

  /**
   * Add arbitrary attributes about the event.
   *
   * @param name
   *          The attribute's key.
   * @param obj
   *          The data associated with the key.
   */
  public void put(String name, Serializable obj) {
    eventData.put(name, obj.toString());
  }

  /**
   * Retrieve an event attribute.
   *
   * @param name
   *          The attribute's key.
   * @return The value associated with the key or null if the key is not
   *         present.
   */
  public Serializable get(String name) {
    return (Serializable) eventData.getData().get(name);
  }

  /**
   * Populate the event data from a Map.
   *
   * @param data
   *          The Map to copy.
   */
  public void putAll(Map<String, Object> data) {
    eventData.putAll(data);
  }

  /**
   * Returns the number of attributes in the EventData.
   *
   * @return the number of attributes in the EventData.
   */
  public int getSize() {
    return eventData.getData().size();
  }

  /**
   * Returns an Iterator over all the entries in the EventDAta.
   *
   * @return an Iterator that can be used to access all the event attributes.
   */
  public Iterator<Map.Entry<String, Object>> getEntrySetIterator() {
    return eventData.getData().entrySet().iterator();
  }

  /**
   * Retrieve all the attributes in the EventData as a Map. Changes to this map
   * will be reflected in the EventData.
   *
   * @return The Map of attributes in this EventData instance.
   */
  public Map<String, Object> getEventMap() {
    Map<String, Object> map = new HashMap<String, Object>(eventData.getData());
    map.put(EVENT_ID, eventData.getId());
    map.put(EVENT_TYPE, eventData.getType());
    map.put(EVENT_MESSAGE, eventData.getMessage());
    return map;
  }

  /**
   * Return the underlying StructuredData object.
   * @return The StructuredData Object.
   */
  public StructuredData getEventData() {
    return eventData;
  }

  /**
   * Convert the EventData to a String.
   *
   * @return The EventData as a String.
   */
  @Override
  public String toString() {
    return toXML();
  }

  /**
   * Compare two EventData objects for equality.
   *
   * @param o
   *          The Object to compare.
   * @return true if the objects are the same instance or contain all the same
   *         keys and their values.
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof EventData || o instanceof Map)) {
      return false;
    }
    Map<String, Object> map = (o instanceof EventData) ? ((EventData) o)
        .getEventMap() : (Map<String, Object>) o;

    return map.equals(getEventMap());
  }

  /**
   * Compute the hashCode for this EventData instance.
   *
   * @return The hashcode for this EventData instance.
   */
  @Override
  public int hashCode() {
    return this.eventData.hashCode();
  }

  private class StructuredEventData extends StructuredDataImpl {
    private static final long serialVersionUID = 1093221292892071920L;

    public StructuredEventData() {
    }

    public StructuredEventData(final String id, final String msg, final String type) {
      super(id, msg, type);
    }

    public void setId(String id) {
      super.setId(id);
    }

    public void setMessage(String msg) {
      super.setMessage(msg);
    }

    public void setType(String type) {
      super.setType(type);
    }

    public String asString(String format) {
      if (format.equals("XML")) {
        return toXML();
      }
      return asString(format, null);
    }
  }
}