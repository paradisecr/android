package com.example.sign;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

/*
 * �����棺
 * ********ѧ��ǩ��������    **********
 * ********��ʦǩ���������********
 */
public class MainActivity extends Activity {

	private String studentID = "10932"; //ѧ��ID��������ʱ��ֵ
	private String studentName = "���";
	private Button studentBut = null;
	private Button teacherBut = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//���������ť�ؼ�
		studentBut = (Button)super.findViewById(R.id.studentBut);
		teacherBut = (Button)super.findViewById(R.id.teacherBut);
		//���á�ѧ��ǩ�����水ť���ļ����¼�-->��ת��ѧ��ѧǩ������
		studentBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ʹ��Intent��ת�����ݲ�����ѧ��ID��ѧ������
				Intent it = new Intent(MainActivity.this,StuSignMainActivity.class);
				it.putExtra("studentID", studentID);
				it.putExtra("studentName", studentName);
				MainActivity.this.startActivity(it);
			}
		});
		//����ʦǩ��������水ť������
		teacherBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ʹ��Intent��ת
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
