package com.dyang.fourband.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class FavouritesManager {

	public static void setFavourites(String first, String second, String third, String fourth, String tolerance, String resultText, Context context) {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other
			// states,
			// but all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}

		try {
			if (mExternalStorageAvailable && mExternalStorageWriteable) {
				File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.dyang.fourband/files");
				if (!dir.exists())
					dir.mkdirs();

				File file = new File(dir, "resistor_list.txt");
				StringBuilder text = new StringBuilder();

				int count = 0;

				if (file.exists()) {
					BufferedReader br = null;
					try {
						br = new BufferedReader(new FileReader(file));
						String line;
						while ((line = br.readLine()) != null) {
							count++;
							text.append(line);
							text.append('\n');
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (br != null) {
							br.close();
						}
					}
				}

				FileOutputStream fOut = new FileOutputStream(file);
				String output;
				if (fourth != null && !fourth.trim().equals("")) {
					output = text.toString() + count + ":" + first + ":" + second + ":" + third + ":" + fourth + ":" + tolerance + ":Val" + resultText + ";\n";
				} else {
					output = text.toString() + count + ":" + first + ":" + second + ":" + third + ":" + tolerance + ":Val" + resultText + ";\n";
				}
				fOut.write(output.getBytes());
				fOut.close();

				CharSequence toastText = "Added To Saved List";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, toastText, duration);
				toast.show();
			} else {
				CharSequence toastText = "SD Card Not Found";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, toastText, duration);
				toast.show();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
