<template>
<div id="back_rank">
  <div style="width: 100%;height: 10%;display: flex">
    <div style="display: flex;width: 70%;height: 100%;align-items: center;justify-content: space-between">
      <div style="display: flex">
        <el-button    type="primary" size="large"  style="margin-left: 0px ;margin-bottom: 10px">添加赛季</el-button>
      </div>
    </div>
  </div>
  <div style="width: 100%;height: 80%;">
    <el-table
        :row-style="{'height':'55px'}"
        :data="tableData.value" border style="width: 100%">
      <el-table-column type="index" width="100" />
      <el-table-column prop="name" label="赛季名称" width="180" />
      <el-table-column  label="创建时间" width="180" >
        <template #default="scope">{{ formatDateTimeForHMS(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column  label="开始时间" width="180" >
        <template #default="scope">{{ formatDateTimeForHMS(scope.row.beginTime) }}</template>
      </el-table-column>
      <el-table-column  label="结束时间" width="180" >
        <template #default="scope">{{ formatDateTimeForHMS(scope.row.overTime) }}</template>
      </el-table-column>

      <el-table-column label="操作"  width="100" >
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
</div>
</template>

<script setup>

import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";
import {ElMessage} from "element-plus";

const value = ref('')
const options = ref([])

const seasonList = () => {
  instance.get('/admin/season').then( res => {
    if (res.data.code == '200'){
      console.log(res.data)
      tableData.value = res.data.season
      for (let i = 0; i < res.data.season.length; i++) {
        options.value.push({
          value: res.data.season[i].id,
          label: res.data.season[i].name
        })
      }
    }
  })
}

const query = reactive({
  username:'',
  theme:'',
  pageNo: 1,
  pageSize: 9
})


const tableData = reactive([])


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


onMounted( () => {
  seasonList()
})

</script>

<style scoped>
#back_rank{
  width: 100%;
  height: 100%;
}
</style>