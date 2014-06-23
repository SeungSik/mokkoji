package com.mokkoji.function;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.example.pro3.R;
import com.mokkoji.connect.Gschedule;
import com.mokkoji.connect.Schedule;

public class GscheduleInputActivity extends Activity {

	EditText titleInput;
	EditText messageInput;
    Button timeButton01;
    Button timeButton02;
    Button dateButton01;
    Button dateButton02;
    Button closeButton;
    Button saveButton;
    RadioButton radiobutton0;
    RadioButton radiobutton1;
    RadioButton radiobutton2;

    //sn빼고 다 있음
    Integer userPn;
    //group 스케줄이니까 gn 받아옴 
    Integer gn;
    
    String userId;
    String titleStr;
    String contentsStr;
    String startStr;
    String endStr;
    Integer importance;  //다른 메소드에서 사용하기위해 멤버변수로 선언함.
    Integer curDayPosition;
 
    public static final int DIALOG_TIME01 = 1101;
    public static final int DIALOG_TIME02 = 1102;
    public static final int DIALOG_DATE01 = 1103;
    public static final int DIALOG_DATE02 = 1104;
   
 
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH시 mm분");
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 
    Date selectedDate;
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_input);
 
        setTitle("일정 추가");
        
        importance=1;
 
        titleInput = (EditText) findViewById(R.id.titleInput);
        messageInput = (EditText) findViewById(R.id.messageInput);
 
        timeButton01 = (Button) findViewById(R.id.timeButton01);
        timeButton01.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_TIME01);
            }
        });
        timeButton02 = (Button) findViewById(R.id.timeButton02);
        timeButton02.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_TIME02);
            }
        });
        
        dateButton01 = (Button) findViewById(R.id.dateButton01);
        dateButton01.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_DATE01);
            }
        });
        
        dateButton02 = (Button) findViewById(R.id.dateButton02);
        dateButton02.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_DATE02);
            }
        });
        
        radiobutton0 = (RadioButton) findViewById(R.id.radio0);
        radiobutton0.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                importance=1;
            }
        });
        radiobutton1 = (RadioButton) findViewById(R.id.radio1);
        radiobutton1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                importance=2;
            }
        });
        radiobutton2 = (RadioButton) findViewById(R.id.radio2);
        radiobutton2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                importance=3;
            }
        });
        
 
        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            	titleStr = titleInput.getText().toString();
                contentsStr = messageInput.getText().toString();
                startStr = dateButton01.getText().toString()+"-"+timeButton01.getText().toString();
                endStr = dateButton02.getText().toString()+"-"+timeButton02.getText().toString();
                //importance = 0;//디폴트갑으로 일단 0 해둠. 아직 라디오버튼 구현을 안했기 때문에...
                
                String startdateInfo=dateButton01.getText().toString();//이걸가지고 그냥 캘린더 몬스 뷰 어댑터에 쳐넣으면 바로 등록된다.
                
               
                Intent intent = new Intent();
                
                intent.putExtra("userPn", userPn);
                intent.putExtra("gn", gn);       
                intent.putExtra("startDate", startStr);
                intent.putExtra("endDate", endStr);
                intent.putExtra("contents", contentsStr);
                intent.putExtra("title", titleStr);
                intent.putExtra("importance", importance);
                
                intent.putExtra("startdateInfo",startdateInfo);
       
                
                new PostGscheduleTask().execute();
 
                setResult(RESULT_OK, intent);
 
                finish();
            }
        });
        
        Button closeButton = (Button) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent resultIntent = new Intent();
        		setResult(RESULT_OK, resultIntent);
        		finish();
        	}
        	
        	
        });
 
 
        // set selected date using current date
        Date curDate = new Date();
        setSelectedDate01(curDate);
        setSelectedDate02(curDate);
        
    }
    
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_TIME01:
                String timeStr01 = timeButton01.getText().toString();
 
                Calendar calendar01 = Calendar.getInstance();
                Date curDate01 = new Date();
                try {
                    curDate01 = timeFormat.parse(timeStr01);
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
 
                calendar01.setTime(curDate01);
 
                int curHour01 = calendar01.get(Calendar.HOUR_OF_DAY);
                int curMinute01 = calendar01.get(Calendar.MINUTE);
 
                return new TimePickerDialog(this,  timeSetListener01,  curHour01, curMinute01, false);
                
                
            case DIALOG_TIME02:
            	String timeStr02 = timeButton02.getText().toString();
            	 
                Calendar calendar02 = Calendar.getInstance();
                Date curDate02 = new Date();
                try {
                    curDate02 = timeFormat.parse(timeStr02);
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
 
                calendar02.setTime(curDate02);
 
                int curHour02 = calendar02.get(Calendar.HOUR_OF_DAY);
                int curMinute02 = calendar02.get(Calendar.MINUTE);
 
                return new TimePickerDialog(this,  timeSetListener02,  curHour02, curMinute02, false);
                
            case DIALOG_DATE01:
                String dateStr01 = dateButton01.getText().toString();
 
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
 
                return new DatePickerDialog(this,  dateSetListener01,  year01, month01,day01);

            	
            case DIALOG_DATE02:
                String dateStr02 = dateButton02.getText().toString();
 
                Calendar date_calendar02 = Calendar.getInstance();
                Date date_curDate02 = new Date();
                try {
                    date_curDate02 = timeFormat.parse(dateStr02);
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
 
                date_calendar02.setTime(date_curDate02);
                
                int year02 = date_calendar02.get(Calendar.YEAR);
                int month02 = date_calendar02.get(Calendar.MONTH);
                int day02 = date_calendar02.get(Calendar.DAY_OF_MONTH);
 
                return new DatePickerDialog(this,  dateSetListener02,  year02, month02,day02); 
                
             default:
                break;
 
        }
 
        return null;
    }
 
    private TimePickerDialog.OnTimeSetListener timeSetListener01 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selectedCalendar.set(Calendar.MINUTE, minute);
 
            Date curDate = selectedCalendar.getTime();
            setSelectedDate01(curDate);
        }
    };
 
    private void setSelectedDate01(Date curDate) {
        selectedDate = curDate;
 
        String selectedTimeStr = timeFormat.format(curDate);
        timeButton01.setText(selectedTimeStr);
       
    }
    
    private TimePickerDialog.OnTimeSetListener timeSetListener02 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selectedCalendar.set(Calendar.MINUTE, minute);
 
            Date curDate = selectedCalendar.getTime();
            setSelectedDate02(curDate);
        }
    };
    
    
    private DatePickerDialog.OnDateSetListener dateSetListener01 =  new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			
			Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(Calendar.YEAR, year);
            selectedCalendar.set(Calendar.MONTH, monthOfYear);
            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
 
            Date curDate = selectedCalendar.getTime();
            date_setSelectedDate01(curDate);
			
			
		}
	};
	
	
	 private DatePickerDialog.OnDateSetListener dateSetListener02 =  new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				
				Calendar selectedCalendar = Calendar.getInstance();
	            selectedCalendar.set(Calendar.YEAR, year);
	            selectedCalendar.set(Calendar.MONTH, monthOfYear);
	            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	 
	            Date curDate = selectedCalendar.getTime();
	            date_setSelectedDate02(curDate);
				
				
			}
		};
    
    
    
 
    private void setSelectedDate02(Date curDate) {
        selectedDate = curDate;
 
        String selectedTimeStr = timeFormat.format(curDate);
        timeButton02.setText(selectedTimeStr);
       
    }
    
    private void date_setSelectedDate01(Date curDate) {
        selectedDate = curDate;
 
        String selectedTimeStr = dateFormat.format(curDate);
        dateButton01.setText(selectedTimeStr);
       
    }
    
    private void date_setSelectedDate02(Date curDate) {
        selectedDate = curDate;
 
        String selectedTimeStr = dateFormat.format(curDate);
        dateButton02.setText(selectedTimeStr);
       
    }
 

 // ***************************************
 	// Private classes
 	// ***************************************
 	private class PostGscheduleTask extends AsyncTask<Void, Void, Gschedule> {

 		private Gschedule gschedule;

 		@Override
 		protected void onPreExecute() {

 			gschedule = new Gschedule();

 			
 			Intent getintent = getIntent();
 				
 			userPn = getintent.getIntExtra("userPn", 0);//받아온 userPn	
 			gn = getintent.getIntExtra("gn", 0);
 
 			
 		   
 			gschedule.setUser_pn(userPn);
 			gschedule.setGn(gn);
			gschedule.setTitle(titleStr);
 			gschedule.setContent(contentsStr);
 			gschedule.setStartDate(startStr);
 			gschedule.setEndDate(endStr);
 			gschedule.setImportance(importance);
 		
 		}
 		@Override
 		protected Gschedule doInBackground(Void... params) {
 			try {
 				
 				// The URL for making the POST request
 				final String url = getString(R.string.base_uri)+"/mokkoji/postGscheduleToSql.json";

 				HttpHeaders requestHeaders = new HttpHeaders();

 				// Sending multipart/form-data
 				requestHeaders.setContentType(MediaType.APPLICATION_JSON);

 				// Populate the MultiValueMap being serialized and headers in an
 				// HttpEntity object to use for the request
 				HttpEntity<Gschedule> requestEntity = new HttpEntity<Gschedule>(gschedule, requestHeaders);

 				// Create a new RestTemplate instance
 				RestTemplate restTemplate = new RestTemplate(true);
 				restTemplate.getMessageConverters().add(
 						new StringHttpMessageConverter());
 				restTemplate.getMessageConverters().add(
 						new MappingJackson2HttpMessageConverter());

 				// Make the network request, posting the message, expecting a
 				// String in response from the server
 				ResponseEntity<Gschedule> response = restTemplate.exchange(url,
 						HttpMethod.POST, requestEntity, Gschedule.class);

 				// Return the response body to display to the user
 				return response.getBody();
 			} catch (Exception e) {
 				//Log.e(TAG, e.getMessage(), e);
 			}

 			return null;
 		}

 		@Override
 		protected void onPostExecute(Gschedule result) {
 			//showResult(result);일단 할 필요가 없을듯.
 		}

 	}
  
}