<template>
  <d2-container>
    <template slot="header">系统设置</template>

    <!--设置列表-->
    <el-table :data="settingList" stripe border tyle="width: 100%" v-loading="listLoading">
      <el-table-column prop="name" label="名称"/>
      <el-table-column prop="description" label="描述" width="400"/>
      <el-table-column prop="default_value" label="默认值"/>
      <el-table-column prop="value" label="值"/>
      <el-table-column prop="type" label="类型"/>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)" v-if="scope.row.id != 1">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--设置编辑弹窗表单-->
    <el-dialog title="设置修改" :visible.sync="settingFormVisible" width="500px" :close-on-click-modal="false">
      <el-form :model="settingFormData" label-width="100px" ref="settingForm">
        <el-form-item label="名称：" prop="name">
          <el-input v-model="settingFormData.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="默认值：" prop="default_value">
          <el-input v-model="settingFormData.default_value" disabled></el-input>
        </el-form-item>
        <el-form-item label="值：" prop="value" :rules="settingValueRules">
          <el-input v-model="settingFormData.value" placeholder="请输入值"></el-input>
        </el-form-item>
        <el-form-item label="描述：" prop="remark">
          <el-input type="textarea" v-model="settingFormData.description" disabled rows="6"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="settingFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="editSettingLoading">确 定</el-button>
      </div>
    </el-dialog>

  </d2-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  computed: {
    ...mapState('plat/setting', ['settingList', 'listLoading', 'editSettingLoading'])
  },
  data () {
    return {
      settingFormVisible: false,
      settingFormData: {},
      settingValueRules: [
        { validator: this.checkValueRule, trigger: 'blur' }
      ]
    }
  },
  mounted () {
    this.getSettingList()
  },
  methods: {
    ...mapActions('plat/setting', ['getSettingList', 'editSetting']),
    checkValueRule (rule, value, callback) {
      if (this.settingFormData.type === 'Number') {
        if (isNaN(value)) {
          // eslint-disable-next-line standard/no-callback-literal
          callback('请输入数字')
        }
        if (value < this.settingFormData.min) {
          // eslint-disable-next-line standard/no-callback-literal
          callback('请输入大于' + this.settingFormData.min + '的值')
        }
        if (value > this.settingFormData.max) {
          // eslint-disable-next-line standard/no-callback-literal
          callback('请输入小于' + this.settingFormData.max + '的值')
        }
      }
      return callback()
    },
    handleEdit (index, row) {
      this.settingFormVisible = true
      this.settingFormData = { ...row }
    },
    handleSubmit () {
      this.$refs.settingForm.validate((valid) => {
        if (valid) {
          const that = this
          const data = {
            ...this.settingFormData,
            callback: (responseData) => {
              that.settingFormVisible = false
              that.getSettingList()
            },
            successNotice: 1
          }
          this.editSetting(data)
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
</style>
