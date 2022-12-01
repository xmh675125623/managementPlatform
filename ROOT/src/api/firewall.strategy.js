import { request } from '@/api/_service.js'

/**
 * 防火墙资产查询
 * @param data
 * @returns {*}
 * @constructor
 */
export function FIREWALL_RULE_ASSET_SEARCH (data = {}) {
  data.method = 'firewall.rule_asset.search'
  return request({
    url: '/function/firewall_asset.do',
    method: 'POST',
    data
  })
}

/**
 * 防火墙资产添加
 * @param data
 * @returns {*}
 * @constructor
 */
export function FIREWALL_RULE_ASSET_ADD (data = {}) {
  data.method = 'firewall.rule_asset.add'
  return request({
    url: '/function/firewall_asset.do',
    method: 'POST',
    data
  })
}

/**
 * 防火墙资产编辑
 * @param data
 * @returns {*}
 * @constructor
 */
export function FIREWALL_RULE_ASSET_UPDATE (data = {}) {
  data.method = 'firewall.rule_asset.edit'
  return request({
    url: '/function/firewall_asset.do',
    method: 'POST',
    data
  })
}

/**
 * 防火墙资产删除
 * @param data
 * @returns {*}
 * @constructor
 */
export function FIREWALL_RULE_ASSET_DELETE (data = {}) {
  data.method = 'firewall.rule_asset.delete'
  return request({
    url: '/function/firewall_asset.do',
    method: 'POST',
    data
  })
}

/**
 * 策略分组查询
 * @param data
 * @returns {*}
 * @constructor
 */
export function FIREWALL_STRATEGY_GROUP_SEARCH (data = {}) {
  data.method = 'firewall.rule_strategy_group.search'
  return request({
    url: '/function/firewall_strategy_group.do',
    method: 'POST',
    data
  })
}

/**
 * 策略分组添加
 * @param data
 * @returns {*}
 * @constructor
 */
export function FIREWALL_STRATEGY_GROUP_ADD (data = {}) {
  data.method = 'firewall.rule_strategy_group.add'
  return request({
    url: '/function/firewall_strategy_group.do',
    method: 'POST',
    data
  })
}

/**
 * 策略分组修改
 * @param data
 * @returns {*}
 * @constructor
 */
export function FIREWALL_STRATEGY_GROUP_EDIT (data = {}) {
  data.method = 'firewall.rule_strategy_group.edit'
  return request({
    url: '/function/firewall_strategy_group.do',
    method: 'POST',
    data
  })
}

/**
 * 策略分组删除
 * @param data
 * @returns {*}
 * @constructor
 */
export function FIREWALL_STRATEGY_GROUP_DELETE (data = {}) {
  data.method = 'firewall.rule_strategy_group.delete'
  return request({
    url: '/function/firewall_strategy_group.do',
    method: 'POST',
    data
  })
}
