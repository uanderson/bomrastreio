class EventBus {

  static on(name, callback) {
    eventBus.$on(name, callback);
  }

  static emit(name, content) {
    eventBus.$emit(name, content);
  }

}

export default EventBus;
