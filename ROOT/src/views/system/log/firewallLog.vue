<template>
  <d2-container>
    <template slot="header">防火墙日志查看</template>
    <el-row style="padding-bottom: 12px">
      <el-col :span="12">
        <span style="display:inline-block; width: 100px">表/日期：</span>
        <el-select placeholder="请选择" @change="handleChangeTable" :value="tableName" style="width: 300px">
          <el-option
            v-for="item in tableList"
            :key="item.table_name"
            :label="item.table_name"
            :value="item.table_name">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="12" style="padding-top: 8px;" v-show="isOpenCondition">
        <span style="display: inline-block; width: 100px">事件等级：</span>
        <el-checkbox-group v-model="level" style="display: inline-block">
          <el-checkbox v-for="level_ in levelArray" :label="level_.value" :key="level_.value">{{level_.label}}</el-checkbox>
        </el-checkbox-group>
      </el-col>
      <el-col :span="12" v-show="!isOpenCondition">
        <el-button type="primary" icon="el-icon-search" @click="handleSearch" :loading="listLoading">搜索</el-button>
        <el-button @click="handleResetForm">重置</el-button>
<!--        <el-button type="primary" icon="el-icon-download" :loading="exportLoading" @click="exportLogs">导出</el-button>-->
<!--        <el-button type="primary" icon="el-icon-printer" @click="printLog">打印</el-button>-->
        <a href="javascript:;" style="margin-left: 12px;" @click="toggleForm">展开 <i class="el-icon-arrow-down"></i></a>
      </el-col>
    </el-row>

    <el-row style="padding-bottom: 12px" v-show="isOpenCondition">
      <el-col :span="12">
        <span style="display: inline-block; width: 100px">描述：</span>
        <el-input v-model="context" placeholder="请输入描述内容" style="width: 300px"></el-input>
      </el-col>
      <el-col :span="12">
        <span style="display: inline-block; width: 100px">模块：</span>
        <el-checkbox-group v-model="module" style="display: inline-block">
          <el-checkbox v-for="module_ in moduleArray" :label="module_.value" :key="module_.value">{{module_.label}}</el-checkbox>
        </el-checkbox-group>
      </el-col>
    </el-row>

    <el-row style="padding-bottom: 12px" v-show="isOpenCondition">
      <el-col :span="12">
        <span style="display: inline-block; width: 100px">源IP：</span>
        <el-input v-model="sip" placeholder="请输入源IP" style="width: 300px"></el-input>
      </el-col>
      <el-col :span="12">
        <span style="display: inline-block; width: 100px">目的IP：</span>
        <el-input v-model="dip" placeholder="请输入目的IP" style="width: 300px"></el-input>
      </el-col>
    </el-row>

    <el-row style="padding-bottom: 12px" v-show="isOpenCondition">
      <el-col :span="12"><span style="display: inline-block; width: 100px"></span></el-col>
      <el-col :span="12">
        <el-button type="primary" icon="el-icon-search" @click="handleSearch" :loading="listLoading">搜索</el-button>
        <el-button @click="handleResetForm">重置</el-button>
<!--        <el-button type="primary" icon="el-icon-download" :loading="exportLoading" @click="exportLogs">导出</el-button>-->
<!--        <el-button type="primary" icon="el-icon-printer">打印</el-button>-->
        <a href="javascript:;" style="margin-left: 12px;" @click="toggleForm">收起 <i class="el-icon-arrow-up"></i></a>
      </el-col>
    </el-row>

    <!--日志列表-->
    <el-table :data="logList" stripe border tyle="width: 100%" v-loading="listLoading"
              @selection-change="handleSelectionChange" row-key="id" ref="multipleTable"
              :default-sort = "{prop: 'id', order: 'descending'}" @sort-change="handleSortChange">
<!--      <el-table-column type="selection" width="55" :reserve-selection="true"></el-table-column>-->
      <el-table-column label="ID" width="80">
        <template v-slot="scope">
          {{scope.row.id}}
        </template>
      </el-table-column>
      <el-table-column label="事件等级" width="120" prop="level" sortable="custom">
        <template v-slot="scope">
          {{severitys[scope.row.level]}}
        </template>
      </el-table-column>
      <el-table-column label="模块" width="120" prop="module" sortable="custom">
        <template v-slot="scope">
          {{modules[scope.row.module] || scope.row.module}}
        </template>
      </el-table-column>
      <el-table-column prop="message" label="信息"/>
      <el-table-column prop="id" label="添加时间" width="160" sortable="custom">
        <template v-slot="scope">
          {{scope.row.add_time}}
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
        :total="logCount"
        style="float: right">
      </el-pagination>
    </div>

    <div style="display: none">
      <el-table id="printTable" :data="selectedRows" stripe border tyle="width: 100%" :row-style="rowStyle" row-key="id">
        <el-table-column prop="id" label="ID" width="80"/>
        <el-table-column label="等级" width="60">
          <template v-slot="scope">
            {{severitys[scope.row.level]}}
          </template>
        </el-table-column>
        <el-table-column label="模块" width="120">
          <template v-slot="scope">
            {{modules[scope.row.module] || scope.row.module}}
          </template>
        </el-table-column>
        <el-table-column prop="message" label="信息"/>
        <el-table-column prop="add_time" label="添加时间" width="160"/>
      </el-table>
    </div>

  </d2-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import { Message } from 'element-ui'
import { fileDownloadUrl } from '@/api/_service'

