<template>
  <d2-container>
    <template slot="header">操作日志查看</template>
    <el-row style="padding-bottom: 12px">
      表/日期：
      <el-select placeholder="请选择" @change="handleChangeTable" :value="tableName">s
        <el-option
          v-for="item in tableList"
          :key="item.table_name"
          :label="item.table_name"
          :value="item.table_name">
        </el-option>
      </el-select>
    </el-row>

    <!--日志列表-->
    <el-table :data="logList" stripe border tyle="width: 100%" v-loading="listLoading">
      <el-table-column prop="id" label="ID" width="80"/>
      <el-table-column prop="type" label="类型" width="120"/>
      <el-table-column prop="function_name" label="功能模块" width="120"/>
      <el-table-column prop="user_name" label="用户名" width="120"/>
      <el-table-column prop="ip_address" label="IP地址" width="120"/>
      <el-table-column prop="auth_result" label="验证结果" width="120"/>
      <el-table-column label="描述">
        <template slot-scope="scope">
          <pre style="white-space: pre-wrap; word-wrap: break-word">{{scope.row.description}}</pre>
        </template>
      </el-table-column>
      <el-table-column prop="add_time" label="添加时间" width="200"/>
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
        :total="logCount"
        style="float: right">
      </el-pagination>
    </div>

  </d2-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  computed: {
    ...mapState('plat/operateLog', ['tableName', 'tableList', 'logList', 'logCount', 'listLoading', 'currentPage', 'pageSize'])
  },
  data () {
    return {
    }
  },
  mounted () {
    this.getLogList({ page: this.currentPage, pageSize: this.pageSize })
  },
  methods: {
    ...mapActions('plat/operateLog', ['getLogList']),
    handleCurrentChange (val) {
      this.getLogList({ page: this.val, pageSize: this.pageSize })
    },
    handleSizeChange (val) {
      this.getLogList({ page: this.currentPage, pageSize: val })
    },
    handleChangeTable (tableName) {
      this.getLogList({ table_name: tableName, page: 1, pageSize: this.pageSize })
    },
    handleSubmit () {
      this.$refs.userForm.validate((valid) => {
        if (valid) {
          const that = this
          const data = {
            ...this.userForm,
            callback: (responseData) => {
              that.userEditFormVisible = false
              that.list({ page: that.currentPage, pageSize: that.pageSize })
            },
            successNotice: 1
          }
          if (this.userFormTitle === '用户添加') {
            this.addUser(data)
          } else {
            data.uid = data.id
            this.editUser(data)
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
