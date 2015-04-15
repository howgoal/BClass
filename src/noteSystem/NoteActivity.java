package noteSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bclassMain.MainActivity;
import bclassStudent.StudentActivity;

import com.example.bclass.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * 
 * @author JiaChing
 *
 */
public class NoteActivity extends Activity {
	private String user = "jiachingTest";

	// private InternetDetector internetDetector;
	// private Boolean isInternetPresent = false;
	private List<Note> note_list;

	private Database database;
	private ImageButton imgBtnBack;
	private ImageButton imgBtnCreateNote;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_note);
		
		database = Database.getInstance();
		note_list = new ArrayList<Note>();
		
		imgBtnBack = (ImageButton) findViewById(R.id.note_buttonGoBack);
		imgBtnCreateNote = (ImageButton) findViewById(R.id.note_buttonCreate);
		
		imgBtnBack.setOnClickListener(noteBarListener);
		imgBtnCreateNote.setOnClickListener(noteBarListener);
		
		initData();
	}

	private void initData() {
		try {
			// get data from noteSystem

			List<ParseObject> getData = database.getQuery("NoteSystem").orderByDescending("createdAt").find();
			if (getData != null) {
				for (ParseObject i : getData) {
					note_list.add(new Note(i));
					ListView listview = (ListView) findViewById(R.id.note_listView);
					listview.setAdapter(new NoteListAdapter(NoteActivity.this,
							note_list));
				}

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
										

										note_list.add(0,new Note(createNote(editTextNote.getText().toString())));
										ListView listview = (ListView) findViewById(R.id.note_listView);
										listview.setAdapter(new NoteListAdapter(
												NoteActivity.this, note_list));

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
	public ParseObject createNote (String message) {
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

}
