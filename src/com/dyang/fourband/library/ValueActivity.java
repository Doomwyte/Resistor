package com.dyang.fourband.library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.dyang.fourband.library.adapter.ToleranceAdapter;
import com.dyang.fourband.library.adapter.UnitAdapter;
import com.dyang.fourband.library.dm.ResultDm;
import com.dyang.fourband.library.dm.RowDm;
import com.dyang.fourband.library.dm.SdDm;
import com.dyang.fourband.library.dm.UnitDm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class ValueActivity extends Activity implements OnClickListener {

	private EditText valueText;
	private Spinner valueUnit, valueTolerance;
	private Button valueEnter;
	private ArrayList<RowDm> firstBand, secondBand, thirdBand, toleranceBand;
	private ArrayList<UnitDm> units;
	private boolean firstMatched, secondMatched, thirdMatched, fourthMatched = false;
	private String[] infoText;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.value);

		valueText = (EditText) findViewById(R.id.valueText);
		valueUnit = (Spinner) findViewById(R.id.valueUnit);
		valueTolerance = (Spinner) findViewById(R.id.valueTolerance);
		valueEnter = (Button) findViewById(R.id.valueEnter);

		// Initialize first band array
		firstBand = new ArrayList<RowDm>();
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
		secondBand = new ArrayList<RowDm>();
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
		thirdBand = new ArrayList<RowDm>();
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
		toleranceBand = new ArrayList<RowDm>();
		toleranceBand.add(new RowDm("Exact (0%)", Color.TRANSPARENT, 0));
		toleranceBand.add(new RowDm("Red (2%)", R.color.Red, 2));
		toleranceBand.add(new RowDm("Gold (5%)", R.color.Gold, 5));
		toleranceBand.add(new RowDm("Silver (10%)", R.color.Silver, 10));
		toleranceBand.add(new RowDm("None (20%)", Color.TRANSPARENT, 20));

		// Initialize units array
		units = new ArrayList<UnitDm>();
		units.add(new UnitDm("nΩ", 0.000000001));
		units.add(new UnitDm("uΩ", 0.000001));
		units.add(new UnitDm("mΩ", 0.001));
		units.add(new UnitDm("Ω", 1));
		units.add(new UnitDm("kΩ", 1000));
		units.add(new UnitDm("MΩ", 1000000));

		UnitAdapter unitsAdapter = new UnitAdapter(this, R.layout.my_simple_spinner_dropdown_item, units);
		ToleranceAdapter toleranceAdapter = new ToleranceAdapter(this, R.layout.my_simple_spinner_dropdown_item,
				toleranceBand);

		valueUnit.setAdapter(unitsAdapter);
		valueUnit.setSelection(3);
		valueTolerance.setAdapter(toleranceAdapter);
		valueEnter.setOnClickListener(this);

		valueText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					valueEnter.performClick();
				}
				return false;
			}
		});
	}

	public void onClick(View arg0) {

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(valueText.getWindowToken(), 0);

		double input = 0;
		if (!valueText.getText().toString().equals("") && valueText.getText().toString() != null) {
			try {
				String input_String = valueText.getText().toString();
				input = Double.parseDouble(input_String);
			} catch (NumberFormatException nfe) {
				showAlert("Please enter a valid value.");
				return;
			}
			input *= ((UnitDm) valueUnit.getSelectedItem()).getMultiple();
		}

		if (input == 0) {
			showAlert("Please enter a valid value.");
			return;
		}

		if (input > 118800000000.0) {
			showAlert("Please enter a smaller value.");
			return;
		}

		LayoutParams rowLayout = new LayoutParams(13, 100);
		rowLayout.topMargin = 8;
		rowLayout.bottomMargin = 8;
		rowLayout.leftMargin = 13;
		rowLayout.rightMargin = 13;

		LayoutParams infoLayout = new LayoutParams();
		infoLayout.span = 6;

		LayoutParams preEndLayout = new LayoutParams(75, 100);

		LayoutParams seperator = new LayoutParams();
		seperator.height = 1;

		TableLayout tl = (TableLayout) findViewById(R.id.resultTable);
		tl.removeAllViews();

		// Third Band Processing
		double powOfTens;
		if (input >= 1)
			powOfTens = Math.pow(10, (int) Math.log10(input) - 1);
		else
			powOfTens = Math.pow(10, (int) Math.floor(Math.log10(input)) - 1);

		// First and Second Band Processing
		double firstTwoDigit = input / powOfTens;
		int firstDigit, secondDigit;
		if (firstTwoDigit >= 10) {
			firstDigit = Integer.valueOf(Double.toString(firstTwoDigit).substring(0, 1));
			secondDigit = Integer.valueOf(Double.toString(firstTwoDigit).substring(1, 2));
		} else if (firstTwoDigit >= 1) {
			firstDigit = Integer.valueOf(Double.toString(firstTwoDigit).substring(0, 1));
			secondDigit = 0;
		} else {
			String decimalNumber = Double.toString(firstTwoDigit).substring(
					Double.toString(firstTwoDigit).indexOf(".") + 1);
			firstDigit = Integer.valueOf(decimalNumber.substring(0, 1));
			secondDigit = Integer.valueOf(decimalNumber.substring(1, 2));
			powOfTens = 0.01;
		}

		if (((RowDm) valueTolerance.getSelectedItem()).getResisInt() == 0) {
			TableRow trInfo = new TableRow(this);
			trInfo.setGravity(Gravity.CENTER);
			TextView viewInfo = new TextView(this);
			viewInfo.setLayoutParams(infoLayout);
			viewInfo.setGravity(Gravity.CENTER);

			/* Create a textview to be the row-content */
			TextView viewPre = new TextView(this);
			viewPre.setLayoutParams(preEndLayout);
			TextView view1 = new TextView(this);
			view1.setLayoutParams(rowLayout);
			TextView view2 = new TextView(this);
			view2.setLayoutParams(rowLayout);
			TextView view3 = new TextView(this);
			view3.setLayoutParams(rowLayout);
			TextView view4 = new TextView(this);
			view4.setLayoutParams(rowLayout);
			TextView viewEnd = new TextView(this);
			viewEnd.setLayoutParams(preEndLayout);

			firstMatched = false;
			secondMatched = false;
			thirdMatched = false;
			infoText = new String[3];

			for (int i = 0; i < 12; i++) {
				if (firstBand.size() > i && !firstMatched && firstDigit == firstBand.get(i).getResisInt()) {
					view1.setBackgroundResource(firstBand.get(i).getColorInt());
					infoText[0] = firstBand.get(i).getLabel();
					firstMatched = true;
				}
				if (secondBand.size() > i && !secondMatched && secondDigit == secondBand.get(i).getResisInt()) {
					view2.setBackgroundResource(secondBand.get(i).getColorInt());
					infoText[1] = secondBand.get(i).getLabel();
					secondMatched = true;
				}
				if (thirdBand.size() > i && !thirdMatched && powOfTens == thirdBand.get(i).getResisInt()) {
					view3.setBackgroundResource(thirdBand.get(i).getColorInt());
					infoText[2] = thirdBand.get(i).getLabel();
					thirdMatched = true;
				}
			}

			view4.setBackgroundResource(R.drawable.slash);

			if (thirdMatched) {
				TableRow tr = new TableRow(this);
				tr.setGravity(Gravity.CENTER);
				tr.setBackgroundResource(R.drawable.resistor);
				tr.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

				/* Add texts to row. */
				tr.addView(viewPre);
				tr.addView(view1);
				tr.addView(view2);
				tr.addView(view3);
				tr.addView(view4);
				tr.addView(viewEnd);

				/* Add resistor info to row */
				viewInfo.setText(infoText[0] + " | " + infoText[1] + " | " + infoText[2] + " | " + "Any");
				viewInfo.setTextColor(Color.BLACK);

				trInfo.addView(viewInfo);

				String resistValue = valueText.getText() + " " + ((UnitDm) valueUnit.getSelectedItem()).getLabel();
				tr.setTag(new SdDm(infoText[0], infoText[1], infoText[2], "Any", resistValue));
				tr.setOnLongClickListener(new MyOnLongClickListener());

				/* Add the row to the table */
				tl.addView(tr);
				tl.addView(trInfo);
			} else {
				TextView viewNoResult = new TextView(this);
				viewNoResult.setLayoutParams(rowLayout);
				viewNoResult.setText("No Result");
				viewNoResult.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
				// Declare new row
				TableRow tr = new TableRow(this);
				tr.setGravity(Gravity.CENTER);
				tr.addView(viewNoResult);
				tl.addView(tr);
			}
		} else {
			// As low as possible
			ArrayList<ResultDm> low_queue = new ArrayList<ResultDm>();
			boolean low_end = false;
			double powOfTensBak = powOfTens;
			int firstDigitBak = firstDigit;
			int secondDigitBak = secondDigit;
			while (!low_end) {
				if (secondDigit == -1) {
					secondDigit = 9;
					firstDigit--;
				}
				if (firstDigit == 0) {
					firstDigit = 9;
					powOfTens /= 10;
				}
				double lowestResistBeforeMultiple = Integer.valueOf(Integer.toString(firstDigit)
						+ Integer.toString(secondDigit))
						* powOfTens;
				double lowestResistance = lowestResistBeforeMultiple
						* (1 + (((RowDm) valueTolerance.getSelectedItem()).getResisInt() / 100));
				if (lowestResistance < input) {
					low_end = true;
					break;
				}
				low_queue.add(new ResultDm(firstDigit, secondDigit, powOfTens, ((RowDm) valueTolerance
						.getSelectedItem()).getResisInt(), lowestResistBeforeMultiple));
				secondDigit--;
			}

			// Set back to default
			powOfTens = powOfTensBak;
			firstDigit = firstDigitBak;
			secondDigit = secondDigitBak;

			// As high as possible
			ArrayList<ResultDm> high_queue = new ArrayList<ResultDm>();
			boolean high_end = false;
			while (!high_end) {
				secondDigit++;
				if (secondDigit == 10) {
					secondDigit = 0;
					firstDigit++;
				}
				if (firstDigit == 10) {
					firstDigit = 1;
					powOfTens *= 10;
				}
				double highestResistBeforeMultiple = Integer.valueOf(Integer.toString(firstDigit)
						+ Integer.toString(secondDigit))
						* powOfTens;
				double highestResistance = highestResistBeforeMultiple
						* (1 - (((RowDm) valueTolerance.getSelectedItem()).getResisInt() / 100));
				if (highestResistance > input) {
					high_end = true;
					break;
				}
				high_queue.add(new ResultDm(firstDigit, secondDigit, powOfTens, ((RowDm) valueTolerance
						.getSelectedItem()).getResisInt(), highestResistBeforeMultiple));
			}

			// Set back to default
			powOfTens = powOfTensBak;
			firstDigit = firstDigitBak;
			secondDigit = secondDigitBak;

			int lowQueueSize = low_queue.size();
			for (int i = lowQueueSize - 1; i >= 0; i--) {

				TableRow trInfo = new TableRow(this);
				trInfo.setGravity(Gravity.CENTER);
				TextView viewInfo = new TextView(this);
				viewInfo.setLayoutParams(infoLayout);
				viewInfo.setGravity(Gravity.CENTER);

				/* Create a textview to be the row-content */
				TextView viewPre = new TextView(this);
				viewPre.setLayoutParams(preEndLayout);
				TextView view1 = new TextView(this);
				view1.setLayoutParams(rowLayout);
				TextView view2 = new TextView(this);
				view2.setLayoutParams(rowLayout);
				TextView view3 = new TextView(this);
				view3.setLayoutParams(rowLayout);
				TextView view4 = new TextView(this);
				view4.setLayoutParams(rowLayout);
				TextView viewEnd = new TextView(this);
				viewEnd.setLayoutParams(preEndLayout);

				TableRow tr = new TableRow(this);
				tr.setGravity(Gravity.CENTER);
				tr.setBackgroundResource(R.drawable.resistor);

				firstMatched = false;
				secondMatched = false;
				thirdMatched = false;
				fourthMatched = false;
				infoText = new String[4];

				for (int match = 0; match < 12; match++) {
					if (firstBand.size() > match) {
						if (firstBand.get(match).getResisInt() == low_queue.get(i).getFirstBand() && !firstMatched) {
							view1.setBackgroundResource(firstBand.get(match).getColorInt());
							infoText[0] = firstBand.get(match).getLabel();
							firstMatched = true;
						}
					}
					if (secondBand.size() > match) {
						if (secondBand.get(match).getResisInt() == low_queue.get(i).getSecondBand() && !secondMatched) {
							view2.setBackgroundResource(secondBand.get(match).getColorInt());
							infoText[1] = secondBand.get(match).getLabel();
							secondMatched = true;
						}
					}
					if (thirdBand.size() > match) {
						if (thirdBand.get(match).getResisInt() == low_queue.get(i).getMultiplierBand() && !thirdMatched) {
							view3.setBackgroundResource(thirdBand.get(match).getColorInt());
							infoText[2] = thirdBand.get(match).getLabel();
							thirdMatched = true;
						}
					}
					if (toleranceBand.size() > match) {
						if (toleranceBand.get(match).getResisInt() == low_queue.get(i).getToleranceBand()
								&& !fourthMatched) {
							if (toleranceBand.get(match).getResisInt() == 0) {
								view4.setBackgroundResource(R.drawable.slash);
								infoText[3] = "Any";
							} else if (toleranceBand.get(match).getResisInt() == 20) {
								view4.setBackgroundResource(R.drawable.slash);
								infoText[3] = "None";
							} else {
								view4.setBackgroundResource(toleranceBand.get(match).getColorInt());
								infoText[3] = toleranceBand.get(match).getLabel();
							}
							fourthMatched = true;
						}
					}
				}

				if (thirdMatched) {
					/* Add texts to row. */
					tr.addView(viewPre);
					tr.addView(view1);
					tr.addView(view2);
					tr.addView(view3);
					tr.addView(view4);
					tr.addView(viewEnd);

					/* Add resistor info to row */
					if (infoText[3].contains("("))
						infoText[3] = infoText[3].substring(0, infoText[3].indexOf("(") - 1);
					UnitDm selectedUnit = (UnitDm) valueUnit.getSelectedItem();
					Double resistValue = low_queue.get(i).getResisVal() / selectedUnit.getMultiple();
					resistValue = adjustDouble(resistValue, 3);
					if (resistValue == -1.0)
						return;
					viewInfo.setText(resistValue + " " + selectedUnit.getLabel() + "\n" + infoText[0] + " | "
							+ infoText[1] + " | " + infoText[2] + " | " + infoText[3]);
					viewInfo.setTextColor(Color.BLACK);

					/* Add Separator */
					View seperatorView = new View(this);
					seperatorView.setLayoutParams(seperator);
					seperatorView.setBackgroundColor(Color.DKGRAY);

					trInfo.addView(viewInfo);

					tr.setTag(new SdDm(infoText[0], infoText[1], infoText[2], infoText[3], resistValue + " "
							+ selectedUnit.getLabel()));
					tr.setOnLongClickListener(new MyOnLongClickListener());

					/* Add the row to the table */
					tl.addView(tr);
					tl.addView(trInfo);
					tl.addView(seperatorView);
				}
			}

			int highQueueSize = high_queue.size();
			for (int i = 0; i < highQueueSize; i++) {
				TextView viewInfo = new TextView(this);
				viewInfo.setLayoutParams(infoLayout);
				viewInfo.setGravity(Gravity.CENTER);

				/* Create a textview to be the row-content */
				TextView viewPre = new TextView(this);
				viewPre.setLayoutParams(preEndLayout);
				TextView view1 = new TextView(this);
				view1.setLayoutParams(rowLayout);
				TextView view2 = new TextView(this);
				view2.setLayoutParams(rowLayout);
				TextView view3 = new TextView(this);
				view3.setLayoutParams(rowLayout);
				TextView view4 = new TextView(this);
				view4.setLayoutParams(rowLayout);
				TextView viewEnd = new TextView(this);
				viewEnd.setLayoutParams(preEndLayout);

				TableRow tr = new TableRow(this);
				tr.setGravity(Gravity.CENTER);
				tr.setBackgroundResource(R.drawable.resistor);

				TableRow trInfo = new TableRow(this);
				trInfo.setGravity(Gravity.CENTER);

				firstMatched = false;
				secondMatched = false;
				thirdMatched = false;
				fourthMatched = false;
				infoText = new String[4];

				for (int match = 0; match < 12; match++) {
					if (firstBand.size() > match) {
						if (firstBand.get(match).getResisInt() == high_queue.get(i).getFirstBand() && !firstMatched) {
							view1.setBackgroundResource(firstBand.get(match).getColorInt());
							infoText[0] = firstBand.get(match).getLabel();
							firstMatched = true;
						}
					}
					if (secondBand.size() > match) {
						if (secondBand.get(match).getResisInt() == high_queue.get(i).getSecondBand() && !secondMatched) {
							view2.setBackgroundResource(secondBand.get(match).getColorInt());
							infoText[1] = secondBand.get(match).getLabel();
							secondMatched = true;
						}
					}
					if (thirdBand.size() > match) {
						if (thirdBand.get(match).getResisInt() == high_queue.get(i).getMultiplierBand()
								&& !thirdMatched) {
							view3.setBackgroundResource(thirdBand.get(match).getColorInt());
							infoText[2] = thirdBand.get(match).getLabel();
							thirdMatched = true;
						}
					}
					if (toleranceBand.size() > match) {
						if (toleranceBand.get(match).getResisInt() == high_queue.get(i).getToleranceBand()
								&& !fourthMatched) {
							if (toleranceBand.get(match).getResisInt() == 0) {
								view4.setBackgroundResource(R.drawable.slash);
								infoText[3] = "Any";
							} else if (toleranceBand.get(match).getResisInt() == 20) {
								view4.setBackgroundResource(R.drawable.slash);
								infoText[3] = "None";
							} else {
								view4.setBackgroundResource(toleranceBand.get(match).getColorInt());
								infoText[3] = toleranceBand.get(match).getLabel();
							}
							fourthMatched = true;
						}
					}
				}

				if (thirdMatched) {
					/* Add texts to row. */
					tr.addView(viewPre);
					tr.addView(view1);
					tr.addView(view2);
					tr.addView(view3);
					tr.addView(view4);
					tr.addView(viewEnd);

					/* Add Separator */
					View seperatorView = new View(this);
					seperatorView.setLayoutParams(seperator);
					seperatorView.setBackgroundColor(Color.DKGRAY);

					/* Add resistor info to row */
					if (infoText[3].contains("("))
						infoText[3] = infoText[3].substring(0, infoText[3].indexOf("(") - 1);
					UnitDm selectedUnit = (UnitDm) valueUnit.getSelectedItem();
					Double resistValue = high_queue.get(i).getResisVal() / selectedUnit.getMultiple();
					resistValue = adjustDouble(resistValue, 3);
					if (resistValue == -1.0)
						return;
					viewInfo.setText(resistValue + " " + selectedUnit.getLabel() + "\n" + infoText[0] + " | "
							+ infoText[1] + " | " + infoText[2] + " | " + infoText[3]);
					viewInfo.setTextColor(Color.BLACK);

					trInfo.addView(viewInfo);

					tr.setTag(new SdDm(infoText[0], infoText[1], infoText[2], infoText[3], resistValue + " "
							+ selectedUnit.getLabel()));
					tr.setOnLongClickListener(new MyOnLongClickListener());

					/* Add the row to the table */
					tl.addView(tr);
					tl.addView(trInfo);
					tl.addView(seperatorView);
				}
			}

			if (tl.getChildCount() == 0) {
				TextView viewNoResult = new TextView(this);
				viewNoResult.setLayoutParams(rowLayout);
				viewNoResult.setText("No Result");
				viewNoResult.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
				// Declare new row
				TableRow tr = new TableRow(this);
				tr.setGravity(Gravity.CENTER);
				tr.addView(viewNoResult);
				tl.addView(tr);
			}
		}
	}

	public class MyOnLongClickListener implements OnLongClickListener {

		public boolean onLongClick(View arg0) {

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
					String output = text + Integer.toString(count) + ":" + ((SdDm) arg0.getTag()).getFirstBand() + ":"
							+ ((SdDm) arg0.getTag()).getSecondBand() + ":" + ((SdDm) arg0.getTag()).getMultiplierBand()
							+ ":" + ((SdDm) arg0.getTag()).getToleranceBand() + ":" + "Val"
							+ ((SdDm) arg0.getTag()).getResistValue() + ";\n";
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
	}

	public Double adjustDouble(double input, int decimalPlaces) {
		try {
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(decimalPlaces);
			df.setGroupingUsed(false);
			double rValue  = Double.parseDouble(df.format(input));
			return rValue;
		} catch (NumberFormatException nfe) {
			showAlert("Please input a numeric-only value.");
			return -1.0;
		}
	}

	public void showAlert(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				valueText.setText("");
				dialog.cancel();
				return;
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
		return;
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
			updateMode("5", "update");
		} else {
			Intent myIntent = new Intent(ValueActivity.this, ListActivity.class);
			ValueActivity.this.startActivity(myIntent);
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

				Intent myIntent = new Intent(ValueActivity.this, ValueActivity5.class);
				ValueActivity.this.startActivity(myIntent);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
