package br.com.bomrastreio.api.notification;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a notification to send to the user.
 */
public class Notification {

  private NotificationType type;
  private String message;

  /**
   * Creates a new notification with default type {@link NotificationType#GLOBAL}.
   *
   * @param message Message to be notified
   */
  public Notification(String message) {
    this(NotificationType.GLOBAL, message);
  }

  /**
   * Creates a new notification the custom type and message.
   *
   * @param type    Message type to be notified
   * @param message Message to be notified
   */
  public Notification(NotificationType type, String message) {
    this.type = checkNotNull(type);
    this.message = checkNotNull(message);
  }

  public NotificationType getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }

}
