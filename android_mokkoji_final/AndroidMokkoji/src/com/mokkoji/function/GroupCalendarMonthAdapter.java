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

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

 
public class GroupCalendarMonthAdapter extends BaseAdapter {
 
    public static final String TAG = "CalendarMonthAdapter";
 
    Context mContext;
 
    public static int oddColor = Color.rgb(225, 225, 225);
    public static int headColor = Color.rgb(12, 32, 158);
 
    private int selectedPosition = -1;
 
    private MonthItem[] items;
 
    private int countColumn = 7;
 
    int mStartDay;
    int startDay;
    int curYear;
    int curMonth;
    int curDay;
 
    int firstDay;
    int lastDay;
    
    Integer userPn;
    Integer gn;
    
   
      
    Calendar mCalendar;        //월 정보를 가지고 있다.
    boolean recreateItems = false;
 
    // schedule Hash
    HashMap<String,ArrayList<Gschedule>> gscheduleHash;
 
    public GroupCalendarMonthAdapter(Context context,int userPn,int gn) {
        super();
 
        mContext = context;
        
        this.userPn=userPn;
        this.gn=gn;
 
        init();
    }
 
    public GroupCalendarMonthAdapter(Context context, AttributeSet attrs) {
        super();
 
        mContext = context;
 
        init();
    }
 
    private void init() {
        items = new MonthItem[7 * 6];
 
        mCalendar = Calendar.getInstance();
        recalculate();
        resetDayNumbers();
 
        gscheduleHash = new HashMap<String,ArrayList<Gschedule>>();
        
        //new PostListofGSchedule().execute(); //여기서  일정타이틀이 추가됨
    }
 
    public void recalculate() {
    	
    	 curDay = mCalendar.get(Calendar.DAY_OF_MONTH); // ���ó�¥�� �ޱ����� �߰�   	
 
        // set to the first day of the month
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
 
        // get week day
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
        firstDay = getFirstDay(dayOfWeek);
        Log.d("aaa", "firstDay : " + firstDay);
 
        mStartDay = mCalendar.getFirstDayOfWeek();
        curYear = mCalendar.get(Calendar.YEAR);
        curMonth = mCalendar.get(Calendar.MONTH);
        lastDay = getMonthLastDay(curYear, curMonth);
       
        
    
        Log.d(TAG, "curYear : " + curYear + ", curMonth : " + curMonth + ", lastDay : " + lastDay);
 
        int diff = mStartDay - Calendar.SUNDAY - 1;
        startDay = getFirstDayOfWeek();
        Log.d(TAG, "mStartDay : " + mStartDay + ", startDay : " + startDay);
 
    }
 
    public void setPreviousMonth() {
        mCalendar.add(Calendar.MONTH, -1);
        recalculate();
 
        resetDayNumbers();
        selectedPosition = -1;
    }
 
    public void setNextMonth() {
        mCalendar.add(Calendar.MONTH, 1);
        recalculate();
 
        resetDayNumbers();
        selectedPosition = -1;
    }
 
    public void resetDayNumbers() {
        for (int i = 0; i < 42; i++) {
            // calculate day number
            int dayNumber = (i+1) - firstDay;
            if (dayNumber < 1 || dayNumber > lastDay) {
                dayNumber = 0;
            }
 
            // save as a data item
            items[i] = new MonthItem(dayNumber);
        }
    }
 
    private int getFirstDay(int dayOfWeek) {
        int result = 0;
        if (dayOfWeek == Calendar.SUNDAY) {
            result = 0;
            curDay-=1;//���糯¥�� ���ϰ��� �̵������� ��߳��°��� �����ֱ����� ����
        } else if (dayOfWeek == Calendar.MONDAY) {
            result = 1;
            //curDay+=0;
        } else if (dayOfWeek == Calendar.TUESDAY) {
            result = 2;
            curDay+=1;
        } else if (dayOfWeek == Calendar.WEDNESDAY) {
            result = 3;
            curDay+=2;
        } else if (dayOfWeek == Calendar.THURSDAY) {
            result = 4;
            curDay+=3;
        } else if (dayOfWeek == Calendar.FRIDAY) {
            result = 5;
            curDay+=4;
        } else if (dayOfWeek == Calendar.SATURDAY) {
            result = 6;
            curDay+=5;
        }
 
        return result;
    }
 
 
    public int getCurYear() {
        return curYear;
    }
 
