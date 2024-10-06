<template>
<div id="login_container">
  <!--  header  -->
    <div style="margin-top: 1rem;border-radius: 10rem;position: absolute;height: 8rem;width: 8rem;background-color: #ffffff;display: flex;" @click="goTheme">
      <van-icon size="2rem" color="black" style="margin: auto" name="arrow-left"/>
    </div>
  <el-card style="padding: 5vh 5vw 5vh 5vw;box-sizing: border-box;width: 90%;height: 60%;margin: auto;background-color: #ffffff;border-radius: 2rem">
    <div style="width: 100%;height: 100%">
      <div style="width: 100%;height: 20%;display: flex;">
        <div style="margin: auto;font-size: 2rem;">You 打卡</div>
      </div>
      <div style="width: 100%;height: 70%;margin-top: 13%">
        <van-form v-if="loginstatus == true" @submit="onSubmit">
          <van-cell-group style="background-color: transparent" inset>
            <van-field
                style="background-color: transparent"
                v-model="loginForm.username"
                name="用户名"
                label="用户名"
                placeholder="用户名"
                :rules="[{ required: true, message: '请填写用户名' }]"
            />
            <van-field
                style="background-color: transparent"
                v-model="loginForm.password"
                type="password"
                name="密码"
                label="密码"
                placeholder="新密码"
                :rules="[{ required: true, message: '请填写密码' }]"
            />

          </van-cell-group>
          <div style="margin-top: 50px;width: 100%;display: flex;justify-content: center;align-items: center">
            <van-button  block style="background-color: #1c8ee0;width: 260px;border-radius: 10px" native-type="submit">
              登录
            </van-button>
          </div>
        </van-form>
        <div  v-if="loginstatus == true" style="margin-top: 30px;width: 100%;height: 5%;display: flex;justify-content: center;align-items: center">
          <div @click="loginstatus = false"  style="text-decoration: underline">无账号?前往注册页面</div>
        </div>
        <van-form v-if="loginstatus == false" @submit="onSubmitRegister">
          <van-cell-group style="background-color: transparent" inset>
            <van-field
                style="background-color: transparent"
                v-model="registerForm.username"
                name="用户名"
                label="用户名"
                placeholder="用户名"
                :rules="[{ required: true, message: '请填写用户名' }]"
            />
            <van-field
                style="background-color: transparent"
                v-model="registerForm.password"
                type="password"
                name="密码"
                label="密码"
                placeholder="密码"
                :rules="[{ required: true, message: '请填写密码' }]"
            />
            <van-field
              style="background-color: transparent"
              v-model="registerForm.newPass"
              type="password"
              name="密码"
              label="密码"
              placeholder="再次填写"
              :rules="[{ required: true, message: '请再次填写密码' }]"
          />
          </van-cell-group>
          <div style="margin: 16px;">
            <van-button round block style="background-color: #1bcb1b" native-type="submit">
              注册
            </van-button>
          </div>
        </van-form>
        <div v-if="loginstatus == false"
             style="width: 100%;height: 5%;display: flex;justify-content: center;align-items: center">
          <div @click="loginstatus = true" style="text-decoration: underline">已有账号，前往登录</div>
        </div>

      </div>
    </div>
  </el-card>
</div>
  <van-toast v-model:show="show" style="padding: 5rem">
    <template #message>
      登录成功
    </template>
  </van-toast>

</template>

<script setup>
import {useRouter} from "vue-router";
import {reactive, ref} from "vue";
import instance from "../api/request.js";
import useDataStore from "../store/index.js";

const useData = useDataStore()

const router = useRouter()

const show = ref(false)

const loginstatus = ref(true)

const loginForm = reactive({
  username:'',
  password:''
})

const registerForm = reactive({
  username:'',
  password:'',
  newPass:''
})

const onSubmit = () => {
  instance.post('/user/login',loginForm).then(res =>{
    if (res.data.code == '200'){
      console.log(loginForm)
      useData.userId = res.data.user.id
      useData.nickname = res.data.user.nickname
      useData.avater  = res.data.user.avater
      useData.token = res.data.token
      router.push('/front')
      show.value = true
    }
  })
}

const onSubmitRegister = () => {
  instance.post('/user/register',registerForm).then(res =>{
    if (res.data.code == '200'){
      console.log(registerForm)
      show.value = true
      loginstatus.value = true
    }
  })
}

const goTheme = () => {
  router.push('/theme')
}



</script>

<style scoped>
#login_container{
  width: 100%;
  height: 100%;
  display: flex;
}
</style>