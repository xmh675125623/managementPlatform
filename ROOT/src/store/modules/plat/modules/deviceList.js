import { PLAT_DEVICE_ADD, PLAT_DEVICE_LIST, PLAT_DEVICE_REMOVE, PLAT_DEVICE_UPDATE } from '@/api/plat.device'

export default {
  namespaced: true,
  state: {
    deviceList: [],
    deviceCount: 0,
    currentPage: 1,
    pageSize: 10,
    listLoading: false,
    updateLoading: false
  },
  actions: {
    async getDeivceList ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/deviceList/listLoading'
      const res = await PLAT_DEVICE_LIST(data)
      commit('setDeviceList', res)
    },
    async updateDevice ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/deviceList/updateLoading'
      await PLAT_DEVICE_UPDATE(data)
    },
    async addDevice ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/deviceList/updateLoading'
      await PLAT_DEVICE_ADD(data)
    },
    async removeDevice ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/deviceList/listLoading'
      await PLAT_DEVICE_REMOVE(data)
    },
  },
  mutations: {
    setDeviceList (state, payload) {
      state.deviceList = payload.list
      state.deviceCount = payload.count
      state.currentPage = payload.page
      state.pageSize = payload.pageSize
    },
    listLoading (state, loading) {
      state.listLoading = loading
    },
    updateLoading (state, loading) {
      state.updateLoading = loading
    }
  }
}
