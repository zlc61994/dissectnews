package com.zlc.dissectnews.dao;


import com.zlc.dissectnews.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Insert("insert into user (id,username,password,headUrl) values (null,#{user.username},#{user.password},null) ")
    public int addUser(@Param("user") User user);

    @Select("select * from user where username=#{user.username} and password=#{user.password}")
    public User fingUserByUsernameAndPassword(@Param("user") User user);

    @Select("select * from user where id=#{id} ")
    public User findUserById(int id);
}
