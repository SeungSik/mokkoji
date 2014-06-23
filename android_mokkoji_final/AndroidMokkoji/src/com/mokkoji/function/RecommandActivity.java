package com.mokkoji.function;

import java.util.ArrayList;

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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pro3.R;
import com.mokkoji.connect.Group;
import com.mokkoji.connect.Grouplist;
import com.mokkoji.connect.Recomschedule;
import com.mokkoji.connect.Schedule;

public class RecommandActivity extends Activity{
	
	private Spinner recommend_type;   //운동인지 독서인지
	private Spinner during_time;      // 1주일, 2주일,3주일
	private Spinner week_time;        //일주일에 몇번
	private Spinner timeofday;        //하루에 몇시간 
	
	private Button adaptbtn;
	
	Integer userPn;
	
	//받아와야할 것
	 String typename;
     int duringday;
     int weektimes;
     int daytime;
     
     //일정의 마지막 날짜 계산하기 
 	int end_month;
	int end_day;
	
	String startDate;
	String endDate;

     
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommand_schedule);
        
        Intent intent;
		 
		intent = getIntent();
		userPn=intent.getIntExtra("userPn", 0);
        
        recommend_type = (Spinner) findViewById(R.id.recommend_type);
        
        during_time = (Spinner) findViewById(R.id.during_time);
        week_time = (Spinner) findViewById(R.id.week_time);
        timeofday = (Spinner) findViewById(R.id.timeofday);
        
        adaptbtn = (Button)findViewById(R.id.adaptbtn);
        
       
        adaptbtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               
            	
            	typename= recommend_type.getSelectedItem().toString();  //0->운동, 1->독서
            	duringday = during_time.getSelectedItemPosition();  //1주일, 2주일,3주일  
            	weektimes = week_time.getSelectedItemPosition();    //2일,3일,4일,5일
            	daytime = timeofday.getSelectedItemPosition();  //1시간, 2시간,3시간
            	
            	
            	//현재 날짜 받아와야함
            	java.util.Calendar cal = java.util.Calendar.getInstance();

            	//현재 년도, 월, 일
            	int year = cal.get ( cal.YEAR );
            	int month = cal.get ( cal.MONTH ) + 1 ;
            	int date = cal.get ( cal.DATE ) ;
  
            	startDate=year+"-"+month+"-"+date;
            	
            	//마지막 날짜 계산  -> INT형으로 구하기 
            	int lastmonthday = getMonthLastDay(year,month); 
            	int enddate = date + 7*weektimes;
            	
            	//더한 값이 이 날의 마지막 날짜보다 클 때는 다음 달로 넘어가고 몇일인지 계산해줌!
            	if(enddate>= lastmonthday){
            		end_month = month +1;
            		end_day = enddate - lastmonthday -1;
            	}else{
            		end_month = month;
                	end_day = enddate;
            	}

            	endDate = year+"-"+end_month+"-"+end_day;

            	Log.i("test",typename +" "+ duringday+" " + weektimes+ " " + daytime);
            	Log.i("test2",month+"월"+date+"일");
            	Log.i("test2",end_month+"월"+end_day+"일" +lastmonthday);
            	
    
            	//post로 넘겨줌 
            	new PostRecomschedule().execute();
            }
        });
	}
	public int getMonthLastDay(int year, int month){
		
	        switch (month) {
	            case 0:
	            case 2:
	            case 4:
	            case 6:
	            case 7:
	            case 9:
	            case 11:
	                return (30);
	 
	            case 3:
	            case 5:
	            case 8:
	            case 10:
	                return (31);
	 
	            default:
	                if(((year%4==0)&&(year%100!=0)) || (year%400==0) ) {
	                    return (29); 
	                } else {
	                    return (28);
	                }
	        }
	    }
	
	private void showResult(Recomschedule result) {
		
		if(result != null){
			Toast.makeText(getApplicationContext(), "추천 일정이 적용되었습니다.", Toast.LENGTH_LONG).show();
			finish();
		}
		else{
			Toast.makeText(getApplicationContext(), "적용이 실패되었습니다.", Toast.LENGTH_LONG).show();
		}

	}
	// ***************************************
	 	// Private classes
	 	// ***************************************
	 	private class PostRecomschedule extends AsyncTask<Void, Void,Recomschedule > {

	 		private Recomschedule recom;

	 		@Override
	 		protected void onPreExecute() {

	 			recom = new Recomschedule(); 	
	 			recom.setUserpn(userPn);
	 			recom.setDaytime(daytime);
	 			recom.setTypname(typename);
	 			recom.setStartdate(startDate);
	 			recom.setEnddate(endDate);
	 			recom.setWeektimes(weektimes);

	 		}

	 		@Override
	 		protected Recomschedule doInBackground(Void... params) {
	 			try {
	 				// The URL for making the POST request
	 				final String url = getString(R.string.base_uri)+"/mokkoji/PostRecomschedule.json";

	 				HttpHeaders requestHeaders = new HttpHeaders();

	 				// Sending multipart/form-data
	 				requestHeaders.setContentType(MediaType.APPLICATION_JSON);

	 				// Populate the MultiValueMap being serialized and headers in an
	 				// HttpEntity object to use for the request
	 				HttpEntity<Recomschedule> requestEntity = new HttpEntity<Recomschedule>(recom, requestHeaders);

	 				// Create a new RestTemplate instance
	 				RestTemplate restTemplate = new RestTemplate(true);
	 				restTemplate.getMessageConverters().add(
	 						new StringHttpMessageConverter());
	 				restTemplate.getMessageConverters().add(
	 						new MappingJackson2HttpMessageConverter());

	 				// Make the network request, posting the message, expecting a
	 				// String in response from the server
	 				ResponseEntity<Recomschedule> response = restTemplate.exchange(url,
	 						HttpMethod.POST, requestEntity, Recomschedule.class);

	 				// Return the response body to display to the user
	 				return response.getBody();
	 			} catch (Exception e) {
	 				//Log.e(TAG, e.getMessage(), e);
	 			}

	 			return null;
	 		}

	 		@Override
	 		protected void onPostExecute(Recomschedule result) {
	 			showResult(result);
	 		}
	 		
	 	}

}
