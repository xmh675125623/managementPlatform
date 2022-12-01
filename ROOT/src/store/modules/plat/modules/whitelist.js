import { PLAT_WHITELIST_ADD, PLAT_WHITELIST_DELETE, PLAT_WHITELIST_SEARCH } from '@/api/plat.whitelist'

export default {
  namespaced: true,
  state: {
    whitelistList: [],
    listLoading: false,
    addLoading: false,
    deleteLoading: false
  },
  actions: {
    async getWhitelistList ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/whitelist/listLoading'
      const res = await PLAT_WHITELIST_SEARCH(data)
      commit('setWhitelistList', res)
    },
    async addWhitelist ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/whitelist/addLoading'
      await PLAT_WHITELIST_ADD(data)
    },
    async deleteWhitelist ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/whitelist/deleteLoading'
      await PLAT_WHITELIST_DELETE(data)
    }
  },
  mutations: {
    setWhitelistList (state, payload) {
      state.whitelistList = payload.list
    },
    listLoading (state, loading) {
      state.listLoading = loading
    },
    addLoading (state, loading) {
      state.addLoading = loading
    },
    deleteLoading (state, loading) {
      state.deleteLoading = loading
    }
  }
}
