import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

const meta = { auth: true }

export default {
  path: '/firewall',
  name: 'firewall',
  meta,
  redirect: { name: 'firewall-device-sniff' },
  component: layoutHeaderAside,
  children: [
    { path: 'device/sniff', name: 'firewall-device-sniff', component: _import('firewall/deviceSniff.vue'), meta: { ...meta, title: '防火墙设备探索' } },
    { path: 'device/list', name: 'firewall-device-list', component: _import('firewall/deviceList.vue'), meta: { ...meta, title: '防火墙设备列表' } },
    { path: 'rule/asset', name: 'firewall-rule-asset', component: _import('firewall/asset.vue'), meta: { ...meta, title: '防火墙资产管理' } },
    { path: 'rule/strategy_group', name: 'firewall-strategy-group', component: _import('firewall/strategyGroup.vue'), meta: { ...meta, title: '防火墙策略分组' } },
    { path: 'rule/strategy', name: 'firewall-strategy', component: _import('firewall/strategy.vue'), meta: { ...meta, title: '防火墙策略管理' } }
  ]
}
