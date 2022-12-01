<template>
  <d2-container>
    <template slot="header">设备管理</template>

    <el-row style="padding-bottom: 12px">
      <el-button type="primary" icon="el-icon-plus" @click="handleAddDeviceClick">设备添加</el-button>
    </el-row>

    <!--设备列表-->
    <el-table :data="deviceList" stripe border tyle="width: 100%" v-loading="listLoading">
      <el-table-column prop="id" label="ID" width="100px"/>
      <el-table-column label="设备类型">
        <template slot-scope="scope">
          {{scope.row.type === 1 ? '防火墙':''}}
          {{scope.row.type === 2 ? '审计':''}}
          {{scope.row.type === 3 ? '网关':''}}
        </template>
      </el-table-column>
      <el-table-column prop="device_name" label="设备名称"/>
      <el-table-column prop="ip_address" label="IP地址"/>
      <el-table-column label="操作" width="320px">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleManage(scope.row)">管理</el-button>
          <el-button size="mini" @click="handleInfoModalVisible(scope.row)">编辑</el-button>
          &nbsp;
          <el-popconfirm title="确定移除吗？" @confirm="handleRemove(scope.row)">
            <el-button size="mini" slot="reference" type="danger" :loading="updateLoading">移除</el-button>
          </el-popconfirm>
          &nbsp;
<!--          <el-dropdown @command="commond => handleCommand(commond, scope.row)">-->
<!--            <el-button size="mini" type="primary">-->
<!--              更多<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
<!--            </el-button>-->
<!--            <el-dropdown-menu slot="dropdown">-->
<!--              <el-dropdown-item command="rule">默认规则配置</el-dropdown-item>-->
<!--              <el-dropdown-item command="snmp">SNMP配置</el-dropdown-item>-->
<!--              <el-dropdown-item command="syslog">Syslog配置</el-dropdown-item>-->
<!--              <el-dropdown-item command="time">时间同步</el-dropdown-item>-->
<!--            </el-dropdown-menu>-->
<!--          </el-dropdown>-->
        </template>
      </el-table-column>
    </el-table>

    <!--分页器-->
    <div style="overflow: hidden;padding-top: 12px">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="deviceCount"
        style="float: right">
      </el-pagination>
    </div>

    <!--基础信息修改表单弹窗-->
    <el-dialog title="设备信息" :visible.sync="infoFormVisible" width="500px" :close-on-click-modal="false">

      <el-form :model="formData" label-width="100px" ref="infoForm" :rules="infoFormRules">
        <el-form-item label="设备类型：" prop="type">
          <el-select v-model="formData.type" placeholder="请选择设备类型" style="width: 100%">
            <el-option key="1" label="防火墙" :value="1"></el-option>
            <el-option key="2" label="审计" :value="2"></el-option>
            <el-option key="3" label="网关" :value="3"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="设备名称：" prop="device_name">
          <el-input v-model="formData.device_name" placeholder="请输入设备名称"></el-input>
        </el-form-item>

        <el-form-item label="IP地址：" prop="ip_address">
          <el-input v-model="formData.ip_address" placeholder="请输入IP地址"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="infoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmitInfoForm" :loading="updateLoading">确 定</el-button>
      </div>
    </el-dialog>

  </d2-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import { ipv4Check } from '@/libs/checkData'

const infoFormRules = {
  type: [
    { required: true, message: '请选择设备类型', trigger: 'blur' }
  ],
  device_name: [
    { required: true, message: '请输入设备名称', trigger: 'blur' },
    { max: 20, message: '长度不可超过20', trigger: 'blur' }
  ],
  ip_address: [
    { required: true, message: '请输入IP地址', trigger: 'blur' },
    { validator: (rule, value, callback) => ipv4Check('IP地址', value, callback), trigger: 'blur' }
  ]
}

export default {
  computed: {
    ...mapState('plat/deviceList', ['deviceList', 'listLoading', 'updateLoading', 'deviceCount', 'currentPage', 'pageSize'])
  },
  data () {
    return {
      formData: {},
      infoFormVisible: false,
      infoFormRules: infoFormRules,
      submitAction: 'add'
    }
  },
  mounted () {
    this.getDeivceList()
  },
  methods: {
    ...mapActions('plat/deviceList', ['getDeivceList', 'updateDevice', 'addDevice', 'removeDevice']),
    handleCurrentChange (val) {
      this.getDeivceList({ page: val, pageSize: this.pageSize })
    },
    handleSizeChange (val) {
      this.getDeivceList({ page: this.currentPage, pageSize: val })
    },
    handleAddDeviceClick () {
      this.formData = {}
      this.infoFormVisible = true
      this.submitAction = 'add'
    },
    handleManage (row) {
      if (row.type === 3) {
        window.open('http://' + row.ip_address + ':7000')
      }
      window.open('http://' + row.ip_address)
    },
    handleInfoModalVisible (row) {
      this.formData = { ...row }
      this.infoFormVisible = true
      this.submitAction = 'edit'
    },
    handleSubmitInfoForm () {
      this.$refs.infoForm.validate((valid) => {
        if (!valid) return false
        this.handleSubmit()
      })
    },
    handleRemove (row) {
      this.formData = { ...row }
      this.submitAction = 'remove'
      this.handleSubmit(row)
    },
    handleSubmit (param) {
      const that = this
      const data = {
        ...this.formData,
        ...param,
        successNotice: 1,
        callback: (responseData) => {
          this.infoFormVisible = false
          that.getDeivceList({ page: this.currentPage, pageSize: this.pageSize })
        }
      }
      if (this.submitAction === 'add') {
        this.addDevice(data)
      } else if (this.submitAction === 'edit') {
        this.updateDevice(data)
      } else if (this.submitAction === 'remove') {
        this.removeDevice(data)
      }
    }
  }
}
</script>

<style lang="scss">
</style>
