package com.zlc.dissectnews.service;

import com.zlc.dissectnews.bean.News;

import java.util.List;

public interface NewsService {


    public boolean addNews(News news);

    public List<News> findAllNews();

    News findNewsById(int id);
}
