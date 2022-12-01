import {
  FIREWALL_STRATEGY_GROUP_ADD,
  FIREWALL_STRATEGY_GROUP_DELETE,
  FIREWALL_STRATEGY_GROUP_EDIT,
  FIREWALL_STRATEGY_GROUP_SEARCH
} from '@/api/firewall.strategy'

export default {
  namespaced: true,
  state: {
    groupList: [],
    groupCount: 0,
    assetList: [],
    deviceList: [],
    device: {},
    listLoading: false,
    addLoading: false,
    deleteLoading: false,
    pagination: {
      currentPage: 1,
      pageSize: 10
    }
  },
  actions: {
    async getGroupList ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'firewall/strategyGroup/listLoading'
      const res = await FIREWALL_STRATEGY_GROUP_SEARCH(data)
      commit('setGroupList', res)
    },
    async addGroup ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'firewall/strategyGroup/addLoading'
      await FIREWALL_STRATEGY_GROUP_ADD(data)
    },
    async editGroup ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'firewall/strategyGroup/addLoading'
      await FIREWALL_STRATEGY_GROUP_EDIT(data)
    },
    async deleteGroup ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'firewall/strategyGroup/deleteLoading'
      await FIREWALL_STRATEGY_GROUP_DELETE(data)
    }
  },
  mutations: {
    setGroupList (state, payload) {
      state.groupList = payload.groups
      state.assetList = payload.assets
      state.deviceList = payload.deviceArray
      state.device = payload.device
      state.groupCount = payload.groupCount
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
