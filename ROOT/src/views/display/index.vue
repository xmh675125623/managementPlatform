<template>
<!--  <div class="container-component" style="padding: 0px; overflow: hidden;">-->
<!--    <div class="d2-container-full">-->
<!--      <iframe src="https://192.168.0.21:5601/app/dashboards?security_tenant=private#/view/c44e71b0-6225-11ec-9834-4b83413431da?embed=true&_g=(filters:!(),refreshInterval:(pause:!t,value:0),time:(from:now-15m,to:now))&_a=(description:'',filters:!(),fullScreenMode:!f,options:(hidePanelTitles:!f,useMargins:!t),query:(language:kuery,query:''),timeRestore:!f,title:%E9%A6%96%E9%A1%B5,viewMode:view)"-->
<!--              style="width: 100%; height: 100%; border: none">-->
<!--      </iframe>-->
<!--    </div>-->
<!--  </div>-->
<!--  <d2-container style="padding: 0; overflow: hidden;">-->
<!--    <keep-alive :include="keepAlive">-->
<!--      <iframe src="https://192.168.0.21:5601/app/dashboards?security_tenant=private#/view/c44e71b0-6225-11ec-9834-4b83413431da?embed=true&_g=(filters:!(),refreshInterval:(pause:!t,value:0),time:(from:now-15m,to:now))&_a=(description:'',filters:!(),fullScreenMode:!f,options:(hidePanelTitles:!f,useMargins:!t),query:(language:kuery,query:''),timeRestore:!f,title:%E9%A6%96%E9%A1%B5,viewMode:view)"-->
<!--              style="width: 100%; height: 100%">-->
<!--      </iframe>-->
<!--    </keep-alive>-->
<!--  </d2-container>-->
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
    this.frameSrc = 'https://' + util.cookies.get('hostKibana') + ':15601/app/dashboards#/view/c44e71b0-6225-11ec-9834-4b83413431da?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3Anow-1h%2Cto%3Anow))&show-query-input=true&show-time-filter=true'
  },
  methods: {
  }
}
</script>

<style lang="scss">
</style>
