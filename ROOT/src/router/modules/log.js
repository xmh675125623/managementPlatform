import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

const meta = { auth: true }

export default {
  path: '/log',
  name: 'log',
  meta,
  redirect: { name: 'log-firewall-search' },
  component: layoutHeaderAside,
  children: [
    { path: 'audit', name: 'log-audit-search', component: _import('system/log/auditLog.vue'), meta: { ...meta, title: '审计日志查看' } },
    { path: 'firewall', name: 'log-firewall-search', component: _import('system/log/firewallLog.vue'), meta: { ...meta, title: '防火墙日志查看' } },
    { path: 'isolation', name: 'log-isolation-search', component: _import('system/log/isolationLog.vue'), meta: { ...meta, title: '隔离日志查看' } },
    { path: 'ids', name: 'log-ids-search', component: _import('system/log/idsLog.vue'), meta: { ...meta, title: 'IDS日志查看' } },
    { path: 'gateway', name: 'log-gateway-search', component: _import('system/log/gatewayLog.vue'), meta: { ...meta, title: '网关日志查看' } }
  ]
}
