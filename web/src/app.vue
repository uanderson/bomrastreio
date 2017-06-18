<template>
  <div class="wrapper">
    <header class="page-header">
      <nav class="navbar mega-menu" role="navigation">
        <div class="container-fluid">
          <div class="clearfix navbar-fixed-top">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
              data-target=".navbar-responsive-collapse">
              <span class="sr-only">Abrir navegação</span>
              <span class="toggle-icon">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </span>
            </button>
            <a id="index" class="page-logo" href="//bomrastreio.com.br">
              <img src="/static/img/logo.png" alt="Logo">
            </a>
            <div class="topbar-actions">
              <div v-if="false" class="btn-group-notification btn-group">
                <button type="button" class="btn btn-sm md-skip dropdown-toggle" data-toggle="dropdown"
                  data-hover="dropdown" data-close-others="true" aria-expanded="false">
                  <i class="icon-bell"></i>
                  <span class="badge">{{notifications.length}}</span>
                </button>
                <ul class="dropdown-menu-v2">
                  <li class="external">
                    <h3>
                      <span class="bold">{{notifications.length}} notificação</span> pendente
                    </h3>
                    <a href="javascript:void(0)">ler todas</a>
                  </li>
                  <li>
                    <ul class="dropdown-menu-list notification-panel">
                      <li v-for="notification in notifications">
                        <a href="javascript:void(0);">
                          <span class="details">
                            <span class="label label-sm label-icon label-success md-skip">
                              <i class="fa fa-check"></i>
                            </span> {{notification.message}}
                          </span>
                          <span class="time">{{notification.elapsed}}</span>
                        </a>
                      </li>
                    </ul>
                  </li>
                </ul>
              </div>
              <div class="btn-group-red btn-group">
                <button type="button" class="btn btn-sm md-skip dropdown-toggle" data-toggle="dropdown"
                  data-hover="dropdown" data-close-others="true" aria-expanded="false">
                  <i class="fa fa-plus"></i>
                </button>
                <ul class="dropdown-menu-v2" role="menu">
                  <li><a href="javascript:void(0)" @click="openModal('object:single')">Adicionar objeto</a></li>
                  <li><a href="javascript:void(0)" @click="openModal('object:batch')">Adicionar objetos (lote)</a></li>
                </ul>
              </div>
              <div class="btn-group-img btn-group">
                <button type="button" class="btn btn-sm md-skip dropdown-toggle"
                    data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                  <span>Olá, {{profile.firstName}}</span>
                  <img src="/static/img/no-profile-picture.png" alt="">
                </button>
                <ul class="dropdown-menu-v2" role="menu">
                  <li>
                    <a href="javascript:void(0)" @click="manageAccount()">
                      <i class="icon-user"></i> Profile
                    </a>
                  </li>
                  <li class="divider"></li>
                  <li>
                    <a href="javascript:void(0)" @click="logout()">
                      <i class="icon-key"></i> Sair
                    </a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div class="nav-collapse collapse navbar-collapse navbar-responsive-collapse">
            <ul class="nav navbar-nav">
              <router-link to="/" tag="li" class="dropdown dropdown-fw" exact>
                <a href="/" class="text-uppercase">
                  <i class="icon-home"></i> Dashboard
                </a>
              </router-link>
              <router-link to="/settings" tag="li" class="dropdown dropdown-fw">
                <a href="/settings" class="text-uppercase">
                  <i class="icon-settings"></i> Configurações
                </a>
              </router-link>
            </ul>
          </div>
        </div>
      </nav>
    </header>
    <div class="container-fluid">
      <div class="page-content">
        <router-view></router-view>
      </div>
      <p class="copyright">
        Desenvolvido com <i class="fa fa-heart" style="color: firebrick"></i> pela equipe <a target="_blank"
        href="//bomrastreio.com.br">BomRastreio</a>
      </p>
    </div>

    <!-- Global modal component registration -->
    <object-single-modal></object-single-modal>
    <object-batch-modal></object-batch-modal>
  </div>
</template>

<script>
  import SockJS from 'sockjs-client';
  import Stomp from 'stompjs';
  import EventBus from './common/event/bus';
  import Notifications from './common/notification/notifications';

  export default {
    name: 'app',

    data() {
      return {
        profile: {},
        notifications: []
      }
    },

    created () {
      this.loadProfile();
      this.connectWebSocket();
    },

    components: {
      'object-single-modal': require('./object/single-modal.vue'),
      'object-batch-modal': require('./object/batch-modal.vue')
    },

    methods: {

      /**
       * Load Keycloak profile to be used by ui.
       */
      loadProfile() {
        this.profile = keycloak.profile;
      },

      /**
       * Connect to websocket if permission was granted.
       */
      connectWebSocket() {
        if (!Notifications.hasPermission()) {
          return;
        }

        let socket = new SockJS(baseApiUrl + '/ws-connect');
        this.stompClient = Stomp.over(socket);

        this.stompClient.connect({}, () => {
          let channel = '/user/' + this.profile.username + '/notifications';
          this.stompClient.subscribe(channel, this.handleNotification);
        });
      },

      /**
       * Handle received notification.
       *
       * @param pushNotification Notification sent by the server
       */
      handleNotification(pushNotification) {
        let notification = JSON.parse(pushNotification.body);
        this.notifications.push(notification);
        this.showNotification('Notificação', notification);
      },

      /**
       * Show notification on browser.
       *
       * @param title        Notification title
       * @param notification Notification object
       */
      showNotification(title, notification) {
        let browserNotification = new Notification(title, {
          body: notification.message
        });

        let lowerTypeName = notification.type.toLowerCase();
        EventBus.emit(lowerTypeName + ':notification-received', notification);
        setTimeout(() => browserNotification.close(), 6000);
      },

      /**
       * Open the modal by name.
       *
       * @param modalName Modal name
       */
      openModal(modalName) {
        EventBus.emit(modalName + ':open-modal');
      },

      /**
       * Logout from Keycloak.
       */
      logout() {
        keycloak.logout();
      },

      /**
       * Manage Keycloak account.
       */
      manageAccount() {
        keycloak.accountManagement();
      }
    }
  }
</script>

<style>
  .dropdown-menu-list.notification-panel {
    height: 250px;
    padding: 0;
    overflow: hidden;
    width: auto;
  }

  @media (min-width: 992px) {
    .page-content {
      padding-top: 30px;
    }
  }

  table td.actions {
    width: 1px;
    white-space: nowrap;
  }

  table td.actions a {
    margin-left: 0 !important;
    margin-right: 0 !important;
  }

  .item-divider {
    height: 2px;
    background-color: #f5f5f5;
    margin-bottom: 8px;
  }
</style>
