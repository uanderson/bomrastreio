<template>
  <div class="row">
    <div class="col-md-12">
      <div class="portlet light bordered">
        <div class="portlet-title">
          <div class="caption">
            <i class="fa fa-bell-o"></i> Preferências de notificação
          </div>
        </div>
        <div class="portlet-body form">
          <div class="form-body">
            <div class="form-group">
              <label class="mt-checkbox">
                <input type="checkbox" v-model="settings.NOTIFICATION" v-bind:true-value="'true'"
                  v-bind:false-value="'false'" @click="saveSetting('NOTIFICATION')"> Ativar notificações
                <span></span>
              </label>
              <p class="help-block">
                Enviaremos notificações caso essa opção esteja marcada
              </p>
            </div>
            <div v-if="settings.NOTIFICATION === 'true'">
              <div class="item-divider margin-bottom-20"></div>
              <div class="form-group">
                <label class="mt-checkbox">
                  <input type="checkbox" v-model="settings.MAIL_NOTIFICATION" v-bind:true-value="'true'"
                    v-bind:false-value="'false'" @click="saveSetting('MAIL_NOTIFICATION')"> Receber e-mail por evento
                  <span></span>
                </label>
                <p class="help-block">
                  Enviaremos um e-mail toda vez que um evento ocorrer
                </p>
              </div>
              <div class="item-divider margin-bottom-20"></div>
              <div class="form-group">
                <label class="mt-checkbox">
                  <input type="checkbox" v-model="settings.WEB_SOCKET_NOTIFICATION" v-bind:true-value="'true'"
                    v-bind:false-value="'false'" @click="saveSetting('WEB_SOCKET_NOTIFICATION')"> Receber notificação no navegador por evento
                  <span></span>
                </label>
                <p class="help-block">
                  Enviaremos uma notificação para o navegador toda vez que um evento ocorrer
                </p>
                <div v-if="!hasNotificationPermission()" class="alert alert-warning margin-top-15">
                  <strong>Importante: </strong> Seu browser não tem permissão para receber notificações. Mesmo com essa
                  opção selecionada, não será possível notificá-lo. Solicite permissão clicando
                  <a href="javascript:void(0)" @click="requestNotificationPermission()">aqui</a>.
                </div>
              </div>
              <div class="item-divider margin-bottom-20"></div>
              <div class="form-group">
                <label class="mt-checkbox">
                  <input type="checkbox" v-model="settings.DIGEST_MAIL_NOTIFICATION" v-bind:true-value="'true'"
                    v-bind:false-value="'false'" @click="saveSetting('DIGEST_MAIL_NOTIFICATION')"> Receber notificação diária
                  <span></span>
                </label>
                <p class="help-block">
                  Enviaremos uma notificação diária com todas os eventos ocorridos no dia
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Http from '../common/http';
  import Swal from '../common/swal';
  import Toastr from '../common/toastr';
  import Notifications from '../common/notification/notifications';

  export default {
    data() {
      return {
        settings: {
          NOTIFICATION: 'false',
          MAIL_NOTIFICATION: 'false',
          WEB_SOCKET_NOTIFICATION: 'false',
          DIGEST_MAIL_NOTIFICATION: 'false'
        }
      }
    },

    created() {
      this.loadData();
    },

    methods: {

      /**
       * Load initial data.
       */
      loadData() {
        Http.prepare().then(() => {
          axios.get('/v1/settings').then(this.transformData);
        });
      },

      /**
       * Transform the response into the ui object.
       *
       * @param response Server response
       */
      transformData(response) {
        response.data.forEach(setting => {
          this.settings[setting.key] = setting.value
        });
      },

      /**
       * Check if the user has notification permission.
       *
       * @returns true if has, false otherwise
       */
      hasNotificationPermission() {
        return Notifications.hasPermission();
      },

      /**
       * Request permission to receive notification.
       */
      requestNotificationPermission() {
        Notifications.requestPermission();
      },

      /**
       * Save the current setting to server.
       *
       * @param settingName Setting name
       */
      saveSetting(settingName) {
        let data = {
          value: this.settings[settingName]
        };

        Http.prepare().then(() => {
          axios.put('/v1/settings/' + settingName, data)
            .then(() => {
              Toastr.success('Configuração salva com sucesso');
            });
        });
      }
    }
  }
</script>