    public int getCurMonth() {
        return curMonth;
    }
 
    public int getCurDay() {
    	return curDay;
    }
 
    public int getNumColumns() {
        return 7;
    }
 
    public int getCount() {
        return 7 * 6;
    }
 
    public Object getItem(int position) {
        return items[position];
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView(" + position + ") called.");
 
        MonthItemView itemView;
        if (convertView == null) {
            itemView = new MonthItemView(mContext);
        } else {
            itemView = (MonthItemView) convertView;
        }
 
        // create a params
        GridView.LayoutParams params = new GridView.LayoutParams(
                GridView.LayoutParams.FILL_PARENT,135);//ĭ�� ����
 
        // calculate row and column
        int rowIndex = position / countColumn;
        int columnIndex = position % countColumn;
 
        Log.d(TAG, "Index : " + rowIndex + ", " + columnIndex);
 
        // set item data and properties
        itemView.setItem(items[position]);
        itemView.setLayoutParams(params);
        itemView.setPadding(2, 2, 2, 2);
 
        // set properties
        itemView.setGravity(Gravity.LEFT);
 
        if (columnIndex == 0) {
            itemView.setTextColor(Color.RED);
        } else {
            itemView.setTextColor(Color.BLACK);
        }
 
        // set background color
        ArrayList outList = getGschedule(position);
        boolean scheduleExist = false;
        if (outList != null && outList.size() > 0) {
            scheduleExist = true;
        }
 
        Log.i("Now position", Integer.toBinaryString(position));
        Log.i("curday",Integer.toString(curDay));
      
        
        
        
        	
        
        if (position == getSelectedPosition()) {
            itemView.setBackgroundColor(Color.YELLOW);
        } else {
        	
        	if(position == /*curDay*/Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+firstDay-1 && curMonth==Calendar.getInstance().get(Calendar.MONTH)){
        		itemView.setBackgroundColor(Color.GREEN);//���ó�¥�� �ϴû����� ����
        	}
        	
        	else if (scheduleExist) {
                itemView.setBackgroundColor(Color.WHITE);
            } else {
                itemView.setBackgroundColor(Color.WHITE);
            }
        }
 
 
        return itemView;
    }
 
 
    /**
     * Get first day of week as android.text.format.Time constant.
     * @return the first day of week in android.text.format.Time
     */
    public static int getFirstDayOfWeek() {
        int startDay = Calendar.getInstance().getFirstDayOfWeek();
        if (startDay == Calendar.SATURDAY) {
            return Time.SATURDAY;
        } else if (startDay == Calendar.MONDAY) {
            return Time.MONDAY;
        } else {
            return Time.SUNDAY;
        }
    }
 
 
    /**
     * get day count for each month
     *
     * @param year
     * @param month
     * @return
     */
    private int getMonthLastDay(int year, int month){
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return (31);
 
            case 3:
            case 5:
            case 8:
            case 10:
                return (30);
 
            default:
                if(((year%4==0)&&(year%100!=0)) || (year%400==0) ) {
                    return (29);   // 2�� ������
                } else {
                    return (28);
                }
        }
    }
 
    /**
     * set selected row
     *
     * @param selectedRow
     */
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
 
    /**
     * get selected row
     *
     * @return
     */
    public int getSelectedPosition() {
        return selectedPosition;
    }
 
 
    /**
     * get schedule
     *
     * @param year
     * @param month
     * @param position
     * @return
     */
    public ArrayList<Gschedule> getGschedule(int year, int month, int position) {
        String keyStr = year + "-" + month + "-" + position;
        ArrayList<Gschedule> outList = gscheduleHash.get(keyStr);
 
        return outList;
    }
 
    public ArrayList<Gschedule> getGschedule(int position) {
        String keyStr = curYear + "-" + curMonth + "-" + position;
        ArrayList<Gschedule> outList = gscheduleHash.get(keyStr);
 
        return outList;
    }
 
    public void putGschedule(int year, int month, int position, ArrayList<Gschedule> aList) {//
        String keyStr = year + "-" + month + "-" + position;
        gscheduleHash.put(keyStr, aList);
    }
 
    public void putGschedule(int position, ArrayList<Gschedule> aList) { 
        String keyStr = curYear + "-" + curMonth + "-" + position;
        Log.i("test",keyStr );
        gscheduleHash.put(keyStr, aList);
    }
    
    public void putGschedule(String keyStr,ArrayList<Gschedule> aList){////////////임시로만든거
    	Log.i("test",keyStr );
    	gscheduleHash.put(keyStr, aList);
    }
    
    public int getfirstDay(){//임시로만듬
    	return this.firstDay;
    }
        
    
