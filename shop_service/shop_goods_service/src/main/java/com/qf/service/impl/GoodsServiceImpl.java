package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.IGoodsMapper;
import com.qf.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user qf
 * @date 2019/5/20 17:54
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private IGoodsMapper goodsMapper;

    @Reference
    private ISearchService searchService;

    @Override
    public List<Goods> queryList() {
        List<Goods> list = goodsMapper.selectList(null);
        return list;
    }

    @Override
    public int addGoods(Goods goods) {
        //添加到数据库中
        goodsMapper.insert(goods);

        //同步到索引库中
        searchService.addGoods(goods);

        return 1 ;
    }

    @Override
    public int deleteGoods(int id) {
        return goodsMapper.deleteById(id);
    }

    @Override
    public Goods getGoodId(int id) {

        return goodsMapper.selectById(id);
    }

    @Override
    public int updateGoods(Goods goods) {
        goodsMapper.updateById(goods);
        return 0;
    }
}
