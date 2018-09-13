package com.zlc.dissectnews.dao;

import com.zlc.dissectnews.bean.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsDao {

    @Insert("insert into news values(null,#{news.title},#{news.link}," +
            "#{news.likeCount},#{news.commentCount}," +
            "#{news.createdDate},#{news.image},#{news.uid})")
    public int addNews(@Param("news") News news);

    @Select("select * from news order by id desc")
    public List<News> findAllNews();

    @Select("select * from news where id = #{id}")
    News findNewsById(int id);
}
