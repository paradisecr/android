package com.example.sign;

import com.example.signServer.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
public class CourseSignActivity extends Activity{

	private String studentID = null;
	private String courseID = null;
	private String courseName = null;
	private String courseSignData[][] = null;
	private List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	private SimpleAdapter simpleAdapter = null;
	private ListView listview = null;
	private TextView courseNameTxt = null;
	
	//服务器信息
	private String serverFile="WebServerParams.ini";
	private String NAMESPACE="http://ws.apache.org/axis2";
	private String LINK="http://192.168.191.3:8080/axis2/services/SignServer?wsdl";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_sign);
		try {
			initParam(serverFile);//初始化服务器参数
			init();//初始化
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//courseSignData = getCourseSignData(studentID, courseID);
		try {
			list = getSignList(studentID, courseID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showCourseSign();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void showCourseSign(){
		simpleAdapter = new SimpleAdapter(CourseSignActivity.this,list,
					R.layout.course_sign_listview_model,new String[]{"weekTime","courseTime","isSigned"},
					new int[]{R.id.weekTimeInCourseListView,R.id.courseTimeInCourseListView,R.id.isSignedInCourseListView});
		listview.setAdapter(simpleAdapter);
	}
	
	public List<Map<String,String>> getSignList(String studentID,String courseID) throws Exception{
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		SoapObject soapObject = new SoapObject(NAMESPACE,"getStudentCourseSignData");
		soapObject.addProperty("studentID", studentID);
		soapObject.addProperty("courseID", courseID);
		SoapSerializationEnvelope envelope = 
				new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = soapObject;
		envelope.dotNet = false;
		envelope.setOutputSoapObject(soapObject);
		HttpTransportSE trans = new HttpTransportSE(LINK);
		trans.debug = true;
		try {
			trans.call("", envelope);
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
		
		String data[] = String.valueOf(envelope.getResponse()).split("@");
		for(int x=0; x<data.length; x++){
			System.out.println(data[x]);
		}
		String courseTime = null;
		String weekTime =null;
		String courseSignHistory = null;
		int currentWeek = 0;
		int currentTime = 0;
		courseTime = data[0];
		weekTime = data[1];
		courseSignHistory = data[2];
		currentWeek = Integer.valueOf(data[3]);
		currentTime = Integer.valueOf(data[4]);
		StudentSingleCourseSignRecords stu1 = new StudentSingleCourseSignRecords(
				courseTime, weekTime, courseSignHistory, currentWeek, currentTime);
		return stu1.getSignList();
	}
	
	//初始化
	public void init(){
		Intent tmp = super.getIntent();
		studentID = tmp.getStringExtra("studentID");
		courseID = tmp.getStringExtra("courseID");
		courseName = tmp.getStringExtra("courseName");
		listview = (ListView)super.findViewById(R.id.courseSignListView);
		courseNameTxt = (TextView)super.findViewById(R.id.courseNameInCourseSign);
		courseNameTxt.setText(courseName);
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
	}
}
