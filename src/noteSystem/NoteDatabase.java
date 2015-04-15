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
	
	public void updateMessage(String objectId,String table,final String _message) {
		query = getQuery(table);
		query.getInBackground(objectId, new GetCallback<ParseObject>() {
		    public void done(ParseObject object, ParseException e) {
		        if (e == null) {
		            // Now let's update it with some new data. In this case, only cheatMode and score
		            // will get sent to the Parse Cloud. playerName hasn't changed.
		        	object.put("message", _message);
		        	object.saveInBackground();
		        }
		    }
		});
	}
	public void delete(String objectId,final String table) {
		query = getQuery(table);
		
		query.getInBackground(objectId, new GetCallback<ParseObject>() {
		    public void done(ParseObject object, ParseException e) {
		        if (e == null) {
		        	if(table.equals("NoteSystem")) {
		        		ParseQuery<ParseObject> query1 = getQuery("NoteReplySystem");
		        		query1.whereEqualTo("parent_objectId", object);
		        		 try {
							for (ParseObject reply_list : query1.find()) {
							        // This does not require a network access.
									Log.v("delete", reply_list.getObjectId());
							       reply_list.delete();
							    }
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		        	}
		        	object.deleteInBackground();	
		        }
		    }
		});
	}

}
