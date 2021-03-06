package com.connext.wms.service;

import com.connext.wms.entity.ExpressCompany;

import java.util.List;

/**
 * @Author: Chao.Sun
 * @Date: 2019/1/7 9:37
 * @Version 1.0
 */


public interface ExpressCompanyService {

    //分页查询所有快递公司信息
    List<ExpressCompany> selectByPage(Integer start,Integer size);

    //根据关键字查询
    List<ExpressCompany> selectByExample(String key);

    //添加快递公司信息
    void insert(String expressCompanyName,String contactWay);

    //根据公司名称删除信息
    void deleteByExample(String expressCompanyName);

    //修改快递公司信息
    void updateByExample(String newName,String expressCompanyName,String contactWay);


}
