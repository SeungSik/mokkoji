package com.mokkoji.function;

import java.text.SimpleDateFormat;
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
import com.mokkoji.connect.Group;
import com.mokkoji.connect.Grouplist;
import com.mokkoji.connect.Schedule;
import com.mokkoji.connect.ScheduleList;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

public class ScheduleLookActivity  extends Activity{
   
   
    public static final int REQUEST_CODE_SCHEDULE_LOOK1= 1001;
    public static final int REQUEST_CODE_SCHEDULE_LOOK2= 1002;
    
    public static final int DIALOG_DATE = 1103;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    Date selectedDate;
    String keyword;
     
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
      EditText editKey;

      
      Button dateButton;
   
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
         
          setContentView(R.layout.schedule_look);
          
     
         
          
          Button button1 = (Button)findViewById(R.id.datesearchButton);
          Button button2 = (Button)findViewById(R.id.keywordbutton);
          
          intent=getIntent();
          userPn = intent.getIntExtra("userPn", 0);
          
          
          mListview = (ListView) findViewById(R.id.searchscheduleList);
           

           mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                  int position, long arg3) {

            }
         });//포스트랑 순서 상관없는지 테스트
          //날짜로 검색
          button1.setOnClickListener(new OnClickListener(){
             
             public void onClick(View v){
              //  Intent intent = new Intent(getApplicationContext(),)
                
                new PostListofSchedule().execute();  
             }
             
          });
          
          //키워드로 검색
          
          editKey = (EditText)findViewById(R.id.editKey);
          
          
          button2.setOnClickListener(new OnClickListener(){
             
             public void onClick(View v){
                keyword = editKey.getText().toString();
                new findkeyword().execute();
              
                
             }
             
          });

          
          dateButton = (Button) findViewById(R.id.datebutton);
           dateButton.setOnClickListener(new OnClickListener() {
               public void onClick(View v) {
                   showDialog(DIALOG_DATE);
               }
           });
    }
    
    protected Dialog onCreateDialog(int id) {
           switch (id) {        
                   
                   
               case DIALOG_DATE:
                   String dateStr01 = dateButton.getText().toString();
    
                   Calendar date_calendar01 = Calendar.getInstance();
                   Date date_curDate01 = new Date();
                   try {
                       date_curDate01 = dateFormat.parse(dateStr01);
                   } catch(Exception ex) {
                       ex.printStackTrace();
                   }
    
                   date_calendar01.setTime(date_curDate01);
    
                  
                   
                   int year01 = date_calendar01.get(Calendar.YEAR);
                   int month01 = date_calendar01.get(Calendar.MONTH);
                   int day01 = date_calendar01.get(Calendar.DAY_OF_MONTH);
    
                   return new DatePickerDialog(this,  dateSetListener,  year01, month01,day01);

                  
             
                   
                default:
                   break;
    
           }
    
           return null;
       }
    
    private DatePickerDialog.OnDateSetListener dateSetListener =  new DatePickerDialog.OnDateSetListener() {
         
         @Override
         public void onDateSet(DatePicker view, int year, int monthOfYear,
               int dayOfMonth) {
            
            Calendar selectedCalendar = Calendar.getInstance();
               selectedCalendar.set(Calendar.YEAR, year);
               selectedCalendar.set(Calendar.MONTH, monthOfYear);
               selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    
               Date curDate = selectedCalendar.getTime();
               date_setSelectedDate(curDate);
            
            
         }
         
      };

      private void date_setSelectedDate(Date curDate) {
           selectedDate = curDate;
    
           String selectedTimeStr = dateFormat.format(curDate);
           dateButton.setText(selectedTimeStr);
          
       }
   // ~ Post
          private void showResult(ScheduleList result) { 
             
                 
             ArrayList outScheduleList = null;
             Integer schedulelist_size = result.getSchedulelist().size();
             
             String inputDate= (String) dateButton.getText();
          
                    
             
             StringTokenizer inputst=new StringTokenizer(inputDate,"-");//가져온것을 -로 자른다
               
               int inputYear; //이거 세개는 포스트로 가져온거
               int inputMonth;
               int inputDay;
               
                                
               inputYear=Integer.parseInt(inputst.nextToken()); //각각 잘라서 넣어줌.
               //Log.i("test","year"+inputYear.toString());
               inputMonth=Integer.parseInt(inputst.nextToken())-1;//04가 4 가 되는지가 궁금..... 및 5를 4로 바꾸어준다.
               //Log.i("test","month"+inputMonth.toString());//입력을 05-21로 넣을거니까 로그에 4가 찍히면 성공
               inputDay=Integer.parseInt(inputst.nextToken())-1;
               //Log.i("test","day"+inputDay.toString());
             
               Log.i("test","size"+schedulelist_size.toString());
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
                  
                                   
                  year=Integer.parseInt(st.nextToken());   //각각 잘라서 넣어줌.
                  month=Integer.parseInt(st.nextToken())-1;//04가 4 가 되는지가 궁금..... 및 5를 4로 바꾸어준다.
            
                  day=Integer.parseInt(st.nextToken())-1;
                  
   
                  if (inputYear==year && inputMonth==month && inputDay==day) {
                     
                     Log.i("test","여기세번인가들어와야되는데");
       
                      Schedule aItem = new Schedule(sn,userPn,userId,title,content,startDate,endDate,importance);
      
                   
                    //재정의해서넣기
       
                      if (outScheduleList == null) {
                          outScheduleList = new ArrayList();
                      }
                      outScheduleList.add(aItem);
      
                  }
   
                  }//포문 끝 이게 좀 느리면 위에를좀 손본다 하지만 일단 그냥 써보기
             
             
             SA = new ScheduleListAdapter(this);
             mListview.setAdapter(SA);
             
             if (outScheduleList == null) {
                    outScheduleList = new ArrayList();
                }
             
             SA.scheduleList = outScheduleList; //리스트에 추가, 삭제와 같은 것들을 하기위함.
               SA.notifyDataSetChanged(); //스케줄어댑터새로고침
       
             
          }//////여기까지 showResult

          // ***************************************
          // Private classes  아직 사용안함.
          // ***************************************
          private class PostListofSchedule extends AsyncTask<Integer, Void, ScheduleList> {  //중간건 모르겠고 첫번째것이 보내는거 , 뒤에것이 받아오는거

             protected void onPreExecute() {

             //어레이리스트 에다가 userPn으로 가져온거 다 담을려고      
             
                
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
          
          // ~ Post
      private void showResult_2(ScheduleList result) {
         
         SA = new ScheduleListAdapter(this);
          mListview.setAdapter(SA);
          
          if (result == null) {
                result = new ScheduleList();
            }
          
          SA.scheduleList = result.getSchedulelist(); //리스트에 추가, 삭제와 같은 것들을 하기위함.
            SA.notifyDataSetChanged(); //스케줄어댑터새로고침
         
            
      }

      // ***************************************
      // Private classes
      // ***************************************
      private class findkeyword extends AsyncTask<Void, Void, ScheduleList> {
      
         Schedule temp;
         
         protected void onPreExecute() {
            temp = new Schedule();
            temp.setUserPn(userPn);
            temp.setContent(keyword);
         }

         @Override
         protected ScheduleList doInBackground(Void... params) {
            try {
               // The URL for making the POST request
               final String url = getString(R.string.base_uri)
                     + "/mokkoji/findkeyword.json";

               HttpHeaders requestHeaders = new HttpHeaders();

               // Sending multipart/form-data
               requestHeaders.setContentType(MediaType.APPLICATION_JSON);

               // Populate the MultiValueMap being serialized and headers in an
               // HttpEntity object to use for the request
               HttpEntity<Schedule> requestEntity = new HttpEntity<Schedule>(
                     temp, requestHeaders);

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
               e.printStackTrace();
            }

            return null;
         }
         @Override
         protected void onPostExecute(ScheduleList result) {
            showResult_2(result);
         }

      }

    
}