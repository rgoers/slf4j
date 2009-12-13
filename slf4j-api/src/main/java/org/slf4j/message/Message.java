package org.slf4j.message;

import java.io.Serializable;

/**
 * An interface for various Message implementations that can be logged.
 */
public interface Message extends Serializable {
  /**
   * Returns the Message formatted as a String.
   * @return The message String.
   */
  String getFormattedMessage();

  /**
   * Returns the format portion of the Message
   * @return
   */
  String getMessageFormat();

  /**
   * Returns parameter values, if any.
   * @return An array of parameter values or null.
   */
  Object[] getParameters();
}
