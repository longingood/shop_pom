package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.impl.IGoodsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @version 1.0
 * @user qf
 * @date 2019/5/20 19:35
 */
@Controller
@RequestMapping(value = "goodsController")
public class GoodsController {

    @Reference
    private IGoodsService goodsService;

    @Value("${img.service}")
    private String imgPath;

    /**
     * 遍历全部商品
     * @param model
     * @return
     */
    @RequestMapping("queryList")
    public String queryList(Model model){

        List<Goods> goods = goodsService.queryList();

        model.addAttribute("goods",goods);
        model.addAttribute("imgPath",imgPath);

        return "goodsList";
    }

    /**
     * 添加商品
     * @param goods
     * @param gimageList
     * @return
     */
    @RequestMapping("/goodsAdd")
    public String goodsAdd(Goods goods,String[] gimageList){

        String gimages="";
        for (String s : gimageList) {
            if (gimages.length()>0){
                gimages += "|";
            }
            gimages += s;
        }

        goods.setGimages(gimages);

        goodsService.addGoods(goods);

        return "redirect:/goodsController/queryList";
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @RequestMapping("/goodsDelete")
    public String goodsDelete(int id){
        System.out.println(id);
        goodsService.deleteGoods(id);
        return "redirect:/goodsController/queryList";
    }

    /**
     * 通过id查询商品
     * @return
     */
    @RequestMapping("/getGoodsById")
    public String getGoodsById(int id,Model model){

        Goods goods = goodsService.getGoodId(id);

        model.addAttribute("goods",goods);
        model.addAttribute("imgPath",imgPath);
        return "updateGoods";
    }


    /**
     *商品的更改
     * @param goods
     * @return
     */
    @RequestMapping("/goodsUpdate")
    public String goodsUpdate(Goods goods){

        goodsService.updateGoods(goods);

        return "redirect:/goodsController/queryList";
    }
}
