package com.mokkoji.function;

import com.example.pro3.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GroupAdminActivity extends Activity{

	public static final int REQUEST_CODE_GROUP_CREATE = 111;
	public static final int REQUEST_CODE_GROUP_MODIFY = 112;
	
	Integer userPn;
	
	Intent intent;
	
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	      
	        setContentView(R.layout.group_admin);
	       
	       intent  =getIntent();
	       
	       userPn = intent.getIntExtra("userPn", 6);
	       
	       Button button1 = (Button)findViewById(R.id.Creategroup);
	       Button button2 = (Button)findViewById(R.id.Modifygroup);
	       
	       button1.setOnClickListener(new OnClickListener(){
	    	   
	    	   public void onClick(View v){
	    		   
	    		   Intent intent = new Intent(getApplicationContext(), GroupCreateActivity.class);
	    		   intent.putExtra("userPn", userPn);
	    		   startActivityForResult(intent,REQUEST_CODE_GROUP_CREATE);
	    	
	    	   }
	    	   
	       });
	       
	       
	       button2.setOnClickListener(new OnClickListener(){
	    	   
	    	   public void onClick(View v){
	    		 
	    		   Intent intent = new Intent(getApplicationContext(), GroupModifyActivity.class);
	    		   intent.putExtra("userPn", userPn);
	    		   startActivityForResult(intent,REQUEST_CODE_GROUP_MODIFY);
	    	   }
	    	   
	       });

	 }
}
