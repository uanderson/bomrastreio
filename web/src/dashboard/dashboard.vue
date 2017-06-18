<template>
  <div class="router-view-content">
    <div class="breadcrumbs">
      <h1>Rastreamento</h1>
      <ol class="breadcrumb">
        <router-link to="/" tag="li">
          <a href="/" class="text-uppercase">
            <i class="icon-home"></i> Home
          </a>
        </router-link>
        <li class="active">Dashboard</li>
      </ol>
    </div>

    <div v-if="!loading">
      <div v-if="!hasObjects()" class="row">
        <div class="col col-xs-12">
          <div class="note note-success">
            <h4 class="block">Não encontramos nenhum objeto </h4>
            <p>
              <b><a href="javascript:void(0)" class="alert-link" @click="openModal('object:single')" >Clique aqui</a></b> e
              cadastre o seu primeiro objeto para rastrearmos ele para você
            </p>
          </div>
        </div>
      </div>

      <div v-if="hasObjects()">
        <div class="row">
          <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
            <div class="dashboard-stat2 bordered">
              <div class="display">
                <div class="number">
                  <h3 class="font-green-sharp">
                    <span data-counter="counterup"
                      :data-value="dashboard.deliveredCount">{{dashboard.deliveredCount}}</span>
                  </h3>
                  <small>Total de entregas</small>
                </div>
                <div class="icon">
                  <i class="icon-like"></i>
                </div>
              </div>
              <div class="progress-info">
                <div class="progress">
                  <span :style="{width: dashboard.deliveredPercentage + '%'}"
                    class="progress-bar progress-bar-success green-sharp">
                    <span class="sr-only">{{dashboard.deliveredPercentage}}%</span>
                  </span>
                </div>
                <div class="status">
                  <div class="status-title">progresso</div>
                  <div class="status-number"> {{dashboard.deliveredPercentage}}%</div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
            <div class="dashboard-stat2 bordered">
              <div class="display">
                <div class="number">
                  <h3 class="font-yellow-haze">
                    <span data-counter="counterup"
                      :data-value="dashboard.delayedCount">{{dashboard.delayedCount}}</span>
                  </h3>
                  <small>Entregas com atraso</small>
                </div>
                <div class="icon">
                  <i class="icon-pie-chart"></i>
                </div>
              </div>
              <div class="progress-info">
                <div class="progress">
                  <span :style="{width: dashboard.delayedPercentage + '%'}"
                    class="progress-bar progress-bar-success yellow-haze">
                    <span class="sr-only">{{dashboard.delayedPercentage}}%</span>
                  </span>
                </div>
                <div class="status">
                  <div class="status-title">total</div>
                  <div class="status-number"> {{dashboard.delayedPercentage}}%</div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
            <div class="dashboard-stat2 bordered">
              <div class="display">
                <div class="number">
                  <h3 class="font-blue-sharp">
                    <span data-counter="counterup" :data-value="dashboard.lossCount">{{dashboard.lossCount}}</span>
                  </h3>
                  <small>Extravios</small>
                </div>
                <div class="icon">
                  <i class="icon-pie-chart"></i>
                </div>
              </div>
              <div class="progress-info">
                <div class="progress">
                  <span :style="{width: dashboard.lossPercentage + '%'}"
                    class="progress-bar progress-bar-success blue-sharp">
                    <span class="sr-only">{{dashboard.lossPercentage}}%</span>
                  </span>
                </div>
                <div class="status">
                  <div class="status-title">total</div>
                  <div class="status-number"> {{dashboard.lossPercentage}}%</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Table -->
        <div class="row">
          <div class="col-md-12">
            <div class="portlet light bordered">
              <div class="portlet-title">
                <div class="caption">
                  <i class="fa fa-truck"></i>Objetos
                </div>
                <div class="actions">
                  <a href="javascript:void(0);" @click="openModal('object:single')" class="btn btn-sm blue">
                    <i class="fa fa-plus"></i> Adicionar
                  </a>
                  <a href="javascript:void(0);" @click="openModal('object:batch')" class="btn btn-sm default">
                    <i class="fa fa-plus"></i> Adicionar em lote
                  </a>
                </div>
              </div>
              <div class="portlet-body">
                <div class="table-responsive">
                  <table class="table">
                    <thead>
                    <tr>
                      <th>Código</th>
                      <th class="hidden-xs">Destinatário</th>
                      <th class="hidden-xs">Previsão de entrega</th>
                      <th>
                        <a @click="actionFilterStatus()">  <i class="fa fa-filter"></i></a>
                        Status

                      <div
                        v-bind:class="{'popover fade right in':!filterStatus, 'popover fade right in show-popover':filterStatus}"
                        role="tooltip">

                        <h3 class="popover-title">Filtro de Status</h3>

                        <div class="popover-content">
                          <select style="width: 160px;" multiple v-model="selected" @click="reloadTable()">
                            <option :class="['label', option.labelClass]" style="display: block; text-align: left;"
                                    v-for="option in options"
                                    v-bind:value="option.id">
                              <span>{{option.description}}</span>
                            </option>
                          </select>
                        </div>
                      </div>
                    </th>
                      <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(object, index) in objects">
                      <td>
                        <a :href="object.createTrackingUrl()" target="_blank">
                          {{object.code}}
                        </a>
                      </td>
                      <td class="hidden-xs">{{object.addressee}}</td>
                      <td class="hidden-xs">{{object.formattedEta}}</td>
                      <td>
                        <span :class="['label', object.statusClass]">{{object.statusDescription}}</span>
                      </td>
                      <td class="actions">
                        <a v-if="object.hasActionRequired()" :href="object.createActionUrl()"
                          class="btn btn-xs default" title="Executar ação" target="_blank">
                          <i class="fa fa-arrow-circle-right"></i>
                        </a>
                        <a href="javascript:void(0);" class="btn btn-xs default" title="Editar"
                            @click="editOne(object, index)">
                          <i class="fa fa-edit"></i>
                        </a>
                        <a href="javascript:void(0);" class="btn btn-xs red" title="Excluir"
                            @click="deleteOne(object, index)">
                          <i class="fa fa-trash-o"></i>
                        </a>
                      </td>
                    </tr>
                    </tbody>
                  </table>
                </div>
              </div>

              <!-- More data pagination -->
              <scroo ref="scroo" endpoint="/v1/objects" @loaded="handleObjects">Carregar mais objetos</scroo>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>

  .show-popover {
    display: block;
    left: auto;
  }

