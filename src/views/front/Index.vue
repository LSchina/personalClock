<template>
  <div id="index_container">
    <van-swipe style="width: 100%;height: 15%;" :autoplay="3000" height="200" lazy-render>
      <van-swipe-item v-for="item in imageList.value" :key="item">
        <van-image :src="item.image"/>
      </van-swipe-item>
    </van-swipe>
    <van-cell
        style="width: 100%;margin-bottom: 0.5rem;height: 3rem;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-vertical-padding: 5rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem"
        title="系统推荐任务(点击即可添加)"
        center="center"
        size="large"/>
    <van-cell
        v-for="item in recList.value"
        style="width: 100%;height: 3rem;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-vertical-padding: 5rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem"
        :title="item.name"
        center="center"
        @click="addRec(item.id)"
        size="large"/>
    <van-floating-bubble
        axis="xy"
        icon="plus"
        magnetic="x"
        @offset-change="onOffsetChange"
        @click="addTask"
    />
    <div style="width: 100%;height: 8%;display: flex;justify-content: center;align-items: center">
      <div style="font-size: 1.5rem;letter-spacing: 0.2rem">功能列表</div>
    </div>
    <van-grid style="width: 100%;height: 30%" :column-num="2">
      <van-grid-item v-for="item in iconList" style="--van-grid-item-icon-size: 2.5rem;--van-grid-item-text-font-size: 1.05rem" :key="value" :icon="item.icon" :text="item.value"/>
    </van-grid>
    <div style="width: 100%;height: 8%;display: flex;justify-content: center;align-items: center">
      <div style="font-size: 1.5rem;letter-spacing: 0.2rem">App简介</div>
    </div>
    <div style="width: 100%;height: 25%;padding: 5vh 5vw 5vh 5vw;box-sizing: border-box;background-color: white">
      <div style="width: 100%;height: 100%;letter-spacing: 0.1rem;line-height: 1.7rem">
        <div style="color: #706f6f">简介：</div>
        <div style="font-size: 1.15rem;">个人学习打卡系统是一种记录个人学习时间和进度的工具，通过持续记录学习时长、学习内容和学习目标的完成情况，帮助个人保持学习的连续性和动力。</div>
        <div  style="color: #706f6f">意义：</div>
        <div style="font-size: 1.15rem;">可以帮助用户更好地规划和管理自己的学习，提高学习的效果和成果。</div>
      </div>
    </div>
  </div>
  <van-toast v-model:show="show" style="padding: 5rem">
    <template #message>
      操作成功
    </template>
  </van-toast>
</template>

<script setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";

const router = useRouter()

const imageList = reactive([])

const iconList = reactive([
  {
    icon:'todo-list-o',
    value:'打卡计时'
  },
  {
    icon:'award-o',
    value:'任务打卡'
  },
  {
    icon:'manager-o',
    value:'个人信息'
  },
  {
    icon:'chart-trending-o',
    value:'任务分析'
  },
  {
    icon:'records-o',
    value:'添加任务'
  },
  {
    icon:'newspaper-o',
    value:'系统推荐'
  }
])

const addTask = () => {
  router.push('/front/add')
}

const addRec = (id) => {
  instance.post('/task/add/' + id).then(res => {
    if (res.data.code == '200') {
      show.value = true
    }
  })
}

onMounted(() =>{
  instance.get('/poster/list').then(res =>{
    if (res.data.code == '200'){
      imageList.value = res.data.list
    }
  })
})

const recList = reactive([])

onMounted(() =>{
  instance.get('/task/RecList').then(res =>{
    if (res.data.code == '200'){
      recList.value = res.data.list
      console.log(recList)
    }
  })
})

</script>

<style scoped>
#index_container{
  width: 100%;
  height: 200%;
  background-color: #efefef;

}
</style>