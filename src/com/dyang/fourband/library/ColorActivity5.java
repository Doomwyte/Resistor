package com.dyang.fourband.library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.dyang.fourband.library.adapter.DecimalAdapter;
import com.dyang.fourband.library.adapter.GenericAdapter;
import com.dyang.fourband.library.adapter.UnitAdapter;
import com.dyang.fourband.library.dm.RowDm;
import com.dyang.fourband.library.dm.UnitDm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TableRow.LayoutParams;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ColorActivity5 extends Activity {

	private Spinner firstSpinner, secondSpinner, thirdSpinner, multiplierSpinner, toleranceSpinner, unitSpinner,
			decimalSpinner;

	private TextView resultText, toleranceText, minmaxText;

	private TableRow colorImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set view to colour page
		setContentView(R.layout.color5);

		// Attach object to local variable
		firstSpinner = (Spinner) findViewById(R.id.spinner1);
		secondSpinner = (Spinner) findViewById(R.id.spinner2);
		thirdSpinner = (Spinner) findViewById(R.id.spinner3);
		multiplierSpinner = (Spinner) findViewById(R.id.spinner7);
		toleranceSpinner = (Spinner) findViewById(R.id.spinner4);
		unitSpinner = (Spinner) findViewById(R.id.spinner5);
		decimalSpinner = (Spinner) findViewById(R.id.spinner6);
		colorImage = (TableRow) findViewById(R.id.colorImage);

		// Initialise units array
		ArrayList<UnitDm> units = new ArrayList<UnitDm>();
		units.add(new UnitDm("nΩ", 0.000000001));
		units.add(new UnitDm("uΩ", 0.000001));
		units.add(new UnitDm("mΩ", 0.001));
		units.add(new UnitDm("Ω", 1));
		units.add(new UnitDm("kΩ", 1000));
		units.add(new UnitDm("MΩ", 1000000));

		// Initialise decimal array
		ArrayList<Integer> decimalPlaces = new ArrayList<Integer>();
		decimalPlaces.add(1);
		decimalPlaces.add(2);
		decimalPlaces.add(3);
		decimalPlaces.add(4);
		decimalPlaces.add(5);
		decimalPlaces.add(6);

		// Initialise first band array
		ArrayList<RowDm> firstBand = new ArrayList<RowDm>();
		firstBand.add(new RowDm("Brown", R.color.Brown, 1));
		firstBand.add(new RowDm("Red", R.color.Red, 2));
		firstBand.add(new RowDm("Orange", R.color.Orange, 3));
		firstBand.add(new RowDm("Yellow", R.color.Yellow, 4));
		firstBand.add(new RowDm("Green", R.color.Green, 5));
		firstBand.add(new RowDm("Blue", R.color.Blue, 6));
		firstBand.add(new RowDm("Violet", R.color.Violet, 7));
		firstBand.add(new RowDm("Grey", R.color.Grey, 8));
		firstBand.add(new RowDm("White", R.color.White, 9));

		// Initialise second band array
		ArrayList<RowDm> secondBand = new ArrayList<RowDm>();
		secondBand.add(new RowDm("Black", R.color.Black, 0));
		secondBand.add(new RowDm("Brown", R.color.Brown, 1));
		secondBand.add(new RowDm("Red", R.color.Red, 2));
		secondBand.add(new RowDm("Orange", R.color.Orange, 3));
		secondBand.add(new RowDm("Yellow", R.color.Yellow, 4));
		secondBand.add(new RowDm("Green", R.color.Green, 5));
		secondBand.add(new RowDm("Blue", R.color.Blue, 6));
		secondBand.add(new RowDm("Violet", R.color.Violet, 7));
		secondBand.add(new RowDm("Grey", R.color.Grey, 8));
		secondBand.add(new RowDm("White", R.color.White, 9));

		// Initialise third band array
		ArrayList<RowDm> thirdBand = new ArrayList<RowDm>();
		thirdBand.add(new RowDm("Black", R.color.Black, 0));
		thirdBand.add(new RowDm("Brown", R.color.Brown, 1));
		thirdBand.add(new RowDm("Red", R.color.Red, 2));
		thirdBand.add(new RowDm("Orange", R.color.Orange, 3));
		thirdBand.add(new RowDm("Yellow", R.color.Yellow, 4));
		thirdBand.add(new RowDm("Green", R.color.Green, 5));
		thirdBand.add(new RowDm("Blue", R.color.Blue, 6));
		thirdBand.add(new RowDm("Violet", R.color.Violet, 7));
		thirdBand.add(new RowDm("Grey", R.color.Grey, 8));
		thirdBand.add(new RowDm("White", R.color.White, 9));

		// Initialise third band array
		ArrayList<RowDm> multiplierBand = new ArrayList<RowDm>();
		multiplierBand.add(new RowDm("Black", R.color.Black, 1));
		multiplierBand.add(new RowDm("Brown", R.color.Brown, 10));
		multiplierBand.add(new RowDm("Red", R.color.Red, 100));
		multiplierBand.add(new RowDm("Orange", R.color.Orange, 1000));
		multiplierBand.add(new RowDm("Yellow", R.color.Yellow, 10000));
		multiplierBand.add(new RowDm("Green", R.color.Green, 100000));
		multiplierBand.add(new RowDm("Blue", R.color.Blue, 1000000));
		multiplierBand.add(new RowDm("Violet", R.color.Violet, 10000000));
		multiplierBand.add(new RowDm("Grey", R.color.Grey, 100000000));
		multiplierBand.add(new RowDm("White", R.color.White, 1000000000));
		multiplierBand.add(new RowDm("Gold", R.color.Gold, 0.1));
		multiplierBand.add(new RowDm("Silver", R.color.Silver, 0.01));

		// Initialise tolerance array
		ArrayList<RowDm> toleranceBand = new ArrayList<RowDm>();
		toleranceBand.add(new RowDm("Brown", R.color.Brown, 1));
		toleranceBand.add(new RowDm("Red", R.color.Red, 2));
		toleranceBand.add(new RowDm("Orange", R.color.Orange, 3));
		toleranceBand.add(new RowDm("Yellow", R.color.Yellow, 4));
		toleranceBand.add(new RowDm("Green", R.color.Green, 0.5));
		toleranceBand.add(new RowDm("Blue", R.color.Blue, 0.25));
		toleranceBand.add(new RowDm("Violet", R.color.Violet, 0.1));
		toleranceBand.add(new RowDm("Grey", R.color.Grey, 0.05));
		toleranceBand.add(new RowDm("Gold", R.color.Gold, 5));
		toleranceBand.add(new RowDm("Silver", R.color.White, 10));

		// Initiate Customized Adapter
		GenericAdapter firstBandAdapter = new GenericAdapter(this, R.layout.my_simple_spinner_dropdown_item, firstBand);
		GenericAdapter secondBandAdapter = new GenericAdapter(this, R.layout.my_simple_spinner_dropdown_item,
				secondBand);
		GenericAdapter thirdBandAdapter = new GenericAdapter(this, R.layout.my_simple_spinner_dropdown_item, thirdBand);
		GenericAdapter multiplierBandAdapter = new GenericAdapter(this, R.layout.my_simple_spinner_dropdown_item,
				multiplierBand);
		GenericAdapter toleranceBandAdapter = new GenericAdapter(this, R.layout.my_simple_spinner_dropdown_item,
				toleranceBand);
		UnitAdapter unitsAdapter = new UnitAdapter(this, R.layout.my_simple_spinner_dropdown_item, units);
		DecimalAdapter decimalAdapter = new DecimalAdapter(this, R.layout.my_simple_spinner_dropdown_item,
				decimalPlaces);

		// Attach object to adapter
		firstSpinner.setAdapter(firstBandAdapter);
		secondSpinner.setAdapter(secondBandAdapter);
		thirdSpinner.setAdapter(thirdBandAdapter);
		multiplierSpinner.setAdapter(multiplierBandAdapter);
		toleranceSpinner.setAdapter(toleranceBandAdapter);
		unitSpinner.setAdapter(unitsAdapter);
		decimalSpinner.setAdapter(decimalAdapter);

		// Set defaults
		unitSpinner.setSelection(4);
		decimalSpinner.setSelection(3);

		// Set Selection Listener
		firstSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		secondSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		thirdSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		multiplierSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		toleranceSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		unitSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		decimalSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		colorImage.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View arg0) {
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
						File dir = new File(Environment.getExternalStorageDirectory().getPath()
								+ "/Android/data/com.dyang.fourband/files");
						if (!dir.exists())
							dir.mkdirs();

						File file = new File(dir, "resistor_list.txt");
						StringBuilder text = new StringBuilder();

						int count = 0;

						if (file.exists()) {
							try {
								BufferedReader br = new BufferedReader(new FileReader(file));
								String line;
								while ((line = br.readLine()) != null) {
									count++;
									text.append(line);
									text.append('\n');
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						FileOutputStream fOut = new FileOutputStream(file);
						String output = text + Integer.toString(count) + ":"
								+ ((RowDm) firstSpinner.getSelectedItem()).getLabel() + ":"
								+ ((RowDm) secondSpinner.getSelectedItem()).getLabel() + ":"
								+ ((RowDm) thirdSpinner.getSelectedItem()).getLabel() + ":"
								+ ((RowDm) multiplierSpinner.getSelectedItem()).getLabel() + ":"
								+ ((RowDm) toleranceSpinner.getSelectedItem()).getLabel() + ":" + "Val"
								+ resultText.getText() + ";\n";
						fOut.write(output.getBytes());

						Context context = getApplicationContext();
						CharSequence toastText = "Added to custom list";
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(context, toastText, duration);
						toast.show();
					} else {
						Context context = getApplicationContext();
						CharSequence toastText = "SD Card not found";
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(context, toastText, duration);
						toast.show();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}
		});

		loadResistor();

	}

	public void loadResistor() {
		colorImage.removeAllViews();

		// Define layout params
		LayoutParams rowLayout = new LayoutParams(13, 100);
		rowLayout.topMargin = 8;
		rowLayout.bottomMargin = 8;
		rowLayout.leftMargin = 13;
		rowLayout.rightMargin = 13;

		/* Create a textview to be the row-content */
		TextView view1 = new TextView(this);
		view1.setLayoutParams(rowLayout);
		TextView view2 = new TextView(this);
		view2.setLayoutParams(rowLayout);
		TextView view3 = new TextView(this);
		view3.setLayoutParams(rowLayout);
		TextView view4 = new TextView(this);
		view4.setLayoutParams(rowLayout);
		TextView view5 = new TextView(this);
		view5.setLayoutParams(rowLayout);

		view1.setBackgroundResource(((RowDm) firstSpinner.getSelectedItem()).getColorInt());
		view2.setBackgroundResource(((RowDm) secondSpinner.getSelectedItem()).getColorInt());
		view3.setBackgroundResource(((RowDm) thirdSpinner.getSelectedItem()).getColorInt());
		view4.setBackgroundResource(((RowDm) multiplierSpinner.getSelectedItem()).getColorInt());
		view5.setBackgroundResource(((RowDm) toleranceSpinner.getSelectedItem()).getColorInt());

		/* Add texts to row. */
		colorImage.setGravity(Gravity.CENTER);
		colorImage.addView(view1);
		colorImage.addView(view2);
		colorImage.addView(view3);
		colorImage.addView(view4);
		colorImage.addView(view5);
	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

			// Initialisation
			resultText = (TextView) findViewById(R.id.resultText);
			toleranceText = (TextView) findViewById(R.id.toleranceText);
			minmaxText = (TextView) findViewById(R.id.minmaxText);
			double resultOhm = 0;

			// Mathematical Calculations
			int firstDigit = (int) ((RowDm) firstSpinner.getSelectedItem()).getResisInt();
			int secondDigit = (int) ((RowDm) secondSpinner.getSelectedItem()).getResisInt();
			int thirdDigit = (int) ((RowDm) thirdSpinner.getSelectedItem()).getResisInt();
			double multiplierDigit = ((RowDm) multiplierSpinner.getSelectedItem()).getResisInt();
			double toleranceDigit = ((RowDm) toleranceSpinner.getSelectedItem()).getResisInt();
			String firstThreeDigit = Integer.toString(firstDigit).concat(Integer.toString(secondDigit))
					.concat(Integer.toString(thirdDigit));
			resultOhm = Integer.valueOf(firstThreeDigit) * multiplierDigit;
			double range = resultOhm * toleranceDigit / 100;
			double upRange = resultOhm + range;
			double downRange = resultOhm - range;

			// View Processing
			String unitSign = ((UnitDm) unitSpinner.getSelectedItem()).getLabel();
			Integer decimalPlaces = Integer.valueOf(decimalSpinner.getSelectedItem().toString());
			resultText.setText(adjustDouble(resultOhm, decimalPlaces) + " " + unitSign);
			toleranceText.setText("Tolerance: " + toleranceDigit + "%" + " (" + adjustDouble(range, decimalPlaces)
					+ " " + unitSign + ")");
			minmaxText.setText("Range: " + adjustDouble(downRange, decimalPlaces) + " ~ "
					+ adjustDouble(upRange, decimalPlaces) + " " + unitSign);
			resultText.setTextSize(40);
			resultText.setTypeface(null, Typeface.BOLD);
			toleranceText.setTextSize(20);
			minmaxText.setTextSize(20);
			resultText.bringToFront();

			loadResistor();

		}

		@SuppressWarnings("rawtypes")
		public void onNothingSelected(AdapterView parent) {
			// Nothing
		}
	}

	public String adjustDouble(double input, int decimalPlaces) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(decimalPlaces);
		input = input / ((UnitDm) unitSpinner.getSelectedItem()).getMultiple();
		return df.format(input);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.modelist5_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.mode4) {
			updateMode("4", "update");
		} else {
			Intent myIntent = new Intent(ColorActivity5.this, ListActivity.class);
			ColorActivity5.this.startActivity(myIntent);
		}
		finish();
		return true;
	}

	public void updateMode(String input, String action) {
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
			File dir = new File(Environment.getExternalStorageDirectory().getPath()
					+ "/Android/data/com.dyang.fourband/files");

			if (!dir.exists())
				dir.mkdirs();

			File file = new File(dir, "mode.txt");

			if (file.exists())
				file.delete();

			try {
				FileOutputStream fOut = new FileOutputStream(file);
				String output = input;
				fOut.write(output.getBytes());

				Intent myIntent = new Intent(ColorActivity5.this, ColorActivity.class);
				ColorActivity5.this.startActivity(myIntent);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}