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
import android.widget.Button;
import android.widget.TextView;

public class NoteListAdapter extends BaseAdapter {
	private Context activityContext;
	private LayoutInflater inflater;
	private List<ParseObject> note_list;
	private NoteTag noteTag;
	ParseQuery<ParseObject> query;

	public NoteListAdapter(Context context) {
		// TODO Auto-generated constructor stub

		activityContext = context;
		inflater = LayoutInflater.from(context);
		query = ParseQuery.getQuery("NoteSystem");
		try {
			note_list = query.find();
			Log.v("test", String.valueOf(note_list.size()));
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

		if (convertView == null) {
			// 取得listItem容器 view
			convertView = inflater.inflate(R.layout.note_row, null);

			// 建構listItem內容view
			noteTag = new NoteTag(
					(TextView) convertView.findViewById(R.id.note_tv_username),
					(TextView) convertView
							.findViewById(R.id.note_tv_user_message),
					(TextView) convertView
							.findViewById(R.id.tv_note_rabbit_count),
					(Button) convertView
							.findViewById(R.id.btn_note_create_reply));

			// 設置容器內容
			convertView.setTag(noteTag);

		} else {
			noteTag = (NoteTag) convertView.getTag();
		}

		Note note = new Note(activityContext, noteTag.buttonDisaplyReply,
				position);

		noteTag.tvUserName.setText(note_list.get(position).getString("author"));
		noteTag.tvUserMessage.setText(note_list.get(position).getString(
				"message"));
		noteTag.tvRabbitCount.setText(String.valueOf(note_list.get(position)
				.getInt("rabbit_count")));
		return convertView;
	}

}
