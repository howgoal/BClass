package noteReplySystem;

import com.example.bclass.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReplyView extends LinearLayout {
	private TextView tvAuthor;
	private TextView tvMessage;
	private Reply reply;
	public ReplyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ReplyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ReplyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		tvAuthor = (TextView) findViewById(R.id.note_reply_row_tv_username);
		tvMessage = (TextView) findViewById(R.id.note_reply_row_tv_message);
	}
	public void show(Reply _reply) {
		reply = _reply;
		tvAuthor.setText(reply.author);
		tvMessage.setText(reply.message);
	}

}
