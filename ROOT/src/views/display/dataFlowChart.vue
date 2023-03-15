<template>
  <d2-container-frame :src="frameSrc" />
</template>

<script>
import { mapState } from 'vuex'
import util from '@/libs/util.js'

export default {
  data () {
    return {
      frameSrc: ''
    }
  },
  computed: {
    ...mapState('d2admin', {
      keepAlive: state => state.page.keepAlive
    })
  },
  mounted () {
    this.frameSrc = 'https://' +
      util.cookies.get('hostKibana') +
      '/dashboards/app/dashboards#/view/eec67e20-64ee-11e8-9e8d-39632dc6b766?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3Anow-1h%2Cto%3Anow))&show-query-input=true&show-time-filter=true'
  },
  methods: {
  }
}
</script>

<style lang="scss">
</style>
