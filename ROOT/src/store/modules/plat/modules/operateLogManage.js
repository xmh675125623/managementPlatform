import {
  PLAT_OPERATE_LOG_TABLE_DELETE,
  PLAT_OPERATE_LOG_TABLE_EXPORT,
  PLAT_OPERATE_LOG_TABLE_SEARCH
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
      data.requestLoading = 'plat/operateLogManage/listLoading'
      const res = await PLAT_OPERATE_LOG_TABLE_SEARCH(data)
      commit('setTableList', res)
    },
    async deleteTable ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/operateLogManage/deleteLoading'
      await PLAT_OPERATE_LOG_TABLE_DELETE(data)
    },
    async exportTable ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/operateLogManage/exportLoading'
      await PLAT_OPERATE_LOG_TABLE_EXPORT(data)
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
