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
