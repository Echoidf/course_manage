import { createRouter, createWebHashHistory } from "vue-router";
import Layout from "@/views/layout/Layout.vue";

/*
路由说明：
    新增的路由可挂载到路由'/'的子路由下，也可直接新增路由项。
    带有子路由的路由项默认在侧边栏菜单呈现嵌套结构，支持多重路由嵌套。
    name：应该与路由对应的组件名称一致，否则KeepAlive无法正常工作
    meta{
        title:侧边栏菜单名
        icon：显示在侧边栏的图标（本项目使用阿里图标库的css图标）
        limit：权限控制，列表项为能访问到当前路由的角色，与当前角色权限不符的路由不会显示在侧边栏菜单
        fixCache：是否固定缓存该路由对应的组件，选择此项的路由同时会固定在tagViews不可删除
        cache：是否缓存该路由对应的组件，可在tagViews中清除
        isLink：表明该路由对应外链
        hideInMenu:决定当前路由是否在侧边栏菜单隐藏
    }
*/

const routes = [
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/login/Login.vue"),
    meta: { hideInMenu: true },
  },
  {
    path: "/",
    component: Layout,
    redirect: "/homepage",
    meta: { hideInMenu: true },
    children: [
      {
        path: "homepage",
        name: "HomePage",
        component: () => import("@/views/userpages/HomePage.vue"),
        meta: {
          title: "首页",
          icon: "icon-shouye",
          limit: ["admin", "teacher", "student"],
          fixCache: true,
        },
      },
      {
        path: "userinfo",
        name: "UserInfo",
        component: () => import("@/views/StudentInfo.vue"),
        meta: { title: "学生信息", icon: "icon-xuesheng", limit: ["admin"] },
      },
      {
        path: "teacherinfo",
        name: "TeacherInfo",
        component: () => import("@/views/TeacherInfo.vue"),
        meta: { title: "教师信息", icon: "icon-jiaoshi", limit: ["admin"] },
      },
      {
        path: "course",
        name: "CourseInfo",
        component: () => import("@/views/CourseInfo.vue"),
        meta: {
          title: "课程管理",
          icon: "icon-jiaoshi",
          limit: ["admin", "teacher"],
        },
      },
      
      {
        path: "score",
        name: "ScoreManage",
        component: () => import("@/views/userpages/teacher/ScoreManage.vue"),
        meta: {
          title: "成绩录入",
          icon: "icon-jiaoshi",
          limit: ["teacher"],
        },
      },
      {
        path: "detail",
        name: "CourseDetail",
        component: () => import("@/views/CourseDetail.vue"),
        meta: {
          title: "",
          icon: "",
          limit: ["admin", "teacher"],
        },
      },
      {
        path: "course-select",
        name: "course-select",
        redirect: "/course-select/selected-course",
        meta: {
          title: "课程选择",
          icon: "icon-kechengguanli",
          limit: ["student"],
        },
        children: [
          {
            path: "selected-course",
            name: "SelectedCourse",
            component: () =>
              import("@/views/userpages/student/SelectedCourse.vue"),
            meta: { title: "已选课程", icon: "", limit: ["student"] },
          },
          {
            path: "select-course",
            name: "SelectCourse",
            component: () =>
              import("@/views/userpages/student/SelectCourse.vue"),
            meta: { title: "选择课程", icon: "", limit: ["student"] },
          },
        ],
      },
    ],
  },
  {
    path: "/404",
    component: () => import("@/views/error/404.vue"),
    meta: { hideInMenu: true },
  },
  {
    path: "/401",
    component: () => import("@/views/error/401.vue"),
    meta: { hideInMenu: true },
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: "/404",
    meta: { hideInMenu: true },
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
