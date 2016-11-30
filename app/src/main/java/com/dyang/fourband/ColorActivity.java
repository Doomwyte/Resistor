package com.dyang.fourband;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.dyang.fourband.R;
import com.dyang.fourband.helper.FavouritesManager;
import com.dyang.fourband.helper.ModeManager;
import com.dyang.fourband.library.adapter.DecimalAdapter;
import com.dyang.fourband.library.adapter.GenericAdapter;
import com.dyang.fourband.library.adapter.UnitAdapter;
import com.dyang.fourband.library.dm.RowDm;
import com.dyang.fourband.library.dm.UnitDm;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
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

public class ColorActivity extends AbstractActivity {

	private Spinner firstSpinner, secondSpinner, thirdSpinner, multiplierSpinner, toleranceSpinner, unitSpinner, decimalSpinner;
	private TextView resultText;
	private TableRow colorImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set view to color page
		setContentView();
		ModeManager.updateMode(-1, this);
		initializeUI();
		loadResistor();
	}

	private void setContentView() {
		if (ModeManager.getMode() == 4) {
			setContentView(R.layout.color);
		} else {
			setContentView(R.layout.color5);
		}
	}

	private void initializeUI() {
		if (ModeManager.getMode() == 4) {
			initializeFourBandUI();
		} else {
			initializeFiveBandUI();
		}
	}

	private void initializeFourBandUI() {
		// Attach object to local variable
		firstSpinner = (Spinner) findViewById(R.id.spinner1);
		secondSpinner = (Spinner) findViewById(R.id.spinner2);
		multiplierSpinner = (Spinner) findViewById(R.id.spinner3);
		toleranceSpinner = (Spinner) findViewById(R.id.spinner4);
		unitSpinner = (Spinner) findViewById(R.id.spinner5);
		decimalSpinner = (Spinner) findViewById(R.id.spinner6);
		colorImage = (TableRow) findViewById(R.id.colorImage);

		// Initialize units array
		List<UnitDm> units = new ArrayList<>();
		units.add(new UnitDm("nΩ", 0.000000001));
		units.add(new UnitDm("uΩ", 0.000001));
		units.add(new UnitDm("mΩ", 0.001));
		units.add(new UnitDm("Ω", 1));
		units.add(new UnitDm("kΩ", 1000));
		units.add(new UnitDm("MΩ", 1000000));

		// Initialize decimal array
		List<Integer> decimalPlaces = new ArrayList<>();
		decimalPlaces.add(1);
		decimalPlaces.add(2);
		decimalPlaces.add(3);
		decimalPlaces.add(4);
		decimalPlaces.add(5);
		decimalPlaces.add(6);

		// Initialize first band array
		List<RowDm> firstBand = new ArrayList<>();
		firstBand.add(new RowDm("Brown", R.color.Brown, 1));
		firstBand.add(new RowDm("Red", R.color.Red, 2));
		firstBand.add(new RowDm("Orange", R.color.Orange, 3));
		firstBand.add(new RowDm("Yellow", R.color.Yellow, 4));
		firstBand.add(new RowDm("Green", R.color.Green, 5));
		firstBand.add(new RowDm("Blue", R.color.Blue, 6));
		firstBand.add(new RowDm("Violet", R.color.Violet, 7));
		firstBand.add(new RowDm("Grey", R.color.Grey, 8));
		firstBand.add(new RowDm("White", R.color.White, 9));

		// Initialize second band array
		List<RowDm> secondBand = new ArrayList<>();
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

		// Initialize third band array
		List<RowDm> thirdBand = new ArrayList<>();
		thirdBand.add(new RowDm("Black", R.color.Black, 1));
		thirdBand.add(new RowDm("Brown", R.color.Brown, 10));
		thirdBand.add(new RowDm("Red", R.color.Red, 100));
		thirdBand.add(new RowDm("Orange", R.color.Orange, 1000));
		thirdBand.add(new RowDm("Yellow", R.color.Yellow, 10000));
		thirdBand.add(new RowDm("Green", R.color.Green, 100000));
		thirdBand.add(new RowDm("Blue", R.color.Blue, 1000000));
		thirdBand.add(new RowDm("Violet", R.color.Violet, 10000000));
		thirdBand.add(new RowDm("Grey", R.color.Grey, 100000000));
		thirdBand.add(new RowDm("White", R.color.White, 1000000000));
		thirdBand.add(new RowDm("Gold", R.color.Gold, 0.1));
		thirdBand.add(new RowDm("Silver", R.color.Silver, 0.01));

		// Initialize tolerance array
		List<RowDm> toleranceBand = new ArrayList<>();
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
		GenericAdapter secondBandAdapter = new GenericAdapter(this, R.layout.my_simple_spinner_dropdown_item, secondBand);
		GenericAdapter thirdBandAdapter = new GenericAdapter(this, R.layout.my_simple_spinner_dropdown_item, thirdBand);
		GenericAdapter toleranceBandAdapter = new GenericAdapter(this, R.layout.my_simple_spinner_dropdown_item, toleranceBand);
		UnitAdapter unitsAdapter = new UnitAdapter(this, R.layout.my_simple_spinner_dropdown_item, units);
		DecimalAdapter decimalAdapter = new DecimalAdapter(this, R.layout.my_simple_spinner_dropdown_item, decimalPlaces);

		// Attach object to adapter
		firstSpinner.setAdapter(firstBandAdapter);
		secondSpinner.setAdapter(secondBandAdapter);
		multiplierSpinner.setAdapter(thirdBandAdapter);
		toleranceSpinner.setAdapter(toleranceBandAdapter);
		unitSpinner.setAdapter(unitsAdapter);
		decimalSpinner.setAdapter(decimalAdapter);

		// Set defaults
		unitSpinner.setSelection(4);
		decimalSpinner.setSelection(3);

		// Set Selection Listener
		firstSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		secondSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		multiplierSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		toleranceSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		unitSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		decimalSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		colorImage.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				FavouritesManager.setFavourites(((RowDm) firstSpinner.getSelectedItem()).getLabel(), ((RowDm) secondSpinner.getSelectedItem()).getLabel(),
						((RowDm) thirdSpinner.getSelectedItem()).getLabel(), null, ((RowDm) toleranceSpinner.getSelectedItem()).getLabel(), resultText.getText().toString(), ColorActivity.this);
				return true;
			}
		});
	}

	private void initializeFiveBandUI() {
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
		List<UnitDm> units = new ArrayList<>();
		units.add(new UnitDm("nΩ", 0.000000001));
		units.add(new UnitDm("uΩ", 0.000001));
		units.add(new UnitDm("mΩ", 0.001));
		units.add(new UnitDm("Ω", 1));
		units.add(new UnitDm("kΩ", 1000));
		units.add(new UnitDm("MΩ", 1000000));

		// Initialise decimal array
		List<Integer> decimalPlaces = new ArrayList<>();
		decimalPlaces.add(1);
		decimalPlaces.add(2);
		decimalPlaces.add(3);
		decimalPlaces.add(4);
		decimalPlaces.add(5);
		decimalPlaces.add(6);

		// Initialise first band array
		List<RowDm> firstBand = new ArrayList<>();
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
		List<RowDm> secondBand = new ArrayList<>();
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
		List<RowDm> thirdBand = new ArrayList<>();
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
		List<RowDm> multiplierBand = new ArrayList<>();
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
		List<RowDm> toleranceBand = new ArrayList<>();
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
		GenericAdapter secondBandAdapter = new GenericAdapter(this, R.layout.my_simple_spinner_dropdown_item, secondBand);
		GenericAdapter thirdBandAdapter = new GenericAdapter(this, R.layout.my_simple_spinner_dropdown_item, thirdBand);
		GenericAdapter multiplierBandAdapter = new GenericAdapter(this, R.layout.my_simple_spinner_dropdown_item, multiplierBand);
		GenericAdapter toleranceBandAdapter = new GenericAdapter(this, R.layout.my_simple_spinner_dropdown_item, toleranceBand);
		UnitAdapter unitsAdapter = new UnitAdapter(this, R.layout.my_simple_spinner_dropdown_item, units);
		DecimalAdapter decimalAdapter = new DecimalAdapter(this, R.layout.my_simple_spinner_dropdown_item, decimalPlaces);

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
				FavouritesManager.setFavourites(((RowDm) firstSpinner.getSelectedItem()).getLabel(), ((RowDm) secondSpinner.getSelectedItem()).getLabel(),
						((RowDm) thirdSpinner.getSelectedItem()).getLabel(), ((RowDm) multiplierSpinner.getSelectedItem()).getLabel(), ((RowDm) toleranceSpinner.getSelectedItem()).getLabel(),
						resultText.getText().toString(), ColorActivity.this);
				return true;
			}
		});
	}

	private void loadResistor() {
		if (ModeManager.getMode() == 4) {
			loadFourBandResistor();
		} else {
			loadFiveBandResistor();
		}
	}

	private void loadFourBandResistor() {
		colorImage.removeAllViews();

		// Define layout params
		int px_1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 86, getResources().getDisplayMetrics());
		int px_2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 71, getResources().getDisplayMetrics());
		int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
		int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13, getResources().getDisplayMetrics());
		LayoutParams rowLayout_1 = new LayoutParams(width, px_1);
		LayoutParams rowLayout_2 = new LayoutParams(width, px_2);
		rowLayout_1.leftMargin = margin;
		rowLayout_1.rightMargin = margin;
		rowLayout_2.leftMargin = margin;
		rowLayout_2.rightMargin = margin;

		/* Create a textview to be the row-content */
		TextView view1 = new TextView(this);
		TextView view2 = new TextView(this);
		TextView view3 = new TextView(this);
		TextView view4 = new TextView(this);

		/* Apply row layout params to each column */
		view1.setLayoutParams(rowLayout_1);
		view2.setLayoutParams(rowLayout_2);
		view3.setLayoutParams(rowLayout_2);
		view4.setLayoutParams(rowLayout_1);

		// Set color of each spinner
		view1.setBackgroundResource(((RowDm) firstSpinner.getSelectedItem()).getColorInt());
		view2.setBackgroundResource(((RowDm) secondSpinner.getSelectedItem()).getColorInt());
		view3.setBackgroundResource(((RowDm) multiplierSpinner.getSelectedItem()).getColorInt());
		view4.setBackgroundResource(((RowDm) toleranceSpinner.getSelectedItem()).getColorInt());

		// Add texts to row.
		colorImage.setGravity(Gravity.CENTER);
		colorImage.addView(view1);
		colorImage.addView(view2);
		colorImage.addView(view3);
		colorImage.addView(view4);
	}

	public void loadFiveBandResistor() {
		colorImage.removeAllViews();

		// Define layout params
		int px1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85, getResources().getDisplayMetrics());
		int px2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 74, getResources().getDisplayMetrics());
		int px3 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
		int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
		int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, getResources().getDisplayMetrics());

		LayoutParams rowLayout1 = new LayoutParams(width, px1);
		LayoutParams rowLayout2 = new LayoutParams(width, px2);
		LayoutParams rowLayout3 = new LayoutParams(width, px3);
		rowLayout1.leftMargin = margin;
		rowLayout1.rightMargin = margin;
		rowLayout2.leftMargin = margin;
		rowLayout2.rightMargin = margin;
		rowLayout3.leftMargin = margin;
		rowLayout3.rightMargin = margin;

		/* Create a textview to be the row-content */
		TextView view1 = new TextView(this);
		view1.setLayoutParams(rowLayout1);
		TextView view2 = new TextView(this);
		view2.setLayoutParams(rowLayout2);
		TextView view3 = new TextView(this);
		view3.setLayoutParams(rowLayout3);
		TextView view4 = new TextView(this);
		view4.setLayoutParams(rowLayout2);
		TextView view5 = new TextView(this);
		view5.setLayoutParams(rowLayout1);

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
			TextView toleranceText = (TextView) findViewById(R.id.toleranceText);
			TextView minmaxText = (TextView) findViewById(R.id.minmaxText);
			double resultOhm = 0;

			// Mathematical Calculations
			int firstDigit = (int) ((RowDm) firstSpinner.getSelectedItem()).getResisInt();
			int secondDigit = (int) ((RowDm) secondSpinner.getSelectedItem()).getResisInt();

			int thirdDigit = 0;
			if (ModeManager.getMode() == 5) {
				thirdDigit = (int) ((RowDm) thirdSpinner.getSelectedItem()).getResisInt();
			}

			double multiplierDigit = ((RowDm) multiplierSpinner.getSelectedItem()).getResisInt();
			double toleranceDigit = ((RowDm) toleranceSpinner.getSelectedItem()).getResisInt();

			String firstSectionDigits;
			if (ModeManager.getMode() == 4) {
				firstSectionDigits = Integer.toString(firstDigit).concat(Integer.toString(secondDigit));
			} else {
				firstSectionDigits = Integer.toString(firstDigit).concat(Integer.toString(secondDigit)).concat(Integer.toString(thirdDigit));
			}

			resultOhm = Integer.valueOf(firstSectionDigits) * multiplierDigit;
			double range = resultOhm * toleranceDigit / 100;
			double upRange = resultOhm + range;
			double downRange = resultOhm - range;

			// View Processing
			String unitSign = ((UnitDm) unitSpinner.getSelectedItem()).getLabel();
			Integer decimalPlaces = Integer.valueOf(decimalSpinner.getSelectedItem().toString());
			resultText.setText(adjustDouble(resultOhm, decimalPlaces) + " " + unitSign);
			toleranceText.setText("Tolerance: " + toleranceDigit + "%" + " (" + adjustDouble(range, decimalPlaces) + " " + unitSign + ")");
			minmaxText.setText("Range: " + adjustDouble(downRange, decimalPlaces) + " ~ " + adjustDouble(upRange, decimalPlaces) + " " + unitSign);
			resultText.setTypeface(null, Typeface.BOLD);
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
		inflater.inflate(R.menu.modelist_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.mode5) {
			ModeManager.updateMode(5, ColorActivity.this);
			Intent myIntent = getIntent();
			finish();
			startActivity(myIntent);
			return true;
		} else if (item.getItemId() == R.id.viewSavedList) {
			Intent myIntent = new Intent(this, ListActivity.class);
			startActivity(myIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}