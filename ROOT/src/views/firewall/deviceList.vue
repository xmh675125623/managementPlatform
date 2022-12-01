<template>
  <d2-container>
    <template slot="header">防火墙设备列表</template>

    <!--防火墙设备列表-->
    <el-table :data="deviceList" stripe border tyle="width: 100%" v-loading="listLoading">
      <el-table-column prop="device_name" label="设备名称"/>
      <el-table-column prop="device_mark" label="设备别名"/>
      <el-table-column prop="ip_address" label="管理口IP"/>
      <el-table-column label="设备模式">
        <template slot-scope="scope">
          <el-link type="primary" @click="handleModeModalVisible(scope.row)">
            {{scope.row.mode === 1 ? '初始模式':''}}
            {{scope.row.mode === 2 ? '测试模式':''}}
            {{scope.row.mode === 3 ? '运行模式':''}}
            {{scope.row.mode === 4 ? '学习模式':''}}
          </el-link>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="320px">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleInfoModalVisible(scope.row)">编辑</el-button>
          &nbsp;
          <el-popconfirm title="确定重启吗？" @confirm="handleUpdate(scope.row)">
            <el-button size="mini" slot="reference" type="warning" plain :loading="updateLoading">重启</el-button>
          </el-popconfirm>
          &nbsp;
          <el-popconfirm title="确定移除吗？" @confirm="handleUpdate(scope.row, {action: 'remove'})">
            <el-button size="mini" slot="reference" type="danger" :loading="updateLoading">移除</el-button>
          </el-popconfirm>
          &nbsp;
          <el-dropdown @command="commond => handleCommand(commond, scope.row)">
            <el-button size="mini" type="primary">
              更多<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="rule">默认规则配置</el-dropdown-item>
              <el-dropdown-item command="snmp">SNMP配置</el-dropdown-item>
              <el-dropdown-item command="syslog">Syslog配置</el-dropdown-item>
              <el-dropdown-item command="time">时间同步</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <!--模式修改表单弹窗-->
    <el-dialog title="设备模式" :visible.sync="modeFormVisible" width="500px">
      <el-form :model="formData" label-width="100px">
        <el-form-item label="设备模式：" prop="mode">
          <el-select v-model="formData.mode" placeholder="请选择设备模式">
            <el-option label="初始模式" :value="1">初始模式</el-option>
            <el-option label="测试模式" :value="2">测试模式</el-option>
            <el-option label="运行模式" :value="3">运行模式</el-option>
            <el-option label="学习模式" :value="4">学习模式</el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="modeFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit({action: 'mode'})" :loading="updateLoading">确 定</el-button>
      </div>
    </el-dialog>

    <!--基础信息修改表单弹窗-->
    <el-dialog title="设备信息" :visible.sync="infoFormVisible" width="500px">
      <el-form :model="formData" label-width="100px" ref="infoForm" :rules="infoFormRules">
        <el-form-item label="设备名称：" prop="device_name">
          <el-input v-model="formData.device_name" disabled></el-input>
        </el-form-item>

        <el-form-item label="设备别名：" prop="device_mark">
          <el-input v-model="formData.device_mark" placeholder="请输入设备别名"></el-input>
        </el-form-item>

        <el-form-item label="版本号：" prop="edition">
          <el-input v-model="formData.edition" disabled></el-input>
        </el-form-item>

        <el-form-item label="添加时间：" prop="insert_time">
          <el-input v-model="formData.insert_time" disabled></el-input>
        </el-form-item>

        <el-form-item label="IP地址：" prop="ip_address">
          <el-input v-model="formData.ip_address" placeholder="请输入IP地址"></el-input>
        </el-form-item>

        <el-form-item label="子网掩码：" prop="subnetmask">
          <el-input v-model="formData.subnetmask" placeholder="请输入子网掩码"></el-input>
        </el-form-item>

        <el-form-item label="网关：" prop="gateway">
          <el-input v-model="formData.gateway" placeholder="网关"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="infoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmitInfoForm" :loading="updateLoading">确 定</el-button>
      </div>
    </el-dialog>

    <!--默认规则配置表单弹窗-->
    <el-dialog title="默认规则配置" :visible.sync="ruleFormVisible" width="500px">
      <el-form :model="formData" label-width="120px">
        <el-form-item label="默认规则行为：" prop="default_rule_action">
          <el-select v-model="formData.default_rule_action" placeholder="请选择行为" disabled style="width: 80%">
            <el-option label="放行" :value="1"></el-option>
            <el-option label="拦截" :value="2"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="默认日志记录：" prop="default_rule_log">
          <el-select v-model="formData.default_rule_log" placeholder="请选择" disabled style="width: 80%">
            <el-option label="开启" :value="1"></el-option>
            <el-option label="关闭" :value="2"></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="ruleFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit({action: 'defaule_log'})" :loading="updateLoading">确 定</el-button>
      </div>
    </el-dialog>

    <!--SNMP配置表单弹窗-->
    <el-dialog title="SNMP配置" :visible.sync="snmpFormVisible" width="500px">
      <el-form :model="formData" label-width="120px" :rules="snmpFormRules" ref="snmpForm" >
        <el-form-item label="默认规则行为：" prop="snmp_version">
          <el-checkbox-group v-model="formData.snmp_version">
            <el-checkbox label="1" name="snmp_version" value="1">v1&v2</el-checkbox>
            <el-checkbox label="3" name="snmp_version" value="3">v3</el-checkbox>
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="SNMP IP：" prop="snmp_ip">
          <el-input v-model="formData.snmp_ip" placeholder="请输入IP地址"></el-input>
        </el-form-item>

        <el-form-item label="SNMP端口：" prop="snmp_port">
          <el-input-number v-model="formData.snmp_port" controls-position="right" :min="1" :max="0xffff" placeholder="默认161"></el-input-number>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="snmpFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmitSnmp()" :loading="updateLoading">{{ `${formData.snmp_power}`==='1'?'停止服务':'开启服务' }}</el-button>
      </div>
    </el-dialog>

    <!--Syslog配置表单弹窗-->
    <el-dialog title="Syslog配置" :visible.sync="syslogFormVisible" width="500px">
      <el-form :model="formData" label-width="100px" :rules="syslogFormRules" ref="syslogForm" >

        <el-form-item label="服务器IP：" prop="syslog_ip">
          <el-input v-model="formData.syslog_ip" placeholder="请输入服务器IP"></el-input>
        </el-form-item>

        <el-form-item label="端口：" prop="syslog_port">
          <el-input-number v-model="formData.syslog_port" controls-position="right" :min="1" :max="0xffff" placeholder="默认514"></el-input-number>
        </el-form-item>

        <el-form-item label="协议类型：" prop="syslog_protocol">
          <el-select v-model="formData.syslog_protocol" placeholder="默认UDP" style="width: 100%">
            <el-option label="TCP" value="tcp">TCP</el-option>
            <el-option label="UDP" value="udp">UDP</el-option>
          </el-select>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="syslogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmitSyslog()" :loading="updateLoading">{{ `${formData.syslog_power}`==='1'?'停止服务':'开启服务' }}</el-button>
      </div>
    </el-dialog>

    <!--时间同步表单弹窗-->
    <el-dialog title="时间同步" :visible.sync="timeFormVisible" width="500px">
      <el-form :model="formData" label-width="120px" :rules="timeFormRules" ref="timeForm" >

        <el-form-item label="同步方式：" prop="syncType">
          <el-select v-model="formData.syncType" placeholder="请选择同步方式" style="width: 100%">
            <el-option label="NTP同步" :value="1">NTP同步</el-option>
            <el-option label="手动同步" :value="2">手动同步</el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="NTP服务器IP：" prop="ntp_ip" v-if="formData.syncType === 1">
          <el-input v-model="formData.ntp_ip" placeholder="请输入NTP服务器IP"></el-input>
        </el-form-item>

        <el-form-item label="同步时间：" prop="syncTime" v-if="formData.syncType === 2">
         <el-date-picker v-model="formData.syncTime" type="datetime" placeholder="请选择时间"></el-date-picker>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="timeFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmitTime()" :loading="updateLoading">同步</el-button>
      </div>
    </el-dialog>

  </d2-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import { ipv4Check } from '@/libs/checkData'
