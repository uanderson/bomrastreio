import Errors from './errors';
import Http from '../common/http';
import Swal from '../common/swal';

class Form {

  constructor(data) {
    this.originalData = data;
    this.errors = new Errors();

    for (let field in data) {
      this[field] = data[field];
    }
  }

  reset() {
    for (let field in this.originalData) {
      this[field] = undefined;
    }

    this.errors.clear();
  }

  data() {
    let data = {};

    for (let property in this.originalData) {
      data[property] = this[property];
    }

    return data;
  }

  post(url) {
    return this.submit('post', url);
  }

  put(url) {
    return this.submit('put', url);
  }

  submit(requestType, url) {
    return new Promise((resolve, reject) => {
      Http.prepare().then(() => {
        axios[requestType](url, this.data())
          .then(response => {
            this.onSuccess(response.data);
            resolve(response.data);
          })
          .catch(error => {
            this.onFail(error.response.data);
            reject(error.response.data);
          });
      });
    });
  }

  onSuccess(data) {
    this.reset();
  }

  onFail(response) {
    if (response.errors) {
      this.errors.record(response);
    } else if (response.message) {
      Swal.error('Oops ..', response.message);
    }
  }
}

export default Form;
