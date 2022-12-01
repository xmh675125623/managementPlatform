import { PLAT_SETTING_EDIT, PLAT_SETTING_SEARCH } from '@/api/plat.setting'

export default {
  namespaced: true,
  state: {
    settingList: [],
    listLoading: false,
    editSettingLoading: false
  },
  actions: {
    async getSettingList ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/setting/listLoading'
      const res = await PLAT_SETTING_SEARCH(data)
      commit('setSettingList', res)
    },
    async editSetting ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/setting/editSettingLoading'
      await PLAT_SETTING_EDIT(data)
    }
  },
  mutations: {
    setSettingList (state, payload) {
      state.settingList = payload.list
    },
    listLoading (state, loading) {
      state.listLoading = loading
    },
    editSettingLoading (state, loading) {
      state.editSettingLoading = loading
    }
  }
}
