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

/**
 * 查询隔离日志
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_ISOLATION_LOG_SEARCH (data = {}) {
  data.method = 'log.isolation.search'
  return request({
    url: '/function/logs.do',
    method: 'POST',
    data
  })
}

/**
 * 审计日志表列表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_AUDIT_LOG_TABLE_SEARCH (data = {}) {
  data.method = 'plat.disk_audit.search'
  return request({
    url: '/function/disk_space.do',
    method: 'POST',
    data
  })
}

/**
 * 删除审计日志表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_AUDIT_LOG_TABLE_DELETE (data = {}) {
  data.method = 'plat.disk_audit.delete'
  return request({
    url: '/function/disk_space.do',
    method: 'POST',
    data
  })
}

/**
 * 导出审计日志表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_AUDIT_LOG_TABLE_EXPORT (data = {}) {
  data.method = 'plat.disk_audit.export'
  return request({
    url: '/function/disk_export.do',
    method: 'POST',
    data
  })
}

/**
 * 防火墙日志表列表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_FIREWALL_LOG_TABLE_SEARCH (data = {}) {
  data.method = 'plat.disk_firewall.search'
  return request({
    url: '/function/disk_space.do',
    method: 'POST',
    data
  })
}

/**
 * 删除防火墙日志表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_FIREWALL_LOG_TABLE_DELETE (data = {}) {
  data.method = 'plat.disk_firewall.delete'
  return request({
    url: '/function/disk_space.do',
    method: 'POST',
    data
  })
}

/**
 * 导出防火墙日志表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_FIREWALL_LOG_TABLE_EXPORT (data = {}) {
  data.method = 'plat.disk_firewall.export'
  return request({
    url: '/function/disk_export.do',
    method: 'POST',
    data
  })
}

/**
 * 隔离日志表列表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_ISOLATION_LOG_TABLE_SEARCH (data = {}) {
  data.method = 'plat.disk_isolation.search'
  return request({
    url: '/function/disk_space.do',
    method: 'POST',
    data
  })
}

/**
 * 删除隔离日志表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_ISOLATION_LOG_TABLE_DELETE (data = {}) {
  data.method = 'plat.disk_isolation.delete'
  return request({
    url: '/function/disk_space.do',
    method: 'POST',
    data
  })
}

/**
 * 导出隔离日志表
 * @param data
 * @returns {*}
 * @constructor
 */
export function PLAT_ISOLATION_LOG_TABLE_EXPORT (data = {}) {
  data.method = 'plat.disk_isolation.export'
  return request({
    url: '/function/disk_export.do',
    method: 'POST',
    data
  })
}
