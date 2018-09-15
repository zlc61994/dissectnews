package com.zlc.dissectnews.controller;

import com.zlc.dissectnews.bean.Comment;
import com.zlc.dissectnews.bean.CommentVO;
import com.zlc.dissectnews.bean.News;
import com.zlc.dissectnews.bean.User;
import com.zlc.dissectnews.service.CommentService;
import com.zlc.dissectnews.service.NewsService;
import com.zlc.dissectnews.service.UserService;
import com.zlc.dissectnews.util.JedisUtils;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class NewsController {

    @Autowired
    NewsService newsService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userServic;
    //文件的保存路径
    String saveDir = "/file";

    @RequestMapping("/uploadImage")
    @ResponseBody
    public HashMap upload(@RequestParam("file") MultipartFile newsimg, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        //文件保存地址
        String realPath = request.getServletContext().getRealPath(saveDir);
        //上传文件名
        String originalFilename = newsimg.getOriginalFilename();
        try {
            //保存文件
            newsimg.transferTo(new File(realPath,originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("code",0);
        String imgurl = "http://192.168.3.6:80"+"/file/"+originalFilename;
        map.put("msg",imgurl);
        return map;
    }
    @RequestMapping("/user/addNews")
    @ResponseBody
    public HashMap<String, Object> addNews(News news,HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        news.setUid(user.getId());//设置news的分享人id

        Date date = new Date();
        news.setCreatedDate(date);

        boolean b = newsService.addNews(news);
        if (b){
            map.put("code",0);
        }else {
            map.put("code",1);
        }
        return map;
    }

    @RequestMapping("/news/{id}")
    public ModelAndView findNewsById(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView();
        News news  = newsService.findNewsById(id);
        List<Comment> comments = commentService.findCommentByNewsId(id);
        ArrayList<CommentVO> commentsvo = new ArrayList<>();
        if (comments != null){
            for (Comment comment:comments) {
                CommentVO commentVO = new CommentVO();
                commentVO.setComment(comment);
                User userById = userServic.findUserById(comment.getUserId());
                commentVO.setUser(userById);
                commentsvo.add(commentVO);
            }
        }
        modelAndView.addObject(news);
        modelAndView.addObject("comments",commentsvo);
        modelAndView.addObject("date",new DateTool());
        modelAndView.setViewName("/detail");

        return modelAndView;
    }

    @RequestMapping("/like")
    @ResponseBody
    public HashMap<String, Object> addLikeCount(Integer newsId, HttpSession session){
        User user = (User) session.getAttribute("user");
        HashMap<String, Object> map = new HashMap<>();
        if (user != null && newsId != null) {
            boolean flag = newsService.addLikeCount(newsId, user.getId());
            if (flag) {//增加成功
                map.put("code", 0);
            } else {//增加失败
                map.put("code", 1);
            }
        } else {//user 或 新闻id为null
            map.put("code", 1);
        }
        Long likecount = JedisUtils.Count(newsId.toString() + "like");

        Long dislikecount = JedisUtils.Count(newsId.toString() + "dislike");
        newsService.updateLikeCount(newsId, likecount.intValue()-dislikecount.intValue());
        map.put("msg", likecount-dislikecount);
        return map;
    }


    @RequestMapping("/dislike")
    @ResponseBody
    public HashMap<String, Object> addDislikeCount(Integer newsId, HttpSession session){
        User user = (User) session.getAttribute("user");
        HashMap<String, Object> map = new HashMap<>();
        if (user != null && newsId != null) {
            boolean flag = newsService.addDislikeCount(newsId, user.getId());
            if (flag) {//增加成功
                map.put("code", 0);
            } else {//增加失败
                map.put("code", 1);
            }
        } else {//user 或 新闻id为null
            map.put("code", 1);
        }
        Long likecount = JedisUtils.Count(newsId.toString() + "like");

        Long dislikecount = JedisUtils.Count(newsId.toString() + "dislike");
        newsService.updateLikeCount(newsId, likecount.intValue()-dislikecount.intValue());

        map.put("msg", likecount-dislikecount);//返回like 和 dislike 相减
        return map;
    }

}
