import { request } from '@/api/_service.js'

/**
 * 防火墙设备探索
 * @param data
 * @returns {*}
 * @constructor
 */
export function FIREWALL_DEVICE_SNIFF (data = {}) {
  data.method = 'firewall.device.sniff'
  return request({
    url: '/function/firewall_device.do',
    method: 'POST',
    data
  })
}

/**
 * 防火墙设备添加
 * @param data
 * @returns {*}
 * @constructor
 */
export function FIREWALL_DEVICE_ADD (data = {}) {
  data.method = 'firewall.device.add'
  return request({
    url: '/function/firewall_device.do',
    method: 'POST',
    data
  })
}

/**
 * 防火墙设备列表
 * @param data
 * @returns {*}
 * @constructor
 */
export function FIREWALL_DEVICE_LIST (data = {}) {
  data.method = 'firewall.device.list'
  return request({
    url: '/function/firewall_device.do',
    method: 'POST',
    data
  })
}

/**
 * 防火墙设备编辑
 * @param data
 * @returns {*}
 * @constructor
 */
export function FIREWALL_DEVICE_UPDATE (data = {}) {
  data.method = 'firewall.device.update'
  return request({
    url: '/function/firewall_device.do',
    method: 'POST',
    data
  })
}
