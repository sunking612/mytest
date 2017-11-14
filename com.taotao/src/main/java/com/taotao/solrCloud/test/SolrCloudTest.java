package com.taotao.solrCloud.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import net.sf.jsqlparser.statement.create.table.ColumnDefinition;

public class SolrCloudTest {

	private CloudSolrServer cloudSolrServer;
	
	@Before
	public void init(){
		//声明接口地址
		String zkHost="192.168.37.161:3181,192.168.37.161:3182,192.168.37.161:3183";
		//创建CloudSolrServer连接
		this.cloudSolrServer=new CloudSolrServer(zkHost);
		this.cloudSolrServer.setDefaultCollection("collection2");
	}
	
	//添加和修改
	@Test
	public void testCloudSolrServer() throws Exception{
		
		//创建SolrInputDocument对象
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		solrInputDocument.addField("id", "c005");
		solrInputDocument.addField("item_title", "太古神王4");
		//将文档对象添加到solr云连接
		this.cloudSolrServer.add(solrInputDocument);
		//提交
		this.cloudSolrServer.commit();
	}
	
	@Test
	public void testSearchIndex() throws Exception{
		//  创建搜索对象
		SolrQuery solrQuery = new SolrQuery();
		//创建查询条件
		solrQuery.setQuery("*:*");
		//设置分页
		solrQuery.setStart(0);
		solrQuery.setRows(10);
		//设置高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<font color='red'>");
		solrQuery.setHighlightSimplePost("</font>");
		//查询数据
		QueryResponse query = this.cloudSolrServer.query(solrQuery);
		//返回结果
		SolrDocumentList results = query.getResults();
		System.err.println("搜索到数据总条数："+results.getNumFound());
		
		//获取高亮数据
		Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
		for (SolrDocument solrDocument : results) {
			System.out.println("------------------------------");
			System.err.println(solrDocument.get("id"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			if(list!=null && list.size()>0){
				System.err.println(list.get(0));
			}else{
				System.err.println(solrDocument.get("item_title"));
			}
			
		}
		
	}
}
