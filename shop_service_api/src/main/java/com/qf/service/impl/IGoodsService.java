package com.qf.service.impl;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @version 1.0
 * @user qf
 * @date 2019/5/20 17:25
 */
public interface IGoodsService {

    List<Goods> queryList();

    int addGoods(Goods goods);

    int deleteGoods(int id);

    Goods getGoodId(int id);

    int updateGoods(Goods goods);

    Goods queryById(Integer gid);
}
