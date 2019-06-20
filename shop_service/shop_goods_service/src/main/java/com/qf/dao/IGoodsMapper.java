package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Goods;
import com.qf.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version 1.0
 * @user qf
 * @date 2019/5/20 19:04
 */
public interface IGoodsMapper extends BaseMapper<Goods> {

    List<Goods> selectAll(@Param("index") int index, @Param("size") int size);

    List<Goods> Pagelist(Page Page);
}
