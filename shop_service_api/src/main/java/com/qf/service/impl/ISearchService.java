package com.qf.service.impl;

import com.qf.entity.Goods;

import java.util.List;

public interface ISearchService {

    List<Goods> query(String searchByKeyWord);

    int addGoods(Goods goods);
}
