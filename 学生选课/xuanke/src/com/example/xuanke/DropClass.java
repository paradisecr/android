package com.example.xuanke;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.CourseInfo.Course;
import com.example.HttpHelper.HttpHelper;
import com.example.HttpHelper.Parameter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 退选课程
 * @author SUN
 *
 */
public class DropClass extends Activity {

	
	private Button but_TuiXuan = null;
	private Button but_FanHui = null;
	private String userid = null;
	private Course[] courseList= null;
	List<Map<String, String>> list = null;

	private ListView listView = null;
	private static final String PAGE = "MyClasses.jsp";
	private static final String PAGE2 = "DropClass.jsp";
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four);
        
        this.but_TuiXuan=(Button)super.findViewById(R.id.but_TuiXuan4);
        this.but_FanHui=(Button)super.findViewById(R.id.but_FanHui4);
	    this.listView = (ListView)super.findViewById(R.id.threelist4);
        Intent it = super.getIntent();
	    userid = it.getStringExtra("userid");
        but_TuiXuan.setOnClickListener(new TuiXuanKe());
	    but_FanHui.setOnClickListener(new FanHui());
	    listView.setOnItemClickListener(new OnItemClickListenerImpl());
	    DropThread thread = new DropThread();
	    thread.execute("1");
	}
	public class FanHui implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			DropClass.this.finish();
		}

	}
	public class TuiXuanKe implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//将 选择的课程退选
			DropThread thread = new DropThread();
		    thread.execute("2");
		}
    }
	private class DropThread extends AsyncTask<String, String, String>{

	   	 
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(params[0].equals("2")){
				Parameter parameters2 = new Parameter();
			    parameters2.addParameter("userid",userid);
			    String request2 = "";
			    for(int j = 0; j<courseList.length; j++){
			    	if(courseList[j].isChanged()){
			    		if(request2.equals("")){
			    			request2 = courseList[j].getID();
			    		}else{
			    			request2 += ";";
				    		request2 += courseList[j].getID();
			    		}
			    		
			    	}
			    }
			    parameters2.addParameter("class",request2);
			    HttpHelper.sendRequest(PAGE2, parameters2);
			}
			    
		    	Parameter parameters = new Parameter();
			    parameters.addParameter("userid",userid);
			    String result = HttpHelper.sendRequest(PAGE, parameters);
			    //System.out.println(userid);
			    //String result = "001@高数";
			    result = result.trim();
			    if(result.equals("@")){
			    	return null;
			    }
			    String result2[] = result.split("@");
			    String idList[] = result2[0].split(";");
			    String nameList[] = result2[1].split(";");
			    courseList = new Course[idList.length];
			    for(int i = 0; i<idList.length; i++){
			    	courseList[i] = new Course(idList[i], nameList[i],true);
			    	System.out.println(nameList[i]);
			    }
		    
			this.publishProgress();
			return null;
		}
		
		@Override
		protected void onProgressUpdate(String...result ){
			list = new ArrayList<Map<String,String>>();
			for(int i = 0; i<courseList.length; i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", courseList[i].getName());
				if(courseList[i].isChanged()){

					map.put("image", String.valueOf(R.drawable.yes));
				}else{
					map.put("image", String.valueOf(R.drawable.no));
				}
				list.add(map);
		    }
			SimpleAdapter simpleAdapter = new SimpleAdapter(DropClass.this, DropClass.this.list,
					R.layout.four_list,
					new String[]{"name","image"}, 
					new int[]{R.id.Class_Name4,R.id.icon4});
			DropClass.this.listView.setAdapter(simpleAdapter);
		}
	}
	private class OnItemClickListenerImpl implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			courseList[position].check();
			ImageView image = (ImageView)view.findViewById(R.id.icon4);
			if(courseList[position].isChanged()){
				image.setImageResource(R.drawable.yes);
			}else{
				image.setImageResource(R.drawable.no);
			}
		}
		
	}
}
