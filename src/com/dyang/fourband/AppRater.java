package com.dyang.fourband;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

public class AppRater {
	private static String APP_TITLE;
	private static String APP_PNAME;

	private final static int DAYS_UNTIL_PROMPT = 0;
	private final static int LAUNCHES_UNTIL_PROMPT = 3;

	static Dialog dialog = null;

	public static void app_launched(Context mContext, String PACKAGE_NAME, String APP_NAME) {

		APP_PNAME = PACKAGE_NAME;
		APP_TITLE = APP_NAME;

		SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);

		if (prefs.getBoolean("dontshowagain", false)) {
			return;
		}

		SharedPreferences.Editor editor = prefs.edit();

		// Increment launch counter
		long launch_count = prefs.getLong("launch_count", 0) + 1;
		editor.putLong("launch_count", launch_count);

		// Get date of first launch
		Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
		if (date_firstLaunch == 0) {
			date_firstLaunch = System.currentTimeMillis();
			editor.putLong("date_firstlaunch", date_firstLaunch);
		}

		// Wait at least n days before opening
		if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
			if (System.currentTimeMillis() >= date_firstLaunch + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
				showRateDialog(mContext, editor);
			}
		}

		editor.commit();
	}

	public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("If " + APP_TITLE + " has helped you in any way, please take a moment to rate it. Thanks for your support!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if (editor != null) {
					editor.putBoolean("dontshowagain", true);
					editor.commit();
				}
				mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
				dialog.dismiss();
			}
		}).setNeutralButton("Remind Me", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				dialog.dismiss();
			}
		}).setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if (editor != null) {
					editor.putBoolean("dontshowagain", true);
					editor.commit();
				}
				dialog.dismiss();
			}
		});
		// Create the AlertDialog object and return it
		dialog = builder.create();
		dialog.show();
	}
}
