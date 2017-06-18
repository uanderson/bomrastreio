<template>
  <modal ref="modal">
    <div slot="title">Cadastro de objeto</div>
    <div slot="body">
      <div class="form-body" @keydown.enter="submit()" @keydown="form.errors.clear($event.target.name)">
        <div class="row">
          <div class="col col-md-7 col-sm-7">
            <div class="form-group" v-bind:class="{'has-error':form.errors.has('code')}">
              <label class="control-label">Código</label>
              <input :disabled="form.id" name="code" type="text" class="form-control" maxlength="13" v-model="form.code">
              <span class="help-block" v-if="form.errors.has('code')" v-text="form.errors.get('code')"></span>
            </div>
          </div>
          <div class="col col-md-5 col-sm-5">
            <div class="form-group" v-bind:class="{'has-error':form.errors.has('eta')}">
              <label class="control-label">Previsão de entrega</label>
              <datepicker name="eta" v-model="form.eta"></datepicker>
              <span class="help-block" v-if="form.errors.has('eta')" v-text="form.errors.get('eta')"></span>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col col-md-12 col-sm-12">
            <div class="form-group" v-bind:class="{'has-error':form.errors.has('addressee')}">
              <label class="control-label">Destinatário</label>
              <input type="text" name="addressee" class="form-control" maxlength="255" v-model="form.addressee">
              <span class="help-block" v-if="form.errors.has('addressee')" v-text="form.errors.get('addressee')"></span>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col col-md-12 col-sm-12">
            <div class="form-group" v-bind:class="{'has-error':form.errors.has('note')}">
              <label class="control-label">Anotação</label>
              <textarea rows="4" name="form.note" class="form-control" maxlength="1000" v-model="form.note"></textarea>
              <span class="help-block" v-if="form.errors.has('note')" v-text="form.errors.get('note')"></span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div slot="footer">
      <button type="button" class="btn default" @click="close()">Fechar</button>
      <button type="button" class="btn green" @click="save()">Salvar objeto</button>
    </div>
  </modal>
</template>

<script>
  import Form from '../component/form';
  import EventBus from '../common/event/bus';
  import Toastr from '../common/toastr';

  export default {
    data() {
      return {
        submitting: false,
        form: new Form({
          id: '',
          code: '',
          eta: '',
          addressee: '',
          note: ''
        })
      };
    },

    mounted() {
      EventBus.on('object:single:open-modal', this.open);
      EventBus.on('object:single:close-modal', this.close);
    },

    components: {
      'modal': require('../component/modal.vue')
    },

    methods: {

      /**
       * Open the modal and initialize data.
       *
       * @param object Object for edit
       */
      open(object) {
        this.form.reset();

        if (object) {
          this.form.id = object.id;
          this.form.code = object.code;
          this.form.eta = object.eta;
          this.form.addressee = object.addressee;
          this.form.note = object.note;
        }

        this.form.eta = new Date();
        this.$refs.modal.open();
      },

      /**
       * Close this modal.
       */
      close() {
        this.$refs.modal.close();
      },

      /**
       * Save object to the server.
       */
      save() {
        let promise;

        if (this.form.id) {
          promise = this.form.put('/v1/objects/' + this.form.code);
        } else {
          promise = this.form.post('/v1/objects');
        }

        promise
          .then(this.handleSaveSuccess)
          .catch(this.handleSaveFailure);
      },

      /**
       * Handle the success result from saving.
       *
       * @param response Server response
       */
      handleSaveSuccess(response) {
        Toastr.success('Objeto salvo com sucesso');
        EventBus.emit('object:single-added', response.data);
        this.close();
      },

      handleSaveFailure(error) {
        let errorData = error.response.data;

        if (errorData.errors) {
          this.errors.record(errorData);
        } else {
          Swal.error('Oops ... ', errorData.message);
        }
      }
    }
  }

</script>
