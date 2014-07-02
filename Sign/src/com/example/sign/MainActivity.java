package com.example.sign;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

/*
 * 主界面：
 * ********学生签到主界面    **********
 * ********教师签到管理界面********
 */
public class MainActivity extends Activity {

	private String studentID = "10932"; //学生ID，由启动时赋值
	private String studentName = "曹睿";
	private Button studentBut = null;
	private Button teacherBut = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//获得两个按钮控件
		studentBut = (Button)super.findViewById(R.id.studentBut);
		teacherBut = (Button)super.findViewById(R.id.teacherBut);
		//设置“学生签到界面按钮”的监听事件-->跳转至学生学签到界面
		studentBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//使用Intent跳转，传递参数：学生ID，学生姓名
				Intent it = new Intent(MainActivity.this,StuSignMainActivity.class);
				it.putExtra("studentID", studentID);
				it.putExtra("studentName", studentName);
				MainActivity.this.startActivity(it);
			}
		});
		//“教师签到管理界面按钮”功能
		teacherBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//使用Intent跳转
				//Intent it = new Intent(MainActivity.this,StuSignMainActivity.class);
				//it.putExtra("StudentID", StudentID);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void init(){
		
	}

}
