package bclassMain;

import java.sql.Date;

import bclassTeacher.TVoteActivity;

import com.example.bclass.R;
import com.parse.ParseObject;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class NewVoteActivity extends Activity {
 
	private String[] choice = {"2", "3", "4", "5", "6", "7"};
	private String[] time = {"3", "5", "10", "15", "30"};
	private Context testContext;
	private String title = "";
	private String detail = "";
	private int choice_OK = 2; 
	private int time_OK = 3;
	private EditText vote_title;
	private EditText vote_detail;
	private Spinner spinnerChoice;
	private Spinner spinnerTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newvote);
		init();
	}
	
	public void init() {
		vote_title = (EditText) findViewById(R.id.voteTitle);
		vote_detail = (EditText) findViewById(R.id.voteDetail);
		Button btn_OK = (Button) findViewById(R.id.voteOK);
		Button btn_cancel = (Button) findViewById(R.id.voteCancel);
		spinnerChoice = (Spinner) findViewById(R.id.spinnerChoice);
		spinnerTime = (Spinner) findViewById(R.id.spinnerTime);
		ArrayAdapter<String> adapterChoice = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, choice);
		ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, time);
		
	    btn_OK.setOnClickListener(decision);
	    btn_cancel.setOnClickListener(decision);
		spinnerChoice.setAdapter(adapterChoice);
		spinnerChoice.setOnItemSelectedListener(spinnerListener);
		spinnerChoice.setVisibility(View.VISIBLE);
		spinnerTime.setAdapter(adapterTime);
		spinnerTime.setOnItemSelectedListener(spinnerListener);
		spinnerTime.setVisibility(View.VISIBLE);
		
		testContext = this.getApplicationContext();
	}
	
	private OnClickListener decision = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.voteOK:
				title = vote_title.getText().toString();
				detail = vote_detail.getText().toString();
				//curDate = new Date(System.currentTimeMillis());
				//Log.e("!!!", curDate.toString());
				ParseObject vote = new ParseObject("Vote");
				vote.put("description", detail);
				vote.put("name", title);
				vote.put("choice", choice_OK);
				vote.put("time", time_OK);
				vote.put("result", "");
				vote.saveInBackground();
				
				Toast.makeText(NewVoteActivity.this, "資料已創建完成", Toast.LENGTH_SHORT).show();
				
				Intent intent_back = new Intent();
				intent_back.setClass(NewVoteActivity.this, TVoteActivity.class);
				startActivity(intent_back); 
				NewVoteActivity.this.finish();  
				break;
			case R.id.voteCancel:
				Intent intent_tvote = new Intent();
				intent_tvote.setClass(NewVoteActivity.this, bclassTeacher.TVoteActivity.class);
				startActivity(intent_tvote); 
				NewVoteActivity.this.finish(); 
				break;
			default:
				break;
			}
		}
	};
	
	private OnItemSelectedListener spinnerListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			switch (parent.getId()) {
			case R.id.spinnerChoice:
				//Log.i("@@", choice[position]);
				choice_OK = Integer.parseInt(choice[position]);
				//Toast.makeText(testContext, "Choice: "+choice[position], Toast.LENGTH_SHORT).show();
				break;
			case R.id.spinnerTime:
				//Log.i("@@", time[position]);
				time_OK = Integer.parseInt(time[position]);
				//Toast.makeText(testContext, "Time: "+time[position], Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.newvote, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.setClass(NewVoteActivity.this, TVoteActivity.class);
		startActivity(intent); 
		NewVoteActivity.this.finish(); 
		init();
	}
}
