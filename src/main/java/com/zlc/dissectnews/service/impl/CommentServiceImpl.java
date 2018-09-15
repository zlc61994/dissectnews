package com.zlc.dissectnews.service.impl;

import com.zlc.dissectnews.bean.Comment;
import com.zlc.dissectnews.bean.News;
import com.zlc.dissectnews.dao.CommentDao;
import com.zlc.dissectnews.dao.NewsDao;
import com.zlc.dissectnews.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;
    @Autowired
    NewsDao newsDao;

    @Override
    @Transactional
    public boolean addComment(Comment comment) {
      boolean flag = commentDao.addComment(comment) == 1?true:false;
      if(flag){
          News news = newsDao.findNewsById(comment.getNewsId());
          news.setCommentCount(news.getCommentCount()+1);
         flag = newsDao.updateNewsCommentCount(news.getCommentCount(), news.getId()) == 1?true:false;

      }
        return flag;
    }

    @Override
    public List<Comment> findCommentByNewsId(Integer id) {
        if (id!=null){
           List<Comment> comments = commentDao.findCommentByNewsId(id);
            return comments;
        }
        return null;
    }
}
