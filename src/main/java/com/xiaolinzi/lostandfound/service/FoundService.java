package com.xiaolinzi.lostandfound.service;

import com.xiaolinzi.lostandfound.model.Found;
import com.xiaolinzi.lostandfound.model.PageBean;

import java.util.List;

public interface FoundService {
    /**
     * 获取所有置顶的招领信息
     * @return
     */
    List<Found> getStickFound();

    /**
     * 根据招领信息编号增加点击量，并获取同类型的信息
     * @param foundID 招领信息编号
     * @param typeID 分类编号
     * @return
     */
    List<Found> getSimilarFound(int foundID,int typeID);

    /**
     * 用户发布招领信息
     * @param found（title，content，place，phone，img，type，user）
     * @return
     */
    boolean addFound(Found found);

    /**
     * 查询未找到的总记录数
     * @return
     */
    int selectTotalCount(String condition);

    /**
     * 分页查询未找到的招领信息
     * @param begin
     * @param size
     * @param condition 招领信息的状态
     * @return
     */
    List<Found> selectFoundByPage(int begin, int size, String condition);

    /**
     *查询分页信息
     * @param currentPage 当前页码
     * @param pageSize 每页展示条数
     * @param condition 查询信息的状态
     * @return
     */
    PageBean<Found> selectByPage(int currentPage, int pageSize, String condition);

    /**
     *分类查询分页信息
     * @param currentPage 当前页码
     * @param pageSize 每页展示条数
     * @param typeID 类别编号
     * @param condition 查询信息的状态
     * @return
     */
    PageBean<Found> selectByPageAndType(int currentPage, int pageSize,int typeID ,String condition);

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
    PageBean<Found> getDateBySearch(String searchTitle,String searchPlace,String typeID,String condition,int currentPage,int pageSize);


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
    PageBean<Found> getDate(String searchTitle,String searchPlace,String typeID,int stick,String sort,String condition,int currentPage,int pageSize);

    /**
     * 用户修改招领信息
     * @param found
     * @return
     */
    boolean userUpdateFound(Found found);

    /**
     * 修改招领信息的置顶状态
     * @param foundID
     * @param stick
     * @return
     */
    boolean updateStick(int foundID, int stick);

    /**
     * 管理员删除招领信息（可批量删除）
     * @param foundIDs 招领信息编号的数组
     * @return
     */
    boolean adminDeleteFound(int[] foundIDs);

    /**
     * 增加招领信息点击量
     * @param foundID 招领信息编号
     * @return
     */
    boolean updateClickCount(int foundID);

    /**
     * 用户删除招领信息
     * @param foundID 招领信息编号
     * @return
     */
    boolean userDeleteFound(int foundID);

    /**
     *不根据分类获取个人的招领信息
     * @param userID 用户编号
     * @param condition 招领信息的状态
     * @return
     */
    List<Found> getSelfFound(int userID,String condition);

    /**
     *根据分类获取个人的招领信息
     * @param userID 用户编号
     * @param typeID 分类编号
     * @param condition 招领信息的状态
     * @return
     */
    List<Found> getSelfFoundAndType(int userID,int typeID,String condition);


    /**
     * 修改招领信息状态为已找到
     * @param foundID 招领信息编号
     * @return
     */
    boolean updateStateForTrue(int foundID);

}
