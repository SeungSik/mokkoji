package com.mokkoji.function;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;



import com.example.pro3.R;
import com.mokkoji.connect.Schedule;
import com.mokkoji.connect.ScheduleList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ListView;

public class ScheduleTodayActivity extends Activity {

    Button closeButton;
   
    TextView DayView;
    TextView todayList;
    
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy년MM월dd일", Locale.KOREA );
    Date currentTime = new Date ( );
    String mTime = mSimpleDateFormat.format ( currentTime );
    
    
    private ScheduleListAdapter SA;//약자
	private ListView mListview = null;
	
	Integer userPn;
	
	
	Intent intent;
	private Integer sn;
	private String userId;
	private String title;
	private String content;
	private String startDate;
	private String endDate;
	private Integer importance;
	ArrayList outScheduleList;
    
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        intent=getIntent();
        setContentView(R.layout.schedule_today);
 
  
        
        DayView = (TextView) findViewById(R.id.DayView);
        DayView.setText(mTime);
        
        mListview = (ListView) findViewById(R.id.todayscheduleList);
        
        new PostListofSchedule().execute();
        
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {

			}
		});//포스트랑 순서 상관없는지 테스트

        Button closeButton = (Button) findViewById(R.id.cancel);
        closeButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent resultIntent = new Intent();
        		setResult(RESULT_OK, resultIntent);
        		finish();
        	}
        	
        });
        
        
        //todayList = (TextView)findViewById(R.id.todayList);
        
 
 
     
    }
    
    
 // ~ Post
 	 	private void showResult(ScheduleList result) { 
 	 		
 	 	 		
 	 		int schedulelist_size = result.getSchedulelist().size();
 	 		
 	 	
 	 		 		
 	 		for(int t=0; t<schedulelist_size; t++){
 	 			
 	 			sn=result.getSchedulelist().get(t).getSn();
 	 			userPn=result.getSchedulelist().get(t).getUserPn();
 	 			userId=result.getSchedulelist().get(t).getuserId();
 	 			title=result.getSchedulelist().get(t).getTitle();
 	 			content=result.getSchedulelist().get(t).getContent();
 	 			startDate=result.getSchedulelist().get(t).getStartDate();
 	 			endDate=result.getSchedulelist().get(t).getEndDate();
 	 			importance=result.getSchedulelist().get(t).getImportance();
 	 			
 	 			
 	 			//schedulelist를 받아오고 거기 인덱스별로
 	 		
 	 		

 	            
 	            StringTokenizer st=new StringTokenizer(startDate,"-");//가져온것을 -로 자른다
 	            
 	            int year; //이거 세개는 포스트로 가져온거
 	            int month;
 	            int day;
 	            
 	            int firstDay=intent.getIntExtra("firstDay", 0);//퍼스트데이얻어옴
 	            int curDay=intent.getIntExtra("curDay", 0)-1;
 	            int curMonth=intent.getIntExtra("curMonth", 0);
 		        int curYear=intent.getIntExtra("curYear", 0);
 	            
 	            year=Integer.parseInt(st.nextToken());   //각각 잘라서 넣어줌.
 	            month=Integer.parseInt(st.nextToken())-1;//04가 4 가 되는지가 궁금..... 및 5를 4로 바꾸어준다.
 	            //Log.i("test",month.toString());//입력을 05-21로 넣을거니까 로그에 4가 찍히면 성공
 	            day=Integer.parseInt(st.nextToken())-1;
 	            
 	           // Integer tempcurmonth=monthViewAdapter.getCurMonth();
 	           	            
 	           
 	           
 	           
 	           
 	            if ( curDay == day) {
 	               
 	               
 	 
 	                Schedule aItem = new Schedule(sn,userPn,userId,title,content,startDate,endDate,importance);
 	 
 	                
 	             
 	              //재정의해서넣기
 	 
 	                if (outScheduleList == null) {
 	                    outScheduleList = new ArrayList();
 	                }
 	                outScheduleList.add(aItem);
 	                
 	               
 	 
 	               
 	            }
 	            
 	 	        
 	 	        
 	 	        }//포문 끝 이게 좀 느리면 위에를좀 손본다 하지만 일단 그냥 써보기
 	 		
 	 		if (outScheduleList == null) { //일정 아무것도없을때 오류 안나게해주는 코드
                 outScheduleList = new ArrayList();
             }
 	 		
 	 		SA = new ScheduleListAdapter(this);
 	 		mListview.setAdapter(SA);
 	 		SA.scheduleList = outScheduleList; //리스트에 추가, 삭제와 같은 것들을 하기위함.
             SA.notifyDataSetChanged(); //스케줄어댑터새로고침
 	 		
 	 		
 	 		

 	 		
 	 	}//////여기까지 showResult

 	 	// ***************************************
 	 	// Private classes  아직 사용안함.
 	 	// ***************************************
 	 	private class PostListofSchedule extends AsyncTask<Integer, Void, ScheduleList> {  //중간건 모르겠고 첫번째것이 보내는거 , 뒤에것이 받아오는거

 	 		protected void onPreExecute() {

 	 		//어레이리스트 에다가 userPn으로 가져온거 다 담을려고		
 	 			

 	 			userPn = intent.getIntExtra("userPn", 0);
 	 			
 	 		}

 	 		@Override
 	 		protected ScheduleList doInBackground(Integer... params) {
 	 			try {
 	 				// The URL for making the POST request
 	 				final String url = getString(R.string.base_uri)
 	 						+ "/mokkoji/PostListofSchedule.json";

 	 				HttpHeaders requestHeaders = new HttpHeaders();

 	 				// Sending multipart/form-data
 	 				requestHeaders.setContentType(MediaType.APPLICATION_JSON);

 	 				// Populate the MultiValueMap being serialized and headers in an
 	 				// HttpEntity object to use for the request
 	 				HttpEntity<Integer> requestEntity = new HttpEntity<Integer>(
 	 						userPn, requestHeaders);

 	 				// Create a new RestTemplate instance
 	 				RestTemplate restTemplate = new RestTemplate(true);
 	 				restTemplate.getMessageConverters().add(
 	 						new StringHttpMessageConverter());
 	 				restTemplate.getMessageConverters().add(
 	 						new MappingJackson2HttpMessageConverter());

 	 				// Make the network request, posting the message, expecting a
 	 				// String in response from the server
 	 				ResponseEntity<ScheduleList> response = restTemplate.exchange(
 	 						url, HttpMethod.POST, requestEntity, ScheduleList.class);

 	 				// Return the response body to display to the user
 	 				return response.getBody();

 	 			} catch (Exception e) {
 	 				//Log.e(TAG, e.getMessage(), e);
 	 			}

 	 			return null;
 	 		}

 	 		@Override
 	 		protected void onPostExecute(ScheduleList result) {
 	 			showResult(result);
 	 		}

 	 	}
 	   
   
    

}