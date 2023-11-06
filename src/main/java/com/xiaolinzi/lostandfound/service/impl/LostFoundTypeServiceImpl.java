package com.xiaolinzi.lostandfound.service.impl;

import com.xiaolinzi.lostandfound.mapper.LostFoundTypeMapper;
import com.xiaolinzi.lostandfound.model.LostFoundType;
import com.xiaolinzi.lostandfound.model.PageBean;
import com.xiaolinzi.lostandfound.model.Type;
import com.xiaolinzi.lostandfound.service.LostFoundTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional//事务管理器
public class LostFoundTypeServiceImpl implements LostFoundTypeService {

    @Autowired
    private LostFoundTypeMapper lostFoundTypeMapper;

    @Override
    public List<LostFoundType> getAll() {
        return lostFoundTypeMapper.getAll();
    }

    @Override
    public LostFoundType getTypeByID(int typeID) {
        return lostFoundTypeMapper.getTypeByID(typeID);
    }

    @Override
    public PageBean<LostFoundType> getDate(String typeName, int currentPage, int pageSize) {
        // 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;
        //5. 查询当前页数据
        List<LostFoundType> rows = lostFoundTypeMapper.getDate("%"+typeName+"%",begin,size);

        //6. 查询总记录数
        int totalCount = lostFoundTypeMapper.getDateTotal("%"+typeName+"%");

        //7. 封装PageBean对象
        PageBean<LostFoundType> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        return pageBean;
    }

    @Override
    public PageBean<Type> getTypeDate(String typeName, String foundSort, String lostSort, int currentPage, int pageSize) {
        // 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;
        //5. 查询当前页数据
        List<Type> rows = lostFoundTypeMapper.getTypeDate("%"+typeName+"%",foundSort,lostSort,begin,size);
        if (!(foundSort.equals("")&&lostSort.equals(""))){
            if (foundSort.equals("")){//在存在排序的情况下，如果foundSort为空,则代表是按照lostSort排序，则foundCount获取为空
                for (Type row : rows) {
                    row.setFoundCount(lostFoundTypeMapper.getFoundCountByID(row.getId()));
                    row.setSumCount(row.getFoundCount()+row.getLostCount());
                }
            }else {
                for (Type row : rows) {
                    row.setLostCount(lostFoundTypeMapper.getLostCountByID(row.getId()));
                    row.setSumCount(row.getFoundCount()+row.getLostCount());
                }
            }
        }else {
            for (Type row : rows) {
                row.setFoundCount(lostFoundTypeMapper.getFoundCountByID(row.getId()));
                row.setLostCount(lostFoundTypeMapper.getLostCountByID(row.getId()));
                row.setSumCount(row.getFoundCount()+row.getLostCount());
            }
        }

        //6. 查询总记录数
        int totalCount = lostFoundTypeMapper.getDateTotal("%"+typeName+"%");

        //7. 封装PageBean对象
        PageBean<Type> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        return pageBean;
    }

    @Override
    public boolean addType(LostFoundType lostFoundType) {
        LostFoundType lostFoundType1 = lostFoundTypeMapper.getTypeByName(lostFoundType);
        if (lostFoundType1!=null){
            return false;
        }
        return lostFoundTypeMapper.addType(lostFoundType)>0;
    }

    @Override
    public boolean updateType(LostFoundType lostFoundType) {
        LostFoundType lostFoundType1 = lostFoundTypeMapper.getTypeByName(lostFoundType);
        if (lostFoundType1!=null){
            return false;
        }
        return lostFoundTypeMapper.updateType(lostFoundType)>0;
    }

    @Override
    public boolean deleteType(int typeID) {
        return lostFoundTypeMapper.deleteType(typeID)>0;
    }
}
