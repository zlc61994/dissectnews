package com.zlc.dissectnews.dao;

import com.zlc.dissectnews.bean.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsDao {

    @Insert("insert into news values(null,#{news.title},#{news.link}," +
            "#{news.likeCount},#{news.commentCount}," +
            "#{news.createdDate},#{news.image},#{news.uid})")
     int addNews(@Param("news") News news);

    @Select("select * from news order by id desc")
     List<News> findAllNews();

    @Select("select * from news where id = #{id}")
    News findNewsById(int id);

    @Update("update news set  commentCount = #{commentCount} where id = #{id}")
    int updateNewsCommentCount(@Param("commentCount") Integer commentCount, @Param("id") String id);

    @Update("update news set  likeCount = #{likeCount} where id = #{newsId}")
    int updateNewsLikeCount(@Param("newsId")Integer newsId,@Param("likeCount") int likeCount);

}
