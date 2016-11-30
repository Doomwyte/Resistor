package com.dyang.fourband.helper;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class UnitConverter {

	public static float pixelToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		return px / (metrics.densityDpi / 160f);
	}

	public static int dpToPixel(float dp, Context context) {
		Resources r = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		return Math.round(px);
	}

}
