package com.zlc.dissectnews.controller;

import com.zlc.dissectnews.bean.User;
import com.zlc.dissectnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class FormController {

    @Autowired
    UserService userService;

   @RequestMapping("/{formName}")
    public String home(HttpServletRequest request, @PathVariable String formName){

       if ("home".equals(formName)) {//判断是否有记录登录用户的信息
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
           }
       }

       return formName;
    }


}
