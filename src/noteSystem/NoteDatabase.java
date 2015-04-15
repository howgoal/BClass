package noteSystem;

import java.util.List;

import android.util.Log;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class NoteDatabase {
	String databaseTable = "NoteSystem";
	public static NoteDatabase noteDatabaseInstance = null;
	ParseQuery<ParseObject> query;
	List<ParseObject> list;
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
	public ParseQuery<ParseObject> getQuery(String table) {
		return ParseQuery.getQuery(table);
	}
	public List<ParseObject> getReplyListByParent_id(String _objectId) {
		try {
			query= getQuery("NoteSystem");
			list = query.whereEqualTo("objectId", _objectId).find();
			query = getQuery("NoteReplySystem");
			list = query.whereEqualTo("parent_objectId", list.get(0)).find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	

}
