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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.pro3.R;
import com.mokkoji.connect.GroupManagement;
import com.mokkoji.connect.NewGroup;

public class GroupExitActivity extends Activity{

	protected static final String TAG = GroupExitActivity.class
			.getSimpleName();
	private Button okbutton, cancelbutton;
	
	private int gn;
	private int userPn;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.exit_group);
		
		setTitle("그룹 나가기");
		
		// Intent get
		Intent intent = getIntent();

		// getIntExtra("받는변수명", 기본값)
		gn  = intent.getIntExtra("gn", 1);
		userPn = intent.getIntExtra("pn", 1);
		
		okbutton = (Button) findViewById(R.id.exitok);
		cancelbutton = (Button) findViewById(R.id.exitno);

	
		okbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				new GroupExitTask().execute();
				onResume();
				finish();
				
			}

		});
		
		// 취소면 이전 페이지로 돌아감
		cancelbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent resultIntent = new Intent();
				setResult(RESULT_OK, resultIntent);
				finish();
			}

		});

	}
	
	// ~ Post
	private void showResult(GroupManagement result) {

		Toast.makeText(this, "해당 모꼬지에서 나갔습니다.",
				Toast.LENGTH_LONG).show();

	}

	// ***************************************
	// Private classes
	// ***************************************
	private class GroupExitTask extends AsyncTask<Void, Void, GroupManagement> {

	
		private GroupManagement gm;
		@Override
		protected void onPreExecute() {

			gm = new GroupManagement();
			gm.setUser_pn(userPn);
			gm.setGroup_gn(gn);
			
		}

		@Override
		protected GroupManagement doInBackground(Void... params) {
			try {
				// The URL for making the POST request
				final String url = getString(R.string.base_uri)
						+ "/mokkoji/groupexitJson.json";

				HttpHeaders requestHeaders = new HttpHeaders();

				// Sending multipart/form-data
				requestHeaders.setContentType(MediaType.APPLICATION_JSON);

				// Populate the MultiValueMap being serialized and headers in an
				// HttpEntity object to use for the request
				HttpEntity<GroupManagement> requestEntity = new HttpEntity<GroupManagement>(gm,
						requestHeaders);

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate(true);
				restTemplate.getMessageConverters().add(
						new StringHttpMessageConverter());
				restTemplate.getMessageConverters().add(
						new MappingJackson2HttpMessageConverter());

				// Make the network request, posting the message, expecting a
				// String in response from the server
				ResponseEntity<GroupManagement> response = restTemplate.exchange(url,
						HttpMethod.POST, requestEntity, GroupManagement.class);

				// Return the response body to display to the user
			
				return response.getBody();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(GroupManagement result) {
			showResult(result);

		}

	}
}
