package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.dao.IGoodsMapper;
import com.qf.entity.Goods;
import com.qf.entity.Page;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public List<Goods> queryList() {
        List<Goods> list = goodsMapper.selectList(null);
        return list;
    }



    @Override
    public int addGoods(Goods goods) {
        //添加到数据库中
        goodsMapper.insert(goods);

       /* //同步到索引库中
        searchService.addGoods(goods);*/

       //将商品信息放入到队列中,进行异步化处理
      rabbitTemplate.convertAndSend("goods_exchange", "", goods);

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

    /**
     * 更改商品
     * @param goods
     * @return
     */
    @Override
    public int updateGoods(Goods goods) {
        goodsMapper.updateById(goods);
        return 0;
    }

    @Override
    public Goods queryById(Integer gid) {
        return goodsMapper.selectById(gid);
    }

    @Override
    public Page<Goods> pageList(Page page) {
        //总条数
        int count = goodsMapper.selectCount(null);
        page.setTotalElements(count);

        //总页数
        if (count%page.getSize()==0){
            page.setTotalPage(count/page.getSize());
        }else {
            page.setTotalPage(count/page.getSize()+1);
        }

        //设置分页参数
        PageHelper.startPage(page.getNumber(),page.getSize());
        com.github.pagehelper.Page page2= (com.github.pagehelper.Page) goodsMapper.selectList(null);
        PageInfo<Goods> pageInfo=new PageInfo<>(page2);

        page.setList(pageInfo.getList());
        return page;
    }

    @Override
    public Page<Goods> getGoodsPage(Page<Goods> page) {

           int number= page.getNumber();//当前页
        int size = page.getSize();//当前页显示的条数

        int pageElements= goodsMapper.selectCount(null);//查询总条数

            int  totalPage = 0;
        if(pageElements % size == 0){

            totalPage=pageElements/size;
        }else {
            totalPage=(pageElements/size)+1;
        }


           List<Goods> list = goodsMapper.selectAll((number-1)*size,size);

           page.setTotalElements(pageElements);
        page.setTotalPage(totalPage);
        page.setList(list);
        return page;
    }
}
