package com.zlc.dissectnews.service.impl;

import com.zlc.dissectnews.bean.User;
import com.zlc.dissectnews.dao.UserDao;
import com.zlc.dissectnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public boolean addUser(User user) {

        int i = userDao.addUser(user);
        if (i == 0){
            return false;
        }
        return true;
    }

    @Override
    public User findUsernameAndPassword(User loginUser) {
        User user = userDao.fingUserByUsernameAndPassword(loginUser);
        return user;
    }

    @Override
    public User findUserById(int id) {
        User user = userDao.findUserById(id);
        if (user != null){
            return user;
        }
        return null;
    }
}
