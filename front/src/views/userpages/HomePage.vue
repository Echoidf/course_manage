<template>
    <div class="appmain">
        <div class="appcart welcome-wrap">
            <img src="@/assets/imgs/head_portrait.jpg" class="user-head-icon" />
            <span class="text-wrap">
                <p class="welcome">{{ welcomeMessage[0] }}&nbsp;&nbsp;{{ userStore.userName }}，{{ welcomeMessage[1] }}</p>
                <p class="weather">今日晴 🌤，温度20°C-28°C，温度舒适</p>
            </span>
        </div>

    

        <!-- 相关链接 -->
        <div class="appcart related-link">
            <h2 class="block-header">
                <i class="iconfont icon-icon-taikong8 icon" style="color: gold"></i>
                <span>相关链接</span>
            </h2>
            <div class="shortcut-wrap">
                <span class="shortcut">
                    <div class="icon-wrap s1"><i class="iconfont icon-weiguanwang"></i></div>
                    <p>学校主页</p>
                </span>
                <span class="shortcut">
                    <div class="icon-wrap s2"><i class="iconfont icon-14"></i></div>
                    <p>校友论坛</p>
                </span>
                <span class="shortcut">
                    <div class="icon-wrap s3"><i class="iconfont icon-nav_tushuguan"></i></div>
                    <p>图书馆</p>
                </span>
                <span class="shortcut">
                    <div class="icon-wrap s4"><i class="iconfont icon-duomeiti-"></i></div>
                    <p>媒体中心</p>
                </span>
                <span class="shortcut">
                    <div class="icon-wrap s5"><i class="iconfont icon-wangluoxitong"></i></div>
                    <p>校园网络</p>
                </span>
                <span class="shortcut">
                    <div class="icon-wrap s6"><i class="iconfont icon-yewushouce"></i></div>
                    <p>学生手册</p>
                </span>
                <span class="shortcut">
                    <div class="icon-wrap s7"><i class="iconfont icon-keyanfangxiang"></i></div>
                    <p>科研生活</p>
                </span>
            </div>
        </div>
    </div>
</template>

<script setup>
import { computed } from "vue";
import { useUserStore } from "@/store";

const userStore = useUserStore();
const date = new Date();


const welcomeMessage = computed(() => {
    let hour = date.getHours();
    if (hour >= 0 && hour < 6) return ["🌙凌晨了", "或许月亮已经高挂~⭐"];
    if (hour >= 6 && hour < 12) return ["☀早安", "开始新的一天吧！"];
    if (hour >= 12 && hour < 14) return ["☀中午啦", "别忘记休息哦。"];
    if (hour >= 14 && hour < 19) return ["☀下午好呀", "继续努力吧！"];
    if (hour >= 19 && hour <= 23) return ["🌙晚上好", "累了的话喝杯茶提提神吧。"];
    return [];
});


</script>

<style lang="less" scoped>
.welcome-wrap {
    position: relative;
    overflow: hidden;

    .user-head-icon {
        display: inline-block;
        height: 120px;
        border-radius: 10%;
        margin-right: 24px;
    }

    .text-wrap {
        vertical-align: top;
        display: inline-block;
        width: 85%;

        .welcome {
            font-size: calc(@baseSize * 1.5);
            font-weight: bolder;
            color: rgb(100, 100, 100);
        }
        .weather {
            font-size: calc(@baseSize * 1);
            color: rgb(138, 138, 138);
            margin: 12px 0px;
        }
        .introduce {
            white-space: wrap;
            font-size: @baseSize;
            color: rgb(121, 121, 121);
        }
    }

}

.app-blockdiv.mid {
    width: 50%;
}

.icon {
    display: inline-block;
    height: 20px;
    line-height: 20px;
    font-size: 20px;
    padding-right: 4px;
    color: rgb(49, 49, 49);
}

.info-area {
    width: 40%;
    flex: 1 0 auto;

    .inform-wrap {
        width: 100%;

        .inform {
            font-size: @baseSize;
            color: rgb(90, 90, 90);
            position: relative;
            margin: 12px 0px;

            a {
                display: inline-block;
                text-decoration: none;
                vertical-align: bottom;
                cursor: pointer;
                width: 65%;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                &:hover {
                    color: @theme-main-color1;
                }
            }
            .type {
                color: rgb(128, 128, 128);
                margin-right: 4px;
            }
            .date {
                position: absolute;
                font-size: 12px;
                right: 2%;
                bottom: 0%;
                color: rgb(156, 156, 156);
            }
        }
    }
}
.block-header {
    padding-bottom: 12px;
    margin-bottom: 12px;
    border-bottom: 1px solid rgb(204, 204, 204);
    font-size: calc(@baseSize * 1.2);
    font-weight: bolder;
    color: rgb(100, 100, 100);
    position: relative;

    span.more {
        position: absolute;
        right: 2%;
        bottom: 12px;
        font-size: calc(@baseSize * 0.9);
        font-weight: normal;
        cursor: pointer;
        color: rgb(156, 156, 156);
        &:hover {
            color: @theme-main-color2;
        }
    }
}

.todo-list-wrap {
    .apptable.todo-list-list {
        outline: 0;

        th,
        td {
            outline: 0;
            border-bottom: 1px solid rgb(226, 226, 226);
        }
        tr:hover {
            background-color: rgb(241, 241, 241);
        }
    }
}

.room-reserve-wrap {
    width: 100%;

    .reserve {
        outline: 0;

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

        th,
        td {
            outline: 0;
            border-bottom: 1px solid rgb(226, 226, 226);
        }
        tr:hover {
            background-color: rgb(241, 241, 241);
        }
    }
}

.related-link{
    //width:100%;

    .shortcut-wrap {
        display: flex;
        justify-content: space-between;
        flex-wrap: wrap;

        .shortcut {
            display: inline-block;
            width: 80px;
            height: 80px;
            padding: 20px;

            .s1 {
                background-color: rgb(233, 188, 128);
            }
            .s2 {
                background-color: rgb(175, 212, 115);
            }
            .s3 {
                background-color: rgb(115, 206, 150);
            }
            .s4 {
                background-color: rgb(113, 206, 190);
            }
            .s5 {
                background-color: rgb(122, 173, 221);
            }
            .s6 {
                background-color: rgb(138, 112, 207);
            }
            .s7 {
                background-color: rgb(211, 119, 173);
            }

            .icon-wrap {
                height: 60px;
                width: 60px;
                line-height: 60px;
                margin: auto;
                text-align: center;
                border-radius: 10px;
                cursor: pointer;
                color: white;

                i {
                    font-size: 22px;
                }
            }

            p {
                font-size: @baseSize;
                text-align: center;
                margin-top: 12px;
            }
        }
    }
}


</style>
