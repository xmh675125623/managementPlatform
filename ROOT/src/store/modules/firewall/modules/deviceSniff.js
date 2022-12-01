import { FIREWALL_DEVICE_ADD, FIREWALL_DEVICE_SNIFF } from '@/api/firewall.device'

export default {
  namespaced: true,
  state: {
    deviceList: [],
    listLoading: false,
    addLoading: false
  },
  actions: {
    async sniffDevice ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'firewall/deviceSniff/listLoading'
      const res = await FIREWALL_DEVICE_SNIFF(data)
      commit('setDeviceList', res)
    },
    async addDevice ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'firewall/deviceSniff/addLoading'
      await FIREWALL_DEVICE_ADD(data)
    }
  },
  mutations: {
    setDeviceList (state, payload) {
      state.deviceList = payload.devices
    },
    listLoading (state, loading) {
      state.listLoading = loading
    },
    addLoading (state, loading) {
      state.addLoading = loading
    }
  }
}
