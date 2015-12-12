/*
 * This is an example test project created in Eclipse to test NotePad which is a sample 
 * project located in AndroidSDK/samples/android-11/NotePad
 * 
 * 
 * You can run these test cases either on the emulator or on device. Right click
 * the test project and select Run As --> Run As Android JUnit Test
 * 
 * @author Renas Reda, renas.reda@robotium.com
 * 
 */

package com.robotium.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;

import com.robotium.solo.Solo;

@SuppressWarnings("rawtypes")
public class NotePadTest extends ActivityInstrumentationTestCase2 {

	private static String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.android.camera.CameraLauncher";
	private Solo solo;

	@SuppressWarnings("unchecked")
	public NotePadTest() throws ClassNotFoundException {
		super(Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME));
	}

	@Override
	public void setUp() throws Exception {
		// setUp() is run before a test case is started.
		// This is where the solo object is created.
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		// tearDown() is run after a test case has finished.
		// finishOpenedActivities() will finish all the activities that have
		// been opened during the test execution.
		solo.finishOpenedActivities();
	}

	public void testFrontAndBackCamera() throws Exception {
		// Unlock the lock screen
		solo.unlockScreen();
		if (solo.searchText("NEXT")) {
			solo.clickOnText("NEXT");
		}

		ImageButton cameraStatus = null;
		ImageButton cameraStatus_2 = null;
		solo.clickOnImage(0);
		int count = 24 * 60 * 50;
		for (int i = 1; i <= count; i++) {
			cameraStatus = solo.getImageButton(0);
			System.out.println("1. Image button is: " + cameraStatus);

			solo.clickOnImageButton(0);

			cameraStatus_2 = solo.getImageButton(0);
			System.out.println("2. Image button is: " + cameraStatus_2);
			assertTrue("Fail to change camera",
					!cameraStatus.equals(cameraStatus_2));
			System.out.println("-----Already test time: " + i + "-----");
		}
		System.out.println("-----testFrontAndBackCamera_passed-----");
	}

	public void testEditNote() throws Exception {
		// Click on the second list line
		solo.clickInList(2);
		// Hides the soft keyboard
		solo.hideSoftKeyboard();
		// Change orientation of activity
		solo.setActivityOrientation(Solo.LANDSCAPE);
		// Change title
		solo.clickOnMenuItem("Edit title");
		// In first text field (0), add test
		solo.enterText(0, " test");
		solo.goBack();
		solo.setActivityOrientation(Solo.PORTRAIT);
		// (Regexp) case insensitive
		boolean noteFound = solo.waitForText("(?i).*?note 1 test");
		// Assert that Note 1 test is found
		assertTrue("Note 1 test is not found", noteFound);

	}

	public void testRemoveNote() throws Exception {
		// (Regexp) case insensitive/text that contains "test"
		solo.clickOnText("(?i).*?test.*");
		// Delete Note 1 test
		solo.clickOnMenuItem("Delete");
		// Note 1 test should not be found
		boolean noteFound = solo.searchText("Note 1 test");
		// Assert that Note 1 test is not found
		assertFalse("Note 1 Test is found", noteFound);
		solo.clickLongOnText("Note 2");
		// Clicks on Delete in the context menu
		solo.clickOnText("Delete");
		// Will wait 100 milliseconds for the text: "Note 2"
		noteFound = solo.waitForText("Note 2", 1, 100);
		// Assert that Note 2 is not found
		assertFalse("Note 2 is found", noteFound);
	}
}
