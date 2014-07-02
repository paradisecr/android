package com.example.xuanke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ChooseActivity extends Activity{

	
	private Button butXuanKe = null;
	private Button butChaKan = null;
	private Button butTuiXuan = null;
	private Button butTuiChu = null;
	private TextView nameText = null;
	private String name = null;
	private String userid = null;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);
        this.butXuanKe=(Button)super.findViewById(R.id.butXuanKe);
        this.butChaKan=(Button)super.findViewById(R.id.butChaKan);
        this.butTuiXuan=(Button)super.findViewById(R.id.butTuiXuan);
        this.butTuiChu=(Button)super.findViewById(R.id.butTuiChu);
        this.nameText = (TextView)super.findViewById(R.id.nametext);
        butXuanKe.setOnClickListener(new Show());
        butChaKan.setOnClickListener(new Show());
        butTuiXuan.setOnClickListener(new Show());
        butTuiChu.setOnClickListener(new Show());
        
        Intent it = super.getIntent();
        name = it.getStringExtra("name");
        nameText.setText(name);
        userid = it.getStringExtra("ID");
	}
	public class Show implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = null;
			if (v == butXuanKe){
				it =new Intent(ChooseActivity.this,ChooseClassActivity.class);
			}
			else if (v == butChaKan){
				it =new Intent(ChooseActivity.this,DropClass.class);
				}
			else if (v == butTuiXuan){
				it =new Intent(ChooseActivity.this,DropClass.class);
				}
			else if (v == butTuiChu){
				//it =new Intent(ChooseActivity.this,MainActivity.class);
				ChooseActivity.this.finish();
			}
			if(it != null){
				it.putExtra("userid", userid);
				ChooseActivity.this.startActivity(it);
			}
		}
		
		
	}
        
}
