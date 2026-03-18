package com.accompany.mapper;

import com.accompany.entity.Hospital;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 医院 Mapper
 */
@Mapper
public interface HospitalMapper {

    /**
     * 查询所有医院列表
     */
    List<Hospital> findAll();

    /**
     * 根据关键词搜索医院
     */
    List<Hospital> searchByKeyword(@Param("keyword") String keyword);
}