package org.example.studentsystem.service.impl;

import lombok.AllArgsConstructor;
import org.example.studentsystem.entity.OperationLogEntity;
import org.example.studentsystem.mapper.OperationLogMapper;
import org.example.studentsystem.service.OperationLogService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OperationLogServiceImpl implements OperationLogService {

    private final OperationLogMapper mapper;

    @Override
    public void save(OperationLogEntity log) {

        mapper.saveInfo(log);

    }
}
