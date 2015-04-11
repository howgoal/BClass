package com.example.bclass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class TVoteActivity extends Activity {

	ListView listView1;
	Button btnCreatVote;
	private List<ParseObject> vote_list;
	private Dialog progressDialog;
	SimpleAdapter myAdapter;
	List<HashMap<String, String>> voteList;
	HashMap<String, String> voteItem;
	SimpleDateFormat sdf;
	VoteAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tvote);

		sdf = new SimpleDateFormat("yyyy/MM/dd");
		init();
		new RemoteDataTask().execute();

	}

	public void init() {
		listView1 = (ListView) findViewById(android.R.id.list);
		btnCreatVote = (Button) findViewById(R.id.btnCreatVote);
		btnCreatVote.setOnClickListener(creatVote);
	}

	private OnClickListener creatVote = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(TVoteActivity.this, NewVoteActivity.class);
			startActivity(intent); 
			TVoteActivity.this.finish(); 
		}

	};

	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		// Override this method to do custom remote calls
		protected Void doInBackground(Void... params) {
			// Gets the current list of material_in in sorted order
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Vote");
			query.orderByDescending("createdAt");

			try {
				vote_list = query.find();
				Log.i("!!", String.valueOf(query.count()));
			} catch (ParseException e) {

			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			TVoteActivity.this.progressDialog = ProgressDialog.show(
					TVoteActivity.this, "", "Loading...", true);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// Put the list of todos into the list view

			voteList = new ArrayList<HashMap<String, String>>();
			voteItem = new HashMap<String, String>();
			if (vote_list != null) {
				for (ParseObject in : vote_list) {
					String name = in.getString("name");
					String objectId = in.getObjectId();
					Date date = in.getCreatedAt();
					String dateString = sdf.format(date);
					Log.i("!!", dateString);
					voteItem = new HashMap<String, String>();

					voteItem.put("name", name);
					voteItem.put("day", dateString);
					voteItem.put("objectId", objectId);
					voteList.add(voteItem);
				}
			}

			adapter = new VoteAdapter(TVoteActivity.this);
			listView1.setAdapter(adapter);
			/*
			 * myAdapter = new SimpleAdapter(MainActivity.this, fridgeList,
			 * R.layout.fridge_list_item, new String[] { "name", "expiration",
			 * "due_day", "day" }, new int[] { R.id.material_name,
			 * R.id.material_expiration, R.id.due_day, R.id.day });
			 * listView1.setAdapter(myAdapter);
			 */

			TVoteActivity.this.progressDialog.dismiss();
		}

	}

	public final class MyView {
		public TextView vote_name;
		public TextView vote_day;
		public ImageButton vote_detail;
	}

	private class VoteAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		public VoteAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return voteList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			MyView myviews = null;
			myviews = new MyView();
			convertView = inflater.inflate(R.layout.vote_list_item, null);
			myviews.vote_name = (TextView) convertView
					.findViewById(R.id.vote_name);
			myviews.vote_day = (TextView) convertView
					.findViewById(R.id.vote_day);
			myviews.vote_detail = (ImageButton) convertView
					.findViewById(R.id.vote_detail);

			myviews.vote_name.setText(voteList.get(position).get("name"));
			myviews.vote_day.setText(voteList.get(position).get("day"));
			myviews.vote_detail.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(TVoteActivity.this, GoVoteActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("objectId",
							voteList.get(position).get("objectId"));
					// 將Bundle物件assign給intent
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});

			return convertView;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (intent == null) {
			return;
		}
		Toast.makeText(TVoteActivity.this, "感謝您的投票", Toast.LENGTH_SHORT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tvote, menu);
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
