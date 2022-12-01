<template>
  <d2-container>
    <template slot="header">角色管理</template>
    <el-row style="padding-bottom: 12px">
      <el-button type="primary" icon="el-icon-plus" @click="handleAddUserClick">角色添加</el-button>
    </el-row>

    <!--角色列表-->
    <el-table :data="roleList" stripe border tyle="width: 100%" v-loading="listLoading">
      <el-table-column prop="id" label="ID"/>
      <el-table-column prop="role_name" label="角色名称"/>
      <el-table-column prop="role_description" label="描述"/>
      <el-table-column label="备注" width="260">
        <template slot-scope="scope">
          <pre style="white-space: pre-wrap; word-wrap: break-word">{{scope.row.remark}}</pre>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)" v-if="scope.row.id != 1">编辑</el-button>
          <el-button size="mini" @click="showAuthWin(scope.$index, scope.row)" v-if="scope.row.id != 1">权限设置</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.$index, scope.row)" style="margin-left: 10px">
            <el-button size="mini" type="danger" v-if="scope.row.id != 1" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--角色编辑弹窗表单-->
    <el-dialog :title="roleFormTitle" :visible.sync="roleFormVisible" width="500px" :close-on-click-modal="false">
      <el-form :model="roleFormData" label-width="100px" ref="roleForm" :rules="rules">
        <el-form-item label="角色名称：" prop="role_name">
          <el-input v-model="roleFormData.role_name" autocomplete="off" placeholder="请输入角色名称"></el-input>
        </el-form-item>
        <el-form-item label="描述：" prop="role_description">
          <el-input v-model="roleFormData.role_description" placeholder="请输入描述"></el-input>
        </el-form-item>
        <el-form-item label="备注：" prop="remark">
          <el-input type="textarea" v-model="roleFormData.remark" placeholder="请输入备注（长度不超过200字）" rows="4"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="roleFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="addRoleLoading">确 定</el-button>
      </div>
    </el-dialog>

    <!--角色权限树-->
    <el-dialog title="权限编辑" :visible.sync="authListVisible" width="500px" :close-on-click-modal="false">
      <el-tree
        :data="functionList"
        show-checkbox
        node-key="id"
        :default-checked-keys="treeSelectedId"
        :default-expanded-keys="treeExpandId"
        ref="tree"
        highlight-current
        :props="{label: getAuthLabel}"
        v-loading="treeLoading"
      >
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="authListVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmitAuth" :loading="addRoleLoading">确 定</el-button>
      </div>
    </el-dialog>

  </d2-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import { checkNoSpace } from '@/libs/checkData'

const rules = {
  role_name: [
    { required: true, message: '请输入角色名称' },
    { max: 10, message: '角色名称长度不可超过10' },
    { validator: (rule, value, callback) => checkNoSpace(rule, value, callback) }
  ],
  role_description: [
    { whitespace: true, message: '描述不可全部为空格' },
    { max: 200, message: '描述长度不可超过200' }
  ],
  remark: [
    { whitespace: true, message: '备注不可全部为空格' },
    { max: 200, message: '备注长度不可超过200' }
  ]
}

export default {
  computed: {
    ...mapState('plat/role', ['roleList', 'listLoading', 'roleListCount',
      'addRoleLoading', 'functionList', 'treeLoading', 'treeSelectedId',
      'treeExpandId'])
  },
  data () {
    return {
      roleFormTitle: '角色添加',
      roleFormAction: 'add',
      roleFormVisible: false,
      roleFormData: {},
      rules: rules,
      authListVisible: false
    }
  },
  mounted () {
    this.getRoleList()
  },
  methods: {
    ...mapActions('plat/role', ['getRoleList', 'addRole', 'editRole', 'deleteRole', 'getAuthTree']),
    handleAddUserClick () {
      this.roleFormData = {}
      this.roleFormVisible = true
      this.roleFormTitle = '角色添加'
      this.roleFormAction = 'add'
    },
    handleEdit (index, row) {
      this.roleFormVisible = true
      this.roleFormTitle = '角色编辑'
      this.roleFormData = { ...row }
      this.roleFormAction = 'edit'
    },
    handleDelete (index, row) {
      const that = this
      const data = {
        id: row.id,
        callback: (responseData) => {
          that.getRoleList()
        },
        successNotice: 1
      }
      this.deleteRole(data)
    },
    handleSubmit () {
      this.$refs.roleForm.validate((valid) => {
        if (valid) {
          const that = this
          const data = {
            ...this.roleFormData,
            callback: (responseData) => {
              that.roleFormVisible = false
              that.getRoleList()
            },
            successNotice: 1
          }
          if (this.roleFormAction === 'add') {
            this.addRole(data)
          } else {
            this.editRole(data)
          }
        } else {
          return false
        }
      })
    },
    showAuthWin (index, row) {
      this.roleFormData = { ...row }
      this.authListVisible = true
      const data = {
        id: row.id,
        action: 'authTree'
      }
      this.getAuthTree(data)
    },
    getAuthLabel (data, node) {
      return data.is_menu === 1 ? (`${data.menu} -- （菜单）`) : `${data.function_name} -- （功能模块）`
    },
    handleSubmitAuth () {
      const selectedIds = this.$refs.tree.getCheckedKeys()
      const halfSelectedIds = this.$refs.tree.getHalfCheckedKeys()
      const that = this
      const data = {
        action: 'authEdit',
        id: this.roleFormData.id,
        selected_id: selectedIds,
        half_selected_id: halfSelectedIds,
        callback: (responseData) => {
          that.authListVisible = false
        },
        successNotice: 1
      }
      this.editRole(data)
    }
  }
}
</script>

<style lang="scss">
</style>
