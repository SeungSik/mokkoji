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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pro3.R;
import com.mokkoji.connect.Schedule;
import com.mokkoji.connect.ScheduleList;

public class ScheduleWeekViewActivity extends Activity{

   
   public static final int REQUEST_CODE_CALENDAR_SHOW= 1080;
   
   int curPosition;
   
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
   
   
   Calendar mCalendar;
   Button preweek;
   Button nextweek;
   
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
   
   int week_sunday;  // 그 주의 일요일을 계산 
   int prev_sunday; 
   int year, month, date;
   int lastday;
   int prev_lastday;
   int this_lastday;
   
   int curdayofmonth;
   int curmonth;
   int curyear;
   int curdayofweek;

   
   public ScheduleWeekViewActivity() {
      // TODO Auto-generated constructor stub
      
      
   }
   
      public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           
           
          
           intent=getIntent();
           setContentView(R.layout.schedule_week);
           
           sun = (TextView) findViewById(R.id.weeksunday);
           mon = (TextView) findViewById(R.id.weekmonday);
           tue = (TextView) findViewById(R.id.weektuesday);
           wed = (TextView) findViewById(R.id.weekwednesday);
           thu = (TextView) findViewById(R.id.weekthursday);
           fri = (TextView) findViewById(R.id.weekfriday);
           sat = (TextView) findViewById(R.id.weeksaturday);
           
           preweek = (Button) findViewById(R.id.pre_week);
           nextweek = (Button) findViewById(R.id.next_week);
              
          
           sunAdapter= new ScheduleListAdapter(this);
           monAdapter= new ScheduleListAdapter(this);
           tueAdapter= new ScheduleListAdapter(this);
           wedAdapter= new ScheduleListAdapter(this);
           thuAdapter= new ScheduleListAdapter(this);
           friAdapter= new ScheduleListAdapter(this);
           satAdapter= new ScheduleListAdapter(this);

           
           sunListView =(ListView) findViewById(R.id.sunView);
           monListView =(ListView) findViewById(R.id.monView);
           tueListView =(ListView) findViewById(R.id.tueView);
           wedListView =(ListView) findViewById(R.id.wedView);
           thuListView =(ListView) findViewById(R.id.thuView);
           friListView =(ListView) findViewById(R.id.friView);
           satListView =(ListView) findViewById(R.id.satView);
           
           
           //  new PostListofSchedule().execute();   //전체 스케줄을 가지고옴 
           //현재 날짜 받아옴 
           
           Calendar cal= Calendar.getInstance ();
           
           curdayofmonth=cal.get(cal.DAY_OF_MONTH);
           curdayofweek=cal.get(cal.DAY_OF_WEEK);
           curyear=cal.get(cal.YEAR);
           curmonth=cal.get(cal.MONTH);
           
           Log.i("test",""+curdayofmonth+" "+curdayofweek+" "+curyear+" "+curmonth);
           
           year = cal.get ( cal.YEAR );
           month = cal.get ( cal.MONTH ) + 1 ;
           date = cal.get ( cal.DATE ) ;
           
           this_lastday = getMonthLastDay(year, month);
          
           //현재 요일이 무슨 요일인지 구함  1-일요일 , 7-토요일 
           int whatday = cal.get(Calendar.DAY_OF_WEEK);
         
