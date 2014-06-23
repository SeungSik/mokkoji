package com.mokkoji.function;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro3.R;
import com.mokkoji.connect.Member;

public class MemberListAdapter extends BaseAdapter{
	
	
	private LayoutInflater inflater;
	private ArrayList<Member> memberList;
	private Context mContext;
	private ViewHolder viewHolder;
	private ArrayList<MemberSelect> selection;
	private ArrayList<Member> checked;

	
	public MemberListAdapter(Context c, ArrayList<Member> mem){
		this.mContext = c;
		this.inflater = LayoutInflater.from(c);
		this.memberList = new ArrayList<Member>();
		memberList = mem;
		this.checked = new ArrayList<Member> ();
		this.selection= new ArrayList<MemberSelect> ();
		
		int size = mem.size();
		
		for(int i=0; i<size; i++){
			MemberSelect temp  = new MemberSelect();
			temp.setMember(memberList.get(i));
			temp.setSelceted(false);
			selection.add(temp);
		}
	

	}
	
	@Override
	public int getCount(){
		return memberList.size();
	}
	@Override
	public Member getItem(int position){
		return memberList.get(position);
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
			v = inflater.inflate(R.layout.memberlist_row, null);
			viewHolder.tv_title = (TextView)v.findViewById(R.id.tv_title);
			viewHolder.cb_box = (CheckBox)v.findViewById(R.id.cb_box);

			v.setTag(viewHolder);
			
		}else {
			viewHolder = (ViewHolder)v.getTag();
		}
		
		viewHolder.tv_title.setText(getItem(position).getId());
		

		viewHolder.cb_box.setTag(position);
		viewHolder.cb_box.setOnClickListener(buttonClickListener);
		
		
		return v;
	}
	
	public void setArrayList(ArrayList<Member> mem){
		this.memberList = mem;
	}
	
	public ArrayList<Member> getArrayList(){
		return memberList;
	}
	
	public ArrayList<Member> getchecked() {
		
		int size = selection.size();
		for(int i=0; i<size; i++){
			if(selection.get(i).isSelceted() == true){
				checked.add(selection.get(i).getMember());
			}
		}
		return checked;
	}

	private View.OnClickListener buttonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
		
			case R.id.cb_box:
				/*Toast.makeText(
						mContext, 
						" 선택  " + v.getTag(),
						Toast.LENGTH_SHORT
						).show();*/
				
				if(selection.get((Integer)v.getTag()).isSelceted() == true)
					selection.get((Integer)v.getTag()).setSelceted(false);
				else
					selection.get((Integer)v.getTag()).setSelceted(true);
				
				break;

			default:
				break;
			}
		}
	};
	class ViewHolder{
		
		public TextView tv_title = null;
		public CheckBox cb_box = null;
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		free();
		super.finalize();
	}
	
	private void free(){
		inflater = null;
		memberList = null;
		viewHolder = null;
		mContext = null;
	}
	

}
