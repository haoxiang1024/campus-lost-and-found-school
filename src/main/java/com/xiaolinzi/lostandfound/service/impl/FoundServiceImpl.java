package com.xiaolinzi.lostandfound.service.impl;

import com.xiaolinzi.lostandfound.mapper.FoundMapper;
import com.xiaolinzi.lostandfound.model.Found;
import com.xiaolinzi.lostandfound.model.PageBean;
import com.xiaolinzi.lostandfound.service.FoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional//事务管理器
public class FoundServiceImpl implements FoundService {

    @Autowired
    private FoundMapper foundMapper;

    @Override
    public List<Found> getStickFound() {
        return foundMapper.getStickFound();
    }

    @Override
    public List<Found> getSimilarFound(int foundID,int typeID) {
        foundMapper.updateClickCount(foundID);
        return foundMapper.getSimilarFound(foundID,typeID);
    }

    @Override
    public boolean addFound(Found found) {
        return foundMapper.addFound(found)>0;
    }

    @Override
    public int selectTotalCount(String condition) {
        return foundMapper.selectTotalCount(condition);
    }

    @Override
    public List<Found> selectFoundByPage(int begin, int size, String condition) {
        return foundMapper.selectFoundByPage(begin,size,condition);
    }

    @Override
    public PageBean<Found> selectByPage(int currentPage, int pageSize,String condition) {
        // 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;
        //5. 查询当前页数据
        List<Found> rows = foundMapper.selectFoundByPage(begin, size,condition);

        //6. 查询总记录数
        int totalCount = foundMapper.selectTotalCount(condition);

        //7. 封装PageBean对象
        PageBean<Found> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);


        return pageBean;

    }

    @Override
    public PageBean<Found> selectByPageAndType(int currentPage, int pageSize, int typeID, String condition) {
        // 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;
        //5. 查询当前页数据
        List<Found> rows = foundMapper.selectByPageAndType(begin, size,typeID,condition);

        //6. 查询总记录数
        int totalCount = foundMapper.selectTotalCountType(typeID,condition);

        //7. 封装PageBean对象
        PageBean<Found> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);


        return pageBean;
    }

    @Override
    public PageBean<Found> getDateBySearch(String searchTitle, String searchPlace, String typeID, String condition, int currentPage, int pageSize) {
        // 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;
        //5. 查询当前页数据
        List<Found> rows = foundMapper.getDateBySearch("%"+searchTitle+"%","%"+searchPlace+"%",typeID,condition,begin,size);

        //6. 查询总记录数
        int totalCount = foundMapper.selectTotalCountSearch("%"+searchTitle+"%","%"+searchPlace+"%",typeID,condition);

        //7. 封装PageBean对象
        PageBean<Found> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);


        return pageBean;
    }

    @Override
    public PageBean<Found> getDate(String searchTitle, String searchPlace, String typeID, int stick, String sort, String condition, int currentPage, int pageSize) {
        // 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;
        //5. 查询当前页数据
        List<Found> rows = foundMapper.getDate("%"+searchTitle+"%","%"+searchPlace+"%",typeID,stick,sort,condition,begin,size);

        //6. 查询总记录数
        int totalCount = foundMapper.getDateTotal("%"+searchTitle+"%","%"+searchPlace+"%",typeID,stick,condition);

        //7. 封装PageBean对象
        PageBean<Found> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);


        return pageBean;
    }

    @Override
    public boolean userUpdateFound(Found found) {
        return foundMapper.userUpdateFound(found)>0;
    }

    @Override
    public boolean updateStick(int foundID, int stick) {
        return foundMapper.updateStick(foundID,stick)>0;
    }

    @Override
    public boolean adminDeleteFound(int[] foundIDs) {
        return foundMapper.adminDeleteFound(foundIDs)>0;
    }

    @Override
    public boolean updateClickCount(int foundID) {
        return foundMapper.updateClickCount(foundID)>0;
    }

    @Override
    public boolean userDeleteFound(int foundID) {
        return foundMapper.userDeleteFound(foundID)>0;
    }

    @Override
    public List<Found> getSelfFound(int userID, String condition) {
        return foundMapper.getSelfFound(userID,condition);
    }

    @Override
    public List<Found> getSelfFoundAndType(int userID, int typeID, String condition) {
        return foundMapper.getSelfFoundAndType(userID,typeID,condition);
    }

    @Override
    public boolean updateStateForTrue(int foundID) {
        return foundMapper.updateStateForTrue(foundID)>0;
    }
}
