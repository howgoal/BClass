package bclassMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import com.example.bclass.R;
import com.parse.GetCallback;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ShowVoteActivity extends Activity {

	Dialog progressDialog;
	Bundle bundle;
	String objectId, voteResult, voteName;
	int[] votes;
	int voteChoice;
	SimpleAdapter myAdapter;
	List<HashMap<String, String>> resultList;
	HashMap<String, String> resultItem;
	TreeMap<Integer, String> resultSort;
	ResultAdapter adapter;
	ListView listView_result;
	TextView result_title;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_vote);

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
						voteResult = object.getString("result");
						Log.i("result", voteResult);
						voteChoice = object.getInt("choice");
						voteName = object.getString("name");
						String[] xx = voteResult.split(",");
						votes = new int[voteChoice + 1];
						for (int i = 1; i < xx.length; i++) {
							votes[Integer.parseInt(xx[i])] += 1; // 得票數
						}
						makeList();
						adapter = new ResultAdapter(ShowVoteActivity.this);
						listView_result = (ListView) findViewById(R.id.listView_result);
						result_title = (TextView) findViewById(R.id.result_title);
						result_title.setText(voteName);
						listView_result.setAdapter(adapter);
						// Log.i("result", Arrays.toString(yy));
					}
				}
			});
			return null;
		}

		@Override
		protected void onPreExecute() {
			ShowVoteActivity.this.progressDialog = ProgressDialog.show(
					ShowVoteActivity.this, "", "Loading...", true);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// Put the list of todos into the list view

			ShowVoteActivity.this.progressDialog.dismiss();
		}

	}

	public void makeList() {
		resultList = new ArrayList<HashMap<String, String>>();
		for (int i = 1; i < votes.length; i++) {
			resultItem = new HashMap<String, String>();
			resultItem.put("team_name", "第" + String.valueOf(i) + "組");
			resultItem.put("team_votes", String.valueOf(votes[i]));
			resultItem.put("vote_text", "票");
			resultList.add(resultItem);
		}

	}

	public final class MyView {
		public TextView team_name;
		public TextView team_votes;
		public TextView vote_text;
	}

	private class ResultAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		public ResultAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return resultList.size();
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
			convertView = inflater.inflate(R.layout.result_list_item, null);
			myviews.team_name = (TextView) convertView
					.findViewById(R.id.team_name);
			myviews.team_votes = (TextView) convertView
					.findViewById(R.id.team_votes);
			myviews.vote_text = (TextView) convertView
					.findViewById(R.id.vote_text);

			myviews.team_name
					.setText(resultList.get(position).get("team_name"));
			myviews.team_votes.setText(resultList.get(position).get(
					"team_votes"));
			myviews.vote_text
					.setText(resultList.get(position).get("vote_text"));

			return convertView;
		}
	}

}