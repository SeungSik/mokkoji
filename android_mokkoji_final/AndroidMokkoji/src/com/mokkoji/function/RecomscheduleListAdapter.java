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

public class RecomscheduleListAdapter extends BaseAdapter{

	private Context mContext;
	private int selectedPosition = -1;
	 
    // schedule List
    ArrayList<Recomschedule> scheduleList;
 
    public RecomscheduleListAdapter(Context context) {
        mContext = context;
 
        scheduleList = new ArrayList<Recomschedule>();
    }
 
    public void clear() {
        scheduleList.clear();
    }
 
    public void addItem(Recomschedule item) {
        scheduleList.add(item);
    }
 
    public void removeItem(Recomschedule item) {

        scheduleList.remove(item);
    }
 
    public void addAll(ArrayList<Recomschedule> items) {
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
        Recomschedule curItem = (Recomschedule) scheduleList.get(position);
      
        itemView.setStartdateText(curItem.getStartdate());
        itemView.setEnddateText(curItem.getEnddate());        
        itemView.setMessage(curItem.getTypename());//타이틀이랑 컨텐츠랑 헷갈리지 말것.
        //itemView.setImportanceText(curItem.getImportance());

        
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
