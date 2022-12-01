import {
  FIREWALL_DEVICE_LIST,
  FIREWALL_DEVICE_UPDATE
} from '@/api/firewall.device'

export default {
  namespaced: true,
  state: {
    deviceList: [],
    listLoading: false,
    updateLoading: false
  },
  actions: {
    async getDeivceList ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'firewall/deviceList/listLoading'
      const res = await FIREWALL_DEVICE_LIST(data)
      commit('setDeviceList', res)
    },
    async updateDevice ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'firewall/deviceList/updateLoading'
      await FIREWALL_DEVICE_UPDATE(data)
    }
  },
  mutations: {
    setDeviceList (state, payload) {
      state.deviceList = payload.deviceArray
    },
    listLoading (state, loading) {
      state.listLoading = loading
    },
    updateLoading (state, loading) {
      state.updateLoading = loading
    }
  }
}
