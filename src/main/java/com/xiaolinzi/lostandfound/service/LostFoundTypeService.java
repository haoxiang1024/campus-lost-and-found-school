package com.xiaolinzi.lostandfound.service;

import com.xiaolinzi.lostandfound.model.LostFoundType;
import com.xiaolinzi.lostandfound.model.PageBean;
import com.xiaolinzi.lostandfound.model.Type;

import java.util.List;

public interface LostFoundTypeService {

    /**
     * 获取所有的失物招领分类
     * @return
     */
    List<LostFoundType> getAll();

    LostFoundType getTypeByID(int typeID);

    PageBean<LostFoundType> getDate(String typeName,int currentPage,int pageSize);

    PageBean<Type> getTypeDate(String typeName,String foundSort,String lostSort, int currentPage, int pageSize);

    boolean addType(LostFoundType lostFoundType);

    boolean updateType(LostFoundType lostFoundType);

    boolean deleteType(int typeID);
}
