class Swal {

  static confirm(title, text, extras) {
    return new Promise((resolve, reject) => {
      let options = {
        title: title,
        text: text,
        type: "warning"
      };

      swal(Swal.mergeOptions(options, extras),
        confirmed => {
          if (confirmed) {
            resolve();
          } else {
            reject();
          }
        });
    });
  }

  static success(title, text, extras) {
    let options = {
      title: title,
      text: text,
      type: 'success',
      timer: 2000,
      showCancelButton: false,
      showConfirmButton: false
    };

    swal(Swal.mergeOptions(options, extras));
  }

  static close() {
    swal.close();
  }

  static error(title, text, extras) {
    let options = {
      title: title,
      text: text,
      type: 'error',
      showCancelButton: false,
      showConfirmButton: true
    };

    swal(Swal.mergeOptions(options, extras));
  }

  static mergeOptions(options, extras) {
    let merged = options;

    if (extras) {
      merged = Object.assign(options, extras);
    }

    if (merged.showConfirmButton) {
      merged.timer = null;
    }

    return merged;
  }
}

export default Swal;
