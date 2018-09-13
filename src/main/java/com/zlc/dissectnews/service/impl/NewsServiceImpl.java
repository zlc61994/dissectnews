package com.zlc.dissectnews.service.impl;

import com.zlc.dissectnews.bean.News;
import com.zlc.dissectnews.dao.NewsDao;
import com.zlc.dissectnews.service.NewsService;
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
}
