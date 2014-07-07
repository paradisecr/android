package com.example.HttpHelper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpHelper {
	public static final String ROOTURL = "http://192.168.1.105:8080/XuankeServer/";
	
	public static String sendRequest(String pageName,Parameter parameters){
		String url = ROOTURL + pageName;
		String result = "";
		HttpPost request = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Map<String,String> map = (Map<String, String>)parameters.getParameters(); 
		for (String key : map.keySet()) {
			params.add(new BasicNameValuePair(key, map.get(key)));
			System.out.println(key+":"+map.get(key));
			  }
		try {
			//params.add(new BasicNameValuePair("userid", "lalalala"));
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			
			HttpResponse response = new DefaultHttpClient().execute(request);
			//if(response.getStatusLine().getStatusCode() != 404){
				result = EntityUtils.toString(response.getEntity()).trim();  //获得结果
				
			//}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("result:"+result);
		return result;
	}

}
