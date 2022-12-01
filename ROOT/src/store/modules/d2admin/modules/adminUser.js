import { ADMIN_USER_ADD, ADMIN_USER_DELETE, ADMIN_USER_EDIT, ADMIN_USER_LIST } from '@/api/sys.user.js'

export default {
  namespaced: true,
  state: {
    userList: [],
    userListCount: 0,
    loading: false,
    roles: []
  },
  actions: {

    /**
     * 获取用户列表
     * @param commit
     * @param dispatch
     * @param data
     * @returns {Promise<void>}
     */
    async list ({ commit, dispatch }, data) {
      data.requestLoading = 'd2admin/adminUser/loading'
      const res = await ADMIN_USER_LIST(data)
      commit('setUserList', res)

      // 存储用户密码规则
      const loginPasswordRule = {}
      if (res.passLowercase) {
        loginPasswordRule.passLowercase = res.passLowercase
      }
      if (res.passUppercase) {
        loginPasswordRule.passUppercase = res.passUppercase
      }
      if (res.passSpecialChar) {
        loginPasswordRule.passSpecialChar = res.passSpecialChar
      }
      if (res.passNumber) {
        loginPasswordRule.passNumber = res.passNumber
      }
      if (res.passNumber) {
        loginPasswordRule.passLength = res.passLength
      }
      localStorage.setItem('loginPasswordRule', JSON.stringify(loginPasswordRule))
    },
    async addUser ({ commit, dispatch }, data) {
      return await ADMIN_USER_ADD(data)
    },
    async editUser ({ commit, dispatch }, data) {
      return await ADMIN_USER_EDIT(data)
    },
    async deleteUser ({ commit, dispatch }, data) {
      return await ADMIN_USER_DELETE(data)
    }
  },
  mutations: {

    setUserList (state, payload) {
      state.userList = payload.list
      state.userListCount = payload.count
      if (payload.roles) {
        state.roles = payload.roles
      }
    },

    loading (state, loading) {
      state.loading = loading
    }

  }
}
