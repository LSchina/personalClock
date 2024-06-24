<template>
  <div id="container404">
    <div style="margin: auto;width: 100%;height: 100%">
      <div style="width: 100%;height: 65%;display: flex">
        <van-image style="margin: auto;width: 20rem;height: 20rem" fit="'fill'" :src="image.url"/>
      </div>
      <div style="width: 100%;height: 30%;display: flex">
        <div style="margin: auto;letter-spacing: 2px;font-size: 10rem;font-family: 华文琥珀;color: #4d317a">
          404
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>

import {reactive, ref} from "vue";

const image = reactive({
  url: 'https://img.picui.cn/free/2024/06/21/66751155be80f.png'
})

</script>

<style scoped>
#container404{
  width: 100vw;
  height: 100vh;
  display: flex;
}
</style>