package bclassMain;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import com.example.bclass.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GoExamActivity extends Activity {

	TextView textViewTitle, textViewDescrip, textViewDate;
	LinearLayout layoutCheckBox;
	String examName, examDescript, examDate;
	int examNumber, examTime;
	SimpleDateFormat sdf;
	Bundle bundle;
	String objectId;
	Dialog progressDialog;
	ImageButton btnSubmit, btnCancel;
	ParseObject todos;
	HashSet<Integer> examssSet;
	int[] examArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_go_exam);
		getActionBar().hide();
		sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		bundle = this.getIntent().getExtras();
		objectId = bundle.getString("objectId");
		new RemoteDataTask().execute();
	}

	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		// Override this method to do custom remote calls
		protected Void doInBackground(Void... params) {
			// Gets the current list of material_in in sorted order

			ParseQuery<ParseObject> query = ParseQuery.getQuery("Exam");
			Log.i("!", objectId);
			query.whereEqualTo("objectId", objectId);
			query.getFirstInBackground(new GetCallback<ParseObject>() {
				public void done(ParseObject object, ParseException e) {
					if (object == null) {
						Log.d("error", "The getFirst request failed.");
					} else {
						examName = object.getString("name");
						examDescript = object.getString("description");
						examDate = sdf.format(object.getCreatedAt());
						examNumber = object.getInt("choice");
						examTime = object.getInt("time");
						todos = object;
						Log.i("!!", examName);
					}
					init();
				}
			});
			return null;
		}

		@Override
		protected void onPreExecute() {
			GoExamActivity.this.progressDialog = ProgressDialog.show(
					GoExamActivity.this, "", "Loading...", true);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// Put the list of todos into the list view
			GoExamActivity.this.progressDialog.dismiss();
		}

	}

	public void init() {
		textViewTitle = (TextView) findViewById(R.id.examName);
		textViewDescrip = (TextView) findViewById(R.id.textDesrcipt);
		textViewDate = (TextView) findViewById(R.id.examDate);
		layoutCheckBox = (LinearLayout) findViewById(R.id.layoutCheckBox);
		btnSubmit = (ImageButton) findViewById(R.id.btnSubmit);
		btnCancel = (ImageButton) findViewById(R.id.btnCancel);
		examArray = new int[examNumber + 1];
		examssSet = new HashSet<Integer>();
		btnSubmit.setOnClickListener(examBtnFunction);
		btnCancel.setOnClickListener(examBtnFunction);
		textViewTitle.setText(examName);
		textViewDescrip.setText(examDescript);
		textViewDate.setText(examDate);
		Log.i("number", String.valueOf(examNumber));
		for (int i = 1; i < examNumber + 1; i++) {
			CheckBox checkBox = new CheckBox(this);
			checkBox.setText("選項 : " + i);
			checkBox.setOnClickListener(examCheck);
			checkBox.setId(i);
			layoutCheckBox.addView(checkBox);
		}
		for (int j = 0; j < examArray.length; j++) {
			examArray[j] = 0;
		}
	}

	private OnClickListener examCheck = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (((CheckBox) v).isChecked()) {
				examssSet.add(v.getId());
			} else {
				examssSet.remove(v.getId());
			}
		}

	};

	private OnClickListener examBtnFunction = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btnSubmit:
				// todos.put("name", extras.getString("name"));
				if (examssSet.size() < 1) {
					Toast.makeText(GoExamActivity.this, "請記得投票",
							Toast.LENGTH_SHORT);
				} else {
					Iterator iterator = examssSet.iterator();
					while (iterator.hasNext()) {
						examArray[(Integer) iterator.next()] += 1;
						Log.i("data", Arrays.toString(examArray));
					}

				}

				new RemoteDataTask() {
					protected Void doInBackground(Void... params) {
						try {
							for (int i = 0; i < examArray.length; i++) {
								if (examArray[i] > 0) {
									String result = todos.getString("result");
									result = result + String.valueOf("," + i);
									todos.put("result", result);
								}
							}

							todos.save();
						} catch (ParseException e) {
						}
						super.doInBackground();
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						// Put the list of todos into the list view
						Intent intent = new Intent();
						setResult(RESULT_OK, intent);
						finish();
					}
				}.execute();
				break;
			case R.id.btnCancel:
				finish();
				break;

			default:
				break;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.go_vote, menu);
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
