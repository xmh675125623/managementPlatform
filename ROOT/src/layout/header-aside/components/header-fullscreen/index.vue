<template>
  <div>
    <div>
    </div>
    <el-tooltip effect="dark" :content="active ? '退出全屏' : '全屏'" placement="bottom">
      <el-button class="d2-mr btn-text can-hover" type="text" @click="clickFun">
        <d2-icon  name="arrows-alt" style="font-size: 16px" />
      </el-button>
    </el-tooltip>
  </div>

</template>

<script>
// import screenFull from 'screenfull';
export default {
  data () {
    return {
      isFullFlag: false
    }
  },
  methods: {
    clickFun () {
      this.isFullFlag = !this.isFullFlag
      const element = document.getElementById('content')
      element.requestFullscreen()
    }

  }
}
</script>

