package com.dyang.fourband.helper;

import com.dyang.fourband.AbstractActivity;
import com.dyang.fourband.ColorActivity;
import com.dyang.fourband.R;
import com.dyang.fourband.ValueActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ModeManager extends AbstractActivity {

	private static int mode;
	private static TextView modeText;
	private static Activity mActivity;

	public static int getMode() {
		return mode;
	}
	
	public static void updateMode(int modeToApply, Activity activity) {
		mActivity = activity;

		SharedPreferences prefs = activity.getApplicationContext().getSharedPreferences("com.dyang.fourband", Context.MODE_PRIVATE);
		String modeKey = "com.dyang.fourband.mode";

		if (modeToApply == -1) {
			mode = prefs.getInt(modeKey, 4);
		} else {
			prefs.edit().putInt(modeKey, modeToApply).apply();
			mode = modeToApply;
		}

		modeText = (TextView) activity.findViewById(R.id.modeText);
		modeText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				// Set mode
				if (mode == 4) {
					updateMode(5, mActivity);
				} else {
					updateMode(4, mActivity);
				}
				
				// Get intent
				Intent myIntent = null;
				if (mActivity instanceof ColorActivity) {
					myIntent = new Intent(mActivity, ColorActivity.class);
				} else if (mActivity instanceof ValueActivity) {
					myIntent = new Intent(mActivity, ValueActivity.class);
				}
				
				// Start intent
				if (myIntent != null) {
					mActivity.startActivity(myIntent);
					mActivity.finish();
				}
				
			}
		});
		setModeText();
	}

	public static void setModeText() {
		if (mode == 4) {
			modeText.setText(R.string.fourBandMode);
			modeText.setBackgroundResource(R.color.Green);
		} else {
			modeText.setText(R.string.fiveBandMode);
			modeText.setBackgroundResource(R.color.Orange);
		}
	}

}
