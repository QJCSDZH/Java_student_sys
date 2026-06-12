package org.example.studentsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.studentsystem.entity.OperationLogEntity;

@Mapper
public interface OperationLogMapper {
    void saveInfo(OperationLogEntity log);
}
