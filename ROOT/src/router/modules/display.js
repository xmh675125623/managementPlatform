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
    { path: 'index', name: 'display-index', component: _import('display/index.vue'), meta: { ...meta, title: '大数据分析', keepAlive: true } },
    { path: 'dashboards', name: 'display-dashboards', component: _import('display/dashboards.vue'), meta: { ...meta, title: '协议分析' } },
    { path: 'device_status', name: 'device_status', component: _import('display/deviceStatus.vue'), meta: { ...meta, title: '工业互联网协议分析' } },
    { path: 'threat_alarm', name: 'threat_alarm', component: _import('display/threatAlarm.vue'), meta: { ...meta, title: '威胁及告警' } },
    { path: 'abnormal_events', name: 'abnormal_events', component: _import('display/abnormalEvents.vue'), meta: { ...meta, title: '网络异常事件' } },
    { path: 'data_flow_analysis', name: 'data_flow_analysis', component: _import('display/dataFlowAnalysis.vue'), meta: { ...meta, title: '数据流分析' } },
    { path: 'data_flow_chart', name: 'data_flow_chart', component: _import('display/dataFlowChart.vue'), meta: { ...meta, title: '数据流量图' } },
    { path: 'flow_statistics', name: 'flow_statistics', component: _import('display/flowStatistics.vue'), meta: { ...meta, title: '流量统计' } },
    { path: 'network_connections', name: 'network_connections', component: _import('display/networkConnections.vue'), meta: { ...meta, title: '网络连接' } },
    { path: 'network_service_action_response', name: 'network_service_action_response', component: _import('display/networkServiceActionResponse.vue'), meta: { ...meta, title: '网络服务-动作/响应' } }
  ]
}
