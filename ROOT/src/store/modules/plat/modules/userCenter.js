import { USER_CENTER_EDIT, USER_CENTER_SEARCH } from '@/api/sys.user'

export default {
  namespaced: true,
  state: {
    userInfo: {},
    editLoading: false
  },
  actions: {
    async getUserInfo ({ commit, dispatch }, data = {}) {
      const res = await USER_CENTER_SEARCH(data)
      commit('setUserInfo', res)
    },
    async editUserInfo ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/userCenter/editLoading'
      await USER_CENTER_EDIT(data)
    }
  },
  mutations: {
    setUserInfo (state, payload) {
      state.userInfo = payload.user_info
      const loginPasswordRule = {}
      if (payload.passLowercase) {
        loginPasswordRule.passLowercase = payload.passLowercase
      }
      if (payload.passUppercase) {
        loginPasswordRule.passUppercase = payload.passUppercase
      }
      if (payload.passSpecialChar) {
        loginPasswordRule.passSpecialChar = payload.passSpecialChar
      }
      if (payload.passNumber) {
        loginPasswordRule.passNumber = payload.passNumber
      }
      if (payload.passNumber) {
        loginPasswordRule.passLength = payload.passLength
      }
      localStorage.setItem('loginPasswordRule', JSON.stringify(loginPasswordRule))
    },
    editLoading (state, loading) {
      state.editLoading = loading
    }
  }
}