private void showResult(GscheduleList result) { 
   		
   		int schedulelist_size = result.getGschedulelist().size();
   		
   	 	
 	 		
  		for(int t=0; t<schedulelist_size; t++){
  			
  			//sn=result.getSchedulelist().get(t).getSn();
  			userPn=result.getGschedulelist().get(t).getUser_pn();
  			String title=result.getGschedulelist().get(t).getTitle();
  			//content=result.getSchedulelist().get(t).getContent();
  			String startDate=result.getGschedulelist().get(t).getStartDate();
  			//endDate=result.getSchedulelist().get(t).getEndDate();
  			//importance=result.getSchedulelist().get(t).getImportance();
  			
  			Log.i("test", title+" "+startDate);

  			
             StringTokenizer st=new StringTokenizer(startDate,"-");//가져온것을 -로 자른다
             
             int year;
             int month;
             int day;
             
             int firstday=getfirstDay();//퍼스트데이얻어옴
             
             year=Integer.parseInt(st.nextToken());   //각각 잘라서 넣어줌.
             month=Integer.parseInt(st.nextToken())-1;//04가 4 가 되는지가 궁금..... 및 5를 4로 바꾸어준다.
          
             day=Integer.parseInt(st.nextToken())-1;
             
             Integer tempcurmonth=curMonth;
             Integer undo;
             
             tempcurmonth-=month;
             
            /* if(tempcurmonth!=0)//현재달과 입력된달의 차이가 있을때만 실행
             {
             
             	if(tempcurmonth>0){//현재 달에서 입력된 달을 뺏을때 양수이면 현재달보다 작은 달이 입력된것이므로(예를들면 현재가 4월인데 3월이 입력됨) 그러면 이전달로가야됨
             		undo=tempcurmonth;
             		while(tempcurmonth>0){
             			tempcurmonth--;
             			setPreviousMonth();
             		
             		}
             		firstday=getfirstDay();//달 정보가다를경우 이렇게 달을 옮긴후 퍼스트데이 가져온다.
             	
             	
             		while(undo>0){//그리고나서 원래 달로 돌려줌.
             			undo--;
             			setNextMonth();
             		}
             		
             	}
             	
             	else{//위에랑 반대일때
             		tempcurmonth=-tempcurmonth;
             		undo=tempcurmonth;
             		
             		while(tempcurmonth>0){
             			tempcurmonth--;
             			setNextMonth();
             			
             		
             		}
             		firstday=getfirstDay();//달 정보가다를경우 이렇게 달을 옮긴후 퍼스트데이 가져온다.
             	
             	
             		while(undo>0){//그리고나서 원래 달로 돌려줌.
             			undo--;            			
             			setPreviousMonth();
             		}
             		
             	}
                 
             }*/
             if(curMonth==month)
             items[firstday+day].addSchedule(title);
             
                       
           			 	        
  	        
  	        }
   		
   	 		  		
   	        
   	      

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
 				final String url = mContext.getString(R.string.base_uri)
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
 	
 	public void post(){
 		new PostListofGSchedule().execute(); //여기서  일정타이틀이 추가됨
 	}
 	
 	public void ForPrintTitle(int position,String schedule){
   	 
   	 items[position].addSchedule(schedule);
    }

 
}