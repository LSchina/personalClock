<template>
<div @click="goLogin" id="theme_container">
  <div style="width: 100%;height: 80%;display: flex;justify-content: center;align-items: center">
    <div>
      <van-image
          round
          width="8rem"
          height="8rem"
          src="https://img95.699pic.com/element/40135/4581.png_300.png"
      />
    </div>
  </div>
    <div style="letter-spacing: 0.2rem;width: 100%;height: 20%">
      <div style="width: 100%;height: 30%;display: flex;justify-content: center;align-items: center;font-size: 1.3rem;font-weight: bolder;font-family: 楷体">自律~让你离成功更近一步</div>
      <div style="width: 100%;height: 30%;display: flex;justify-content: center;align-items: center;">（点击前往下一页面）</div>
    </div>

</div>
</template>

<script setup>
import {useRouter} from "vue-router";

const router = useRouter()


const goLogin = () => {
  router.push('/login')
}

</script>

<style scoped>
#theme_container{
  width: 100vw;
  height: 100vh;
  background-color: white;
}
</style>