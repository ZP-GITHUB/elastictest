package com.zp.elastictest;

import com.zp.elastictest.bean.article;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElastictestApplicationTests {

	@Autowired
	JestClient jestClient;

	@Test
	public void contextLoads() {
		//给ES中索引一个文档
		article article = new article();
		article.setId(1);
		article.setAuthor("张三");
		article.setTitle("哈希表");
		article.setContent("HashMap");

		//构建一个索引功能
		Index build = new Index.Builder(article).index("zptest").type("news").build();
		try {
			jestClient.execute(build);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void search(){
		//查询表达式
		String json = "{\n" +
				"    \"query\" : {\n" +
				"        \"match\" : {\n" +
				"            \"content\" : \"HashMap\"\n" +
				"        }\n" +
				"    }\n" +
				"}";
		//构建搜索功能
		Search search = new Search.Builder(json).addIndex("zptest").addType("news").build();

		try {
			SearchResult searchResult = jestClient.execute(search);
			System.out.println(searchResult.getJsonString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
