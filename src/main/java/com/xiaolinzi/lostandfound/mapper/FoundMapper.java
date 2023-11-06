package com.xiaolinzi.lostandfound.mapper;

import com.xiaolinzi.lostandfound.model.Found;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FoundMapper {

    /**
     * 获取所有置顶的招领信息
     * @return
     */
    List<Found> getStickFound();

    /**
     * 不分类分页查询招领信息
     * @param begin
     * @param size
     * @param condition
     * @return
     */
    List<Found> selectFoundByPage(@Param("begin") int begin, @Param("size") int size,@Param("condition") String condition );

    /**
     * 用户发布招领信息
     * @param found（title，content，place，phone，img，type，user）
     * @return
     */
    @Insert("insert into found (title,img,pub_date,content,place,phone,state,stick,lostfoundtype_id,user_id,click_count) " +
            "values(#{title},#{img},now(),#{content},#{place},#{phone},'未找到',0,#{lostFoundType.id},#{user.id},0)")
    int addFound(Found found);

    /**
     * 分类分页查询招领信息
     * @param begin
     * @param size
     * @param size
     * @param condition
     * @return
     */
    List<Found> selectByPageAndType(@Param("begin") int begin, @Param("size") int size, @Param("typeID") int typeID,@Param("condition") String condition );

    /**
     *用户修改招领信息
     * @param found
     * @return
     */
    @Update("update found set title=#{title},img=#{img},content=#{content},place=#{place},phone=#{phone},lostfoundtype_id=#{lostFoundType.id} where id = #{id}")
//    @Update("update found set title=#{title},img=#{img},content=#{content},place=#{place},phone=#{phone} where id = #{id}")
    int userUpdateFound(Found found);

    /**
     * 查询所有的招领总记录数
     * @return
     */
//    @Select("select count(*) from found where state=#{condition} ")
    int selectTotalCount(String condition);

    /**
     * 某一个分类下的总记录数
     * @param typeID 分类编号
     * @param condition 信息的状态
     * @return
     */
    int selectTotalCountType(@Param("typeID") int typeID, @Param("condition") String condition);
//    searchTitle,searchPlace,typeID,condition
    int selectTotalCountSearch(@Param("searchTitle") String searchTitle, @Param("searchPlace") String searchPlace,@Param("typeID") String typeID, @Param("condition") String condition);

    List<Found> getDateBySearch(@Param("searchTitle") String searchTitle, @Param("searchPlace") String searchPlace,
                                @Param("typeID") String typeID, @Param("condition") String condition,
                                @Param("begin") int begin, @Param("size") int size);

    List<Found> getDate(@Param("searchTitle") String searchTitle, @Param("searchPlace") String searchPlace,
                        @Param("typeID") String typeID,@Param("stick") int stick,@Param("sort") String sort,
                        @Param("condition") String condition,@Param("begin") int begin, @Param("size") int size);

    int getDateTotal(@Param("searchTitle") String searchTitle, @Param("searchPlace") String searchPlace,
                     @Param("typeID") String typeID,@Param("stick") int stick, @Param("condition") String condition);
    /**
     * 修改招领信息的置顶状态
     * @param foundID
     * @param stick
     * @return
     */
    @Update("update found set stick = #{stick} where id = #{foundID}")
    public int updateStick(@Param("foundID") int foundID, @Param("stick") int stick);

    /**
     * 修改招领信息的点击量
     * @param foundID 招领信息编号
     * @return
     */
    @Update("update found set click_count = click_count+1 where id = #{foundID}")
    int updateClickCount(int foundID);

    /**
     * 用户删除招领信息
     * @param foundID
     * @return
     */
    @Delete("delete from found where id = #{foundID}")
    int userDeleteFound(int foundID);


    /**
     * 管理员删除招领信息（可批量删除）
     * @param foundIDs 失物信息编号的数组
     * @return
     */
    int adminDeleteFound(@Param("foundIDs") int[] foundIDs);

    /**
     * 根据招领信息编号增加点击量，并获取同类型的信息
     * @param foundID 招领信息编号
     * @param typeID 分类编号
     * @return
     */
    List<Found> getSimilarFound(@Param("foundID") int foundID, @Param("typeID") int typeID);


    /**
     *不根据分类获取个人的招领信息
     * @param userID 用户编号
     * @param condition 招领信息的状态
     * @return
     */
    List<Found> getSelfFound(@Param("userID") int userID, @Param("condition") String condition);

    /**
     *根据分类获取个人的招领信息
     * @param userID 用户编号
     * @param typeID 分类编号
     * @param condition 招领信息的状态
     * @return
     */
    List<Found> getSelfFoundAndType(@Param("userID") int userID,@Param("typeID") int typeID, @Param("condition") String condition);


    /**
     * 修改招领信息状态为已找到
     * @param foundID 招领信息编号
     * @return
     */
    @Update("update found set state = '已找到',stick=0 where id = #{foundID}")
    int updateStateForTrue(int foundID);
}
