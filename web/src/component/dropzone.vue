<template>
  <form :id="id" :action="url" class="dropzone"></form>
</template>

<script>
  import Dropzone from 'dropzone';

  export default {
    props: {
      id: {
        type: String,
        required: true
      },
      url: {
        type: String,
        required: true
      }
    },

    methods: {
      removeAllFiles() {
        this.dropzone.removeAllFiles(true)
      },

      processQueue() {
        this.dropzone.processQueue()
      }
    },

    data() {
      return {
        options: {
          maxFiles: 4,
          autoProcessQueue: false,
          addRemoveLinks: true,
          dictDefaultMessage: 'Adicione arquivos aqui para serem enviados'
        }
      }
    },

    mounted () {
      Dropzone.autoDiscover = false;
      let element = document.getElementById(this.id);
      this.dropzone = new Dropzone(element, this.options);
    },

    beforeDestroy () {
      this.dropzone.destroy();
    },

  }
</script>

<style>
  @import url('../../node_modules/dropzone/dist/dropzone.css');
</style>
