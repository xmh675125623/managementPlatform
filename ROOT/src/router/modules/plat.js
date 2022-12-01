import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

const meta = { auth: true }

export default {
  path: '/plat',
  name: 'plat',
  meta,
  redirect: { name: 'plat-user-role-user' },
  component: layoutHeaderAside,
  children: [
    { path: 'user_role/user', name: 'plat-user-role-user', component: _import('system/user_role/adminUser.vue'), meta: { ...meta, title: '用户管理' } },
    { path: 'user_role/role', name: 'plat-user-role-role', component: _import('system/user_role/role.vue'), meta: { ...meta, title: '角色管理' } },
    { path: 'setting/system_setting', name: 'plat-setting-system-setting', component: _import('system/setting/setting.vue'), meta: { ...meta, title: '系统设置' } },
    { path: 'setting/ip_whitelist', name: 'plat-setting-ip-whitelist', component: _import('system/whitelist/whitelist.vue'), meta: { ...meta, title: '地址白名单' } },
    { path: 'setting/user_center', name: 'plat-setting-user-center', component: _import('system/user_role/userCenter.vue'), meta: { ...meta, title: '用户中心' } },
    { path: 'log/operate_log_search', name: 'plat-log-operate-log-search', component: _import('system/log/operateLog.vue'), meta: { ...meta, title: '操作日志查看' } },
    { path: 'log/operate_log_manage', name: 'plat-log-operate-log-manage', component: _import('system/log/operateLogManage.vue'), meta: { ...meta, title: '操作日志管理' } }
  ]
}
