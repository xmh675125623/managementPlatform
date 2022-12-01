<template>
  <d2-container>
<!--    <template slot="header">防火墙设备探索</template>-->

<!--    <el-form :inline="true" :model="sniffFormData" class="demo-form-inline" :rules="rules" ref="sniffForm">-->
<!--      <el-form-item label="起始IP地址" prop="searchips">-->
<!--        <el-input v-model="sniffFormData.searchips" placeholder="请输入起始IP地址"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="结束IP地址" prop="searchipf">-->
<!--        <el-input v-model="sniffFormData.searchipf" placeholder="请输入结束IP地址"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" @click="handleSniff" :loading="listLoading">开始探索</el-button>-->
<!--      </el-form-item>-->
<!--      <el-form-item v-if="selectedDevices.length > 0">-->
<!--        <el-button type="primary" @click="handleAdd" :loading="addLoading">添加到设备管理</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->

    <!--探索设备列表-->
<!--    <el-table :data="deviceList" stripe border tyle="width: 100%" v-loading="listLoading" @selection-change="handleSelectionChange">-->
<!--      <el-table-column type="selection" width="55"></el-table-column>-->
<!--      <el-table-column prop="device_name" label="设备名称"/>-->
<!--      <el-table-column prop="ip_address" label="IP地址"/>-->
<!--      <el-table-column prop="edition" label="版本号"/>-->
<!--      <el-table-column prop="modal" label="型号"/>-->
<!--    </el-table>-->

<!--    <iframe src="http://192.168.0.43:5601/app/dashboards#/view/0ad3d7c2-3441-485e-9dfe-dbb22e84e576?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3Anow-15m%2Cto%3Anow))&show-top-menu=true&show-query-input=true&show-time-filter=true" height="100%" width="100%"></iframe>-->
    <iframe src="http://192.168.0.43:3000/d-solo/5OUqTG5nk/pfsense-system-dashboard?orgId=1&refresh=10s&from=1636526289216&to=1636612689216&panelId=6" width="450" height="200" frameborder="0"></iframe>
  </d2-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import { ipv4Check } from '@/libs/checkData'
import { Message } from 'element-ui'

const rules = {
  searchips: [
    { required: true, message: '请输入起始IP地址', trigger: 'blur' },
    { validator: (rule, value, callback) => ipv4Check('起始IP地址', value, callback), trigger: 'blur' }
  ],
  searchipf: [
    { required: true, message: '请输入结束IP地址', trigger: 'blur' },
    { validator: (rule, value, callback) => ipv4Check('结束IP地址', value, callback), trigger: 'blur' }
  ]
}

export default {
  computed: {
    ...mapState('firewall/deviceSniff', ['deviceList', 'addDevice', 'listLoading', 'addLoading'])
  },
  data () {
    return {
      sniffFormData: {},
      rules: rules,
      selectedDevices: []
    }
  },
  methods: {
    ...mapActions('firewall/deviceSniff', ['sniffDevice', 'addDevice']),
    handleSelectionChange (val) {
      this.selectedDevices = val
    },
    handleSniff () {
      this.$refs.sniffForm.validate((valid) => {
        if (!valid) {
          return
        }

        const ipslen = this.sniffFormData.searchips.lastIndexOf('.')
        const ipflen = this.sniffFormData.searchipf.lastIndexOf('.')

        const ipstmp = this.sniffFormData.searchips.substring(0, ipslen + 1)
        const ipftmp = this.sniffFormData.searchipf.substring(0, ipflen + 1)

        const ipsstmp = this.sniffFormData.searchips.substring(ipslen + 1, this.sniffFormData.searchips.length)
        const ipfftmp = this.sniffFormData.searchipf.substring(ipflen + 1, this.sniffFormData.searchipf.length)

        if (ipstmp !== ipftmp) {
          Message({
            message: '请输入同网段的起始结束地址！',
            type: 'error',
            duration: 5 * 1000
          })
          return
        }

        if (ipsstmp * 1 > ipfftmp * 1) {
          Message({
            message: '请保证输入的结束地址大于起始地址！',
            type: 'error',
            duration: 5 * 1000
          })
        }

        const data = {
          ipss: ipstmp,
          ips: ipsstmp,
          ipf: ipfftmp,
          successNotice: 1
        }
        this.sniffDevice(data)
      })
    },
    handleAdd () {
      let deviceNames = ''
      this.selectedDevices.forEach((item, index) => {
        if (index > 0) {
          deviceNames += ','
        }
        deviceNames += item.device_name
      })

      const data = {
        deviceNames: deviceNames,
        successNotice: 1
      }

      this.addDevice(data)
    }
  }
}
</script>

<style lang="scss">
</style>
