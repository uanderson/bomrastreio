import ObjectStatus from './status';
import moment from 'moment';

class TrackingObject {

  constructor(object) {
    this.object = object;

    for (let field in object) {
      this[field] = object[field];
    }

    let objectStatus = ObjectStatus.of(this.status);
    this.statusDescription = objectStatus.description;
    this.statusClass = objectStatus.labelClass;

    this.formattedEta = this.eta
      ? moment(this.eta).format('DD/MM/YYYY')
      : 'Não informada';
  }

  /**
   * Create the Correios tracking URL.
   *
   * @returns {string} Full URL
   */
  createTrackingUrl() {
    return 'http://websro.correios.com.br/sro_bin/txect01$.QueryList?P_LINGUA=001&P_TIPO=001&P_COD_UNI=' + this.code;
  }

  /**
   * Create action URL for user get in touch with Correios.
   *
   * @returns {string} Contact URL
   */
  createActionUrl() {
    return 'http://www2.correios.com.br/sistemas/falecomoscorreios/';
  }

  /**
   * CHeck if the object has the status ACTION_REQUIRED.
   *
   * @returns {boolean} true if has, false otherwise
   */
  hasActionRequired() {
    return this.status === 'ACTION_REQUIRED';
  }
}

export default TrackingObject;
