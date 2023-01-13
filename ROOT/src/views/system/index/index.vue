<template>
<!--  <d2-container type="card">-->
<!--    <div style="margin: -20px">-->
<!--      <img src="./image/temp.jpg" style="width: 100%;">-->
<!--    </div>-->
<!--  </d2-container>-->

  <d2-container>
    <div style="margin: -20px; padding-bottom: 40px;  overflow: hidden; height: 100%;display: flex;min-width: 300px; min-height: 580px;" :style="{background: 'url(' + require('./image/index_bg.jpg') + ')', backgroundSize: 'cover', backgroundPosition: 'center'}">

      <div style="flex: 1; height: 100%; margin-right: 12px;display: flex;flex-direction: column; margin-left: 12px">
        <div class="chart-card" style="margin-top: 50px;"
             :style="{background: 'url(' + require('./image/card_bg1.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}"
             @click="jumpToGFXKHD">
          <div class="chart-card-name">高风险客户端</div>
          <div class="chart" ref="clientIpChart"></div>
        </div>
        <div class="chart-card" style="margin-top: 12px"
             :style="{background: 'url(' + require('./image/card_bg1.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}"
             @click="jumpToYCWLSJ">
          <div class="chart-card-name">网络异常事件(TOP5)</div>
          <div class="chart" ref="exceptionEventChart"></div>
        </div>
        <div class="chart-card" style="margin-top: 12px"
             :style="{background: 'url(' + require('./image/card_bg1.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}"
             @click="jumpToSJDJFB">
          <div class="chart-card-name">事件等级分布</div>
          <div class="chart" ref="eventSeverityChart"></div>
        </div>
      </div>

      <div style=" flex: 2; display: flex; flex-direction: column">
        <div style="letter-spacing:10px; color: #ffffff; text-align: center;padding-bottom: 8px; padding-top: 8px; border-bottom-left-radius:30px;border-bottom-right-radius:30px;background: linear-gradient(#001733, #033d73);">九州信泰工控可视化图表</div>
        <div class="chart-card" style="flex: 3; margin-top: 14px;" :style="{background: 'url(' + require('./image/card_bg3.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}">
<!--          <div :style="{background: 'url(' + require('./image/index_main.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center', width: '100%', height: '100%'}"></div>-->
          <video id="play_video" controls autoplay="autoplay" loop name="media">
            <source src="./image/942a5a253f8639b83c33dd0c9a3e69dd.mp4" type="video/mp4">
          </video>
        </div>
        <div class="chart-card" style="margin-top: 12px; flex: 2;display: flex;flex-direction: column; height: 0; " :style="{background: 'url(' + require('./image/card_bg2.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}">
          <div class="chart-card-name" style="padding-top: 2%">实时告警</div>
          <div class="chart-card-table-head">
            <div>时间</div>
            <div>严重级别</div>
            <div>告警名称</div>
            <div>源IP</div>
            <div>目的IP</div>
          </div>
          <div class="chart-card-table-body-outer">
            <div v-for="alarm in realTimeAlarmList" class="chart-card-table-body">
              <div>{{formatTime(alarm._source.timestamp)}}</div>
              <div>{{alarm._source.event.severity}}</div>
              <div>{{alarm._source.zeek.notice.category}}</div>
              <div>{{alarm._source.related.ip[1]}}</div>
              <div>{{alarm._source.related.ip[0]}}</div>
            </div>
          </div>
        </div>
      </div>

      <div style="flex: 1; height: 100%; margin-left: 12px; margin-right: 12px;display: flex;flex-direction: column">
        <div class="chart-card" style="overflow:hidden;margin-top: 50px;"
             :style="{background: 'url(' + require('./image/card_bg1.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}"
             @click="jumpToWBGJZ">
          <div class="chart-card-name">外部攻击者</div>
          <div class="chart-card-table-head">
            <div>攻击者IP</div>
            <div>告警数</div>
          </div>
          <div class="chart-card-table-body-outer">
            <div v-for="attackere in externalAttackerList" :key="attackere.key" class="chart-card-table-body">
              <div>{{attackere.key}}</div>
              <div>{{attackere.doc_count}}</div>
            </div>
          </div>
        </div>
        <div class="chart-card" style="margin-top: 12px"
             :style="{background: 'url(' + require('./image/card_bg1.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}"
             @click="jumpToGCDDITXY">
          <div class="chart-card-name">观测到的IT协议(TOP5)</div>
          <div class="chart" ref="networkProtocolChart"></div>
        </div>
        <div class="chart-card" style="margin-top: 12px"
             :style="{background: 'url(' + require('./image/card_bg1.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}"
             @click="jumpToRZSJQS">
          <div class="chart-card-name">日志时间趋势</div>
          <div class="chart" ref="dateHistogramChart"></div>
        </div>
      </div>
    </div>

  </d2-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import echarts from 'echarts'
