package com.example.bclass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.os.Build;

public class GoVoteActivity extends Activity {

	TextView textViewTitle, textViewDescrip, textViewDate;
	LinearLayout layoutCheckBox;
	String voteName, voteDescript, voteDate;
	int voteNumber, voteTime;
	SimpleDateFormat sdf;
	Bundle bundle;
	String objectId;
	private Dialog progressDialog;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_go_vote);
		sdf = new SimpleDateFormat("yyyy/MM/dd hh:kk");
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
						voteNumber = object.getInt("choise");
						voteTime = object.getInt("time");
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
		textViewTitle.setText(voteName);
		textViewDescrip.setText(voteDescript);
		textViewDate.setText(voteDate);
		Log.i("number", String.valueOf(voteNumber));
		for (int i = 1; i < voteNumber + 1; i++) {
			CheckBox checkBox = new CheckBox(this);
			checkBox.setText("²Ä" + i + "²Õ");
			layoutCheckBox.addView(checkBox);
		}
	}

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
