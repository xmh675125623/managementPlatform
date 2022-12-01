import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

const meta = { auth: true }

export default {
  path: '/display',
  name: 'display',
  meta,
  redirect: { name: 'display-index' },
  component: layoutHeaderAside,
  children: [
    { path: 'index', name: 'display-index', component: _import('display/index.vue'), meta: { ...meta, title: 'Kibana首页', keepAlive: true } },
    { path: 'dashboards', name: 'display-dashboards', component: _import('display/dashboards.vue'), meta: { ...meta, title: 'dashboards' } },
    { path: 'device_status', name: 'device_status', component: _import('display/deviceStatus.vue'), meta: { ...meta, title: '设备状态' } }
  ]
}
