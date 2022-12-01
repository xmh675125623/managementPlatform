import { request } from '@/api/_service.js'

/**
 * IP白名单列表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_WHITELIST_SEARCH (data = {}) {
  data.method = 'plat.whitelist.search'
  return request({
    url: '/function/ip_whitelist.do',
    method: 'POST',
    data
  })
}

/**
 * 添加IP白名单
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_WHITELIST_ADD (data = {}) {
  data.method = 'plat.whitelist.add'
  return request({
    url: '/function/ip_whitelist.do',
    method: 'POST',
    data
  })
}

/**
 * IP白名单删除
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_WHITELIST_DELETE (data = {}) {
  data.method = 'plat.whitelist.delete'
  return request({
    url: '/function/ip_whitelist.do',
    method: 'POST',
    data
  })
}