</style>

<script>
  import Http from '../common/http';
  import Swal from '../common/swal';
  import Toastr from '../common/toastr';
  import EventBus from '../common/event/bus';
  import TrackingObject from '../object/tracking-object';
  import ObjectStatus from '../object/status';

  export default {
    data() {
      return {
        loading: true,
        filterStatus: false,
        dashboard: {},
        objects: [],
        allObjects: [],
        selected: [],
        options: [],
      }
    },

    mounted() {
      this.loadData();
      this.registerEvents();
      this.options = ObjectStatus.STATUSES;
    },

    methods: {

      /**
       * Start loading something.
       */
      startLoading() {
        this.loading = true;
      },

      /**
       * Stop loading anything.
       */
      stopLoading() {
        this.loading = false;
      },

      actionFilterStatus(){
        this.filterStatus = !this.filterStatus;
      },

      /**
       * Load initial data.
       */
      loadData() {
        Http.prepare().then(() => {
          axios.all([
            axios.get('/v1/dashboards/DEFAULT'),
            axios.get('/v1/objects')
          ])
          .then(axios.spread((dashboardResponse, objectsResponse) => {
            this.assignDashboard(dashboardResponse.data);
            this.assignObjects(objectsResponse.data);
            this.stopLoading();
            this.resetScroo();
          }));
        });
      },

      /**
       * Register events used by this page.
       */
      registerEvents() {
        EventBus.on('object:single-added', this.loadData);
        EventBus.on('object:batch-added', this.loadData);
        EventBus.on('object:notification-received', this.loadData);
      },

      /**
       * Assign the response to the ui.
       */
      assignDashboard(dashboard) {
        this.dashboard = dashboard;
      },

      /**
       * Transform and assign the objects to the ui.
       */
      assignObjects(objects) {
        this.allObjects=[];
        this.handleObjects(objects);
      },

      /**
       * Process the result from scroo.
       *
       * @param objects New objects
       */
      handleObjects(objects) {
        for (let object of objects) {
          this.allObjects.push(new TrackingObject(object));
        }

        this.reloadTable();

      },

      /**
       * Return true or false if the ui has objects.
       */
      hasObjects() {
        return this.allObjects.length > 0;
      },

      /**
       * Reset the scroo component.
       */
      resetScroo() {
        if (this.$refs.scroo) {
          this.$refs.scroo.reset();
        }
      },

      /**
       * Open the modal by name.
       *
       * @param modalName Modal name
       * @param object    Object case was editing
       */
      openModal(modalName, object) {
        EventBus.emit(modalName + ':open-modal', object);
      },

      /**
       * Edit the object passed.
       *
       * @param object Object to be edited
       */
      editOne(object) {
        this.openModal('object:single', object);
      },

      /**
       * Delete the object passed after confirmation.
       *
       * @param object Object to be deleted
       */
      deleteOne(object) {
        Swal.confirm('Você confirma a exclusão?', 'O objeto \'' + object.code + '\' será excluido permanentemente')
          .then(() => this.deleteRemoteOne(object))
      },

      /**
       * Delete the object in the server.
       *
       * @param object Object to be deleted
       */
      deleteRemoteOne(object) {
        Http.prepare().then(() => {
          axios.delete('/v1/objects/' + object.code)
            .then(() => {
              Swal.close();
              Toastr.success('Objeto \'' + object.code + '\' excluido permanentemente');
              this.loadData();
            })
            .catch(error => {
              Swal.error('Oops .. ', error.response.data.message);
            });
        });
      },

      reloadTable() {
        this.objects=[];
        this.objects = this.allObjects;

        if (this.canNotFilter())
          return;

        this.objects = this.objects.filter(object => {
          return this.selected.filter(selec => {
              if (selec == object.status)
                return true;
            }).length != 0;
        });

      },

      canNotFilter: function () {
        return this.selected == undefined || (this.selected.length == 1 && this.selected[0] == '') || this.selected.length == 0;
      }
    }
  }
</script>
