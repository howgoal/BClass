package noteSystem;

import com.example.bclass.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoteListAdapter extends BaseAdapter {
	private Context activityContext;
	private LayoutInflater inflater;
	private String users[] = {"aaa","bbb","ccc"};
	public NoteListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		activityContext = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return users.length;
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
		TextView tv = (TextView) convertView.findViewById(R.id.userMessage);
		tv.setText(users[position]);
		return convertView;
	}

}
