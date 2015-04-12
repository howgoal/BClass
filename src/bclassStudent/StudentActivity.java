package bclassStudent;

import noteSystem.NoteActivity;
import bclassMain.MainActivity;

import com.example.bclass.R;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Build;

public class StudentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student);
		init();
	}

	public void init() {
		Button btn_SBack = (Button) findViewById(R.id.btn_SBack);
		Button btn_ToVote = (Button) findViewById(R.id.btn_SToVote);
		Button testAdvise = (Button) findViewById(R.id.testAdvise); // test
		Button btn_SToNote = (Button) findViewById(R.id.btn_SToNote);
		btn_SBack.setOnClickListener(modeChange);
		btn_ToVote.setOnClickListener(modeChange);
		testAdvise.setOnClickListener(modeChange); // test
		btn_SToNote.setOnClickListener(modeChange);
	}
	
	private OnClickListener modeChange = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_SBack:
				Intent intent_back = new Intent();
				intent_back.setClass(StudentActivity.this, MainActivity.class);
				startActivity(intent_back); 
				StudentActivity.this.finish();  
				break;
			case R.id.btn_SToVote:
//				Intent intent_vote = new Intent();
//				intent_vote.setClass(StudentActivity.this, SVoteActivity.class);
//				startActivity(intent_vote);  
				break;
			case R.id.testAdvise:
				Intent intent = new Intent();
				intent.setClass(StudentActivity.this, NewAdviseActivity.class);
				startActivity(intent);  
				break;
			case R.id.btn_SToNote:
				Intent intent_Note = new Intent();
				intent_Note.setClass(StudentActivity.this, NoteActivity.class);
				startActivity(intent_Note); 
				StudentActivity.this.finish();  
				break;
			default:
				break;
			}
			 
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student, menu);
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
		intent.setClass(StudentActivity.this, MainActivity.class);
		startActivity(intent); 
		StudentActivity.this.finish(); 
		init();
	}

}
