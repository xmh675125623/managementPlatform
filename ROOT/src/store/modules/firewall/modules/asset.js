import {
  FIREWALL_RULE_ASSET_ADD,
  FIREWALL_RULE_ASSET_DELETE,
  FIREWALL_RULE_ASSET_SEARCH,
  FIREWALL_RULE_ASSET_UPDATE
} from '@/api/firewall.strategy'

export default {
  namespaced: true,
  state: {
    assetList: [],
    listLoading: false,
    addLoading: false,
    deleteLoading: false
  },
  actions: {
    async getAssetList ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'firewall/asset/listLoading'
      const res = await FIREWALL_RULE_ASSET_SEARCH(data)
      commit('setAssetList', res)
    },
    async addAsset ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'firewall/asset/addLoading'
      await FIREWALL_RULE_ASSET_ADD(data)
    },
    async editAsset ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'firewall/asset/addLoading'
      await FIREWALL_RULE_ASSET_UPDATE(data)
    },
    async deleteAsset ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'firewall/asset/deleteLoading'
      await FIREWALL_RULE_ASSET_DELETE(data)
    }
  },
  mutations: {
    setAssetList (state, payload) {
      state.assetList = payload.assets
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
