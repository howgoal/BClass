package bclassMain;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.example.bclass.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.R.integer;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class GoVoteActivity extends Activity {

	TextView textViewTitle, textViewDescrip, textViewDate;
	LinearLayout layoutCheckBox;
	String voteName, voteDescript, voteDate;
	int voteNumber, voteTime;
	SimpleDateFormat sdf;
	Bundle bundle;
	String objectId;
	Dialog progressDialog;
	ImageButton btnSubmit, btnCancel;
	ParseObject todos;
	HashSet<Integer> votessSet; 
	int[] voteArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_go_vote);
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

			ParseQuery<ParseObject> query = ParseQuery.getQuery("Vote");
			Log.i("!", objectId);
			query.whereEqualTo("objectId", objectId);
			query.getFirstInBackground(new GetCallback<ParseObject>() {
				public void done(ParseObject object, ParseException e) {
					if (object == null) {
						Log.d("error", "The getFirst request failed.");
					} else {
						voteName = object.getString("name");
						voteDescript = object.getString("description");
						voteDate = sdf.format(object.getCreatedAt());
						voteNumber = object.getInt("choice");
						voteTime = object.getInt("time");
						todos = object;
						Log.i("!!", voteName);
					}
					init();
				}
			});
			return null;
		}

		@Override
		protected void onPreExecute() {
			GoVoteActivity.this.progressDialog = ProgressDialog.show(
					GoVoteActivity.this, "", "Loading...", true);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// Put the list of todos into the list view
			GoVoteActivity.this.progressDialog.dismiss();
		}

	}

	public void init() {
		textViewTitle = (TextView) findViewById(R.id.textName);
		textViewDescrip = (TextView) findViewById(R.id.textDesrcipt);
		textViewDate = (TextView) findViewById(R.id.textDate);
		layoutCheckBox = (LinearLayout) findViewById(R.id.layoutCheckBox);
		btnSubmit = (ImageButton) findViewById(R.id.btnSubmit);
		btnCancel = (ImageButton) findViewById(R.id.btnCancel);
		voteArray = new int[voteNumber + 1];
		votessSet = new HashSet<Integer>();
		btnSubmit.setOnClickListener(voteBtnFunction);
		btnCancel.setOnClickListener(voteBtnFunction);
		textViewTitle.setText(voteName);
		textViewDescrip.setText(voteDescript);
		textViewDate.setText(voteDate);
		Log.i("number", String.valueOf(voteNumber));
		for (int i = 1; i < voteNumber + 1; i++) {
			CheckBox checkBox = new CheckBox(this);
			checkBox.setText("第" + i + "組");
			checkBox.setOnClickListener(voteCheck);
			checkBox.setId(i);
			layoutCheckBox.addView(checkBox);
		}
		for (int j = 0; j < voteArray.length; j++) {
			voteArray[j] = 0;
		}
	}

	private OnClickListener voteCheck = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (((CheckBox) v).isChecked()) {
				votessSet.add(v.getId());
			} else {
				votessSet.remove(v.getId());
			}
		}

	};

	private OnClickListener voteBtnFunction = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btnSubmit:
				// todos.put("name", extras.getString("name"));
				if (votessSet.size() < 1) {
					Toast.makeText(GoVoteActivity.this, "請記得投票",
							Toast.LENGTH_SHORT);
				} else {
					Iterator iterator = votessSet.iterator();
					while (iterator.hasNext()) {
						voteArray[(Integer) iterator.next()] += 1;
						Log.i("data", Arrays.toString(voteArray));
					}

				}

				new RemoteDataTask() {
					protected Void doInBackground(Void... params) {
						try {
							for (int i = 0; i < voteArray.length; i++) {
								if (voteArray[i] > 0) {
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
