<template>
  <d2-container>
    <template slot="header">IP白名单</template>

    <el-row style="padding-bottom: 12px">
      <el-button type="primary" icon="el-icon-plus" @click="handleAddWhitelistClick">添加IP地址白名单</el-button>
    </el-row>

    <!--白名单列表-->
    <el-table :data="whitelistList" stripe border tyle="width: 100%" v-loading="listLoading">
      <el-table-column prop="ip_address" label="IP地址"/>
      <el-table-column prop="remark" label="备注"/>
      <el-table-column prop="add_user" label="添加者"/>
      <el-table-column prop="add_time" label="添加时间"/>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.$index, scope.row)" style="margin-left: 10px">
            <el-button size="mini" type="danger" :loading="deleteWhitelistLoading(scope.row)" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--ip白名单添加弹窗表单-->
    <el-dialog title="IP白名单添加" :visible.sync="addFormVisible" width="500px" :close-on-click-modal="false">
      <el-form :model="addFormData" label-width="100px" ref="addForm" :rules="addRules">
        <el-form-item label="IP地址：" prop="ip_address">
          <el-input v-model="addFormData.ip_address"></el-input>
        </el-form-item>
        <el-form-item label="备注：" prop="remark">
          <el-input v-model="addFormData.remark" ></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="addLoading">确 定</el-button>
      </div>
    </el-dialog>

  </d2-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import { ipv4Check } from '@/libs/checkData'

const addRules = {
  ip_address: [
    { required: true, message: '请填写IP地址', trigger: 'blur' },
    { validator: (rule, value, callback) => ipv4Check('IP地址', value, callback), trigger: 'blur' }
  ]
}

export default {
  computed: {
    deleteWhitelistLoading (row) {
      const that = this
      return (row) => {
        return that.deleteId === row.id && that.deleteLoading
      }
    },
    ...mapState('plat/whitelist', ['whitelistList', 'listLoading', 'addLoading', 'deleteLoading'])
  },
  data () {
    return {
      addFormVisible: false,
      addFormData: {},
      addRules: addRules,
      deleteId: 0
    }
  },
  mounted () {
    this.getWhitelistList()
  },
  methods: {
    ...mapActions('plat/whitelist', ['getWhitelistList', 'addWhitelist', 'deleteWhitelist']),
    handleAddWhitelistClick () {
      this.addFormVisible = true
    },
    handleSubmit () {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          const that = this
          const data = {
            ...this.addFormData,
            callback: (responseData) => {
              that.addFormVisible = false
              this.addFormData = {}
              that.getWhitelistList()
            },
            successNotice: 1
          }
          this.addWhitelist(data)
        } else {
          return false
        }
      })
    },
    handleCancel () {
      this.addFormVisible = false
      this.addFormData = {}
    },
    handleDelete (index, row) {
      this.deleteId = row.id
      const that = this
      const data = {
        id: row.id,
        callback: (responseData) => {
          that.getWhitelistList()
        },
        successNotice: 1
      }
      this.deleteWhitelist(data)
    }
  }
}
</script>

<style lang="scss">
</style>
