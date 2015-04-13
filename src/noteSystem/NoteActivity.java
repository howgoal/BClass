package noteSystem;

import java.io.Console;

import bclassMain.MainActivity;
import bclassStudent.StudentActivity;

import com.example.bclass.R;
import com.parse.ParseObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 
 * @author JiaChing
 *
 */
public class NoteActivity extends Activity {
	boolean shouldRefresh = true, isRefreshing = false;
	boolean shouldLoadData = true, isLoadingData = false;

	private ListView listViewNote;
	private View viewFooter;
	private View viewHead;
	private ImageButton buttonCreateNote;
	private ImageButton buttonBack;

	private NoteListAdapter noteListAdapter;
	private Button buttonCreateReply;
	private String user = "jiachingTest";

	//private InternetDetector internetDetector;
	//private Boolean isInternetPresent = false;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		//internetDetector =InternetDetector.getInstance();
		// viewHead = LayoutInflater.from(this).inflate(R.layout.note_head,
		// null);
		// viewFooter = LayoutInflater.from(this).inflate(
		// R.layout.note_footer, null);

		listViewNote = (ListView) findViewById(R.id.note_listView);
		// listViewNote.addHeaderView(viewHead, null, false);
		// listViewNote.addFooterView(viewFooter, null, false);

		noteListAdapter = new NoteListAdapter(NoteActivity.this);

		buttonBack = (ImageButton) findViewById(R.id.note_buttonGoBack);
		buttonCreateNote = (ImageButton) findViewById(R.id.note_buttonCreate);
		buttonCreateNote.setOnClickListener(noteBarListener);
		buttonBack.setOnClickListener(noteBarListener);
		listViewNote.setAdapter(noteListAdapter);
	}

	/**
	 * 
	 * @param username
	 *            username
	 * @param message
	 *            user's message
	 */
	public void insertNote(String username, String message) {
		ParseObject note = new ParseObject("NoteSystem");
		note.put("author", username);
		note.put("message", message);
		note.put("rabbit_count", 1);
		note.put("reply_count", 0);
		note.put("is_Hidden", false);
		note.put("is_Delete", false);
		note.put("is_Solved", false);
		note.saveInBackground();
	}

	//
	// private ListView.OnScrollListener listViewScrollListener = new
	// OnScrollListener() {
	//
	// @Override
	// public void onScrollStateChanged(AbsListView view, int scrollState) {
	// if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
	// //當ListView拉到頂或底時
	// if (shouldRefresh) {//當ListView拉到頂
	// if (!isRefreshing) noteListAdapter.updateNoteDate();//沒在更新資料時
	//
	// listViewNote.setSelection(1);//不管更不更新，都移到第一項
	// }
	// if (shouldLoadData && !isLoadingData) {
	// noteListAdapter.updateNoteDate();
	// }
	// //當ListView拉到底,且沒在載入資料時
	// }
	//
	// }
	//
	// @Override
	// public void onScroll(AbsListView view, int firstVisibleItem,
	// int visibleItemCount, int totalItemCount) {
	// // TODO Auto-generated method stub
	// shouldLoadData = false;
	// shouldRefresh = false;
	// if (firstVisibleItem == 0) {// 拉到頂時
	// shouldRefresh = true;
	// } else if (firstVisibleItem + visibleItemCount == totalItemCount) {
	// // 拉到底時
	// if (listViewNote.getCount() < 60) {
	// shouldLoadData = true;
	// } else {// 只是測試用，如果超過60筆資料就不要再載入了
	// viewFooter.setVisibility(View.GONE);
	// listViewNote.removeFooterView(viewFooter);
	// shouldRefresh = false;
	// }
	// }
	// }
	// };

	private Button.OnClickListener noteBarListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch (v.getId()) {
			case R.id.note_buttonGoBack:
				Intent intent_back = new Intent();
				intent_back.setClass(NoteActivity.this, MainActivity.class);
				startActivity(intent_back);
				NoteActivity.this.finish();
				break;
			case R.id.note_buttonCreate:
				View customDialog = getLayoutInflater().inflate(
						R.layout.note_create_note_layout, null);
				final EditText editTextNote = (EditText) customDialog
						.findViewById(R.id.note_cteate_editText_message);
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
										insertNote(user, editTextNote.getText()
												.toString());
										noteListAdapter.updateNoteDate();
										noteListAdapter.notifyDataSetChanged();

									}

								}).create().show();
				break;
			default:

				break;
			}
		}
	};

}
