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
      '/dashboards/app/dashboards#/view/a33e0a50-afcd-11ea-993f-b7d8522a8bed'
  },
  methods: {
  }
}
</script>

<style lang="scss">
</style>
