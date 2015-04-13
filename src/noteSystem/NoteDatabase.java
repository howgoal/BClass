package noteSystem;

import com.parse.ParseObject;
import com.parse.ParseQuery;

public class NoteDatabase {
	String databaseTable = "NoteSystem";
	public static NoteDatabase noteDatabaseInstance = null;
	ParseQuery<ParseObject> query;
	private NoteDatabase() {
		
		// TODO Auto-generated constructor stub
	}
	public static NoteDatabase getInstance() {
		if(noteDatabaseInstance == null) {
			noteDatabaseInstance = new NoteDatabase();
		}
		return noteDatabaseInstance;
	}
	public void insertNote(String username,String message) {
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
	public void insertReply(String id,String username,String message) {
		ParseObject note = new ParseObject("NoteReplySystem");
		note.put("author", username);
		note.put("message", message);
		note.put("rabbit_count", 1);
		note.put("is_Hidden", false);
		note.put("is_Delete", false);
		note.put("is_Solved", false);
		note.put("parent_objectId", ParseObject.createWithoutData("NoteSystem",id));
		note.saveInBackground();
	}
	
	public void update() {
		
	}
	public ParseQuery<ParseObject> getQuery() {
		return ParseQuery.getQuery("NoteSystem");
	}

}
