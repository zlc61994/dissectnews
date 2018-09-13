package com.zlc.dissectnews.service;

import com.zlc.dissectnews.bean.User;

public interface UserService {

    public boolean addUser(User user);


    User findUsernameAndPassword(User loginUser);

    User findUserById(int id);
}
