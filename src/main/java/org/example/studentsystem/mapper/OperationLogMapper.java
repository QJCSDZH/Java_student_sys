package org.example.studentsystem.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.studentsystem.entity.OperationLog;

import java.util.List;

@Mapper
public interface OperationLogMapper {

    @Insert("""
            INSERT INTO operation_log (
                trace_id, module, operation, description, method,
                request_url, http_method, request_params, response_body, response_code,
                operator_name, client_ip, cost_ms, status, error_msg, created_at
            ) VALUES (
                #{traceId}, #{module}, #{operation}, #{description}, #{method},
                #{requestUrl}, #{httpMethod}, #{requestParams}, #{responseBody}, #{responseCode},
                #{operatorName}, #{clientIp}, #{costMs}, #{status}, #{errorMsg}, #{createdAt}
            )
            """)
    int insert(OperationLog log);

    @Select("""
            <script>
            SELECT id, trace_id AS traceId, module, operation, description, method,
                   request_url AS requestUrl, http_method AS httpMethod,
                   request_params AS requestParams, response_body AS responseBody,
                   response_code AS responseCode, operator_name AS operatorName,
                   client_ip AS clientIp, cost_ms AS costMs, status, error_msg AS errorMsg,
                   created_at AS createdAt
            FROM operation_log
            <where>
                <if test="module != null and module != ''">
                    AND module = #{module}
                </if>
                <if test="operation != null and operation != ''">
                    AND operation = #{operation}
                </if>
            </where>
            ORDER BY created_at DESC
            </script>
            """)
    List<OperationLog> list(@Param("module") String module, @Param("operation") String operation);
}
