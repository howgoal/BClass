package noteSystemStudent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import bclassTeacher.TVoteActivity;

import com.example.bclass.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DeleteAyncTask extends AsyncTask<Void, Void, Void> {
	private Context context;
	private ProgressDialog dialog;
	private String id;
	private Database database;
	
	public DeleteAyncTask(Context _context,String _id) {
		super();
		context = _context;
		id = _id;
		database = Database.getInstance();
		
	}
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		//database.deleteNotInBackground(id, "NoteSystem");
		return null;
	}


	@Override
	protected void onPreExecute() {
		dialog  = ProgressDialog.show(context, "留言刪除中","請稍後....");
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Void result) {
		// Put the list of todos into the list view
		NoteActivity noteActivity = (NoteActivity) context;
		ListView listview = (ListView) noteActivity.findViewById(R.id.note_listView);
		ListAdapter noteListAdapter = listview.getAdapter();
	//noteListAdapter.notify
//		try {
//			List<ParseObject> getData = database.getQuery("NoteSystem").find();
//			List<Note> note_list = new ArrayList<Note>();
//		
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//				.orderByDescending("createdAt").setLimit(listViewCount)
//				.find();
//		note_list = new ArrayList<Note>();
		
//		try {
//			// get data from noteSystem
//
//			List<ParseObject> getData = database.getQuery("NoteSystem")
//					.orderByDescending("createdAt").setLimit(listViewCount)
//					.find();
//			note_list = new ArrayList<Note>();
//			listview.setOnScrollListener(this);
//			if (getData != null) {
//				for (ParseObject i : getData) {
//					note_list.(new Note(i));
//					listview.setAdapter(new NoteListAdapter(NoteActivity.this,
//							note_list));
//					listview.setSelection(1);
//				}
//			
//
//			} else {
//				Log.v("null", "null");
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		listview.setAdapter(new NoteListAdapter(context, notes));
		
	}
	public ParseObject createNote(String message) {
		ParseObject createNote = new ParseObject("NoteSystem");
		createNote.put("author", "test");
		createNote.put("message", message);
		createNote.put("reply_count", 0);
		createNote.put("rabbit_count", 0);
		createNote.put("is_Hidden", false);
		createNote.put("is_Delete", false);
		createNote.put("is_solved", false);
		createNote.saveInBackground();
		Log.v("updateDatabase","createNote");
		return createNote;
	}
}
