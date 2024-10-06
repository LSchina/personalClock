<template>
  <div id="savePoster_container">
    <el-form :model="form" label-width="auto" style="max-width: 600px">
      <el-form-item label="url" :label-width="70">
          <el-input style="--el-input-border-color: #c7c5c5" v-model="form.url" autocomplete="off" />
        </el-form-item>
        <el-form-item label="广告描述" :label-width="70">
          <el-input style="--el-input-border-color: #c7c5c5" v-model="form.posterDesc" type="textarea" autocomplete="off" />
        </el-form-item>
        <el-form-item label="图片" :label-width="70">
          <el-upload
              class="avatar-uploader"
              action=""
              :show-file-list="true"
              :file-list="fileList"
              :http-request="uploadFile"
          >
            <img style="object-fit: fill"  v-if="form.image" :src="form.image" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
      <el-form-item style="margin-left: 70px">
        <el-button type="primary" @click="handleSave">添加</el-button>
        <el-button @click="handleCancle">取消</el-button>
      </el-form-item>
      </el-form>

  </div>

</template>

<script setup>
import instance from "../../api/request.js";
import {ElMessage} from "element-plus";
import {reactive, ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter()


const form = reactive({
  id:'',
  url:'',
  image:'',
  posterDesc:'',
})

const fileList = ref([])

const uploadFile = (v) => {
  form.image = v.file
}

const handleSave = () => {
  let formData = new FormData();
  formData.append('file',form.image)
  formData.append('url',form.url)
  formData.append('posterDesc',form.posterDesc)
  let config = {
    headers: {
      "Content-Type": "multipart/form-data",
    }
  }
  instance.post('/poster/addPoster',formData,config).then( res => {
    if (res.data.code == '200'){
      ElMessage({
        message: '添加成功',
        type: 'success',
      })
    }else {
      ElMessage({
        message: '系统错误！！！',
        type: 'error',
      })
    }
  })
  dialogFormVisible.value = false
  fileList.value = []
}

const handleCancle = () => {
  router.push('/back/poster')
}

</script>

<style scoped>
#savePoster_container{
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  padding: 20px;
}
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader{
  border: 1px solid #c7c5c5;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}
.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #c7c5c5;
  width: 200px;
  height: 178px;
  text-align: center;
}

</style>