package bclassMain;
import com.parse.ParseObject;


public class UserInstance {
	private static UserInstance userInstance = null;
	public String name;
	public String identification;
	private UserInstance() {
		// TODO Auto-generated constructor stub
	}
	public static UserInstance getInstance() {
		if(userInstance == null) {
			userInstance = new UserInstance();
		}
		return userInstance;
	}
	public void init(ParseObject user) {
		name = user.getString("name");
		identification = user.getString("identification");
	}

}
