package com.zlc.dissectnews.controller;

import com.zlc.dissectnews.bean.User;
import com.zlc.dissectnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/reg")
    @ResponseBody
    public HashMap<String, Object> register( @Validated User user,BindingResult bindingResult){
        HashMap<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()){
            map.put("code", 1);
            for (FieldError fieldError:bindingResult.getFieldErrors()){
                String field = fieldError.getField();
                String defaultMessage = fieldError.getDefaultMessage();
                if ("username".equals(field)){
                   map.put("msgname", defaultMessage);
               }else{
                    map.put("msgpwd", defaultMessage);
               }
            }
            return map;
            }else {
            boolean b = userService.addUser(user);
            if (b){
                map.put("code",0);
            }else {
                map.put("code",1);
            }
            return map;
        }
    }

    @RequestMapping("/login")
    @ResponseBody
    public HashMap login(User loginUser, @RequestParam("rember") int rember,
                         HttpServletRequest request, HttpServletResponse response){
        HashMap<String, Object> map = new HashMap<>();
        if (loginUser != null) {
            User user = userService.findUsernameAndPassword(loginUser);
            if (user != null) {
                map.put("code",0);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                if (rember == 1) {//实现记住用户
                    Cookie username = new Cookie("username",user.getUsername());
                    Cookie password = new Cookie("password",user.getPassword());
                    response.addCookie(username);
                    response.addCookie(password);
                }
            }else {
                map.put("code",1);
                map.put("msgname","用户名或密码错误");
            }
        }
        return map;
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "home";
    }

    @RequestMapping("/user/{id}")
    public String  personal(@PathVariable int id, Model model){
      User user = userService.findUserById(id);
     model.addAttribute("user",user);
      return "/personal";
    }
}
