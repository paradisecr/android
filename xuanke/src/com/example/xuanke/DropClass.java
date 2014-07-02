package com.example.xuanke;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DropClass extends Activity {

	
	private Button but_TuiXuan = null;
	private Button but_FanHui = null;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four);
        
        this.but_TuiXuan=(Button)super.findViewById(R.id.but_TuiXuan);
        this.but_FanHui=(Button)super.findViewById(R.id.but_FanHui2);
	    
        but_TuiXuan.setOnClickListener(new TuiXuanKe());
	    but_FanHui.setOnClickListener(new FanHui());
	}
	public class FanHui implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it =new Intent(DropClass.this,ChooseActivity.class);
			DropClass.this.startActivity(it);
			DropClass.this.finish();
		}

	}
	public class TuiXuanKe implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//将 选择的课程退选
		
		}
    }
}
