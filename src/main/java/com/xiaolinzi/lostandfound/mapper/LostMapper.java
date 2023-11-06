package com.xiaolinzi.lostandfound.mapper;

import com.xiaolinzi.lostandfound.model.Lost;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LostMapper {
    /**
     * 获取所有置顶的失物信息
     * @return
     */
    List<Lost> getStickLost();

    /**
     * 查询总记录数
     * @return
     */
//    @Select("select count(*) from lost where state=#{condition} ")
    int selectTotalCount(String condition);

    /**
     * 某一个分类下的总记录数
     * @param typeID 分类编号
     * @param condition 信息的状态
     * @return
     */
    int selectTotalCountType(@Param("typeID") int typeID, @Param("condition") String condition);

    /**
     *用户修改失物信息
     * @param lost
     * @return
     */
    @Update("update lost set title=#{title},img=#{img},content=#{content},place=#{place},phone=#{phone},lostfoundtype_id=#{lostFoundType.id} where id = #{id}")
    int userUpdateLost(Lost lost);

    /**
     * 用户删除招领信息
     * @param lostID
     * @return
     */
    @Delete("delete from lost where id = #{lostID}")
    int userDeleteLost(int lostID);


    int selectTotalCountSearch(@Param("searchTitle") String searchTitle, @Param("searchPlace") String searchPlace,@Param("typeID") String typeID, @Param("condition") String condition);

    List<Lost> getDateBySearch(@Param("searchTitle") String searchTitle, @Param("searchPlace") String searchPlace,@Param("typeID") String typeID, @Param("condition") String condition,@Param("begin") int begin, @Param("size") int size);

    List<Lost> getDate(@Param("searchTitle") String searchTitle, @Param("searchPlace") String searchPlace,
                        @Param("typeID") String typeID,@Param("stick") int stick,@Param("sort") String sort,
                        @Param("condition") String condition,@Param("begin") int begin, @Param("size") int size);

    int getDateTotal(@Param("searchTitle") String searchTitle, @Param("searchPlace") String searchPlace,
                     @Param("typeID") String typeID,@Param("stick") int stick, @Param("condition") String condition);

    /**
     * 不分类分页查询的失物信息
     * @param begin
     * @param size
     * @param condition
     * @return
     */
    List<Lost> selectLostByPage(@Param("begin") int begin, @Param("size") int size, @Param("condition") String condition);

    /**
     * 分类分页查询招领信息
     * @param begin
     * @param size
     * @param size
     * @param condition
     * @return
     */
    List<Lost> selectByPageAndType(@Param("begin") int begin, @Param("size") int size, @Param("typeID") int typeID,@Param("condition") String condition );


    /**
     * 修改失物信息的置顶状态
     * @param lostID
     * @param stick
     * @return
     */
    @Update("update lost set stick = #{stick} where id = #{lostID}")
    public int updateStick(@Param("lostID") int lostID, @Param("stick") int stick);

    /**
     * 用户发布失物信息
     * @param lost（title，content，place，phone，img，type，user）
     * @return
     */
    @Insert("insert into lost (title,img,pub_date,content,place,phone,state,stick,lostfoundtype_id,user_id,click_count) " +
            "values(#{title},#{img},now(),#{content},#{place},#{phone},'未找到',0,#{lostFoundType.id},#{user.id},0)")
    int addLost(Lost lost);

    /**
     * 增加失物信息的点击量
     * @param lostID
     * @return
     */
    @Update("update lost set click_count = click_count+1 where id = #{lostID}")
    int updateClickCount(int lostID);

    /**
     *不根据分类获取个人的招领信息
     * @param userID 用户编号
     * @param condition 失物信息的状态
     * @return
     */
    List<Lost> getSelfLost(@Param("userID") int userID, @Param("condition") String condition);

    /**
     * 根据失物信息编号增加点击量，并获取同类型的信息
     * @param lostID 招领信息编号
     * @param typeID 分类编号
     * @return
     */
    List<Lost> getSimilarLost(@Param("lostID")int lostID,@Param("typeID")int typeID);

    /**
     *根据分类获取个人的招领信息
     * @param userID 用户编号
     * @param typeID 分类编号
     * @param condition 失物信息的状态
     * @return
     */
    List<Lost> getSelfLostAndType(@Param("userID") int userID,@Param("typeID") int typeID, @Param("condition") String condition);

    /**
     * 管理员删除失物信息（可批量删除）
     * @param lostIDs 失物信息编号的数组
     * @return
     */
    int adminDeleteLost(@Param("lostIDs") int[] lostIDs);

    /**
     * 修改失物信息为已找到
     * @param lostID 失物信息编号
     * @return
     */
    @Update("update lost set state = '已找到',stick=0 where id = #{lostID}")
    int updateStateForTrue(int lostID);
}