import util from '@/libs/util'

export default {
  computed: {
    ...mapState('d2admin/auditIndex', [
      'networkProtocolOption',
      'eventSeverityOption',
      'clientIpOption',
      'dateHistogramOption',
      'externalAttackerList',
      'realTimeAlarmList',
      'exceptionEventOption'
    ])
  },
  data () {
    return {
      networkProtocolChart: null,
      networkProtocolChartTimer: null,
      eventSeverityChart: null,
      eventSeverityChartTimer: null,
      clientIpChart: null,
      clientIpChartTimer: null,
      dateHistogramChart: null,
      dateHistogramTimer: null,
      exceptionEventChart: null,
      exceptionEventTimer: null
    }
  },
  mounted () {
    const that = this
    setTimeout(function () {
      that.networkProtocolChartRender()
      that.eventSeverityChartRender()
      that.clientIpChartRender()
      that.dateHistogramChartTender()
      that.externalAttacker()
      that.realTimeAlarm()
      that.exceptionEventChartRender()
    }, 200)
    window.onresize = () => {
      this.networkProtocolChart.resize()
      this.eventSeverityChart.resize()
      this.clientIpChart.resize()
      this.exceptionEventChart.resize()
      this.dateHistogramChart.resize()
    }
  },
  destroyed () {
    clearInterval(this.networkProtocolChartTimer)
    clearInterval(this.eventSeverityChartTimer)
    clearInterval(this.clientIpChartTimer)
    clearInterval(this.dateHistogramTimer)
    clearInterval(this.exceptionEventTimer)
  },
  methods: {
    ...mapActions('d2admin/auditIndex', [
      'networkProtocol',
      'eventSeverity',
      'clientIp',
      'dateHistogram',
      'externalAttacker',
      'realTimeAlarm',
      'exceptionEvent'
    ]),
    formatTime (value) {
      if (value) {
        const date = new Date(value) // 时间戳为秒：10位数
        const year = date.getFullYear()
        const month = date.getMonth() + 1 < 10 ? `0${date.getMonth() + 1}` : date.getMonth() + 1
        const day = date.getDate() < 10 ? `0${date.getDate()}` : date.getDate()
        const hour = date.getHours() < 10 ? `0${date.getHours()}` : date.getHours()
        const minute = date.getMinutes() < 10 ? `0${date.getMinutes()}` : date.getMinutes()
        const second = date.getSeconds() < 10 ? `0${date.getSeconds()}` : date.getSeconds()
        return `${year}-${month}-${day} ${hour}:${minute}:${second}`
      } else {
        return ''
      }
    },
    networkProtocolChartRender () {
      this.networkProtocol()
      this.networkProtocolChart = echarts.init(this.$refs.networkProtocolChart)
      const that = this
      this.networkProtocolChart.setOption(that.networkProtocolOption)
      this.networkProtocolChartTimer = setInterval(function () {
        that.networkProtocolChart.setOption(that.networkProtocolOption)
      }, 1000)
    },
    eventSeverityChartRender () {
      this.eventSeverity()
      this.eventSeverityChart = echarts.init(this.$refs.eventSeverityChart)
      const that = this
      this.eventSeverityChart.setOption(that.eventSeverityOption)
      this.eventSeverityChartTimer = setInterval(function () {
        that.eventSeverityChart.setOption(that.eventSeverityOption)
      }, 1000)
    },
    clientIpChartRender () {
      this.clientIp()
      this.clientIpChart = echarts.init(this.$refs.clientIpChart)
      const that = this
      this.clientIpChart.setOption(that.clientIpOption)
      this.clientIpChartTimer = setInterval(function () {
        that.clientIpChart.setOption(that.clientIpOption)
      }, 1000)
    },
    dateHistogramChartTender () {
      this.dateHistogram()
      this.dateHistogramChart = echarts.init(this.$refs.dateHistogramChart)
      const that = this
      this.dateHistogramChart.setOption(that.dateHistogramOption)
      this.dateHistogramTimer = setInterval(function () {
        that.dateHistogramChart.setOption(that.dateHistogramOption)
      }, 1000)
    },
    exceptionEventChartRender () {
      this.exceptionEvent()
      this.exceptionEventChart = echarts.init(this.$refs.exceptionEventChart)
      const that = this
      this.exceptionEventChart.setOption(that.exceptionEventOption)
      this.exceptionEventTimer = setInterval(function () {
        that.exceptionEventChart.setOption(that.exceptionEventOption)
      }, 1000)
    },
    jumpToGFXKHD () {
      window.open('https://' + util.cookies.get('hostIndex') + ':15601/app/dashboards#/view/076caa20-64aa-11e8-9e8d-39632dc6b766')
    },
    jumpToYCWLSJ () {
      window.open('https://' + util.cookies.get('hostIndex') + ':15601/app/dashboards#/view/1fff49f6-0199-4a0f-820b-721aff9ff1f1')
    },
    jumpToSJDJFB () {
      window.open('https://' + util.cookies.get('hostIndex') + ':15601/app/dashboards#/view/d2dd0180-06b1-11ec-8c6b-353266ade330')
    },
    jumpToWBGJZ () {
      window.open('https://' + util.cookies.get('hostIndex') + ':15601/app/dashboards#/view/cfa96750-6651-11e8-a67b-cd4cf123b2a5')
    },
    jumpToGCDDITXY () {
      window.open('https://' + util.cookies.get('hostIndex') + ':15601/app/dashboards#/view/0ad3d7c2-3441-485e-9dfe-dbb22e84e576')
    },
    jumpToRZSJQS () {
      window.open('https://' + util.cookies.get('hostIndex') + ':15601/app/dashboards#/view/a33e0a50-afcd-11ea-993f-b7d8522a8bed')
    }
  }
}
</script>

