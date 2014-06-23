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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pro3.R;
import com.mokkoji.connect.Group;
import com.mokkoji.connect.Member;
import com.mokkoji.connect.Memberlist;
import com.mokkoji.connect.NewGroup;


public class GroupCreateActivity extends Activity {

	public static final int REQUEST_CODE_GROUP_CREATE = 11;

	private Memberlist mems;
	private Member User;
	private ArrayList<Member> memselected = null;
	private ArrayList<Member> memberList = null;
	private ListView mListview = null;
	private EditText Groupname;
	private Button button1;
	private Button button2;
	MemberListAdapter MA;
	Integer userPn;
	

	protected static final String TAG = GroupCreateActivity.class
			.getSimpleName();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.group_create);
		
		Intent intent;
		intent  =getIntent();
			       
		userPn = intent.getIntExtra("userPn", 6);

		
		User = new Member();   //현재 user를 받아오기 위한 객체 
		
		mListview = (ListView) findViewById(R.id.friendList);
		Groupname = (EditText) findViewById(R.id.Groupname);

		// list를 받아온다 -> memberlist를 가져와서 출력해준다.
		new PostListTask().execute();

		mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {

			}
		});

		button1 = (Button) findViewById(R.id.Okbutton);
		button2 = (Button) findViewById(R.id.Cancelbutton);

		// 생성 해주면 등록해야함 -> 그룹에
		button1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int checked = MA.getchecked().size();
				for (int i = 0; i < checked; i++) {
					memselected.add(MA.getchecked().get(i));
				}
			
				new GroupTask().execute();

				Intent resultIntent = new Intent();
				setResult(RESULT_OK, resultIntent);
				finish();
			}

		});

		// 취소면 이전 페이지로 돌아감
		button2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent resultIntent = new Intent();
				setResult(RESULT_OK, resultIntent);
				finish();
			}

		});

	}

	// ~ Post
	private void showResult(Memberlist result) {

		memberList = result.getMemberlist();
		
		int memlist_size =memberList.size();
		
		for(int t=0; t<memlist_size; t++){
			
			if(memberList.get(t).getPn() == userPn) {  //현재 사용자 id 넣어야함
				User = memberList.get(t);      //현재 user객체를 받아옴 
				memberList.remove(t);    //현재 사용자를 목록에서 없애기 
				break;
			}
		}

		MA = new MemberListAdapter(this, memberList);
		mListview.setAdapter(MA);

	}

	// ***************************************
	// Private classes
	// ***************************************
	private class PostListTask extends AsyncTask<Memberlist, Void, Memberlist> {

		protected void onPreExecute() {

			memberList = new ArrayList<Member>();      //생성된 member들 다 보기
			memselected = new ArrayList<Member>();     //선택된 memberlist
			mems = new Memberlist();     

		}

		@Override
		protected Memberlist doInBackground(Memberlist... membs) {
			try {
				// The URL for making the POST request
				final String url = getString(R.string.base_uri)
						+ "/mokkoji/postlistJson.json";

				HttpHeaders requestHeaders = new HttpHeaders();

				// Sending multipart/form-data
				requestHeaders.setContentType(MediaType.APPLICATION_JSON);

				// Populate the MultiValueMap being serialized and headers in an
				// HttpEntity object to use for the request
				HttpEntity<Memberlist> requestEntity = new HttpEntity<Memberlist>(
						mems, requestHeaders);

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

	// ~ Post
	private void showResult(NewGroup result) {

		Toast.makeText(this, result.getGroup().getGname() + " 모꼬지가 생성되었습니다",
				Toast.LENGTH_LONG).show();

	}

	// ***************************************
	// Private classes
	// ***************************************
	private class GroupTask extends AsyncTask<Void, Void, NewGroup> {

		private Group group;
		private Group groupmanagement;
		private NewGroup newgroup;
		private String memsname;

		@Override
		protected void onPreExecute() {

			memsname = new String();
			Memberlist mems = new Memberlist();
			newgroup = new NewGroup();
			
			memselected.add(User);
			mems.setMemberlist(memselected);
			
			
			group = new Group();
			group.setGname(Groupname.getText().toString());
			group.setMasterPn(userPn);   //현재 사용자의 pn받아오기 
			
			newgroup.setGroup(group);
			newgroup.setMemberlist(mems);
			
		}

		@Override
		protected NewGroup doInBackground(Void... params) {
			try {
				// The URL for making the POST request
				final String url = getString(R.string.base_uri)
						+ "/mokkoji/postgroupJson.json";

				HttpHeaders requestHeaders = new HttpHeaders();

				// Sending multipart/form-data
				requestHeaders.setContentType(MediaType.APPLICATION_JSON);

				// Populate the MultiValueMap being serialized and headers in an
				// HttpEntity object to use for the request
				HttpEntity<NewGroup> requestEntity = new HttpEntity<NewGroup>(newgroup,
						requestHeaders);

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate(true);
				restTemplate.getMessageConverters().add(
						new StringHttpMessageConverter());
				restTemplate.getMessageConverters().add(
						new MappingJackson2HttpMessageConverter());

				// Make the network request, posting the message, expecting a
				// String in response from the server
				ResponseEntity<NewGroup> response = restTemplate.exchange(url,
						HttpMethod.POST, requestEntity, NewGroup.class);

				// Return the response body to display to the user
			
				return response.getBody();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(NewGroup result) {
			showResult(result);

		}

	}
	
}
