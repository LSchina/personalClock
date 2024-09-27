<template>
  <div id="ding_container">
    <div style="width: 100%;height: 10%;display: flex">
      <div style="display: flex;width: 100%;height: 100%;align-items: center;">
        <div style="display: flex">
          <el-input
              v-model="query.username"
              style="width: 280px;"
              size="large"
              placeholder="输入用户账号"
              :prefix-icon="Search"
          />

          <el-button @click="searchDing"  type="primary" size="large"  style="margin-left: 2%">搜索</el-button>
        </div>
        <div style="display: flex;margin-left: 150px">
          <el-input
              v-model="query.theme"
              style="width: 280px"
              size="large"
              placeholder="输入任务主题"
              :prefix-icon="Search"
          />
          <el-button  @click="searchDing"   type="primary" size="large"  style="margin-left: 2%">搜索</el-button>
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
        <el-table-column  prop="timeCou" label="打卡所用时间" width="180">
        </el-table-column>
        <el-table-column  label="打卡时间" width="180" >
          <template #default="scope">{{ formatDateTimeForHMS(scope.row.finishTime) }}</template>
        </el-table-column>
        <el-table-column prop="comment"   label="任务内容" width="250" >
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
          @change="searchDing"
          :total="38"/>
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
  theme:'',
  pageNo: 1,
  pageSize: 7
})

const searchDing = () => {
  instance.post('/dingb/page',query).then(res =>{
    console.log(query)
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      total.value = res.data.page.total
      console.log(res.data.page)
    }
  })
}

onMounted(() =>{
  instance.post('/dingb/page',query).then(res =>{
    console.log(query)
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      total.value = res.data.page.total
      console.log(res.data.page)
    }
  })
})

const handleDelete = (index, row) => {
  instance.delete('/dingb/delete'+ row.id).then(res =>{
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
#ding_container{
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  padding: 20px;
}
</style>