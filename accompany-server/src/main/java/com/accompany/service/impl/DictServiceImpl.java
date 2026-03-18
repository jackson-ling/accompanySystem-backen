package com.accompany.service.impl;

import com.accompany.entity.Department;
import com.accompany.entity.Hospital;
import com.accompany.mapper.DepartmentMapper;
import com.accompany.mapper.HospitalMapper;
import com.accompany.service.DictService;
import com.accompany.vo.DepartmentVo;
import com.accompany.vo.HospitalVo;
import com.accompany.vo.ServiceTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典服务实现类
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private HospitalMapper hospitalMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<ServiceTypeVo> getServiceTypes() {
        // 服务类型是固定的，直接返回硬编码数据
        List<ServiceTypeVo> serviceTypes = new ArrayList<>();
        ServiceTypeVo type1 = new ServiceTypeVo();
        type1.setId(1L);
        type1.setName("陪诊服务");
        type1.setValue("companion");
        serviceTypes.add(type1);

        ServiceTypeVo type2 = new ServiceTypeVo();
        type2.setId(2L);
        type2.setName("代理服务");
        type2.setValue("agency");
        serviceTypes.add(type2);

        return serviceTypes;
    }

    @Override
    public List<HospitalVo> getHospitalList(String keyword) {
        List<Hospital> hospitals;
        if (StringUtils.hasText(keyword)) {
            hospitals = hospitalMapper.searchByKeyword(keyword);
        } else {
            hospitals = hospitalMapper.findAll();
        }

        return hospitals.stream().map(hospital -> {
            HospitalVo vo = new HospitalVo();
            vo.setId(hospital.getId());
            vo.setName(hospital.getName());
            vo.setAddress(hospital.getAddress());
            vo.setLevel(hospital.getLevel());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<DepartmentVo> getDepartmentList(Long hospitalId) {
        List<Department> departments = departmentMapper.findByHospitalId(hospitalId);

        return departments.stream().map(department -> {
            DepartmentVo vo = new DepartmentVo();
            vo.setId(department.getId());
            vo.setName(department.getName());
            vo.setHospitalId(department.getHospitalId());
            return vo;
        }).collect(Collectors.toList());
    }
}