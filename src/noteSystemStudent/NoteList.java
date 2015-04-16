package noteSystemStudent;

import java.util.List;

import com.parse.Parse;

public class NoteList {
	private List<Note> list;
    public static NoteList noteListInstance;
	private NoteList() {
		// TODO Auto-generated constructor stub
	}
	public static NoteList getInstance () {
		if(noteListInstance == null) {
			noteListInstance = new NoteList();
		}
		return noteListInstance;
	}
	public void setList(List<Note> _list) {
		list = _list;
	}
	public List<Note> getList() {
		return list;
	}
	public void removeObject(Parse object) {
		list.remove(object);
	}
	

}
