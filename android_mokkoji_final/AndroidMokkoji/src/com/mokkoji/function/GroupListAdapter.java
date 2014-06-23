package com.mokkoji.function;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro3.R;
import com.mokkoji.connect.Group;

public class GroupListAdapter extends BaseAdapter{
	
	
	private LayoutInflater inflater;
	private ArrayList<Group> groupList;
	private Context mContext;
	private ViewHolder viewHolder;
	

	public GroupListAdapter(Context c, ArrayList<Group> group){
		this.mContext = c;
		this.inflater = LayoutInflater.from(c);
		this.groupList = new ArrayList<Group>();
		groupList = group;
		
	}
	
	@Override
	public int getCount(){
		return groupList.size();
	}
	@Override
	public Group getItem(int position){
		return groupList.get(position);
	}
	@Override
	public long getItemId(int position){
		return position;
	}
	
	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		
		View v = convertview;
		
		if(v == null){
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.grouplist_row, null);
			viewHolder.groprow= (TextView)v.findViewById(R.id.gl_title);
			

			v.setTag(viewHolder);
			
		}else {
			viewHolder = (ViewHolder)v.getTag();
		}
		
		viewHolder.groprow.setText(getItem(position).getGname());
		
		
		return v;
	}
	
	public void setArrayList(ArrayList<Group> group){
		this.groupList = group;
	}
	
	public ArrayList<Group> getArrayList(){
		return groupList;
	}


	
	class ViewHolder{
		
		public TextView groprow = null;
		
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		free();
		super.finalize();
	}
	
	private void free(){
		inflater = null;
		groupList = null;
		viewHolder = null;
		mContext = null;
	}
	

}