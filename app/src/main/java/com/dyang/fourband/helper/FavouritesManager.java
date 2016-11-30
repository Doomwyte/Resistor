package com.dyang.fourband.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class FavouritesManager {

	private static final String FAVOURITES_KEY = "com.dyang.fourband.favourites";
	private static final String APP_PACKAGE = "com.dyang.fourband";

	public static void setFavourites(String first, String second, String third, String fourth, String tolerance, String resultText, Activity activity) {
		SharedPreferences prefs = activity.getApplicationContext().getSharedPreferences(APP_PACKAGE, Context.MODE_PRIVATE);
		String favouritesKey = FAVOURITES_KEY;

		String originalText = getFavourites(activity);
		String[] originalText_Array;

		int count = 0;
		if (originalText != null && !originalText.trim().equals("")) {
			originalText_Array = originalText.split(";");
			count = originalText_Array.length;
		}

		String output;
		if (fourth != null && !fourth.trim().equals("")) {
			output = originalText + count + ":" + first + ":" + second + ":" + third + ":" + fourth + ":" + tolerance + ":Val" + resultText + ";";
		} else {
			output = originalText + count + ":" + first + ":" + second + ":" + third + ":" + tolerance + ":Val" + resultText + ";";
		}

		prefs.edit().putString(favouritesKey, output).apply();

		CharSequence toastText = "Added To Saved List";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(activity, toastText, duration);
		toast.show();
	}

	private static void setFavourites(String newFavourites, Activity activity) {
		SharedPreferences prefs = activity.getApplicationContext().getSharedPreferences(APP_PACKAGE, Context.MODE_PRIVATE);
		String favouritesKey = FAVOURITES_KEY;
		prefs.edit().putString(favouritesKey, newFavourites).apply();
	}

	public static String getFavourites(Activity activity) {
		SharedPreferences prefs = activity.getApplicationContext().getSharedPreferences(APP_PACKAGE, Context.MODE_PRIVATE);
		String favouritesKey = FAVOURITES_KEY;
		return prefs.getString(favouritesKey, "");
	}

	public static void deleteItem(Activity activity, String index) {
		String favourites = getFavourites(activity);
		if (favourites != null && !favourites.trim().equals("")) {
			String[] favourites_list = favourites.split(";");
			StringBuilder newFavourites = new StringBuilder();
			for (String favourite : favourites_list) {
				if (!favourite.split(":")[0].equals(index)) {
					newFavourites.append(favourite).append(";");
				}
			}
			setFavourites(newFavourites.toString(), activity);
		}
	}

	public static void deleteAllItems(Activity activity) {
		SharedPreferences prefs = activity.getApplicationContext().getSharedPreferences(APP_PACKAGE, Context.MODE_PRIVATE);
		String favouritesKey = FAVOURITES_KEY;
		prefs.edit().putString(favouritesKey, "").apply();
	}
}
