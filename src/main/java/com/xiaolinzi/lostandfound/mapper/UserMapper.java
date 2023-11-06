package com.xiaolinzi.lostandfound.mapper;


import com.xiaolinzi.lostandfound.model.User;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

// TODO 添加@Mapper
@Mapper
public interface UserMapper {

    /**
     * 用户登陆
     * @param account (账号/手机号)
     * @param password
     * @return
     */
    User login(@Param("account")String account, @Param("password")String password);

    /**
     * 根据用户ID获取用户
     * @param userID (账号/手机号)
     * @return
     */
    User getUserByID(int userID);

    /**
     * 获取所有用户
     * @return
     */
    @Select("select * from user")
    public List<User> getAll();

    /**
     * 查询总记录数
     * @return
     */
    @Select("select count(*) from user ")
    int selectTotalCount();

    /**
     * 查询是否有账号重复的情况
     * @param
     * @return
     */
    @Select("select count(*) from user where account=#{account} or phone=#{phone}")
    int selectAccount(@Param("account")String account,@Param("phone")String phone);

    /**
     * 查询是否有账号重复的情况
     * @param
     * @return
     */
    @Select("select count(*) from user where user.id!=#{userID} and phone=#{phone}")
    int selectPhone(@Param("userID")int userID,@Param("phone")String phone);

    /**
     *用户修改个人信息
     * @param user
     * @return
     */
    @Update("update user set nickname=#{nickname},sex=#{sex},phone=#{phone} where id = #{id}")
    int userUpdateSelf(User user);

    /**
     * 用户修改个人密码
     * @param userID 用户ID
     * @param password 修改的新密码
     * @return
     */
    @Update("update user set password=#{password} where id = #{userID}")
    int userUpdatePassword(@Param("userID")int userID, @Param("password")String password);

    /**
     * 用户修改个人密码
     * @param userID 用户ID
     * @param photo 修改的新密码
     * @return
     */
    @Update("update user set photo=#{photo} where id = #{userID}")
    int userUpdatePhoto(@Param("userID")int userID, @Param("photo")String photo);

    @Update("update user set password=#{password},nickname=#{nickname},phone=#{phone},sex=#{sex},photo=#{photo} where id = #{id}")
    int adminUpdateUser(User user);

    /**
     * 用户充值个人账户余额
     * @param userID
     * @param balance
     * @return
     */
    @Update("update user set balance=balance+#{balance} where id = #{userID}")
    int rechargeBalance(@Param("userID")int userID, @Param("balance") BigDecimal balance);

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Insert("insert into user (account,password,nickname,photo,sex,phone,balance,prestige,reg_date) " +
            "values(#{account},#{password},#{nickname},'default.jpg',#{sex},#{phone},0,100,now())")
    int userRegister(User user);

    /**
     * 根据对应的条件获取对应的信息
     * @param user
     * @param begin 开始位置
     * @param size 条数
     * @return
     */
    List<User> getDateBySearch(@Param("user")User user,@Param("state")int state,@Param("begin")int begin,@Param("size")int size);

    /**
     * 根据条件计算总数
     * @param user
     * @param state 用户的状态（-1：所有，0：禁用，1：启用）
     * @return
     */
    int getTotalBySearch(@Param("user")User user,@Param("state")int state);

    /**
     * 设置用户账号的禁用与启用
     * @param userID 用户ID
     * @param state 用户的状态（0：禁用，1：启用）
     * @return
     */
    int setUpForbidden(@Param("userID")int userID,@Param("state")int state);
}
