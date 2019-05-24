package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.impl.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @version 1.0
 * @user qf
 * @date 2019/5/22 20:48
 */
@Controller
@RequestMapping(value="searchController")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @RequestMapping("searchByKey")
    public String searchBykey(String searchByKeyWord, Model model){

        System.out.println("进行商品的搜索，关键词是：" + searchByKeyWord);

        List<Goods> goods = searchService.query(searchByKeyWord);

        model.addAttribute("goods",goods);

        return "searchList";
    }

}
