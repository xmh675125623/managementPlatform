import {
  PLAT_AUDIT_LOG_SEARCH,
  PLAT_AUDIT_LOG_SEARCH_EXPORT
} from '@/api/plat.log'

export default {
  namespaced: true,
  state: {
    tableName: null,
    tableList: [],
    logList: [],
    logCount: 0,
    listLoading: false,
    currentPage: 1,
    pageSize: 10,
    searchCondition: {
      level: [],
      context: '',
      module: [],
      sip: '',
      dip: '',
      sortField: 'id',
      sortOrder: 'descending'
    },
    isOpenCondition: false,
    exportLoading: false
  },
  actions: {
    async getLogList ({ commit, dispatch, state }, data = {}) {
      data.requestLoading = 'plat/auditLog/listLoading'
      const res = await PLAT_AUDIT_LOG_SEARCH({ ...data, ...state.searchCondition })
      commit('setLogList', res)
    },
    async exportLog ({ commit, dispatch, state }, data = {}) {
      data.requestLoading = 'plat/auditLog/exportLoading'
      await PLAT_AUDIT_LOG_SEARCH_EXPORT(data)
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
    },
    setLevel (state, value) {
      state.searchCondition.level = value
    },
    setContext (state, value) {
      state.searchCondition.context = value
    },
    setModule (state, value) {
      state.searchCondition.module = value
    },
    setSip (state, value) {
      state.searchCondition.sip = value
    },
    setDip (state, value) {
      state.searchCondition.dip = value
    },
    toggleCondition (state) {
      state.isOpenCondition = !state.isOpenCondition
    },
    setSortField (state, value) {
      state.searchCondition.sortField = value.sortField
      state.searchCondition.sortOrder = value.sortOrder
    },
    exportLoading (state, loading) {
      state.exportLoading = loading
    }
  }
}
