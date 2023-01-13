import {
  PLAT_AUDIT_LOG_TABLE_DELETE, PLAT_AUDIT_LOG_TABLE_EXPORT,
  PLAT_AUDIT_LOG_TABLE_SEARCH
} from '@/api/plat.log'

export default {
  namespaced: true,
  state: {
    tableList: [],
    listLoading: false,
    deleteLoading: false,
    exportLoading: false
  },
  actions: {
    async getTableList ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/auditLogManage/listLoading'
      const res = await PLAT_AUDIT_LOG_TABLE_SEARCH(data)
      commit('setTableList', res)
    },
    async deleteTable ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/auditLogManage/deleteLoading'
      await PLAT_AUDIT_LOG_TABLE_DELETE(data)
    },
    async exportTable ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/auditLogManage/exportLoading'
      await PLAT_AUDIT_LOG_TABLE_EXPORT(data)
    }
  },
  mutations: {
    setTableList (state, payload) {
      state.tableList = payload.list
    },
    listLoading (state, loading) {
      state.listLoading = loading
    },
    deleteLoading (state, loading) {
      state.deleteLoading = loading
    },
    exportLoading (state, loading) {
      state.exportLoading = loading
    }
  }
}