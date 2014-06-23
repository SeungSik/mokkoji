package com.mokkoji.function;

import java.util.ArrayList;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro3.R;
import com.mokkoji.connect.Group;
import com.mokkoji.connect.GroupManagement;
import com.mokkoji.connect.Grouplist;

public class GroupModifyListAdapter extends BaseAdapter{
	
	
	private LayoutInflater inflater;
	private ArrayList<Group> groupList;
	private Context mContext;
	private ViewHolder viewHolder;
	private GroupManagement groupmanager;
	private int userPn;
	
	private int exitposition = -1;

	
	public GroupModifyListAdapter(Context c, ArrayList<Group> group){
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
			v = inflater.inflate(R.layout.group_modify_list, null);
			viewHolder.tv_title= (TextView)v.findViewById(R.id.grouprow);	
			viewHolder.groupexit = (Button)v.findViewById(R.id.groupexit);

			v.setTag(viewHolder);
			
		}else {
			viewHolder = (ViewHolder)v.getTag();
		}
		
		viewHolder.tv_title.setText(getItem(position).getGname());
		
		viewHolder.groupexit.setTag(position);
		viewHolder.groupexit.setOnClickListener(buttonClickListener);

		
		return v;
	}
	
	public void setArrayList(ArrayList<Group> group){
		this.groupList = group;
	}
	
	public ArrayList<Group> getArrayList(){
		return groupList;
	}
	private View.OnClickListener buttonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
		
			case R.id.groupexit:  //나가기 버튼 눌렀을 때, 해당 그룹에서 나가기 
				
				exitposition = (Integer)v.getTag();
				
				int exit_gn = find_whichgroup(exitposition);
				int user_pn = userPn;  //사용자 pn이거 나중에 수정해줘야함
			
				Intent intent = new Intent(mContext, GroupExitActivity.class);
				intent.putExtra("gn", exit_gn);
				intent.putExtra("pn", user_pn);
			
				mContext.startActivity(intent);
				
				//groupList.remove(exitposition);
				refresh_setting();
				
				break;

			default:
				break;
			}
		}
	};	
	class ViewHolder{
		
		public TextView tv_title = null;
		public Button groupexit = null;
		
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
	
	private int find_whichgroup(int exitposition){
		
		int group_num =0;
		
		group_num = groupList.get(exitposition).getGn();
		
		return group_num;
	}
	public void setuserPn(int userPn){
		this.userPn = userPn;
	}
	public void refresh_setting(){
		this.notifyDataSetChanged();
		
	}

}