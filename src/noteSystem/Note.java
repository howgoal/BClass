package noteSystem;

import java.util.ArrayList;
import java.util.List;

import noteReplySystem.Reply;
import android.widget.ListView;

import com.example.bclass.R;
import com.parse.ParseException;
import com.parse.ParseObject;

public class Note {
	// Database column
	String id;
	String author;
	String message;
	int reply_count;
	int rabbit_count;
	boolean is_Hidden = false;
	boolean is_Delete = false;
	boolean is_solved = false;
	List<ParseObject> getRabbit_lsit;
	// note self
	private boolean isClicked = false;

	private Database database;

	public Note(ParseObject object) {
		// TODO Auto-generated constructor stub
		id = object.getObjectId();
		author = object.getString("author");
		message = object.getString("message");
		reply_count = object.getInt("reply_count");
		rabbit_count = object.getInt("rabbit_count");
		is_Hidden = object.getBoolean("is_Hidden");
		is_Delete = object.getBoolean("is_Delete");
		is_solved = object.getBoolean("is_solved");

		database = Database.getInstance();

//		try {
//			getRabbit_lsit = database.getQuery("getRabbitSystem")
//					.whereEqualTo("rabbit_note_parent", object).find();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public boolean getIsClicked() {
		return isClicked;
	}

	public void setClickedStatus(Boolean click) {
		isClicked = click;
	}

	public void updateRabbitCount(boolean status) {
		database.updateCount(id, "NoteSystem", "rabbit_count", status);
	}
	public void updateMessage(String _message) {
		database.updateMessage(id, "NoteSystem", _message);
	}
	public void delete() {
		database.delete(id, "NoteSystem");
	}
	public void createReply(String _user,String _message) {

		database.createReply(id,_user,_message);
		//update reply count
		database.updateCount(id, "NoteSystem", "reply_count", true);
	}
	public List<Reply> getReplyList() {
		List<ParseObject> temp = database.getReplyListByParent_id(id);
		List<Reply> reply_list = new ArrayList<Reply>();
		if (temp != null) {
			for (ParseObject i : temp) {
				reply_list.add(new Reply(i));
			}
			
		}
		return reply_list;
	}

}
