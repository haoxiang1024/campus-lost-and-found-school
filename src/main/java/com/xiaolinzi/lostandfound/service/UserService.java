package com.xiaolinzi.lostandfound.service;

import com.xiaolinzi.lostandfound.model.PageBean;
import com.xiaolinzi.lostandfound.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    /**
     * 查询全部用户
     * @return
     */
    public List<User> getAll();

    /**
     * 用户登录
     * @param account
     * @param password
     * @return
     */
    public User login(String account,String password);

    /**
     * 根据用户id获取用户信息
     * @param userID 用户ID
     * @return
     */
    User getUserByID(int userID);

    /**
     * 用户修改个人信息
     * @param user
     * @return
     */
    boolean userUpdateSelf(User user);

    /**
     * 用户充值个人账户余额
     * @param userID
     * @param balance
     * @return
     */
    boolean rechargeBalance(int userID, BigDecimal balance);

    /**
     * 用户修改个人密码
     * @param userID 用户ID
     * @param password 修改的新密码
     * @return
     */
    boolean userUpdatePassword(int userID,String password);

    /**
     * 用户修改个人的头像
     * @param userID 用户ID
     * @param photo 修改的新头像文件名
     * @return
     */
    boolean userUpdatePhoto(int userID,String photo);

    /**
     * 用户进行个人注册
     * @param user
     * @return
     */
    boolean userRegister(User user);

    boolean adminUpdateUser(User user);

    /**
     * 账号和联系方式进行查重
     * @param account
     * @param phone
     * @return
     */
    boolean selectAccount(String account,String phone);

    /**
     * 修改的时候联系方式进行查重
     * @param userID 用户编号
     * @param phone 用户联系方式
     * @return
     */
    boolean selectPhone(int userID,String phone);


    /**
     * 根据对应的条件获取对应的信息
     * @param user
     * @param currentPage 页码
     * @param pageSize 每页的条数
     * @param state 用户的状态（-1：所有，0：禁用，1：启用）
     * @return
     */
    PageBean<User> getDateBySearch(User user ,int state, int currentPage, int pageSize);

    /**
     * 设置用户账号的禁用与启用
     * @param userID 用户ID
     * @param state 用户的状态（0：禁用，1：启用）
     * @return
     */
    boolean setUpForbidden(int userID,int state);
}
