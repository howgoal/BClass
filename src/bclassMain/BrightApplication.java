package bclassMain;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseRole;
import com.parse.ParseUser;

public class BrightApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		// Enable Local Datastore.
		// Parse.enableLocalDatastore(this);

		Parse.initialize(this, "xjE8p2v1Mfq3gQEKvlFFlU383yYKZbr6UFFjENh7",
				"edwKa636W00ibHkGAfk4hYhRO7r6U3DekAhFKI5L");

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		defaultACL.setPublicReadAccess(true);
		defaultACL.setPublicWriteAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	}
}
