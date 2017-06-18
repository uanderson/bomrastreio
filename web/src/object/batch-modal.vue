<template>
  <modal ref="modal">
    <div slot="title">Cadastro de objetos (lote)</div>
    <div slot="body">
      <div class="form-body" @keydown="errors.clear($event.target.name, $event.target.dataset.index)">
        <div v-for="(item, index) in items">
          <div class="row">
            <div class="col col-xs-12 col-sm-6 col-md-3">
              <div class="form-group" v-bind:class="{'has-error':errors.has('code', index)}">
                <label class="control-label" :class="{'visible-xs visible-sm':index > 0}">Código</label>
                <input :data-index="index" name="code" type="text" class="form-control" maxlength="13" v-model="item.code">
                <span class="help-block" v-if="errors.has('code', index)" v-text="errors.get('code', index)"></span>
              </div>
            </div>
            <div class="col col-xs-12 col-sm-6 col-md-3">
              <div class="form-group" v-bind:class="{'has-error':errors.has('eta', index)}">
                <label class="control-label" :class="{'visible-xs visible-sm':index > 0}">Previsão de entrega</label>
                <datepicker :data-index="index" name="eta" v-model="item.eta"></datepicker>
                <span class="help-block" v-if="errors.has('eta', index)" v-text="errors.get('eta', index)"></span>
              </div>
            </div>
            <div class="col col-sm-12 col-md-5">
              <div class="form-group" v-bind:class="{'has-error':errors.has('addressee', index)}">
                <label class="control-label" :class="{'visible-xs visible-sm':index > 0}">Destinatário</label>
                <input :data-index="index" type="text" name="addressee" class="form-control" maxlength="255" v-model="item.addressee">
                <span class="help-block" v-if="errors.has('addressee', index)" v-text="errors.get('addressee', index)"></span>
              </div>
            </div>
            <div class="col col-sm-12 col-md-1">
              <div class="form-group">
                <label class="control-label visible-md visible-lg" v-if="index === 0">&nbsp;</label>
                <button type="button" class="btn red btn-block" :disabled="hasItems(1)" @click="removeLine(index)" tabindex="-1">
                  <i class="fa fa-trash-o"></i>
                  <span class="hidden-md hidden-lg">Excluir linha</span>
                </button>
              </div>
            </div>
          </div>
          <div class="item-divider hidden-md hidden-lg"></div>
        </div>

        <div class="row">
          <div class="col col-md-12">
            <button class="btn btn-block default" @click="addLine()">
              <i class="fa fa-plus"></i> Adicionar nova linha
            </button>
          </div>
        </div>
      </div>
    </div>
    <div slot="footer">
      <button type="button" class="btn default" @click="close()">Fechar</button>
      <button type="button" class="btn green" @click="save()">Salvar objetos</button>
    </div>
  </modal>
</template>

<script>
  import Errors from '../component/errors';
  import Form from '../component/form';
  import Http from '../common/http';
  import EventBus from '../common/event/bus';
  import Swal from '../common/swal';
  import Toastr from '../common/toastr';

  export default {
    data() {
      return {
        items: [],
        errors: new Errors()
      };
    },

    mounted() {
      EventBus.on('object:batch:open-modal', this.open);
      EventBus.on('object:batch:close-modal', this.close);
    },

    components: {
      'modal': require('../component/modal.vue')
    },

    methods: {

      /**
       * Open this modal.
       */
      open() {
        this.clear();
        this.addLine();
        this.$refs.modal.open({
          modalClass: 'modal-lg'
        });
      },

      /**
       * Close this modal.
       */
      close() {
        this.$refs.modal.close();
      },

      /**
       * Add a empty line to the items array.
       */
      addLine() {
        this.items.push({
          code: '',
          eta: '',
          addressee: ''
        });
      },

      /**
       * Remove the line in the passed index.
       *
       * @param index Line index to be removed
       */
      removeLine(index) {
        this.items.splice(index, 1);
        this.errors.clear();
      },

      /**
       * Reset all this data.
       */
      clear() {
        this.items = [];
        this.errors.clear();
      },

      /**
       * Check if has the exact amount of items.
       *
       * @param count Total items to check on
       * @returns {boolean} true if has the same amount, false otherwise
       */
      hasItems(count) {
        return this.items.length === count
      },

      /**
       *
       */
      save() {
        Http.prepare().then(() => {
          axios.post("/v1/objects/batches", this.items)
            .then(this.handleSaveSuccess)
            .catch(this.handleSaveFailure);
        });
      },

      /**
       * Handle success result of saving.
       *
       * @param response Server response
       */
      handleSaveSuccess(response) {
        Toastr.success('Objetos salvos com sucesso');
        EventBus.emit('object:batch-added', response.data);
        this.close();
      },

      /**
       * Handle failure result of saving.
       *
       * @param error Server error
       */
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
