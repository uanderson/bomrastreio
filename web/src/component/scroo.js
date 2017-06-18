import Vue from 'vue';
import EventBus from '../common/event/bus';

import Http from '../common/http';

Vue.component('scroo', {
  props: {
    endpoint: {
      required: true
    }
  },
  template: `
    <div v-if="loadable" class="row">
      <div class="col col-12-md text-center">
        <button :disabled="loading" type="button" class="btn default" @click="load()">
          <i v-if="loading" class="fa fa-circle-o-notch fa-spin"></i>
          <slot>Carregar mais</slot>
        </button>
      </div>
    </div>
  `,

  data() {
    return {
      page: 1,
      size: 20,
      loading: false,
      loadable: true
    }
  },

  methods: {
    load() {
      let options = {
        params: {
          page: this.page++,
          size: this.size
        }
      };

      Http.prepare().then(() => {
        this.loading = true;

        axios.get(this.endpoint, options)
          .then(this.handleSuccessLoad);
      });

    },

    handleSuccessLoad(response) {
      let dataLength = response.data.length;

      if (dataLength === 0) {
        this.loadable = false;
      }

      this.$nextTick(() => {
        this.loading = false;
        this.$emit('loaded', response.data);

        if (dataLength > 0) {
          window.scrollTo(0, document.body.scrollHeight);
        }
      })
    },

    reset() {
      this.loading = false;
      this.loadable = true;
    }
  }
});
