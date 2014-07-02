package com.example.HttpHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parameter {
	//List<Map<String,String>> parameters = null;    //存储参数
	private Map<String, String> parameters = null;           //存储参数
	public Parameter(){
		//parameters = new ArrayList<Map<String,String>>();
		parameters = new HashMap<String, String>();
	}
	
	/**添加参数*/
	public void addParameter(String name,String value){
		parameters.put(name, value);
		
	}
	
	/**
	 *返回存储的参数集合 
	 */
	public Map getParameters(){
		return parameters; 
	}
}
