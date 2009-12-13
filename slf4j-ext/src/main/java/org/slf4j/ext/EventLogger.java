package org.slf4j.ext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.ext.LoggerWrapper;
import org.slf4j.message.Message;
import org.slf4j.message.MessageLogger;
import org.slf4j.message.MessageLoggerFactory;
import org.slf4j.message.MessageLoggerWrapper;
import org.slf4j.spi.LocationAwareLogger;

/**
 * Simple Logger used to log events. All events are directed to a logger named "EventLogger"
 * with a level of INFO and with an Event marker.
 *
 * @author Ralph Goers
 */
public class EventLogger {

  private static final String FQCN = EventLogger.class.getName();

  static Marker EVENT_MARKER = MarkerFactory.getMarker("EVENT");

  private static MessageLogger eventLogger = MessageLoggerFactory.getLogger("EventLogger", FQCN);

  /**
   * There can only be a single EventLogger.
   */
  private EventLogger() {

  }

  /**
   * Logs the event.
   *
   * @param data The EventData.
   */
  public static void logEvent(EventData data) {
    logEvent(data, "XML");
  }

  /**
   * Logs the event.
   * @param data The EventData.
   * @param format the format to use when converting the data to a String in Loggers that do not
   * support structured data.
   * @deprecated Use logEvent(StructuredData data) instead.
   */
  public static void logEvent(EventData data, String format) {
    if (format.equals("XML")) {
      String msg = data.toXML();
      eventLogger.log(EVENT_MARKER, FQCN, MessageLogger.INFO_INT, msg, null);
    } else {
      eventLogger.log(EVENT_MARKER, FQCN, MessageLogger.INFO_INT, data.getEventData(), null);
    }
  }

  /**
   * Logs structured data
   * @param data The StructuredData
   */
  public static void logEvent(Message data) {
    eventLogger.log(EVENT_MARKER, FQCN, MessageLogger.INFO_INT, data, null);
  }
}
