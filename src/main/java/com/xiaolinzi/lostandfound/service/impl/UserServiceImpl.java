package com.xiaolinzi.lostandfound.service.impl;

import com.xiaolinzi.lostandfound.mapper.UserMapper;
import com.xiaolinzi.lostandfound.model.PageBean;
import com.xiaolinzi.lostandfound.model.User;
import com.xiaolinzi.lostandfound.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional//事务管理器
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public User login(String account, String password) {
        return userMapper.login(account,password);
    }

    @Override
    public User getUserByID(int userID) {
        return userMapper.getUserByID(userID);
    }

    @Override
    public boolean userUpdateSelf(User user) {
        return userMapper.userUpdateSelf(user)>0;
    }

    @Override
    public boolean rechargeBalance(int userID, BigDecimal balance) {
        return userMapper.rechargeBalance(userID,balance)>0;
    }

    @Override
    public boolean userUpdatePassword(int userID,String password) {
        return userMapper.userUpdatePassword(userID,password)>0;
    }

    @Override
    public boolean userUpdatePhoto(int userID, String photo) {
        return userMapper.userUpdatePhoto(userID,photo)>0;
    }

    @Override
    public boolean userRegister(User user) {
        return userMapper.userRegister(user)>0;
    }

    @Override
    public boolean adminUpdateUser(User user) {
        return userMapper.adminUpdateUser(user)>0;
    }

    @Override
    public boolean selectAccount(String account, String phone) {
        return userMapper.selectAccount(account,phone)>0;
    }

    @Override
    public boolean selectPhone( int userID,String phone) {
        return userMapper.selectPhone(userID,phone)>0;
    }


    @Override
    public PageBean<User> getDateBySearch(User user,int state, int currentPage, int pageSize) {
        user.setAccount("%"+user.getAccount()+"%");
        user.setNickname("%"+user.getNickname()+"%");
        user.setPhone("%"+user.getPhone()+"%");
        // 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;

        //5. 查询当前页数据
        List<User> rows = userMapper.getDateBySearch(user,state ,begin, size);

        //6. 查询总记录数
        int totalCount = userMapper.getTotalBySearch(user,state);

        //7. 封装PageBean对象
        PageBean<User> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);


        return pageBean;
    }

    @Override
    public boolean setUpForbidden(int userID, int state) {
        return userMapper.setUpForbidden(userID,state)>0;
    }

}
