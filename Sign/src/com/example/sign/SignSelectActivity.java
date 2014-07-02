package com.example.sign;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.*;
public class SignSelectActivity extends Activity{

	private String studentID = null;
	private Button restSignBut = null;
	private Button courseSignBut = null;
	private Button historySignBut = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_select);
		Intent tmp = super.getIntent();
		studentID = tmp.getStringExtra("studentID");
		restSignBut = (Button)super.findViewById(R.id.restSignBut);
		courseSignBut = (Button)super.findViewById(R.id.courseSignBut);
		historySignBut = (Button)super.findViewById(R.id.historySignBut);
		restSignBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(SignSelectActivity.this,RestSignActivity.class);
				it.putExtra("studentID", studentID);
				SignSelectActivity.this.startActivity(it);
			}
		});
       courseSignBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(SignSelectActivity.this,CourseSignActivity.class);
				it.putExtra("studentID", studentID);
				SignSelectActivity.this.startActivity(it);
			}
		});
       historySignBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(SignSelectActivity.this,HistorySignActivity.class);
				it.putExtra("studentID", studentID);
				SignSelectActivity.this.startActivity(it);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
