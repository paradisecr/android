package com.example.sign;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import android.view.View.OnClickListener;
/*
 * *****学生签到主界面************
 * ***学生姓名***学号*************
 * ***点击签到******查看签到记录***
 * ***当前可签到课程：************
 */
public class StuSignMainActivity extends Activity{
	private String studentID = null;
	private String studentName = null;
	private Button signBut = null;
	private Button historyBut = null;
	private TextView signInfo = null;
	private TextView stuIDTxt = null;
	private TextView stuNameTxt = null;
	private boolean isSignTime = false; //是否为签到时间
	private String courseID = null;     //可签到课程ID
	private String courseName = null;   //可签到课程name
	//服务器信息
	private String serverFile="WebServerParams.ini";
	private String NAMESPACE="http://ws.apache.org/axis2";
	private String LINK="http://192.168.191.3:8080/axis2/services/SignServer?wsdl";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stu_sign_main);
		
		try {
			//initParam(serverFile);//初始化服务器参数
			init();//初始化
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//设置签到按钮监听事件
		signBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent it = new Intent(StuSignMainActivity.this,);
				if(isSignTime){
					try {
						if(getStudentSign(studentID,courseID)){
							signBut.setEnabled(false);
							signBut.setText("已签到");
							signInfo.append("(已签到)");
							Dialog dialog = new AlertDialog.Builder(StuSignMainActivity.this)
							.setTitle("签到提示")
							.setMessage("签到成功！")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									
								}
							}).create();
							dialog.show();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
					Dialog dialog = new AlertDialog.Builder(StuSignMainActivity.this)
					.setTitle("签到提示")
					.setMessage("签到失败！请尝试重新签到！")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					}).create();
					dialog.show();
				
			}
		});
		//设置查询签到记录按钮监听事件
		historyBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(StuSignMainActivity.this,StudentCourseList.class);
				it.putExtra("studentID", studentID);
				it.putExtra("studentName", studentName);
				StuSignMainActivity.this.startActivity(it);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//初始化数据
	public void init() throws Exception{
		//初始化组件,获得各控件
		Intent tmp = super.getIntent();
		studentID = tmp.getStringExtra("studentID");
		studentName = tmp.getStringExtra("studentName");
		signInfo = (TextView)super.findViewById(R.id.signInfo);
		stuIDTxt = (TextView)super.findViewById(R.id.stuIDInStudentSignMain);
		stuNameTxt = (TextView)super.findViewById(R.id.stuNameInStuSignMain);
		signBut = (Button)super.findViewById(R.id.signBut);//点击签到
		historyBut = (Button)super.findViewById(R.id.historyBut);
		stuIDTxt.setText(studentID);
		stuNameTxt.setText(studentName);
		//调用服务getIsSignTime返回可签到信息
		String str = null;
		//建立SoapObject对象，赋予参数：命名空间+调用的函数名
		SoapObject soapObject = new SoapObject(NAMESPACE,"getIsSignTime");
		//给SoapObject添加：参数+值
		soapObject.addProperty("studentID", studentID);
		//设置版本号
		SoapSerializationEnvelope envelope = 
				new SoapSerializationEnvelope(SoapEnvelope.VER11);
		//设置soapObject为输出
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
		str = String.valueOf(envelope.getResponse());
		String data[] =str.split(";");
		isSignTime = Boolean.parseBoolean(data[0]);
		courseID = data[1];     //可签到课程ID
		courseName = data[2];   //可签到课程name
		if(!isSignTime){
			signBut.setEnabled(false);
			//signBut.setText("已签到");
		}
		signInfo.setText(courseName);
	}
	
	//签到
	public boolean getStudentSign(String studentID,String courseID) throws Exception{
		//调用服务getStudentSign返回是否成功签到
		String str = null;
		SoapObject soapObject = new SoapObject(NAMESPACE,"getIsSignTime");
		soapObject.addProperty("studentID", studentID);
		SoapSerializationEnvelope envelope = 
				new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = soapObject;
		envelope.dotNet = false;
		envelope.setOutputSoapObject(soapObject);
		//设置wsdl描述
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
		//建立SoapObject用于接收返回数据
		SoapObject result = (SoapObject)envelope.bodyIn;
		str = String.valueOf(envelope.getResponse());
		if(Boolean.getBoolean(str)){
			return true;
		}
		return false;
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
