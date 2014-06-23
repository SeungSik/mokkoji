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

import android.app.Activity;
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

import com.example.pro3.R;
import com.mokkoji.connect.Gschedule;
import com.mokkoji.connect.GscheduleList;
import com.mokkoji.connect.Schedule;


public class GroupCalendarMonthViewActivity extends Activity {
 
    GridView monthView;
    GroupCalendarMonthAdapter monthViewAdapter;

    TextView monthText;
 
    int curYear;
    int curMonth;
 
    int curPosition;
    EditText gscheduleInput;
    Button saveButton;
    Button mine;
    Button mygroup;
    String gname;
 
    ListView gscheduleList;
    TempGscheduleListAdapter gscheduleAdapter;
    ArrayList outScheduleList;
    
    Intent intentToScheduleInput;//
    Intent intent;
    
    String startDate ;
    String endDate ;
    String content ;
    String title ;
    Integer importance;
    Integer userPn ;
      
      //그룹 스케줄 관리를 위함 
     Integer gn;
     Integer sn ;
   
      
      String userId ;

    //////////최초 스케쥴 표시를 ㅜ이해 추가한 부분
 
    public static final int REQUEST_CODE_SCHEDULE_INPUT_G= 1020;
    public static final int REQUEST_CODE_SCHEDULE_SEARCH = 1007;
    public static final int REQUEST_CODE_SCHEDULE_TODAY = 1004;
    public static final int REQUEST_CODE_SCHEDULE_WEEK = 1003;
    public static final int REQUEST_CODE_SCHEDULE_GROUP = 1006;
    public static final int REQUEST_CODE_SCHDULE_LIST = 1005;
    public static final int REQUEST_CODE_SCHEDULE_GSCHEDULE = 1021;
    public static final int REQUEST_CODE_CALENDAR_SHOW= 1080;
    
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_main);
        intent = getIntent();
        gname = new String();
        
        userPn = intent.getIntExtra("userPn", 0);
        gn = intent.getIntExtra("gn", 0);
 
        monthView = (GridView) findViewById(R.id.monthView);
        monthViewAdapter = new GroupCalendarMonthAdapter(this,userPn,gn);
        monthView.setAdapter(monthViewAdapter);
 
        
        
        gname = intent.getExtras().getString("gname");
        
        mygroup = (Button) findViewById(R.id.mygroup);
        mygroup.setText(gname);
        
        intentToScheduleInput = new Intent(this, GscheduleInputActivity.class);
        
        new PostListofGSchedule().execute(); //포스트 해주기.
        
        
        // set listener
        monthView.setOnItemClickListener(new OnItemClickListener() {//그리드뷰에서 날짜 한개를 클릭 했을때
            public void onItemClick(AdapterView parent, View view, int position, long id) { //요 포지션을 갖다가 쓰면 몇일인지 알 수 있다.
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                int day = curItem.getDay();//이 포지션에 이게 몇일인지 가져온다.
                

                monthViewAdapter.setSelectedPosition(position); //선택했을떄 노란색으로 바꿔주고
                monthViewAdapter.notifyDataSetChanged();// 새로고침!
 
                // set schedule to the TextView
                curPosition = position;  //선택한 포지션을 현재 포지션으로 집어넣는다.
 
                outScheduleList = monthViewAdapter.getGschedule(position);//
                if (outScheduleList == null) {
                    outScheduleList = new ArrayList<Schedule>();
                }
                gscheduleAdapter.scheduleList = outScheduleList;
 
                gscheduleAdapter.notifyDataSetChanged();
            }
        });
 
        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();
 
        Button monthPrevious = (Button) findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setPreviousMonth();
                //monthViewAdapter.post();
                
                new PostListofGSchedule().execute(); //포스트 해주기.
                monthViewAdapter.notifyDataSetChanged();
 
                setMonthText();
            }
        });
 
        Button monthNext = (Button) findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setNextMonth();
                //monthViewAdapter.post();
                new PostListofGSchedule().execute(); //포스트 해주기.
                monthViewAdapter.notifyDataSetChanged();
 
                setMonthText();
            }
        });
 
 
        curPosition = -1;
 
        gscheduleList = (ListView)findViewById(R.id.scheduleList);
        gscheduleAdapter = new TempGscheduleListAdapter(this);
        gscheduleList.setAdapter(gscheduleAdapter);
 
 
        gscheduleList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Gschedule curItem = (Gschedule) gscheduleAdapter.getItem(position);
      
                gscheduleAdapter.setSelectedPosition(position);
                gscheduleAdapter.notifyDataSetChanged();
 
                // set schedule to the TextView
                curPosition = position;
                
                Intent showschedule = new Intent(getApplicationContext(),ScheduleShowActivity.class);
                showschedule.putExtra("gn", curItem.getGn());
                showschedule.putExtra("userPn", userPn);
                showschedule.putExtra("sn", curItem.getSn());
                showschedule.putExtra("title",curItem.getTitle());
                showschedule.putExtra("content", curItem.getContent());
                showschedule.putExtra("startdate", curItem.getStartDate());
                showschedule.putExtra("enddate", curItem.getEndDate());
                showschedule.putExtra("importance", curItem.getImportance());
                
                startActivityForResult(showschedule,REQUEST_CODE_CALENDAR_SHOW);
                
                
            }
        });
       
        
        mine = (Button) findViewById(R.id.mine);
        mine.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	finish();
            	
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
        int newGschedule =8;
        
        menu.clear();
 
        menu.add(1, addSchedule, Menu.NONE, "일정추가");
        menu.add(1, deleteAll,Menu.NONE, "전체 삭제");
        menu.add(1, searchSchedule,Menu.NONE,"일정 검색");
        menu.add(2, newGschedule, Menu.NONE, "그룹 일정");    //새로 등록된 그룹 일정을 보게 해줌!
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
            	gscheduleAdapter.clear();
            	 gscheduleAdapter.notifyDataSetChanged();
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
            case 8:
            	showGroupSchedule();
            	return true;
            	
            default:
                break;
         }
 
         return false;
    }
 
    private void showScheduleInput() {
    	
    	 Integer userPn=intent.getIntExtra("userPn", 0);
         Integer gn=intent.getIntExtra("gn",0);
    	
         intentToScheduleInput.putExtra("userPn", userPn);
         
         intentToScheduleInput.putExtra("gn", gn);
         
         startActivityForResult(intentToScheduleInput, REQUEST_CODE_SCHEDULE_INPUT_G);

    }
    private void showTodaySchedule() {
    	Intent intent = new Intent(getApplicationContext(), GscheduleTodayActivity.class);
        Calendar mCalendar=Calendar.getInstance();
        intent.putExtra("firstDay", monthViewAdapter.firstDay);
    	intent.putExtra("curDay", mCalendar.get(Calendar.DAY_OF_MONTH));//+1�댁쨾�쇰릺�붿����붾쾭源낆쑝濡��뺤씤
    	intent.putExtra("curMonth",mCalendar.get(Calendar.MONTH));
    	intent.putExtra("curYear", monthViewAdapter.curYear);
    	intent.putExtra("userPn", userPn);
    	intent.putExtra("gn", gn);
     
        startActivityForResult(intent, REQUEST_CODE_SCHEDULE_TODAY);
    }
    private void showSearchSchedule(){
    	Intent intent = new Intent(getApplicationContext(), ScheduleLookActivity.class);
    	intent.putExtra("userPn", userPn);
    	intent.putExtra("gn", gn);
    	startActivityForResult(intent,REQUEST_CODE_SCHEDULE_SEARCH);
    	
    }
    private void showWeekSchedule(){
    	Intent intent = new Intent(this, GscheduleWeekViewActivity.class);
    	Calendar mCalendar=Calendar.getInstance();
    	intent.putExtra("firstDay", monthViewAdapter.firstDay);
    	intent.putExtra("curDay", mCalendar.get(Calendar.DAY_OF_MONTH));//+1�댁쨾�쇰릺�붿����붾쾭源낆쑝濡��뺤씤
    	intent.putExtra("curMonth",mCalendar.get(Calendar.MONTH));
    	intent.putExtra("curYear", monthViewAdapter.curYear);
    	intent.putExtra("userPn", userPn);
    	intent.putExtra("gn",gn);
    	startActivityForResult(intent,REQUEST_CODE_SCHEDULE_WEEK);
    }
    private void showGroupAdmin(){
    	Intent intent = new Intent(this, GroupAdminActivity.class);
    	intent.putExtra("userPn", userPn);
    	intent.putExtra("gn", gn);
    	startActivityForResult(intent,REQUEST_CODE_SCHEDULE_GROUP);
    }
    private void showGroupList(){
    	Intent intent = new Intent(this, GroupViewActivity.class );
    	intent.putExtra("userPn", userPn);
    	intent.putExtra("gn", gn);
    	startActivityForResult(intent,REQUEST_CODE_SCHDULE_LIST);
    }
    private void showGroupSchedule(){
    	
    	Intent intent = new Intent(this, GscheduleAcceptActivity.class );
    	intent.putExtra("userPn", userPn);
    	intent.putExtra("gn", gn);
    	startActivityForResult(intent,REQUEST_CODE_SCHEDULE_GSCHEDULE);
    }
    
 
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
 
        /*if (requestCode == REQUEST_CODE_SCHEDULE_INPUT_G) {
         
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
          
 
              Gschedule aItem = new Gschedule(-1,-1,userPn,title,content,startDate,endDate,importance);
 
                
             
              ArrayList outScheduleList = monthViewAdapter.getGschedule(year,month,day+firstday); //재정의흠...이게아냐....
 
                if (outScheduleList == null) {
                    outScheduleList = new ArrayList();
                }
                outScheduleList.add(aItem);
          
                
                monthViewAdapter.putGschedule(year, month, day+firstday, outScheduleList);
                
                monthViewAdapter.notifyDataSetChanged();// 그리드뷰 새로고침!  임시!!
 
                gscheduleAdapter.scheduleList = outScheduleList; //리스트에 추가, 삭제와 같은 것들을 하기위함.
                gscheduleAdapter.notifyDataSetChanged(); //스케줄어댑터새로고침
            }
		 */
    
    }
 
 // ~ Post
 	private void showResult(GscheduleList result) { 
 		
 	 		
 		int gschedulelist_size = result.getGschedulelist().size();
 		
 		Log.i("test", "으아"+gschedulelist_size+"으어");

 		 		
 		for(int t=0; t<gschedulelist_size; t++){
 			
 			sn=result.getGschedulelist().get(t).getSn();
 			gn=result.getGschedulelist().get(t).getGn();
 			userPn = result.getGschedulelist().get(t).getUser_pn();
 			title=result.getGschedulelist().get(t).getTitle();
 			content=result.getGschedulelist().get(t).getContent();
 			startDate=result.getGschedulelist().get(t).getStartDate();
 			endDate=result.getGschedulelist().get(t).getEndDate();
 			importance=result.getGschedulelist().get(t).getImportance();
 			
 			
 			//schedulelist를 받아오고 거기 인덱스별로
 		
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
            
            /*if(tempcurmonth!=0)//현재달과 입력된달의 차이가 있을때만 실행
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
                
            }*/
     
            if (title != null && monthViewAdapter.getCurMonth()==month) {

              Gschedule aItem = new Gschedule(sn,gn,userPn,title,content,startDate,endDate,importance);
 
              ArrayList outScheduleList = monthViewAdapter.getGschedule(year,month,day+firstday); //재정의해서넣기
 
                if (outScheduleList == null) {
                    outScheduleList = new ArrayList();
                }
                outScheduleList.add(aItem);
                

                monthViewAdapter.putGschedule(year, month, day+firstday, outScheduleList);
 
                monthViewAdapter.ForPrintTitle(day+firstday, title);
                monthViewAdapter.notifyDataSetChanged();// 그리드뷰 새로고침!  임시!!
 
                gscheduleAdapter.scheduleList = outScheduleList; //리스트에 추가, 삭제와 같은 것들을 하기위함.
                gscheduleAdapter.notifyDataSetChanged(); //스케줄어댑터새로고침
            }
 
 	      }//포문 끝 이게 좀 느리면 위에를좀 손본다 하지만 일단 그냥 써보기
 		

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
 			monthViewAdapter.gscheduleHash=new HashMap<String,ArrayList<Gschedule>>();
 			showResult(result);
 		}

 	}
}