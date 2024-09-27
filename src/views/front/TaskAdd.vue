<template>
<div id="add_container">
  <div style="display: flex;justify-content: center;align-items: center;padding: 1vh 4vw 1vh 4vw;box-sizing: border-box;width: 100%;height: 15%;background-color: white;margin-top: 3%;border-radius: 0.5rem">
    <div style="letter-spacing: 0.2rem">温馨提示：时间最好在半个小时之内，这样有利于提高您的学习效率</div>
  </div>

  <div style="width: 100%;height: 45%;margin-top: 4%;display: flex">
    <div style="padding: 3vh 10vw 3vh 10vw;box-sizing: border-box;width: 95%;height: 100%;margin: auto;background-color: white;border-radius: 1rem">
      <div style="width: 100%;height: 12%;display: flex;justify-content: space-between">
        <div style="width: 30%;height: 100%;display: flex">
          <div style="margin: auto;letter-spacing: 0.2rem;font-size: 1.15rem;font-weight: bolder;color: black">主题</div>
        </div>
        <div style="width: 70%;height: 100%;display: flex">
          <van-field style="background-color: #e3e3e3" v-model="saveForm.theme"  />
        </div>
      </div>
      <div style="margin-top: 3%;width: 100%;height: 12%;display: flex;justify-content: space-between">
        <div style="width: 30%;height: 100%;display: flex">
          <div style="margin: auto;letter-spacing: 0.2rem;font-size: 1.15rem;font-weight: bolder;color: black">名称</div>
        </div>
        <div style="width: 70%;height: 100%;display: flex">
          <van-field style="background-color: #e3e3e3" v-model="saveForm.name"  />
        </div>
      </div>
      <div style="margin-top: 3%;width: 100%;height: 28%;display: flex;justify-content: space-between">
        <div style="width: 30%;height: 100%;display: flex">
          <div style="margin: auto;letter-spacing: 0.2rem;font-size: 1.15rem;font-weight: bolder;color: black">内容</div>
        </div>
        <div style="width: 70%;height: 100%;display: flex">
          <van-field
              style="background-color: #e3e3e3"
              v-model="saveForm.comment"
              rows="1"
              type="textarea"
          />
        </div>
      </div>
      <div style="margin-top: 3%;width: 100%;height: 12%;display: flex;justify-content: space-between">
        <div style="width: 30%;height: 100%;display: flex">
          <div style="margin: auto;letter-spacing: 0.2rem;font-size: 1.15rem;font-weight: bolder;color: black">时间</div>
        </div>
        <div style="width: 70%;height: 100%;display: flex;justify-content: left">
          <van-stepper style="background-color: #e3e3e3;margin: auto" v-model="saveForm.duration"/>
        </div>
      </div>
      <div style="width: 100%;height: 15%;margin-top: 8%;display: flex;justify-content: center;align-items: center">
        <van-button @click="saveTask" style="width: 50%;height: 100%">提交</van-button>
      </div>
    </div>
  </div>
</div>
</template>

<script setup>
import instance from "../../api/request.js";
import {reactive, ref} from "vue";

const show = ref(false)

const saveForm = reactive({
  theme:'',
  name:'',
  comment:'',
  duration: ''
})
const saveTask = () => {
  instance.post('/task/saveTask',saveForm).then(res => {
    if (res.data.code == '200'){
      show.value = true
      saveForm.theme = ''
      saveForm.name = ''
      saveForm.comment = ''
      saveForm.duration = 1
    }
  })
}
</script>

<style scoped>
#add_container{
  width: 100%;
  height: 100%;
}
</style>