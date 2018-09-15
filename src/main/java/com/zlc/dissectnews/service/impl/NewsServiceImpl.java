package com.zlc.dissectnews.service.impl;

import com.zlc.dissectnews.bean.News;
import com.zlc.dissectnews.dao.NewsDao;
import com.zlc.dissectnews.service.NewsService;
import com.zlc.dissectnews.util.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsDao newsDao;

    @Override
    public boolean addNews(News news) {
        boolean b = newsDao.addNews(news)==1? true : false ;

        return b;
    }

    @Override
    public List<News> findAllNews() {
        List<News> allNews = newsDao.findAllNews();
        return allNews;
    }

    @Override
    public News findNewsById(int id) {
        return newsDao.findNewsById(id);
    }

    @Override
    public boolean addLikeCount(Integer newsId, Integer userId) {
        //将点赞人的id放入set集合中
        Long add = JedisUtils.add(newsId+"like", userId.toString());
        Long remove = JedisUtils.remove(newsId + "dislike", userId.toString());
        if (add==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean addDislikeCount(Integer newsId, Integer userId) {
        //将点赞人的id放入set集合中
        Long add = JedisUtils.add(newsId+"dislike", userId.toString());
        Long remove = JedisUtils.remove(newsId + "like", userId.toString());
        if (add==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateLikeCount(Integer newsId, int likecount) {
        boolean flag = newsDao.updateNewsLikeCount(newsId, likecount)==1?true:false;
        return flag;
    }


}
