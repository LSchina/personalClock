<template>
<div id="recommend_container">
  <div style="width: 100%;height: 20%;background-color: #4d317a;border-radius: 20px;display: flex">
    <div style="margin: auto;letter-spacing: 3px;font-size: 25px;font-weight: 600;color: white;font-family: 楷体;">系统推荐任务作为前端登录页面的门面，是勾起消费者兴趣的保障,嘿嘿嘿</div>
  </div>
  <div style="width: 100%;height: 70%;display: flex;justify-content: space-between;margin-top: 3%">
    <div
        style="letter-spacing: 3px;font-size: 20px;font-weight: 600;color: white;font-family: 楷体;width: 40%;height: 100%;border-radius: 20px;background-color: #4d317a;box-sizing: border-box;padding: 30px"
        v-for="item in dataList.value"
        :key="item"
    >
      <div style="width: 100%;height: 10%;display: flex;justify-content: space-between">
        <div>任务主题</div>
        <div>{{item.theme}}</div>
      </div>
      <div style="width: 100%;height: 10%;display: flex;justify-content: space-between;margin-top: 7.5%">
        <div>任务名称</div>
        <div>{{item.name}}</div>
      </div>
      <div style="width: 100%;height: 30%;display: flex;justify-content: space-between;margin-top: 7.5%">
        <div style="width: 40%">任务内容</div>
        <div style="width: 60%">{{item.comment}}</div>
      </div>
      <div style="width: 100%;height: 10%;display: flex;justify-content: space-between;margin-top: 0.5%">
        <div>任务创建时间</div>
        <div>{{formatDateTimeForHMS(item.createTime)}}</div>
      </div>
      <div style="width: 100%;height: 10%;display: flex;justify-content: center;align-items: center;margin-top: 5.5%">
        <el-button @click="exitRecommend(item.id)" style="width: 100%;height: 100%"><div style="letter-spacing: 2px">修改系统推荐任务</div></el-button>
      </div>
    </div>
  </div>
</div>
</template>

<script setup>


import {onMounted, reactive} from "vue";
import instance from "../../api/request.js";

const dataList = reactive([])

onMounted(() =>{
  instance.get('/task/recommend').then( res => {
    if (res.data.code == '200'){
      dataList.value = res.data.list
      console.log(res.data.list)
    }
  })
})

const exitRecommend = (id) => {

}

function formatDateTimeForHMS(obj) {
  if (obj == null) {
    return null
  }
  let date = new Date(obj);
  let y = 1900 + date.getYear();
  let m = "0" + (date.getMonth() + 1);
  let d = "0" + date.getDate();
  let h = "0" + date.getHours();
  let mm = "0" + date.getMinutes();
  let s = date.getSeconds();
  return y + "-" + m.substring(m.length - 2, m.length) + "-" + d.substring(d.length - 2, d.length) + " " + h.substring(h.length - 2, h.length) + ":" + mm.substring(mm.length - 2, mm.length);
}
</script>

<style scoped>
#recommend_container{
  width: 100%;
  height: 100%;
}
</style>