import util from '@/libs/util'

const infoFormRules = {
  device_mark: [
    { max: 20, message: '长度不可超过20', trigger: 'blur' }
  ],
  ip_address: [
    { required: true, message: '请输入IP地址', trigger: 'blur' },
    { validator: (rule, value, callback) => ipv4Check('IP地址', value, callback), trigger: 'blur' }
  ],
  subnetmask: [
    { validator: (rule, value, callback) => ipv4Check('子网掩码', value, callback), trigger: 'blur' }
  ],
  gateway: [
    { validator: (rule, value, callback) => ipv4Check('网关', value, callback), trigger: 'blur' }
  ]
}

const snmpFormRules = {
  snmp_ip: [
    { required: true, message: '请输入IP地址', trigger: 'blur' },
    { validator: (rule, value, callback) => ipv4Check('IP地址', value, callback), trigger: 'blur' }
  ]
}

const syslogFormRules = {
  syslog_ip: [
    { required: true, message: '请输入服务器IP', trigger: 'blur' },
    { validator: (rule, value, callback) => ipv4Check('IP地址', value, callback), trigger: 'blur' }
  ]
}

const timeFormRules = {
  syncType: [
    { required: true, message: '请选择同步方式', trigger: 'blur' }
  ],
  ntp_ip: [
    { required: true, message: '请输入NTP服务器IP', trigger: 'blur' },
    { validator: (rule, value, callback) => ipv4Check('IP地址', value, callback), trigger: 'blur' }
  ],
  syncTime: [
    { required: true, message: '请选择时间', trigger: 'blur' }
  ]
}

