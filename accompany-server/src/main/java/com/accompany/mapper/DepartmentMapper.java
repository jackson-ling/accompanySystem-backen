package com.accompany.mapper;

import com.accompany.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科室 Mapper
 */
@Mapper
public interface DepartmentMapper {

    /**
     * 根据医院ID查询科室列表
     */
    List<Department> findByHospitalId(@Param("hospitalId") Long hospitalId);
}