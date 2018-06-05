package person.seven.solr;

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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


/**
 * solr业务接口 增删查改实现类
 *
 */
public class SolrUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SolrUtil.class);
	
	private SolrClient client = null;
	private static final String SOLR_URL = "http://localhost:8983/solr";
	private static final String COLLECTION = "core";

	public static void main(String[] args) throws Exception {
//		add();
		query();
	}


	private static void query() {
		SolrUtil solrService = new SolrUtil();
		solrService.init();
		Map<String,String> queryParamMap = new HashMap<>();
		queryParamMap.put("q","address:*增*");
		try {
			List<AddressBean> hitAddress = solrService.query(COLLECTION, queryParamMap , AddressBean.class);
			System.out.println(hitAddress.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	private static void add() {
		SolrUtil solrService = new SolrUtil();
		solrService.init();
		AddressBean addressBean = new AddressBean();
		addressBean.setId("这是id");
		addressBean.setAddress("这是新增地址");
		try {
			solrService.add(COLLECTION, addressBean);
			System.out.println("添加成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error("错误",e);
		}
	}


	public SolrUtil() {
	}
	
	private void init() {
        ModifiableSolrParams params = new ModifiableSolrParams();  
        params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, 128);  
        params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, 32);  
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
