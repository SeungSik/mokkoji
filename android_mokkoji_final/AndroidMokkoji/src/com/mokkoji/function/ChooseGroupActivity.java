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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pro3.R;
import com.mokkoji.connect.Group;
import com.mokkoji.connect.Grouplist;


public class ChooseGroupActivity extends Activity{
	

	private ArrayList<Group> groupList = null;
	private ListView gListview = null;
	Integer userPn = 6;
	
	GroupListAdapter GA;
	 
	public static final int REQUEST_CODE_GO_GSCHEDUE =  1021;
	
	protected static final String TAG = ChooseGroupActivity.class.getSimpleName();

	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	      
		 setContentView(R.layout.choose_group);
		 setTitle("내 그룹 보기");

		 
		 Intent intent;
		 
		 intent = getIntent();
		 userPn=intent.getIntExtra("userPn", 0);
		 
		 gListview = (ListView) findViewById(R.id.listView1);
		 
		 
		 new PostgListTask().execute();
		 
		 gListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					
					Integer gn = groupList.get(position).getGn();
					String gname = groupList.get(position).getGname();
					
					Intent intent = new Intent(getApplicationContext(), GroupCalendarMonthViewActivity.class);
	            	intent.putExtra("userPn", userPn);
	            	intent.putExtra("gn", gn);
	            	intent.putExtra("gname", gname);
	            	startActivityForResult(intent,REQUEST_CODE_GO_GSCHEDUE);
					
				}
			});

	 }
		// ~ Post
		private void showResult(Grouplist result) {
			
			groupList = new ArrayList<Group>();

			groupList = result.getGrouplist();
			GA = new GroupListAdapter(this, groupList);
			gListview.setAdapter(GA);

		}

		// ***************************************
		// Private classes
		// ***************************************
		private class PostgListTask extends AsyncTask<Void, Void, Grouplist> {
		
			Integer userpn;
			
			protected void onPreExecute() {
				
				userpn = userPn;      //이거 바꿔줘야함 현재 사용자의 pn으로!!! 

			}

			@Override
			protected Grouplist doInBackground(Void... params) {
				try {
					// The URL for making the POST request
					final String url = getString(R.string.base_uri)
							+ "/mokkoji/postgrouplistJson.json";

					HttpHeaders requestHeaders = new HttpHeaders();

					// Sending multipart/form-data
					requestHeaders.setContentType(MediaType.APPLICATION_JSON);

					// Populate the MultiValueMap being serialized and headers in an
					// HttpEntity object to use for the request
					HttpEntity<Integer> requestEntity = new HttpEntity<Integer>(
							userpn, requestHeaders);

					// Create a new RestTemplate instance
					RestTemplate restTemplate = new RestTemplate(true);
					restTemplate.getMessageConverters().add(
							new StringHttpMessageConverter());
					restTemplate.getMessageConverters().add(
							new MappingJackson2HttpMessageConverter());

					// Make the network request, posting the message, expecting a
					// String in response from the server
					ResponseEntity<Grouplist> response = restTemplate.exchange(
							url, HttpMethod.POST, requestEntity, Grouplist.class);

					// Return the response body to display to the user
					return response.getBody();

				} catch (Exception e) {
					Log.e(TAG, e.getMessage(), e);
				}

				return null;
			}

			@Override
			protected void onPostExecute(Grouplist result) {
				showResult(result);
			}

		}

}
