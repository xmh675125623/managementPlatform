import { request } from '@/api/_service.js'

/**
 * 查询操作日志
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_OPERATE_LOG_SEARCH (data = {}) {
  data.method = 'plat.operate_log.search'
  return request({
    url: '/function/logs.do',
    method: 'POST',
    data
  })
}

/**
 * 操作日志表列表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_OPERATE_LOG_TABLE_SEARCH (data = {}) {
  data.method = 'plat.disk_operate.search'
  return request({
    url: '/function/disk_space.do',
    method: 'POST',
    data
  })
}

/**
 * 删除操作日志表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_OPERATE_LOG_TABLE_DELETE (data = {}) {
  data.method = 'plat.disk_operate.delete'
  return request({
    url: '/function/disk_space.do',
    method: 'POST',
    data
  })
}

/**
 * 导出操作日志表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_OPERATE_LOG_TABLE_EXPORT (data = {}) {
  data.method = 'plat.disk_operate.export'
  return request({
    url: '/function/disk_export.do',
    method: 'POST',
    data
  })
}

/**
 * 查询审计日志
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_AUDIT_LOG_SEARCH (data = {}) {
  data.method = 'log.audit.search'
  return request({
    url: '/function/logs.do',
    method: 'POST',
    data
  })
}

/**
 * 导出审计选择的日志
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_AUDIT_LOG_SEARCH_EXPORT (data = {}) {
  data.method = 'log.audit.search_export'
  return request({
    url: '/function/logs.do',
    method: 'POST',
    data
  })
}

/**
 * 查询防火墙日志
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_FIREWALL_LOG_SEARCH (data = {}) {
  data.method = 'log.firewall.search'
  return request({
    url: '/function/logs.do',
    method: 'POST',
    data
  })
}

/**
 * 导出防火墙选择的日志
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_FIREWALL_LOG_SEARCH_EXPORT (data = {}) {
  data.method = 'log.firewall.search_export'
  return request({
    url: '/function/logs.do',
    method: 'POST',
    data
  })
}
