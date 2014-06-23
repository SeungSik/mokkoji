package com.mokkoji.function;

import com.example.pro3.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScheduleListItemView extends LinearLayout{
	
	private Context mContext;
	 
	private TextView startdateText;
    private TextView enddateText;
    private TextView titleText;
    private TextView importanceText;
    private LinearLayout linearLayout;
 
    public ScheduleListItemView(Context context) {
        super(context);
 
        mContext = context; 
 
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.schedule_listitem, this, true);
 
        startdateText = (TextView) findViewById(R.id.startdateText);
        enddateText = (TextView) findViewById(R.id.enddateText);
        titleText = (TextView) findViewById(R.id.titleText);
        importanceText = (TextView) findViewById(R.id.importanceText);
        linearLayout = (LinearLayout)findViewById(R.id.layout);
        //setBackgroundColor(Color.GREEN);//�־ȵǳİ�
        //linear.setBackgroundColor(Color.GREEN);
 
    }
 

    
    public void setImportanceText(int input){
    	switch(input){
    	case 1 :  this.importanceText.setText("★");
    	break;
    	case 2 : this.importanceText.setText("★★");
    	break;
    	case 3 : this.importanceText.setText("★★★");
    	break;
    	default :
    		this.importanceText.setText("☆");
    	
    	}    	
    }
	public void setStartdateText(String input) {
		this.startdateText.setText(input);
	}

	public void setEnddateText(String input) {
		this.enddateText.setText(input);
	}

    public void setMessage(String messageStr) {
        titleText.setText(messageStr);
    }

	public void setLinear() {
		linearLayout.setBackgroundColor(Color.YELLOW);//여기 선택했을때 색깔 노란색으로 바꿔주는 부분.
	}
	public LinearLayout getLinear(){
		
		return this.linearLayout;
	}
	public void setLineardefault() {
		linearLayout.setBackgroundColor(Color.WHITE);
	}
   
}
