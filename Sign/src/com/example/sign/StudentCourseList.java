package com.example.sign;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class StudentCourseList extends Activity{
	private String studentID = null;
	private String studentName = null;
	private String courseID = null;
	private String courseListData[][] = null;
	
	private SimpleAdapter simpleAdapter = null;
	private ListView listview = null;
	private TextView info = null;
	private Map<String,String> map= new HashMap<String,String>();
	private List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	//服务器信息
	private String serverFile="WebServerParams.ini";
	private String NAMESPACE="http://ws.apache.org/axis2";
	private String LINK="http://192.168.191.3:8080/axis2/services/SignServer?wsdl";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list);
		try {
			initParam(serverFile);//初始化服务器参数
			init();//初始化
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;	
	}
	
	public List<Map<String,String>> getCourseList(String studentID) throws Exception{
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		SoapObject soapObject = new SoapObject(NAMESPACE,"getStudentCourseList");
		soapObject.addProperty("studentID", studentID);
		SoapSerializationEnvelope envelope = 
				new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = soapObject;
		envelope.dotNet = false;
		envelope.setOutputSoapObject(soapObject);
		HttpTransportSE trans = new HttpTransportSE(LINK);
		trans.debug = true;
		try {
			trans.call(null, envelope);
		} catch (HttpResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SoapObject result = (SoapObject)envelope.bodyIn;
		Map<String,String> map = new HashMap<String, String>();
		String rs = String.valueOf(envelope.getResponse());
		System.out.println(rs);
		String tmps[] = rs.split(";");
		int index = 0;
		String id,name;
		for(int x=0; x<tmps.length;x++){
			index = tmps[x].indexOf(",");
			id = tmps[x].substring(0, index);
			name = tmps[x].substring(index+1, tmps[x].length());
			map.put(id, name);
		}
		TreeMap treemap = new TreeMap(map);
		Iterator<String> iter = treemap.keySet().iterator();
		String key =null;
		while (iter.hasNext()) {
			key = iter.next();
			Map<String,String> tmp = new HashMap<String, String>();
			tmp.put("courseID",key);
			tmp.put("courseName",map.get(key));	
			list.add(tmp);
			
		}
		return list;
	}
	
	public void showCourseList(List<Map<String,String>> list){
		simpleAdapter = new SimpleAdapter(this,list,R.layout.course_list_listview,
				new String[]{"courseName"},
				new int[]{R.id.courseNameInCourseListView});
		listview.setAdapter(simpleAdapter);
	}
	
	//初始化
	public void init(){
		Intent tmp = super.getIntent();
		studentID = tmp.getStringExtra("studentID");
		studentName = tmp.getStringExtra("studentName");
		listview = (ListView)super.findViewById(R.id.listViewInCourseList);
		System.out.println("init1()");
		try {
			list = getCourseList(studentID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showCourseList(list);
		//课程列表监听事件
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Map<String,String> map = (Map<String,String>)StudentCourseList.this.
						simpleAdapter.getItem(position);
				courseID = map.get("courseID");
				Intent it = new Intent(StudentCourseList.this,CourseSignActivity.class);
				it.putExtra("studentID", studentID);
				it.putExtra("courseID", courseID);
				it.putExtra("studentName", studentName);
				it.putExtra("courseName", map.get("courseName"));
				StudentCourseList.this.startActivity(it);
			}
		});
	}
	//初初始化服务器参数
		public void initParam(String paramFile) throws Exception{
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(paramFile));
				NAMESPACE = props.getProperty("NAMESPACE");
				LINK = props.getProperty("LINK");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("initparams1()");
		}
}
