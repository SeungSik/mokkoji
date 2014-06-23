package com.mokkoji.function;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.pro3.R;

public class RecomscheduleActivity extends Activity{

	Integer userPn ;
	
	private Button newrecom;
	private Button recommdify;
	private ListView recomlist;
	
	public static final int REQUEST_CODE_RECOMMAND_INPUT = 1778;
	public static final int REQUEST_CODE_RECOMMAND_MANAGEMENT = 1989;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommand_admin);
        
        Intent intent;
		 
		intent = getIntent();
		userPn=intent.getIntExtra("userPn", 0);

		newrecom = (Button)findViewById(R.id.newrecom);
        
       
		newrecom.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	  
            	  Intent intent_t = new Intent(getApplication(),RecommandActivity.class);
                  intent_t.putExtra("userPn", userPn);
                  startActivityForResult(intent_t,REQUEST_CODE_RECOMMAND_INPUT );
            	
            	
            }
		});
		
		recommdify = (Button)findViewById(R.id.adminrecom);
        
	       
		recommdify.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            	Intent intent = new Intent(getApplication(),RecomManagementActivity.class);
                intent.putExtra("userPn", userPn);
                startActivityForResult(intent,REQUEST_CODE_RECOMMAND_MANAGEMENT );
            	
            }
		});
        
	}
}
