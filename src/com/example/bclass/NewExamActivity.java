package com.example.bclass;

import java.sql.Date;

import com.parse.ParseObject;

import android.R.integer;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.os.Build;

public class NewExamActivity extends Activity {

	private EditText exam_title;
	private EditText exam_detail;
	private Spinner spExamChoice;
	private String[] choice = {"1", "2", "3", "4", "5"};
	private String title = "";
	private String detail = "";
	private int choice_OK = 4;
	private Date curDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_exam);
		init();
	}
	
	public void init() {
		exam_title = (EditText) findViewById(R.id.examTitle);
		exam_detail = (EditText) findViewById(R.id.examDetail);
		TextView exam_choice = (TextView) findViewById(R.id.examChoice);
		Button btn_OK = (Button) findViewById(R.id.examOK);
		Button btn_cancel = (Button) findViewById(R.id.examCancel);
		spExamChoice = (Spinner) findViewById(R.id.spExamChoice);
		ArrayAdapter<String> adapterChoice = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, choice);
		
		btn_OK.setOnClickListener(decision);
	    btn_cancel.setOnClickListener(decision);
	    spExamChoice.setAdapter(adapterChoice);
	    spExamChoice.setOnItemSelectedListener(spinnerListener);
	    //spExamChoice.setVisibility(View.VISIBLE);
	    spExamChoice.setSelection(3);
	}
	
	private OnClickListener decision = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.examOK:
				title = exam_title.getText().toString();
				detail = exam_detail.getText().toString();
				curDate = new Date(System.currentTimeMillis());
				ParseObject exam = new ParseObject("Exam");
				exam.put("description", detail);
				exam.put("name", title);
				exam.put("choice", choice_OK);
				exam.put("utc8", curDate);
				exam.put("result", "");
				exam.saveInBackground();
				
				Toast.makeText(NewExamActivity.this, "資料已創建完成", Toast.LENGTH_SHORT).show();
				
				Intent intent_back = new Intent();
				intent_back.setClass(NewExamActivity.this, TExamActivity.class);
				startActivity(intent_back); 
				NewExamActivity.this.finish();  
				break;
			case R.id.voteCancel:
				Intent intent_tvote = new Intent();
				intent_tvote.setClass(NewExamActivity.this, TExamActivity.class);
				startActivity(intent_tvote); 
				NewExamActivity.this.finish(); 
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
			choice_OK = Integer.parseInt(choice[position]);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_exam, menu);
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
		intent.setClass(NewExamActivity.this, TExamActivity.class);
		startActivity(intent); 
		NewExamActivity.this.finish(); 
		init();
	}

}
