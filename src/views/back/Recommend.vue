<template>
<div id="recommend_container">
  <el-card style="width: 100%;height: 20%;border-radius: 20px;display: flex">
    <div style="margin: auto;letter-spacing: 3px;font-size: 20px;text-align: center">系统推荐任务作为前端登录页面的门面，是勾起消费者兴趣的保障,嘿嘿嘿</div>
  </el-card>
  <div style="width: 100%;height: 70%;display: flex;justify-content: space-between;margin-top: 3%">
    <el-card
        style="letter-spacing: 3px;font-size: 15px;color: black;width: 40%;height: 100%;border-radius: 20px;box-sizing: border-box;padding: 10px"
        v-for="item in dataList.value"
        :key="item"
    >
      <el-form :label-position="'top'">
      <el-form-item label="任务主题" >
        <el-input    disabled v-model="item.name" />
      </el-form-item>
      <el-form-item label="任务名称" label-position="top">
        <el-input      disabled v-model="item.theme" />
      </el-form-item>
      <el-form-item    disabled label="任务内容" :label-position="'top'">
        <el-input    disabled v-model="item.comment" />
      </el-form-item>
      <el-form-item label="创建时间" label-position="top">
        <el-input disabled v-model="item.createTime" />
      </el-form-item>
      <div style="width: 100%;height: 10%;display: flex;justify-content: center;align-items: center;">
        <el-button @click="getRecommendData(item.id)" style="width: 100%;height: 100%"><div style="letter-spacing: 2px">修改系统推荐任务</div></el-button>
      </div>
      </el-form>
    </el-card>
  </div>
</div>

  <el-dialog v-model="dialogFormVisible" style="height: 300px;" title="修改系统推荐任务信息" width="700">
    <el-form :model="exitForm">
      <el-form-item label="任务主题"  :label-width="180">
        <el-input style="width: 300px" v-model="exitForm.theme"/>
      </el-form-item>
      <el-form-item label="任务名称" :label-width="180">
        <el-input  style="width: 300px" v-model="exitForm.name"/>
      </el-form-item>
      <el-form-item label="任务内容" :label-width="180">
        <el-input
            v-model="exitForm.comment"
            style="width: 300px"
            :rows="2"
            type="textarea"
            placeholder="请输入您的学习内容"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="exitRecommend">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ElMessage } from 'element-plus'

import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";

const dataList = reactive([])

const exitForm = reactive({
  id:'',
  theme:'',
  name:'',
  comment:''
})

const dialogFormVisible = ref(false)

onMounted(() =>{
  instance.get('/btask/recommend').then( res => {
    if (res.data.code == '200'){
      dataList.value = res.data.list
      console.log(res.data.list)
    }
  })
})

const getRecommendData = (id) => {
  dialogFormVisible.value = true
  exitForm.id = id
  instance.get('/btask/get/' + id).then(res => {
    if (res.data.code == '200') {
      exitForm.name = res.data.task.name
      exitForm.theme = res.data.task.theme
      exitForm.comment = res.data.task.comment
    }
  })
}

const exitRecommend = () => {
  instance.post('/btask/exit',exitForm).then( res =>{
    if (res.data.code == '200'){
      ElMessage({
        message: '修改成功！',
        type: 'success',
      })
    }
  })
  dialogFormVisible.value = false
  location.reload()
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
  box-sizing: border-box;
  padding: 30px;
}
</style>