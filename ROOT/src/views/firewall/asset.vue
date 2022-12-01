<template>
  <d2-container>
    <template slot="header">资产管理</template>
    <el-row style="padding-bottom: 12px">
      <el-button type="primary" icon="el-icon-plus" @click="handleAddAssetClick">添加</el-button>
    </el-row>

    <!--资产列表-->
    <el-table :data="assetList" stripe border tyle="width: 100%" v-loading="listLoading">
      <el-table-column prop="id" label="ID"/>
      <el-table-column prop="asset_name" label="资产名称"/>
      <el-table-column prop="ip_address" label="IP地址"/>
      <el-table-column label="备注" width="260">
        <template slot-scope="scope">
          <pre style="white-space: pre-wrap; word-wrap: break-word">{{scope.row.remark}}</pre>
        </template>
      </el-table-column>
      <el-table-column prop="add_time" label="添加时间"/>
      <el-table-column prop="add_user" label="添加者"/>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.$index, scope.row)" style="margin-left: 10px">
            <el-button size="mini" type="danger" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--资产编辑弹窗表单-->
    <el-dialog :title="assetFormTitle" :visible.sync="assetFormVisible" width="500px">
      <el-form :model="assetFormData" label-width="100px" ref="assetForm" :rules="rules">
        <el-form-item label="资产名称：" prop="asset_name">
          <el-input v-model="assetFormData.asset_name" autocomplete="off" placeholder="请输入资产名称"></el-input>
        </el-form-item>
        <el-form-item label="IP地址：" prop="ip_address">
          <el-input v-model="assetFormData.ip_address" autocomplete="off" placeholder="请输入IP地址"></el-input>
        </el-form-item>
        <el-form-item label="备注：" prop="remark">
          <el-input type="textarea" v-model="assetFormData.remark" placeholder="请输入备注（长度不超过200字）" rows="4"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="assetFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="addLoading">确 定</el-button>
      </div>
    </el-dialog>

  </d2-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import { ipv4Check } from '@/libs/checkData'

const rules = {
  asset_name: [
    { required: true, message: '请输入资产名称', trigger: 'blur' },
    { max: 20, message: '资产名称不可超过20', trigger: 'blur' }
  ],
  ip_address: [
    { required: true, message: '请输入IP地址', trigger: 'blur' },
    { validator: (rule, value, callback) => ipv4Check('IP地址', value, callback), trigger: 'blur' }
  ],
  remark: [
    { whitespace: true, message: '备注不可全部为空格', trigger: 'blur' },
    { max: 200, message: '备注长度不可超过200', trigger: 'blur' }
  ]
}

export default {
  computed: {
    ...mapState('firewall/asset', ['assetList', 'listLoading', 'addLoading'])
  },
  data () {
    return {
      rules: rules,
      assetFormTitle: '资产添加',
      assetFormAction: 'add',
      assetFormVisible: false,
      assetFormData: {}
    }
  },
  mounted () {
    this.getAssetList()
  },
  methods: {
    ...mapActions('firewall/asset', ['getAssetList', 'addAsset', 'editAsset', 'deleteAsset']),
    handleAddAssetClick () {
      this.assetFormData = {}
      this.assetFormTitle = '资产添加'
      this.assetFormAction = 'add'
      this.assetFormVisible = true
    },
    handleEdit (index, row) {
      this.assetFormData = { ...row }
      this.assetFormTitle = '资产编辑'
      this.assetFormAction = 'edit'
      this.assetFormVisible = true
    },
    handleDelete (index, row) {
      const that = this
      const data = {
        id: row.id,
        callback: (responseData) => {
          that.getAssetList()
        },
        successNotice: 1
      }
      this.deleteAsset(data)
    },
    handleSubmit () {
      const that = this
      this.$refs.assetForm.validate((valid) => {
        if (valid) {
          const data = {
            ...that.assetFormData,
            callback: (responseData) => {
              that.assetFormVisible = false
              that.getAssetList()
            },
            successNotice: 1
          }
          if (that.assetFormAction === 'add') {
            that.addAsset(data)
          } else {
            that.editAsset(data)
          }
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
