package com.accompany.service.impl;

import com.accompany.base.BasicEnum;
import com.accompany.entity.ServiceItem;
import com.accompany.exception.BaseException;
import com.accompany.mapper.ServiceMapper;
import com.accompany.service.ServiceService;
import com.accompany.vo.ServiceCategoryVo;
import com.accompany.vo.ServiceItemListVo;
import com.accompany.vo.ServiceItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 服务模块Service实现
 */
@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public List<ServiceCategoryVo> getCategories() {
        return serviceMapper.selectAllCategories();
    }

    @Override
    public ServiceItemListVo getServiceItems(Long categoryId, String type, String keyword, String sort, Integer page, Integer pageSize) {
        // 设置默认分页参数
        if (ObjectUtils.isEmpty(page) || page < 1) {
            page = 1;
        }
        if (ObjectUtils.isEmpty(pageSize) || pageSize < 1) {
            pageSize = 10;
        }

        // 计算偏移量
        Integer offset = (page - 1) * pageSize;

        // 查询服务项目列表
        List<ServiceItemVo> list = serviceMapper.selectServiceItems(
                categoryId, type, keyword, sort, offset, pageSize
        );

        // 统计总数
        Integer total = serviceMapper.countServiceItems(categoryId, type, keyword);

        // 封装返回结果
        ServiceItemListVo result = new ServiceItemListVo();
        result.setList(list);
        result.setTotal(total);
        result.setPage(page);
        result.setPageSize(pageSize);

        return result;
    }

    @Override
    public ServiceItemVo getServiceItemById(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new BaseException(BasicEnum.SERVICE_ID_NOT_EMPTY);
        }

        ServiceItemVo serviceItemVo = serviceMapper.selectServiceItemById(id);
        if (ObjectUtils.isEmpty(serviceItemVo)) {
            throw new BaseException(BasicEnum.SERVICE_NOT_FOUND);
        }

        return serviceItemVo;
    }
}