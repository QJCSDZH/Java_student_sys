package org.example.studentsystem.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.example.studentsystem.common.PHResult;
import org.example.studentsystem.entity.OperationLog;
import org.example.studentsystem.service.OperationLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/operation-log/")
public class OperationLogController {

    private final OperationLogService operationLogService;

    // http://127.0.0.1:8081/operation-log/list?pageNum=1&pageSize=10
    // http://127.0.0.1:8081/operation-log/list?module=teacher&operation=INSERT
    @GetMapping("list")
    public PHResult<PageInfo<OperationLog>> list(
            @RequestParam(required = false) String module,
            @RequestParam(required = false) String operation,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        return PHResult.success(operationLogService.list(module, operation, pageNum, pageSize));
    }
}
