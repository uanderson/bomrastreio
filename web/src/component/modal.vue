<template>
  <div ref="modal" class="modal fade background-darken" tabindex="-1" role="dialog"
      :class="{in:opened, show:showed}" @click.self="close()" @keyup.esc="close()">
    <div class="modal-dialog" :class="[options.modalClass]" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="close()">
            <span aria-hidden="true">&times;</span>
          </button>
          <h4 class="modal-title">
            <slot name="title"></slot>
          </h4>
        </div>
        <div class="modal-body">
          <slot name="body"></slot>
        </div>
        <div class="modal-footer">
          <slot name="footer"></slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        opened: false,
        showed: false,
        options: {
          modalClass: 'modal-md'
        }
      }
    },

    methods: {
      /**
       * Open the modal instance.
       *
       * @param options Options to open the modal
       */
      open(options) {
        if (options) {
          this.options = Object.assign(this.options, options);
        }

        this.showed = true;
        this.$nextTick(function () {
          this.opened = true;
          this.$refs.modal.focus();
        });
      },

      /**
       * Close the modal instance.
       */
      close() {
        this.opened = false;
        this.$nextTick(function () {
          setTimeout(() => {
            this.showed = false;
          }, 500);
        });
      }
    }
  }
</script>

<style>
  .background-darken {
    background: rgba(0, 0, 0, 0.5);
  }
</style>
