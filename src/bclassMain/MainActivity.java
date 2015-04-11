package bclassMain;

import bclassStudent.StudentActivity;
import bclassTeacher.TeacherActivity;

import com.example.bclass.R;
import com.parse.ParseObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
	}

	public void init() {
		Button btn_teacher = (Button) findViewById(R.id.btn_teacher);
		Button btn_student = (Button) findViewById(R.id.btn_student);
		btn_teacher.setOnClickListener(modeChoose);
		btn_student.setOnClickListener(modeChoose);

	}

	private OnClickListener modeChoose = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
			case R.id.btn_teacher:
				Intent intent_teacher = new Intent();
				intent_teacher.setClass(MainActivity.this, TeacherActivity.class);
				startActivity(intent_teacher); 
				MainActivity.this.finish(); 
				break;
			case R.id.btn_student:
				Intent intent_student = new Intent();
				intent_student.setClass(MainActivity.this, StudentActivity.class);
				startActivity(intent_student);
				break;

			default:
				break;
			}

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
