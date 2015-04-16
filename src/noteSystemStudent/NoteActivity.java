package noteSystemStudent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bclassMain.MainActivity;
import bclassStudent.StudentActivity;

import com.example.bclass.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * @author JiaChing
 *
 */
public class NoteActivity extends Activity implements OnScrollListener,Runnable {
	private String user = "jiachingTest";

	boolean shouldRefresh = true, isRefreshing = false;
	boolean shouldLoadData = true, isLoadingData = false;

	// private InternetDetector internetDetector;
	// private Boolean isInternetPresent = false;
	private List<Note> note_list;

	private Database database;
	private ImageButton imgBtnBack;
	private ImageButton imgBtnCreateNote;

	private View viewHead;
	private View viewFooter;
	private ListView listView;
	private int listViewCount = 5;
	private int loadDataCount = 5;
	private List<ParseObject> parseObjectsList;
	private Thread getDataThread;
	private Handler loadDataHandler;

	private NoteListAdapter listAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_note);

		listView = (ListView) findViewById(R.id.note_listView);
		viewHead = LayoutInflater.from(this).inflate(R.layout.note_head, null);
		viewFooter = LayoutInflater.from(this).inflate(R.layout.note_footer,
				null);

		listView.addHeaderView(viewHead, null, false);// 就addHeaderView...
		listView.addFooterView(viewFooter, null, false);// 就addFooterView...

		database = Database.getInstance();

		imgBtnBack = (ImageButton) findViewById(R.id.note_buttonGoBack);
		imgBtnCreateNote = (ImageButton) findViewById(R.id.note_buttonCreate);

		imgBtnBack.setOnClickListener(noteBarListener);
		imgBtnCreateNote.setOnClickListener(noteBarListener);

		initData();
		
		getDataThread = new Thread(this);
		loadDataHandler = new Handler() {
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
			
				ParseQuery<ParseObject> getDataQuery = database.getQuery("NoteSystem");
				getDataQuery.orderByDescending("createdAt");
				getDataQuery.setLimit(listViewCount);
				getDataQuery.findInBackground(new FindCallback<ParseObject>() {
				    public void done(List<ParseObject> scoreList, ParseException e) {
				        if (e == null) {
				        	note_list = new ArrayList<Note>();
				        	for (ParseObject i : scoreList) {
								note_list.add(new Note(i));
							}
				        	listAdapter.setList(note_list);
							listAdapter.notifyDataSetChanged();
							Log.v("update", "update");
				        } else {
				            Log.d("score", "Error: " + e.getMessage());
				        }
				    }
				});
				
	        }
	    };
		getDataThread.start();
	}

	private void initData() {
		try {
			// get data from noteSystem

			List<ParseObject> getData = database.getQuery("NoteSystem")
					.orderByDescending("createdAt").setLimit(listViewCount)
					.find();
			note_list = new ArrayList<Note>();
			
			if (getData != null) {
				for (ParseObject i : getData) {
					note_list.add(new Note(i));
				}
				listAdapter = new NoteListAdapter(NoteActivity.this,
						note_list);
				listView.setAdapter(listAdapter);
				listAdapter.notifyDataSetChanged();
				listView.setSelection(1);
				listView.setOnScrollListener(this);
			} else {
				Log.v("null", "null");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void loadData() {
		try {
			// get data from noteSystem

			List<ParseObject> getData = database.getQuery("NoteSystem")
					.orderByDescending("createdAt").setLimit(listViewCount)
					.find();
			note_list = new ArrayList<Note>();
			listView.setOnScrollListener(this);
			if (getData != null) {
				for (ParseObject i : getData) {
					note_list.add(new Note(i));
				}
				listAdapter.setList(note_list);
				listAdapter.notifyDataSetChanged();
				
			} else {
				Log.v("null", "null");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void refresh() {
		try {
			// get data from noteSystem

			List<ParseObject> getData = database.getQuery("NoteSystem")
					.orderByDescending("createdAt").setLimit(listViewCount)
					.find();
			note_list = new ArrayList<Note>();
			listView.setOnScrollListener(this);
			if (getData != null) {
				for (ParseObject i : getData) {
					note_list.add(new Note(i));
				}
				listAdapter.setList(note_list);
				listAdapter.notifyDataSetChanged();
			} else {
				Log.v("null", "null");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Button.OnClickListener noteBarListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
			case R.id.note_buttonGoBack:
				Intent intent_back = new Intent();
				intent_back.setClass(NoteActivity.this, StudentActivity.class);
				startActivity(intent_back);
				NoteActivity.this.finish();
				break;
			case R.id.note_buttonCreate:
				View customDialog = getLayoutInflater().inflate(
						R.layout.note_create_note_layout, null);
				final EditText editTextNote = (EditText) customDialog
						.findViewById(R.id.note_cteate_editText_message);
				final Toast toast = Toast.makeText(getApplicationContext(),
						"新增留言成功", Toast.LENGTH_SHORT);
				new AlertDialog.Builder(NoteActivity.this)
						.setView(customDialog)
						.setNegativeButton("cancel",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}

								})
						.setPositiveButton("Submit",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

										note_list
												.add(0, new Note(
														createNote(editTextNote
																.getText()
																.toString())));
										ListView listview = (ListView) findViewById(R.id.note_listView);
										listview.setAdapter(new NoteListAdapter(
												NoteActivity.this, note_list));
										toast.show();
										listView.setSelection(1);
										// noteListAdapter.updateNoteDate();
										// noteListAdapter.notifyDataSetChanged();

									}

								}).create().show();
				break;
			default:

				break;
			}
		}
	};

	public ParseObject createNote(String message) {
		ParseObject createNote = new ParseObject("NoteSystem");
		createNote.put("author", user);
		createNote.put("message", message);
		createNote.put("reply_count", 0);
		createNote.put("rabbit_count", 0);
		createNote.put("is_Hidden", false);
		createNote.put("is_Delete", false);
		createNote.put("is_solved", false);
		createNote.saveInBackground();
		return createNote;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			// 當ListView拉到頂或底時
			if (shouldRefresh) {// 當ListView拉到頂
				if (!isRefreshing) {
					initData();
					Toast.makeText(getApplicationContext(),
							"資料更新成功", 1000).show();
					refresh();//沒在更新資料時
				}
				listView.setSelection(1);// 不管更不更新，都移到第一項
			}
			if (shouldLoadData && !isLoadingData) {
				listViewCount += loadDataCount;
				loadData();
				listView.setSelection(listViewCount - loadDataCount);
				Toast.makeText(getApplicationContext(),
						"資料載入成功", 1000).show();
			}
			// 當ListView拉到底,且沒在載入資料時
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		shouldLoadData = false;
		shouldRefresh = false;
		if (firstVisibleItem == 0) {// 拉到頂時
			shouldRefresh = true;
		} else if (firstVisibleItem + visibleItemCount == totalItemCount) {
			// 拉到底時
			if (note_list.size() < 30) {
				shouldLoadData = true;
			} else {// 只是測試用，如果超過60筆資料就不要再載入了
				viewFooter.setVisibility(View.GONE);
				listView.removeFooterView(viewFooter);
				shouldRefresh = false;
			}
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true) {
				Thread.sleep(2000);
				Message msg = new Message();
				msg.what =1;
				loadDataHandler.sendMessage(msg);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
