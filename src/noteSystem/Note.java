package noteSystem;

import java.util.zip.Inflater;

import com.example.bclass.R;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Note {
	private Button btnCreate_reply;
	private int index;
	private NoteActivity noteActivity;
	private LayoutInflater inflater;
	public Note(Context _context,View v,int _index) {
		// TODO Auto-generated constructor stub
		index = _index;
		inflater = LayoutInflater.from(_context);
		btnCreate_reply = (Button) v.findViewById(R.id.btn_note_create_reply);
		btnCreate_reply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View customDialog = inflater.inflate(
						R.layout.note_create_note_layout, null);
				final EditText editTextNote = (EditText) customDialog
						.findViewById(R.id.note_cteate_editText_message);
				new AlertDialog.Builder(inflater.getContext())
						.setView(customDialog)
						.setPositiveButton("Submit",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}

								}).create().show();

			}
		});
	}

}
