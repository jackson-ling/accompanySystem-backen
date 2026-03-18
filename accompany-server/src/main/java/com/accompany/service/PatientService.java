package com.accompany.service;

import com.accompany.dto.AddPatientDto;
import com.accompany.dto.UpdatePatientDto;
import com.accompany.vo.PatientVo;

import java.util.List;

public interface PatientService {

    /**
     * 获取当前用户的就诊人列表
     * @return 就诊人列表
     */
    List<PatientVo> getPatientList();

    /**
     * 添加就诊人
     * @param addPatientDto 就诊人信息
     * @return 添加后的就诊人信息
     */
    PatientVo addPatient(AddPatientDto addPatientDto);

    /**
     * 设置默认就诊人
     * @param patientId 就诊人ID
     */
    void setDefaultPatient(Long patientId);

    /**
     * 更新就诊人信息
     * @param patientId 就诊人ID
     * @param updatePatientDto 更新的就诊人信息
     * @return 更新后的就诊人信息
     */
    PatientVo updatePatient(Long patientId, UpdatePatientDto updatePatientDto);

    /**
     * 删除就诊人
     * @param patientId 就诊人ID
     */
    void deletePatient(Long patientId);
}