package com.example.HttpHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parameter {
	//List<Map<String,String>> parameters = null;    //�洢����
	private Map<String, String> parameters = null;           //�洢����
	public Parameter(){
		//parameters = new ArrayList<Map<String,String>>();
		parameters = new HashMap<String, String>();
	}
	
	/**��Ӳ���*/
	public void addParameter(String name,String value){
		parameters.put(name, value);
		
	}
	
	/**
	 *���ش洢�Ĳ������� 
	 */
	public Map getParameters(){
		return parameters; 
	}
}
