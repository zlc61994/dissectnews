package com.zlc.dissectnews.bean;

public class VO {
    News news;
    User user;

    public VO() {
    }

    public VO(News news, User user) {
        this.news = news;
        this.user = user;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
