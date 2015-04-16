package noteSystemStudent;

import java.util.List;

import com.example.bclass.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class NoteListAdapter extends BaseAdapter {
	private List<Note> list_note;
	private Context noteContext;
	private LayoutInflater inflater;
	private ImageButton deleteButton;

	public NoteListAdapter(Context _context, List<Note> notes) {
		// TODO Auto-generated constructor stub
		noteContext = _context;
		inflater = LayoutInflater.from(_context);
		list_note = notes;
	}
	public void setList(List<Note> _list) {
		list_note = _list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.v("getCount", String.valueOf(list_note.size()));
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		NoteView view;
		if (convertView == null) {
			view = (NoteView) inflater.inflate(R.layout.note_row, null);
		} else {
			view = (NoteView) convertView;
		}
		Note item = getItem(position);
		view.show(item);
		deleteButton = view.imgBtnDelete;
		deleteButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(noteContext)
						.setTitle("確認刪除這則紙條嗎?")
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}

								})
						.setPositiveButton("確認",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										// note.delete();
										Log.v("delete", getItem(position).message);
										Note detNote = getItem(position);
										
										list_note.remove(detNote);
										detNote.delete();
										notifyDataSetChanged();
										Toast.makeText(noteContext, "資料刪除成功",
												1000).show();
									}

								}).create().show();

			}
		});
		return view;
	}
}
