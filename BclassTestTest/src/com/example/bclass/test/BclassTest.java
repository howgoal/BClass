/**
 * 
 */
package com.example.bclass.test;

import android.test.ActivityInstrumentationTestCase2;

import com.example.bclass.MainActivity;

/**
 * @author JiaChing
 *
 */
public class BclassTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity MainActivity;


	public BclassTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	@Override
	 protected void setUp() throws Exception {
        super.setUp();
        MainActivity = getActivity();
        
    }
	public void testPreconditions() {
	    assertNotNull("MainActivity is null", MainActivity);
	    
	}

}
