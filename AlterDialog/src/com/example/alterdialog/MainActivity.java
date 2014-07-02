package com.example.alterdialog;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

public class MainActivity extends Activity {

	private Button mybut = null;
	private ImageButton but = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mybut = (Button)super.findViewById(R.id.mubut);
	//	mybut.setOnClickListener(new OnClickListenerImpl());
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//exitDialog();
			}
		});
		
	}
/*
	private class OnClickListenerImpl implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Dialog dialog = new AlertDialog.Builder(MainActivity.this)
			.setTitle("ȷ��ɾ����")
			.setMessage("��ȷ��Ҫɾ��������Ϣ��")
			.setIcon(R.drawable.pic_m)
			.setPositiveButton("ɾ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			.setNeutralButton("�鿴����",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			} )
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			.create();
			dialog.show();
		}
		
	}
	private void exitDialog(){
		Dialog dialog = new AlertDialog.Builder(MainActivity.this)
						.setIcon(R.drawable.pic_m)
						.setTitle("�����˳���")
						.setMessage("��ȷ��Ҫ�˳���������")
						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								MainActivity.this.finish();
							}
						})
						.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						})
						.create();
		dialog.show();
	}
	
	/*public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK){
			this.exitDialog();
		}
		return false;
	}*/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
