class Errors {

  constructor() {
    this.errors = {};
  }

  /**
   * Check if the field is recorded. When the index
   * is informed, try to get the error based on the index. If
   * the index was undefined, assumes zero as default.
   *
   * @param field Field name
   * @param index Field index
   * @returns {boolean} True if founded, false otherwise
   */
  has(field, index) {
    if (this.findErrorBy(index)) {
      return this.findErrorBy(index).hasOwnProperty(field);
    }

    return false;
  }

  /**
   * Get a field error if there is one.
   *
   * @param field Field name
   * @param index Index to get from
   * @returns {*} Field error if founded, empty otherwise
   */
  get(field, index) {
    if (this.findErrorBy(index)[field]) {
      return this.findErrorBy(index)[field];
    }

    return '';
  }

  /**
   * Record the errors in the specific format.
   *
   * @param response Error content
   */
  record(response) {
    this.errors = {};

    if (response.errors) {
      response.errors.forEach(error => {
        let indexName = this.createIndex(error.index);
        this.errors[indexName] = {};

        if (error.fieldErrors) {
          error.fieldErrors.forEach(fieldError => {
            this.errors[indexName][fieldError.field] = fieldError.message;
          });
        }
      });
    }
  }

  /**
   * Clear a specific error when passed. If the
   * index was informed, try to search the error through
   * that index name.
   *
   * @param field Field name
   * @param index Index to be searched
   */
  clear(field, index) {
    let error = this.findErrorBy(index);

    if (field && error) {
      delete error[field];
      return;
    }

    this.errors = {};
  }

  /**
   * Find the error at the passed index.
   *
   * @param index Index to search for
   * @returns {*} The error if founded, undefined otherwise
   */
  findErrorBy(index) {
    return this.errors[this.createIndex(index)];
  }

  /**
   * Create a index name with a prefix.
   *
   * @param index Index to be created
   * @returns {string} Index name with prefix
   */
  createIndex(index) {
    let currentIndex = index ? index : 0;
    return 'I' + currentIndex;
  }
}

export default Errors;
