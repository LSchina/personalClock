<template>
<div id="exit_container">
  <!-- 可以使用 CellGroup 作为容器 -->
  <van-cell @click="exitpsshow" icon="records-o" style="width: 100%;height: 3rem;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="修改密码" is-link  size="large"/>
  <van-cell @click="exitnickshow" icon="records-o" style="width: 100%;height: 3rem;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="修改昵称" is-link  size="large"/>

  <van-form v-if="exitps == true" style="margin-top: 3rem" @submit="exitSubmit">
    <van-cell-group inset>
      <van-field
          v-model="exitPass.password"
          name="旧密码"
          label="旧密码"
          placeholder="请输入旧密码"
          :rules="[{ required: true, message: '请填写旧密码' }]"
      />
      <van-field
          v-model="exitPass.newPass"
          name="新密码"
          label="新密码"
          placeholder="请输入新密码"
          :rules="[{ required: true, message: '请填写新密码' }]"
      />
      <div style="margin: 16px;">
        <van-button round block type="primary" native-type="submit">
          提交
        </van-button>
      </div>
    </van-cell-group>
  </van-form>
    <van-form v-if="exitnick == true" style="margin-top: 3rem" @submit="exitNickname">
    <van-cell-group inset>
      <van-field
          v-model="exitNick.nickname"
          name="旧密码"
          label="旧密码"
          placeholder="请输入旧密码"
          :rules="[{ required: true, message: '请填写旧密码' }]"
      />
    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        提交
      </van-button>
    </div>
  </van-form>


</div>
</template>

<script setup>

import {reactive, ref} from "vue";

const exitps = ref(false)

const exitnick = ref(false)

const exitpsshow = () => {
  exitps.value = true
  exitnick.value = false
}

const exitnickshow = () => {
  exitps.value = false
  exitnick.value = true
}

const exitPass = reactive({
  password:'',
  newPass:''
})

const exitNick = reactive({
  nickname:''
})

const exitSubmit = () => {
  
}

const exitNickname = () => {
  
}
</script>

<style scoped>
#exit_container{
  width: 100%;
  height: 100%;
}
</style>