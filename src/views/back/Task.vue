<template>
  <div id="task_container">
    <div style="width: 100%;height: 10%;display: flex">
      <div style="display: flex;width: 100%;height: 100%;align-items: center;justify-content: space-between">
        <div style="display: flex">
          <el-input
              v-model="query.username"
              style="width: 280px;--el-input-border-color: white"
              size="large"
              placeholder="输入用户账号"
              :prefix-icon="Search"
          />

          <el-button @click="searchTask"  type="primary" size="large"  style="margin-left: 2%">搜索</el-button>
        </div>
        <div style="display: flex">
          <el-input
              v-model="query.theme"
              style="width: 280px;--el-input-border-color: white"
              size="large"
              placeholder="输入任务主题"
              :prefix-icon="Search"
          />
          <el-button  @click="searchTask"   type="primary" size="large"  style="margin-left: 2%">搜索</el-button>
        </div>
        <div style="display: flex">
          <el-input
              v-model="query.name"
              style="width: 280px;--el-input-border-color: white"
              size="large"
              placeholder="输入任务名称"
              :prefix-icon="Search"
          />
          <el-button  @click="searchTask"   type="primary" size="large"  style="margin-left: 2%">搜索</el-button>
        </div>
      </div>
    </div>
    <div style="width: 100%;height: 80%;">
      <el-table
          :row-style="{'height':'55px'}"
          :data="tableData.value" border style="width: 100%">
        <el-table-column type="index" width="100" />
        <el-table-column prop="name" label="任务名称" width="180" />
        <el-table-column prop="username" label="用户账号" width="180" />
        <el-table-column prop="theme" label="任务主题" width="180" />
        <el-table-column label="任务剩余时间" width="180">
          <template #default="scope">
            {{scope.row.duration <= 0 ? 0: scope.row.duration}}
          </template>
        </el-table-column>
        <el-table-column  label="创建时间" width="180" >
          <template #default="scope">{{ formatDateTimeForHMS(scope.row.createTime) }}</template>
        </el-table-column>
        <el-table-column prop="status"   label="状态" width="180">
          <template #default="scope">
        <span>
            {{statusList(scope.row.status)}}
        </span>
          </template>
        </el-table-column>
        <el-table-column prop="comment" label="任务内容" width="250" >
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
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
          @change="searchTask"
          :total="20"/>
    </div>
  </div>

</template>

<script setup>
import {
  Search
} from '@element-plus/icons-vue'
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";
import {ElMessage} from "element-plus";

const total = ref(100)

const tableData = reactive([])

const query = reactive({
  username:'',
  name:'',
  theme:'',
  pageNo: 1,
  pageSize: 8
})

const statusList = (id) => {
  switch (id) {
    case 1:
      return '进行中'
    case 2:
      return '已结束'
    case 3:
      return '已过期'
  }
}

const searchTask = () => {
  instance.post('/btask/page',query).then(res =>{
    console.log(query)
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      total.value = res.data.page.total
      console.log(res.data.page)
    }
  })
}

onMounted(() =>{
  instance.post('/btask/page',query).then(res =>{
    console.log(query)
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      total.value = res.data.page.total
      console.log(res.data.page)
    }
  })
})

const handleDelete = (index, row) => {
  instance.delete('/btask/delete/'+ row.id).then(res =>{
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
#task_container{
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  padding: 20px;
}
</style>