<template>
  <d2-container>
    <template slot="header">操作日志管理</template>

    <el-row style="padding-bottom: 12px">
      <el-upload
        :action="diskSpaceUrlExport"
        accept=".operate"
        name="file"
        :data="uploadData"
        :on-success="uploadOnSuccress"
        :on-error="uploadOnError"
        ref="operateUpload"
        :auto-upload="true"
      >
        <el-button slot="trigger" type="success" icon="el-icon-upload2" @click="handleClickSelectFile">导入（备份恢复）</el-button>
      </el-upload>
    </el-row>

    <!--日志表列表-->
    <el-table :data="tableList" stripe border tyle="width: 100%" v-loading="listLoading">
      <el-table-column prop="table_name" label="表名/日期" width="200"/>
      <el-table-column label="数据占用空间">
        <template slot-scope="scope">
          {{scope.row.data_size}}MB
        </template>
      </el-table-column>
      <el-table-column label="索引占用空间">
        <template slot-scope="scope">
          {{scope.row.index_size}}MB
        </template>
      </el-table-column>
      <el-table-column label="总占用空间">
        <template slot-scope="scope">
          {{scope.row.total_size}}MB
        </template>
      </el-table-column>
      <el-table-column prop="create_time" label="创建时间" width="180"/>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-popconfirm title="导出后的数据为加密数据不可进行修改，如果修改则无法进行导入" @confirm="handleExport(scope.row)" style="margin-left: 10px">
            <el-button size="mini" :loading="exportTableLoading(scope.row)" slot="reference">导出（备份）</el-button>
          </el-popconfirm>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row)" style="margin-left: 10px">
            <el-button size="mini" type="danger" :loading="deleteTableLoading(scope.row)" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </d2-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import { diskSpaceUrlExport, fileDownloadUrl } from '@/api/_service'
import cookies from '@/libs/util.cookies'
import dataCrpty from '@/libs/webDataUtils'
import util from '@/libs/util'
import { Message, MessageBox } from 'element-ui'

export default {
  computed: {
    ...mapState('plat/operateLogManage', ['tableList', 'listLoading', 'deleteLoading', 'exportLoading']),
    exportTableLoading () {
      return (row) => {
        return row.table_name === this.exportingTable && this.exportLoading
      }
    },
    deleteTableLoading () {
      return (row) => {
        return row.table_name === this.deleteTableName && this.deleteLoading
      }
    }
  },
  data () {
    return {
      diskSpaceUrlExport: diskSpaceUrlExport,
      exportingTable: '',
      deleteTableName: '',
      optionPassword: '',
      uploadData: {}
    }
  },
  mounted () {
    this.getTableList()
  },
  methods: {
    ...mapActions('plat/operateLogManage', ['getTableList', 'deleteTable', 'exportTable']),
    handleClickSelectFile () {
      const data = {
        method: 'plat.disk_operate.import',
        aid: cookies.get('uuid'),
        sessionid: cookies.get('token'),
        option_password: this.optionPassword
      }
      this.uploadData = dataCrpty(data)
    },
    uploadOnSuccress (response, file, fileList) {
      if (response && response.status === 'ok') {
        Message({
          message: `${file.name} 备份文件导入成功`,
          type: 'success',
          duration: 3 * 1000
        })
        this.getTableList()
        return
      } else {
        file.status = 'fail'
        if (response.needInputOptPass === 1) {
          Message({
            message: '请输入操作密码，并重新选择文件上传',
            type: 'error',
            duration: 3 * 1000
          })
          MessageBox.prompt('请输入操作密码，并重新选择文件上传', '身份鉴别', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            inputType: 'password'
          }).then(({ value }) => {
            this.optionPassword = value
          })
          return
        }
      }
      if (response.code === 401) {
        Message({
          message: '身份验证信息过期，请重新登录',
          type: 'error',
          duration: 3 * 1000
        })
        this.logout()
      } else if (response.code === 403) {
        Message({
          message: '用户未得到授权，访问是被禁止的',
          type: 'error',
          duration: 3 * 1000
        })
      } else {
        Message({
          message: response.errorMsg,
          type: 'error',
          duration: 3 * 1000
        })
      }
    },
    uploadOnError (err, file, fileList) {
      Message({
        message: err,
        type: 'error',
        duration: 5 * 1000
      })
    },
    logout () {
      // 删除cookie
      util.cookies.remove('token')
      util.cookies.remove('uuid')
      // 清空 vuex 用户信息
      this.$store.dispatch('d2admin/user/set', {}, { root: true })
      // 跳转路由
      this.$router.push({ name: 'login' })
    },
    handleDelete (row) {
      this.deleteTableName = row.table_name
      const that = this
      const data = {
        table_name: row.table_name,
        successNotice: 1,
        callback: (responseData) => {
          that.getTableList()
        }
      }
      this.deleteTable(data)
    },
    handleExport (row) {
      this.exportingTable = row.table_name
      const data = {
        table_name: row.table_name,
        successNotice: 1,
        callback: (responseData) => {
          window.location.href = fileDownloadUrl + responseData.fileMark;
        }
      }
      this.exportTable(data)
    }
  }
}
</script>

<style lang="scss">
</style>