<style lang="scss" scoped>
.page {
  border: 1px solid red;
  &>div {
    padding: 0 !important;
  }
  .d2-container-full{
    padding: 0;
    &>div {
      padding: 0 !important;
    }
  }
}
.chart-div{
  border: 1px solid #FFF;
}

.chart-card {
  //background: rgba(36, 68, 143, 0.3);
  //background: -webkit-linear-gradient(top, rgba(0,0,0,0), rgba(12,80,253,0.06), rgba(12,80,253,0.2));
  //border-top: 2px solid rgb(34, 69,135);
  width: 100%;
  flex: 1;
  display: flex;
  flex-direction: column;
  .chart-card-name {
    color: #17caf0;
    font-size: 15px;
    font-weight: bold;
    padding: 5% 0 0 30px;
  }
  .chart {
    width: 100%;
    height: 20vh;
  }
  .chart-card-table-head {
    display: flex;
    color: #FFF;
    margin: 10px 20px 0 20px;
    //background: rgba(24,129,247,0.5);
    background: -webkit-linear-gradient(right, rgba(6,26,77,1), rgba(5,108,194,0.5), rgba(5,108,194,1));
    padding: 6px 12px;
    font-size: 14px;
    border-radius: 5px;
    div {
      flex: 1;
    }
  }
  .chart-card-table-body-outer {
    flex: 1;
    overflow-y: scroll;
    margin: 10px 20px 25px 20px;
    &::-webkit-scrollbar {
      width: 5px;
    }
    &::-webkit-scrollbar-thumb {
      border-radius: 10px;
      background-color: rgba(36, 68, 143, 0.6);
    }
  }

  .chart-card-table-body {
    display: flex;
    text-align: left;
    font-size: 14px;
    color: rgba(255,255,255,0.6);
    padding: 8px 12px;
    &:nth-child(even) {
      background: rgba(24,129,247,0.1);
      border-radius: 5px;
    }
    div {
      flex: 1;
    }
  }
}
</style>
