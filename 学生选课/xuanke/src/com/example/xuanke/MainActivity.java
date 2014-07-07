package com.example.xuanke;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.HttpHelper.*;
@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") public class MainActivity extends Activity {
    private Button button1 = null;
    private Button button2 = null;
    private EditText  idEdit =null;
    private EditText  pwEdit =null;
    private String ID;
    private String pw;
    private String name;
    private Parameter parameters = null;
    /**
     * 
     */
    private static final String PAGE = "Login.jsp";
    @TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        if (Build.VERSION.SDK_INT >= 11) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads     ().detectDiskWrites().detectNetwork().penaltyLog().build());
//         StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
//        }
        
        this.button1=(Button)super.findViewById(R.id.button1);
        this.button2=(Button)super.findViewById(R.id.button2);
        this.idEdit=(EditText)super.findViewById(R.id.edit1);
        this.pwEdit=(EditText)super.findViewById(R.id.edit2);
        //this.idEdit.setText("0121110340419");
        //this.pwEdit.setText("123456");
        button1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) { 
				// TODO Auto-generated method stub
				ID = idEdit.getText().toString();
				pw = pwEdit.getText().toString();
				//登陆
				LoginThread login = new LoginThread();
				login.execute();
			}
        	
        });
        
        button2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) { 
				// TODO Auto-generated method stub
				//System.exit(0);
				MainActivity.this.finish();
			}});
        
    }
    
    /**判断登陆是否成功
     * 参数：账号，密码
     * 返回值：密码正确true,密码错误false
     * 
     */
    public String CheckPassword(String userid,String pass){
    	parameters = new Parameter();
    	parameters.addParameter("userid", userid);
    	parameters.addParameter("pass", pass);
    	
    	
    	String result = HttpHelper.sendRequest(PAGE, parameters);
    	
    	return result;
    }
    
    private class LoginThread extends AsyncTask<String, String, String>{

    	 
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result[] = CheckPassword(ID,pw).trim().split(";");
			name = result[1];
				//成功则跳转到TWO
			if(result[0].equals("1")){
				Intent it =new Intent(MainActivity.this,ChooseActivity.class);
				it.putExtra("name", name);
				it.putExtra("userid", ID);
				MainActivity.this.startActivity(it);
				MainActivity.this.finish();
			}
			this.publishProgress(result[0]);
			return null;
		}
		
		@Override
		protected void onProgressUpdate(String...result ){
			if(result[0].equals("1")){
				MainActivity.this.finish();
			}else{
				Toast.makeText(MainActivity.this, "账号密码不匹配！"+result[0], Toast.LENGTH_LONG).show();
				MainActivity.this.pwEdit.setText("");
			}
		}
    	
    }
}

