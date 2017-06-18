class Notifications {

  static requestPermission() {
    window.Notification && window.Notification.requestPermission();
  }

  static hasPermission() {
    return window.Notification && (window.Notification.permission === 'granted');
  }

}

export default Notifications;
