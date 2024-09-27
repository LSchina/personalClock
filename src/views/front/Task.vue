<template>
<div id="container_task">
  <van-notice-bar color="#1989fa" background="#ecf9ff" left-icon="info-o">
    点击查看详情打卡
  </van-notice-bar>
  <div style="display: flex;justify-content: center;align-items: center;padding: 1vh 4vw 1vh 4vw;box-sizing: border-box;width: 100%;height: 15%;background-color: white;margin-top: 3%;border-radius: 0.5rem">
    <div style="letter-spacing: 0.2rem">温馨提示：任务最好不要超过两个，否则肯呢个会影响您的打卡效率</div>
  </div>
  <div style="margin-top: 3%">
    <van-cell
        v-for="item in myTaskList.value"
        style="width: 100%;height: 33.33%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem"
        :title="item.name"
        center="center"
        value="点击查看详情"
        @click="taskinfo(item.id)"
        size="large"/>
  </div>
</div>
  <van-action-sheet style="height: 80%" v-model:show="show" title="当前任务详情">
    <div class="content">
      <div style="width: 100%;height: 10%;display: flex;justify-content: left;align-items: center">
        <div style="font-size: 0.8rem">主题：</div>
      </div>
      <div style="width: 100%;height: 10%;display: flex;">
        <div style="margin: auto;font-size: 1.5rem">{{taskDemo.theme}}</div>
      </div>
      <div style="width: 100%;height: 10%;display: flex;justify-content: left;align-items: center">
        <div style="font-size: 0.8rem">内容：</div>
      </div>
      <div style="width: 100%;height: 10%;display: flex;">
        <div style="margin: auto">{{taskDemo.comment}}</div>
      </div>
      <div style="width: 100%;height: 30%;display: flex;margin-top: 10%">
        <div style="background-color: dodgerblue;border-radius: 100%;display: flex;justify-content: center;align-items: center;margin: auto;width: 7.5rem;height: 7.5rem">
        <van-count-down style="color: #100f0f;font-size: 1.35rem" :time="taskDemo.duration * 1000" />
        </div>
      </div>
      <div  style="width: 100%;height: 30%;display: flex;"><van-button @click="dingding(taskDemo.id)" type="primary" style="margin: auto;width: 40%">打卡</van-button></div>
    </div>
  </van-action-sheet>
  <van-toast v-model:show="dingshow" style="padding: 5rem">
    <template #message>
     <div>打卡成功</div>
    </template>
  </van-toast>

</template>

<script setup>
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";

const myTaskList = reactive([])

const show = ref(false)

const dingshow = ref(false)

const taskDemo = reactive({
  id:'',
  theme:'',
  comment:'',
  duration: 30 ,
})

window.setInterval(()=>{
  setTimeout(()=>{
    console.log(myTaskList.value.length)
    for (let i = 0; i < myTaskList.value.length; i++){
      console.log(myTaskList.value,new Date())
      console.log(myTaskList.value[i])
      instance.post(
          '/task/autoSubmit',
          myTaskList.value[i]
      ).then(res => {
        if (res.data.code == '200'){
          instance.get('/task/nowTask').then(res => {
            if (res.data.code == '200'){
              myTaskList.value = res.data.list
            }
          })
        }
      })
    }
  },0)
},5000)

const dingding = (id) => {
  instance.post('/fding/add/' + id).then(res =>{
    console.log(id)
    if (res.data.code == '200'){
      dingshow.value = true
      show.value = false
      instance.get('/task/nowTask').then(res => {
        if (res.data.code == '200'){
          myTaskList.value = res.data.list
        }
      })
    }
  })
}

const taskinfo = (id) => {
  show.value = true
  instance.get('/task/get/' + id).then(res => {
    if (res.data.code == '200'){
      console.log(res.data.task)
      taskDemo.id = res.data.task.id
      taskDemo.theme = res.data.task.theme
      taskDemo.comment = res.data.task.comment
      taskDemo.duration = res.data.task.duration
    }
  })
}

onMounted(() =>{
  instance.get('/task/nowTask').then(res =>{
    if (res.data.code == '200'){
      myTaskList.value = res.data.list
    }
  })
})

</script>

<style scoped>
#container_task{
  width: 100%;
  height: 100%;
}
.content {
  padding: 16px 16px 160px;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}
</style>