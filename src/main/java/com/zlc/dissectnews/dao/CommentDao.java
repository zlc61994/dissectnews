package com.zlc.dissectnews.dao;

import com.zlc.dissectnews.bean.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentDao {
    @Insert("insert into comment values(null,#{comment.content},#{comment.userId},#{comment.newsId},#{comment.createdDate})")
    public int addComment(@Param("comment")Comment comment);

    @Select("select * from comment where newsId = #{id}")
    List<Comment> findCommentByNewsId(Integer id);
}