const levelcolors = { 1: { color: '#000' }, 2: { color: 'orange' }, 3: { color: 'red' } }
const eventTypes = { 1: '正常事件', 0: '异常事件' }
const modules = { 11: 'NET_FILTER', 12: 'MODBUS_TCP', 15: 'HTTP', 16: 'FTP', 17: 'TELNET', 18: 'S7' }
const levelArray = [{ label: 'Emerg', value: 0 }, { label: 'Alert', value: 1 }, { label: 'Critical', value: 2 },
  { label: 'Error', value: 3 }, { label: 'Warning', value: 4 }, { label: 'Notice', value: 5 }, { label: 'Info', value: 6 },
  { label: 'Debug', value: 7 }]
const moduleArray = [{ label: 'IPMAC', value: 10 }, { label: 'NET_FILTER', value: 11 }, { label: 'MODBUS_TCP', value: 12 },
  { label: 'OPC_CLASSIC_TCP', value: 13 }, { label: 'IEC104', value: 14 }, { label: 'HTTP', value: 15 },
  { label: 'FTP', value: 16 }, { label: 'TELNET', value: 17 }]
const severitys = { 0: 'Emergency', 1: 'Alert', 2: 'Critical', 3: 'Error', 4: 'Warning', 5: 'Notice', 6: 'Informational', 7: 'Debug' }

export default {
  computed: {
    ...mapState('plat/firewallLog', ['tableName', 'tableList', 'logList', 'logCount', 'listLoading', 'currentPage',
      'pageSize', 'searchCondition', 'isOpenCondition', 'exportLoading']),
    level: {
      get () {
        return this.$store.state.plat.firewallLog.searchCondition.level
      },
      set (value) {
        this.$store.commit('plat/firewallLog/setLevel', value)
      }
    },
    context: {
      get () {
        return this.$store.state.plat.firewallLog.searchCondition.context
      },
      set (value) {
        this.$store.commit('plat/firewallLog/setContext', value)
      }
    },
    module: {
      get () {
        return this.$store.state.plat.firewallLog.searchCondition.module
      },
      set (value) {
        this.$store.commit('plat/firewallLog/setModule', value)
      }
    },
    sip: {
      get () {
        return this.$store.state.plat.firewallLog.searchCondition.sip
      },
      set (value) {
        this.$store.commit('plat/firewallLog/setSip', value)
      }
    },
    dip: {
      get () {
        return this.$store.state.plat.firewallLog.searchCondition.dip
      },
      set (value) {
        this.$store.commit('plat/firewallLog/setDip', value)
      }
    }
  },
  data () {
    return {
      levelcolors: levelcolors,
      eventTypes: eventTypes,
      modules: modules,
      levelArray: levelArray,
      moduleArray: moduleArray,
      severitys: severitys,
      selectedRows: []
    }
  },
  mounted () {
    this.getLogList({ page: this.currentPage, pageSize: this.pageSize })
  },
  methods: {
    ...mapActions('plat/firewallLog', ['getLogList', 'exportLog']),
    handleCurrentChange (val) {
      this.getLogList({ table_name: this.tableName, page: val, pageSize: this.pageSize })
    },
    handleSizeChange (val) {
      this.getLogList({ table_name: this.tableName, page: this.currentPage, pageSize: val })
    },
    handleChangeTable (tableName) {
      this.getLogList({ table_name: tableName, page: 1, pageSize: this.pageSize })
    },
    handleSearch () {
      this.getLogList({ table_name: this.tableName, page: 1, pageSize: this.pageSize })
    },
    handleResetForm () {
      this.$store.commit('plat/firewallLog/setLevel', [])
      this.$store.commit('plat/firewallLog/setContext', '')
      this.$store.commit('plat/firewallLog/setModule', [])
      this.$store.commit('plat/firewallLog/setSip', '')
      this.$store.commit('plat/firewallLog/setDip', '')
    },
    rowStyle (data) {
      return levelcolors[data.row.level]
    },
    toggleForm () {
      this.$store.commit('plat/firewallLog/toggleCondition')
    },
    handleSelectionChange (val) {
      this.selectedRows = val
    },
    handleSortChange (sort) {
      this.$store.commit('plat/firewallLog/setSortField', { sortField: sort.prop, sortOrder: sort.order })
      this.getLogList({ table_name: this.tableName, page: 1, pageSize: this.pageSize })
    },
    printLog () {
      if (this.selectedRows.length < 1) {
        Message({
          message: '请选择要打印的日志',
          type: 'warning'
        })
        return
      }
      var needPrint = document.getElementById('printTable')
      var win = window.open('')
      win.document.write('<html><head><link href="css/bootstrap/bootstrap.min.css" rel="stylesheet" />' +
        '<link href="css/app.css" rel="stylesheet" />' +
        '<link href="PWA.styles.css" rel="stylesheet" /></head><body>' +
        needPrint.outerHTML + '</body>' +
        '</html>')
      win.document.close()
      // Chrome
      if (navigator.userAgent.indexOf('Chrome') !== -1) {
        win.onload = function () {
          win.document.execCommand('print')
          win.close()
        }
        // eslint-disable-next-line brace-style
      }
      // Firefox
      else {
        win.print()
        win.close()
      }
    },
    exportLogs () {
      if (this.selectedRows.length < 1) {
        Message({
          message: '请选择要导出的日志',
          type: 'warning'
        })
        return
      }
      const selectedRowsStr = JSON.stringify(this.selectedRows)
      const data = {
        selectedRows: selectedRowsStr,
        successNotice: 1,
        callback: (responseData) => {
          window.location.href = fileDownloadUrl + responseData.fileMark
        }
      }
      this.exportLog(data)
    }
  }
}
</script>

<style lang="scss">
</style>
