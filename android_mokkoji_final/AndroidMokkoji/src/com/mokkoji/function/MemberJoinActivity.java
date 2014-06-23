package com.mokkoji.function;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.example.pro3.R;
import com.mokkoji.connect.Member;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MemberJoinActivity extends Activity{
	

	protected static final String TAG = MemberJoinActivity.class.getSimpleName();
	 EditText userid;
	 EditText userpass;
	 EditText useremail;

	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	      
		 setContentView(R.layout.member_join);
		 
		 userid = (EditText) findViewById(R.id.getid);
		 userpass = (EditText) findViewById(R.id.getpass);
		 useremail = (EditText) findViewById(R.id.getemail);
		 
		 
		 //아이디 중복 검사 
	
		 Button lookid = (Button) findViewById(R.id.lookId);
		 lookid.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					
					new IdCheckTask().execute();
				}
			});
		 
		
		 Button joinButton = (Button) findViewById(R.id.joinok);
		 joinButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					
					new JoinTask().execute();
					
				}
			});

	 }
	// ~ Post
		private void showResult(Member result) {
			if(result.getId() != null ){
				Toast.makeText(this, result.getId()+" 환영 합니다! ",
						Toast.LENGTH_LONG).show();
				finish();
			}else{
				Toast.makeText(this, "아이디가 이미 존재합니다. 다른 아이디를 사용하세요.",
						Toast.LENGTH_LONG).show();
			}
			
		}
	// ***************************************
	// Private classes
	// ***************************************
		private class JoinTask extends AsyncTask<Void, Void, Member> {

			private Member member;

			@Override
			protected void onPreExecute() {

				member = new Member();
			
				member.setId(userid.getText().toString());
				member.setPassword(userpass.getText().toString());
				member.setEmail(useremail.getText().toString());
				
			}

			@Override
			protected Member doInBackground(Void... params) {
				try {
					// The URL for making the POST request
					final String url = getString(R.string.base_uri)+"/mokkoji/JoinJson.json";

					HttpHeaders requestHeaders = new HttpHeaders();

					// Sending multipart/form-data
					requestHeaders.setContentType(MediaType.APPLICATION_JSON);

					// Populate the MultiValueMap being serialized and headers in an
					// HttpEntity object to use for the request
					HttpEntity<Member> requestEntity = new HttpEntity<Member>(
							member, requestHeaders);

					// Create a new RestTemplate instance
					RestTemplate restTemplate = new RestTemplate(true);
					restTemplate.getMessageConverters().add(
							new StringHttpMessageConverter());
					restTemplate.getMessageConverters().add(
							new MappingJackson2HttpMessageConverter());

					// Make the network request, posting the message, expecting a
					// String in response from the server
					ResponseEntity<Member> response = restTemplate.exchange(url,
							HttpMethod.POST, requestEntity, Member.class);

					// Return the response body to display to the user
					
					
					return response.getBody();
				} catch (Exception e) {
					Log.e(TAG, e.getMessage(), e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Member result) {
				showResult(result);
			}

		}
		
		 // ~ Post
		private void showidResult(Integer result) {
			
			String message = new String();
		
			if(result == 1){
				message = "아이디를 입력해 주세요";
				
			}else if(result == 2){
				message = "아이디가 이미 존재합니다.";
			}else {
				message = "사용 가능한 아이디 입니다.";
			}
			
				Toast.makeText(this, message,
						Toast.LENGTH_LONG).show();
			
		
			
		}
	// ***************************************
	// Private classes
	// ***************************************
		private class IdCheckTask extends AsyncTask<Void, Void, Integer> {

			private Member member;
			private String memberid;

			@Override
			protected void onPreExecute() {

				memberid = new String();
				memberid = userid.getText().toString();
			
			}

			@Override
			protected Integer doInBackground(Void... params) {
				try {
					// The URL for making the POST request
					final String url = getString(R.string.base_uri)+"/mokkoji/IdCheckJson.json";

					HttpHeaders requestHeaders = new HttpHeaders();

					// Sending multipart/form-data
					requestHeaders.setContentType(MediaType.APPLICATION_JSON);

					// Populate the MultiValueMap being serialized and headers in an
					// HttpEntity object to use for the request
					HttpEntity<String> requestEntity = new HttpEntity<String>(
							memberid, requestHeaders);

					// Create a new RestTemplate instance
					RestTemplate restTemplate = new RestTemplate(true);
					restTemplate.getMessageConverters().add(
							new StringHttpMessageConverter());
					restTemplate.getMessageConverters().add(
							new MappingJackson2HttpMessageConverter());

					// Make the network request, posting the message, expecting a
					// String in response from the server
					ResponseEntity<Integer> response = restTemplate.exchange(url,
							HttpMethod.POST, requestEntity, Integer.class);

					// Return the response body to display to the user
					
					
					return response.getBody();
				} catch (Exception e) {
					Log.e(TAG, e.getMessage(), e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Integer result) {
				showidResult(result);
			}

		} 
	
}