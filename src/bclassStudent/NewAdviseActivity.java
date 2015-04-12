package bclassStudent;

import bclassTeacher.TVoteActivity;

import com.example.bclass.R;
import com.example.bclass.R.id;
import com.example.bclass.R.layout;
import com.example.bclass.R.menu;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.os.Build;

public class NewAdviseActivity extends Activity {

	private RadioButton[][] btn_q= new RadioButton[4][5];
	private RadioGroup[] groups = new RadioGroup[4];
	private EditText comments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_advise);
		init();
	}
	
	public void init() {
		for(int i=1; i<=4; i++) {
			int resId_group = getResources().getIdentifier("radioGroup"+ i, "id", getPackageName());
			groups[i-1] = (RadioGroup)findViewById(resId_group);
			for(int j=1; j<=5; j++) {
				int resId_btn = getResources().getIdentifier("q" + i + 
						"_btn" + j , "id" , getPackageName());
				btn_q[i-1][j-1] = (RadioButton)findViewById(resId_btn);
			}	
		}
		
		TextView advise_title = (TextView)findViewById(R.id.advise_title);
		TextView advise_detail = (TextView)findViewById(R.id.advise_detail);
		TextView question1 = (TextView)findViewById(R.id.question1);
		TextView question2 = (TextView)findViewById(R.id.question2);
		TextView question3 = (TextView)findViewById(R.id.question3);
		TextView question4 = (TextView)findViewById(R.id.question4);
		comments = (EditText)findViewById(R.id.comments);
		Button advise_OK = (Button)findViewById(R.id.advise_OK);
		Button advise_cancel = (Button)findViewById(R.id.advise_cancel);
		
		advise_OK.setOnClickListener(decision);
		advise_cancel.setOnClickListener(decision);
	}
	
	private OnClickListener decision = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.advise_OK:
				ParseObject advise = new ParseObject("Advise");
				for(int i=0; i<4; i++) {
					advise.put("question" + (i+1), getChoice(i+1));
				}
				advise.put("comment", comments.getText().toString());
				advise.saveInBackground();
				
				Toast.makeText(NewAdviseActivity.this, "意見已提交", Toast.LENGTH_SHORT).show();
				Intent intent_back = new Intent();
				intent_back.setClass(NewAdviseActivity.this, StudentActivity.class);
				startActivity(intent_back); 
				NewAdviseActivity.this.finish(); 
				break;
			case R.id.advise_cancel:
				Intent intent_cancel = new Intent();
				intent_cancel.setClass(NewAdviseActivity.this, StudentActivity.class);
				startActivity(intent_cancel); 
				NewAdviseActivity.this.finish();
				break;
			default:
				break;
			}
		}
	};
	
	private int getChoice(int question) {
		int result = 0;
		for(int i=1; i<=5; i++) {
			int resId = getResources().getIdentifier("q" + question + 
					"_btn" + i , "id" , getPackageName());
			if(groups[question-1].getCheckedRadioButtonId() == resId) {
				result = i;
			}
			else {
			}	
		}
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_advise, menu);
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

}
