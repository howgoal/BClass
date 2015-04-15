package noteReplySystem;

import java.util.List;

import noteSystem.Note;
import noteSystem.NoteView;

import com.example.bclass.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ReplyListAdapter extends BaseAdapter {

	private List<Reply> list_reply;
	private Context context;
	private LayoutInflater inflater;
	public ReplyListAdapter(Context _context,List<Reply> list) {
		// TODO Auto-generated constructor stub
		list_reply = list;
		context = _context;
		inflater = LayoutInflater.from(_context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_reply.size();
	}

	@Override
	public Reply getItem(int position) {
		// TODO Auto-generated method stub
		return list_reply.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ReplyView view;
        if (convertView == null) {
            view = (ReplyView) inflater.inflate(R.layout.note_reply_row, null);
        } else {
            view = (ReplyView) convertView;
        }
        Reply item = getItem(position);
        view.show(item);
        return view;
	}

}
