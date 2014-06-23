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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pro3.R;
import com.mokkoji.connect.Member;
import com.mokkoji.connect.Memberlist;

public class ShowmemberofGroupActivity extends Activity{
	
	Integer userPn;
	Integer gn;
	String gname;
	
	ListView memberofgroup;
	Button findfriend;
	FriendAdapter FA;
	private ArrayList<Member> frinedList = null;
	
	protected static final String TAG = ShowmemberofGroupActivity.class.getSimpleName();

	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	      
		 setContentView(R.layout.memberofgroup);
		 
		 TextView groupname = (TextView)findViewById(R.id.nameofgroup);
		 
		 Intent intent;
		 
		 intent = getIntent();
		 userPn=intent.getIntExtra("userPn", 0);
		 gn = intent.getIntExtra("gn", 0);
		 gname = intent.getExtras().get("gname").toString();
		
		 groupname.setText(gname);
		
		 
		 memberofgroup= (ListView) findViewById(R.id.memberofgroup);
		 
		 new memOfgroupList().execute();

		 
		 memberofgroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
				

				}
			});
		 
	 }
	 
		// ~ Post
		private void showResult(Memberlist result) {
			
			frinedList = new ArrayList<Member>();
			frinedList = result.getMemberlist();
			FA = new FriendAdapter(this, frinedList);
			memberofgroup.setAdapter(FA);
		}

		// ***************************************
		// Private classes
		// ***************************************
		private class memOfgroupList extends AsyncTask<Void, Void, Memberlist> {
		
			
			
			protected void onPreExecute() {
				
			}

			@Override
			protected Memberlist doInBackground(Void... params) {
				try {
					// The URL for making the POST request
					final String url = getString(R.string.base_uri)
							+ "/mokkoji/memOfgroupList.json";

					HttpHeaders requestHeaders = new HttpHeaders();

					// Sending multipart/form-data
					requestHeaders.setContentType(MediaType.APPLICATION_JSON);

					// Populate the MultiValueMap being serialized and headers in an
					// HttpEntity object to use for the request
					HttpEntity<Integer> requestEntity = new HttpEntity<Integer>(
							gn, requestHeaders);

					// Create a new RestTemplate instance
					RestTemplate restTemplate = new RestTemplate(true);
					restTemplate.getMessageConverters().add(
							new StringHttpMessageConverter());
					restTemplate.getMessageConverters().add(
							new MappingJackson2HttpMessageConverter());

					// Make the network request, posting the message, expecting a
					// String in response from the server
					ResponseEntity<Memberlist> response = restTemplate.exchange(
							url, HttpMethod.POST, requestEntity, Memberlist.class);

					// Return the response body to display to the user
					return response.getBody();

				} catch (Exception e) {
					Log.e(TAG, e.getMessage(), e);
				}

				return null;
			}

			@Override
			protected void onPostExecute(Memberlist result) {
				showResult(result);
			}

		}
		

}
