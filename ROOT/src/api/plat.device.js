import { request } from '@/api/_service.js'

/**
 * 设备添加
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_DEVICE_ADD (data = {}) {
  data.method = 'plat.device.add'
  return request({
    url: '/function/plat_device.do',
    method: 'POST',
    data
  })
}

/**
 * 设备列表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_DEVICE_LIST (data = {}) {
  data.method = 'plat.device.list'
  return request({
    url: '/function/plat_device.do',
    method: 'POST',
    data
  })
}

/**
 * 设备编辑
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_DEVICE_UPDATE (data = {}) {
  data.method = 'plat.device.edit'
  return request({
    url: '/function/plat_device.do',
    method: 'POST',
    data
  })
}

/**
 * 设备移除
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_DEVICE_REMOVE (data = {}) {
  data.method = 'plat.device.remove'
  return request({
    url: '/function/plat_device.do',
    method: 'POST',
    data
  })
}
