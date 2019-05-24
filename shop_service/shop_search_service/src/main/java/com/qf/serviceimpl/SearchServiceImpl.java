package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.service.impl.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @user qf
 * @date 2019/5/22 23:52
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public List<Goods> query(String keyword) {

        SolrQuery solrQuery;
        //判断关键字是否为空或者空字符
        System.out.println("搜索的关键字为："+ keyword);
        if(keyword==null||keyword.trim().equals("")){
            solrQuery = new SolrQuery("*:*");
        }else{
            solrQuery = new SolrQuery("ginfo:"+keyword+"|| gname:"+keyword);
        }

        //设置高亮
        solrQuery.setHighlight(true);//开启高亮
        solrQuery.setHighlightSimplePre("<font color='red'>");//设置前缀
        solrQuery.setHighlightSimplePost("</font>");//设置后缀
        solrQuery.addHighlightField("gname");


        List<Goods> goodsList = new ArrayList<>();
        try {
            //将关键字传入搜索库中进行查询
            QueryResponse query = solrClient.query(solrQuery);
            //返回一个查询集
            SolrDocumentList results = query.getResults();

            //
            Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();


            for (SolrDocument result : results) {
                Goods goods = new Goods();
                goods.setId(Integer.parseInt(result.get("id")+""));
                goods.setGname(result.get("gname")+"");

                BigDecimal bigDecimal = BigDecimal.valueOf((float)result.get("gprice"));
                goods.setGprice(bigDecimal);

                goods.setGimages(result.get("gimages") + "");
                goods.setGsave(Integer.parseInt(result.get("gsave") + ""));

                if(highlighting.containsKey(goods.getId() + "")){
                    //当前商品存在高亮
                    Map<String, List<String>> stringListMap = highlighting.get(goods.getId() + "");
                    //获得高亮的字段
                    List<String> gname = stringListMap.get("gname");
                    if(gname != null){
                        goods.setGname(gname.get(0));
                    }
                }

                goodsList.add(goods);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return goodsList ;
    }

    @Override
    public int addGoods(Goods goods) {

        SolrInputDocument document = new SolrInputDocument();
        document.addField("id",goods.getId());
        document.addField("gname",goods.getGname());
        document.addField("ginfo",goods.getGinfo());
        document.addField("gprice",goods.getGprice().floatValue());
        document.addField("gsave",goods.getGsave());
        document.addField("gimages",goods.getGimages());

        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 1;
    }
}
