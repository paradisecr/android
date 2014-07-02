package com.example.sign;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class HistorySignActivity extends Activity{

	private String StudentID = null;
	private int currentWeekNum = 0;
	private String weeklySignData[][] = null;
	private List<Map<String,String>> list =  new ArrayList<Map<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_sign);
		Intent tmp = super.getIntent();
		StudentID = tmp.getStringExtra("StudentID");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
