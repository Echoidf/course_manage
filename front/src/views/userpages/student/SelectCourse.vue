<template>
    <div class="appmain">
        <div class="appcart">
            <SelectBlock atr="学期" describe="请选择" v-model="data.semester">
                <li>第一学期</li>
            </SelectBlock>
        </div>

        <div class="appcart courselist-wrap">
            <h2 class="title">第一学期 可选课程</h2>
            <table class="apptable">
                <tr>
                    <th>课程名称</th>
                    <th>课程编号</th>
                    <th>开课学院</th>
                    <th>开放年级</th>
                    <th>授课教师</th>
                    <th>已选人数</th>
                    <th>操作</th>
                </tr>
                <tr v-show="data.courseList.length == 0">
                    <td class="nodata" colspan="6">No Data</td>
                </tr>
                <tr v-for="item in data.courseList" :key="item.id">
                    <td>{{ item.name }}</td>
                    <td>{{ item.cno }}</td>
                    <td>{{ item.college }}</td>
                    <td>{{ item.grade }}</td>
                    <td>{{ item.tname }}</td>
                    <td>{{ item.selectedCount }}</td>
                    <td>
                        <i class="iconfont icon-zengjia1" :class="{ 'bt-active': !item.selected }" @click="confirmSelect(item)"></i>
                    </td>
                </tr>
            </table>
        </div>

        <div class="appmask" v-show="data.maskShow">
            <div class="mesbox">
                <p class="title">确认选择</p>
                <i class="comfirm-bt" @click="addCourse()">确认</i>
                <i class="cancel-bt" @click="data.maskShow = false">取消</i>
            </div>
        </div>
    </div>
</template>

<script setup>
import { onBeforeMount, reactive } from "vue";
import { reOptionCourse, reSelectOrCancelCourse } from "@/api/user.js";
import SelectBlock from "@/components/SelectBlock.vue";
import Bus from "@/utils/bus";

const data = reactive({
    courseList: [],
    semester: "第一学期",
    maskShow: false,
    selectdCourseId: null,
});

//获取课程数据
async function updataData() {
    const result = await reOptionCourse();
    if (result.status && result.status === 200) {
        data.courseList = result.data;
    } 
}

//确认选择
function confirmSelect(item) {
    if (item.selected) {
        Bus.$emit("popMes", { type: "tip", text: "此课程已选择" });
        return;
    }
    data.maskShow = true;
    data.selectdCourseId = item.id;
}

//选择课程
async function addCourse() {
    const result = await reSelectOrCancelCourse({
        action: "select",
        id:data.selectdCourseId,
    });
    data.selectdCourseId = null;
    data.maskShow = false;
    if (result.status && result.status === 200) {
        Bus.$emit("popMes", { type: "success", text: "选课成功" });
        updataData();
    } else console.log("err", result);
}

onBeforeMount(() => {
    updataData();
});
</script>

<style lang="less" scoped>
.courselist-wrap {
    .title {
        color: rgb(148, 148, 148);
        font-size: calc(@baseSize * 1.2);
        font-weight: bolder;
        width: 180px;
        margin: auto;
        margin-bottom: 16px;
    }
}

.icon-zengjia1 {
    display: inline-block;
    color: white;
    font-size: 20px;
    background-color: rgb(185, 185, 185);
    padding: 2px 12px;
    border-radius: 4px;
    cursor: pointer;
}
.bt-active {
    color: @theme-main-color1;
    background-color: white;
    outline: 1px solid @theme-main-color2;
    &:hover {
        background-color: @theme-main-color2;
        color: white;
    }
}

.apptable {
    tr:hover {
        background-color: rgb(241, 241, 241);
    }
}
</style>
