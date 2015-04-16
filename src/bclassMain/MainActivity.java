package bclassMain;

import bclassStudent.NewAdviseActivity;
import bclassStudent.StudentActivity;
import bclassTeacher.TeacherActivity;

import com.example.bclass.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private boolean logged = false;
	private static boolean tmp_log;
	private static String id_tmp;
	private String str_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
		init();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		logged = tmp_log;
		str_id = id_tmp;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		tmp_log = logged;
		id_tmp = str_id;
	}

	public void init() {
		
		ImageButton btn_teacher = (ImageButton) findViewById(R.id.btn_teacher);
		ImageButton btn_student = (ImageButton) findViewById(R.id.btn_student);
		btn_teacher.setOnClickListener(modeChoose);
		btn_student.setOnClickListener(modeChoose);

	}

	private OnClickListener modeChoose = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch (v.getId()) {
			case R.id.btn_teacher:
				if (logged == false) {
					openOptionDialog("t");
				}
				else {
					if(str_id.equals("t")) {
						Intent intent_teacher = new Intent();
	    				intent_teacher.setClass(MainActivity.this, TeacherActivity.class);
	    				startActivity(intent_teacher); 
	    				MainActivity.this.finish(); 
					}
					else if(str_id.equals("s")) {
						Toast.makeText(MainActivity.this, "抱歉!學生只能進入學生的頁面喔", Toast.LENGTH_SHORT).show();
					}
					else {
					}
				}
				break;
			case R.id.btn_student:
				if (logged == false) {
					openOptionDialog("s");
				}
				else {
					if(str_id.equals("t")) {
						Toast.makeText(MainActivity.this, "抱歉!老師只能進入老師的頁面喔", Toast.LENGTH_SHORT).show();
					}
					else if(str_id.equals("s")) {
						Intent intent_student = new Intent();
						intent_student.setClass(MainActivity.this, StudentActivity.class);
						startActivity(intent_student);
						MainActivity.this.finish(); 
					}
					else {
					}
				}
				break;

			default:
				break;
			}

		}
	};
	
	public void openOptionDialog(final String mode) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		LayoutInflater inflater = LayoutInflater.from(this);
		View alert_view = inflater.inflate(R.layout.log_in_view,null);
		dialog.setView(alert_view);
		
		final EditText input_name=(EditText)alert_view.findViewById(R.id.log_in_name);
		final EditText input_password=(EditText)alert_view.findViewById(R.id.log_in_password);   
	      
	    dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
	        public void onClick( DialogInterface dialoginterface, int i) {
	            Toast.makeText(MainActivity.this, "登錄中，請稍等", 1000).show();
	        	ParseQuery<ParseObject> query = ParseQuery.getQuery("UserData");
	        	query.whereEqualTo("name", input_name.getText().toString());
	        	query.whereEqualTo("password", input_password.getText().toString());
	        	query.getFirstInBackground(new GetCallback<ParseObject>() {
					@Override
					public void done(ParseObject object, ParseException e) {
						// TODO Auto-generated method stub
						if (object == null) {
	        	            Log.d("!!", "此人不存在");
	        	            logged = false;
	        	            Toast.makeText(MainActivity.this, "登錄失敗，請再試一次", Toast.LENGTH_SHORT).show();
	        	        } else {
	        	            Log.d("!!", "水唷");
	        	            str_id = object.getString("identification");
	        	            id_tmp = str_id;
	        	            if(mode.equals("t") && str_id.equals("t")) {
	        		        	logged = true;
	        	            	Toast.makeText(MainActivity.this, "登錄成功，老師您好", Toast.LENGTH_SHORT).show();
	        	            	Intent intent_teacher = new Intent();
		        				intent_teacher.setClass(MainActivity.this, TeacherActivity.class);
		        				startActivity(intent_teacher);
		        				MainActivity.this.finish(); 
	        	            }
	        	            else if(mode.equals("s") && str_id.equals("s")) {
	        		        	logged = true;
	        	            	Toast.makeText(MainActivity.this, "登錄成功，同學你好", Toast.LENGTH_SHORT).show();
	        	            	Intent intent_student = new Intent();
	        					intent_student.setClass(MainActivity.this, StudentActivity.class);
	        					startActivity(intent_student);
	        					MainActivity.this.finish(); 
	        	            }
	        	            else {
	        	            	logged = false;
	        	            	Toast.makeText(MainActivity.this, "登錄失敗，請再試一次", Toast.LENGTH_SHORT).show();
	        	            }
	        	        }
					}
	        	});      
	        }
	    } );
	    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface arg0, int arg1) {
	    		logged = false;
	    	}
	    });
	    
	    dialog.show();
	}
	

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
