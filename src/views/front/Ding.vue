<template>
<div id="ding_container">
  <div style="width: 100%;height: 8%;">
  <van-search
      v-model="query.theme"
      show-action
      placeholder="请输入任务主题"
      @search="queryDing"
  >
    <template #action>
      <div @click="queryDing">搜索</div>
    </template>
  </van-search>
  </div>
  <div style="width: 100%;height: 7%;background-color: white;box-sizing: border-box;padding: 0 5vw 0 5vw;display: flex;justify-content: space-between">
    <div style="width: 20%;height: 100%;display: flex"><div style="margin: auto">任务名称</div></div>
    <div style="width: 20%;height: 100%;display: flex"><div style="margin: auto">共计用时</div></div>
  </div>
  <div style="margin-top: 3%;width: 100%;height: 73%">
    <van-cell
        v-for="item in dingList.value"
        style="width: 100%;height: 9.1%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem"
        :title="item.name"
        center="center"
        :value="item.timeCou"
        size="large"/>
  </div>
  <div style="width: 100%;height: 10%;display: flex;justify-content: center;align-items: center">
    <van-pagination v-model="query.pageNo" :show-page-size="pageSize" :total-items="total" @change="queryDing">
      <template #prev-text>
        <van-icon size="1.5rem" name="arrow-left"/>
      </template>
      <template #next-text>
        <van-icon size="1.5rem" name="arrow"/>
      </template>
    </van-pagination>
  </div>
</div>
</template>

<script setup>


import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";

const pageSize = ref(0)

const total = ref(0)

const query = reactive({
  theme:'',
  pageSize: 11,
  pageNo: 1
})

const dingList = reactive([])

const queryDing = () => {
  instance.post('/fding/getding',query).then(res =>{
    if (res.data.code == '200'){
      console.log(res.data.page)
      pageSize.value = res.data.page.pages
      total.value = res.data.page.total
      dingList.value = res.data.page.list
    }
  })
}

onMounted(() =>{
  instance.post('/fding/getding',query).then(res =>{
    if (res.data.code == '200'){
      console.log(res.data.page)
      pageSize.value = res.data.page.pages
      total.value = res.data.page.total
      dingList.value = res.data.page.list
    }
  })
})
</script>

<style scoped>
#ding_container{
  width: 100%;
  height: 100%;
}
</style>