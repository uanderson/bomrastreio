class Http {

  static prepare() {
    return new Promise((resolve, reject) => {
      keycloak.updateToken(30)
        .success(resolve)
        .error(reject);
    });
  }
}

export default Http;
