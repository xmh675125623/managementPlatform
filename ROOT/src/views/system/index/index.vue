<template>
<!--  <d2-container type="card">-->
<!--    <div style="margin: -20px">-->
<!--      <img src="./image/temp.jpg" style="width: 100%;">-->
<!--    </div>-->
<!--  </d2-container>-->

  <d2-container>
    <div style="margin: -20px; padding-bottom: 20px; padding-top: 20px; overflow: hidden; height: 100%;display: flex;min-width: 300px; min-height: 580px;" :style="{background: 'url(' + require('./image/index_bg.jpg') + ')', backgroundSize: 'cover', backgroundPosition: 'center'}">

      <div style="flex: 1; height: 100%; margin-right: 12px;display: flex;flex-direction: column; margin-left: 12px">
        <div class="chart-card" :style="{background: 'url(' + require('./image/card_bg1.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}">
          <div class="chart-card-name">平台状态</div>
          <div class="chart" ref="cpuUsage"></div>
        </div>
        <div class="chart-card" style="margin-top: 12px" :style="{background: 'url(' + require('./image/card_bg1.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}">
          <div class="chart-card-name">管理资产数量</div>
          <div class="chart" ref="exceptionEventChart"></div>
        </div>
        <div class="chart-card" style="margin-top: 12px" :style="{background: 'url(' + require('./image/card_bg1.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}">
          <div class="chart-card-name">当日告警日志占比</div>
          <div class="chart" ref="eventSeverityChart"></div>
        </div>
      </div>

      <div style="flex: 2; display: flex; flex-direction: column">
        <div class="chart-card" style="flex: 3" :style="{background: 'url(' + require('./image/card_bg3.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}">
          <div :style="{background: 'url(' + require('./image/index_main.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center', width: '100%', height: '100%'}"></div>
        </div>
        <div class="chart-card" style="margin-top: 12px; flex: 2;display: flex;flex-direction: column; height: 0; " :style="{background: 'url(' + require('./image/card_bg2.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}">
          <div class="chart-card-name" style="padding-top: 2%">实时告警</div>
          <div class="chart-card-table-head">
            <div>时间</div>
            <div>设备</div>
            <div>严重级别</div>
            <div style="flex: 2">内容</div>
          </div>
          <div class="chart-card-table-body-outer">
            <div v-for="(alarm, i) in realTimeAlarmList" :key="i" class="chart-card-table-body" @click="handleManage(alarm.device)" style="cursor: pointer">
              <div>{{alarm.time}}</div>
              <div>{{alarm.device.device_name}}</div>
              <div>{{alarm.level}}</div>
              <div style="flex: 2; overflow: hidden; word-break: break-all">{{alarm.message}}</div>
            </div>
          </div>
        </div>
      </div>

      <div style="flex: 1; height: 100%; margin-left: 12px; margin-right: 12px;display: flex;flex-direction: column">
        <div class="chart-card" style="overflow:hidden;" :style="{background: 'url(' + require('./image/card_bg1.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}">
          <div class="chart-card-name">设备管理总数</div>
          <div class="chart" ref="memUsageChart"></div>
        </div>
        <div class="chart-card" style="margin-top: 12px" :style="{background: 'url(' + require('./image/card_bg1.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}">
          <div class="chart-card-name">当日日志数统计(条)</div>
          <div class="chart" ref="networkProtocolChart"></div>
        </div>
        <div class="chart-card" style="margin-top: 12px" :style="{background: 'url(' + require('./image/card_bg1.png') + ')', backgroundSize: '100% 100%', backgroundPosition: 'center'}">
          <div class="chart-card-name">日志时间趋势</div>
          <div class="chart" ref="dateHistogramChart" style="text-align: center;color: #CCC;font-size: 28px">
          </div>
        </div>
      </div>
    </div>

  </d2-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import echarts from 'echarts'

export default {
  computed: {
    ...mapState('d2admin/auditIndex', [
      'networkProtocolOption',
      'eventSeverityOption',
      'cpuUsageOption',
      'deviceCountOption',
      'dateHistogramOption',
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
      cpuUsage: null,
      cpuUsageTimer: null,
      memUsage: null,
      memUsageTimer: null,
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
      that.cpuUsageRender()
      that.memUsageRender()
      that.dateHistogramChartTender()
      that.realTimeAlarm()
      that.exceptionEventChartRender()
    }, 200)
    window.onresize = () => {
      this.networkProtocolChart.resize()
      this.eventSeverityChart.resize()
      this.cpuUsage.resize()
      this.memUsage.resize()
      this.exceptionEventChart.resize()
      this.dateHistogramChart.resize()
    }
  },
  destroyed () {
    clearInterval(this.networkProtocolChartTimer)
    clearInterval(this.eventSeverityChartTimer)
    clearInterval(this.cpuUsageTimer)
    clearInterval(this.dateHistogramTimer)
    clearInterval(this.exceptionEventTimer)
  },
  methods: {
    ...mapActions('d2admin/auditIndex', [
      'networkProtocol',
      'eventSeverity',
      'dateHistogram',
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
    cpuUsageRender () {
      this.cpuUsage = echarts.init(this.$refs.cpuUsage)
      const that = this
      this.cpuUsage.setOption(that.cpuUsageOption)
      this.cpuUsageTimer = setInterval(function () {
        that.cpuUsage.setOption(that.cpuUsageOption)
      }, 1000)
    },
    memUsageRender () {
      this.memUsage = echarts.init(this.$refs.memUsageChart)
      const that = this
      this.memUsage.setOption(that.deviceCountOption)
      this.memUsageTimer = setInterval(function () {
        that.memUsage.setOption(that.deviceCountOption)
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
    handleManage (device) {
      window.open(device.manage_protocol + '://' + device.ip_address + (device.manage_port ? ':' + device.manage_port : ''))
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
    height: 100%;
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
