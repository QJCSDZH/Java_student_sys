package org.example.studentsystem.service;

import com.github.pagehelper.PageInfo;
import org.example.studentsystem.entity.OperationLog;

public interface OperationLogService {

    void saveAsync(OperationLog log);

    PageInfo<OperationLog> list(String module, String operation, int pageNum, int pageSize);
}
