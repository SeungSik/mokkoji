package com.mokkoji.function;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.mokkoji.connect.*;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RecomManagementActivity extends Activity{

	
	//public static final int REQUEST_CODE_CALENDAR_SHOW= 1080;
	
	int curPosition;
	
	int curweek;
	
	private ScheduleListAdapter sunAdapter;
	private ScheduleListAdapter monAdapter;
	private ScheduleListAdapter tueAdapter;
	private ScheduleListAdapter wedAdapter;
	private ScheduleListAdapter thuAdapter;
	private ScheduleListAdapter friAdapter;
	private ScheduleListAdapter satAdapter;
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
	private Integer recommand_sn;
	private String userId;
	private String title;
	private String content;
	private String startDate;
	private String endDate;
	private Integer importance;
	ArrayList outScheduleList;
	
	
	
	   public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        curweek=1;
	        
	        intent=getIntent();
	        userPn=intent.getIntExtra("userPn", 0);
	        setContentView(R.layout.recommand_management);
	        
	        sun = (TextView) findViewById(R.id.recommand_weeksunday);
	        mon = (TextView) findViewById(R.id.recommand_weekmonday);
	        tue = (TextView) findViewById(R.id.recommand_weektuesday);
	        wed = (TextView) findViewById(R.id.recommand_weekwednesday);
	        thu = (TextView) findViewById(R.id.recommand_weekthursday);
	        fri = (TextView) findViewById(R.id.recommand_weekfriday);
	        sat = (TextView) findViewById(R.id.recommand_weeksaturday);
	        
	        
	      
	        
	        sunAdapter= new ScheduleListAdapter(this);
	        monAdapter= new ScheduleListAdapter(this);
	        tueAdapter= new ScheduleListAdapter(this);
	        wedAdapter= new ScheduleListAdapter(this);
	        thuAdapter= new ScheduleListAdapter(this);
	        friAdapter= new ScheduleListAdapter(this);
	        satAdapter= new ScheduleListAdapter(this);
	        
	        
	       // mListview = (ListView) findViewById(R.id.weekscheduleList);
	        
	        sunListView =(ListView) findViewById(R.id.recommand_sunView);
	        monListView =(ListView) findViewById(R.id.recommand_monView);
	        tueListView =(ListView) findViewById(R.id.recommand_tueView);
	        wedListView =(ListView) findViewById(R.id.recommand_wedView);
	        thuListView =(ListView) findViewById(R.id.recommand_thuView);
	        friListView =(ListView) findViewById(R.id.recommand_friView);
	        satListView =(ListView) findViewById(R.id.recommand_satView);
	        
	        
	        new PostGetRecomschedule().execute();   
	        
	        /*
	        sunListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) sunAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                
	                

				}
			});
	        monListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) monAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                

				}
			});
	        tueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) tueAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                
	               
				}
			});
	        wedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) wedAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                
	               

				}
			});
	        thuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) thuAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                
	               

				}
			});
	        friListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) friAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                
	                

				}
			});
	        
	        satListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Schedule curItem = (Schedule) satAdapter.getItem(position);
		               
	                //클릭된 스케줄을 저장해줌 
	                
	                
	             
				}
			});*/
	        
	   }

	   private void showResult(RecomscheduleList result) {
			
		     Integer rn;
			 Integer userpn;
			 String typename;
			 String StartDate;
			 String EndDate;
			 Integer weektimes;
			 Integer daytime;
			
			 
			 int dayofweek=0;
			 
			 
			 
			 int schedulelist_size = result.getSchedulelist().size();
			 
			 for(int t=0; t<schedulelist_size; t++){
		 			
		 			rn=result.getSchedulelist().get(t).getRn();
		 			userpn=result.getSchedulelist().get(t).getUserpn();
		 			
		 			typename=result.getSchedulelist().get(t).getTypename();
		 			StartDate=result.getSchedulelist().get(t).getStartdate();		 			
		 			EndDate=result.getSchedulelist().get(t).getEnddate();
		 			weektimes=result.getSchedulelist().get(t).getWeektimes();
		 			daytime=result.getSchedulelist().get(t).getDaytime();
		 			daytime+=1;//1시간더해줌
		 			
		 			
		 			StringTokenizer st=new StringTokenizer(StartDate,"-");//가져온것을 -로 자른다
		 			
		 			int year; //이거 세개는 포스트로 가져온거
		            int month;
		            int day;
		            
		            year=Integer.parseInt(st.nextToken());   //각각 잘라서 넣어줌.
		            month=Integer.parseInt(st.nextToken())-1;//04가 4 가 되는지가 궁금..... 및 5를 4로 바꾸어준다.
		            //Log.i("test",month.toString());//입력을 05-21로 넣을거니까 로그에 4가 찍히면 성공
		            day=Integer.parseInt(st.nextToken())-1;
		            
		            
		            Date temp= new Date();
					 temp.setYear(year);
					 temp.setMonth(month);
					 temp.setDate(day);
					 
					 dayofweek=temp.getDay();
					 
					 Log.i("test", ""+dayofweek);
					 
		            
		            Recomschedule aItem = new Recomschedule();
		            Schedule bItem = new Schedule();
		            
		            bItem.setSn(rn);
		            bItem.setUserPn(userpn);
		            bItem.setTitle(typename);
		            bItem.setStartDate(StartDate);
		            bItem.setEndDate(" ");
		            bItem.setImportance(1);//2갑으로 고정
		            
		            
		            
		            
		            
		            
		            aItem.setRn(rn);
		            aItem.setUserpn(userpn);
		            aItem.setTypname(typename);
		            aItem.setStartdate(StartDate);
		            aItem.setEnddate(EndDate);
		            aItem.setWeektimes(weektimes);
		            aItem.setDaytime(daytime);
		            //일단 만들고  스케줄러에 쳐넣는다.
		            
		 			
		 			
		 			
		            
		            
		 			if(weektimes.equals(0)){//일주일에 2일인 경우// 3일간격
		 				if(dayofweek==0){//일요일이면  일 목
		 					bItem.setStartDate("오후6시부터");
		 					bItem.setEndDate(daytime+"시간");
		 					outScheduleList=new ArrayList();
		 					outScheduleList.add(bItem);
		 					sunAdapter.scheduleList=outScheduleList;
		 					//sunAdapter.addItem(bItem);
		 					//thuAdapter.addItem(bItem);
		 					sunAdapter.scheduleList=outScheduleList;
		 					
		 				}
		 				else if(dayofweek==1){
		 					bItem.setStartDate("오후6시부터");
		 					bItem.setEndDate(daytime+"시간");
		 					
		 					outScheduleList=monAdapter.scheduleList;
		 					if(outScheduleList==null){
		 						outScheduleList=new ArrayList();
		 					}
		 					outScheduleList.add(bItem);
		 					monAdapter.scheduleList=outScheduleList;
		 					friAdapter.scheduleList=outScheduleList;
		 					//monAdapter.addItem(bItem);
		 					//friAdapter.addItem(bItem);

		 					
	
		 					
		 				}
		 				else if(dayofweek==2){
		 					bItem.setStartDate("오후6시부터");
		 					bItem.setEndDate(daytime+"시간");
		 					
		 					outScheduleList=tueAdapter.scheduleList;
		 					if(outScheduleList==null){
		 						outScheduleList=new ArrayList();
		 					}
		 					outScheduleList.add(bItem);
		 					
		 					tueAdapter.scheduleList=outScheduleList;
		 					satAdapter.scheduleList=outScheduleList;
		 					
		 					//tueAdapter.addItem(bItem);
		 					//satAdapter.addItem(bItem);
		 					
		 				}
		 				else if(dayofweek==3){
		 					bItem.setStartDate("오후6시부터");
		 					bItem.setEndDate(daytime+"시간");
		 					
		 					outScheduleList=wedAdapter.scheduleList;
		 					if(outScheduleList==null){
		 						outScheduleList=new ArrayList();
		 					}
		 					outScheduleList.add(bItem);
		 					
		 					wedAdapter.scheduleList=outScheduleList;
		 					
		 					
		 					//wedAdapter.addItem(bItem);
		 					
		 					bItem.setStartDate("2번째주 오후6시부터");
		 					
		 					outScheduleList=sunAdapter.scheduleList;
		 					if(outScheduleList==null){
		 						outScheduleList=new ArrayList();
		 					}
		 					outScheduleList.add(bItem);
		 					sunAdapter.scheduleList=outScheduleList;
		 					
		 					//sunAdapter.addItem(bItem);
		 					
		 				}
		 				else if(dayofweek==4){
		 					bItem.setStartDate("오후6시부터");
		 					bItem.setEndDate(daytime+"시간");
		 					thuAdapter.addItem(bItem);
		 					bItem.setStartDate("2번째주 오후6시부터");
		 					monAdapter.addItem(bItem);
		
		 				}
		 				
		 				else if(dayofweek==5){
		 					bItem.setStartDate("오후6시부터");
		 					bItem.setEndDate(daytime+"시간");
		 					friAdapter.addItem(bItem);
		 					bItem.setStartDate("2번째주 오후6시부터");
		 					tueAdapter.addItem(bItem);
		
		 				}
		 				else{//토요일이면
		 					bItem.setStartDate("오후6시부터");
		 					bItem.setEndDate(daytime+"시간");
		 					satAdapter.addItem(bItem);
		 					bItem.setStartDate("2번째주 오후6시부터");
		 					tueAdapter.addItem(bItem);
		
		 				}
		 				
		 				
		 				
		 				
		 			}
		 			else if(weektimes.equals(1)){//일주일에 3일//월 수 금
		 				bItem.setStartDate("오후6시부터");
	 					bItem.setEndDate(daytime+"시간");
		 				monAdapter.addItem(bItem);
		 				
		 				wedAdapter.addItem(bItem);
		 				
		 				friAdapter.addItem(bItem);
		 				
		 			}
		 			else if(weektimes.equals(2)){//일주일에 4일//월 수 금 일
		 				bItem.setStartDate("오후6시부터");
	 					bItem.setEndDate(daytime+"시간");
		 				monAdapter.addItem(bItem);
		 				
		 				wedAdapter.addItem(bItem);
		 				
		 				friAdapter.addItem(bItem);
		 				
		 				sunAdapter.addItem(bItem);
		 				
		 				
		 			}
		 			
		 			else if(weektimes.equals(3)){//일주일에 5일//월화수목금
		 				
		 				bItem.setStartDate("오후6시부터");
	 					bItem.setEndDate(daytime+"시간");
		 				monAdapter.addItem(bItem);
		 				tueAdapter.addItem(bItem);
		 				wedAdapter.addItem(bItem);
		 				thuAdapter.addItem(bItem);
		 				friAdapter.addItem(bItem);	 				
		 				
		
		 			}
		 			else{
		 				bItem.setStartDate("테스트");
	 					bItem.setEndDate(daytime+"시간");
		 				sunAdapter.addItem(bItem);
		 			}
		 			
		 			
		 			
		 			
		 			
		 			
		 			
			 }
			 			 
				
			 
			 	sunListView.setAdapter(sunAdapter);
		 		monListView.setAdapter(monAdapter);
		 		tueListView.setAdapter(tueAdapter);
		 		wedListView.setAdapter(wedAdapter);
		 		thuListView.setAdapter(thuAdapter);
		 		friListView.setAdapter(friAdapter);
		 		satListView.setAdapter(satAdapter);
			
			
			
			

		}
		// ***************************************
		 	// Private classes
		 	// ***************************************
		 	private class PostGetRecomschedule extends AsyncTask<Void, Void,RecomscheduleList > {

		 		private Recomschedule recom;

		 		@Override
		 		protected void onPreExecute() {

		 			recom = new Recomschedule(); 	
		 			recom.setUserpn(userPn);
		 			
		 		}

		 		@Override
		 		protected RecomscheduleList doInBackground(Void... params) {
		 			try {
		 				// The URL for making the POST request
		 				final String url = getString(R.string.base_uri)+"/mokkoji/PostGetRecomschedule.json";

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
		 				ResponseEntity<RecomscheduleList> response = restTemplate.exchange(url,
		 						HttpMethod.POST, requestEntity, RecomscheduleList.class);

		 				// Return the response body to display to the user
		 				return response.getBody();
		 			} catch (Exception e) {
		 				//Log.e(TAG, e.getMessage(), e);
		 			}

		 			return null;
		 		}

		 		@Override
		 		protected void onPostExecute(RecomscheduleList result) {
		 			showResult(result);
		 		}
		 		
		 	}

	}
