package com.xiaolinzi.lostandfound.service;

import com.xiaolinzi.lostandfound.model.Lost;
import com.xiaolinzi.lostandfound.model.PageBean;

import java.util.List;

public interface LostService {

    /**
     * 获取所有置顶的失物信息
     * @return
     */
    List<Lost> getStickLost();

    /**
     * 查询未找到的总记录数
     * @return
     */
    int selectTotalCount(String condition);

    /**
     *查询分页信息
     * @param currentPage 当前页码
     * @param pageSize 每页展示条数
     * @param condition 信息的状态
     * @return
     */
    PageBean<Lost> selectByPage(int currentPage, int pageSize, String condition);
    /**
     *分类查询分页信息
     * @param currentPage 当前页码
     * @param pageSize 每页展示条数
     * @param typeID 类别编号
     * @param condition 查询信息的状态
     * @return
     */
    PageBean<Lost> selectByPageAndType(int currentPage, int pageSize,int typeID ,String condition);

    /**
     * 用户修改失物信息
     * @param lost
     * @return
     */
    boolean userUpdateLost(Lost lost);

    /**
     * 修改失物信息的置顶状态
     * @param lostID
     * @param stick
     * @return
     */
    boolean updateStick(int lostID, int stick);

    /**
     * 用户发布招领信息
     * @param lost（title，content，place，phone，img，type，user）
     * @return
     */
    boolean addLost(Lost lost);

    /**
     * 增加失物信息的点击量
     * @param lostID
     * @return
     */
    boolean updateClickCount(int lostID);

    /**
     * 根据失物信息编号增加点击量，并获取同类型的信息
     * @param lostID 招领信息编号
     * @param typeID 分类编号
     * @return
     */
    List<Lost> getSimilarLost(int lostID,int typeID);

    /**
     * 管理员删除失物信息（可批量删除）
     * @param lostIDs 失物信息编号的数组
     * @return
     */
    boolean adminDeleteLost(int[] lostIDs);

    /**
     * 用户删除个人发布的失物信息
     * @param lostID 失物信息编号
     * @return
     */
    boolean userDeleteLost(int lostID);

    /**
     *不根据分类获取个人的招领信息
     * @param userID 用户编号
     * @param condition 失物信息的状态
     * @return
     */
    List<Lost> getSelfLost(int userID,String condition);

    /**
     *根据分类获取个人的招领信息
     * @param userID 用户编号
     * @param typeID 分类编号
     * @param condition 失物信息的状态
     * @return
     */
    List<Lost> getSelfLostAndType(int userID,int typeID,String condition);

    /**
     * 修改失物信息为已找到
     * @param lostID 失物信息编号
     * @return
     */
    boolean updateStateForTrue(int lostID);

    /**
     * 查询信息
     * @param searchTitle 标题
     * @param searchPlace 地点
     * @param typeID 分类
     * @param condition 状态
     * @param currentPage 页码
     * @param pageSize 一页多少条
     * @return
     */
    PageBean<Lost> getDateBySearch(String searchTitle,String searchPlace,String typeID,String condition,int currentPage,int pageSize);



    /**
     * 查询信息
     * @param searchTitle 标题
     * @param searchPlace 地点
     * @param typeID 分类
     * @param stick 是否置顶
     * @param sort 排序
     * @param condition 状态
     * @param currentPage 页码
     * @param pageSize 一页多少条
     * @return
     */
    PageBean<Lost> getDate(String searchTitle,String searchPlace,String typeID,int stick,String sort,String condition,int currentPage,int pageSize);
}
