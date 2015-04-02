package com.example.bclass;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class BrightApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);

		Parse.initialize(this, "sMIswsh0Bv2e9C7U4CaceBqf2GkjSNxv5kGVCmqj",
				"drVxTlQi3Lq2e01lg0vjiUat4uGTcFKnQeBHfKvM");

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		// defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	}
}
