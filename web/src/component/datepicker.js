import moment from 'moment';
import Vue from "vue";

Vue.component('datepicker', {
  props: ['name', 'value'],
  template: '<input :name="name" type="text" class="form-control">',

  mounted() {
    let self = this;
    let configuration = {
      format: 'dd/mm/yyyy',
      language: 'pt-BR',
      autoclose: true
    };

    $(this.$el).datepicker(configuration)
      .on('clearDate', () => {
        self.$emit('input', undefined);
      })
      .on('changeDate', event => {
        if (event.date) {
          self.$emit('input', event.date);
        }
      });
  },

  watch: {
    value: function (value) {
      let convertedDate = moment(value);

      if (convertedDate.isValid()) {
        $(this.$el).datepicker('update', convertedDate.toDate());
      }
    }
  }
});
