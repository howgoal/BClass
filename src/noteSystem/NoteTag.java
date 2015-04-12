package noteSystem;

import android.view.TextureView;
import android.widget.Button;
import android.widget.TextView;

public class NoteTag {
	TextView tvUserName;
	TextView tvUserMessage;
	TextView tvRabbitCount;
	Button buttonDisaplyReply;
	public NoteTag(TextView _tvUserName,TextView _tvUserMessage,TextView _tvRabbitCount,
			Button _buttonDisaplyReply) {
		// TODO Auto-generated constructor stub
		tvUserName = _tvUserName;
		tvUserMessage = _tvUserMessage;
		tvRabbitCount = _tvRabbitCount;
		buttonDisaplyReply = _buttonDisaplyReply;
	}

}
