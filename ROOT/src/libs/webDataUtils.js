const CryptoJS = require('crypto-js')
const { JSEncrypt } = require('./jsencrypt')

const encrypt = new JSEncrypt()
encrypt.setPublicKey('MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXkvTbPditn/057cTLaLUqZ0Jn9Gy/HpvpXjK+aQf0drM9SpYNXcaw2bw5BQM1jSE/771m1hAqa8Rr+hvhZZCljQMp3kGtLVez1ZnVdH5haQU+0j6QqY1OTs2UMQonayEenun9RCw/jjz/Xie8OUSECn497ESGfSj83ak03JRYGQIDAQAB')

const S4 = () => {
  return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)
}

export default function dataCrpty (options) {
  const newOptions = { ...options }

  // 请求随机码，用于防会话重放攻击
  newOptions.requestCode = `${S4()}${new Date().getTime()}`
  // console.log('requestCode' + newOptions.requestCode)

  // 传输数据加密
  let AESKey = `${S4()}${S4()}${S4()}${S4()}`
  const RSAKey = encrypt.encrypt(AESKey)
  AESKey = CryptoJS.enc.Utf8.parse(AESKey)
  const bodyStr = CryptoJS.enc.Utf8.parse(JSON.stringify(newOptions))
  const AESBody = CryptoJS.AES.encrypt(bodyStr, AESKey, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  })
  return {
    key: RSAKey,
    value: AESBody.toString()
  }
}
