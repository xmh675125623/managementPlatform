import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

const meta = { auth: true }

export default {
  path: '/log_manage',
  name: 'log_manage',
  meta,
  redirect: { name: 'log-manage-audit' },
  component: layoutHeaderAside,
  children: [
    { path: 'audit', name: 'log-manage-audit', component: _import('system/logManage/auditLogManage.vue'), meta: { ...meta, title: '审计日志管理' } },
    { path: 'firewall', name: 'log-manage-firewall', component: _import('system/logManage/firewallLogManage.vue'), meta: { ...meta, title: '防火墙日志管理' } },
    { path: 'isolation', name: 'log-manage-isolation', component: _import('system/logManage/isolationLogManage.vue'), meta: { ...meta, title: '隔离日志管理' } }
  ]
}
