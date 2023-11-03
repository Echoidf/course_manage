<template>
  <div class="appmain">
    <div class="appcart">
      <p class="title"> 教师信息</p>

      <button class="reserve-bt" @click="addTeacherShow = true">添加教师 +</button>
      <table class="apptable revarse-table">
        <tr>
          <th>工号</th>
          <th>姓名</th>
          <th>性别</th>
          <th>年龄</th>
          <th>学院</th>
          <th>操作</th>
        </tr>
        <tr v-show="teacherList.list.length === 0">
          <td class="nodata" colspan="5"> No Data </td>
        </tr>
        <tr v-for="item in teacherList.list" :key="item.id">
          <td>{{ item.tno }}</td>
          <td>{{ item.username }}</td>
          <td>{{ item.sex ? "男" : "女" }}</td>
          <td>{{ item.age }}</td>
          <td>{{ item.college }}</td>
          <td>
            <i class="detail-bt" @click="getDetail(item.id)">授课信息</i>
            <i class="update-bt" @click="showUpdate(item)">修改</i>
            <i class="remove-bt" @click="showRemove(item.id)">删除</i>
          </td>
        </tr>
      </table>

      <!-- 新增教师 -->
      <div class="appmask" v-show="addTeacherShow">
        <div class="mesbox add-reserve">
          <p class="title">新增教师</p>
          <i class="close-bt" @click="addTeacherShow = false">X</i>

          <div class="addteacher-div">
            <div>
              <InputBlock atr="姓名" v-model="addTeacherParams.username" />
              <InputBlock atr="年龄" v-model="addTeacherParams.age" />
            </div>
            <div>
              <InputBlock atr="密码" v-model="addTeacherParams.password" />
              <InputBlock atr="工号" v-model="addTeacherParams.tno" />
            </div>
            <div>
              <InputBlock atr="学院" v-model="addTeacherParams.college" />
              <SelectBlock atr="性别" describe="请选择性别" v-model="addTeacherParams.sex">
                <li svalue="0">男性</li>
                <li svalue="1">女性</li>
              </SelectBlock>
            </div>

          </div>

          <i class="comfirm-bt" @click="addTeacher">确认</i>
          <i class="cancel-bt" @click="addTeacherShow = false">取消</i>
        </div>
      </div>

      <!-- 更新教师信息 -->
      <div class="appmask" v-show="updateTeacherShow">
        <div class="mesbox add-reserve">
          <p class="title">更新信息</p>
          <i class="close-bt" @click="updateTeacherShow = false">X</i>

          <div class="addteacher-div">
            <div>
              <InputBlock atr="姓名" v-model="updateTeacherParams.username" />
              <InputBlock atr="年龄" v-model="updateTeacherParams.age" />
            </div>

            <div>
              <InputBlock atr="工号" v-model="updateTeacherParams.sno" />
              <InputBlock atr="学院" v-model="updateTeacherParams.cls" />
            </div>
          </div>

          <i class="comfirm-bt" @click="updateTeacher">确认</i>
          <i class="cancel-bt" @click="updateTeacherShow = false">取消</i>
        </div>
      </div>

      <!-- 删除确认 -->
      <div class="appmask" v-show="removeTeacherShow">
        <!-- 因为v-show的关系?，此元素就算放v-for="item in teacherList.list"的元素中，也取不到正确的item值，所以放在这里 -->
        <div class="mesbox">
          <p class="title">确认删除</p>
          <i class="comfirm-bt" @click="removeTeacher()">确认</i>
          <i class="cancel-bt" @click="removeTeacherShow = false">取消</i>
        </div>
      </div>

      <!-- 选课详情 -->
      <div class="appmask" v-show="detailShow">
        <div class="mesbox rese-detail">
          <p class="title">授课列表</p>
          <i class="close-bt" @click="detailShow = false">X</i>

          <table class="apptable detailTable">
            <tr>
              <th>课程号</th>
              <th>课程名称</th>
              <th>开课学院</th>
              <th>年级</th>
            </tr>
            <tr v-show="teacherCourses.length === 0">
              <td class="nodata" colspan="10"> No Data </td>
            </tr>
            <tr v-for="(item, k) in teacherCourses" :key="k">
              <td>{{ item.cno }}</td>
              <td>{{ item.name }}</td>
              <td>{{ item.college }}</td>
              <td>{{ item.grade }}</td>
            </tr>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onBeforeMount, reactive, ref } from "vue";
import { getTeacherList, reGetTeacherCourses, reDeleteTeacher, reAddTeacher } from "@/api/common.js";
import SelectBlock from "@/components/SelectBlock.vue";
import InputBlock from "@/components/InputBlock.vue";
import Bus from "@/utils/bus";

const addTeacherShow = ref(false);
const updateTeacherShow = ref(false);
const removeTeacherShow = ref(false);
const detailShow = ref(false);
const teacherCourses = ref([]);
let deleteId = null;
const teacherList = reactive({
  list: [],
});
const addTeacherParams = reactive({});


const updateTeacherParams = reactive({})

//获取教师列表
async function getdata() {
  const result = await getTeacherList();
  if (result.status && result.status === 200) {
    teacherList.list = result.data.list;
  } else console.log("err", result);
}

async function addTeacher() {
  const params = { ...addTeacherParams };
  if (params.sex === "男性") {
    params.sex = 0
  } else {
    params.sex = 1
  }
  const result = await reAddTeacher(params);
  if (result.status && result.status === 200) {
    addTeacherShow.value = false;
    getdata();
    Bus.$emit("popMes", { type: "success", text: "添加成功" });
  } else {
    console.log("err", result)
  }
}

//打开确认删除的窗口
function showRemove(id) {
  removeTeacherShow.value = true;
  deleteId = id;
}

//删除元素
async function removeTeacher() {
  if (!deleteId) return console.log("删除元素id错误");
  const result = await reDeleteTeacher(deleteId);
  if (result.status && result.status === 200) {
    getdata();
    Bus.$emit("popMes", { type: "success", text: "删除成功" });
  } else console.log("删除失败", result);
  removeTeacherShow.value = false;
}

//获取详情
async function getDetail(id) {
  teacherCourses.value = [];
  detailShow.value = true;
  const result = await reGetTeacherCourses(id);
  if (result.status && result.status === 200) {
    teacherCourses.value = result.data;
  } else console.log("获取详情失败", result);
}

async function showUpdate(row) {
  updateTeacherParams.value = { ...row };
  updateTeacherShow.value = true;
}


onBeforeMount(() => {
  getdata();
});
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
