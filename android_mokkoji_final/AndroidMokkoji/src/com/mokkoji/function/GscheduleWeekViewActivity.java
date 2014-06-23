package com.mokkoji.function;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.mokkoji.connect.Gschedule;
import com.mokkoji.connect.GscheduleList;
import com.mokkoji.connect.Schedule;
import com.mokkoji.connect.ScheduleList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class GscheduleWeekViewActivity extends Activity{

	
	public static final int REQUEST_CODE_CALENDAR_SHOW= 1080;
	
	int curPosition;
	
	private TempGscheduleListAdapter sunAdapter;
	private TempGscheduleListAdapter monAdapter;
	private TempGscheduleListAdapter tueAdapter;
	private TempGscheduleListAdapter wedAdapter;
	private TempGscheduleListAdapter thuAdapter;
	private TempGscheduleListAdapter friAdapter;
	private TempGscheduleListAdapter satAdapter;
	//private ListView mListview = null;
	private TextView sun;
	private TextView mon;
	private TextView tue;
	private TextView wed;
	private TextView thu;
	private TextView fri;
	private TextView sat;
	
	
	private ListView sunListView;
	private ListView monListView;
	private ListView tueListView;
	private ListView wedListView;
	private ListView thuListView;
	private ListView friListView;
	private ListView satListView;
	
	
	Integer userPn;
	
	
	Intent intent;
	private Integer gn;
	private Integer sn;
	private String userId;
	private String title;
	private String content;
	private String startDate;
	private String endDate;
	private Integer importance;
	ArrayList outScheduleList;
	
	public GscheduleWeekViewActivity() {
		// TODO Auto-generated constructor stub
		
		
	}
	
	   public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        intent=getIntent();
	        
	        userPn=intent.getIntExtra("userPn",0 );
	        gn=intent.getIntExtra("gn", 0);
	        setContentView(R.layout.schedule_week);
	        
	        sun = (TextView) findViewById(R.id.weeksunday);
	        mon = (TextView) findViewById(R.id.weekmonday);
	        tue = (TextView) findViewById(R.id.weektuesday);
	        wed = (TextView) findViewById(R.id.weekwednesday);
	        thu = (TextView) findViewById(R.id.weekthursday);
	        fri = (TextView) findViewById(R.id.weekfriday);
	        sat = (TextView) findViewById(R.id.weeksaturday);
	        
	        
	       /* sun.setText("일");
	        mon.setText("월");
	        tue.setText("화");
	        wed.setText("수");
	        thu.setText("목");
	        fri.setText("금");
	        sat.setText("토");*/
	        
	        sunAdapter= new TempGscheduleListAdapter(this);
	        monAdapter= new TempGscheduleListAdapter(this);
	        tueAdapter= new TempGscheduleListAdapter(this);
	        wedAdapter= new TempGscheduleListAdapter(this);
	        thuAdapter= new TempGscheduleListAdapter(this);
	        friAdapter= new TempGscheduleListAdapter(this);
	        satAdapter= new TempGscheduleListAdapter(this);
	        
	        
	       // mListview = (ListView) findViewById(R.id.weekscheduleList);
	        
	        sunListView =(ListView) findViewById(R.id.sunView);
	        monListView =(ListView) findViewById(R.id.monView);
	        tueListView =(ListView) findViewById(R.id.tueView);
	        wedListView =(ListView) findViewById(R.id.wedView);
	        thuListView =(ListView) findViewById(R.id.thuView);
	        friListView =(ListView) findViewById(R.id.friView);
	        satListView =(ListView) findViewById(R.id.satView);
	        
	        
	        new PostListofGSchedule().execute();   
	        
	        
	        sunListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) sunAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                
	                sunAdapter.setSelectedPosition(position);
	                sunAdapter.notifyDataSetChanged();
	 
	                curPosition = position;
	                
	                curItem.getSn();
	                
	               Intent showschedule = new Intent(getApplicationContext(),ScheduleShowActivity.class);
	               showschedule.putExtra("sn", curItem.getSn());
	               showschedule.putExtra("userPn", userPn);
	               showschedule.putExtra("title",curItem.getTitle());
	               showschedule.putExtra("content", curItem.getContent());
	               showschedule.putExtra("startdate", curItem.getStartDate());
	               showschedule.putExtra("enddate", curItem.getEndDate());
	               showschedule.putExtra("importance", curItem.getImportance());
	               
	               startActivityForResult(showschedule,REQUEST_CODE_CALENDAR_SHOW);

				}
			});
	        monListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) monAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                
	                monAdapter.setSelectedPosition(position);
	                monAdapter.notifyDataSetChanged();
	 
	                curPosition = position;
	                
	                curItem.getSn();
	                
	               Intent showschedule = new Intent(getApplicationContext(),ScheduleShowActivity.class);
	               showschedule.putExtra("sn", curItem.getSn());
	               showschedule.putExtra("userPn", userPn);
	               showschedule.putExtra("title",curItem.getTitle());
	               showschedule.putExtra("content", curItem.getContent());
	               showschedule.putExtra("startdate", curItem.getStartDate());
	               showschedule.putExtra("enddate", curItem.getEndDate());
	               showschedule.putExtra("importance", curItem.getImportance());
	               
	               startActivityForResult(showschedule,REQUEST_CODE_CALENDAR_SHOW);

				}
			});
	        tueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) tueAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                
	                tueAdapter.setSelectedPosition(position);
	                tueAdapter.notifyDataSetChanged();
	 
	                curPosition = position;
	                
	                curItem.getSn();
	                
	               Intent showschedule = new Intent(getApplicationContext(),ScheduleShowActivity.class);
	               showschedule.putExtra("sn", curItem.getSn());
	               showschedule.putExtra("userPn", userPn);
	               showschedule.putExtra("title",curItem.getTitle());
	               showschedule.putExtra("content", curItem.getContent());
	               showschedule.putExtra("startdate", curItem.getStartDate());
	               showschedule.putExtra("enddate", curItem.getEndDate());
	               showschedule.putExtra("importance", curItem.getImportance());
	               
	               startActivityForResult(showschedule,REQUEST_CODE_CALENDAR_SHOW);

				}
			});
	        wedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) wedAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                
	                wedAdapter.setSelectedPosition(position);
	                wedAdapter.notifyDataSetChanged();
	 
	                curPosition = position;
	                
	                curItem.getSn();
	                
	               Intent showschedule = new Intent(getApplicationContext(),ScheduleShowActivity.class);
	               showschedule.putExtra("sn", curItem.getSn());
	               showschedule.putExtra("userPn", userPn);
	               showschedule.putExtra("title",curItem.getTitle());
	               showschedule.putExtra("content", curItem.getContent());
	               showschedule.putExtra("startdate", curItem.getStartDate());
	               showschedule.putExtra("enddate", curItem.getEndDate());
	               showschedule.putExtra("importance", curItem.getImportance());
	               
	               startActivityForResult(showschedule,REQUEST_CODE_CALENDAR_SHOW);

				}
			});
	        thuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) thuAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                
	                thuAdapter.setSelectedPosition(position);
	                thuAdapter.notifyDataSetChanged();
	 
	                curPosition = position;
	                
	                curItem.getSn();
	                
	               Intent showschedule = new Intent(getApplicationContext(),ScheduleShowActivity.class);
	               showschedule.putExtra("sn", curItem.getSn());
	               showschedule.putExtra("userPn", userPn);
	               showschedule.putExtra("title",curItem.getTitle());
	               showschedule.putExtra("content", curItem.getContent());
	               showschedule.putExtra("startdate", curItem.getStartDate());
	               showschedule.putExtra("enddate", curItem.getEndDate());
	               showschedule.putExtra("importance", curItem.getImportance());
	               
	               startActivityForResult(showschedule,REQUEST_CODE_CALENDAR_SHOW);

				}
			});
	        friListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) friAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                
	                friAdapter.setSelectedPosition(position);
	                friAdapter.notifyDataSetChanged();
	 
	                curPosition = position;
	                
	                curItem.getSn();
	                
	               Intent showschedule = new Intent(getApplicationContext(),ScheduleShowActivity.class);
	               showschedule.putExtra("sn", curItem.getSn());
	               showschedule.putExtra("userPn", userPn);
	               showschedule.putExtra("title",curItem.getTitle());
	               showschedule.putExtra("content", curItem.getContent());
	               showschedule.putExtra("startdate", curItem.getStartDate());
	               showschedule.putExtra("enddate", curItem.getEndDate());
	               showschedule.putExtra("importance", curItem.getImportance());
	               
	               startActivityForResult(showschedule,REQUEST_CODE_CALENDAR_SHOW);

				}
			});
	        
	        satListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) satAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                
	                satAdapter.setSelectedPosition(position);
	                satAdapter.notifyDataSetChanged();
	 
	                curPosition = position;
	                
	                curItem.getSn();
	                
	               Intent showschedule = new Intent(getApplicationContext(),ScheduleShowActivity.class);
	               showschedule.putExtra("sn", curItem.getSn());
	               showschedule.putExtra("userPn", userPn);
	               showschedule.putExtra("title",curItem.getTitle());
	               showschedule.putExtra("content", curItem.getContent());
	               showschedule.putExtra("startdate", curItem.getStartDate());
	               showschedule.putExtra("enddate", curItem.getEndDate());
	               showschedule.putExtra("importance", curItem.getImportance());
	               
	               startActivityForResult(showschedule,REQUEST_CODE_CALENDAR_SHOW);

				}
			});
	        
	   }

	   private void showResult(GscheduleList result) { 
	 		
	 		
	 		int schedulelist_size = result.getGschedulelist().size();
	 	
	 		 		
	 		for(int t=0; t<schedulelist_size; t++){
	 			
	 			
	 			sn=result.getGschedulelist().get(t).getSn();
	 			gn=result.getGschedulelist().get(t).getGn();
	 			userPn=result.getGschedulelist().get(t).getUser_pn();
	 			//userId=result.getGschedulelist().get(t).getuserId();
	 			title=result.getGschedulelist().get(t).getTitle();
	 			content=result.getGschedulelist().get(t).getContent();
	 			startDate=result.getGschedulelist().get(t).getStartDate();
	 			endDate=result.getGschedulelist().get(t).getEndDate();
	 			importance=result.getGschedulelist().get(t).getImportance();
	 			
	 			
	 			
	 			
	 			

	 			
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
	            
	            
	            Calendar mCalendar=Calendar.getInstance();
	            
	           //Integer monthofweek=mCalendar.get(Calendar.WEEK_OF_MONTH);
	           
	          // Log.i("test", monthofweek.toString());
	            
	           // Integer tempcurmonth=monthViewAdapter.getCurMonth();
	           	            
	           
	           int curWeek;
	           
	           Log.i("test", "firstDay : " +firstDay +"curDay : "+ curDay+ "CurMonth : "+curMonth+"curYear : "+ curYear);
	           if(0<=firstDay+curDay && firstDay+curDay<=6)   //현재날짜가 몇주차인지 알려주는 부분
	        	   curWeek=1;
	           else if(7<=firstDay+curDay && firstDay+curDay<=13)
	        	   curWeek=2;
	           else if(14<=firstDay+curDay && firstDay+curDay<=20)
	        	   curWeek=3;
	           else if(21<=firstDay+curDay && firstDay+curDay<=27)
	        	   curWeek=4;
	           else if(28<=firstDay+curDay && firstDay+curDay<=34)
	        	   curWeek=5;
	           else
	        	   curWeek=0;
	            
	           
	           
	          
	           
	           
	           int week=0;
	           if(curYear==year && curMonth==month){
	           
	        	   
	           
	        	   if(0<=firstDay+day && firstDay+day<=6)   //post로 받아온날짜가 몇주차인지 알려주는 부분
	        		   week=1;
	        	   else if(7<=firstDay+day && firstDay+day<=13)
	        		   week=2;
	        	   else if(14<=firstDay+day && firstDay+day<=20)
	        		   week=3;
	        	   else if(21<=firstDay+day && firstDay+day<=27)
	        		   week=4;
	        	   else if(28<=firstDay+curDay && firstDay+curDay<=34)
	        		   week=5;
	        	   else
	        		   week=0;
	 
	           }
	           
	           
	            if ( week == curWeek) {
	            	
	            	if((firstDay+day)%7==0){
	            		
	            		Gschedule aItem = new Gschedule(sn,gn,userPn,title,content,startDate,endDate,importance);        		

	            		sunAdapter.addItem(aItem);      		
	            	
	            	}
	            	
	            	else if((firstDay+day)%7==1){
	            		Gschedule aItem = new Gschedule(sn,gn,userPn,title,content,startDate,endDate,importance);          		

	            		monAdapter.addItem(aItem); 
	            	}
	            	
	            	else if((firstDay+day)%7==2){
	            		Gschedule aItem = new Gschedule(sn,gn,userPn,title,content,startDate,endDate,importance);           		

	            		tueAdapter.addItem(aItem); 
	            	}
	            	
	            	else if((firstDay+day)%7==3){
	            		Gschedule aItem = new Gschedule(sn,gn,userPn,title,content,startDate,endDate,importance);           		

	            		wedAdapter.addItem(aItem); 
	            	}
	            	
	            	else if((firstDay+day)%7==4){
	            		Gschedule aItem = new Gschedule(sn,gn,userPn,title,content,startDate,endDate,importance);          		

	            		thuAdapter.addItem(aItem); 
	            	}
	            	
	            	else if((firstDay+day)%7==5){
	            		Gschedule aItem = new Gschedule(sn,gn,userPn,title,content,startDate,endDate,importance);          		

	            		friAdapter.addItem(aItem); 
	            	}
	            	
	            	else{
	            		Gschedule aItem = new Gschedule(sn,gn,userPn,title,content,startDate,endDate,importance);           		

	            		satAdapter.addItem(aItem); 
	            	}

	                
	           	         

	              
	            }
	            
	 	        
	 	        
	 	        }//포문 끝 이게 좀 느리면 위에를좀 손본다 하지만 일단 그냥 써보기
	 		
	 		
	 		
	 		sunListView.setAdapter(sunAdapter);
	 		monListView.setAdapter(monAdapter);
	 		tueListView.setAdapter(tueAdapter);
	 		wedListView.setAdapter(wedAdapter);
	 		thuListView.setAdapter(thuAdapter);
	 		friListView.setAdapter(friAdapter);
	 		satListView.setAdapter(satAdapter);
	 		//SA.scheduleList = outScheduleList; //리스트에 추가, 삭제와 같은 것들을 하기위함.
         //  SA.notifyDataSetChanged(); //스케줄어댑터새로고침  //이거 없으면 안되냐?

           
	 	}//////여기까지 showResult

	 	// ***************************************
	 	// Private classes  아직 사용안함.
	 	// ***************************************
	 	private class PostListofGSchedule extends AsyncTask<Gschedule, Void, GscheduleList> {  //중간건 모르겠고 첫번째것이 보내는거 , 뒤에것이 받아오는거

	 		
	 		Gschedule glist; 
	 		
	 		protected void onPreExecute() {	
	 			
	 			glist = new Gschedule();
	 	
	 			glist.setGn(gn);
	 			glist.setUser_pn(userPn);
	 			
	 			
	 		}

	 		@Override
	 		protected GscheduleList doInBackground(Gschedule... params) {
	 			try {
	 				// The URL for making the POST request
	 				final String url = getString(R.string.base_uri)
	 						+ "/mokkoji/PostListofGSchedule.json";

	 				HttpHeaders requestHeaders = new HttpHeaders();

	 				// Sending multipart/form-data
	 				requestHeaders.setContentType(MediaType.APPLICATION_JSON);

	 				// Populate the MultiValueMap being serialized and headers in an
	 				// HttpEntity object to use for the request
	 				HttpEntity<Gschedule> requestEntity = new HttpEntity<Gschedule>(
	 						glist, requestHeaders);

	 				// Create a new RestTemplate instance
	 				RestTemplate restTemplate = new RestTemplate(true);
	 				restTemplate.getMessageConverters().add(
	 						new StringHttpMessageConverter());
	 				restTemplate.getMessageConverters().add(
	 						new MappingJackson2HttpMessageConverter());

	 				// Make the network request, posting the message, expecting a
	 				// String in response from the server
	 				ResponseEntity<GscheduleList> response = restTemplate.exchange(
	 						url, HttpMethod.POST, requestEntity, GscheduleList.class);

	 				// Return the response body to display to the user
	 				return response.getBody();

	 			} catch (Exception e) {
	 				//Log.e(TAG, e.getMessage(), e);
	 			}

	 			return null;
	 		}
	 		
	 		@Override
	 		protected void onPostExecute(GscheduleList result) {
	 			showResult(result);
	 		}

	 	}
	   
	   
}
