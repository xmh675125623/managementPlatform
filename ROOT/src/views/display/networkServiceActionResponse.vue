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
      '/dashboards/app/dashboards#/view/a33e0a50-afcd-11ea-993f-b7d8522a8bed?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3Anow-1h%2Cto%3Anow))&show-query-input=true&show-time-filter=true'
  },
  methods: {
  }
}
</script>

<style lang="scss">
</style>
