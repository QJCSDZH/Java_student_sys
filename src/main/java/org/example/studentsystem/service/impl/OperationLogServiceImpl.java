package org.example.studentsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.studentsystem.entity.OperationLog;
import org.example.studentsystem.mapper.OperationLogMapper;
import org.example.studentsystem.service.OperationLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationLogServiceImpl implements OperationLogService {

    private final OperationLogMapper operationLogMapper;

    @Async
    @Override
    public void saveAsync(OperationLog operationLog) {
        try {
            operationLogMapper.insert(operationLog);
            log.info("审计日志已写入数据库, module={}, operation={}, status={}",
                    operationLog.getModule(), operationLog.getOperation(), operationLog.getStatus());
        } catch (Exception e) {
            log.error("审计日志写入失败, module={}, operation={}",
                    operationLog.getModule(), operationLog.getOperation(), e);
        }
    }

    @Override
    public PageInfo<OperationLog> list(String module, String operation, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        try {
            List<OperationLog> logs = operationLogMapper.list(module, operation);
            return new PageInfo<>(logs);
        } finally {
            PageHelper.clearPage();
        }
    }
}
