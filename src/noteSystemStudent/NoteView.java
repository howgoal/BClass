package noteSystemStudent;

import noteReplySystemStudent.ReplyListAdapter;

import com.example.bclass.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class NoteView extends LinearLayout {

	private TextView tvAuthor;
	private TextView tvMessage;
	private TextView tvRabbitCount;
	private Button btnGetRabbit;
	private Button btnCreateReply;
	private Button btnDisplayReply;
	private ImageButton imgBtnEditMessage;
	private ImageButton imgBtnDelete;
	private LayoutInflater inflater;
	private Note note;

	public NoteView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public NoteView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public NoteView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		inflater = LayoutInflater.from(getContext());
		tvAuthor = (TextView) findViewById(R.id.note_tv_username);
		tvMessage = (TextView) findViewById(R.id.note_tv_user_message);
		tvRabbitCount = (TextView) findViewById(R.id.note_tv_rabbit_count);
		// //button btnGetRabbit; btnCreateReply btnDisplayReply
		btnGetRabbit = (Button) findViewById(R.id.note_btn_get_rabbit);
		btnCreateReply = (Button) findViewById(R.id.note_btn_create_reply);
		btnDisplayReply = (Button) findViewById(R.id.note_btn_disply_reply);
		// button1.setOnClickListener(listener);
		// Image button imgBtnEditMessage imgBtnDelete
		imgBtnDelete = (ImageButton) findViewById(R.id.note_btn_delete_message);
		imgBtnEditMessage = (ImageButton) findViewById(R.id.note_btn_edit_message);

		// Listener

		btnGetRabbit.setOnClickListener(getRabbitListener);
		imgBtnEditMessage.setOnClickListener(editNoteListener);
		imgBtnDelete.setOnClickListener(deleteNoteListener);
		btnCreateReply.setOnClickListener(createReplyListener);
		btnDisplayReply.setOnClickListener(displyReplyListener);
	}

	public void show(Note _note) {
		note = _note;
		tvAuthor.setText(note.author);
		tvMessage.setText(note.message);
		tvRabbitCount.setText(String.valueOf(note.rabbit_count));
		Log.v("update", "updateRabbit");
		// button

		if (note.getIsClicked()) {
			// true : mean clicked => button show I have no problem
			btnGetRabbit.setText("I have no problem");
		} else {
			// false : mean unClicked=> button show Me too
			btnGetRabbit.setText("Me too");
		}
		if (note.reply_count > 0) {
			// show display message
			btnDisplayReply.setText("查看以下" + note.reply_count + "則回覆");
			btnDisplayReply.setClickable(true);
			btnDisplayReply.getBackground().setAlpha(255);
		} else {
			btnDisplayReply.setClickable(false);
			btnDisplayReply.setText("目前沒有回覆");
			btnDisplayReply.getBackground().setAlpha(0);
		}
		
		//note.update();
	}
	private Button.OnClickListener displyReplyListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			View convertView = inflater.inflate(R.layout.note_reply_layout, null);
			ListView listViewReply = (ListView) convertView
					.findViewById(R.id.note_reply_listview);
			listViewReply.setAdapter(new ReplyListAdapter(getContext(),note.getReplyList()));
			final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					getContext());
			alertDialog.setView(convertView).create().show();
		}
	};
	private Button.OnClickListener getRabbitListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int count = Integer.parseInt(tvRabbitCount.getText().toString());
			if (note.getIsClicked()) {
				// true : mean I click the button when button text = I have no
				// problem so rabbit-1
				note.setClickedStatus(false);
				tvRabbitCount.setText(String.valueOf(count - 1));
				btnGetRabbit.setText("Me too");
				note.updateRabbitCount(false);
			} else {
				// false : mean I click the button when button text = "Me too"
				// so rabbit+1
				// Change the note isClicked false
				note.setClickedStatus(true);
				tvRabbitCount.setText(String.valueOf(count + 1));
				btnGetRabbit.setText("I have no problem");
				note.updateRabbitCount(true);
			}
			note.update();
		}

	};
	private Button.OnClickListener editNoteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.v("editNoteListener", "clickmsg");

			View customDialog = inflater.inflate(
					R.layout.note_create_note_layout, null);
			final EditText editTextNote = (EditText) customDialog
					.findViewById(R.id.note_cteate_editText_message);
			// 設定文字內容和設定游標在末端
			editTextNote.setText(tvMessage.getText().toString());
			editTextNote.setSelection(tvMessage.getText().toString().length());
			new AlertDialog.Builder(getContext())
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
									//
									
									tvMessage.setText(editTextNote.getText()
											.toString());

									note.message = editTextNote.getText()
											.toString();
									note.updateMessage(editTextNote.getText()
											.toString());

								}

							}).create().show();

		}

	};
	private Button.OnClickListener deleteNoteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new AlertDialog.Builder(getContext())
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
									note.delete();
								}

							}).create().show();

		}

	};
	private Button.OnClickListener createReplyListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			View customDialog = inflater.inflate(
					R.layout.note_create_reply_layout, null);
			final TextView tvNoteMessage = (TextView) customDialog
					.findViewById(R.id.note_create_reply_origin_message);
			final EditText editTextNote = (EditText) customDialog
					.findViewById(R.id.note_cteate_reply_edittext_message);
			tvNoteMessage.setText(tvMessage.getText().toString());
			new AlertDialog.Builder(getContext())
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
									note.createReply("jiachingreply",editTextNote
											.getText().toString());
									note.reply_count +=1;
									// noteListAdapter.updateNoteDate();
									// noteListAdapter.notifyDataSetChanged();

								}

							}).create().show();
		}

	};
}
