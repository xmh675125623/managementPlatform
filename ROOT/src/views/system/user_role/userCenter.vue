<template>
  <d2-container>
    <template slot="header">用户中心</template>

    <el-form :model="userInfo" label-width="150px">
      <el-form-item label="帐号：" prop="user_account">
        <el-input v-model="userInfo.user_account" disabled></el-input>
      </el-form-item>
      <el-form-item label="用户名：" prop="user_name">
        <el-input v-model="userInfo.user_name" disabled></el-input>
      </el-form-item>
      <el-form-item label="角色：" prop="role_name">
        <el-input v-model="userInfo.role_name" disabled></el-input>
      </el-form-item>
      <el-form-item label="最后登录时间：" prop="login_time_str">
        <el-input v-model="userInfo.login_time_str" disabled></el-input>
      </el-form-item>
    </el-form>

    <el-button type="primary" @click="handleLoginPasswordClick">修改登录密码</el-button>
    <el-button type="primary" @click="handleOptionPasswordClick">修改操作密码</el-button>

    <!--登录密码弹窗表单-->
    <el-dialog title="修改登录密码" :visible.sync="passwordFormVisible" width="500px" :close-on-click-modal="false">
      <el-form :model="loginPasswordFormData" label-width="120px" ref="loginPasswordForm" :rules="loginPasswordRules">
        <el-form-item label="原登录密码：" prop="old_password">
          <el-input v-model="loginPasswordFormData.old_password" type="password" placeholder="请输入原登录密码"></el-input>
        </el-form-item>
        <el-form-item label="新登录密码：" prop="login_password_1">
          <el-input v-model="loginPasswordFormData.login_password_1" type="password" placeholder="请输入新登录密码"></el-input>
        </el-form-item>
        <el-form-item label="重复新密码：" prop="login_password_2" :rules="loginPassword2Rules">
          <el-input v-model="loginPasswordFormData.login_password_2" type="password" placeholder="请重复输入新密码"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="passwordFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleLoginPasswordSubmit" :loading="editLoading">确 定</el-button>
      </div>
    </el-dialog>

    <!--操作密码弹窗表单-->
    <el-dialog title="修改操作密码" :visible.sync="optionPasswordFormVisible" width="500px" :close-on-click-modal="false">
      <el-form :model="optionPasswordFormData" label-width="120px" ref="optionPasswordForm" :rules="optionPasswordRules">
        <el-form-item label="原操作密码：" prop="old_password">
          <el-input v-model="optionPasswordFormData.old_password" type="password" placeholder="请输入原操作密码"></el-input>
        </el-form-item>
        <el-form-item label="新操作密码：" prop="option_password_1">
          <el-input v-model="optionPasswordFormData.option_password_1" type="password" placeholder="请输入新操作密码"></el-input>
        </el-form-item>
        <el-form-item label="重复新密码：" prop="option_password_2" :rules="optionPassword2Rules">
          <el-input v-model="optionPasswordFormData.option_password_2" type="password" placeholder="请重复输入新密码"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="optionPasswordFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleOptionPasswordSubmit">确 定</el-button>
      </div>
    </el-dialog>

  </d2-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import { checkNoSpace, isEqual, loginPasswordCheck } from '@/libs/checkData'

const loginPasswordRules = {
  old_password: [
    { required: true, message: '请输入原登录密码', trigger: 'blur' }
  ],
  login_password_1: [
    { required: true, message: '请输入新登录密码', trigger: 'blur' },
    { validator: checkNoSpace, trigger: 'blur' },
    { validator: loginPasswordCheck, trigger: 'blur' }
  ]
}

const optionPasswordRules = {
  old_password: [
    { required: true, message: '请输入原操作密码', trigger: 'blur' }
  ],
  option_password_1: [
    { required: true, message: '请输入新登录密码', trigger: 'blur' },
    { validator: checkNoSpace, trigger: 'blur' },
    { max: 15, message: '新操作密码长度不可超过15', trigger: 'blur'}
  ]
}

export default {
  computed: {
    ...mapState('plat/userCenter', ['userInfo', 'editLoading'])
  },
  data () {
    return {
      passwordFormVisible: false,
      optionPasswordFormVisible: false,
      loginPasswordRules: loginPasswordRules,
      optionPasswordRules: optionPasswordRules,
      passwordRules: {},
      loginPasswordFormData: {},
      loginPassword2Rules: [
        { required: true, message: '请再次输入新登录密码', trigger: ['blur', 'change'] },
        { validator: (rule, value, callback) => isEqual('重复新密码', value, callback, this.loginPasswordFormData.login_password_1) }
      ],
      optionPassword2Rules: [
        { required: true, message: '请再次输入新登录密码', trigger: ['blur', 'change'] },
        { validator: (rule, value, callback) => isEqual('重复新密码', value, callback, this.optionPasswordFormData.option_password_2) }
      ],
      optionPasswordFormData: {}
    }
  },
  mounted () {
    this.getUserInfo()
  },
  methods: {
    ...mapActions('plat/userCenter', ['getUserInfo', 'editUserInfo']),
    handleLoginPasswordClick () {
      this.loginPasswordFormData = {}
      this.passwordFormVisible = true
    },
    handleOptionPasswordClick () {
      this.optionPasswordFormData = {}
      this.optionPasswordFormVisible = true
    },
    handleLoginPasswordSubmit () {
      this.$refs.loginPasswordForm.validate((valid) => {
        if (valid) {
          const that = this
          const data = {
            action: 'loginPassword',
            ...this.loginPasswordFormData,
            callback: (responseData) => {
              that.passwordFormVisible = false
              this.getUserInfo()
            },
            successNotice: 1
          }
          this.editUserInfo(data)
        } else {
          return false
        }
      })
    },
    handleOptionPasswordSubmit () {
      this.$refs.optionPasswordForm.validate((valid) => {
        if (valid) {
          const that = this
          const data = {
            action: 'optionPassword',
            ...this.optionPasswordFormData,
            callback: (responseData) => {
              that.optionPasswordFormVisible = false
              this.getUserInfo()
            },
            successNotice: 1
          }
          this.editUserInfo(data)
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
</style>
