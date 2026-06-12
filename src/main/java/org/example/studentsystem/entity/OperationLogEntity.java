package org.example.studentsystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLogEntity {
    private Long id;

    private String operation;

    private String requestUrl;

    private String httpMethod;

    private String classMethod;

    private String ip;

    private String token;

    private String userId;

    private String userName;

    private String params;

    private String result;

    private Long costTime;

    private LocalDateTime createTime;
}
