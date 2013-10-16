package com.dyang.fourband;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Properties;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	String[] mainMenu = { "Find Value By Color", "Find Color By Value", "View Custom List", "Help", "Rate This App" };

	String mode;

	private TextView modeText;
	private String[] sets;
	private Properties props;
	private String PACKAGE_NAME;
	private String APP_NAME;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		ListView listView = (ListView) findViewById(R.id.mainlist1);

		listView.setDividerHeight(1);

		listView.setAdapter(new MenuAdapter(this, R.layout.main, mainMenu));

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == 0 && mode.equals("4")) {
					Intent myIntent = new Intent(MainActivity.this, ColorActivity.class);
					MainActivity.this.startActivity(myIntent);
				}
				if (arg2 == 0 && mode.equals("5")) {
					Intent myIntent = new Intent(MainActivity.this, ColorActivity5.class);
					MainActivity.this.startActivity(myIntent);
				} else if (arg2 == 1 && mode.equals("4")) {
					Intent myIntent = new Intent(MainActivity.this, ValueActivity.class);
					MainActivity.this.startActivity(myIntent);
				} else if (arg2 == 1 && mode.equals("5")) {
					Intent myIntent = new Intent(MainActivity.this, ValueActivity5.class);
					MainActivity.this.startActivity(myIntent);
				} else if (arg2 == 2) {
					Intent myIntent = new Intent(MainActivity.this, ListActivity.class);
					MainActivity.this.startActivity(myIntent);
				} else if (arg3 == 3) {
					Intent myIntent = new Intent(MainActivity.this, HelpActivity.class);
					MainActivity.this.startActivity(myIntent);
				} else if (arg3 == 4) {
					MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PACKAGE_NAME)));
				}
			}
		});

		init();

		PACKAGE_NAME = getApplicationContext().getPackageName();
		APP_NAME = getApplicationContext().getPackageManager().getApplicationLabel(getApplicationContext().getApplicationInfo()).toString();
		AppRater.app_launched(this, PACKAGE_NAME, APP_NAME);
	}

	@Override
	public void onResume() {
		updateMode(null, "init");
		super.onResume();
	}

	public void init() {

		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}

		if (mExternalStorageWriteable && mExternalStorageAvailable) {
			File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.dyang.fourband/files");

			if (!dir.exists())
				dir.mkdirs();

			File file = new File(dir, "resistor_list.txt");
			File initFile = new File(dir, "init.txt");

			StringBuilder input = new StringBuilder();
			StringBuilder output = new StringBuilder();

			if (!file.exists()) {
				try {
					initFile.createNewFile();
					return;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (initFile.exists())
				return;

			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = br.readLine()) != null) {
					input.append(line);
				}

				AssetManager assetManager = this.getResources().getAssets();
				InputStream is = assetManager.open("color.properties");
				Properties colorProps = new Properties();
				colorProps.load(is);

				String[] colorTable = null;

				sets = input.toString().split(";");

				int counter = 0;

				for (int i = 0; i < sets.length; i++) {
					try {
						String[] subSets = sets[i].trim().split(",");
						if (subSets[subSets.length - 1].equals("0"))
							subSets[subSets.length - 1] = "Any";
						colorTable = new String[subSets.length - 1];
						for (int x = 1; x < subSets.length; x++) {
							if (!subSets[x].trim().equals("Any")) {
								colorTable[x - 1] = colorProps.get(subSets[x].trim()).toString();
							} else {
								colorTable[x - 1] = "Any";
							}
						}
						// 4 Band
						if (subSets.length == 5) {
							is = assetManager.open("four.properties");
							props = new Properties();
							props.load(is);
							String first = props.get(colorTable[0] + ".1").toString();
							String second = props.get(colorTable[1] + ".2").toString();
							double third = Double.parseDouble(props.get(colorTable[2] + ".3").toString());
							double resistance = Integer.parseInt(first + second) * third;
							resistance /= 1000;
							resistance = adjustDouble(resistance, 3);
							output.append(counter++ + ":" + colorTable[0] + ":" + colorTable[1] + ":" + colorTable[2] + ":" + colorTable[3] + ":Val" + resistance + " k£[;\n");
						}
						// 5 Band
						else if (subSets.length == 6) {
							is = assetManager.open("five.properties");
							props = new Properties();
							props.load(is);
							String first = props.get(colorTable[0] + ".1").toString();
							String second = props.get(colorTable[1] + ".2").toString();
							String third = props.get(colorTable[2] + ".3").toString();
							double fourth = Double.parseDouble(props.get(colorTable[3] + ".4").toString());
							double resistance = Integer.parseInt(first + second + third) * fourth;
							resistance /= 1000;
							resistance = adjustDouble(resistance, 3);
							output.append(counter++ + ":" + colorTable[0] + ":" + colorTable[1] + ":" + colorTable[2] + ":" + colorTable[3] + ":" + colorTable[4] + ":Val" + resistance + " k£[;\n");
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
						continue;
					}
				}

				file.delete();

				FileOutputStream fOut = new FileOutputStream(file);
				String fOutString = output.toString();
				fOut.write(fOutString.getBytes());

				initFile.createNewFile();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mode_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.mode4) {
			updateMode("4", "update");
		} else if (item.getItemId() == R.id.mode5) {
			updateMode("5", "update");
		}
		return true;
	}

	public void updateMode(String input, String action) {
		try {
			boolean mExternalStorageAvailable = false;
			boolean mExternalStorageWriteable = false;
			String state = Environment.getExternalStorageState();

			if (Environment.MEDIA_MOUNTED.equals(state)) {
				mExternalStorageAvailable = mExternalStorageWriteable = true;
			} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				mExternalStorageAvailable = true;
				mExternalStorageWriteable = false;
			} else {
				mExternalStorageAvailable = mExternalStorageWriteable = false;
			}

			modeText = (TextView) findViewById(R.id.modeText);

			if (mExternalStorageWriteable && mExternalStorageAvailable) {
				File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.dyang.fourband/files");

				if (!dir.exists())
					dir.mkdirs();

				File file = new File(dir, "mode.txt");

				if (file.exists()) {
					try {
						if (action.equals("update")) {
							file.delete();
							FileOutputStream fOut = new FileOutputStream(file);
							String output = input;
							fOut.write(output.getBytes());
							fOut.close();
							mode = input;
							modeText.setText(mode + " Band Mode");
						} else {
							BufferedReader br = new BufferedReader(new FileReader(file));
							mode = br.readLine().trim();
							modeText.setText(mode + " Band Mode");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						if (action.equals("update")) {
							FileOutputStream fOut = new FileOutputStream(file);
							String output = input;
							fOut.write(output.getBytes());
							fOut.close();
							mode = input;
							modeText.setText(mode + " Band Mode");
						} else {
							FileOutputStream fOut = new FileOutputStream(file);
							String output = "4";
							fOut.write(output.getBytes());
							fOut.close();
							mode = "4";
							modeText.setText(mode + " Band Mode");
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				if (action.equals("update")) {
					mode = input;
					modeText.setText(mode + " Band Mode");
				} else {
					mode = "4";
					modeText.setText(mode + " Band Mode");
				}
			}

			if (mode.equals("4"))
				modeText.setBackgroundResource(R.color.Green);
			else
				modeText.setBackgroundResource(R.color.Orange);

			modeText.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (mode.equals("4")) {
						updateMode("5", "update");
					} else if (mode.equals("5")) {
						updateMode("4", "update");
					}
				}
			});

		} catch (NullPointerException npe) {
			return;
		}
	}

	public Double adjustDouble(double input, int decimalPlaces) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(decimalPlaces);
		df.setGroupingUsed(false);
		return Double.parseDouble(df.format(input));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public class MenuAdapter extends ArrayAdapter<String> {

		public MenuAdapter(Context context, int textViewResourceId, String[] objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.my_simple_list_item_2, parent, false);
			TextView label = (TextView) row.findViewById(R.id.rowItem);
			label.setText(mainMenu[position]);
			label.setTextColor(Color.BLACK);

			ImageView icon = (ImageView) row.findViewById(R.id.icon);

			if (mainMenu[position] == "Find Value By Color") {
				icon.setImageResource(R.drawable.color_icon);
			} else if (mainMenu[position] == "Find Color By Value") {
				icon.setImageResource(R.drawable.value_menu_icon);
			} else if (mainMenu[position] == "View Custom List") {
				icon.setImageResource(R.drawable.list_menu_icon);
			} else if (mainMenu[position] == "Help") {
				icon.setImageResource(R.drawable.help_menu_icon);
			}
			return row;
		}
	}
}