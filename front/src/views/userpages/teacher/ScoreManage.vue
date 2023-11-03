<template>
  <div class="appmain">
    <div class="appcart">
      <p class="title">成绩录入</p>


      <SelectBlock atr="课程" describe="请选择课程" v-model="curCourse">
        <li v-for="item in allCourse" :value="item.id" :key="item.id">
          {{ item.name }}
        </li>
      </SelectBlock>
      <table class="apptable revarse-table">
        <tr>
          <th>学号</th>
          <th>姓名</th>
          <th>班级</th>
          <th>学院</th>
          <th>签到得分</th>
          <th>作业得分</th>
          <th>实验得分</th>
          <th>考试得分</th>
          <th>平时成绩</th>
          <th>总成绩</th>
          <th>操作</th>
        </tr>
        <tr v-show="courseList.length === 0">
          <td class="nodata" colspan="5"> No Data </td>
        </tr>
        <tr v-for="item in courseList" :key="item.id">
          <td>{{ item.sno }}</td>
          <td>{{ item.username }}</td>
          <td>{{ item.cls }}</td>
          <td>{{ item.college }}</td>
          <td>{{ item.signScore }}</td>
          <td>{{ item.workScore }}</td>
          <td>{{ item.expScore }}</td>
          <td>{{ item.examGrade }}</td>
          <td>{{ item.usualGrade }}</td>
          <td>{{ item.totalGrade?.toFixed(1) }}</td>
          <td>
            <i class="update-bt" @click="showUpdate(item)">修改</i>
          </td>
        </tr>
      </table>

      <el-dialog v-model="updateStuScoreShow" width="300px">
        <el-form>
          <el-form-item label="签到成绩:">
            <el-input v-model="updateStuScoreParams.signScore" label-width="80px"></el-input>
          </el-form-item>
          <el-form-item label="作业成绩:">
            <el-input v-model="updateStuScoreParams.workScore" label-width="80px"></el-input>
          </el-form-item>
          <el-form-item label="实验成绩:">
            <el-input v-model="updateStuScoreParams.expScore" label-width="80px"></el-input>
          </el-form-item>
          <el-form-item label="考试成绩:">
            <el-input v-model="updateStuScoreParams.examGrade" label-width="80px" placeholder="满分100"></el-input>
          </el-form-item>
        </el-form>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="updateStuScoreShow = false">取消</el-button>
            <el-button type="primary" @click="updateStuScore">
              确定
            </el-button>
          </span>
        </template>
      </el-dialog>

    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref, watch } from 'vue';

import { reGetTeacherStudents, reGetMyCourses,reUpdateStuScore } from "@/api/common";

import { useUserStore } from "@/store";

import Bus from "@/utils/bus";

import SelectBlock from "@/components/SelectBlock.vue";

const userStore = useUserStore();

const courseList = ref([])

const curCourse = ref("")

const updateStuScoreShow = ref(false)

const updateStuScoreParams = ref({})

const allCourse = reactive([])

async function getData() {
  const res = await reGetTeacherStudents(userStore.userId, curCourse.value);
  if (res.status && res.status === 200) {
    courseList.value = res.data
  }
}

async function getAllCourse() {
  const res = await reGetMyCourses(userStore.userId);
  allCourse.push(...res.data)
}

function showUpdate(row) {
  updateStuScoreParams.value = row
  updateStuScoreShow.value = true
}

async function updateStuScore() {
  updateStuScoreParams.value.courseId = getCourseIdByName(curCourse.value);
  const res = await reUpdateStuScore(updateStuScoreParams.value.sno, updateStuScoreParams.value);
  if (res.status && res.status === 200) {
    Bus.$emit("popMes", { type: "success", text: "录入成功" });
    getData();
  }
  updateStuScoreShow.value = false;
}

onMounted(() => {
  getAllCourse();
});

watch(curCourse, () => {
  getData();
})

function getCourseIdByName(cname) {
  let course = allCourse.find(item => item.name === cname)
  return course.id
}

</script>

<style lang="less" scoped>
.title {
  color: rgb(148, 148, 148);
  font-size: calc(@baseSize * 1.2);
  font-weight: bolder;
  width: 80px;
  margin: auto;
  margin-bottom: 16px;
}

.reserve-bt {
  padding: 10px 20px;
  background-color: @theme-main-color1;
  color: white;
  font-size: calc(@baseSize * 1.2);
  border: 0px;
  border-radius: 5px;
  cursor: pointer;

  &:hover {
    background-color: @theme-main-color2;
  }
}

.apptable {
  tr:hover {
    background: rgb(245, 245, 245);
  }

  .time-slot {
    width: 20%;

    .timeslot-span {
      display: inline-block;
      font-size: 12px;
      background-color: rgb(243, 241, 241);
      outline: 1px solid rgb(218, 216, 216);
      color: rgb(129, 129, 129);
      padding: 3px 8px;
      margin: 2px 6px;
      border-radius: 2px;
    }
  }

  .check {
    display: inline-block;
    padding: 6px 14px;
    border-radius: 4px;
    text-align: center;
    font-size: 12px;
  }

  .check-true {
    background-color: rgb(235, 255, 236);
    color: rgb(91, 173, 95);
  }

  .check-false {
    background-color: rgb(255, 240, 240);
    color: rgb(218, 114, 114);
  }

  .check-wait {
    background-color: rgb(236, 245, 255);
    color: rgb(107, 172, 247);
  }

  .detail-bt,
  .remove-bt {
    display: inline-block;
    font-size: 12px;
    margin: 0px 12px;
    padding: 6px 12px;
    border-radius: 2px;
    cursor: pointer;
    background-color: rgb(233, 143, 143);
    color: rgb(255, 255, 255);
  }

  .update-bt {
    display: inline-block;
    font-size: 12px;
    margin: 0px 12px;
    padding: 6px 12px;
    border-radius: 2px;
    cursor: pointer;
    background-color: rgb(164, 169, 232);
    color: rgb(255, 255, 255);
  }

  .detail-bt {
    background-color: @theme-main-color1;
  }
}

.add-reserve {
  width: 680px;

  .time-label {
    display: inline-block;
    margin: 0px 12px;
    padding: 6px 12px;
    border-radius: 2px;
    background-color: rgb(236, 245, 255);
    color: rgb(107, 172, 247);

    .label-close {
      margin-left: 10px;
      font-size: 10px;
      cursor: pointer;
    }
  }

  .remark-textarea {
    border: 0;
    outline: 1px solid rgb(216, 216, 216);
    border-radius: 5px;
    padding: 4px;
    width: 400px;
    max-width: 600px;
    height: 100px;
    max-height: 180px;

    &::placeholder {
      color: rgb(218, 218, 218);
    }
  }
}

:deep(.select-wrap) {
  margin-right: 16px;

  .select {
    width: 126px;
  }
}

.rese-detail {
  padding-bottom: 26px !important;
  width: fit-content;
  height: fit-content;
}

.addteacher-div {
  display: flex;
  flex-direction: column;
  width: fit-content;
  height: fit-content;
}

.detailTable {

  tr,
  th,
  td {
    border: 1px solid black;
  }

  border-collapse: collapse;
  font-weight: 600;
  font-size: large;
  text-align: center;
}
</style>