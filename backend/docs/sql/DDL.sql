-- 使用数据库
USE
    `course_manage`;


-- 创建用户表
DROP TABLE If EXISTS `user`;
CREATE TABLE `user`
(
    `id`       int(11)     NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `password` varchar(50) NOT NULL,
    `role`     varchar(20) default NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 创建学生信息表
CREATE TABLE `student`
(
    `id`      int(11)     NOT NULL AUTO_INCREMENT,
    `sno`     varchar(20) NOT NULL, -- 学号
    `class`   varchar(20) NOT NULL, -- 班级
    `college` varchar(50) NOT NULL, -- 学院
    `user_id` int(11)     NOT NULL, -- 外键,关联用户表
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 创建教师信息表
CREATE TABLE `teacher`
(
    `id`      int(11)     NOT NULL AUTO_INCREMENT,
    `tno`     varchar(20) NOT NULL, -- 工号
    `college` varchar(50) NOT NULL, -- 学院
    `user_id` int(11)     NOT NULL, -- 外键,关联用户表
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 创建课程表
CREATE TABLE `course`
(
    `id`         int(11)     NOT NULL AUTO_INCREMENT,
    `cno`        varchar(20) NOT NULL, -- 课号
    `name`       varchar(50) NOT NULL, -- 课程名称
    `college`    varchar(50) NOT NULL, -- 开课学院
    `grade`      varchar(10) NOT NULL, -- 开放年级
    `teacher_id` int(11)     NOT NULL, -- 外键,关联教师表
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 创建学生选课表
DROP TABLE IF EXISTS `course_selection`;
CREATE TABLE `course_selection`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `student_id`  int(11) NOT NULL,           -- 外键,关联学生表
    `course_id`   int(11) NOT NULL,           -- 外键,关联课程表
    `sign_score`  decimal(4, 1) DEFAULT NULL, -- 签到成绩
    `work_score`  decimal(4, 1) DEFAULT NULL, -- 作业成绩
    `exp_score`   decimal(4, 1) DEFAULT NULL, -- 实验成绩
    `exam_grade`  decimal(4, 1) DEFAULT NULL, -- 考试成绩
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 添加管理员用户
INSERT INTO `user` (`id`, `username`, `password`, `role`)
VALUES (1, 'admin', 'admin123', 'admin');

-- 添加学生用户
INSERT INTO `user` (`id`, `username`, `password`, `role`)
VALUES (2, 'stu1', '123456', 'student'),
       (3, 'stu2', '123456', 'student');

-- 添加学生信息
INSERT INTO `student` (`id`, `sno`, cls, `college`, `user_id`)
VALUES (1, '1001', '1班', '计算机学院', 2),
       (2, '1002', '1班', '计算机学院', 3);

-- 添加教师用户
INSERT INTO `user` (`id`, `username`, `password`, `role`)
VALUES (4, 'teacher1', '123456', 'teacher');

-- 添加教师信息
INSERT INTO `teacher` (`id`, `tno`, `college`, `user_id`)
VALUES (1, 'T001', '计算机学院', 4);

-- 添加课程信息
INSERT INTO `course` (`id`, `cno`, `name`, `college`, `grade`, `teacher_id`)
VALUES (1, 'C001', '数据库管理', '计算机学院', '大三', 1);

-- 学生选课信息
INSERT INTO `course_selection` (`id`, `student_id`, `course_id`, `usual_grade`, `exam_grade`)
VALUES (1, 1, 1, 80, 85);