           //그럼 그 주의 일요일이 무슨 요일인지 구함
           switch(whatday){
           
              case 1:
                 week_sunday = date;
                 break;
              case 2:
                 week_sunday = date-1;
                 break;
              case 3:
                 week_sunday = date-2;
                 break;
              case 4:
                 week_sunday = date-3;
                 break;
              case 5:
                 week_sunday = date-4;
                 break;
              case 6:
                 week_sunday = date-5;
                 break;
              case 7:
                 week_sunday = date-6;
                 break;
           }
           //그 주의 일요일이 저번달에 있는 경우 
           if(week_sunday < 0){
              int pre_month = month-1;
              int pre_year = year;
              if(pre_month == 0){   //1월에서 이전달 구할 때는 작년의 12월 구하기 
                 pre_month = 12;
                 pre_year = year-1;
                 
              }
               prev_lastday = getMonthLastDay(pre_year, pre_month);  //이전달의 마지막 날 검색
              prev_sunday = prev_lastday-week_sunday;   //전달의 일요일 구해오기 
           }else{
              prev_sunday =0;    
           }

           
           new findWeekScheudle().execute();
           //저번 주 출력
           preweek.setOnClickListener(new OnClickListener() {
               public void onClick(View v) {
                  
                  if(prev_sunday == 0){
                     Log.i("test", "prev_sunday "+prev_sunday);
                     week_sunday = week_sunday-7;
                     Log.i("test", "week_sunday "+week_sunday);
                     if(week_sunday<=0){
                     
                        int pre_month = month-1;
                         int pre_year = year;
                         if(pre_month == 0){   //1월에서 이전달 구할 때는 작년의 12월 구하기 
                            pre_month = 12;
                            pre_year = year-1;
                            
                         }
                          prev_lastday = getMonthLastDay(pre_year, pre_month);  //이전달의 마지막 날 검색
                         prev_sunday = lastday-week_sunday;   //전달의 일요일 구해오기 
                         
                         
                         
                         Log.i("test", "prev_sunday2 "+prev_sunday);
                         
                         new findWeekScheudle().execute();
                     }
                     new findWeekScheudle().execute();
                  }else{
                     
                     int pre_month = month-1;
                      int pre_year = year;
                      if(pre_month == 0){   //1월에서 이전달 구할 때는 작년의 12월 구하기 
                         pre_month = 12;
                         pre_year = year-1;
                         
                      }
                      month = pre_month;
                      year = pre_year;
                      week_sunday = prev_sunday-7;
                      prev_sunday =0;
                      
                      new findWeekScheudle().execute();
                     
                  }
                  
               }   
         });
           //다음주 출력 
           nextweek.setOnClickListener(new OnClickListener() {
               public void onClick(View v) {
                  
                  if(week_sunday+6>=this_lastday){
                     //마지막 날짜 - 그 주의 일요일 날짜 +1 : 이번달에 해당되는 계수
                     int count = this_lastday-week_sunday+1;
                     int last_count = 7- count;
                     //그러면 그 다음달은 last_count+1 일이 그 주의 첫번째 일요일
                     
                     int next_month = month+1;
                     if(next_month ==13){
                        next_month=1;
                        year = year+1;
                     }
                     month = next_month;
                     week_sunday = last_count +1;
                     
                     new findWeekScheudle().execute();
                  }
                  
                  else{
                     
                     week_sunday  = week_sunday+7;
                     
                     new findWeekScheudle().execute();
                  }

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
      
   
      
  	// ~ Post
  	 	private void showResult(ScheduleList result) { 
  	 		
  	 	 		
  	 		int schedulelist_size = result.getSchedulelist().size();
  	 		
  	 		Log.i("test", "사이즈다"+schedulelist_size);
  	 		
  	 		
  	 		sunAdapter= new ScheduleListAdapter(this);
            monAdapter= new ScheduleListAdapter(this);
            tueAdapter= new ScheduleListAdapter(this);
            wedAdapter= new ScheduleListAdapter(this);
            thuAdapter= new ScheduleListAdapter(this);
            friAdapter= new ScheduleListAdapter(this);
            satAdapter= new ScheduleListAdapter(this);
  	 		 		
            
            sun.setText("일"+"\n"+month+"."+week_sunday);
            mon.setText("월"+"\n"+month+"."+(week_sunday+1));
            tue.setText("화"+"\n"+month+"."+(week_sunday+2));
            wed.setText("수"+"\n"+month+"."+(week_sunday+3));
            thu.setText("목"+"\n"+month+"."+(week_sunday+4));
            fri.setText("금"+"\n"+month+"."+(week_sunday+5));
            sat.setText("토"+"\n"+month+"."+(week_sunday+6));
  	 		for(int t=0; t<schedulelist_size; t++){
  	 			
  	 			sn=result.getSchedulelist().get(t).getSn();
  	 			userPn=result.getSchedulelist().get(t).getUserPn();
  	 			userId=result.getSchedulelist().get(t).getuserId();
  	 			title=result.getSchedulelist().get(t).getTitle();
  	 			content=result.getSchedulelist().get(t).getContent();
  	 			startDate=result.getSchedulelist().get(t).getStartDate();
  	 			endDate=result.getSchedulelist().get(t).getEndDate();
  	 			importance=result.getSchedulelist().get(t).getImportance();
  	 			
  	 			
  	 			
  	 			
  	 			

  	 			
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
  	            
  	            
  	            
  	         
  	          Date temp= new Date();
 			 temp.setYear(year);
 			 temp.setMonth(month);
 			 temp.setDate(day);
 			 
 			 int dayofweek=temp.getDay();
 			 
 			 
 			Schedule aItem = new Schedule(sn,userPn,userId,title,content,startDate,endDate,importance); 
 			 switch(dayofweek){
 			 
 			 case 0:
 				sunAdapter.addItem(aItem);
 				
 				break;
 			 case 1:
 				 monAdapter.addItem(aItem);
 				
 				 break;
 			 case 2:
 				 tueAdapter.addItem(aItem);
 				
 				 break;
 			 case 3:
 				 wedAdapter.addItem(aItem);
 				
 				 break;
 			 case 4:
 				 thuAdapter.addItem(aItem);
 				
 				 break;
 			 case 5:
 				 friAdapter.addItem(aItem);
 				
 				 break;
 			 case 6:
 				 satAdapter.addItem(aItem);
 				
 				 break;
 			 default:
 				 break; 
 			 
 			 
 			 
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
       private class findWeekScheudle extends AsyncTask<Integer, Void, ScheduleList> {  //중간건 모르겠고 첫번째것이 보내는거 , 뒤에것이 받아오는거

          Schedule findweek;
          String firstday_ofweek; 
          String enddayofweek;
          
          protected void onPreExecute() {

          //어레이리스트 에다가 userPn으로 가져온거 다 담을려고      
             findweek = new Schedule();
             
             
             userPn = intent.getIntExtra("userPn", 0);
             findweek.setUserPn(userPn);
             if(prev_sunday==0){
                
                firstday_ofweek = year+"-"+month+"-"+week_sunday;
                findweek.setStartDate(firstday_ofweek);
                
                
             }else{
                
                firstday_ofweek = year+"-"+(month-1)+"-"+prev_sunday;
                findweek.setStartDate(firstday_ofweek);  //저번달의 마지막 주 일요일 계산 
                int pre_month = month-1;
                 int pre_year = year;
                 if(pre_month == 0){   //1월에서 이전달 구할 때는 작년의 12월 구하기 
                    pre_month = 12;
                    pre_year = year-1;
                    
                 }
                 prev_lastday = getMonthLastDay(pre_year, pre_month);
                findweek.setEndDate(pre_year+"-"+pre_month+"-"+prev_lastday); //이전 달의 마지막 날짜 
                
             }
              // 그 주의 일요일을 제외한 나머지 날짜가 다음달에 있는 경우
              if(week_sunday +6 > this_lastday){
                 //마지막 날짜 - 그 주의 일요일 날짜 +1 : 이번달에 해당되는 계수
                 findweek.setEndDate(year+"-"+month+"-"+this_lastday);
                 
              }
             
          }

          @Override
          protected ScheduleList doInBackground(Integer... params) {
             try {
                // The URL for making the POST request
                final String url = getString(R.string.base_uri)
                      + "/mokkoji/findWeekScheudle.json";

                HttpHeaders requestHeaders = new HttpHeaders();

                // Sending multipart/form-data
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);

                // Populate the MultiValueMap being serialized and headers in an
                // HttpEntity object to use for the request
                HttpEntity<Schedule> requestEntity = new HttpEntity<Schedule>(
                      findweek, requestHeaders);

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