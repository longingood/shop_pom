package com.qf.shop_search_service;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchServiceApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Test
    public void contextLoads() throws IOException, SolrServerException {



        for (int i = 0; i < 10; i++) {

            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", i );
            document.addField("gname", "华为手机" + i);
            document.addField("ginfo", "性价比很高的国产手机，国产手机中的战斗机" + i);
            document.addField("gprice", 9.9 + i);
            document.addField("gsave", 1000 + i);
            document.addField("gimages", "http://www.baidu.com");

            solrClient.add(document);

        }

        solrClient.commit();

    }

}
