package com.zlc.dissectnews.controller;

import com.zlc.dissectnews.bean.Comment;
import com.zlc.dissectnews.bean.News;
import com.zlc.dissectnews.bean.User;
import com.zlc.dissectnews.service.CommentService;
import com.zlc.dissectnews.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;
    @Autowired
    NewsService newsService;

    @RequestMapping("/addComment")
    public String addComment(Comment comment, HttpSession session){
        comment.setCreatedDate(new Date());
        //获取评论用户的id
        User user =(User) session.getAttribute("user");
        comment.setUserId(user.getId());
        //插入评论
        boolean b = commentService.addComment(comment);

        return "/news/"+comment.getNewsId();
    }

}
