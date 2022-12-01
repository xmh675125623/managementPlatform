import { find, assign } from 'lodash'
import faker from 'faker/locale/zh_CN'
import { service, serviceForMock, request, requestForMock, mock } from '@/api/_service.js'
import * as tools from '@/api/_tools.js'

const users = [
  { username: 'admin', password: 'admin', uuid: 'admin-uuid', name: 'Admin' },
  { username: 'editor', password: 'editor', uuid: 'editor-uuid', name: 'Editor' },
  { username: 'user1', password: 'user1', uuid: 'user1-uuid', name: 'User1' }
]

/**
 * @description 登录
 * @param {Object} data 登录携带的信息
 */
export function SYS_USER_LOGIN (data = {}) {
  // 模拟数据
  // mock
  //   .onAny('/login')
  //   .reply(config => {
  //     const user = find(users, tools.parse(config.data))
  //     return user
  //       ? tools.responseSuccess(assign({}, user, { token: faker.random.uuid() }))
  //       : tools.responseError({}, '账号或密码不正确')
  //   })
  // 接口请求
  // return requestForMock({
  //   url: '/login',
  //   method: 'post',
  //   data
  // })
  data.login_token = localStorage.getItem('login_token')
  data.account = data.username
  return request({
    url: '/admin_login/login.do',
    method: 'POST',
    data
  })
}

/**
 * 获取用户列表
 * @param data
 * @returns {*}
 * @constructor
 */
export function ADMIN_USER_LIST (data = {}) {
  data.method = 'plat.user.search'
  return request({
    url: '/function/plat_admin_user.do',
    method: 'POST',
    data
  })
}

export function ADMIN_USER_ADD (data = {}) {
  data.method = 'plat.user.add'
  return request({
    url: '/function/plat_admin_user.do',
    method: 'POST',
    data
  })
}

export function ADMIN_USER_EDIT (data = {}) {
  data.method = 'plat.user.edit'
  return request({
    url: '/function/plat_admin_user.do',
    method: 'POST',
    data
  })
}

export function ADMIN_USER_DELETE (data = {}) {
  data.method = 'plat.user.delete'
  return request({
    url: '/function/plat_admin_user.do',
    method: 'POST',
    data
  })
}

export function USER_CENTER_SEARCH (data = {}) {
  data.method = 'plat.user_center.search'
  return request({
    url: '/function/admin_user_center.do',
    method: 'POST',
    data
  })
}

export function USER_CENTER_EDIT (data = {}) {
  data.method = 'plat.user_center.edit'
  return request({
    url: '/function/admin_user_center.do',
    method: 'POST',
    data
  })
}
