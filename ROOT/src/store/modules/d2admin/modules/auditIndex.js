import {
  CLIENT_IP_DATA_GET,
  DATE_HISTOGRAM_DATA_GET,
  EVENT_SEVERITY_DATA_GET, EXCEPTION_EVENT_DATA_GET, EXTERNAL_ATTACKER_DATA_GET,
  NETWORK_PROTOCOL_DATA_GET, REAL_TIME_ALARM_GET
} from '@/api/plat.index'
import echarts from 'echarts'

const backgroundColor_ = 'rgba(0,0,0,0)'
const networkProtocolColor = {
  dns: 'rgb(87, 193, 123)',
  http: 'rgb(111, 135, 216)',
  tls: 'rgb(102, 61, 184)',
  ssl: 'rgb(188, 82, 188)',
  stun: 'rgb(158, 53, 51)',
  ntp: 'rgb(218, 160, 93)',
  'X.509': 'rgb(191, 175, 64)',
  dhcp: 'rgb(64, 175, 191)',
  dtls: 'rgb(64, 80, 191)',
  ayiya: 'rgb(191, 151, 64)',
  imap: 'rgb(112, 191, 64)',
  spicy_stun: 'rgb(191, 80, 64)',
  gquic: 'rgb(191, 64, 167)',
  mqtt: 'rgb(143, 64, 191)',
  quic: 'rgb(64, 191, 88)',
  dce_rpc: 'rgb(64, 104, 191)',
  iso_cotp: 'rgb(191, 64, 72)',
  smtp: 'rgb(64, 191, 183)',
  ssh: 'rgb(135, 191, 64)',
  modbus: 'rgb(120, 64, 191)',
  xmpp: 'rgb(191, 64, 120)'
}
const commonColor = ['#00ffff', '#409B5C', '#006ced', '#ffe000', '#ffa800', '#ff5b00', '#ff3000']

const networkProtocolImg =
  'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAVCAYAAACzK0UYAAACyUlEQVRIiY2VzWoUQRDHf9U7+cIsahIj0ajgwaNi1JNHSRQ3ePMk+AyKd30BQfQFFDzrRWOMJBdBEEUEPQVMiHgQjWA2Jtns7E63dM/MTs/MJqSgpqq7q/9V1VU9LSzNHwPuAdPACPANeAI8AJp4NHbk0r6K4VwkHAcGgPWKYTESvgKRtayY2D6SWLdSWJpbdeACWANxHzt4B1LDUHe7BKpjUzd7NP2U6Rcw21KEgzqLrKViGSCtkRSkI+NoLgLzCJPAmp3fiOjvNRBK3omBwwI1ImY2IOwsRLFQSBvHtPIy5vNIewHVHrJjY6M0OOkzsX7IaKaNpq+4rpCQmFuebJLNhxMQzkM4TALoMvVlxiNoamj6/HmVATYL4Ln5s0hzgfZ61W2MEoCo5MR31O85CQvg210cOT5D4+Vtonp1RydRd0dxJqpZyCTRlcd2TP0ozee3MGGvAzQJcMr+WDNMxFUiApUBbntyOwbG404wa+O0Zy97YN0ySXWb0enAgZpCCztdg6hOj+banJULaF7k+rjQ1p09cDJAGomBJBexCxUBaB1wkXYucAnY3zMYxIVODSTJVfxLmSgqkfaiVNec2d5oI0A1ysdlCtFL4cjapz6kt7l0ZMVsDMvxcaWRlzYZEMl0S/rgd7ZuvNlTJobfCF+8mqSkssI7w7RWCvT+H6zfeUQ0EHZOcedM/gCvbd5xTXax9AL4zN/7T2mNbnbNOk/WwUz6VATIVmFdZVlk9AmYpDl6vVschfFq4iBM15Ka7EofgSn3u9f5BizdH1sDeOUceA0UBDknpSzeA1faUA/s4SYdXKpFTLbIsQO/OzUE49Kw3keVgZ5kVrubHr1VpnJNS1RvUXErKxob0UAHJJM/EeYwiYN8LQmGZHNCGbmLUNNi7Cu5rI08VmIe2l+0fc5sFomTZ8AEcALjnuF/wCLGvfE6l2GqA/8Bnpdx0cUesngAAAAASUVORK5CYII='
