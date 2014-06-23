package com.mokkoji.function;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.pro3.R;
import com.mokkoji.connect.Member;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//commit test

public class MainActivity extends Activity {

	protected static final String TAG = MainActivity.class.getSimpleName();

	public static final int REQUEST_CODE_MENU = 1001;
	public static final int REQUEST_CODE_JOIN = 1111;

	EditText usernameInput;
	EditText passwordInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				new PostTask().execute(); //포스트 해주기.

			}
		});

		Button postButton = (Button) findViewById(R.id.joinBtn);
		postButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						MemberJoinActivity.class);
		
				startActivityForResult(intent, REQUEST_CODE_JOIN);
			}

		});

		usernameInput = (EditText) findViewById(R.id.usernameInput);
		passwordInput = (EditText) findViewById(R.id.passwordInput);
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (requestCode == REQUEST_CODE_MENU) {

			if (resultCode == RESULT_OK) {

				String menu = intent.getStringExtra("menu");
				String message = intent.getStringExtra("message");

				Toast toast = Toast.makeText(getBaseContext(), "result code : "
						+ resultCode + ", menu : " + menu + ", message : "
						+ message, Toast.LENGTH_LONG);
				toast.show();
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// ~ Get
	private void refreshStates(Member member) {
		if (member == null) {
			return;
		}
		System.out.println(member);
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class GetTask extends AsyncTask<Void, Void, Member> {

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Member doInBackground(Void... params) {
			try {
				// The URL for making the GET request
				final String url = getString(R.string.base_uri)+"/mokkoji/getJson.json?id=Jung1234&password=1q2w3e4r!&email=abcd@naver.com";

				// Set the Accept header for "application/json"
				HttpHeaders requestHeaders = new HttpHeaders();
				List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
				acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
				requestHeaders.setAccept(acceptableMediaTypes);

				// Populate the headers in an HttpEntity object to use for the
				// request
				HttpEntity<?> requestEntity = new HttpEntity<Object>(
						requestHeaders);

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(
						new MappingJackson2HttpMessageConverter());

				// Perform the HTTP GET request
				ResponseEntity<Member> responseEntity = restTemplate.exchange(
						url, HttpMethod.GET, requestEntity, Member.class);

				// convert the array to a list and return it
				return responseEntity.getBody();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Member result) {
			refreshStates(result);
		}

	}

	// ~ Post
	private void showResult(Member result) {
		
		if (result.getId() == null) {
			// display a notificati on to the user with the response information
			//Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
			if(result.getPn()== null){  //id가 아예존재 하지 않을 때
				Toast.makeText(this, "회원 가입을 먼저 해주세요", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(this, "비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
			}
			
		} else {
			Toast.makeText(this, "환영합니다", Toast.LENGTH_LONG).show();
			//Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent(getApplicationContext(),
					CalendarMonthViewActivity.class);
			intent.putExtra("userId", result.getId());
			intent.putExtra("userPn",(int)result.getPn());			
			
			startActivityForResult(intent, REQUEST_CODE_MENU);
			
			
		}
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class PostTask extends AsyncTask<Void, Void, Member> {

		private Member member;

		@Override
		protected void onPreExecute() {

			member = new Member();

			member.setId(usernameInput.getText().toString());

			member.setPassword(passwordInput.getText().toString());
		}

		@Override
		protected Member doInBackground(Void... params) {
			try {
				// The URL for making the POST request
				final String url = getString(R.string.base_uri)+"/mokkoji/postJson.json";

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

	
	
}
