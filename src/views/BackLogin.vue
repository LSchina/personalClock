<template>
  <div id="login_container">
    <div style="width: 100%;height: 100%;background-color: #eeeeee;display: flex">
      <div style="border-radius: 20px;box-sizing: border-box;padding: 50px 70px 50px 70px;;width: 35%;height: 70%;margin: auto;background-color: #ffffff">
        <div style="width: 100%;height: 100%;">
          <div style="width: 100%;height: 20%;display: flex">
            <div style="margin: auto;color: #0c0b0e;letter-spacing: 1px;font-size: 35px;font-weight: bolder">个人学习后台登录系统</div>
          </div>
          
          <div style="width: 100%;height: 80%;">
            <el-form :model="loginForm" :rules="rules" :label-position="'top'" ref="loginReForm">
              <el-form-item label="Username">
                <el-input  style="height: 50px;" input-style=""  class="form_input" v-model="loginForm.username"></el-input>
              </el-form-item>
              <el-form-item label="Password">
                <el-input style="height: 50px" :show-password="true" class="form_input" v-model="loginForm.password"></el-input>
              </el-form-item>
              <el-form-item>
                <div style="width: 100%;display: flex;justify-content: center;margin-top: 20px">
                  <el-button type="primary" class="form_button" @click="signIn">登录</el-button>
                </div>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import useDataStore from "../store/index.js";
import {useRouter} from "vue-router";
import {reactive} from "vue";
import instance from "../api/request.js";
import {ElMessage} from "element-plus";


const router = useRouter()

const useData = useDataStore()

const loginForm = reactive({
  username:'',
  password:''
})

const signIn = () => {
  instance.post('/admin/login',loginForm).then(res => {
    console.log(loginForm)
    if (res.data.code == '200'){
      router.push("/back")
      useData.adminId = res.data.admin.id
      useData.nickname = res.data.admin.nickname
      useData.avater = res.data.admin.avater
      ElMessage({
        message: '登录成功',
        grouping: true,
        type: 'success',
      })
      console.log(res.data.admin)
    }else {
    }
  })
}

</script>

<style scoped>
#login_container{
  width: 100%;
  height: 100%;
}
.form_input {
  width: 100%;
  --el-input-border-color: white;
  --el-input-bg-color: #eeeeee;
  --el-input-text-color: black;
}
.form_button{
  height: 50px;
  width: 100%;
  color: black;
  font-size: 20px;
  letter-spacing: 2px;
}
</style>