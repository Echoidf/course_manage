<template>
  <div>
    <div ref="chartElRef" class="chart"></div>

    <!-- 展示各个级别学生的学号 -->
    <div>
      <el-table :data="tableData">
        <el-table-column prop="grade" label="成绩" />
        <el-table-column prop="snos" label="学号" />
      </el-table>
    </div>
  </div>
</template>

<script setup>
import * as echarts from "echarts";
import { ref, onMounted, reactive } from 'vue';

import { getCourseScore,getCourseSnos } from "@/api/common";
import { useRoute } from 'vue-router';

const route = useRoute();

let courseId = route.params.courseId;
let courseName = route.params.courseName;

const chartElRef = ref();
const chartRef = ref();
const tableData = reactive([])

async function getData() {
  const res = await getCourseScore(courseId);
  if (res && res.status === 200) {
    chartRef.value.setOption({
      title: {
        text: '课程详情--' + courseName,
        left: 'center',
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)',
      },
      legend: {
        orient: 'vertical',
        left: 'left',
        data: ['中等 79-70分', '良 89-80分', '优 100-90分', '不及格 60分以下', '及格 69-60分'],
      },
      series: [
        {
          name: '课程详情',
          type: 'pie',
          radius: '55%',
          center: ['50%', '60%'],
          data: [
            { value: res.data.medium, name: '中等 79-70分' },
            { value: res.data.good, name: '良 89-80分' },
            { value: res.data.excellent, name: '优 100-90分' },
            { value: res.data.fail, name: '不及格 60分以下' },
            { value: res.data.pass, name: '及格 69-60分' },
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)',
            },
          },
        },
      ],
    })
  }

  const res2 = await getCourseSnos(courseId);
  if(res2 && res2.status === 200){
    tableData.push(...res2.data)
  }
}

onMounted(() => {
  chartRef.value = echarts.init(chartElRef.value)
  // 大小自适应
  window.addEventListener("resize", () => {
    chartRef.value?.resize();
  });
  getData();
})

</script>

<style scoped>
.chart {
  height: 80vh;
}
</style>
