<template>
  <div id="system_container">
    <div  style="width: 100%;height: 17vh;background-color: white;display: flex;justify-content: space-between">
      <div style="width: 70%;height: 100%;display: flex">
        <div style="width: 60%;height: 100%;display: flex;justify-content: center;align-items: center">
          <el-upload
              action=""
              :show-file-list="false"
              :http-request="uploadFile"
          >
          <van-image
            round
            width="7rem"
            height="7rem"
            :src="useData.avater"
        />
          </el-upload>
        </div>
        <div style="width: 40%;height: 100%;display: flex;align-items: center;justify-content: left">
          <div style="font-size: 1.15rem;letter-spacing: 0.15rem">{{ useData.nickname }}</div>
        </div>
      </div>
      <div @click="goExitMe"
          style="width: 20%;height: 100%;display: flex;align-items: center;justify-content: center">
        <van-icon size="25" name="arrow"/>
      </div>
    </div>
    <div style="width: 100%;height: 52vh;display: flex;">
      <van-cell-group style="width: 100%;height: 79%;margin: auto" inset>
        <van-cell icon="" style="width: 100%;height: 16.66%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="点击头像进行修改" size="large"/>
        <van-cell @click="goExitMe" icon="contact-o" style="width: 100%;height: 16.66%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="个人信息" is-link  size="large"/>
        <van-cell @click="goChart" icon="records-o" style="width: 100%;height: 16.66%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="任务分析" is-link  size="large"/>
        <van-cell @click="goRank" icon="medal-o" style="width: 100%;height: 16.66%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="学习天梯榜" is-link  size="large"/>
        <van-cell @click="goDing" icon="home-o" style="width: 100%;height: 16.66%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="打卡记录" is-link  size="large"/>
        <van-cell @click="goTheme" icon="close" style="width: 100%;height: 16.66%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="退出登录" is-link  size="large"/>
      </van-cell-group>
    </div>
    <div>
      <van-cell
          style="width: 100%;height: 33.33%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-vertical-padding: 5rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem"
          title="正在进行中的任务"
          center="center"
          size="large"/>
    </div>
    <div style="margin-top: 3%;margin-bottom: 3vh">
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
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";
import {useRouter} from "vue-router";
import {User} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";

const router = useRouter()

const useData = useDataStore()

const myTaskList = reactive([])

const goTheme = () => {
  router.push('/theme')
}

const goRank = () => {
  router.push('/front/rank')
}

const goExitMe = () => {
  router.push('/front/exitme')
}

const goChart = () => {
  router.push('/front/chart')
}

const goDing = () => {
  router.push('/front/ding')
}

const uploadFile = (v) => {
  useData.avater = v.file
  //上传文件
  let formData = new FormData();
  //文件追加数据
  formData.append('file', useData.avater)
  //文件变成multipart
  let config = {
    headers: {
      "Content-Type": "multipart/form-data",
    }
  }
  //接口
  instance.post('/user/uploadAvater', formData, config).then(res => {
    if (res.data.code == '200') {

      //前端显示头像
      useData.avater = res.data.avater
      ElMessage({
        message: '上传成功',
        type: 'success',
      })
    } else {
      ElMessage({
        message: '系统错误！！！',
        type: 'error',
      })
    }
  })

}
  onMounted(() => {
    instance.get('/task/nowTask').then(res => {
      if (res.data.code == '200') {
        myTaskList.value = res.data.list
      }
    })
  })


</script>

<style scoped>
#system_container{
  width: 100%;
  height: 115%;
}
</style>
