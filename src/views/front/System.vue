<template>
  <div id="system_container">
    <div @click="goExitMe" style="width: 100%;height: 20%;background-color: white;display: flex;justify-content: space-between">
      <div style="width: 70%;height: 100%;display: flex">
        <div style="width: 60%;height: 100%;display: flex">
          <van-image
              style="margin: auto"
            round
            width="7rem"
            height="7rem"
            :src="useData.avater"
        />
        </div>
        <div style="width: 40%;height: 100%;display: flex;align-items: center;justify-content: left">
          <div style="font-size: 1.15rem;letter-spacing: 0.15rem">{{ useData.nickname }}</div>
        </div>
      </div>
      <div
          style="width: 20%;height: 100%;display: flex;align-items: center;justify-content: center">
        <van-icon size="25" name="arrow"/>
      </div>
    </div>
    <div style="width: 100%;height: 30%;display: flex;">
      <van-cell-group style="width: 100%;height: 80%;margin: auto" inset>
        <van-cell icon="records-o" style="width: 100%;height: 33.33%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="任务分析" is-link  size="large"/>
        <van-cell @click="goDing" icon="home-o" style="width: 100%;height: 33.33%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="打卡记录" is-link  size="large"/>
        <van-cell @click="goTheme" icon="close" style="width: 100%;height: 33.33%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="退出登录" is-link  size="large"/>
      </van-cell-group>
    </div>
    <div>
      <van-cell
          style="width: 100%;height: 33.33%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-vertical-padding: 5rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem"
          title="正在进行中的任务"
          center="center"
          size="large"/>
    </div>
    <div style="margin-top: 3%">
      <van-cell
          v-for="item in myTaskList.value"
          style="width: 100%;height: 33.33%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem"
          :title="item.name"
          center="center"
          :value="item.duration"
          size="large"/>
    </div>
  </div>
</template>

<script setup>
import useDataStore from "../../store/index.js";
import {onMounted, reactive} from "vue";
import instance from "../../api/request.js";
import {useRouter} from "vue-router";

const router = useRouter()

const useData = useDataStore()

const myTaskList = reactive([])

const goTheme = () => {
  router.push('/theme')
}

const goExitMe = () => {
  router.push('/front/exitme')
}

const goDing = () => {
  router.push('/front/ding')
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
#system_container{
  width: 100%;
  height: 100%;
}
</style>
