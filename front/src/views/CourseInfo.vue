<template>
  <div class="appmain">
    <div class="appcart">
      <p class="title">课程管理</p>

      <button class="reserve-bt" @click="showAddCourse">录入课程 +</button>

      <table class="apptable revarse-table">
        <tr>
          <th>课程名称</th>
          <th>课程编号</th>
          <th>开课学院</th>
          <th>开放年级</th>
          <th>授课教师</th>
          <th>已选人数</th>
          <th>操作</th>
        </tr>

        <tr v-show="courseList.length === 0">
          <td class="nodata" colspan="5"> No Data </td>
        </tr>

        <tr v-for="item in courseList" :key="item.id">
          <td>{{ item.name }}</td>
          <td>{{ item.cno }}</td>
          <td>{{ item.college }}</td>
          <td>{{ item.grade }}</td>
          <td>{{ item.tname }}</td>
          <td>{{ item.selectedCount }}</td>

          <td>
            <i class="detail-bt" @click="router.push({ name: 'CourseDetail', params: {courseId: item.id,courseName: item.name}})">课程详情</i>
            <i class="update-bt" @click="showUpdateCourse(item)" v-show="hasRole(`admin`)">修改</i>
            <i class="remove-bt" @click="showRemove(item.id)" v-show="hasRole(`admin`)">删除</i>
          </td>
        </tr>
      </table>

      <!-- 添加课程-->
      <div class="appmask" v-show="addCourseShow">
        <div class="mesbox add-reserve">
          <p class="title">添加课程</p>
          <i class="close-bt" @click="addCourseShow = false">X</i>

          <div class="addteacher-div">
            <div>
              <InputBlock atr="课程名称" v-model="addCourseParams.name" />
              <InputBlock atr="课程编号" v-model="addCourseParams.cno" />
            </div>
            <div>
              <InputBlock atr="开课学院" v-model="addCourseParams.college" />
              <InputBlock atr="开放年级" v-model="addCourseParams.grade" />
            </div>
            <div>
              <SelectBlock atr="课程讲师" describe="请选择讲师" v-model="addCourseParams.teacherId">
                <li v-for="item in allTeacher" :value="item.id" :key="item.id">
                  {{ item.username }}
                </li>
              </SelectBlock>
            </div>
          </div>

          <i class="comfirm-bt" @click="addCourse">确认</i>
          <i class="cancel-bt" @click="addCourseShow = false">取消</i>
        </div>
      </div>

      <!-- 修改课程-->
      <el-dialog v-model="updateCourseShow" title="修改课程" width="25%" center>
        <el-form>
          <el-form-item label="课程名称:">
            <el-input v-model="updateCourseParams.name" label-width="80px"></el-input>
          </el-form-item>
          <el-form-item label="课程编号:">
            <el-input v-model="updateCourseParams.cno" label-width="80px"></el-input>
          </el-form-item>
          <el-form-item label="开课学院:">
            <el-input v-model="updateCourseParams.college" label-width="80px"></el-input>
          </el-form-item>
          <el-form-item label="开放年级:">
            <el-input v-model="updateCourseParams.grade" label-width="80px"></el-input>
          </el-form-item>
          <el-form-item label="课程讲师:">
            <el-select v-model="updateCourseParams.tname">
              <el-option v-for="item in allTeacher" :key="item.id" :label="item.username" :value="item.username" />
            </el-select>
          </el-form-item>
        </el-form>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="updateCourseShow = false">取消</el-button>
            <el-button type="primary" @click="updateCourse">
              确定
            </el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 删除确认 -->
      <div class="appmask" v-show="removeCourseShow">
        <div class="mesbox">
          <p class="title">确认删除</p>
          <i class="comfirm-bt" @click="removeCourse()">确认</i>
          <i class="cancel-bt" @click="removeCourseShow = false">取消</i>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";

import { reGetCourseList, getTeacherList, reAddCourse, reUpdateCourse, delCourse } from "@/api/common";

import { useRouter } from "vue-router";

import { useUserStore } from "@/store";

import SelectBlock from "@/components/SelectBlock.vue";
import InputBlock from "@/components/InputBlock.vue";
import Bus from "@/utils/bus";

const courseList = ref([])

const router = useRouter();

const removeCourseShow = ref(false);
const addCourseShow = ref(false);
const updateCourseShow = ref(false);

const addCourseParams = ref({})
const updateCourseParams = ref({})
const selectedCourseId = ref()

const allTeacher = ref([])

async function getData() {
  const res = await reGetCourseList();
  if (res.status && res.status === 200) {
    courseList.value = res.data
  }
}

async function showAddCourse() {
  const teachers = await getTeacherList()
  allTeacher.value.push(...teachers.data.list)
  addCourseShow.value = true
}

async function showUpdateCourse(item) {
  const teachers = await getTeacherList()
  updateCourseParams.value = { ...item }

  allTeacher.value.push(...teachers.data.list)
  selectedCourseId.value = item.id
  updateCourseShow.value = true
}

async function addCourse() {
  const username = addCourseParams.value.teacherId;
  addCourseParams.value.teacherId = getTeacherId(username);

  const res = await reAddCourse(addCourseParams.value)
  if (res.status && res.status === 200) {
    getData()
    addCourseShow.value = false
    Bus.$emit("popMes", { type: "success", text: "添加成功" });
  }
}

async function updateCourse() {
  const username = updateCourseParams.value.tname;
  updateCourseParams.value.teacherId = getTeacherId(username);
  updateCourseParams.value.id = selectedCourseId.value;

  const res = await reUpdateCourse(updateCourseParams.value);
  if (res.status && res.status === 200) {
    getData()
    updateCourseShow.value = false
    Bus.$emit("popMes", { type: "success", text: "更新成功" });
  }
  console.log(updateCourseParams.value);
}

function hasRole(roleName) {
  const userStore = useUserStore();
  return userStore.role === roleName
}

function showRemove(id) {
  selectedCourseId.value = id
  removeCourseShow.value = true
}

async function removeCourse() {
  const res = await delCourse(selectedCourseId.value);

  if (res && res.status === 200) {
    Bus.$emit("popMes", { type: "success", text: "删除成功" });
    getData();
  }
  removeCourseShow.value = false
  selectedCourseId.value = null
}


onMounted(() => {
  getData();
});

function getTeacherId(username) {
  const teacher = allTeacher.value.find(t => t.username === username)
  return teacher.id
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