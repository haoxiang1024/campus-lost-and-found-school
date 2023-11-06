package com.xiaolinzi.lostandfound.mapper;

import com.xiaolinzi.lostandfound.model.LostFoundType;
import com.xiaolinzi.lostandfound.model.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LostFoundTypeMapper {

    /**
     * 获取所有失物招领分类
     * @return
     */
    @Select("select * from lostfoundtype")
    public List<LostFoundType> getAll();


    List<LostFoundType> getDate(@Param("typeName") String typeName,@Param("begin") int begin, @Param("size") int size);

    int getDateTotal(String typeName);

    int getFoundCountByID(int typeID);

    int getLostCountByID(int typeID);

    @Insert("insert into lostfoundtype (name) " +
            "values(#{name})")
    int addType(LostFoundType lostFoundType);


    /**
     *修改类别信息
     * @param lostFoundType
     * @return
     */
    @Update("update lostfoundtype set name=#{name} where id = #{id}")
    int updateType(LostFoundType lostFoundType);

    @Select("select * from lostfoundtype where id=#{typeID}")
    LostFoundType getTypeByID(int typeID);

    @Select("select * from lostfoundtype where name=#{name}")
    LostFoundType getTypeByName(LostFoundType lostFoundType);

//    @Select("select count(*) from lostfoundtype")
//    int getFoundCountByType(int typeID);

    List<Type> getTypeDate(@Param("typeName")String typeName,@Param("foundSort") String foundSort,
                           @Param("lostSort") String lostSort,@Param("begin") int begin, @Param("size") int size );


    @Delete("delete from lostfoundtype where id = #{typeID}")
    int deleteType(int typeID);
}
