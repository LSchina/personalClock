<template>
  <div id="poster_container">
    <div style="width: 100%;height: 10%;display: flex">
      <div style="display: flex;width: 70%;height: 100%;align-items: center;justify-content: space-between">
        <div style="display: flex">
          <el-button  @click="goSavePoster"   type="primary" size="large"  style="margin-left: 0px ;margin-bottom: 10px">添加广告</el-button>
        </div>
      </div>
    </div>
    <div style="width: 100%;height: 80%;">
      <el-table
          :row-style="{'height':'55px'}"
          :data="tableData.value" border style="width: 100%">
        <el-table-column type="index" label="编号" width="100" />
        <el-table-column  label="图片" width="250">
          <template #default="scope">
            <el-image style="width: 120px;height: 60px" :src="scope.row.image"/>
            </template>
        </el-table-column>
        <el-table-column  prop="url" label="url" width="250">
        </el-table-column>
        <el-table-column prop="posterDesc" label="广告描述" width="250" />
        <el-table-column  label="创建时间" width="200" >
          <template #default="scope">{{ formatDateTimeForHMS(scope.row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">
              修改
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">
              删除
            </el-button>

          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>

  <el-dialog v-model="dialogFormVisible"    width="550" >
    <div style="width: 100%;height: 80%;">
      <el-form style="margin: auto;" :model="form" >
        <el-form-item label="url" :label-width="70">
          <el-input style="--el-input-border-color: #919090" v-model="form.url" autocomplete="off" />
        </el-form-item>
        <el-form-item label="广告描述" :label-width="70">
          <el-input  style="--el-input-border-color: #919090" v-model="form.posterDesc" type="textarea" autocomplete="off" />
        </el-form-item>
        <el-form-item label="图片" :label-width="70">
          <el-upload
              class="avatar-uploader"
              action=""
              :show-file-list="true"
              :file-list="fileList"
              :http-request="uploadFile"
          >
          <img style="background-color: fuchsia;object-fit: fill"  v-if="form.image" :src="form.image" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>

      </el-form>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="editPoster">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<script setup>
import {Plus} from '@element-plus/icons-vue'
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";

const router = useRouter()



const dialogFormVisible = ref(false)
const fileList = ref([])
const form = reactive({
  id:'',
  url:'',
  image:'',
  posterDesc:'',
  file:''
})
/**
 * 文件：。jpg 。png 。xls 。xlsx 。word 。pdf 。。。。
 * @param v
 */
const uploadFile = (v) => {
  form.file = v.file
}

const tableData = reactive([])
const imageUrl = ref(null)

const goSavePoster = () => {
  router.push('/back/savePoster')
}

const posterList = () => {
  instance.get('/admin/posterList').then( res => {
    if (res.data.code == '200'){
      tableData.value = res.data.list
    }
  })
}

const handleDelete = (index, row) => {
  instance.delete('/admin/posterDelete/'+ row.id).then(res =>{
      if (res.data.code == '200'){
        ElMessage({
          message: '删除成功',
          type: 'success',
        })
        //刷新
        posterList()
      }
    })
  }

const handleEdit = (index,row) => {
  dialogFormVisible.value = true
  instance.get('/admin/posterGet/'+ row.id).then(res =>{
    if (res.data.code == '200'){
      form.id = row.id
      form.image = res.data.poster.image
      form.posterDesc = res.data.poster.posterDesc
      form.url = res.data.poster.url
    }
  })
}
const editPoster = () => {
  let formData = new FormData();
  formData.append('id',form.id)
  formData.append('file',form.file)
  formData.append('url',form.url)
  formData.append('posterDesc',form.posterDesc)
  let config = {
    headers: {
      "Content-Type": "multipart/form-data",
    }
  }
  instance.post('/admin/editPoster',formData,config).then( res => {
    if (res.data.code == '200'){
      ElMessage({
        message: '提交成功',
        type: 'success',
      })
    }else {
      ElMessage({
        message: '系统错误！！！',
        type: 'error',
      })
    }
  })
  location.reload()
  posterList()
  dialogFormVisible.value = false
  fileList.value = []
}


onMounted( () => {
  posterList()
})

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
#poster_container{
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
  border: 1px solid #706f6f;
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
  color: #8c939d;
  width: 200px;
  height: 178px;
  text-align: center;
}
</style>