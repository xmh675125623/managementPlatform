<template>
  <d2-container>
    <template slot="header">用户管理</template>
    <el-row style="padding-bottom: 12px">
      <el-button type="primary" icon="el-icon-plus" @click="handleAddUserClick">用户添加</el-button>
    </el-row>

    <!--用户列表-->
    <el-table :data="userList" stripe border tyle="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="ID"/>
      <el-table-column prop="user_account" label="帐号"/>
      <el-table-column prop="role_name" label="角色"/>
      <el-table-column prop="user_name" label="用户名"/>
      <el-table-column label="备注" width="230">
        <template slot-scope="scope">
          <pre style="white-space: pre-wrap; word-wrap: break-word">{{scope.row.remark}}</pre>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
           &nbsp;
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.$index, scope.row)">
            <el-button size="mini" type="danger" v-if="scope.row.id != uuid" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--分页器-->
    <div style="overflow: hidden;padding-top: 12px">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="userListCount"
        style="float: right">
      </el-pagination>
    </div>

    <!--用户编辑弹窗表单-->
    <el-dialog :title="userFormTitle" :visible.sync="userEditFormVisible" width="500px" :close-on-click-modal="false">
      <el-form :model="userForm" :label-width="formLabelWidth" ref="userForm" :rules="rules">
        <el-form-item label="帐号：" prop="user_account">
          <el-input v-model="userForm.user_account" autocomplete="off" :disabled="userFormTitle==='用户编辑'" placeholder="请输入帐号"></el-input>
        </el-form-item>
        <el-form-item label="密码：" prop="password" :rules="passwordRules">
          <el-input v-model="userForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="操作密码：" prop="option_password_1" :rules="optionPasswordRules">
          <el-input v-model="userForm.option_password_1" type="password" placeholder="请输入操作密码"></el-input>
        </el-form-item>
        <el-form-item label="角色：" prop="role_id">
          <el-select v-model="userForm.role_id" placeholder="请选择角色">
            <el-option v-for="role in roles" :key="role.id" :label="role.role_name" :value="role.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户名：" prop="user_name">
          <el-input v-model="userForm.user_name" autocomplete="off" :disabled="userFormTitle==='用户编辑'" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="备注：" prop="remark">
          <el-input type="textarea" v-model="userForm.remark"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancle">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </d2-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import { checkNoSpace, loginPasswordCheck } from '@/libs/checkData'
import cookies from '@/libs/util.cookies'

const rules = {
  user_account: [
    { required: true, message: '请输入帐号', trigger: 'blur' },
    { max: 10, message: '帐号长度不可超过10' },
    { validator: checkNoSpace, trigger: 'blur' }
  ],
  role_id: [
    { required: true, message: '请选择角色' }
  ],
  user_name: [
    { required: true, message: '请输入用户名' },
    { validator: checkNoSpace },
    { max: 10, message: '用户名长度不可超过10' }
  ],
  remark: [
    { whitespace: true, message: '备注不可全部为空格' },
    { max: 200, message: '备注长度不可超过200'}
  ]
}

export default {
  computed: {
    ...mapState('d2admin/adminUser', ['userList', 'userListCount', 'loading', 'roles'])
  },
  data () {
    return {
      uuid: 0,
      currentPage: 1,
      pageSize: 10,
      userEditFormVisible: false,
      userForm: {},
      userFormTitle: '用户添加',
      formLabelWidth: '100px',
      rules: rules,
      passwordRules: {},
      optionPasswordRules: {}
    }
  },
  mounted () {
    this.list({ page: this.currentPage, pageSize: this.pageSize })
    this.uuid = cookies.get('uuid')
  },
  methods: {
    ...mapActions('d2admin/adminUser', ['list', 'addUser', 'editUser', 'deleteUser']),
    handleCurrentChange (val) {
      this.currentPage = val
      this.list({ page: this.currentPage, pageSize: this.pageSize })
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.list({ page: this.currentPage, pageSize: this.pageSize })
    },
    handleAddUserClick () {
      this.passwordRules = [
        { required: true, message: '请输入登录密码', trigger: 'blur' },
        { max: 20, message: '密码长度不可超过20' },
        { validator: checkNoSpace, trigger: 'blur' },
        { validator: loginPasswordCheck, trigger: 'blur' }
      ]
      this.optionPasswordRules = [
        { required: true, message: '请输入操作密码', trigger: 'blur' },
        { validator: checkNoSpace },
        { max: 15, message: '操作密码长度不可超过15' }
      ]
      this.userEditFormVisible = true
      this.userFormTitle = '用户添加'
      this.userForm = {}
    },
    handleEdit (index, row) {
      this.passwordRules = [
        { max: 20, message: '密码长度不可超过20' },
        { validator: checkNoSpace, trigger: 'blur' },
        { validator: loginPasswordCheck, trigger: 'blur' }
      ]
      this.optionPasswordRules = [
        { validator: checkNoSpace },
        { max: 15, message: '操作密码长度不可超过15' }
      ]
      this.userEditFormVisible = true
      this.userFormTitle = '用户编辑'
      this.userForm = { ...row }
    },
    handleDelete (index, row) {
      const that = this
      const data = {
        uid: row.id,
        callback: (responseData) => {
          that.list({ page: that.currentPage, pageSize: that.pageSize })
        },
        successNotice: 1
      }
      this.deleteUser(data)
    },
    handleSubmit () {
      this.$refs.userForm.validate((valid) => {
        if (valid) {
          const that = this
          const data = {
            ...this.userForm,
            callback: (responseData) => {
              that.userEditFormVisible = false
              that.list({ page: that.currentPage, pageSize: that.pageSize })
            },
            successNotice: 1
          }
          if (this.userFormTitle === '用户添加') {
            this.addUser(data)
          } else {
            data.uid = data.id
            this.editUser(data)
          }
        } else {
          return false
        }
      })
    },
    handleCancle () {
      this.userEditFormVisible = false
      this.userForm = {}
    }
  }
}
</script>

<style lang="scss">
</style>
