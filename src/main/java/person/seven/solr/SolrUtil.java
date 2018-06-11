package person.seven.solr;

import com.google.common.collect.Lists;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


/**
 * solr业务接口 增删查改实现类
 *
 */
public class SolrUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SolrUtil.class);
	
	private SolrClient client = null;
	private static final String SOLR_URL = "http://localhost:8983/solr/";
	private static final String COLLECTION = "core";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";

	public static void main(String[] args) throws Exception {
		add();
//		query();
	}


	private static void query() {
		SolrUtil solrService = new SolrUtil();
		Map<String,String> queryParamMap = new HashMap<>();
		queryParamMap.put("q","*:*");
		try {
			List<AddressBean> hitAddress = solrService.query(COLLECTION, queryParamMap , AddressBean.class);
			System.out.println("solr查詢出來地址："+hitAddress.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	private static void addBatch() {
		SolrUtil solrService = new SolrUtil();
        List<AddressBean>  addList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            AddressBean addressBean = new AddressBean();
            addressBean.setId("id"+i);
            addressBean.setAddress("地址"+i);
            addList.add(addressBean);
        }
		try {
			solrService.addBatch(COLLECTION, addList);
			System.out.println("添加成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error("错误",e);
		}
	}

	private static void add() {
		SolrUtil solrService = new SolrUtil();
		AddressBean addressBean = new AddressBean();
		addressBean.setId("ids");
		addressBean.setAddress("地址s");
		try {
			solrService.add(COLLECTION, addressBean);
			System.out.println("添加成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error("错误",e);
		}
	}


	public SolrUtil() {
		init();
	}
	
	private void init() {
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, 128);
        params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, 32);
		params.set(HttpClientUtil.PROP_BASIC_AUTH_USER, USERNAME);
		params.set(HttpClientUtil.PROP_BASIC_AUTH_PASS, PASSWORD);
		params.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS, false);
        params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, 1000);  
        params.set(HttpClientUtil.PROP_ALLOW_COMPRESSION, true);  
        params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, 1000);  
        CloseableHttpClient closeableHttpClient = HttpClientUtil.createClient(params); 
		client = new HttpSolrClient.Builder(SOLR_URL)
			    .withConnectionTimeout(10000).withSocketTimeout(60000).withHttpClient(closeableHttpClient).build();
	}

	public void add(String collection,Object bean) throws Exception {
		client.addBean(collection, bean);
		final UpdateResponse response = client.commit(collection);
		System.out.println("添加数据到solr结果:"+response);
	}

	public <V> List<V> query(String collection,Map<String, String> queryParamMap, Class<V> voCls) throws Exception{
		List<V> voList = new ArrayList<>();
		MapSolrParams queryParams = new MapSolrParams(queryParamMap);
		final QueryResponse response = client.query(collection, queryParams);
		final SolrDocumentList documents = response.getResults();
		for(SolrDocument document : documents) {
			V voObjt = voCls.newInstance();
			Collection<String> fieldNames = document.getFieldNames();
			for(String fieldName : fieldNames) {
				Object value = document.getFieldValue(fieldName);
				Field f = null ;
				try {
					f = voCls.getDeclaredField(fieldName); 
				}catch(Exception e) {
					logger.error(fieldName+" not exist",e);
					continue;
				}
				Class<?> fieldType = f.getType();   
				Method m = voCls.getMethod("set"+captureName(fieldName), fieldType);
				m.invoke(voObjt,value);
			}
			voList.add(voObjt);
		}
		return voList;
	}

	
	
	public void destroy() throws Exception {
		client.close();
	}
	
	/**
	 * 首字符变大写
	 * @param name
	 * @return
	 */
	 public static String captureName(String name) {
	       name = name.substring(0, 1).toUpperCase() + name.substring(1);
	       return  name;
	 }

	public void afterPropertiesSet() throws Exception {
		init();
	}

	public void addBatch(String collection, List<?> beanList) throws Exception {
		for(Object bean : beanList) {
			client.addBean(collection, bean);
		}
		final UpdateResponse response = client.commit(collection);
		System.out.println("批量添加数据到solr结果:"+response);
	} 
	
}
