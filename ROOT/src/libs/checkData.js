/**
 * 校验器，不可含空格
 */
export function checkNoSpace (rule, value, callback) {
  if (value && (value.indexOf(' ') > -1 || value.indexOf('\t') > -1 || value.indexOf('　') > -1)) {
    // eslint-disable-next-line standard/no-callback-literal
    callback('不可包含空格')
  }
  callback()
}

/**
 * 校验器，是否相等
 */
export function isEqual (label, value, callback, sValue) {
  if (value !== sValue && sValue !== '' && value !== '') {
    // eslint-disable-next-line standard/no-callback-literal
    callback('两次密码输入不一致')
  }
  callback()
}

/**
 * 检查ip格式,可以用于ipv4和ipv6
 * @param label
 * @param value
 * @param callback
 */
export function ipCheck (label, value, callback) {
  if (value && value.length > 0) {
    if (!/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(value) &&
      !/^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*$/
        .test(value) &&
      !/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\/([1-9]|[1-2]\d|3[0-2])$/.test(value)) {
      if (label) {
        // eslint-disable-next-line standard/no-callback-literal
        callback(`请输入正确的${label}`)
      } else {
        // eslint-disable-next-line standard/no-callback-literal
        callback('请输入正确的IP地址')
      }
    }
  }
  callback()
}

/**
 * 检查ip格式,可以用于ipv4
 * @param label
 * @param value
 * @param callback
 */
export function ipv4Check (label, value, callback) {
  if (value && value.length > 0) {
    if (!/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(value) &&
      !/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\/([0-9]|[1-2]\d|3[0-2])$/.test(value)) {
      if (label) {
        // eslint-disable-next-line standard/no-callback-literal
        callback(`请输入正确的${label}(ipv4)`)
      } else {
        // eslint-disable-next-line standard/no-callback-literal
        callback('请输入正确的IP地址(ipv4)')
      }
    }
  }
  callback()
}

/**
 * 检查ip格式,可以用于ipv4段
 * @param label
 * @param value
 * @param callback
 */
export function ipv4MaskCheck (value, callback) {
  if (value && value.length > 0) {
    if (!/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\/(\d{1}|1\d|2\d|3[0-2])$/.test(value)) {
      // eslint-disable-next-line standard/no-callback-literal
      callback('请输入正确的IP地址段，如：192.168.0.0/24')
    }
  }
  callback()
}

/**
 * 检查ip格式,可以用于ipv4段
 * @param label
 * @param value
 * @param callback
 */
export function ipv4MaskCheck2 (value, callback) {
  if (value && value.length > 0) {
    if (!/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(value)) {
      // eslint-disable-next-line standard/no-callback-literal
      callback('请输入正确的IP地址段，如：192.168.0')
    }
  }
  callback()
}

/**
 * 检查ip格式,可以用于ipv6
 * @param label
 * @param value
 * @param callback
 */
export function ipv6Check (label, value, callback) {
  if (value && value.length > 0) {
    if (!/^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*$/.test(value)) {
      if (label) {
        // eslint-disable-next-line standard/no-callback-literal
        callback(`请输入正确的${label}(ipv6)`)
      } else {
        // eslint-disable-next-line standard/no-callback-literal
        callback('请输入正确的IP地址(ipv6)')
      }
    }
  }
  callback()
}

/**
 * 检查mac地址
 * @param label
 * @param value
 * @param callback
 */
export function macCheck (label, value, callback) {
  if (value && value.length > 0) {
    if (!/[a-f\dA-F\d]{2}:[a-f\dA-F\d]{2}:[a-f\dA-F\d]{2}:[a-f\dA-F\d]{2}:[a-f\dA-F\d]{2}:[a-f\dA-F\d]{2}/.test(value)) {
      // eslint-disable-next-line standard/no-callback-literal
      callback('请输入正确的MAC地址')
    }
  }
  callback()
}

/**
 * 检查登录密码
 * @param label
 * @param value
 * @param callback
 * @param passLowercase
 * @param passUppercase
 * @param passSpecialChar
 * @param passNumber
 * @param passLength
 */
export function loginPasswordCheck (rule, value, callback) {
  if (!value || value.indexOf(' ') >= 0) {
    callback()
    return
  }

  let loginPasswordRule = localStorage.getItem('loginPasswordRule');
  loginPasswordRule = JSON.parse(loginPasswordRule)

  if (value.length < loginPasswordRule.passLength) {
    // eslint-disable-next-line standard/no-callback-literal
    callback(`密码长度不得小于${loginPasswordRule.passLength}`)
    return
  }

  let passLowercase2 = 0
  let passUppercase2 = 0
  let passSpecialChar2 = 0
  let passNumber2 = 0

  for (let i = 0; i < value.length; i += 1) {
    const c = value[i]
    if (c >= 'A' && c <= 'Z') {
      passUppercase2 = 1
    } else if (c >= 'a' && c <= 'z') {
      passLowercase2 = 1
    } else if (isNaN(c)) {
      passSpecialChar2 = 1
    } else {
      passNumber2 = 1
    }
  }

  if ((!loginPasswordRule.passLowercase || passLowercase2 === loginPasswordRule.passLowercase) &&
    (!loginPasswordRule.passUppercase || passUppercase2 === loginPasswordRule.passUppercase) &&
    (!loginPasswordRule.passSpecialChar || passSpecialChar2 === loginPasswordRule.passSpecialChar) &&
    (!loginPasswordRule.passNumber || passNumber2 === loginPasswordRule.passNumber)) {
    callback()
    return
  }

  let message = '密码必须包含'
  if (loginPasswordRule.passLowercase === 1) {
    message += '小写字母、'
  }
  if (loginPasswordRule.passUppercase === 1) {
    message += '大写字母、'
  }
  if (loginPasswordRule.passSpecialChar === 1) {
    message += '特殊字符、'
  }
  if (loginPasswordRule.passNumber === 1) {
    message += '数字、'
  }
  message = message.substr(0, message.length - 1)
  callback(message)
}

export function numGroupCheck (value, callback) {
  if (value && value.length > 0) {
    if (!/^(\d+[,])*(\d+)$/.test(value)) {
      // eslint-disable-next-line standard/no-callback-literal
      callback('请输入正确的数字组合，如：1,2,3')
    }
  }
  callback()
}
