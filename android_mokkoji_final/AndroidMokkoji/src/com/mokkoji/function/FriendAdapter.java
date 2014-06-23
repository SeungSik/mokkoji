package com.mokkoji.function;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pro3.R;
import com.mokkoji.connect.Member;


public class FriendAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private ArrayList<Member> friendList;
	private Context mContext;
	private ViewHolder viewHolder;
	

	public FriendAdapter (Context c, ArrayList<Member> group){
		this.mContext = c;
		this.inflater = LayoutInflater.from(c);
		this.friendList = new ArrayList<Member>();
		friendList = group;
		
	}
	@Override
	public int getCount(){
		return friendList.size();
	}
	@Override
	public Member getItem(int position){
		return friendList.get(position);
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
			v = inflater.inflate(R.layout.memgrouprow, null);
			viewHolder.friendrow= (TextView)v.findViewById(R.id.friend);
			

			v.setTag(viewHolder);
			
		}else {
			viewHolder = (ViewHolder)v.getTag();
		}
		
		viewHolder.friendrow.setText(friendList.get(position).getId());
		
		
		return v;
	}
	
	public void setArrayList(ArrayList<Member> group){
		this.friendList = group;
	}
	
	public ArrayList<Member> getArrayList(){
		return friendList;
	}


	
	class ViewHolder{
		
		public TextView friendrow = null;
		
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		free();
		super.finalize();
	}
	
	private void free(){
		inflater = null;
		friendList = null;
		viewHolder = null;
		mContext = null;
	}
}
