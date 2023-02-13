<template>
  <d2-container-frame :src="frameSrc"/>
</template>

<script>
import util from '@/libs/util'

export default {
  data () {
    return {
      frameSrc: ''
    }
  },
  mounted () {
    this.frameSrc = 'https://' + util.cookies.get('hostGrafana') + '/grafana/?orgId=1&var-interval=$__auto_interval_interval'
  },
  methods: {
  }
}
</script>

<style lang="scss">
</style>
