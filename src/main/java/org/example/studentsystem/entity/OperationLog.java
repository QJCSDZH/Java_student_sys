package org.example.studentsystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLog {
    private Long id;
    private String traceId;
    private String module;
    private String operation;
    private String description;
    private String method;
    private String requestUrl;
    private String httpMethod;
    private String requestParams;
    private String responseBody;
    private Integer responseCode;
    private String operatorName;
    private String clientIp;
    private Integer costMs;
    private Integer status;
    private String errorMsg;
    private LocalDateTime createdAt;
}
