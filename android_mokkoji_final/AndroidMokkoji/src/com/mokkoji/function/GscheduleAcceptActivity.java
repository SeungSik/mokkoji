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
import android.widget.ListView;

import com.example.pro3.R;
import com.mokkoji.connect.Gschedule;
import com.mokkoji.connect.GscheduleList;

public class GscheduleAcceptActivity extends Activity{
	
	protected static final String TAG = GscheduleAcceptActivity.class
			.getSimpleName();

	private int gn;
	private int userPn;
	private ListView gListview = null;
	
	private ArrayList<Gschedule> gslist = null;
	
	GscheduleListAdapter GSA;
	

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.gscheule_accept);
		
		Intent intent;
		intent = getIntent();
		userPn = intent.getIntExtra("userPn", 0);
		gn = intent.getIntExtra("gn", 0);
		
		gListview = (ListView) findViewById(R.id.gschedule_list);
		
		//checkgschedule에 check가 false가 되어있는 gs_sn을 가져옴
		//이를 위해서 userpn과 group_gn이 필요함 
		//gn을 통해서 -> 스케줄을 검색함 
		//검색한 스케줄 중에 -> sn을 가지고 옴 
	
		//해당 gs_sn들을 gschedule 클래스에서 가져옴 -> arraylsit로 adapter에 적용 
		new PostgsListTask().execute();
		
	
		
	}
	
	// ~ Post
	private void showResult(GscheduleList result) {
		
		gslist = new ArrayList<Gschedule>();
		gslist = result.getGschedulelist();
		
		GSA = new GscheduleListAdapter(this, gslist);
		GSA.setuserPn(userPn);
		GSA.setgn(gn);
		gListview.setAdapter(GSA);
		
		GSA.notifyDataSetChanged();
		
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class PostgsListTask extends AsyncTask<Void, Void, GscheduleList> {
		
		Gschedule gschedule;
		
		protected void onPreExecute() {
		
			gschedule = new Gschedule();
			gschedule.setGn(gn);
			gschedule.setUser_pn(userPn);
		}

		@Override
		protected GscheduleList doInBackground(Void... params) {
			try {
				// The URL for making the POST request
				final String url = getString(R.string.base_uri)
						+ "/mokkoji/wholegslistJson.json";

				HttpHeaders requestHeaders = new HttpHeaders();

				// Sending multipart/form-data
				requestHeaders.setContentType(MediaType.APPLICATION_JSON);

				// Populate the MultiValueMap being serialized and headers in an
				// HttpEntity object to use for the request
				HttpEntity<Gschedule> requestEntity = new HttpEntity<Gschedule>(
						gschedule, requestHeaders);

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate(true);
				restTemplate.getMessageConverters().add(
						new StringHttpMessageConverter());
				restTemplate.getMessageConverters().add(
						new MappingJackson2HttpMessageConverter());

				// Make the network request, posting the message, expecting a
				// String in response from the server
				ResponseEntity<GscheduleList> response = restTemplate.exchange(
						url, HttpMethod.POST, requestEntity, GscheduleList.class);

				// Return the response body to display to the user
				return response.getBody();

			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(GscheduleList result) {
			showResult(result);
		}

	}

}

