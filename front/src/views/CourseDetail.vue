<template>
  <div>
    <div ref="chartElRef" class="chart"></div>
  </div>
</template>

<script setup>
import * as echarts from "echarts";
import { ref, onMounted } from 'vue';

import { getCourseScore } from "@/api/common";
import { useRoute } from 'vue-router';

const route = useRoute();

let courseId = route.params.courseId;
let courseName = route.params.courseName;

const chartElRef = ref();
const chartRef = ref();

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
