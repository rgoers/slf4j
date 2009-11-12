package org.slf4j.ext;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.StructuredData;
import org.slf4j.spi.LocationAwareLogger;
import org.slf4j.spi.XLocationAwareLogger;

/**
 * Simple Logger used to log events. All events are directed to a logger named "EventLogger"
 * with a level of INFO and with an Event marker.
 *
 * @author Ralph Goers
 */
public class EventLogger {

  private static final String FQCN = EventLogger.class.getName();

  static Marker EVENT_MARKER = MarkerFactory.getMarker("EVENT");

  private static LoggerWrapper eventLogger =
      new LoggerWrapper(LoggerFactory.getLogger("EventLogger"), FQCN);

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
    if (eventLogger.instanceofXLAL) {
      ((XLocationAwareLogger) eventLogger.logger).log(EVENT_MARKER, FQCN,
          XLocationAwareLogger.INFO_INT, data.getEventData(), format, null);
    } else if (eventLogger.instanceofLAL) {
      ((LocationAwareLogger) eventLogger.logger).log(EVENT_MARKER, FQCN,
          LocationAwareLogger.INFO_INT, data.toXML(), null);
    } else {
      eventLogger.logger.info(EVENT_MARKER, data.toXML(), data);
    }
  }

  /**
   * Logs structured data
   * @param data The StructuredData
   */
  public static void logEvent(StructuredData data) {
    final String format = "full";
     if (eventLogger.instanceofXLAL) {
      ((XLocationAwareLogger) eventLogger.logger).log(EVENT_MARKER, FQCN,
          XLocationAwareLogger.INFO_INT, data, format, null);
    } else if (eventLogger.instanceofLAL) {
      ((LocationAwareLogger) eventLogger.logger).log(EVENT_MARKER, FQCN,
          LocationAwareLogger.INFO_INT, data.asString(format), null);
    } else {
      eventLogger.logger.info(EVENT_MARKER, data.asString(format), data);
    }
  }
}