const obliqueRectangle = echarts.graphic.extendShape({
  shape: {
    x: 0,
    y: 0,
    width: 0,
    height: 0,
    offset: 10
  },
  buildPath: (ctx, shape) => {
    ctx.moveTo(shape.x + shape.offset, shape.y)
    ctx.lineTo(shape.x + shape.width + shape.offset, shape.y)
    ctx.lineTo(shape.x + shape.width, shape.y + shape.height)
    ctx.lineTo(shape.x, shape.y + shape.height)
    ctx.closePath()
  }
})
echarts.graphic.registerShape('obliqueRectangle', obliqueRectangle)

export default {
  namespaced: true,
  state: {
    // 观测到的IP协议
    networkProtocolOption: {
      backgroundColor: backgroundColor_,
      grid: [
        {
          left: 50,
          top: 0,
          right: 20,
          bottom: 20,
          containLabel: true
        }
      ],
      xAxis: [
        {
          gridIndex: 0,
          type: 'value',
          axisLabel: { show: false },
          axisLine: { show: false },
          splitLine: { show: false },
          axisTick: { show: false }
        },
        {
          gridIndex: 0,
          type: 'value',
          max: 100,
          axisLabel: { show: false },
          axisLine: { show: false },
          splitLine: { show: false },
          axisTick: { show: false }
        }
      ],
      yAxis: [
        {
          gridIndex: 0,
          type: 'category',
          inverse: true,
          data: [],
          axisTick: { show: false },
          axisLine: { show: false },
          splitLine: { show: false },
          axisLabel: {
            width: 80,
            padding: [0, 0, 0, -80],
            align: 'left',
            formatter: (name, index) => {
              const _index = index + 1
              const id = _index > 9 ? _index : `0${_index}`
              return `{icon|}{num|${id}}{value|${name}}`
            },
            rich: {
              icon: {
                width: 14,
                height: 11,
                backgroundColor: {
                  image: networkProtocolImg
                }
              },
              num: {
                padding: [0, 0, 0, 5],
                width: 25,
                color: '#7FE5FF',
                align: 'left',
                fontSize: 15,
                fontFamily: 'Oswald',
                fontWeight: 400,
                fontStyle: 'italic'
              },
              value: {
                // width:
                fontSize: 14,
                color: 'rgba(255,255,255,0.8)',
                fontFamily: 'SourceHanSerifSC-Bold',
                fontWeight: 500
              }
            }
          }
        },
        {
          gridIndex: 0,
          type: 'category',
          inverse: true,
          margin: 20,
          data: [],
          axisTick: { show: false },
          axisLine: { show: false },
          splitLine: { show: false },
          axisLabel: {
            padding: [7, 0, 0, 10],
            formatter: (_, index) => {
            },
            rich: {
              unit: {
                color: '#fff',
                fontSize: 8,
                verticalAlign: 'bottom'
              },
              value: {
                color: '#fff',
                fontSize: 12,
                fontWeight: 500
              }
            }
          }
        }
      ],
      series: [
        {
          type: 'custom',
          xAxisIndex: 1,
          yAxisIndex: 0,
          data: [],
          renderItem: (params, api) => {
            const barLayout = api.barLayout({
              count: 1,
              barWidth: 8
            })
            const { bandWidth, offsetCenter, width } = barLayout[0]
            const x = params.coordSys.x
            const y = bandWidth * (params.dataIndex + 1 / 2) + offsetCenter + params.coordSys.y - width / 2
            return {
              type: 'obliqueRectangle',
              shape: {
                x: x,
                y: y + 2,
                width: api.size([api.value()])[0],
                height: width,
                offset: width * 0.5
              },
              style: api.style()
            }
          },
          itemStyle: {
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 1,
              y2: 0,
              colorStops: [
                {
                  offset: 0,
                  color: '#22324A' // 0% 处的颜色
                },
                {
                  offset: 1,
                  color: '#1C3D57' // 100% 处的颜色
                }
              ],
              global: false // 缺省为 false
            }
          },
          silent: true
        },
        {
          type: 'custom',
          xAxisIndex: 0,
          yAxisIndex: 0,
          data: [],
          silent: true,
          itemStyle: {
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 1,
              y2: 0,
              colorStops: [
                {
                  offset: 0,
                  color: '#2FC7FF' // 0% 处的颜色
                },
                {
                  offset: 1,
                  color: '#73F3FF' // 100% 处的颜色
                }
              ],
              global: false // 缺省为 false
            }
          },
          renderItem: (params, api) => {
            const barLayout = api.barLayout({
              barWidth: 11,
              count: 1
            })
            const { bandWidth, offsetCenter, width } = barLayout[0]
            const x = params.coordSys.x
            const y = bandWidth * (params.dataIndex + 1 / 2) + offsetCenter + params.coordSys.y - width / 2
            return {
              type: 'obliqueRectangle',
              shape: {
                x: x,
                y: y,
                width: api.size([api.value()])[0],
                height: width,
                offset: width * 0.5
              },
              style: api.style()
            }
          }
        },
        {
          type: 'pictorialBar',
          data: [],
          xAxisIndex: 0,
          yAxisIndex: 0,
          symbol: 'path://M15,0L5,20L0,20L10,0Z',
          symbolRotate: null,
          symbolSize: [13, 12],
          symbolPosition: 'end',
          symbolOffset: [10, 0],
          itemStyle: {
            color: 'white',
            shadowColor: 'rgba(115, 243, 255, 0.2)',
            shadowBlur: 10
          }
        }
      ]
    },
    networkProtocolTimer: null,

    // 时间等级分布
    eventSeverityOption: {
      backgroundColor: backgroundColor_,
      color: ['#906BF9', '#eaea26', 'rgb(120, 64, 191)', '#01E17E', '#3DD1F9', '#FFAD05', 'rgb(188, 82, 188)', 'rgb(218, 160, 93)', 'rgb(191, 64, 120)', '#FE5656'],
      grid: {
        left: -100,
        top: 50,
        bottom: 10,
        right: 10,
        containLabel: true
      },
      tooltip: {
        trigger: 'item',
        formatter: '{b} : {c} ({d}%)'
      },

      polar: {},
      angleAxis: {
        interval: 1,
        type: 'category',
        data: [],
        z: 10,
        axisLine: {
          show: false,
          lineStyle: {
            color: '#0B4A6B',
            width: 1,
            type: 'solid'
          }
        },
        axisLabel: {
          interval: 0,
          show: true,
          color: '#0B4A6B',
          margin: 8,
          fontSize: 16
        }
      },
      radiusAxis: {
        min: 40,
        max: 120,
        interval: 20,
        axisLine: {
          show: false,
          lineStyle: {
            color: '#0B3E5E',
            width: 1,
            type: 'solid'
          }
        },
        axisLabel: {
          formatter: '{value} %',
          show: false,
          padding: [0, 0, 20, 0],
          color: '#0B3E5E',
          fontSize: 16
        },
        splitLine: {
          lineStyle: {
            color: '#0B3E5E',
            width: 2,
            type: 'solid'
          }
        }
      },
      calculable: true,
      series: [{
        type: 'pie',
        radius: ['2%', '5%'],
        hoverAnimation: false,
        tooltip: { show: false },
        labelLine: {
          normal: {
            show: false,
            length: 30,
            length2: 55
          },
          emphasis: {
            show: false
          }
        },
        data: [{
          name: '',
          value: 0,
          itemStyle: {
            normal: {
              color: '#0B4A6B'
            }
          }
        }]
      }, {
        type: 'pie',
        radius: ['60%', '65%'],
        hoverAnimation: false,
        tooltip: { show: false },
        labelLine: {
          normal: {
            show: false,
            length: 30,
            length2: 55
          },
          emphasis: {
            show: false
          }
        },
        name: '',
        data: [{
          name: '',
          value: 0,
          itemStyle: {
            normal: {
              color: '#0B4A6B'
            }
          }
        }]
      }, {
        stack: 'a',
        type: 'pie',
        radius: ['10%', '65%'],
        roseType: 'area',
        zlevel: 10,
        label: {
          normal: {
            show: true,
            formatter: '{b}',
            textStyle: {
              fontSize: 12
            },
            position: 'outside'
          },
          emphasis: {
            show: true
          }
        },
        labelLine: {
          normal: {
            show: true,
            length: 10,
            length2: 20
          },
          emphasis: {
            show: false
          }
        },
        data: []
      }]
    },
    eventSeverityTimer: null,

    // 高风险客户端
    clientIpOption: {
      backgroundColor: backgroundColor_,
      color: commonColor,
      title: {
        text: '总告警数',
        subtext: '----',
        top: '40%',
        textAlign: 'center',
        left: '33%',
        textStyle: {
          color: '#fff',
          fontSize: 16,
          fontWeight: '400'
        },
        subtextStyle: {
          color: '#fff',
          fontSize: 14,
          fontWeight: 'normal',
          align: 'center'
        }
      },
      tooltip: {
        show: true
      },
      legend: {
        type: 'scroll',
        icon: 'circle',
        orient: 'vertical',
        // x: 'left',
        right: '8%',
        bottom: '10%',
        align: 'right',
        itemHeight: 10,
        textStyle: {
          color: '#fff'
        },
        itemGap: 10,
        height: '75%'
      },
      toolbox: {
        show: false
      },
      series: [{
        name: '',
        type: 'pie',
        clockWise: false,
        radius: ['71%', '73%'],
        hoverAnimation: false,
        right: '32%',
        itemStyle: {
          normal: {
            label: {
              show: false
            },
            labelLine: {
              show: false
            }
          }
        },
        data: []
      }, {
        name: '',
        type: 'pie',
        clockWise: false,
        radius: ['62%', '64.5%'],
        hoverAnimation: false,
        right: '32%',
        tooltip: {
          show: false
        },
        itemStyle: {
          normal: {
            label: {
              show: false
            },
            labelLine: {
              show: false
            }
          }
        },
        data: [{ value: 20, itemStyle: { color: '#ffe000' } }, { value: 5, itemStyle: { color: 'rgba(0,0,0,0)' } }, { value: 20, itemStyle: { color: '#ffe000' } }, { value: 5, itemStyle: { color: 'rgba(0,0,0,0)' } }, { value: 20, itemStyle: { color: '#ffe000' } }, { value: 5, itemStyle: { color: 'rgba(0,0,0,0)' } }, { value: 20, itemStyle: { color: '#ffe000' } }, { value: 5, itemStyle: { color: 'rgba(0,0,0,0)' } }]
      }]
    },
    clientIpTimer: null,

    // 日志时间趋势
    dateHistogramOption: {
      backgroundColor: backgroundColor_,
      textStyle: {
        color: 'rgba(255,255,255,0.5)'
      },
      tooltip: {
        trigger: 'axis',
        formatter: '{b}{a} {c}条'
      },
      grid: {
        left: '4%',
        right: '5%',
        bottom: '5%',
        top: '10%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          boundaryGap: false,
          data: [],
          axisLine: {
            lineStyle: {
              color: '#4b4d64'
            }
          }
        }
      ],
      yAxis: [
        {
          type: 'value',
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          splitLine: {
            lineStyle: {
              type: 'dashed',
              color: '#4b4d64'
            }
          }
        }
      ],
      series: [
        {
          name: '',
          type: 'line',
          stack: '总量',
          smooth: true,
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(45, 181, 217, 1)'
              }, {
                offset: 0.9,
                color: 'rgba(45, 181, 217, 0.05)'
              }], false),
              shadowColor: 'rgba(0, 0, 0, 0.1)',
              shadowBlur: 10
            }
          },
          itemStyle: {
            normal: {
              color: '#2db5d9',
              borderType: 'solid',
              lineStyle: {
                width: 1
              }
            }
          },
          data: []
        }
      ]
    },
    dateHistogramTimer: null,

    // 外部攻击者
    externalAttackerList: [],
    externalAttackerTimer: null,

    // 实时告警
    realTimeAlarmList: [],
    realTimeAlarmTimer: null,

    // 网络异常事件
    exceptionEventOption: {
      backgroundColor: backgroundColor_,
      title: {
        show: false
      },
      grid: [
        {
          top: '8%',
          bottom: '5%',
          // width: '50%',
          left: '6%',
          right: '12%',
          containLabel: false
        }
      ],
      angleAxis: {
        polarIndex: 0,
        min: 0,
        max: 100,
        show: false,
        boundaryGap: ['60%', '60%'],
        startAngle: 90
      },
      radiusAxis: {
        type: 'category',
        show: true,
        axisLabel: {
          show: false
        },
        axisLine: {
          show: false
        },
        axisTick: {
          show: false
        }
      },
      polar: [
        {
          center: ['20%', '50%'], // 中心点位置
          radius: ['60%', '75%']
        }
      ],
      xAxis: {
        show: false,
        type: 'value'
      },
      yAxis: [
        {
          type: 'category',
          inverse: true,
          axisLabel: {
            show: false,
            textStyle: {
              color: '#fff'
            }
          },
          splitLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          data: []
        },
        {
          type: 'category',
          inverse: true,
          axisTick: 'none',
          axisLine: 'none',
          show: true,
          axisLabel: {
            textStyle: {
              color: '#17caf0',
              fontSize: '12'
            },
            formatter: function (value) {
              return value
            }
          },
          data: []
        }
      ],
      series: [
        {
          name: '完成率',
          type: 'bar',
          zlevel: 1,
          silent: false,
          label: {
            normal: {
              color: 'rgba(255,255,255,0.8)',
              show: true,
              position: [0, '-14px'],
              textStyle: {
                fontSize: 12
              },
              formatter: function (a, b) {
                return a.name
              }
            }
          },
          itemStyle: {
            normal: {
              barBorderRadius: [10, 10, 10, 10],
              color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                {
                  offset: 1,
                  color: 'rgba(0,121,254,0.7)'
                },
                {
                  offset: 0,
                  color: 'rgba(13,213,236,1)'
                }
              ])
            }
          },
          barWidth: 10,
          data: []
        },
        {
          name: '背景',
          type: 'bar',
          barWidth: 10,
          barGap: '-100%',
          data: [],
          itemStyle: {
            normal: {
              color: '#042239',
              barBorderRadius: 30
            }
          }
        }
      ]
    },
    exceptionEventTimer: null
  },
  actions: {
    async networkProtocol ({ state, commit, dispatch }, data = {}) {
      try {
        const res = await NETWORK_PROTOCOL_DATA_GET()
        if (res.data && res.data.aggregations) {
          commit('setNetworkProtocolOption', res.data.aggregations['2'].buckets)
        }
      } catch (e) {
        console.log('协议=============================')
        console.log(e.message)
      }

      const iTimer = setTimeout(function () {
        dispatch('networkProtocol')
      }, 1000)
      commit('setNetworkProtocolTimer', { timer: iTimer })
    },
    async eventSeverity ({ state, commit, dispatch }, data = {}) {
      try {
        const res = await EVENT_SEVERITY_DATA_GET()
        if (res.data && res.data.aggregations) {
          commit('setEventSeverityOption', res.data.aggregations['2'].buckets)
        }
      } catch (e) {
        console.log('事件=============================')
        console.log(e.message)
      }

      const iTimer = setTimeout(function () {
        dispatch('eventSeverity')
      }, 1000)
      commit('setEventSeverityTimer', { timer: iTimer })
    },
    async clientIp ({ state, commit, dispatch }, data = {}) {

      try {
        const res = await CLIENT_IP_DATA_GET()
        if (res.data && res.data.aggregations) {
          commit('setClientIpOption', res.data.aggregations['2'])
        }
      } catch (e) {
        console.log('客户端=============================')
        console.log(e.message)
      }

      const iTimer = setTimeout(function () {
        dispatch('clientIp')
      }, 1000)
      commit('setClientIpTimer', { timer: iTimer })
    },
    async dateHistogram ({ state, commit, dispatch }, data = {}) {

      try {
        const res = await DATE_HISTOGRAM_DATA_GET()
        if (res.data && res.data.aggregations) {
          commit('setDateHistogramOption', res.data.aggregations['2'])
        }
      } catch (e) {
        console.log('日志时间趋势=============================')
        console.log(e.message)
      }

      const iTimer = setTimeout(function () {
        dispatch('dateHistogram')
      }, 1000)
      commit('setDateHistogramTimer', { timer: iTimer })
    },
    async externalAttacker ({ state, commit, dispatch }, data = {}) {

      try {
        const res = await EXTERNAL_ATTACKER_DATA_GET()
        if (res.data && res.data.aggregations) {
          commit('setExternalAttackerList', res.data.aggregations['2'])
        }
      } catch (e) {
        console.log('外部攻击者=============================')
        console.log(e.message)
      }

      const iTimer = setTimeout(function () {
        dispatch('externalAttacker')
      }, 1000)
      commit('setExternalAttackerTimer', { timer: iTimer })
    },
    async realTimeAlarm ({ state, commit, dispatch }, data = {}) {

      try {
        const res = await REAL_TIME_ALARM_GET()
        if (res.data && res.data.hits.hits) {
          commit('setRealTimeAlarmList', res.data.hits.hits)
        }
      } catch (e) {
        console.log('实时告警=============================')
        console.log(e.message)
      }

      const iTimer = setTimeout(function () {
        dispatch('realTimeAlarm')
      }, 1000)
      commit('setRealTimeAlarmTimer', { timer: iTimer })
    },
    async exceptionEvent ({ state, commit, dispatch }, data = {}) {

      try {
        const res = await EXCEPTION_EVENT_DATA_GET()
        if (res.data && res.data.aggregations) {
          commit('setExceptionEventOption', res.data.aggregations['2'])
        }
      } catch (e) {
        console.log('事件2=============================')
        console.log(e.message)
      }

      const iTimer = setTimeout(function () {
        dispatch('exceptionEvent')
      }, 1000)
      commit('setExceptionEventTimer', { timer: iTimer })
    }

  },
  mutations: {
    setNetworkProtocolTimer (state, payload) {
      state.networkProtocolTimer = payload.timer
    },
    setNetworkProtocolOption (state, payload) {
      const data = []
      for (let i = 0; i < payload.length && i < 5; i++) {
        data.push({ name: payload[i].key, value: payload[i].doc_count, unit: '' })
      }
      state.networkProtocolOption.yAxis[0].data = data.map((item) => item.name)
      state.networkProtocolOption.yAxis[1].data = data.map((item) => item.name)
      state.networkProtocolOption.yAxis[1].axisLabel.formatter = (_, index) => {
        return `{value|${data[index].value}}`
      }
      state.networkProtocolOption.series[0].data = data.map(() => 100)
      state.networkProtocolOption.series[1].data = data.map((item) => item.value)
      state.networkProtocolOption.series[2].data = data.map((item) => item.value)
    },
    setEventSeverityOption (state, payload) {
      const data = []
      for (const key in payload) {
        let name_ = ''
        if (payload[key].from) {
          name_ += '>=' + payload[key].from + ' '
        }
        if (payload[key].to) {
          name_ += 'and <' + payload[key].to
        }
        data.push({ name: name_, value: payload[key].doc_count })
      }
      state.eventSeverityOption.series[2].data = data
    },
    setEventSeverityTimer (state, payload) {
      state.eventSeverityTimer = payload.timer
    },
    setClientIpTimer (state, payload) {
      state.clientIpTimer = payload.timer
    },
    setClientIpOption (state, payload) {
      const data = []
      const legendData = []
      let dataSum = 0
      if (payload.buckets) {
        payload.buckets.forEach(function (d, i) {
          data.push({
            name: d.key,
            value: d.doc_count,
            itemStyle: {
              normal: {
                borderWidth: 5,
                shadowBlur: 20,
                borderColor: commonColor[i % 7],
                shadowColor: commonColor[i % 7]
              }
            }
          })
          legendData.push(d.key)
          dataSum += d.doc_count
        })
      }
      if (payload.sum_other_doc_count) {
        data.push({ name: 'other', value: payload.sum_other_doc_count })
        legendData.push('other')
        dataSum += payload.sum_other_doc_count
      }
      state.clientIpOption.series[0].data = data
      state.clientIpOption.legend.data = legendData
      state.clientIpOption.title.subtext = dataSum + ''
    },
    setDateHistogramOption (state, payload) {
      const xdate = []
      const ydata = []
      if (payload.buckets) {
        payload.buckets.forEach(function (d) {
          const date = new Date(d.key)
          xdate.push(date.getHours() + ':' + date.getMinutes())
          ydata.push(d.doc_count)
        })
      }
      state.dateHistogramOption.xAxis[0].data = xdate
      state.dateHistogramOption.series[0].data = ydata
    },
    setDateHistogramTimer (state, payload) {
      state.dateHistogramTimer = payload.timer
    },
    setExternalAttackerList (state, payload) {
      state.externalAttackerList = payload.buckets
    },
    setExternalAttackerTimer (state, payload) {
      state.externalAttackerTimer = payload.timer
    },
    setRealTimeAlarmList (state, payload) {
      state.realTimeAlarmList = payload
    },
    setRealTimeAlarmTimer (state, payload) {
      state.realTimeAlarmTimer = payload.timer
    },
    setExceptionEventOption (state, payload) {
      const names = []
      const values = []
      let count = 0
      for (let i = 0; i < payload.buckets.length && i < 5; i++) {
        names.push(payload.buckets[i].key)
        values.push(payload.buckets[i].doc_count)
        count += payload.buckets[i].doc_count
      }
      state.exceptionEventOption.yAxis[0].data = names
      state.exceptionEventOption.yAxis[1].data = values
      state.exceptionEventOption.series[0].data = values
      state.exceptionEventOption.series[1].data = [count, count, count, count, count]
    },
    setExceptionEventTimer (state, payload) {
      state.exceptionEventTimer = payload.timer
    }
  }
}
