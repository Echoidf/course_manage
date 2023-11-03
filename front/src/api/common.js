import request from './request'

//登录
export function reLogin(params) {
    return request.post(
        '/auth/login',
         params, 
        {"Content-Type": "multipart/form-data"});
}

//获取用户信息
export function reUserInfo() {
    return request.get('/user/info');
}

//退出登录
export function reLogout() {
    return request.get('/logout');
}

//获取学生列表
export function getStudentList() {
    return request.get('/student/list');
}

//新增学生
export function addStu(params) {
    return request.post('/student/add', params);
}

//获取学生选课信息
export function reGetStuClsDetail(id) {
    return request.get(`/courseSelection/list/${id}`);
}

//删除学生
export function reDeleteStudent(id) {
    return request.post(`/student/delete/${id}`);
}

export function reUpdateStudent(data) {
    return request.post(`/student/update`,data)
}

//获取教师列表
export function getTeacherList() {
    return request.get('/teacher/list');
}

//删除教师
export function reDeleteTeacher(id) {
    return request.post(`/teacher/delete/${id}`);
}

//新增教师
export function reAddTeacher(params) {
    return request.post('/teacher/add', params);
}

//获取教师授课列表
export function reGetTeacherCourses(id) {
    return request.get(`/course/teacher/${id}`);
}

export function reGetMyCourses(uId) {
    return request.get(`/teacher/course/${uId}`)
}

// 获取教师学生的课程信息
export function reGetTeacherStudents(uId, cname) {
    return request.get(`/teacher/students?uId=${uId}&cname=${cname}`);
}


//获取课程信息列表
export function reGetCourseList() {
    return request.get(`/course/list`);
}

//新增课程
export function reAddCourse(params) {
    return request.post('/course/add', params);
}

//更新课程
export function reUpdateCourse(data){
    return request.post(`/course/update`, data)
}

//删除课程
export function delCourse(id){
    return request.post(`/course/delete/${id}`)
}

//更新学生成绩
export function reUpdateStuScore(sno, data){
    return request.post(`/courseSelection/update/${sno}`, data)
}

//获取成绩分布
// example:
// {
//     "excellent": 0,
//     "good": 0,
//     "medium": 0,
//     "pass": 1,
//     "fail": 0
// }
export function getCourseScore(cId) {
    return request.get(`/course/score/${cId}`)
}
