package noteReplySystemStudent;

import noteSystemStudent.Database;
import android.app.DownloadManager.Query;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Reply {
	String author;
	String message;
	int rabbit_count;
	boolean is_Hidden = false;
	boolean is_Delete = false;
	boolean is_solved = false;
	ParseObject parent;
	private Database database;
	public Reply(ParseObject object) {
		// TODO Auto-generated constructor stub
		database = Database.getInstance();
		author = object.getString("author");
		message = object.getString("message");
//		parent = (ParseObject) object.get("parent_objectid");
//		Log.v("parent",parent.getString("message"));
		
	}


}
