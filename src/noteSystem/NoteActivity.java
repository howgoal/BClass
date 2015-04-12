package noteSystem;

import java.io.Console;

import com.example.bclass.R;
import com.parse.ParseObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
	private ListView listViewNote;
	private ImageButton buttonCreateNote;
	private ImageButton buttonBack;
	private NoteListAdapter noteListAdapter;
	private String user = "jiachingTest";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		
		
		listViewNote = (ListView) findViewById(R.id.note_listView);
		buttonBack = (ImageButton) findViewById(R.id.note_buttonGoBack);
		buttonCreateNote = (ImageButton) findViewById(R.id.note_buttonCreate);
		buttonCreateNote.setOnClickListener(createNoteListener);
		noteListAdapter = new NoteListAdapter(NoteActivity.this);
		listViewNote.setAdapter(noteListAdapter);
//		ParseObject note = new ParseObject("NoteSystem");
//        note.put("author","jiaching");
//        note.put("message", "success1");
//        note.put("rabbit_count",1);
//        note.put("reply_count", 0);
//        note.put("is_Hidden", false);
//        note.put("is_Delete", false);
//        note.put("is_Solved", false);
//        note.saveInBackground();
	}
	/**
	 * 
	 * @param username username
	 * @param message user's message
	 */
	public void insertNote(String username,String message) {
	  ParseObject note = new ParseObject("NoteSystem");
      note.put("author",username);
      note.put("message", message);
      note.put("rabbit_count",1);
      note.put("reply_count", 0);
      note.put("is_Hidden", false);
      note.put("is_Delete", false);
      note.put("is_Solved", false);
      note.saveInBackground();
	}
	
	
	
	private Button.OnClickListener createNoteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			View customDialog = getLayoutInflater().inflate(R.layout.note_create_note_layout, null);
			final EditText editTextNote =(EditText) customDialog.findViewById(R.id.note_cteate_editText_message);
			new AlertDialog.Builder(NoteActivity.this)
		    .setView(customDialog)
		    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
		    	
		    })
		    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					insertNote(user, editTextNote.getText().toString());
					noteListAdapter.updateNoteDate();
					noteListAdapter.notifyDataSetChanged();
				
				}
		    	
		    })
		    .create()
		    .show();
		}
	};

	
	
	
}
