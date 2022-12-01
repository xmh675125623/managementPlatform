import { request } from '@/api/_service.js'

/**
 * 获取角色列表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_ROLE_SEARCH (data = {}) {
  data.method = 'plat.role.search'
  return request({
    url: '/function/role.do',
    method: 'POST',
    data
  })
}

/**
 * 添加角色
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_ROLE_ADD (data = {}) {
  data.method = 'plat.role.add'
  return request({
    url: '/function/role.do',
    method: 'POST',
    data
  })
}

/**
 * 编辑角色
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_ROLE_EDIT (data = {}) {
  data.method = 'plat.role.edit'
  return request({
    url: '/function/role.do',
    method: 'POST',
    data
  })
}

/**
 * 删除角色
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_ROLE_DELETE (data = {}) {
  data.method = 'plat.role.delete'
  return request({
    url: '/function/role.do',
    method: 'POST',
    data
  })
}
