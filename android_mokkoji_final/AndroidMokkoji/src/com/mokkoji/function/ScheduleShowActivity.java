package com.mokkoji.function;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro3.R;
import com.mokkoji.connect.Gschedule;
import com.mokkoji.connect.Schedule;

public class ScheduleShowActivity extends Activity{
	
	
	 public static final int REQUEST_CODE_SCHEDULE_MODIFY= 1099;
	 
	 protected static final String TAG = ScheduleShowActivity.class.getSimpleName();
	 
	 Button modifybtn;
	 Button cancelbtn;
	 Button deletebtn;
	 
	 TextView titleStr, contentsStr, startDate, endDate,  importance;
	 Integer sn;
	 Integer gn;
	 Integer userPn;
	 String title, content, startdate, enddate;
	 
	
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 	requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.click_schedule);
	        
	        modifybtn = (Button)findViewById(R.id.modify);
	        cancelbtn = (Button)findViewById(R.id.cancel);
	        deletebtn =(Button)findViewById(R.id.delete);
	        titleStr = (TextView)findViewById(R.id.title);
	        contentsStr = (TextView)findViewById(R.id.content);
	        startDate = (TextView)findViewById(R.id.startdate);
	        endDate = (TextView)findViewById(R.id.enddate);
	        importance = (TextView)findViewById(R.id.importance);
	        
	
	      
	        Intent showschedule;
	        showschedule = getIntent();
	        sn = showschedule.getIntExtra("sn", 0);
	        gn = showschedule.getIntExtra("gn",0 );
	        userPn = showschedule.getIntExtra("userPn", 0);
	        
	        title = showschedule.getExtras().get("title").toString();
	        titleStr.setText(title);
	        content = showschedule.getExtras().get("content").toString();
	        contentsStr.setText(content);
	        startdate = showschedule.getExtras().get("startdate").toString();
	        startDate.setText(startdate);
	        enddate = showschedule.getExtras().get("enddate").toString();
	        endDate.setText(enddate);
	      
	        Integer star = showschedule.getIntExtra("importance", 0);
	        importance.setText(star.toString());
	        
	       //수정 버튼 
		    modifybtn.setOnClickListener(new OnClickListener(){
		    	   
		    	public void onClick(View v){
		    		
		    		   if(gn == 0){ //개인일정일때
		    		    	Intent intent = new Intent(getApplicationContext(), ScheduleModifyActivity.class);
		    		    	intent.putExtra("userPn", userPn);
		    		    	intent.putExtra("sn", sn);
		    		    	intent.putExtra("title",title.toString());
		    		    	intent.putExtra("content", content.toString());
		    		    	intent.putExtra("startdate", startdate.toString());
		    		    	intent.putExtra("enddate", enddate.toString());
		    		    	intent.putExtra("importance", importance.toString());
		    		    	
		    		    	startActivityForResult(intent,REQUEST_CODE_SCHEDULE_MODIFY);
		    		   }
		    		   else{  //그룹 일정일 떄
		    			   
		    			   Intent intent = new Intent(getApplicationContext(), ScheduleModifyActivity.class);
		    			   intent.putExtra("userPn", userPn);
		    			   intent.putExtra("gn", gn);
		    			   intent.putExtra("sn", sn);
		    			   intent.putExtra("title",title.toString());
		    		       intent.putExtra("content", content.toString());
		    		       intent.putExtra("startdate", startdate.toString());
		    		       intent.putExtra("enddate", enddate.toString());
		    		       intent.putExtra("importance", importance.toString());
		    		    	
		    			   startActivityForResult(intent,REQUEST_CODE_SCHEDULE_MODIFY);
		    			   
		    		   }
		    	   }
		    	   
		    });
		    //닫기 버튼
		    cancelbtn.setOnClickListener(new OnClickListener(){
  
		    	public void onClick(View v){
		    		
		    		
		    		   finish();
		    	   }
		    	   
		    });
		    //일정 삭제 
		    deletebtn.setOnClickListener(new OnClickListener(){
		    	  
		    	public void onClick(View v){
		    		
		    		  if(gn == 0){   //개인 일정일 때
		    			  new deleteScheduleTask().execute();	  
		    			  
		    		   }
		    		   else{ //그룹 일정일 때 
		    			   new deleteGscheduleTask().execute();
		    		   }
		    		 
		    		   finish();
		    	   }
		    	   
		    });
	 }
	 public void resetting(){
		 this.onResume();
	 }
		// ~ Post
		private void showResult(Schedule result) {
		
			if(result.getSn() != null){
			
				Toast.makeText(this,"해당 일정이 삭제되었습니다.", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(this,"일정이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
			}
				
		}

		// ***************************************
		// Private classes
		// ***************************************
		private class deleteScheduleTask extends AsyncTask<Void, Void, Schedule> {
		
			protected void onPreExecute() {
				
			}
			@Override
			protected Schedule doInBackground(Void... params) {
				try {
					// The URL for making the POST request
					final String url = getString(R.string.base_uri)
							+ "/mokkoji/deleteSchedule.json";

					HttpHeaders requestHeaders = new HttpHeaders();

					// Sending multipart/form-data
					requestHeaders.setContentType(MediaType.APPLICATION_JSON);

					// Populate the MultiValueMap being serialized and headers in an
					// HttpEntity object to use for the request
					HttpEntity<Integer> requestEntity = new HttpEntity<Integer>(
							sn, requestHeaders);

					// Create a new RestTemplate instance
					RestTemplate restTemplate = new RestTemplate(true);
					restTemplate.getMessageConverters().add(
							new StringHttpMessageConverter());
					restTemplate.getMessageConverters().add(
							new MappingJackson2HttpMessageConverter());

					// Make the network request, posting the message, expecting a
					// String in response from the server
					ResponseEntity<Schedule> response = restTemplate.exchange(
							url, HttpMethod.POST, requestEntity, Schedule.class);

					// Return the response body to display to the user
					return response.getBody();

				} catch (Exception e) {
					Log.e(TAG, e.getMessage(), e);
				}

				return null;
			}
			@Override
			protected void onPostExecute(Schedule result) {
				showResult(result);
			}
		}
		
		// ~ Post
		private void showResult(Gschedule result) {
		
			if(result.getSn() != null){
				if(result.getUser_pn() == null){
					Toast.makeText(this,"삭제할 권한이 없습니다.", Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(this,"해당 일정이 삭제되었습니다.", Toast.LENGTH_LONG).show();
				}
			}else{
				Toast.makeText(this,"일정이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
			}
				
		}

		// ***************************************
		// Private classes
		// ***************************************
		private class deleteGscheduleTask extends AsyncTask<Void, Void, Gschedule> {
		
			Gschedule temp;
			
			protected void onPreExecute() {
				temp = new Gschedule();
				temp.setGn(gn);
				temp.setSn(sn);
				temp.setUser_pn(userPn);
				
			}
			@Override
			protected Gschedule doInBackground(Void... params) {
				try {
					// The URL for making the POST request
					final String url = getString(R.string.base_uri)
							+ "/mokkoji/deleteGschedule.json";

					HttpHeaders requestHeaders = new HttpHeaders();

					// Sending multipart/form-data
					requestHeaders.setContentType(MediaType.APPLICATION_JSON);

					// Populate the MultiValueMap being serialized and headers in an
					// HttpEntity object to use for the request
					HttpEntity<Gschedule> requestEntity = new HttpEntity<Gschedule>(
							temp, requestHeaders);

					// Create a new RestTemplate instance
					RestTemplate restTemplate = new RestTemplate(true);
					restTemplate.getMessageConverters().add(
							new StringHttpMessageConverter());
					restTemplate.getMessageConverters().add(
							new MappingJackson2HttpMessageConverter());

					// Make the network request, posting the message, expecting a
					// String in response from the server
					ResponseEntity<Gschedule> response = restTemplate.exchange(
							url, HttpMethod.POST, requestEntity, Gschedule.class);

					// Return the response body to display to the user
					return response.getBody();

				} catch (Exception e) {
					Log.e(TAG, e.getMessage(), e);
				}

				return null;
			}
			@Override
			protected void onPostExecute(Gschedule result) {
				showResult(result);
			}

		}
	
}
