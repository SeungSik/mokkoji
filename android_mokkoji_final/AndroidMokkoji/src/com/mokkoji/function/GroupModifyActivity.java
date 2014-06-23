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

import com.example.pro3.R;
import com.mokkoji.connect.Group;
import com.mokkoji.connect.Grouplist;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class GroupModifyActivity extends Activity{
	
	
	private ArrayList<Group> groupList = null;
	private ListView gListview = null;
	
	public static final int REQUEST_CODE_GROUP_EXIT = 8;
	
	GroupModifyListAdapter GA;
	Integer userPn;

	
	protected static final String TAG = GroupModifyActivity.class
			.getSimpleName();
	
	
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	      
		 setContentView(R.layout.group_modify);
		 
		 Intent intent;
		 intent  =getIntent();
		 	       
		 userPn = intent.getIntExtra("userPn", 6);
		 
		 gListview = (ListView) findViewById(R.id.GroupList);
		 
		 
		 new PostgListTask().execute();
		 
		 gListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
				
		
					}
			
		});
		 
	 }
	// ~ Post
			private void showResult(Grouplist result) {
				
				groupList = new ArrayList<Group>();
				groupList = result.getGrouplist();
				
				GA = new GroupModifyListAdapter(this, groupList);
				GA.setuserPn(userPn);
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
/*
 나가기 버튼을 누르면, 내가 속한 그룹 리스트 중에 몇번째 인지 나온다! 
 그러면  glist값에서 나오게됨 
 glist에서 group을 가져옴!! 
 group에서 gn을 가지고 오고 ,
 현재 user의 pn을 가져 온다음에 groupmanagement에 가서 (pn, gn)에 해당되는 것을 삭제한다.
 
 * */
