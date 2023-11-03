<template>
    <div class="appmain">
        <div class="appcart">
            <p class="title">学生信息</p>

            <button class="reserve-bt" @click="addStudentShow = true">注册学生 +</button>
            <table class="apptable revarse-table">
                <tr>
                    <th>学号</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>年龄</th>
                    <th>班级</th>
                    <th>学院</th>
                    <th>操作</th>
                </tr>
                <tr v-show="stuList.list.length === 0">
                    <td class="nodata" colspan="5"> No Data </td>
                </tr>
                <tr v-for="item in stuList.list" :key="item.id">
                    <td>{{ item.sno }}</td>
                    <td>{{ item.username }}</td>
                    <td>{{ item.sex ? "男" : "女" }}</td>
                    <td>{{ item.age }}</td>
                    <td>{{ item.cls }}</td>
                    <td>{{ item.college }}</td>

                    <td>
                        <i class="detail-bt" @click="getDetail(item.id)">选课信息</i>
                        <i class="update-bt" @click="showUpdate(item)">修改</i>
                        <i class="remove-bt" @click="showRemove(item.id)">删除</i>
                    </td>
                </tr>
            </table>

            <!-- 新增学生 -->
            <div class="appmask" v-show="addStudentShow">
                <div class="mesbox add-reserve">
                    <p class="title">注册学生</p>
                    <i class="close-bt" @click="addStudentShow = false">X</i>

                    <div class="addstu-div">
                        <div>
                            <InputBlock atr="姓名" v-model="addStuParams.username" />
                            <InputBlock atr="年龄" v-model="addStuParams.age" />
                        </div>

                        <div>
                            <InputBlock atr="学号" v-model="addStuParams.sno" />
                            <InputBlock atr="班级" v-model="addStuParams.cls" />
                        </div>

                        <div>
                            <InputBlock atr="学院" v-model="addStuParams.college" />
                            <InputBlock atr="密码" v-model="addStuParams.password" />
                        </div>
                        <SelectBlock atr="性别" describe="请选择性别" v-model="addStuParams.sex">
                            <li svalue="0">男性</li>
                            <li svalue="1">女性</li>
                        </SelectBlock>
                    </div>

                    <i class="comfirm-bt" @click="addStudent">确认</i>
                    <i class="cancel-bt" @click="addStudentShow = false">取消</i>
                </div>
            </div>

            <el-dialog v-model="updateStudentShow" width="400px">
                <el-form>
                    <el-form-item label="姓名：">
                        <el-input v-model="updateStuParams.username" label-width="60px"></el-input>
                    </el-form-item>
                    
                    <el-form-item label="学号：">
                        <el-input v-model="updateStuParams.sno" label-width="60px"></el-input>
                    </el-form-item>
                    <el-form-item label="班级：">
                        <el-input v-model="updateStuParams.cls" label-width="60px"></el-input>
                    </el-form-item>
                    <el-form-item label="学院：">
                        <el-input v-model="updateStuParams.college" label-width="60px"></el-input>
                    </el-form-item>
                </el-form>

                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="updateStudentShow = false">取消</el-button>
                        <el-button type="primary" @click="updateStudent">
                            确定
                        </el-button>
                    </span>
                </template>
            </el-dialog>

            <!-- 删除确认 -->
            <div class="appmask" v-show="removeStudentShow">
                <!-- 因为v-show的关系?，此元素就算放v-for="item in stuList.list"的元素中，也取不到正确的item值，所以放在这里 -->
                <div class="mesbox">
                    <p class="title">确认删除</p>
                    <i class="comfirm-bt" @click="removeStu()">确认</i>
                    <i class="cancel-bt" @click="removeStudentShow = false">取消</i>
                </div>
            </div>

            <!-- 选课详情 -->
            <div class="appmask" v-if="detailShow">
                <div class="mesbox rese-detail">
                    <p class="title">选课详情</p>
                    <i class="close-bt" @click="detailShow = false">X</i>

                    <table class="apptable detailTable">
                        <tr>
                            <th>课程号</th>
                            <th>课程名称</th>
                            <th>开课学院</th>
                            <th>年级</th>
                            <th>教师编号</th>
                            <th>签到得分</th>
                            <th>作业得分</th>
                            <th>实验得分</th>
                            <th>考试得分</th>
                            <th>平时成绩</th>
                            <th>总成绩</th>
                        </tr>
                        <tr v-show="stuClsDetail.length === 0">
                            <td class="nodata" colspan="10"> No Data </td>
                        </tr>
                        <tr v-for="(item, k) in stuClsDetail" :key="k">
                            <td>{{ item.cno }}</td>
                            <td>{{ item.name }}</td>
                            <td>{{ item.college }}</td>
                            <td>{{ item.grade }}</td>
                            <td>{{ item.tno }}</td>
                            <td>{{ item.signScore }}</td>
                            <td>{{ item.workScore }}</td>
                            <td>{{ item.expScore }}</td>
                            <td>{{ item.examGrade }}</td>
                            <td>{{ item.usualGrade }}</td>
                            <td>{{ item.totalGrade }}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { onBeforeMount, reactive, ref } from "vue";
import { getStudentList, reGetStuClsDetail, reDeleteStudent, addStu , reUpdateStudent} from "@/api/common.js";
import SelectBlock from "@/components/SelectBlock.vue";
import InputBlock from "@/components/InputBlock.vue";
import Bus from "@/utils/bus";

const addStudentShow = ref(false);
const updateStudentShow = ref(false);
const removeStudentShow = ref(false);
const detailShow = ref(false);
const stuClsDetail = ref([]);
let deleteId = null;
const stuList = reactive({
    list: [],
});
const addStuParams = reactive({
    sex: "男性",
    age: 23,
    password: "",
    sno: "",
    cls: "",
    college: "",
    username: "",
});

const updateStuParams = ref({})

//获取学生列表
async function getdata() {
    const result = await getStudentList();
    if (result.status && result.status === 200) {
        stuList.list = result.data.list;
    } else console.log("err", result);
}

async function addStudent() {
    const params = { ...addStuParams };
    if (params.sex === "男性") {
        params.sex = 0
    } else {
        params.sex = 1
    }
    const result = await addStu(params);
    if (result.status && result.status === 200) {
        addStudentShow.value = false;
        getdata();
        Bus.$emit("popMes", { type: "success", text: "添加成功" });
    } else {
        console.log("err", result)
    }
}

//打开确认删除的窗口
function showRemove(id) {
    removeStudentShow.value = true;
    deleteId = id;
}

//删除元素
async function removeStu() {
    if (!deleteId) return console.log("删除元素id错误");
    const result = await reDeleteStudent(deleteId);
    if (result.status && result.status === 200) {
        getdata();
        Bus.$emit("popMes", { type: "success", text: "删除成功" });
    } else console.log("删除失败", result);
    removeStudentShow.value = false;
}

//获取详情
async function getDetail(id) {
    stuClsDetail.value = [];
    detailShow.value = true;
    const result = await reGetStuClsDetail(id);
    if (result.status && result.status === 200) {
        stuClsDetail.value = result.data;
    } else console.log("获取详情失败", result);
}
function showUpdate(row) {
    updateStuParams.value = { ...row };
    updateStudentShow.value = true;
}

async function updateStudent() {
    console.log(updateStuParams.value);

    const res = await reUpdateStudent(updateStuParams.value)
    if(res && res.status === 200) {
        Bus.$emit("popMes", { type: "success", text: "更新成功" });
        updateStudentShow.value = false;
        getdata();
    }
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

.addstu-div {
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
