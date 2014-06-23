package com.mokkoji.function;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
public class CalendarMonthViewActivity extends Activity {
 
    GridView monthView;
    CalendarMonthAdapter monthViewAdapter;
    
   

    TextView monthText;
 
    int curYear;
    int curMonth;
 
    int curPosition;
    EditText scheduleInput;
    Button saveButton;
    Button Groupbtn;
    Button Recbtn;
 
    ListView scheduleList;
    ScheduleListAdapter scheduleAdapter;
    ArrayList outScheduleList;
    
    Intent intentToScheduleInput;//
    Intent getintent ;
    
    
    ////////////최초 스케쥴 표시를 위해 추가한 부분

    ScheduleList tempschedulelist ;
    
    String startDate ;
    String endDate ;
    String content ;
    String title ;
    Integer importance;
    Integer userPn ;
      
    Integer sn ;   
    String userId ;
   
    //////////최초 스케쥴 표시를 ㅜ이해 추가한 부분
 
    public static final int REQUEST_CODE_SCHEDULE_INPUT = 1001;
    public static final int REQUEST_CODE_SCHEDULE_SEARCH = 1007;
    public static final int REQUEST_CODE_SCHEDULE_TODAY = 1004;
    public static final int REQUEST_CODE_SCHEDULE_WEEK = 1003;
    public static final int REQUEST_CODE_SCHEDULE_GROUP = 1006;
    public static final int REQUEST_CODE_SCHDULE_LIST = 1005;
    public static final int REQUEST_CODE_CALENDAR_GROUP= 1111;
    public static final int REQUEST_CODE_CALENDAR_SHOW= 1080;
    public static final int REQUEST_CODE_RECOMMAND = 1777;
    
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        
        getintent = getIntent();
        userPn = getintent.getIntExtra("userPn", 0);
        
        monthView = (GridView) findViewById(R.id.monthView);
        monthViewAdapter = new CalendarMonthAdapter(this,userPn);
        
        monthView.setAdapter(monthViewAdapter);
        
        
        intentToScheduleInput = new Intent(this, ScheduleInputActivity.class);
        
        
 
        
        //시작하자마자 받아오기 테스트!!!!!!!!!!
                       
       new PostListofSchedule().execute(); //포스트 해주기.
          
        
        //여기까지!!!!!!!
      
        // set listener
        monthView.setOnItemClickListener(new OnItemClickListener() {//그리드뷰에서 날짜 한개를 클릭 했을때
            public void onItemClick(AdapterView parent, View view, int position, long id) { //요 포지션을 갖다가 쓰면 몇일인지 알 수 있다.
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                int day = curItem.getDay();//이 포지션에 이게 몇일인지 가져온다.
                
                          
 
                monthViewAdapter.setSelectedPosition(position); //선택했을떄 노란색으로 바꿔주고
                monthViewAdapter.notifyDataSetChanged();// 새로고침!
 
                // set schedule to the TextView
                curPosition = position;  //선택한 포지션을 현재 포지션으로 집어넣는다.
 
                outScheduleList = monthViewAdapter.getSchedule(position);//
                if (outScheduleList == null) {
                    outScheduleList = new ArrayList<Schedule>();
                }
                scheduleAdapter.scheduleList = outScheduleList;
 
                scheduleAdapter.notifyDataSetChanged();
            }
        });
 
        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();
 
        Button monthPrevious = (Button) findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
                monthViewAdapter.setPreviousMonth();
                //monthViewAdapter.temp();
                
                new PostListofSchedule().execute(); //포스트 해주기
                
                
                

                setMonthText();
                
