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
 * *****ѧ��ǩ��������************
 * ***ѧ������***ѧ��*************
 * ***���ǩ��******�鿴ǩ����¼***
 * ***��ǰ��ǩ���γ̣�************
 */
public class StuSignMainActivity extends Activity{
	private String studentID = null;
	private String studentName = null;
	private Button signBut = null;
	private Button historyBut = null;
	private TextView signInfo = null;
	private TextView stuIDTxt = null;
	private TextView stuNameTxt = null;
	private boolean isSignTime = false; //�Ƿ�Ϊǩ��ʱ��
	private String courseID = null;     //��ǩ���γ�ID
	private String courseName = null;   //��ǩ���γ�name
	//��������Ϣ
	private String serverFile="WebServerParams.ini";
	private String NAMESPACE="http://ws.apache.org/axis2";
	private String LINK="http://192.168.191.3:8080/axis2/services/SignServer?wsdl";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stu_sign_main);
		
		try {
			//initParam(serverFile);//��ʼ������������
			init();//��ʼ��
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//����ǩ����ť�����¼�
		signBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent it = new Intent(StuSignMainActivity.this,);
				if(isSignTime){
					try {
						if(getStudentSign(studentID,courseID)){
							signBut.setEnabled(false);
							signBut.setText("��ǩ��");
							signInfo.append("(��ǩ��)");
							Dialog dialog = new AlertDialog.Builder(StuSignMainActivity.this)
							.setTitle("ǩ����ʾ")
							.setMessage("ǩ���ɹ���")
							.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
								
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
					.setTitle("ǩ����ʾ")
					.setMessage("ǩ��ʧ�ܣ��볢������ǩ����")
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					}).create();
					dialog.show();
				
			}
		});
		//���ò�ѯǩ����¼��ť�����¼�
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
	
	//��ʼ������
	public void init() throws Exception{
		//��ʼ�����,��ø��ؼ�
		Intent tmp = super.getIntent();
		studentID = tmp.getStringExtra("studentID");
		studentName = tmp.getStringExtra("studentName");
		signInfo = (TextView)super.findViewById(R.id.signInfo);
		stuIDTxt = (TextView)super.findViewById(R.id.stuIDInStudentSignMain);
		stuNameTxt = (TextView)super.findViewById(R.id.stuNameInStuSignMain);
		signBut = (Button)super.findViewById(R.id.signBut);//���ǩ��
		historyBut = (Button)super.findViewById(R.id.historyBut);
		stuIDTxt.setText(studentID);
		stuNameTxt.setText(studentName);
		//���÷���getIsSignTime���ؿ�ǩ����Ϣ
		String str = null;
		//����SoapObject���󣬸�������������ռ�+���õĺ�����
		SoapObject soapObject = new SoapObject(NAMESPACE,"getIsSignTime");
		//��SoapObject��ӣ�����+ֵ
		soapObject.addProperty("studentID", studentID);
		//���ð汾��
		SoapSerializationEnvelope envelope = 
				new SoapSerializationEnvelope(SoapEnvelope.VER11);
		//����soapObjectΪ���
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
		courseID = data[1];     //��ǩ���γ�ID
		courseName = data[2];   //��ǩ���γ�name
		if(!isSignTime){
			signBut.setEnabled(false);
			//signBut.setText("��ǩ��");
		}
		signInfo.setText(courseName);
	}
	
	//ǩ��
	public boolean getStudentSign(String studentID,String courseID) throws Exception{
		//���÷���getStudentSign�����Ƿ�ɹ�ǩ��
		String str = null;
		SoapObject soapObject = new SoapObject(NAMESPACE,"getIsSignTime");
		soapObject.addProperty("studentID", studentID);
		SoapSerializationEnvelope envelope = 
				new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = soapObject;
		envelope.dotNet = false;
		envelope.setOutputSoapObject(soapObject);
		//����wsdl����
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
		//����SoapObject���ڽ��շ�������
		SoapObject result = (SoapObject)envelope.bodyIn;
		str = String.valueOf(envelope.getResponse());
		if(Boolean.getBoolean(str)){
			return true;
		}
		return false;
	}
	//����ʼ������������
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
