import { PLAT_ROLE_ADD, PLAT_ROLE_DELETE, PLAT_ROLE_EDIT, PLAT_ROLE_SEARCH } from '@/api/plat.role'

export default {
  namespaced: true,
  state: {
    roleList: [],
    listLoading: false,
    roleListCount: 0,
    addRoleLoading: false,
    deleteRoleLoading: false,
    functionList: [],
    treeLoading: false,
    treeSelectedId: [],
    treeExpandId: []
  },
  actions: {
    async getRoleList ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/role/listLoading'
      const res = await PLAT_ROLE_SEARCH(data)
      commit('setRoleList', res)
    },
    async addRole ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/role/addRoleLoading'
      await PLAT_ROLE_ADD(data)
    },
    async editRole ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/role/addRoleLoading'
      await PLAT_ROLE_EDIT(data)
    },
    async deleteRole ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/role/deleteRoleLoading'
      await PLAT_ROLE_DELETE(data)
    },
    async getAuthTree ({ commit, dispatch }, data = {}) {
      data.requestLoading = 'plat/role/treeLoading'
      const res = await PLAT_ROLE_EDIT(data)
      commit('setFunctionList', res)
    }
  },
  mutations: {
    setRoleList (state, payload) {
      state.roleList = payload.roles
    },
    setFunctionList (state, payload) {
      state.functionList = payload.functions
      let expandId = payload.expand_id.split(',')
      if (!expandId) {
        expandId = []
      } else {
        expandId.pop()
      }
      expandId.forEach((item, i) => {
        expandId[i] = item * 1
      })
      const checkedKeys = payload.selected_id.split(',')
      checkedKeys.pop()
      state.treeSelectedId = checkedKeys
      state.treeExpandId = expandId
    },
    listLoading (state, loading) {
      state.listLoading = loading
    },
    addRoleLoading (state, loading) {
      state.addRoleLoading = loading
    },
    deleteRoleLoading (state, loading) {
      state.deleteRoleLoading = loading
    },
    treeLoading (state, loading) {
      state.treeLoading = loading
    }
  }
}