                monthViewAdapter.notifyDataSetChanged();
            }
        });
 
        Button monthNext = (Button) findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
                monthViewAdapter.setNextMonth();
                //monthViewAdapter.temp();
                
                new PostListofSchedule().execute(); //포스트 해주기
                
                setMonthText();
                
                monthViewAdapter.notifyDataSetChanged();
            }
        });
 
 
        curPosition = -1;
 
        scheduleList = (ListView)findViewById(R.id.scheduleList);
        scheduleAdapter = new ScheduleListAdapter(this);
        scheduleList.setAdapter(scheduleAdapter);
 
 
        scheduleList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Schedule curItem = (Schedule) scheduleAdapter.getItem(position);
               
                //클릭된 스케줄을 저장해줌 
                
                
                scheduleAdapter.setSelectedPosition(position);
                scheduleAdapter.notifyDataSetChanged();
 
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
        
        Groupbtn = (Button) findViewById(R.id.Groupbtn);
        Groupbtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               
               Intent intent = new Intent(getApplicationContext(), ChooseGroupActivity.class);
               intent.putExtra("userPn", userPn);
               startActivityForResult(intent,REQUEST_CODE_CALENDAR_GROUP);
               
            }
        });
        
        Recbtn = (Button) findViewById(R.id.recommandbtn);
        Recbtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               
               Intent intent = new Intent(getApplicationContext(), RecomscheduleActivity.class);
               intent.putExtra("userPn", userPn);
               startActivityForResult(intent,REQUEST_CODE_RECOMMAND );
               
            }
        });
 
    }
 
 
    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();
 
        monthText.setText(curYear + "년 " + (curMonth+1) + "월");
    }
 
 
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
 
        addOptionMenuItems(menu);
 
        return true;
    }
 
    private void addOptionMenuItems(Menu menu) {
        int addSchedule = Menu.FIRST;
        int deleteAll= 2;
        int gotoWeek=3;
        int gotoToday=4;
        int modifyGroup=5;
        int groupsSchedule=6;
        int searchSchedule=7;
        menu.clear();
 
        menu.add(1, addSchedule, Menu.NONE, "일정추가");
        menu.add(1, deleteAll,Menu.NONE, "전체 삭제");
        menu.add(1, searchSchedule,Menu.NONE,"일정 검색");
        menu.add(2, gotoWeek,Menu.NONE,"주간 일정");
        menu.add(2, gotoToday,Menu.NONE,"금일 일정");
        menu.add(3, modifyGroup,Menu.NONE,"그룹 설정");
        menu.add(3, groupsSchedule,Menu.NONE,"그룹 정보");
        
    }
 
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST:
                showScheduleInput();
 
                return true;
            case 2:
               scheduleAdapter.clear();
                scheduleAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "전체 일정이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
               
               return true;
            case 3:
               showWeekSchedule();
               return true;
            case 4:
               showTodaySchedule();
               return true;
            case 5:
               showGroupAdmin();
               return true;
            case 6:
               showGroupList();
               return true;
            case 7:
               showSearchSchedule();
               return true;
               
            default:
                break;
         }
 
         return false;
    }
 
    private void showScheduleInput() {
  
        Integer userPn=getintent.getIntExtra("userPn", 0);
        String userId=getintent.getStringExtra("userId");
 
        intentToScheduleInput.putExtra("userPn", userPn);
        intentToScheduleInput.putExtra("userId", userId);
       
        startActivityForResult(intentToScheduleInput, REQUEST_CODE_SCHEDULE_INPUT);
    }
    private void showTodaySchedule() {
       Intent intent = new Intent(getApplicationContext(), ScheduleTodayActivity.class);
        Calendar mCalendar=Calendar.getInstance();
        intent.putExtra("firstDay", monthViewAdapter.firstDay);
       intent.putExtra("curDay", mCalendar.get(Calendar.DAY_OF_MONTH));//+1�댁쨾�쇰릺�붿����붾쾭源낆쑝濡��뺤씤
       intent.putExtra("curMonth",mCalendar.get(Calendar.MONTH));
       intent.putExtra("curYear", monthViewAdapter.curYear);
       intent.putExtra("userPn", userPn);
     
        startActivityForResult(intent, REQUEST_CODE_SCHEDULE_TODAY);
    }
    private void showSearchSchedule(){
       Intent intent = new Intent(getApplicationContext(), ScheduleLookActivity.class);
       intent.putExtra("userPn", userPn);
       
       startActivityForResult(intent,REQUEST_CODE_SCHEDULE_SEARCH);
       
    }
    private void showWeekSchedule(){
       Calendar mCalendar=Calendar.getInstance();
       Intent intent = new Intent(this, ScheduleWeekViewActivity.class);
       intent.putExtra("firstDay", monthViewAdapter.firstDay);
       intent.putExtra("curDay", mCalendar.get(Calendar.DAY_OF_MONTH));//+1�댁쨾�쇰릺�붿����붾쾭源낆쑝濡��뺤씤
       intent.putExtra("curMonth",mCalendar.get(Calendar.MONTH));
       intent.putExtra("curYear", monthViewAdapter.curYear);
       intent.putExtra("userPn", userPn);
       startActivityForResult(intent,REQUEST_CODE_SCHEDULE_WEEK);
    }
    private void showGroupAdmin(){
       Intent intent = new Intent(this, GroupAdminActivity.class);
       intent.putExtra("userPn", userPn);
       startActivityForResult(intent,REQUEST_CODE_SCHEDULE_GROUP);
    }
    private void showGroupList(){
       Intent intent = new Intent(this, GroupViewActivity.class );
       intent.putExtra("userPn", userPn);
       startActivityForResult(intent,REQUEST_CODE_SCHDULE_LIST);
    }
    
 
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
 
        if (requestCode == REQUEST_CODE_SCHEDULE_INPUT  &&  resultCode==RESULT_OK  ) {
            
           
           String startDate = intent.getStringExtra("startDate");   //여기 변수들은 여기서만 쓰기위해 새로 재정의됨. 클래스의 멤버변수들과는 상관이 없다.
            String endDate = intent.getStringExtra("endDate");
            String content = intent.getStringExtra("contents");
            String title = intent.getStringExtra("title");
            Integer importance = intent.getIntExtra("importance", 0);
            Integer userPn = intent.getIntExtra("userPn", 0);

            
            String startdateInfo = intent.getStringExtra("startdateInfo");//여기에 2014-05-21 같은 정보가 온다 이것을 int 형 2014, 4,21등으로 바꾸어주어야한다.
            
            StringTokenizer st=new StringTokenizer(startdateInfo,"-");//가져온것을 -로 자른다
            
            int year;
            int month;
            int day;
            
            int firstday=monthViewAdapter.getfirstDay();//퍼스트데이얻어옴
            
            year=Integer.parseInt(st.nextToken());   //각각 잘라서 넣어줌.
            month=Integer.parseInt(st.nextToken())-1;//04가 4 가 되는지가 궁금..... 및 5를 4로 바꾸어준다.
            //Log.i("test",month.toString());//입력을 05-21로 넣을거니까 로그에 4가 찍히면 성공
            day=Integer.parseInt(st.nextToken())-1;
            
            Integer tempcurmonth=monthViewAdapter.getCurMonth();
            Integer undo;
            
            tempcurmonth-=month;
            
            if(tempcurmonth!=0)//현재달과 입력된달의 차이가 있을때만 실행
            {
            
               if(tempcurmonth>0){//현재 달에서 입력된 달을 뺏을때 양수이면 현재달보다 작은 달이 입력된것이므로(예를들면 현재가 4월인데 3월이 입력됨) 그러면 이전달로가야됨
                  undo=tempcurmonth;
                  while(tempcurmonth>0){
                     tempcurmonth--;
                     monthViewAdapter.setPreviousMonth();
                  
                  }
                  firstday=monthViewAdapter.getfirstDay();//달 정보가다를경우 이렇게 달을 옮긴후 퍼스트데이 가져온다.
               
               
                  while(undo>0){//그리고나서 원래 달로 돌려줌.
                     undo--;
                     monthViewAdapter.setNextMonth();
                  }
                  
               }
               
               else{//위에랑 반대일때
                  tempcurmonth=-tempcurmonth;
                  undo=tempcurmonth;
                  
                  while(tempcurmonth>0){
                     tempcurmonth--;
                     monthViewAdapter.setNextMonth();
                     
                  
                  }
                  firstday=monthViewAdapter.getfirstDay();//달 정보가다를경우 이렇게 달을 옮긴후 퍼스트데이 가져온다.
               
               
                  while(undo>0){//그리고나서 원래 달로 돌려줌.
                     undo--;                     
                     monthViewAdapter.setPreviousMonth();
                  }
                  
               }
                
            }
                        
            
            
            String userId = intent.getStringExtra("userId");
 
            if (title != null) {
          
                Schedule aItem = new Schedule(-1,userPn,userId,title,content,startDate,endDate,importance);
 
                
             
              ArrayList outScheduleList = monthViewAdapter.getSchedule(year,month,day+firstday); //재정의흠...이게아냐....
 
                if (outScheduleList == null) {
                    outScheduleList = new ArrayList();
                }
                outScheduleList.add(aItem);
                
    
                monthViewAdapter.putSchedule(year, month, day+firstday, outScheduleList);
                monthViewAdapter.ForPrintTitle(day+firstday, title);
                
                monthViewAdapter.notifyDataSetChanged();// 그리드뷰 새로고침!  임시!!
 
                scheduleAdapter.scheduleList = outScheduleList; //리스트에 추가, 삭제와 같은 것들을 하기위함.
                scheduleAdapter.notifyDataSetChanged(); //스케줄어댑터새로고침
            }
        }
 
    }
 
 // ~ Post
    private void showResult(ScheduleList result) { 
        
        
        int schedulelist_size = result.getSchedulelist().size();
     
               
        for(int t=0; t<schedulelist_size; t++){
           
           sn=result.getSchedulelist().get(t).getSn();
           userPn=result.getSchedulelist().get(t).getUserPn();
           title=result.getSchedulelist().get(t).getTitle();
           content=result.getSchedulelist().get(t).getContent();
           startDate=result.getSchedulelist().get(t).getStartDate();
           endDate=result.getSchedulelist().get(t).getEndDate();
           importance=result.getSchedulelist().get(t).getImportance();

           
             StringTokenizer st=new StringTokenizer(startDate,"-");//가져온것을 -로 자른다
             
             int year;
             int month;
             int day;
             
             int firstday=monthViewAdapter.getfirstDay();//퍼스트데이얻어옴
             
             year=Integer.parseInt(st.nextToken());   //각각 잘라서 넣어줌.
             month=Integer.parseInt(st.nextToken())-1;//04가 4 가 되는지가 궁금..... 및 5를 4로 바꾸어준다.
          
             day=Integer.parseInt(st.nextToken())-1;
             
             Integer tempcurmonth=monthViewAdapter.getCurMonth();
             Integer undo;
             
             tempcurmonth-=month;
             /*
             if(tempcurmonth!=0)//현재달과 입력된달의 차이가 있을때만 실행
             {
             
                if(tempcurmonth>0){//현재 달에서 입력된 달을 뺏을때 양수이면 현재달보다 작은 달이 입력된것이므로(예를들면 현재가 4월인데 3월이 입력됨) 그러면 이전달로가야됨
                   undo=tempcurmonth;
                   while(tempcurmonth>0){
                      tempcurmonth--;
                      monthViewAdapter.setPreviousMonth();
                   
                   }
                   firstday=monthViewAdapter.getfirstDay();//달 정보가다를경우 이렇게 달을 옮긴후 퍼스트데이 가져온다.
                
                
                   while(undo>0){//그리고나서 원래 달로 돌려줌.
                      undo--;
                      monthViewAdapter.setNextMonth();
                   }
                   
                }
                
                else{//위에랑 반대일때
                   tempcurmonth=-tempcurmonth;
                   undo=tempcurmonth;
                   
                   while(tempcurmonth>0){
                      tempcurmonth--;
                      monthViewAdapter.setNextMonth();
                      
                   
                   }
                   firstday=monthViewAdapter.getfirstDay();//달 정보가다를경우 이렇게 달을 옮긴후 퍼스트데이 가져온다.
                
                
                   while(undo>0){//그리고나서 원래 달로 돌려줌.
                      undo--;                     
                      monthViewAdapter.setPreviousMonth();
                   }
                   
                }
                 
             }
             */
                       
  
             if (title != null && monthViewAdapter.getCurMonth()==month) {
                 //Toast toast = Toast.makeText(getBaseContext(), "result code : " + resultCode + ", time : " + time + ", message : " + content, Toast.LENGTH_LONG); 필요없으니 보류
                
  
                 Schedule aItem = new Schedule(sn,userPn,userId,title,content,startDate,endDate,importance);
  
                 
              
               ArrayList outScheduleList = monthViewAdapter.getSchedule(year,month,day+firstday); //재정의해서넣기
  
                 if (outScheduleList == null) {
                     outScheduleList = new ArrayList();
                 }
                 outScheduleList.add(aItem);

                 
                 monthViewAdapter.putSchedule(year, month, day+firstday, outScheduleList);
                 monthViewAdapter.ForPrintTitle(day+firstday, title);
                 
                 
                 monthViewAdapter.notifyDataSetChanged();// 그리드뷰 새로고침!  임시!!
  
                 scheduleAdapter.scheduleList = outScheduleList; //리스트에 추가, 삭제와 같은 것들을 하기위함.
                 scheduleAdapter.notifyDataSetChanged(); //스케줄어댑터새로고침
             }
           
             
             
             }//포문 끝 이게 좀 느리면 위에를좀 손본다 하지만 일단 그냥 써보기

     }//////여기까지 showResult

     // ***************************************
     // Private classes  아직 사용안함.
     // ***************************************
     private class PostListofSchedule extends AsyncTask<Integer, Void, ScheduleList> {  //중간건 모르겠고 첫번째것이 보내는거 , 뒤에것이 받아오는거

        protected void onPreExecute() {

        //어레이리스트 에다가 userPn으로 가져온거 다 담을려고      
           tempschedulelist = new ScheduleList();   //문법상 만들기는 했는데 보내는게 아니라서 필요없을듯

           userPn = getintent.getIntExtra("userPn", 0);
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
        	monthViewAdapter.scheduleHash=new HashMap<String,ArrayList<Schedule>>();
        	showResult(result);
        }

     }
 }