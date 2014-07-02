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
	private List<Map<String,String>> list =  new ArrayList<Map<String,String>>();//缺课数据包装
	private String restData[][] = null;         //缺课列表数据
	private TextView restNumInRestSign = null;  //缺课次数显示
	private int restNum = 0;                     //缺课次数
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rest_sign);
		Intent tmp = super.getIntent();
		studentID = tmp.getStringExtra("studentID");
		listview = (ListView)super.findViewById(R.id.listViewInRestSign);
		restNumInRestSign = (TextView)super.findViewById(R.id.restNumInRestSign);
		getRestData();       //从服务器获得缺课数据
		showRestSignList();  //显示缺课列表
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void getRestData(){       //获取缺课数据
		this.restData = new String[][]{{"高数","周一第三、四节","是"},
				{"计算机导论到轮到轮到轮到了","周二第三、四节","否"}};
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