export default {
  computed: {
    ...mapState('firewall/deviceList', ['deviceList', 'listLoading', 'updateLoading'])
  },
  data () {
    return {
      formData: {},
      modeFormVisible: false,
      infoFormVisible: false,
      ruleFormVisible: false,
      snmpFormVisible: false,
      syslogFormVisible: false,
      timeFormVisible: false,
      infoFormRules: infoFormRules,
      snmpFormRules: snmpFormRules,
      syslogFormRules: syslogFormRules,
      timeFormRules: timeFormRules

    }
  },
  mounted () {
    this.getDeivceList()
  },
  methods: {
    ...mapActions('firewall/deviceList', ['getDeivceList', 'updateDevice']),
    handleCommand (command, row) {
      if (command === 'rule') {
        this.handleRuleVisible(row)
      } else if (command === 'snmp') {
        this.handleSnmpVisible(row)
      } else if (command === 'syslog') {
        this.handleSyslogVisible(row)
      } else if (command === 'time') {
        this.handleTimeVisible(row)
      }
    },
    handleModeModalVisible (row) {
      this.formData = { ...row }
      this.modeFormVisible = true
    },
    handleInfoModalVisible (row) {
      this.formData = { ...row }
      this.infoFormVisible = true
    },
    handleRuleVisible (row) {
      this.formData = { ...row }
      this.ruleFormVisible = true
    },
    handleSnmpVisible (row) {
      this.formData = { ...row }
      this.formData.snmp_version = this.formData.snmp_version ? this.formData.snmp_version.split(',') : []
      this.snmpFormVisible = true
    },
    handleSyslogVisible (row) {
      this.formData = { ...row }
      this.syslogFormVisible = true
    },
    handleTimeVisible (row) {
      this.formData = { ...row }
      this.timeFormVisible = true
    },
    handleSubmitTime () {
      this.$refs.timeForm.validate((valid) => {
        if (!valid) return false
        const param = {}
        if (this.formData.syncType === 1) {
          param.action = 'npt'
        } else if (this.formData.syncType === 2) {
          this.formData.syncTime = util.dateFtt('yyyy-MM-dd hh:mm:ss', this.formData.syncTime)
          param.action = 'syncTime'
        }
        this.handleSubmit(param)
      })
    },
    handleSubmitSyslog () {
      this.$refs.syslogForm.validate((valid) => {
        const param = {}
        if (`${this.formData.syslog_power}` !== '1') {
          if (!valid) return false
          param.action = 'syslog'
        } else {
          param.action = 'clear_syslog'
        }
        this.handleSubmit(param)
      })
    },
    handleSubmitSnmp () {
      this.$refs.snmpForm.validate((valid) => {
        const param = {}
        if (`${this.formData.snmp_power}` !== '1') {
          if (!valid) return false
          param.action = 'snmp'
        } else {
          param.action = 'clear_snmp'
        }
        this.handleSubmit(param)
      })
    },
    handleSubmitInfoForm () {
      const param = { action: 'baseInfo' }
      this.$refs.infoForm.validate((valid) => {
        if (!valid) return false
        alert('修改IP地址若5秒钟没有自动跳转，请在浏览器输入新的IP地址进入管理平台')
        this.handleSubmit(param)
      })
    },
    handleUpdate (row, param) {
      this.formData = { ...row }
      this.handleSubmit(param)
    },
    handleSubmit (param) {
      const that = this
      const data = {
        deviceName: this.formData.device_name,
        ...this.formData,
        ...param,
        successNotice: 1,
        callback: (responseData) => {
          this.modeFormVisible = false
          this.infoFormVisible = false
          this.ruleFormVisible = false
          this.snmpFormVisible = false
          this.syslogFormVisible = false
          this.timeFormVisible = false
          that.getDeivceList()
        }
      }
      this.updateDevice(data)
    }
  }
}
</script>

<style lang="scss">
</style>
