package com.dyang.fourband;

import java.text.DecimalFormat;

import com.dyang.fourband.helper.ModeManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private String[] mainMenu;
	private String PACKAGE_NAME;
	private String APP_NAME;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ModeManager.updateMode(-1, this);

		mainMenu = getResources().getStringArray(R.array.mainMenu);

		ListView listView = (ListView) findViewById(R.id.mainlist1);
		listView.setDividerHeight(1);
		listView.setAdapter(new MenuAdapter(this, R.layout.main, mainMenu));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == 0 && ModeManager.mode == 4) {
					Intent myIntent = new Intent(MainActivity.this, ColorActivity.class);
					startActivity(myIntent);
				}
				if (arg2 == 0 && ModeManager.mode == 5) {
					Intent myIntent = new Intent(MainActivity.this, ColorActivity5.class);
					startActivity(myIntent);
				} else if (arg2 == 1 && ModeManager.mode == 4) {
					Intent myIntent = new Intent(MainActivity.this, ValueActivity.class);
					startActivity(myIntent);
				} else if (arg2 == 1 && ModeManager.mode == 5) {
					Intent myIntent = new Intent(MainActivity.this, ValueActivity5.class);
					startActivity(myIntent);
				} else if (arg2 == 2) {
					Intent myIntent = new Intent(MainActivity.this, ListActivity.class);
					startActivity(myIntent);
				} else if (arg3 == 3) {
					Intent myIntent = new Intent(MainActivity.this, HelpActivity.class);
					startActivity(myIntent);
				}
			}
		});

		PACKAGE_NAME = getApplicationContext().getPackageName();
		APP_NAME = getApplicationContext().getPackageManager().getApplicationLabel(getApplicationContext().getApplicationInfo()).toString();
		AppRater.app_launched(this, PACKAGE_NAME, APP_NAME);
	}

	@Override
	public void onResume() {
		ModeManager.updateMode(-1, this);
		super.onResume();
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
			ModeManager.updateMode(4, this);
			return true;
		} else if (item.getItemId() == R.id.mode5) {
			ModeManager.updateMode(5, this);
			return true;
		}
		return super.onOptionsItemSelected(item);
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

			if (mainMenu[position].equals(getResources().getString(R.string.findByColor))) {
				icon.setImageResource(R.drawable.color_icon);
			} else if (mainMenu[position].equals(getResources().getString(R.string.findByValue))) {
				icon.setImageResource(R.drawable.value_menu_icon);
			} else if (mainMenu[position].equals(getResources().getString(R.string.savedList))) {
				icon.setImageResource(R.drawable.list_menu_icon);
			} else if (mainMenu[position].equals(getResources().getString(R.string.help))) {
				icon.setImageResource(R.drawable.help_menu_icon);
			}
			return row;
		}
	}
}