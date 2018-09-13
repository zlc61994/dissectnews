package com.zlc.dissectnews.controller;

import com.zlc.dissectnews.bean.News;
import com.zlc.dissectnews.bean.User;
import com.zlc.dissectnews.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import java.util.Date;
import java.util.HashMap;

@Controller
public class NewsController {

    @Autowired
    NewsService newsService;

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
        String imgurl = "http://192.168.3.33:80"+"/file/"+originalFilename;
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

        modelAndView.addObject(news);

        modelAndView.setViewName("/detail");

        return modelAndView;
    }

}
