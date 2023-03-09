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
    // this.frameSrc = 'https://' + util.cookies.get('hostGrafana') + '/grafana/?orgId=1&var-interval=$__auto_interval_interval'
    this.frameSrc = 'https://' + util.cookies.get('hostGrafana') + '/dashboards/app/dashboards#/view/4a4bde20-4760-11ea-949c-bbb5a9feecbf'
  },
  methods: {
  }
}
</script>

<style lang="scss">
</style>
