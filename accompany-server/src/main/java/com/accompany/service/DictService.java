package com.accompany.service;

import com.accompany.vo.DepartmentVo;
import com.accompany.vo.HospitalVo;
import com.accompany.vo.ServiceTypeVo;

import java.util.List;

/**
 * 字典服务接口
 */
public interface DictService {

    /**
     * 获取服务类型列表
     * @return 服务类型列表
     */
    List<ServiceTypeVo> getServiceTypes();

    /**
     * 获取医院列表
     * @param keyword 搜索关键词（可选）
     * @return 医院列表
     */
    List<HospitalVo> getHospitalList(String keyword);

    /**
     * 获取科室列表
     * @param hospitalId 医院ID（可选）
     * @return 科室列表
     */
    List<DepartmentVo> getDepartmentList(Long hospitalId);
}