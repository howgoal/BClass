package bclassTeacher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import bclassMain.GoExamActivity;
import bclassMain.NewExamActivity;
import bclassMain.ShowExamActivity;

import com.example.bclass.R;
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
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TExamActivity extends Activity {

	ListView listView1;
	ImageButton btnCreatExam;
	private List<ParseObject> exam_list;
	private Dialog progressDialog;
	SimpleAdapter myAdapter;
	List<HashMap<String, String>> examList;
	HashMap<String, String> examItem;
	SimpleDateFormat sdf;
	VoteAdapter adapter;
	Date curDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_texam);
		getActionBar().hide();
		sdf = new SimpleDateFormat("yyyy/MM/dd");
		init();
		new RemoteDataTask().execute();

	}

	public void init() {
		listView1 = (ListView) findViewById(android.R.id.list);
		btnCreatExam = (ImageButton) findViewById(R.id.btnCreatExam);
		btnCreatExam.setOnClickListener(creatExam);
	}

	private OnClickListener creatExam = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(TExamActivity.this, NewExamActivity.class);
			startActivity(intent);
			TExamActivity.this.finish();
		}

	};

	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		// Override this method to do custom remote calls
		protected Void doInBackground(Void... params) {
			// Gets the current list of material_in in sorted order
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Exam");
			query.orderByDescending("createdAt");

			try {
				exam_list = query.find();
			} catch (ParseException e) {

			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			TExamActivity.this.progressDialog = ProgressDialog.show(
					TExamActivity.this, "", "Loading...", true);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// Put the list of todos into the list view

			examList = new ArrayList<HashMap<String, String>>();
			examItem = new HashMap<String, String>();
			if (exam_list != null) {
				for (ParseObject in : exam_list) {
					String name = in.getString("name");
					String objectId = in.getObjectId();
					Date creatDate = in.getCreatedAt();
					long creatLong = creatDate.getTime();
					String dateString = sdf.format(creatDate);
					int time = in.getInt("time");
					examItem = new HashMap<String, String>();

					examItem.put("name", name);
					examItem.put("day", dateString);
					examItem.put("objectId", objectId);
					examItem.put("time", String.valueOf(time));
					examItem.put("creatLong", String.valueOf(creatLong));

					examList.add(examItem);
				}
			}

			adapter = new VoteAdapter(TExamActivity.this);
			listView1.setAdapter(adapter);

			TExamActivity.this.progressDialog.dismiss();
		}

	}

	public final class MyView {
		public TextView vote_name;
		public TextView vote_day;
		public ImageButton vote_detail;
		public ImageButton vote_result;
	}

	private class VoteAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		public VoteAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return examList.size();
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
			myviews.vote_result = (ImageButton) convertView
					.findViewById(R.id.vote_result);

			myviews.vote_name.setText(examList.get(position).get("name"));
			myviews.vote_day.setText(examList.get(position).get("day"));
			myviews.vote_detail.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String time = examList.get(position).get("time");
					String creatLong = examList.get(position).get("creatLong");
					Log.i("time", time);
					Log.i("creatLong", creatLong);

					if (checkDeadLine(creatLong, time)) {
						Intent intent = new Intent();
						intent.setClass(TExamActivity.this,
								GoExamActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("objectId", examList.get(position)
								.get("objectId"));
						// 將Bundle物件assign給intent
						intent.putExtras(bundle);
						startActivity(intent);
					} else {
						Toast.makeText(TExamActivity.this, "考試時間已過",
								Toast.LENGTH_SHORT).show();
					}

				}
			});
			myviews.vote_result.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String time = examList.get(position).get("time");
					String creatLong = examList.get(position).get("creatLong");
					Log.i("time", time);
					Log.i("creatLong", creatLong);

					if (checkDeadLine(creatLong, time)) {
						Toast.makeText(TExamActivity.this, "考試還沒結束",
								Toast.LENGTH_SHORT).show();
					} else {
						Intent intent = new Intent();
						intent.setClass(TExamActivity.this,
								ShowExamActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("objectId", examList.get(position)
								.get("objectId"));
						// 將Bundle物件assign給intent
						intent.putExtras(bundle);
						startActivity(intent);
					}

				}
			});

			return convertView;
		}

		public boolean checkDeadLine(String creatLong, String time) {
			curDate = new Date(System.currentTimeMillis());
			long plusTime = Long.parseLong(time);
			long startTime = Long.parseLong(creatLong);
			long curTime = curDate.getTime();
			Log.i("curTime", String.valueOf(curTime));
			if (curTime < (startTime + plusTime * 60 * 1000)) {
				return true;
			} else {
				return false;
			}

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (intent == null) {
			return;
		}
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
