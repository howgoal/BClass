package noteSystem;

import java.util.List;

import com.example.bclass.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.ProgressDialog;
import android.content.Context;
import android.renderscript.Type.CubemapFace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReplyAdapter extends BaseAdapter{
	private NoteDatabase noteDatabase;
	private Context context;
	private LayoutInflater inflater;
	private List<ParseObject> reply_list;
	private ParseQuery<ParseObject> query;
	public ReplyAdapter(Context _context,List<ParseObject> _list) {
		// TODO Auto-generated constructor stub
		noteDatabase = NoteDatabase.getInstance();
		inflater = inflater.from(_context);
		context = _context;
		reply_list = _list;
		////
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.v("size",String.valueOf(reply_list.size()));
		return reply_list.size();
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
			convertView = inflater.inflate(R.layout.note_reply_row, null);
		} 
		TextView tvUser = (TextView) convertView.findViewById(R.id.note_reply_row_tv_username);
		TextView tvMessage = (TextView) convertView.findViewById(R.id.note_reply_row_user_message);
		Log.v("test", reply_list.get(position).getString("message"));
		tvUser.setText(reply_list.get(position).getString("author"));
		tvMessage.setText(reply_list.get(position).getString("message"));
		return convertView;

	}

}
