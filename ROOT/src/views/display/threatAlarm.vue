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
      '/dashboards#/view/076caa20-64aa-11e8-9e8d-39632dc6b766'
  },
  methods: {
  }
}
</script>

<style lang="scss">
</style>
