package com.accompany.controller;

import com.accompany.base.Result;
import com.accompany.service.DictService;
import com.accompany.vo.DepartmentVo;
import com.accompany.vo.HospitalVo;
import com.accompany.vo.ServiceTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典模块控制器
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 获取服务类型
     */
    @GetMapping("/service-types")
    public Result<List<ServiceTypeVo>> getServiceTypes() {
        List<ServiceTypeVo> serviceTypes = dictService.getServiceTypes();
        return Result.success(serviceTypes);
    }

    /**
     * 获取医院列表
     */
    @GetMapping("/hospitals")
    public Result<List<HospitalVo>> getHospitalList(@RequestParam(required = false) String keyword) {
        List<HospitalVo> hospitals = dictService.getHospitalList(keyword);
        return Result.success(hospitals);
    }

    /**
     * 获取科室列表
     */
    @GetMapping("/departments")
    public Result<List<DepartmentVo>> getDepartmentList(@RequestParam(required = false) Long hospitalId) {
        List<DepartmentVo> departments = dictService.getDepartmentList(hospitalId);
        return Result.success(departments);
    }
}