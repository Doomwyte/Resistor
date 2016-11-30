package com.dyang.fourband;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.dyang.fourband.helper.FavouritesManager;
import com.dyang.fourband.helper.ModeManager;
import com.dyang.fourband.library.adapter.ToleranceAdapter;
import com.dyang.fourband.library.adapter.UnitAdapter;
import com.dyang.fourband.library.dm.ResultDm;
import com.dyang.fourband.library.dm.RowDm;
import com.dyang.fourband.library.dm.SdDm;
import com.dyang.fourband.library.dm.UnitDm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
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
import android.widget.TableRow.LayoutParams;

public class ValueActivity extends AbstractActivity implements OnClickListener {

	private EditText valueText;
	private Spinner valueUnit, valueTolerance;
	private Button valueEnter;
	private List<RowDm> firstBand, secondBand, thirdBand, toleranceBand;
	private List<UnitDm> units;
	private List<ResultDm> low_queue, high_queue;
	private LayoutParams rowLayout1, rowLayout2, infoLayout, preEndLayout, seperator;
	private TableLayout tl;
	private ProgressDialog progress;
	private List<View> views;
	private Boolean stopThreads = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.value);

		ModeManager.updateMode(-1, this);

		valueText = (EditText) findViewById(R.id.valueText);
		valueUnit = (Spinner) findViewById(R.id.valueUnit);
		valueTolerance = (Spinner) findViewById(R.id.valueTolerance);
		valueEnter = (Button) findViewById(R.id.valueEnter);

		// Initialize first band array
		firstBand = new ArrayList<>();
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
		secondBand = new ArrayList<>();
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
		thirdBand = new ArrayList<>();
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
		toleranceBand = new ArrayList<>();
		toleranceBand.add(new RowDm("Exact (0%)", Color.TRANSPARENT, 0));
		toleranceBand.add(new RowDm("Red (2%)", R.color.Red, 2));
		toleranceBand.add(new RowDm("Gold (5%)", R.color.Gold, 5));
		toleranceBand.add(new RowDm("Silver (10%)", R.color.Silver, 10));
		toleranceBand.add(new RowDm("None (20%)", Color.TRANSPARENT, 20));

		// Initialize units array
		units = new ArrayList<>();
		units.add(new UnitDm("nΩ", 0.000000001));
		units.add(new UnitDm("uΩ", 0.000001));
		units.add(new UnitDm("mΩ", 0.001));
		units.add(new UnitDm("Ω", 1));
		units.add(new UnitDm("kΩ", 1000));
		units.add(new UnitDm("MΩ", 1000000));

		UnitAdapter unitsAdapter = new UnitAdapter(this, R.layout.my_simple_spinner_dropdown_item, units);
		ToleranceAdapter toleranceAdapter = new ToleranceAdapter(this, R.layout.my_simple_spinner_dropdown_item, toleranceBand);

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
				displayMessage("Please enter a valid value");
				return;
			}
			input *= ((UnitDm) valueUnit.getSelectedItem()).getMultiple();
		}

		if (input == 0) {
			displayMessage("Please enter a valid value");
			return;
		}

		if (input > 118800000000.0) {
			displayMessage("Please enter a smaller value");
			return;
		}

		// Define layout params
		int px1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 86, getResources().getDisplayMetrics());
		int px2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 71, getResources().getDisplayMetrics());
		int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
		int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13, getResources().getDisplayMetrics());
		rowLayout1 = new LayoutParams(width, px1);
		rowLayout2 = new LayoutParams(width, px2);
		rowLayout1.leftMargin = margin;
		rowLayout1.rightMargin = margin;
		rowLayout2.leftMargin = margin;
		rowLayout2.rightMargin = margin;

		infoLayout = new LayoutParams();
		infoLayout.span = 6;

		preEndLayout = new LayoutParams(150, 100);

		seperator = new LayoutParams();
		seperator.height = 2;

		tl = (TableLayout) findViewById(R.id.resultTable);
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
			String decimalNumber = Double.toString(firstTwoDigit).substring(Double.toString(firstTwoDigit).indexOf(".") + 1);
			firstDigit = Integer.valueOf(decimalNumber.substring(0, 1));
			secondDigit = Integer.valueOf(decimalNumber.substring(1, 2));
			powOfTens = 0.01;
		}

		if (((RowDm) valueTolerance.getSelectedItem()).getResisInt() == 0) {

			if (firstTwoDigit % 1.0 > 0) {
				displayMessage("Invalid Value");
				return;
			}

			TableRow trInfo = new TableRow(this);
			trInfo.setGravity(Gravity.CENTER);
			TextView viewInfo = new TextView(this);
			viewInfo.setLayoutParams(infoLayout);
			viewInfo.setGravity(Gravity.CENTER);

			/* Create a textview to be the row-content */
			TextView viewPre = new TextView(this);
			viewPre.setLayoutParams(preEndLayout);
			TextView view1 = new TextView(this);
			view1.setLayoutParams(rowLayout1);
			TextView view2 = new TextView(this);
			view2.setLayoutParams(rowLayout2);
			TextView view3 = new TextView(this);
			view3.setLayoutParams(rowLayout2);
			TextView view4 = new TextView(this);
			view4.setLayoutParams(rowLayout1);
			TextView viewEnd = new TextView(this);
			viewEnd.setLayoutParams(preEndLayout);

			Boolean firstMatched = false;
			Boolean secondMatched = false;
			Boolean thirdMatched = false;
			String[] infoText = new String[3];

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
				tr.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

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
				viewNoResult.setLayoutParams(rowLayout1);
				viewNoResult.setText("No Result");
				viewNoResult.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
				// Declare new row
				TableRow tr = new TableRow(this);
				tr.setGravity(Gravity.CENTER);
				tr.addView(viewNoResult);
				tl.addView(tr);
			}
		} else {
			// As low as possible
			low_queue = new ArrayList<>();
			boolean low_end = false;

			// backup
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
				double lowestResistBeforeMultiple = Integer.valueOf(Integer.toString(firstDigit) + Integer.toString(secondDigit)) * powOfTens;
				double lowestResistance = lowestResistBeforeMultiple * (1 + (((RowDm) valueTolerance.getSelectedItem()).getResisInt() / 100));
				if (lowestResistance < input) {
					low_end = true;
					break;
				}
				low_queue.add(new ResultDm(firstDigit, secondDigit, powOfTens, ((RowDm) valueTolerance.getSelectedItem()).getResisInt(), lowestResistBeforeMultiple));
				secondDigit--;
			}

			// Set back to default
			powOfTens = powOfTensBak;
			firstDigit = firstDigitBak;
			secondDigit = secondDigitBak;

			// As high as possible
			high_queue = new ArrayList<>();
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
				double highestResistBeforeMultiple = Integer.valueOf(Integer.toString(firstDigit) + Integer.toString(secondDigit)) * powOfTens;
				double highestResistance = highestResistBeforeMultiple * (1 - (((RowDm) valueTolerance.getSelectedItem()).getResisInt() / 100));
				if (highestResistance > input) {
					high_end = true;
					break;
				}
				high_queue.add(new ResultDm(firstDigit, secondDigit, powOfTens, ((RowDm) valueTolerance.getSelectedItem()).getResisInt(), highestResistBeforeMultiple));
			}

			// Set back to default
			powOfTens = powOfTensBak;
			firstDigit = firstDigitBak;
			secondDigit = secondDigitBak;

			progress = new ProgressDialog(this);
			progress.setTitle("Calculating");
			progress.setMessage("Please Wait ...");
			progress.setCancelable(false);
			progress.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					stopThreads = true;
				}
			});
			progress.show();

			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					Callable<List<View>> lowCallable = new Callable<List<View>>() {
						@Override
						public List<View> call() throws Exception {
							List<View> localTr = new ArrayList<View>();
							int lowQueueSize = low_queue.size();
							for (int i = lowQueueSize - 1; i >= 0; i--) {

								if (stopThreads) {
									return null;
								}

								TableRow trInfo = new TableRow(ValueActivity.this);
								trInfo.setGravity(Gravity.CENTER);

								/* Create a textview to be the row-content */
								TextView viewPre = new TextView(ValueActivity.this);
								viewPre.setLayoutParams(preEndLayout);
								TextView view1 = new TextView(ValueActivity.this);
								view1.setLayoutParams(rowLayout1);
								TextView view2 = new TextView(ValueActivity.this);
								view2.setLayoutParams(rowLayout2);
								TextView view3 = new TextView(ValueActivity.this);
								view3.setLayoutParams(rowLayout2);
								TextView view4 = new TextView(ValueActivity.this);
								view4.setLayoutParams(rowLayout1);
								TextView viewEnd = new TextView(ValueActivity.this);
								viewEnd.setLayoutParams(preEndLayout);
								TextView viewInfo = new TextView(ValueActivity.this);
								viewInfo.setLayoutParams(infoLayout);
								viewInfo.setGravity(Gravity.CENTER);

								TableRow tr = new TableRow(ValueActivity.this);
								tr.setGravity(Gravity.CENTER);
								tr.setBackgroundResource(R.drawable.resistor);

								Boolean firstMatched = false;
								Boolean secondMatched = false;
								Boolean thirdMatched = false;
								Boolean fourthMatched = false;
								String[] infoText = new String[4];

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
										if (toleranceBand.get(match).getResisInt() == low_queue.get(i).getToleranceBand() && !fourthMatched) {
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
									if (resistValue.equals(-1.0)) {
										continue;
									}
									viewInfo.setText(resistValue + " " + selectedUnit.getLabel() + "\n" + infoText[0] + " | " + infoText[1] + " | " + infoText[2] + " | " + infoText[3]);
									viewInfo.setTextColor(Color.BLACK);

									/* Add Separator */
									View seperatorView = new View(ValueActivity.this);
									seperatorView.setLayoutParams(seperator);
									seperatorView.setBackgroundColor(Color.LTGRAY);

									trInfo.addView(viewInfo);

									tr.setTag(new SdDm(infoText[0], infoText[1], infoText[2], infoText[3], resistValue + " " + selectedUnit.getLabel()));
									tr.setOnLongClickListener(new MyOnLongClickListener());

									/* Add the row to the table */
									localTr.add(tr);
									localTr.add(trInfo);
									localTr.add(seperatorView);
								}
							}
							return localTr;
						}
					};

					Callable<List<View>> highCallable = new Callable<List<View>>() {
						@Override
						public List<View> call() throws Exception {
							List<View> localTr = new ArrayList<View>();
							int highQueueSize = high_queue.size();
							for (int i = 0; i < highQueueSize; i++) {

								if (stopThreads) {
									return null;
								}

								TextView viewInfo = new TextView(ValueActivity.this);
								viewInfo.setLayoutParams(infoLayout);
								viewInfo.setGravity(Gravity.CENTER);

								/* Create a textview to be the row-content */
								TextView viewPre = new TextView(ValueActivity.this);
								viewPre.setLayoutParams(preEndLayout);
								TextView view1 = new TextView(ValueActivity.this);
								view1.setLayoutParams(rowLayout1);
								TextView view2 = new TextView(ValueActivity.this);
								view2.setLayoutParams(rowLayout2);
								TextView view3 = new TextView(ValueActivity.this);
								view3.setLayoutParams(rowLayout2);
								TextView view4 = new TextView(ValueActivity.this);
								view4.setLayoutParams(rowLayout1);
								TextView viewEnd = new TextView(ValueActivity.this);
								viewEnd.setLayoutParams(preEndLayout);

								TableRow tr = new TableRow(ValueActivity.this);
								tr.setGravity(Gravity.CENTER);
								tr.setBackgroundResource(R.drawable.resistor);

								TableRow trInfo = new TableRow(ValueActivity.this);
								trInfo.setGravity(Gravity.CENTER);

								Boolean firstMatched = false;
								Boolean secondMatched = false;
								Boolean thirdMatched = false;
								Boolean fourthMatched = false;
								String[] infoText = new String[4];

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
										if (thirdBand.get(match).getResisInt() == high_queue.get(i).getMultiplierBand() && !thirdMatched) {
											view3.setBackgroundResource(thirdBand.get(match).getColorInt());
											infoText[2] = thirdBand.get(match).getLabel();
											thirdMatched = true;
										}
									}
									if (toleranceBand.size() > match) {
										if (toleranceBand.get(match).getResisInt() == high_queue.get(i).getToleranceBand() && !fourthMatched) {
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
									View seperatorView = new View(ValueActivity.this);
									seperatorView.setLayoutParams(seperator);
									seperatorView.setBackgroundColor(Color.LTGRAY);

									/* Add resistor info to row */
									if (infoText[3].contains("("))
										infoText[3] = infoText[3].substring(0, infoText[3].indexOf("(") - 1);
									UnitDm selectedUnit = (UnitDm) valueUnit.getSelectedItem();
									Double resistValue = high_queue.get(i).getResisVal() / selectedUnit.getMultiple();
									resistValue = adjustDouble(resistValue, 3);
									if (resistValue.equals(-1.0)) {
										continue;
									}
									viewInfo.setText(resistValue + " " + selectedUnit.getLabel() + "\n" + infoText[0] + " | " + infoText[1] + " | " + infoText[2] + " | " + infoText[3]);
									viewInfo.setTextColor(Color.BLACK);

									trInfo.addView(viewInfo);

									tr.setTag(new SdDm(infoText[0], infoText[1], infoText[2], infoText[3], resistValue + " " + selectedUnit.getLabel()));
									tr.setOnLongClickListener(new MyOnLongClickListener());

									/* Add the row to the table */
									localTr.add(tr);
									localTr.add(trInfo);
									localTr.add(seperatorView);
								}
							}
							return localTr;
						}
					};

					List<Future<List<View>>> futureList = new ArrayList<>();
					ExecutorService executorService = Executors.newCachedThreadPool();
					Future<List<View>> lowFuture = executorService.submit(lowCallable);
					futureList.add(lowFuture);
					Future<List<View>> highFuture = executorService.submit(highCallable);
					futureList.add(highFuture);

					views = new ArrayList<>();
					for (Future<List<View>> future : futureList) {
						try {
							List<View> viewList = future.get();
							if (viewList == null) {
								continue;
							}
							views.addAll(viewList);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
					}

					ValueActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							for (View view : views) {
								synchronized (tl) {
									tl.addView(view);
								}
							}
							if (stopThreads) {
								displayMessage("Operation Cancelled");
								stopThreads = false;
							} else if (tl.getChildCount() == 0) {
								displayMessage("No Results");
							}
							progress.dismiss();
						}
					});
				}
			});
			thread.start();
		}
	}

	public void displayMessage(String message) {
		tl.removeAllViews();
		TextView messageView = new TextView(this);
		messageView.setText(message);
		messageView.setTextColor(Color.RED);
		messageView.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		// Declare new row
		TableRow tr = new TableRow(this);
		tr.setGravity(Gravity.CENTER);
		tr.addView(messageView);
		tl.addView(tr);
	}

	public class MyOnLongClickListener implements OnLongClickListener {

		public boolean onLongClick(View arg0) {
			FavouritesManager.setFavourites(((SdDm) arg0.getTag()).getFirstBand(), ((SdDm) arg0.getTag()).getSecondBand(), ((SdDm) arg0.getTag()).getMultiplierBand(), null,
					((SdDm) arg0.getTag()).getToleranceBand(), ((SdDm) arg0.getTag()).getResistValue(), ValueActivity.this);
			return true;
		}
	}

	public Double adjustDouble(double input, int decimalPlaces) {
		try {
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(decimalPlaces);
			df.setGroupingUsed(false);
			return Double.parseDouble(df.format(input));
		} catch (NumberFormatException nfe) {
			displayMessage("Please input a numeric-only value");
			return -1.0;
		}
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
			ModeManager.updateMode(5, ValueActivity.this);
			Intent myIntent = new Intent(this, ValueActivity5.class);
			startActivity(myIntent);
			finish();
			return true;
		} else if (item.getItemId() == R.id.viewSavedList) {
			Intent myIntent = new Intent(this, ListActivity.class);
			startActivity(myIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
