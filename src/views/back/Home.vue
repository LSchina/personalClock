<template>
<div id="home_container">
  <div style="width: 100%;height: 7%;background-color: #4d317a;display: flex;justify-content: space-between">
    <div style="margin-left: 2%;width: 68%;height: 100%;text-align: left;display: flex;align-items: center">
      <div style="letter-spacing: 2px;font-size: 25px;color: #e5d4f8;font-weight: bolder;font-family: 华文彩云">个人学习养成后台</div>
    </div>
    <div style="box-sizing: border-box;width: 12%;height: 100%;display: flex;justify-content: space-between;padding: 5px 10px 5px 10px">
      <div style="height: 100%;width: 50%;display: flex">
        <div style="width: 100%;margin: auto;text-align: right;font-size: 17px;letter-spacing: 1px;color: white">{{ useData.nickname }}</div>
      </div>
      <div style="height: 100%;width: 50%;display: flex" @click="goLogout">
        <el-avatar
            style="margin: auto"
            size="20"
          :src="useData.avater"
      /></div>
    </div>
  </div>

  <div style="margin-top: 0.05%;height: 92.95%;display: flex">
    <el-col :span="4">
      <el-menu
          router
          style="height: 70%"
          active-text-color="#ffd04b"
          background-color="#4d317a"
          class="el-menu-vertical-demo"
          default-active="2"
          text-color="#fff"
      >
        <el-menu-item index="/back/index">
          <el-icon>
            <House/>
          </el-icon>
          <span style="font-size: 20px;letter-spacing: 1px;font-family: 楷体">主页</span>
        </el-menu-item>
        <el-sub-menu index="1">
          <template #title>
            <el-icon>
              <User/>
            </el-icon>
            <span style="font-size: 20px;letter-spacing: 1px;font-family: 楷体">用户管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item style="font-size: 20px;letter-spacing: 1px;font-family: 楷体" index="/back/user">用户管理</el-menu-item>
            <el-menu-item style="font-size: 20px;letter-spacing: 1px;font-family: 楷体" index="/back/admin">管理员管理</el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title>
            <el-icon>
              <IconMenu/>
            </el-icon>
            <span style="font-size: 20px;letter-spacing: 1px;font-family: 楷体">任务管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item style="font-size: 20px;letter-spacing: 1px;font-family: 楷体" index="/back/recommend">系统推荐任务</el-menu-item>
            <el-menu-item style="font-size: 20px;letter-spacing: 1px;font-family: 楷体" index="/back/task">用户任务管理</el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>

        <el-menu-item index="/back/poster">
          <el-icon>
            <document/>
          </el-icon>
          <span style="font-size: 20px;letter-spacing: 1px;font-family: 楷体">打卡记录管理</span>
        </el-menu-item>
        <el-menu-item index="/back/system">
          <el-icon>
            <setting/>
          </el-icon>
          <span style="font-size: 20px;letter-spacing: 1px;font-family: 楷体">系统管理</span>
        </el-menu-item>
      </el-menu>
      <div style="width: 100%;height: 30%;background-color: #4d317a;display: flex">
        <div @click="goLogout" style="margin: auto;width: 40%;height: 40%;display: flex">
          <el-icon style="margin: auto;cursor: pointer" size="40" color="white">
            <SwitchButton/>
          </el-icon>
        </div>
      </div>
    </el-col>
    <el-col style="background-color: #f5f5f5;padding: 10px 10px 0 10px" :span="20">
      <router-view/>
    </el-col>
  </div>
</div>

</template>

<script setup>
import useDataStore from "../../store/index.js";
import {useRouter} from "vue-router";
import {
  Document,
  Menu as IconMenu,
  Setting,
    House,
    User,
  SwitchButton
} from '@element-plus/icons-vue'
import {ref} from "vue";
const useData = useDataStore()
const router = useRouter()


const goLogout = () => {
  router.push('/backLogin')
}

</script>

<style scoped>
#home_container{
  width: 100%;
  height: 100%;
}
</style>