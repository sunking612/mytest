package com.taotao.solr.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author wzq
 *
 */
public class Solrtest { 
	/**
	 * @throws Exception
	 */
	@Test
	public void addAndUpdate() throws Exception{
		// 声明接口地址hhhhh
		String baseURL = "http://solr.taotao.com/solr";
		// 创建HttpSolrServer,测试冲突1111
		HttpSolrServer httpSolrServer = new HttpSolrServer(baseURL);
		// 创建SolrInputDocument对象
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "c1001");
		document.addField("item_title", "java编程思想");
		//将document添加到索引库
		httpSolrServer.add(document);
		//提交
		httpSolrServer.commit();


	}
}
