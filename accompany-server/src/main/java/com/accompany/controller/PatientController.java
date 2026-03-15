package com.accompany.controller;

import com.accompany.base.Result;
import com.accompany.dto.AddPatientDto;
import com.accompany.dto.UpdatePatientDto;
import com.accompany.service.PatientService;
import com.accompany.vo.PatientVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class PatientController {

    @Autowired
    private PatientService patientService;

    /**
     * 获取就诊人列表
     */
    @GetMapping("/patients")
    public Result<List<PatientVo>> getPatientList() {
        List<PatientVo> patientVos = patientService.getPatientList();
        return Result.success(patientVos);
    }

    /**
     * 添加就诊人
     */
    @PostMapping("/patients")
    public Result<PatientVo> addPatient(@RequestBody AddPatientDto addPatientDto) {
        PatientVo patientVo = patientService.addPatient(addPatientDto);
        return Result.success(patientVo);
    }

    /**
     * 设置默认就诊人
     */
    @PutMapping("/patients/{id}/default")
    public Result setDefaultPatient(@PathVariable("id") Long patientId) {
        patientService.setDefaultPatient(patientId);
        return Result.success();
    }

    /**
     * 更新就诊人信息
     */
    @PutMapping("/patients/{id}")
    public Result<PatientVo> updatePatient(@PathVariable("id") Long patientId, @RequestBody UpdatePatientDto updatePatientDto) {
        PatientVo patientVo = patientService.updatePatient(patientId, updatePatientDto);
        return Result.success(patientVo);
    }

    /**
     * 删除就诊人
     */
    @DeleteMapping("/patients/{id}")
    public Result deletePatient(@PathVariable("id") Long patientId) {
        patientService.deletePatient(patientId);
        return Result.success();
    }
}