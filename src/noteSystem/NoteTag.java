package noteSystem;

import java.util.List;
import java.util.logging.Handler;

import org.w3c.dom.ls.LSException;

import com.example.bclass.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class NoteTag {

	TextView tvUserName;
	TextView tvUserMessage;
	TextView tvRabbitCount;
	Button buttonDisaplyReply;
	Button buttonCreateReply;
	Button buttonGetRabbit;
	private Context activityContext;
	private View convertView;
	private String objectId = "";
	private Boolean isGetRabbit = false;
	private NoteDatabase noteDatabase;
	private LayoutInflater inflater;
	private ListView listViewReply;
	private List<ParseObject> reply_list;
	private AlertDialog dialog;
	
	public NoteTag(View _convertView, Context _context) {
		// TODO Auto-generated constructor stub
		convertView = _convertView;
		activityContext = _context;
	
		noteDatabase = NoteDatabase.getInstance();
		inflater = LayoutInflater.from(activityContext);
		tvUserName = (TextView) convertView.findViewById(R.id.note_tv_username);
		tvUserMessage = (TextView) convertView
				.findViewById(R.id.note_tv_user_message);
		tvRabbitCount = (TextView) convertView
				.findViewById(R.id.tv_note_rabbit_count);
		buttonCreateReply = (Button) convertView
				.findViewById(R.id.btn_note_create_reply);
		buttonDisaplyReply = (Button) convertView
				.findViewById(R.id.btn_note_disply_reply);
		buttonGetRabbit = (Button) convertView
				.findViewById(R.id.btn_note_get_rabbit);

		buttonDisaplyReply.setOnClickListener(displyReplyListener);
		buttonGetRabbit.setOnClickListener(getRabbitListener);
		buttonCreateReply.setOnClickListener(createReplyListener);
		
		
		
	}

	public void setNoteID(String id) {
		objectId = id;
		
	}

	private Button.OnClickListener displyReplyListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			convertView = inflater.inflate(R.layout.note_reply_layout, null);
			listViewReply = (ListView) convertView.findViewById(R.id.note_reply_listview);
			listViewReply.setAdapter(new ReplyAdapter(activityContext, noteDatabase.getReplyListByParent_id(objectId)));
			
			final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activityContext);
			alertDialog.setView(convertView).create().show();
		}
	};
	private Button.OnClickListener createReplyListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			View customDialog = inflater.inflate(
					R.layout.note_create_reply_layout, null);
			 final TextView tvNoteMessage = (TextView)customDialog.findViewById(R.id.note_create_reply_origin_message);
			 final EditText editTextNote = (EditText) customDialog.findViewById(R.id.note_cteate_reply_edittext_message);
			 tvNoteMessage.setText(tvUserMessage.getText().toString());
			 new AlertDialog.Builder(activityContext)
					.setView(customDialog)
					.setNegativeButton("cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

								}

							})
					.setPositiveButton("Submit",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									 noteDatabase.insertReply(objectId,"jiachingreply",editTextNote.getText().toString());
									// noteListAdapter.updateNoteDate();
									// noteListAdapter.notifyDataSetChanged();

								}

							}).create().show();
		}

	};
	private Button.OnClickListener getRabbitListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int count = Integer.valueOf(tvRabbitCount.getText().toString());
			if (isGetRabbit) {
				tvRabbitCount.setText(String.valueOf(count - 1));
				buttonGetRabbit.setText("Me too");
				isGetRabbit = false;

			} else {
				tvRabbitCount.setText(String.valueOf(count + 1));
				buttonGetRabbit.setText("No problem");
				isGetRabbit = true;
			}

		}

	};
	
}
