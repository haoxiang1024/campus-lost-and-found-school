package com.xiaolinzi.lostandfound.service;

import com.xiaolinzi.lostandfound.model.Admin;

import java.util.List;

public interface AdminService {
    /**
     * 查询全部管理员
     * @return
     */
    public List<Admin> getAll();

    /**
     * 管理员修改自己的个人头像
     * @param adminID 管理员编号
     * @param photo 新头像的文件名
     * @return
     */
    boolean adminUpdatePhoto(int adminID,String photo);



    /**
     * 管理员登录
     * @param account
     * @param password
     * @return
     */
    public Admin login(String account,String password);


    /**
     * 管理员修改个人信息
     * @param admin
     * @return
     */
    boolean adminUpdateSelf(Admin admin);

    /**
     *管理员修改个人密码
     * @param admin
     * @return
     */
    boolean adminUpdatePassword(Admin admin);

    /**
     * 管局管理员编号获取管理员信息
     * @param adminID
     * @return
     */
    Admin getAdminByID(int adminID);
}
