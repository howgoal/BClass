package noteSystem;

import java.util.List;

import com.example.bclass.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoteListAdapter extends BaseAdapter {
	private Context activityContext;
	private LayoutInflater inflater;
	private List<ParseObject> note_list;
	ParseQuery<ParseObject> query;
	public NoteListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		activityContext = context;
		inflater = LayoutInflater.from(context);
		query = ParseQuery.getQuery("NoteSystem");
		try {
			note_list = query.find();
			Log.v("test",String.valueOf(note_list.size()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void updateNoteDate() {
		try {
			note_list = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return note_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = inflater.inflate(R.layout.note_row, null);
		TextView tv = (TextView) convertView.findViewById(R.id.note_tv_user_message);
		TextView tv2 = (TextView) convertView.findViewById(R.id.note_tv_username);
		tv.setText(note_list.get(position).getString("message"));
		tv2.setText(note_list.get(position).getString("author"));
		return convertView;
	}

}
