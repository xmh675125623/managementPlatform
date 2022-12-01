<template>
  <d2-container>
    <template slot="header">策略管理</template>
    <el-row style="padding-bottom: 12px">
      选择设备：
      <el-select v-model="device.device_name" placeholder="请选择设备" @change="deviceChange">
        <el-option
          v-for="item in deviceList"
          :key="item.deviceName"
          :label="item.deviceMark?item.deviceName+'_'+item.deviceMark : item.deviceName"
          :value="item.deviceName"
          :title="item.deviceMark">
        </el-option>
      </el-select>
      &nbsp;
      <el-button type="primary" icon="el-icon-plus" @click="handleAddGroupClick">添加</el-button>
    </el-row>

    <!--策略分组列表-->
    <el-table :data="groupList" stripe border tyle="width: 100%" v-loading="listLoading">
      <el-table-column prop="id" label="ID"/>
      <el-table-column prop="group_name" label="分组名称"/>
      <el-table-column prop="source_asset_name" label="源资产名"/>
      <el-table-column prop="source_asset_ip" label="源资产IP"/>
      <el-table-column prop="target_asset_name" label="目的资产名"/>
      <el-table-column prop="target_asset_ip" label="目的资产IP"/>
      <el-table-column prop="add_time" label="添加时间"/>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.$index, scope.row)" style="margin-left: 10px">
            <el-button size="mini" type="danger" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--分页器-->
    <div style="overflow: hidden;padding-top: 12px">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pagination.currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pagination.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="groupCount"
        style="float: right">
      </el-pagination>
    </div>

    <!--分组编辑弹窗表单-->
    <el-dialog :title="groupFormTitle" :visible.sync="groupFormVisible" width="500px">
      <el-form :model="groupFormData" label-width="100px" ref="groupForm" :rules="rules">
        <el-form-item label="策略组名：" prop="group_name">
          <el-input v-model="groupFormData.group_name" autocomplete="off" placeholder="请输入策略组名"></el-input>
        </el-form-item>
        <el-form-item label="源资产：" prop="source_asset">
          <el-select v-model="groupFormData.source_asset" placeholder="请选择源资产" style="width: 100%">
            <el-option
              v-for="item in assetList"
              :key="item.id"
              :label="item.asset_name"
              :value="item.id"
              :title="item.ip_address">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="目的资产：" prop="target_asset">
          <el-select v-model="groupFormData.target_asset" placeholder="请选择目的资产" style="width: 100%">
            <el-option
              v-for="item in assetList"
              :key="item.id"
              :label="item.asset_name"
              :value="item.id"
              :title="item.ip_address">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="groupFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="addLoading">确 定</el-button>
      </div>
    </el-dialog>

  </d2-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'

const rules = {
  group_name: [
    { required: true, message: '请输入策略组名称', trigger: 'blur' },
    { max: 20, message: '策略组名称不可超过20', trigger: 'blur' }
  ],
  source_asset: [
    { required: true, message: '请选择源资产', trigger: 'blur' }
  ],
  target_asset: [
    { required: true, message: '请选择源资产', trigger: 'blur' }
  ]
}

export default {
  computed: {
    ...mapState('firewall/strategyGroup', ['device', 'groupList', 'deviceList', 'assetList', 'listLoading', 'addLoading', 'groupCount', 'pagination'])
  },
  data () {
    return {
      rules: rules,
      groupFormTitle: '策略分组添加',
      groupFormAction: 'add',
      groupFormVisible: false,
      groupFormData: {}
    }
  },
  mounted () {
    this.getGroupList({
      deviceName: this.device.device_name,
      page: this.pagination.currentPage,
      pageSize: this.pagination.pageSize
    })
  },
  methods: {
    ...mapActions('firewall/strategyGroup', ['getGroupList', 'addGroup', 'editGroup', 'deleteGroup']),
    deviceChange () {
      this.pagination.currentPage = 1
      this.getGroupList({
        deviceName: this.device.device_name,
        page: this.pagination.currentPage,
        pageSize: this.pagination.pageSize
      })
    },
    handleCurrentChange (val) {
      this.currentPage = val
      this.getGroupList({
        deviceName: this.device.device_name,
        page: this.pagination.currentPage,
        pageSize: this.pagination.pageSize
      })
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.getGroupList({
        deviceName: this.device.device_name,
        page: this.pagination.currentPage,
        pageSize: this.pagination.pageSize
      })
    },
    handleAddGroupClick () {
      this.groupFormData = {}
      this.groupFormTitle = '策略分组添加'
      this.groupFormAction = 'add'
      this.groupFormVisible = true
    },
    handleEdit (index, row) {
      this.groupFormData = { ...row }
      this.groupFormTitle = '策略分组编辑'
      this.groupFormAction = 'edit'
      this.groupFormVisible = true
    },
    handleDelete (index, row) {
      const that = this
      const data = {
        id: row.id,
        callback: (responseData) => {
          that.getGroupList({
            deviceName: that.device.device_name,
            page: that.pagination.currentPage,
            pageSize: that.pagination.pageSize
          })
        },
        successNotice: 1
      }
      this.deleteGroup(data)
    },
    handleSubmit () {
      const that = this
      this.$refs.groupForm.validate((valid) => {
        if (valid) {
          const data = {
            deviceName: this.device.device_name,
            ...that.groupFormData,
            callback: (responseData) => {
              that.groupFormVisible = false
              that.getGroupList({
                deviceName: that.device.device_name,
                page: that.pagination.currentPage,
                pageSize: that.pagination.pageSize
              })
            },
            successNotice: 1
          }
          if (that.groupFormAction === 'add') {
            that.addGroup(data)
          } else {
            that.editGroup(data)
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
