import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

const meta = { auth: true }

export default {
  path: '/device',
  name: 'device',
  meta,
  redirect: { name: 'plat-user-role-user' },
  component: layoutHeaderAside,
  children: [
    { path: 'device/list', name: 'plat-device-list', component: _import('system/device/deviceList.vue'), meta: { ...meta, title: '设备管理' } }
  ]
}
