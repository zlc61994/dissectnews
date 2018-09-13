package com.zlc.dissectnews.controller;

import com.zlc.dissectnews.bean.Comment;
import com.zlc.dissectnews.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {



    @RequestMapping("/addComment")
    public String addComment(Comment comment, HttpSession session){
        //获取评论用户的id
        User user =(User) session.getAttribute("user");
        comment.setUserId(user.getId());

        return "detail";
    }

}
