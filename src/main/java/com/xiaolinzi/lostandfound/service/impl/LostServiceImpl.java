package com.xiaolinzi.lostandfound.service.impl;

import com.xiaolinzi.lostandfound.mapper.LostMapper;
import com.xiaolinzi.lostandfound.model.Lost;
import com.xiaolinzi.lostandfound.model.PageBean;
import com.xiaolinzi.lostandfound.service.LostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional//事务管理器
public class LostServiceImpl implements LostService {

    @Autowired
    private LostMapper lostMapper;

    @Override
    public List<Lost> getStickLost() {
        return lostMapper.getStickLost();
    }

    @Override
    public int selectTotalCount(String condition) {
        return lostMapper.selectTotalCount(condition);
    }

    @Override
    public PageBean<Lost> selectByPage(int currentPage, int pageSize, String condition) {
        // 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;
        //5. 查询当前页数据
        List<Lost> rows = lostMapper.selectLostByPage(begin, size,condition);

        //6. 查询总记录数
        int totalCount = lostMapper.selectTotalCount(condition);

        //7. 封装PageBean对象
        PageBean<Lost> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        return pageBean;
    }
    @Override
    public PageBean<Lost> selectByPageAndType(int currentPage, int pageSize, int typeID, String condition) {
        // 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;
        //5. 查询当前页数据
        List<Lost> rows = lostMapper.selectByPageAndType(begin, size,typeID,condition);

        //6. 查询总记录数
        int totalCount = lostMapper.selectTotalCountType(typeID,condition);

        //7. 封装PageBean对象
        PageBean<Lost> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);


        return pageBean;
    }

    @Override
    public boolean userUpdateLost(Lost lost) {
        return lostMapper.userUpdateLost(lost)>0;
    }

    @Override
    public boolean updateStick(int lostID, int stick) {
        return lostMapper.updateStick(lostID,stick)>0;
    }

    @Override
    public boolean addLost(Lost lost) {
        return lostMapper.addLost(lost)>0;
    }

    @Override
    public boolean updateClickCount(int lostID) {
        return lostMapper.updateClickCount(lostID)>0;
    }

    @Override
    public List<Lost> getSimilarLost(int lostID, int typeID) {
        lostMapper.updateClickCount(lostID);
        return lostMapper.getSimilarLost(lostID,typeID);
    }

    @Override
    public boolean adminDeleteLost(int[] lostIDs) {
        return lostMapper.adminDeleteLost(lostIDs)>0;
    }

    @Override
    public boolean userDeleteLost(int lostID) {
        return lostMapper.userDeleteLost(lostID)>0;
    }

    @Override
    public List<Lost> getSelfLost(int userID, String condition) {
        return lostMapper.getSelfLost(userID,condition);
    }

    @Override
    public List<Lost> getSelfLostAndType(int userID, int typeID, String condition) {
        return lostMapper.getSelfLostAndType(userID,typeID,condition);
    }

    @Override
    public boolean updateStateForTrue(int lostID) {
        return lostMapper.updateStateForTrue(lostID)>0;
    }

    @Override
    public PageBean<Lost> getDateBySearch(String searchTitle, String searchPlace, String typeID, String condition, int currentPage, int pageSize) {
        // 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;
        //5. 查询当前页数据
        List<Lost> rows = lostMapper.getDateBySearch("%"+searchTitle+"%","%"+searchPlace+"%",typeID,condition,begin,size);

        //6. 查询总记录数
        int totalCount = lostMapper.selectTotalCountSearch("%"+searchTitle+"%","%"+searchPlace+"%",typeID,condition);

        //7. 封装PageBean对象
        PageBean<Lost> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);


        return pageBean;
    }

    @Override
    public PageBean<Lost> getDate(String searchTitle, String searchPlace, String typeID, int stick, String sort, String condition, int currentPage, int pageSize) {
        // 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;
        //5. 查询当前页数据
        List<Lost> rows = lostMapper.getDate("%"+searchTitle+"%","%"+searchPlace+"%",typeID,stick,sort,condition,begin,size);

        //6. 查询总记录数
        int totalCount = lostMapper.getDateTotal("%"+searchTitle+"%","%"+searchPlace+"%",typeID,stick,condition);

        //7. 封装PageBean对象
        PageBean<Lost> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);


        return pageBean;
    }
}
