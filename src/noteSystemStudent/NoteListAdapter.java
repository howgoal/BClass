package noteSystemStudent;

import java.util.List;

import com.example.bclass.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NoteListAdapter extends BaseAdapter {
	private List<Note> list_note;
	private Context noteContext;
	private LayoutInflater inflater;

	public NoteListAdapter(Context _context,List<Note> notes) {
		// TODO Auto-generated constructor stub
		noteContext = _context;
		inflater = LayoutInflater.from(_context);
		list_note = notes;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.v("getCount",String.valueOf(list_note.size()));
		return list_note.size();
	}

	@Override
	public Note getItem(int position) {
		// TODO Auto-generated method stub
		return list_note.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		NoteView view;
        if (convertView == null) {
            view = (NoteView) inflater.inflate(R.layout.note_row, null);
        } else {
            view = (NoteView) convertView;
        }
        Note item = getItem(position);
        view.show(item);
        return view;
	}

}
