package com.zlc.dissectnews.controller;

import com.zlc.dissectnews.bean.News;
import com.zlc.dissectnews.bean.User;
import com.zlc.dissectnews.bean.VO;
import com.zlc.dissectnews.service.NewsService;
import com.zlc.dissectnews.service.UserService;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FormController {

    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;

   @RequestMapping("/home")
    public String home(HttpServletRequest request,Model model){
      /* //判断是否有记录登录用户的信息
           Cookie[] cookies = request.getCookies();
           if (cookies != null) {
               User user = new User();
               for (Cookie c : cookies) {
                   if ("username".equals(c.getName()) && c.getValue() != null) {
                       user.setUsername(c.getValue());
                   } else if ("password".equals(c.getValue()) && c.getValue() != null) {
                       user.setPassword(c.getValue());
                   }
               }
               if (user.getUsername() != null && user.getPassword() != null) {
                   user = userService.findUsernameAndPassword(user);
                   if (user != null) {
                       HttpSession session = request.getSession();
                       session.setAttribute("user", user);
                   }

               }
           }*/

       List<News> allNews = newsService.findAllNews();
       List<VO> vos = new ArrayList<>();
       for (News news:allNews){
           User userById = userService.findUserById(news.getUid());
           VO vo = new VO(news, userById);
           vos.add(vo);
       }

        model.addAttribute("vos",vos);
       model.addAttribute("date",new DateTool());
       return "home";
    }


}
