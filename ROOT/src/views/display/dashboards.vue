<template>
  <div class="container-component" style="padding: 0px; overflow: hidden;">
    <div class="d2-container-full">
      <iframe :src="frameSrc"
              style="width: 100%; height: 100%; border: none">
      </iframe>
    </div>
  </div>
</template>

<script>
import util from '@/libs/util.js'

export default {
  data () {
    return {
      frameSrc: ''
    }
  },
  mounted () {
    this.frameSrc =
      'https://' +
      util.cookies.get('hostKibana') +
      '/dashboards/app/dashboards#/view/0ad3d7c2-3441-485e-9dfe-dbb22e84e576'
  },
  methods: {
  }
}
</script>

<style lang="scss">
</style>
