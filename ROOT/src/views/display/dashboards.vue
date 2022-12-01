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
    this.frameSrc = 'https://' + util.cookies.get('hostKibana') + ':15601/app/dashboards#/view/0ad3d7c2-3441-485e-9dfe-dbb22e84e576?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3Anow-1h%2Cto%3Anow))&show-query-input=true&show-time-filter=true'
  },
  methods: {
  }
}
</script>

<style lang="scss">
</style>
