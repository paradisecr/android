package com.example.sign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestSignActivity extends Activity{

	private String studentID = null;
	private ListView listview  = null;
	private List<Map<String,String>> list =  new ArrayList<Map<String,String>>();//ȱ�����ݰ�װ
	private String restData[][] = null;         //ȱ���б�����
	private TextView restNumInRestSign = null;  //ȱ�δ�����ʾ
	private int restNum = 0;                     //ȱ�δ���
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rest_sign);
		Intent tmp = super.getIntent();
		studentID = tmp.getStringExtra("studentID");
		listview = (ListView)super.findViewById(R.id.listViewInRestSign);
		restNumInRestSign = (TextView)super.findViewById(R.id.restNumInRestSign);
		getRestData();       //�ӷ��������ȱ������
		showRestSignList();  //��ʾȱ���б�
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void getRestData(){       //��ȡȱ������
		this.restData = new String[][]{{"����","��һ�������Ľ�","��"},
				{"��������۵��ֵ��ֵ��ֵ���","�ܶ��������Ľ�","��"}};
		this.restNum  = 2;
	}
	
	public void showRestSignList(){
		SimpleAdapter simpleAdaper = null;
		for(int x=0;x<restData.length;x++){
			Map<String,String> map = new HashMap<String, String>();
			map.put("courseName", restData[x][0]);
			map.put("weekNum", restData[x][1]);
			map.put("isSigned", restData[x][2]);
			this.list.add(map);
		}
		simpleAdaper = new SimpleAdapter(RestSignActivity.this,list,R.layout.rest_sign_list_show,
							new String[]{"courseName","weekNum","isSigned"},
							new int[]{R.id.course_name,R.id.week_num,R.id.is_signed});
		this.listview.setAdapter(simpleAdaper);
		restNumInRestSign.setText(String.valueOf(restNum));
	}
}
