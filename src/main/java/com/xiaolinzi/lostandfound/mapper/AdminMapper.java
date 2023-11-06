package com.xiaolinzi.lostandfound.mapper;

import com.xiaolinzi.lostandfound.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminMapper {
    /**
     * 用户登陆
     * @param account (账号/手机号)
     * @param password
     * @return
     */
    Admin login(@Param("account")String account, @Param("password")String password);

    /**
     * 管理员修改自己的个人头像
     * @param adminID 管理员编号
     * @param photo 新头像的文件名
     * @return
     */
    @Update("update admin set photo=#{photo} where id = #{adminID}")
    int adminUpdatePhoto(@Param("adminID")int adminID, @Param("photo")String photo);

    /**
     * 获取所有管理员
     * @return
     */
//    @Select("select * from admin")
    public List<Admin> getAll();

    /**
     *管理员修改个人密码
     * @param admin
     * @return
     */
    @Update("update admin set password=#{password} where id = #{id}")
    int adminUpdatePassword(Admin admin);


    /**
     *管理员修改个人信息
     * @param admin
     * @return
     */
    @Update("update admin set nickname=#{nickname},photo=#{photo},phone=#{phone} where id = #{id}")
    int adminUpdateSelf(Admin admin);

    /**
     * 根据管理员编号获取管理员信息
     * @param adminID
     * @return
     */
    Admin getAdminByID(int adminID);

}
