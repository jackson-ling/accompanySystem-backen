package com.accompany.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.accompany.base.BasicEnum;
import com.accompany.dto.AddPatientDto;
import com.accompany.dto.UpdatePatientDto;
import com.accompany.entity.Patient;
import com.accompany.exception.BaseException;
import com.accompany.mapper.PatientMapper;
import com.accompany.service.PatientService;
import com.accompany.utill.UserThreadLocal;
import com.accompany.vo.PatientVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public List<PatientVo> getPatientList() {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询就诊人列表
        List<Patient> patients = patientMapper.selectByUserId(userId);

        // 转换为PatientVo列表
        List<PatientVo> patientVos = BeanUtil.copyToList(patients, PatientVo.class);
        return patientVos;
    }

    @Override
    @Transactional
    public PatientVo addPatient(AddPatientDto addPatientDto) {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 验证必填字段
        if (ObjectUtil.isEmpty(addPatientDto.getName())) {
            throw new BaseException(BasicEnum.PATIENT_NAME_NOT_EMPTY);
        }

        // 如果设置为默认就诊人，先将该用户的所有就诊人设置为非默认
        if (Integer.valueOf(1).equals(addPatientDto.getIsDefault())) {
            patientMapper.updateAllToNonDefault(userId);
        }

        // 创建就诊人实体
        Patient patient = BeanUtil.toBean(addPatientDto, Patient.class);
        patient.setUserId(userId);

        // 设置默认值
        if (ObjectUtil.isEmpty(patient.getIsDefault())) {
            patient.setIsDefault(0);
        }

        // 插入数据库
        patientMapper.insert(patient);

        // 转换为PatientVo返回
        PatientVo patientVo = BeanUtil.toBean(patient, PatientVo.class);
        return patientVo;
    }

    @Override
    @Transactional
    public void setDefaultPatient(Long patientId) {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询就诊人信息
        Patient patient = patientMapper.selectById(patientId);
        if (ObjectUtils.isEmpty(patient)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 验证就诊人是否属于当前用户
        if (!patient.getUserId().equals(userId)) {
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }

        // 将该用户的所有就诊人设置为非默认
        patientMapper.updateAllToNonDefault(userId);

        // 将指定就诊人设置为默认
        patientMapper.updateToDefault(patientId);
    }

    @Override
    @Transactional
    public PatientVo updatePatient(Long patientId, UpdatePatientDto updatePatientDto) {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询就诊人信息
        Patient patient = patientMapper.selectById(patientId);
        if (ObjectUtils.isEmpty(patient)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 验证就诊人是否属于当前用户
        if (!patient.getUserId().equals(userId)) {
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }

        // 检查是否至少有一个字段被更新
        boolean hasUpdate = ObjectUtil.isNotEmpty(updatePatientDto.getName())
                || ObjectUtil.isNotEmpty(updatePatientDto.getPhone())
                || ObjectUtil.isNotEmpty(updatePatientDto.getIdCard())
                || ObjectUtil.isNotEmpty(updatePatientDto.getGender())
                || ObjectUtil.isNotEmpty(updatePatientDto.getAge())
                || ObjectUtil.isNotEmpty(updatePatientDto.getAddress())
                || ObjectUtil.isNotEmpty(updatePatientDto.getRelationship())
                || ObjectUtil.isNotEmpty(updatePatientDto.getMedicalCardNo());

        if (!hasUpdate) {
            throw new BaseException(BasicEnum.UPDATE_AT_LEAST_ONE_FIELD);
        }

        // 更新就诊人信息
        Patient updatePatient = BeanUtil.toBean(updatePatientDto, Patient.class);
        updatePatient.setId(patientId);
        patientMapper.updatePatient(updatePatient);

        // 重新查询就诊人信息并返回
        Patient updatedPatient = patientMapper.selectById(patientId);
        PatientVo patientVo = BeanUtil.toBean(updatedPatient, PatientVo.class);
        return patientVo;
    }

    @Override
    @Transactional
    public void deletePatient(Long patientId) {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询就诊人信息
        Patient patient = patientMapper.selectById(patientId);
        if (ObjectUtils.isEmpty(patient)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 验证就诊人是否属于当前用户
        if (!patient.getUserId().equals(userId)) {
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }

        // 删除就诊人
        patientMapper.deletePatient(patientId);
    }
}