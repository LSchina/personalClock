<template>
  <div id="user_container">
    <div style="width: 100%;height: 10%;display: flex">
      <div style="display: flex;width: 70%;height: 100%;align-items: center;justify-content: space-between">
        <div style="display: flex">
          <el-input
              v-model="query.username"
              style="width: 280px;;--el-input-border-color: white"
              size="large"
              placeholder="输入您的用户名"
              :prefix-icon="Search"
          />

          <el-button @click="searchUser"  type="primary" size="large"  style="margin-left: 2%">搜索</el-button>
        </div>
        <div style="display: flex">
          <el-input
              v-model="query.nickname"
              style="width: 280px;--el-input-border-color: white"
              size="large"
              placeholder="输入您的昵称"
              :prefix-icon="Search"
          />
          <el-button  @click="searchUser"   type="primary" size="large"  style="margin-left: 2%">搜索</el-button>
        </div>
      </div>
    </div>
    <div style="width: 100%;height: 80%;">
      <el-table
          :row-style="{'height':'55px'}"
          :data="tableData.value" border style="width: 100%">
        <el-table-column type="index" width="100" />
        <el-table-column prop="username" label="用户名" width="180" />
        <el-table-column prop="nickname" label="昵称" width="180" />
        <el-table-column label="密码" width="180" />
        <el-table-column  label="创建时间" width="180" >
          <template #default="scope">{{ formatDateTimeForHMS(scope.row.createTime) }}</template>
        </el-table-column>
        <el-table-column prop="stataus"   label="状态" width="180" >
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
              Edit
            </el-button>
            <el-button
                size="small"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
            >
              Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="width: 100%;height: 10%;display: flex;align-items: end">
      <el-pagination
          :page-size="query.pageSize"
          v-model:current-page="query.pageNo"
          style="margin: auto"
          background
          layout="prev, pager, next"
          @change="searchUser"
          :total="20"/>
    </div>
  </div>

  <el-dialog v-model="dialogFormVisible"  title="修改用户信息" width="500">
   <div style="display: flex;width: 100%;height: 80%;">
    <el-form style="margin: auto;" :model="userForm">
      <el-form-item label="用户名" :label-width="60">
        <el-input disabled  v-model="userForm.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="昵称" :label-width="60">
        <el-input v-model="userForm.nickname" autocomplete="off" />
      </el-form-item>
       <el-form-item label="密码" :label-width="60">
        <el-input v-model="userForm.password" show-password autocomplete="off" />
      </el-form-item>
      <el-form-item label="状态" :label-width="60">
        <el-input v-model="userForm.stataus" autocomplete="off" />
      </el-form-item>
    </el-form>
   </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import {
  Search
} from '@element-plus/icons-vue'
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";
import {ElMessage} from "element-plus";

const dialogFormVisible = ref(false)

const total = ref(100)

const userForm = reactive({
  username:'',
  nickname:'',
  password:'',
  stataus:''
})

const tableData = reactive([])

const query = reactive({
  username:'',
  nickname:'',
  pageNo: 1,
  pageSize: 8
})

const searchUser = () => {
  instance.post('/user/page',query).then(res =>{
    console.log(query)
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      total.value = res.data.page.total
      console.log(res.data.page)
    }
  })
}

onMounted(() =>{
  instance.post('/user/page',query).then(res =>{
    console.log(query)
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      total.value = res.data.page.total
      console.log(res.data.page)
    }
  })
})


const handleEdit = (index, row) => {
  dialogFormVisible.value = true
  instance.get('/user/get/'+ row.id).then(res =>{
    if (res.data.code == '200'){
      userForm.nickname = res.data.user.nickname
      userForm.username = res.data.user.username
      userForm.password = res.data.user.password
      userForm.stataus = res.data.user.stataus
    }
  })
}
const handleSave = () => {
  dialogFormVisible.value = false
  instance.post('/user/udpateUser', userForm).then(res => {
    if (res.data.code == '200') {
      ElMessage({
        message: '修改成功',
        grouping: true,
        type: 'success',
      })
    }
  })
  location.reload();
}
const handleDelete = (index, row) => {
  instance.delete('/user/delete/'+ row.id).then(res =>{
    if (res.data.code == '200'){
      ElMessage({
        message: '删除成功',
        grouping: true,
        type: 'success',
      })
      location.reload();
    }
  })
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
#user_container{
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  padding: 20px;
}
</style>