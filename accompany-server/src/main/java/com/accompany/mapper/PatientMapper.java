package com.accompany.mapper;

import com.accompany.entity.Patient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PatientMapper {

    /**
     * 根据用户ID查询就诊人列表
     * @param userId 用户ID
     * @return 就诊人列表
     */
    List<Patient> selectByUserId(Long userId);

    /**
     * 根据ID查询就诊人
     * @param id 就诊人ID
     * @return 就诊人信息
     */
    Patient selectById(Long id);

    /**
     * 插入就诊人
     * @param patient 就诊人信息
     * @return 插入的行数
     */
    int insert(Patient patient);

    /**
     * 将用户的所有就诊人设置为非默认
     * @param userId 用户ID
     */
    void updateAllToNonDefault(Long userId);

    /**
     * 更新就诊人为默认
     * @param id 就诊人ID
     */
    void updateToDefault(Long id);

    /**
     * 更新就诊人信息
     * @param patient 就诊人信息
     */
    void updatePatient(Patient patient);

    /**
     * 删除就诊人
     * @param id 就诊人ID
     */
    void deletePatient(Long id);
}