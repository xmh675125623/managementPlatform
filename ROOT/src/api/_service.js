import { Message, MessageBox } from 'element-ui'
import axios from 'axios'
import Adapter from 'axios-mock-adapter'
import { get, isEmpty } from 'lodash'
import qs from 'qs'
import util from '@/libs/util'
import store from '@/store'
import router from '@/router'
import dataCrpty from '@/libs/webDataUtils'

let baseUrl = ''
const protocolStr = document.location.protocol
if (protocolStr === 'https:') {
  baseUrl = `https://${window.document.domain}/ManagementPlatformApi`
} else {
  baseUrl = `http://${window.document.domain}/ManagementPlatformApi`
}

export const diskSpaceUrlExport = `${baseUrl}/function/disk_import.do`
export const fileDownloadUrl = `${baseUrl}/file_download/download.do?file=`

/**
 * @description 记录和显示错误
 * @param {Error} error 错误对象
 */
function handleError (error) {
  // 添加到日志
  store.dispatch('d2admin/log/push', {
    message: '数据请求异常',
    type: 'danger',
    meta: {
      error
    }
  })
  // 打印到控制台
  if (process.env.NODE_ENV === 'development') {
    util.log.danger('>>>>>> Error >>>>>>')
    console.log(error)
  }
  // 显示提示
  Message({
    message: error.message,
    type: 'error',
    duration: 5 * 1000
  })
}

/**
 * @description 创建请求实例
 */
function createService () {
  // 创建一个 axios 实例
  const service = axios.create()
  // 请求拦截
  service.interceptors.request.use(
    config => {
      // 数据请求loading状态更新
      if (config.sourceDataa.requestLoading) {
        store.commit(config.sourceDataa.requestLoading, true)
      }
      return config
    },
    error => {
      // 发送失败
      console.log(error)
      return Promise.reject(error)
    }
  )
  // 响应拦截
  service.interceptors.response.use(
    response => {
      // 数据请求loading状态更新
      if (response.config.sourceDataa.requestLoading) {
        store.commit(response.config.sourceDataa.requestLoading, false)
      }

      if (response.data.status === 'ok') {
        if (response.config.sourceDataa.successNotice === 1) {
          Message({
            message: '操作成功',
            type: 'success',
            duration: 5 * 1000
          })
        }
        if ('callback' in response.config.sourceDataa) {
          response.config.sourceDataa.callback(response.data)
        }
        return response.data
      } else {
        // 判断是否需要输入验证码
        if (response.data.needInputOptPass === 1) {
          optionPassword(response.config)
        } else {
          Message({
            message: response.data.errorMsg,
            type: 'error',
            duration: 5 * 1000
          })
        }
      }
    },
    error => {
      const status = get(error, 'response.status')
      switch (status) {
        case 400: error.message = '请求错误'; break
        case 401: {
          error.message = '未授权，请登录'
          logout()
          break
        }
        case 403: error.message = '拒绝访问'; break
        case 404: error.message = `请求地址出错: ${error.response.config.url}`; break
        case 408: error.message = '请求超时'; break
        case 500: error.message = '服务器内部错误'; break
        case 501: error.message = '服务未实现'; break
        case 502: error.message = '网关错误'; break
        case 503: error.message = '服务不可用'; break
        case 504: error.message = '网关超时'; break
        case 505: error.message = 'HTTP版本不受支持'; break
        default: break
      }
      handleError(error)
      throw error
    }
  )
  return service
}

function stringify (data) {
  return qs.stringify(data, { allowDots: true, encode: false })
}

function logout () {
  // 删除cookie
  util.cookies.remove('token')
  util.cookies.remove('uuid')
  // 清空 vuex 用户信息
  store.dispatch('d2admin/user/set', {}, { root: true })
  // 跳转路由
  router.push({ name: 'login' })
}

function optionPassword (config) {
  MessageBox.prompt('请输入操作密码', '身份鉴别', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputType: 'password'
  }).then(({ value }) => {
    config.sourceDataa.option_password = value
    config.data = config.sourceDataa
    request(config)
  })
}

/**
 * @description 创建请求方法
 * @param {Object} service axios 实例
 */
function createRequest (service) {
  return function (config) {
    const configDefault = {
      headers: {
        'Content-Type': get(config, 'headers.Content-Type', 'application/json')
      },
      // timeout: 5000,
      baseURL: baseUrl,
      data: {}
    }
    const option = Object.assign(configDefault, config)

    const sessionid = util.cookies.get('token')
    const aid = util.cookies.get('uuid')

    if (sessionid && aid) {
      option.data.sessionid = sessionid
      option.data.aid = aid
    }

    option.sourceDataa = option.data
    // 数据加密传输处理
    option.data = dataCrpty(option.data)

    // 处理 get 请求的参数
    // 请根据实际需要修改
    if (!isEmpty(option.params)) {
      option.url = option.url + '?' + stringify(option.params)
      option.params = {}
    }
    // 当需要以 form 形式发送时 处理发送的数据
    // 请根据实际需要修改
    if (!isEmpty(option.data) && option.headers['Content-Type'] === 'application/x-www-form-urlencoded') {
      option.data = stringify(option.data)
    }
    return service(option)
  }
}

// 用于真实网络请求的实例和请求方法
export const service = createService()
export const request = createRequest(service)

// 用于模拟网络请求的实例和请求方法
export const serviceForMock = createService()
export const requestForMock = createRequest(serviceForMock)

// 网络请求数据模拟工具
export const mock = new Adapter(serviceForMock)

export const loginValidateCodeUrl = `${baseUrl}/admin_login/validate_code.do`
