<template>
  <div id="index_container">
    <div
        style="width: 100%;height: 7%;display: flex;justify-content: center;align-items: center;background-color: white;">
      <div style="letter-spacing: 0.2rem;font-size: 1.1rem;color: black">自律天堂</div>
    </div>
    <div style="width: 100%;height: 86%;padding: 0.5rem 0 0.5rem 0">
      <router-view/>
    </div>
    <van-tabbar style="width: 100%;height: 7%;" route>
      <van-tabbar-item replace to="/front/index" icon="home-o">主页</van-tabbar-item>
      <van-tabbar-item replace to="/front/task" icon="tosend">任务</van-tabbar-item>
      <van-tabbar-item replace to="/front/system" icon="setting-o">系统</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script setup>

</script>

<style scoped>
#index_container{
  width: 100vw;
  height: 100vh;
  background-color: #efefef;
}
</style>