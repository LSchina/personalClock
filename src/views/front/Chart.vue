<template>
<div id="chart_container">
  <div style="display: flex;justify-content: center;align-items: center;padding: 1vh 8vw 1vh 8vw;box-sizing: border-box;width: 100%;height: 12%;background-color: white;margin-top: 1%;border-radius: 0.5rem">
    <div style="letter-spacing: 0.2rem">
      今日截止到{{ formattedTime }}的打卡任务数据智能分析
    </div>
  </div>

  <div style="width: 100%;height: 39.5%;display: flex;flex-wrap: wrap;margin-top: 10px;background-color: white;border-radius: 0.5rem">
    <div style="display: flex;justify-content: center;align-items: center;box-sizing: border-box;padding-top: 5px;padding-left: 10px" ref="chartContainers" class="chart-container">
      <v-chart style="width: 100px;height: 100px" :option="chart" autoresize></v-chart>
    </div>
  </div>

  <div style="width: 100%;margin-top: 10px;height: 40.5%;display: flex;justify-content: center;align-items: center;background-color: white;border-radius: 0.5rem">
    <div style="display: flex;justify-content: center;align-items: center;box-sizing: border-box;padding-top: 5px;padding-left: 10px" ref="chartTwoContainers" class="chart-container">
      <v-chart :option="chartTwo" autoresize></v-chart>
    </div>
  </div>
</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';
import VChart from 'vue-echarts';
import instance from "../../api/request.js";
// 获取当前时间并格式化
function getCurrentTime() {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  const seconds = String(now.getSeconds()).padStart(2, '0');
  return ` ${year}-${month}-${day} ${hours}:${minutes} `;
}

// 初始化时间
const formattedTime = ref(getCurrentTime());

const chart = ref({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  title: {
    text: '打卡完成情况智能分析',
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: ['进行中', '已完成', '已过期', '任务数', '打卡数']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [120, 200, 150, 80, 70],
      type: 'bar'
    }
  ]
})

const chartTwo = ref( {
  title: {
    text: '打卡效率智能分析',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    top: '87%',
    left: 'center'
  },
  series: [
    {
      name: formattedTime,
      type: 'pie',
      radius: '50%',
      data: [
        { value: 735, name: '0-10min' },
        { value: 600, name: '10-20min' },
        { value: 800, name: '20-25min' },
        { value: 1048, name: '25min~' },
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
})

const chartOne = () => {
  instance.get('/chart/one').then(res => {
    if (res.data.code == '200') {
      chart.value.series[0].data = res.data.list
    }
    initCharts()
  })
  instance.get('/chart/two').then(res => {
    if (res.data.code == '200') {
      for (let i = 0; i < res.data.list.length; i++) {
        chartTwo.value.series[0].data[i].value = res.data.list[i]
      }
    }
    initCharts()
  })
}

const chartContainers = ref(null);

const chartTwoContainers = ref(null);



const initCharts = () => {
  const chartInstance = echarts.init(chartContainers.value);
  const chartInstanceTwo = echarts.init(chartTwoContainers.value);
  chartInstance.setOption(chart.value);
  chartInstanceTwo.setOption(chartTwo.value)
  // 自动调整图表大小
  window.addEventListener('resize', () => {
    chartInstance.resize();
  })
};

onMounted( () => {
  chartOne();
})

</script>

<style scoped>
#chart_container{
  width: 100%;
  height: 100%;
}
.chart-container {
  width: 100%;
  height: 100%;
}
</style>