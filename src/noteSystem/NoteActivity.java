package noteSystem;

import java.io.Console;

import com.example.bclass.R;
import com.parse.ParseObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class NoteActivity extends Activity {
	private ListView listViewNote;
	private NoteListAdapter noteListAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_note);
		listViewNote = (ListView) findViewById(R.id.note_listView);
		noteListAdapter = new NoteListAdapter(NoteActivity.this);
		listViewNote.setAdapter(noteListAdapter);
//		ParseObject note = new ParseObject("NoteSystem");
//        note.put("author","jiaching");
//        note.put("message", "success");
//        note.put("rabbit_count",1);
//        note.put("reply_count", 0);
//        note.put("is_Hidden", false);
//        note.put("is_Delete", false);
//        note.put("is_Solved", false);
//        note.saveInBackground();
	}
	
	
}
