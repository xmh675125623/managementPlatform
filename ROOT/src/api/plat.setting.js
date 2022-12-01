import { request } from '@/api/_service.js'

/**
 * 获取设置列表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_SETTING_SEARCH (data = {}) {
  data.method = 'plat.setting.search'
  return request({
    url: '/function/syssetting.do',
    method: 'POST',
    data
  })
}

/**
 * 编辑设置
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_SETTING_EDIT (data = {}) {
  data.method = 'plat.setting.edit'
  return request({
    url: '/function/syssetting.do',
    method: 'POST',
    data
  })
}
