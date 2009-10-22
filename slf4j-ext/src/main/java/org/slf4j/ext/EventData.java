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
import org.slf4j.StructuredDataId;

import java.io.Serializable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.AbstractSet;
import java.util.AbstractCollection;
import java.util.HashMap;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.beans.ExceptionListener;

/**
 * Base class for Event Data. Event Data contains data to be logged about an
 * event. Users may extend this class for each EventType they want to log.
 *
 * @author Ralph Goers
 */
public class EventData extends StructuredDataImpl implements Serializable {

  private static final long serialVersionUID = 153270778642103985L;

  public static final String EVENT_MESSAGE = "EventMessage";
  public static final String EVENT_TYPE = "EventType";
  public static final String EVENT_DATETIME = "EventDateTime";
  public static final String EVENT_ID = "EventId";
  private EventMap eventData = new EventMap(getData());

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
    eventData.putAll(map);
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
      eventData.putAll((Map<String, Object>) decoder.readObject());
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
    return toXML(eventData);
  }

  /**
   * Serialize all the EventData items into an XML representation.
   * @param map The map containing the event data.
   * @return an XML String containing all the EventDAta items.
   */
  public static String toXML(Map<String, Object> map) {
    Map<String, Object> copy = new HashMap<String, Object>(map);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      XMLEncoder encoder = new XMLEncoder(baos);
      encoder.setExceptionListener(new ExceptionListener() {
        public void exceptionThrown(Exception exception) {
          exception.printStackTrace();
        }
      });
      encoder.writeObject(copy);
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
    return getId().toString();
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
    setId(eventId);
  }

  /**
   * Retrieve the message text associated with this event, if any.
   *
   * @return The message text associated with this event or null if there is
   *         none.
   */
  public String getMessage() {
    return super.getMessage();
  }

  /**
   * Set the message text associated with this event.
   *
   * @param message
   *          The message text.
   */
  public void setMessage(String message) {
    super.setMessage(message);
  }

  /**
   * Retrieve the date and time the event occurred.
   *
   * @return The Date associated with the event.
   */
  public Date getEventDateTime() {
    return (Date) getData().get(EVENT_DATETIME);
  }

  /**
   * Set the date and time the event occurred in case it is not the same as when
   * the event was logged.
   *
   * @param eventDateTime
   *          The event Date.
   */
  public void setEventDateTime(Date eventDateTime) {
    getData().put(EVENT_DATETIME, eventDateTime);
  }

  /**
   * Set the type of event that occurred.
   *
   * @param eventType
   *          The type of the event.
   */
  public void setEventType(String eventType) {
    setType(eventType);
  }

  /**
   * Retrieve the type of the event.
   *
   * @return The event type.
   */
  public String getEventType() {
    return getType();
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
    getData().put(name, obj);
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
    Object obj = eventData.get(name);
    if (obj == null) {
      return null;
    } else if (obj instanceof Serializable) {
      return (Serializable) obj;
    }
    return obj.toString();
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
    return eventData.size();
  }

  /**
   * Returns an Iterator over all the entries in the EventDAta.
   *
   * @return an Iterator that can be used to access all the event attributes.
   */
  public Iterator<Map.Entry<String, Object>> getEntrySetIterator() {
    return eventData.entrySet().iterator();
  }

  /**
   * Retrieve all the attributes in the EventData as a Map. Changes to this map
   * will be reflected in the EventData.
   *
   * @return The Map of attributes in this EventData instance.
   */
  public Map<String, Object> getEventMap() {
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
   * Format the EventData for printing.
   * @param format The format identifier. This implementation supports "XML".
   * @return The formatted EventData.
   */
  @Override
  public String asString(String format) {
    if ("XML".equalsIgnoreCase(format)) {
      return toXML();
    }
    return super.asString(format);
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
        .eventData : (Map<String, Object>) o;

    return eventData.equals(map);
  }

  /**
   * The EventMap makes the underlying StructuredData look like a Map which is convenient for serialization to XML.
   */
  private final class EventMap implements Map<String, Object> {

    private final Map<String, Object> parent;

    public EventMap(Map<String, Object> map) {
      parent = map;
    }

    public int size() {
      int count = 1;
      if (getMessage() != null) {
        ++count;
      }
      return parent.size() + count;
    }

    public boolean isEmpty() {
      return parent.isEmpty() && getId() == null && getMessage() == null;
    }

    public Object get(Object key) {
      if (EVENT_ID.equals(key)) {
        return getId();
      } else if (EVENT_MESSAGE.equals(key)) {
        return getMessage();
      } else if (EVENT_TYPE.equals(key)) {
        return getType();
      }
      return parent.get(key);
    }

    public boolean containsKey(Object key) {
      if (EVENT_ID.equals(key)) {
        return getId() == null;
      } else if (EVENT_MESSAGE.equals(key)) {
        return getMessage() == null;
      } else if (EVENT_TYPE.equals(key)) {
        return getType() == null;
      }
      return parent.containsKey(key);
    }

    public Object put(String key, Object newValue) {
      Object oldValue = get(key);
      if (EVENT_ID.equals(key)) {
        setId(newValue.toString());
      } else if (EVENT_MESSAGE.equals(key)) {
        setMessage(newValue.toString());
      } else if (EVENT_TYPE.equals(key)) {
        setType(newValue.toString());
      } else {
        parent.put(key, newValue);
      }
      return oldValue;
    }

    public void putAll(Map<? extends String, ?> map) {
      for (Map.Entry<? extends String, ?> entry : map.entrySet()) {
        put(entry.getKey(), entry.getValue());
      }
    }

    public Object remove(Object key) {
      Object oldValue = get(key);
      if (EVENT_ID.equals(key)) {
        setId((StructuredDataId) null);
      } else if (EVENT_MESSAGE.equals(key)) {
        setMessage(null);
      } else if (EVENT_TYPE.equals(key)) {
        setType(null);
      } else {
        parent.remove(key);
      }
      return oldValue;
    }

    public void clear() {
      parent.clear();
      setMessage(null);
    }

    public boolean containsValue(Object obj) {
      return parent.containsValue(obj) || getId().equals(obj.toString()) || getMessage().equals(obj.toString());
    }

    public Set<String> keySet() {
      return new EventKeySet();
    }

    public Collection<Object> values() {
      return new EventValues();
    }

    public Set<Map.Entry<String, Object>> entrySet() {
      return new EventEntrySet();
    }

    private class Entry implements Map.Entry<String, Object> {
      private String key;
      private Object value;

      public Entry(String key, Object value) {
        this.key = key;
        this.value = value;
      }
      public String getKey() {
        return key;
      }

      public Object getValue() {
        return value;
      }

      public Object setValue(Object newValue) {
        Object oldValue = value;
        if (key.equals(EVENT_ID)) {
          setEventId(newValue.toString());
        } else if (key.equals(EVENT_MESSAGE)) {
          setMessage(newValue.toString());
        } else if (key.equals(EVENT_TYPE)) {
          setType(newValue.toString());
        }
        value = newValue;
        return oldValue;
      }
    }

    private abstract class EventIterator<T> implements Iterator<T> {
      protected Iterator<Map.Entry<String, Object>> iterator;
      protected boolean iterId;
      protected boolean iterMessage;
      protected boolean iterType;
      private Map.Entry<String, Object> current = null;

      public EventIterator() {
        iterator = getData().entrySet().iterator();
        iterId = getId() != null;
        iterMessage = getMessage() != null;
        iterType = getType() != null;
      }

      public Map.Entry<String, Object> nextEntry() {
        if (iterId) {
          iterId = false;
          current = new Entry(EVENT_ID, getId());
        } else if (iterMessage) {
          iterMessage = false;
          current = new Entry(EVENT_MESSAGE, getMessage());
        } else if (iterType) {
          iterType = false;
          current = new Entry(EVENT_TYPE, getType());
        } else {
          current = iterator.next();
        }
        return current;
      }

      public boolean hasNext() {
        return iterator.hasNext() || iterId || iterMessage || iterType;
      }

      public void remove() {
        if (current != null) {
          EventMap.this.remove(current.getKey());
        }
      }
    }

    private final class EventKeyIterator extends EventIterator<String> {

      public String next() {
        return nextEntry().getKey();
      }
    }

    private final class EventKeySet extends AbstractSet<String> {
      public Iterator<String> iterator() {
        return new EventKeyIterator();
      }
      public int size() {
        return EventMap.this.size();
      }
      public boolean contains(Object o) {
        return containsKey(o);
      }
      public boolean remove(Object o) {
        return EventMap.this.remove(o) != null;
      }
      public void clear() {
        EventMap.this.clear();
      }
    }

    private final class EventValueIterator extends EventIterator<Object> {

      public Object next() {
        return nextEntry().getValue();
      }
    }

    private final class EventValues extends AbstractCollection<Object> {
      public Iterator<Object> iterator() {
        return new EventValueIterator();
      }
      public int size() {
        return EventMap.this.size();
      }
      public boolean contains(Object o) {
        return containsValue(o);
      }
      public void clear() {
        EventMap.this.clear();
      }
    }

    private final class EventEntrySetIterator extends EventIterator<Map.Entry<String, Object>> {

      public Map.Entry<String, Object> next() {
        return nextEntry();
      }
    }

    private final class EventEntrySet extends AbstractSet<Map.Entry<String, Object>> {
      public Iterator<Map.Entry<String, Object>> iterator() {
        return new EventEntrySetIterator();
      }
      public int size() {
        return EventMap.this.size();
      }
      public boolean contains(Object o) {
        return containsKey(o);
      }
      public boolean remove(Object o) {
        return EventMap.this.remove(o) != null;
      }
      public void clear() {
        EventMap.this.clear();
      }
    }
  }
}