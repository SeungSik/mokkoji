package com.mokkoji.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mokkoji.connect.*;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ScheduleListAdapter extends BaseAdapter{

	private Context mContext;
	private int selectedPosition = -1;
	 
    // schedule List
    ArrayList<Schedule> scheduleList;
 
    public ScheduleListAdapter(Context context) {
        mContext = context;
 
        scheduleList = new ArrayList<Schedule>();
    }
 
    public void clear() {
        scheduleList.clear();
    }
 
    public void addItem(Schedule item) {
        scheduleList.add(item);
    }
 
    public void removeItem(Schedule item) {

        scheduleList.remove(item);
    }
 
    public void addAll(ArrayList<Schedule> items) {
        scheduleList.addAll(items);
    }
 
    public int getCount() {
        return scheduleList.size();
    }
 
    public Object getItem(int position) {
        return scheduleList.get(position);
    }
 
    public boolean isSelectable(int position) {
        return true;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ScheduleListItemView itemView = null;
       
        if (convertView == null) {
            itemView = new ScheduleListItemView(mContext);
        } else {
            itemView = (ScheduleListItemView) convertView;
        }
        Log.i("1", Integer.toBinaryString(position));
        Schedule curItem = (Schedule) scheduleList.get(position);
      
        itemView.setStartdateText(curItem.getStartDate());
        itemView.setEnddateText(curItem.getEndDate());        
        itemView.setMessage(curItem.getTitle());//타이틀이랑 컨텐츠랑 헷갈리지 말것.
        itemView.setImportanceText(curItem.getImportance());

        
        if(position == getSelectedPosition()){
        	 itemView.setLinear();//���� ���ִºκ�
        	
        }
        else
        {
        	itemView.setLineardefault();
        }
        
        return itemView;
    }

	public void setSelectedPosition(int selectedPosition) {
		this.selectedPosition = selectedPosition;
		
	}
	public int getSelectedPosition() {
        return selectedPosition;
    }
 
    
    
}
