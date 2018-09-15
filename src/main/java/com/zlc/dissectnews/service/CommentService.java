package com.zlc.dissectnews.service;

import com.zlc.dissectnews.bean.Comment;

import java.util.List;

public interface CommentService {

    boolean addComment(Comment comment);

    List<Comment> findCommentByNewsId(Integer id);
}
