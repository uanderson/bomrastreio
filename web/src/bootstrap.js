// Anonymous imports
import './component/datepicker';
import './component/scroo';

// Imports
import Keycloak from 'keycloak-js';
import Vue from 'vue';

import axios from 'axios';
import app from './app.vue';
import router from './router';

/**
 * Initialize a event based helper.
 */
window.eventBus = new Vue();
window.baseApiUrl = 'https://api.bomrastreio.com.br';

/**
 * Initialize global axios to work with APIs.
 */
window.axios = axios;
window.axios.defaults.baseURL = baseApiUrl;
window.axios.defaults.headers.common = {
  'X-Requested-With': 'XMLHttpRequest'
};

window.axios.interceptors.request.use(configuration => {
  if (keycloak.authenticated) {
    configuration.headers.common['Authorization'] = 'Bearer ' + keycloak.token;
  }

  return configuration;
}, error => {
  return Promise.reject(error)
});

/**
 * Initialize swal globals
 */
swal.setDefaults({
  animation: false,
  closeOnConfirm: false,
  showCancelButton: true,
  cancelButtonText: 'Cancelar',
  confirmButtonColor: '#3598dc',
  showLoaderOnConfirm: true
});

/**
 * Initialize toastr globals
 */
toastr.options = Object.assign(toastr.options, {
  newestOnTop: true,
  preventDuplicates: true,
  positionClass: "toast-top-center",
});

/**
 * Check if the user is logged in and redirect if not.
 */
window.keycloak = Keycloak('configuration/keycloak.json')
window.keycloak.init({
  onLoad: 'login-required',
  responseMode: 'query',
  checkLoginIframe: false
})
.success(authenticated => {
  if (authenticated) {
    keycloak.loadUserProfile()
      .success(() => new Vue({
        el: '#app',
        router: router,
        render: h => h(app)
      }));
  } else {
    window.location.reload();
  }
})
.error(() => window.location.reload());
