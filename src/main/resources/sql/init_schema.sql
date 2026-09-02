-- 本地空库初始化：student_system
CREATE DATABASE IF NOT EXISTS student_system
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE student_system;

CREATE TABLE IF NOT EXISTS teacher (
    id     INT          NOT NULL PRIMARY KEY COMMENT '教师ID（业务写入，非自增）',
    name   VARCHAR(50)  DEFAULT NULL COMMENT '姓名',
    age    INT          DEFAULT NULL COMMENT '年龄',
    gender VARCHAR(10)  DEFAULT NULL COMMENT '性别'
) COMMENT '教师表';

CREATE TABLE IF NOT EXISTS student (
    id         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '学生ID',
    name       VARCHAR(50)  DEFAULT NULL COMMENT '姓名',
    age        INT          DEFAULT NULL COMMENT '年龄',
    gender     VARCHAR(10)  DEFAULT NULL COMMENT '性别',
    chinese    INT          DEFAULT NULL COMMENT '语文',
    math       INT          DEFAULT NULL COMMENT '数学',
    english    INT          DEFAULT NULL COMMENT '英语',
    teacher_id INT          DEFAULT NULL COMMENT '所属教师ID',
    INDEX idx_teacher_id (teacher_id),
    INDEX idx_name (name)
) COMMENT '学生表';

DROP TABLE IF EXISTS operation_log;

CREATE TABLE operation_log (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    trace_id        VARCHAR(32)   COMMENT '链路ID，关联文件日志',
    module          VARCHAR(50)   NOT NULL COMMENT '模块：teacher/student',
    operation       VARCHAR(50)   NOT NULL COMMENT '操作：INSERT/UPDATE/DELETE',
    description     VARCHAR(200)  COMMENT '操作描述',
    method          VARCHAR(200)  COMMENT '方法签名',
    request_url     VARCHAR(500)  COMMENT '请求URL',
    http_method     VARCHAR(10)   COMMENT 'HTTP方法',
    request_params  TEXT          COMMENT '请求参数 JSON',
    response_body   TEXT          COMMENT '响应结果 JSON',
    response_code   INT           COMMENT '响应码',
    operator_name   VARCHAR(50)   DEFAULT 'anonymous' COMMENT '操作人',
    client_ip       VARCHAR(50)   COMMENT '客户端IP',
    cost_ms         INT           COMMENT '耗时(ms)',
    status          TINYINT       NOT NULL COMMENT '1成功 0失败',
    error_msg       VARCHAR(500)  COMMENT '失败原因',
    created_at      DATETIME      NOT NULL COMMENT '操作时间',
    INDEX idx_module_operation (module, operation),
    INDEX idx_created_at (created_at),
    INDEX idx_trace_id (trace_id)
) COMMENT '关键操作审计日志';
