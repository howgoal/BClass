package bclassTeacher;

import bclassMain.MainActivity;
import bclassMain.NewExamActivity;

import com.example.bclass.R;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.os.Build;

public class TeacherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher);
		getActionBar().hide(); 
		init();
	}
	
	public void init() {
		ImageButton btn_TBack = (ImageButton) findViewById(R.id.btn_TBack);
		ImageButton btn_ToVote = (ImageButton) findViewById(R.id.btn_TToVote);
		ImageButton btn_testExam = (ImageButton) findViewById(R.id.btn_testExam); // test
		btn_TBack.setOnClickListener(modeChange);
		btn_ToVote.setOnClickListener(modeChange);
		btn_testExam.setOnClickListener(modeChange); // test
	}
	
	public OnClickListener modeChange = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_TBack:
				Intent intent_back = new Intent();
				intent_back.setClass(TeacherActivity.this, MainActivity.class);
				startActivity(intent_back);
				break;
			case R.id.btn_TToVote:
				Intent intent_vote = new Intent();
				intent_vote.setClass(TeacherActivity.this, TVoteActivity.class);
				startActivity(intent_vote); 
				break;
			case R.id.btn_testExam: // test
				Intent intent_exam = new Intent();
				intent_exam.setClass(TeacherActivity.this, NewExamActivity.class);
				startActivity(intent_exam); 
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teacher, menu);
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
		intent.setClass(TeacherActivity.this, MainActivity.class);
		startActivity(intent); 
		TeacherActivity.this.finish(); 
		init();
	}
}
