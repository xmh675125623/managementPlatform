import { PLAT_OPERATE_LOG_SEARCH } from '@/api/plat.log'

export default {
  namespaced: true,
  state: {
    tableName: null,
    tableList: [],
    logList: [],
    logCount: 0,
    listLoading: false,
    currentPage: 1,
    pageSize: 10
  },
  actions: {
    async getLogList ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/operateLog/listLoading'
      const res = await PLAT_OPERATE_LOG_SEARCH(data)
      commit('setLogList', res)
    }
  },
  mutations: {
    setLogList (state, payload) {
      state.logList = payload.list
      state.logCount = payload.count
      state.currentPage = payload.page
      state.pageSize = payload.pageSize
      state.tableName = payload.tableName
      state.tableList = payload.tableList
    },
    listLoading (state, loading) {
      state.listLoading = loading
    }
  }
}
