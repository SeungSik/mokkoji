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
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.pro3.R;
import com.mokkoji.connect.CheckGschedule;
import com.mokkoji.connect.Gschedule;
import com.mokkoji.connect.Schedule;
import com.mokkoji.connect.Usergschedule;

public class GscheduleListAdapter extends BaseAdapter{
	
	
	private LayoutInflater inflater;
	public ArrayList<Gschedule> gsList;
	private Context mContext;
	private ViewHolder viewHolder;

	
	private int bntposition  = -1;
	private int sn;
	private int userPn;
	private int gn;
	
	private int selectedPosition;	
	
	public GscheduleListAdapter(Context context) {
        mContext = context;
 
        gsList = new ArrayList<Gschedule>();
    }

	
	public GscheduleListAdapter(Context c, ArrayList<Gschedule> gsList){
		
		this.mContext = c;
		this.inflater = LayoutInflater.from(c);
		this.gsList = new ArrayList<Gschedule>();
		this.gsList = gsList;
		
	}
	
	
	public void clear() {
        gsList.clear();
    }
 
    public void addItem(Gschedule item) {
        gsList.add(item);
    }
 
    public void removeItem(Gschedule item) {

        gsList.remove(item);
    }
 
    public void addAll(ArrayList<Gschedule> items) {
        gsList.addAll(items);
    }
 
	
	
	@Override
	public int getCount(){
		return gsList.size();
	}
	@Override
	public Gschedule getItem(int position){
		return gsList.get(position);
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
			v = inflater.inflate(R.layout.gschedule_row, null);
			viewHolder.grouprow = (TextView)v.findViewById(R.id.grouprow);	
			viewHolder.scheduletime = (TextView)v.findViewById(R.id.scheduletime);
			viewHolder.gs_accept = (Button)v.findViewById(R.id.gs_accept);
			viewHolder.gs_reject= (Button)v.findViewById(R.id.gs_reject);
			v.setTag(viewHolder);
			
		}else {
			viewHolder = (ViewHolder)v.getTag();
		}
		
		viewHolder.grouprow.setText(getItem(position).getTitle());    //title로 text 선택함 
		viewHolder.scheduletime.setText(getItem(position).getStartDate()+" ~ "+ getItem(position).getEndDate());
		
		viewHolder.gs_accept.setTag(position);
		viewHolder.gs_accept.setOnClickListener(buttonClickListener);
		
		viewHolder.gs_reject.setTag(position);
		viewHolder.gs_reject.setOnClickListener(buttonClickListener);

		
		return v;
	}
	
	public void setArrayList(ArrayList<Gschedule> gslist){
		this.gsList = gslist;
	}
	
	public ArrayList<Gschedule> getArrayList(){
		return gsList;
	}
	private View.OnClickListener buttonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
		
			case R.id.gs_accept:  //일정을 수락할 때, 
				
				bntposition = (Integer)v.getTag();
				sn = gsList.get(bntposition).getSn();  //sn과userpn으로 바꿔줌	
				//check값이 false인것을 true로 바꾸고	
				new PostCheckedTask().execute();
				//usergschedule에 등록해줌 
				new PostAcceptGs().execute();	
				gsList.remove(bntposition);
				refresh_setting();
				
				break;
				
			case R.id.gs_reject:  //일정을 거절할 때, 
				
				bntposition  = (Integer)v.getTag();		
				sn = gsList.get(bntposition).getSn();
				
				//check 값이 false인것을 true로 바꿔줌
				new PostCheckedTask().execute();
				gsList.remove(bntposition);
				refresh_setting();
				
				break;
				
				
			default:
				break;
			}
		}
	};
	
	class ViewHolder{
		
		public TextView grouprow = null;
		public TextView scheduletime = null;
		public Button gs_accept = null;
		public Button gs_reject = null;
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		free();
		super.finalize();
	}
	
	private void free(){
		inflater = null;
		gsList = null;
		viewHolder = null;
		mContext = null;
	}
	public void setuserPn(int userPn){
		this.userPn = userPn;
	}
	public void setgn(int gn){
		this.gn = gn;
	}
	
	public void setSelectedPosition(int selectedPosition) {
		this.selectedPosition = selectedPosition;
		
	}
	
	public int getSelectedPosition() {
        return selectedPosition;
    }
	public void refresh_setting(){
		this.notifyDataSetChanged();
		
	}
	
	
	// ~ Post
	private void showResult(CheckGschedule result) {
		
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class PostCheckedTask extends AsyncTask<Void, Void, CheckGschedule> {
		
		CheckGschedule gs;
		
		protected void onPreExecute() {
		
			gs = new CheckGschedule();
			gs.setChecked(0);
			gs.setGroup_sn(sn);
			gs.setUser_pn(userPn);
		}

		@Override
		protected CheckGschedule doInBackground(Void... params) {
			try {
				// The URL for making the POST request
				final String url = mContext.getString(R.string.base_uri)
						+ "/mokkoji/checkgscheduleJson.json";

				HttpHeaders requestHeaders = new HttpHeaders();

				// Sending multipart/form-data
				requestHeaders.setContentType(MediaType.APPLICATION_JSON);

				// Populate the MultiValueMap being serialized and headers in an
				// HttpEntity object to use for the request
				HttpEntity<CheckGschedule> requestEntity = new HttpEntity<CheckGschedule>(
						gs, requestHeaders);

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate(true);
				restTemplate.getMessageConverters().add(
						new StringHttpMessageConverter());
				restTemplate.getMessageConverters().add(
						new MappingJackson2HttpMessageConverter());

				// Make the network request, posting the message, expecting a
				// String in response from the server
				ResponseEntity<CheckGschedule> response = restTemplate.exchange(
						url, HttpMethod.POST, requestEntity, CheckGschedule.class);

				// Return the response body to display to the user
				return response.getBody();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(CheckGschedule result) {
			showResult(result);
		}

	}
	
	// ~ Post
		private void showResult_2(Usergschedule result) {
			
		}

		// ***************************************
		// Private classes
		// ***************************************
		private class PostAcceptGs extends AsyncTask<Void, Void, Usergschedule> {
			
			Usergschedule ug;
			
			protected void onPreExecute() {
				ug = new Usergschedule();
				ug.setGs_gn(gn);
				ug.setGs_sn(sn);
				ug.setUser_pn(userPn);
			}

			@Override
			protected Usergschedule doInBackground(Void... params) {
				try {
					// The URL for making the POST request
					final String url = mContext.getString(R.string.base_uri)
							+ "/mokkoji/AcceptgscheduleJson.json";

					HttpHeaders requestHeaders = new HttpHeaders();

					// Sending multipart/form-data
					requestHeaders.setContentType(MediaType.APPLICATION_JSON);

					// Populate the MultiValueMap being serialized and headers in an
					// HttpEntity object to use for the request
					HttpEntity<Usergschedule> requestEntity = new HttpEntity<Usergschedule>(
							ug, requestHeaders);

					// Create a new RestTemplate instance
					RestTemplate restTemplate = new RestTemplate(true);
					restTemplate.getMessageConverters().add(
							new StringHttpMessageConverter());
					restTemplate.getMessageConverters().add(
							new MappingJackson2HttpMessageConverter());

					// Make the network request, posting the message, expecting a
					// String in response from the server
					ResponseEntity<Usergschedule> response = restTemplate.exchange(
							url, HttpMethod.POST, requestEntity, Usergschedule.class);

					// Return the response body to display to the user
					return response.getBody();

				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(Usergschedule result) {
				showResult_2(result);
			}

		}
}