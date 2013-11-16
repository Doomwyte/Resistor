package com.dyang.fourband;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class ListActivity extends AbstractActivity {

	private String[] sets;
	private TableLayout tl;
	private String color1, color2, color3, color4, color5, resistValue;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		loadTable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.removeAll) {
			File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.dyang.fourband/files");
			File file = new File(dir, "resistor_list.txt");
			file.delete();
			loadTable();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void loadTable() {

		tl = (TableLayout) findViewById(R.id.listTable);
		tl.clearAnimation();
		tl.removeAllViews();

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
			StringBuilder text = new StringBuilder();

			if (file.exists()) {
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(file));
					String line;
					while ((line = br.readLine()) != null) {
						text.append(line);
					}
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				sets = text.toString().split(";");

				for (int i = 0; i < sets.length; i++) {

					String[] subSets = sets[i].trim().split(":");

					TableRow tr = new TableRow(this);
					tr.setGravity(Gravity.CENTER);
					tr.setBackgroundResource(R.drawable.resistor);

					// Define layout params
					LayoutParams preEndLayout = new LayoutParams(150, 100);

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

					LayoutParams seperator = new LayoutParams();
					seperator.height = 1;

					LayoutParams infoLayout = new LayoutParams();
					if (subSets.length > 5)
						infoLayout.span = 7;
					else
						infoLayout.span = 6;

					/* Create a textview to be the row-content */
					TextView viewPre = new TextView(this);
					viewPre.setLayoutParams(preEndLayout);
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
					TextView viewEnd = new TextView(this);
					viewEnd.setLayoutParams(preEndLayout);

					tr.setTag(subSets[0].trim());
					color1 = subSets[1].trim();
					color2 = subSets[2].trim();
					color3 = subSets[3].trim();
					color4 = subSets[4].trim();
					if (subSets[5] != null)
						color5 = subSets[5].trim();

					view1.setBackgroundResource(this.getResources().getIdentifier(color1, "color", this.getPackageName()));
					view2.setBackgroundResource(this.getResources().getIdentifier(color2, "color", this.getPackageName()));
					view3.setBackgroundResource(this.getResources().getIdentifier(color3, "color", this.getPackageName()));

					if (color4.equals("Any") || color4.equals("None")) {
						view4.setBackgroundResource(R.drawable.slash);
					} else {
						view4.setBackgroundResource(this.getResources().getIdentifier(color4, "color", this.getPackageName()));
					}

					if (subSets.length > 5) {
						if (color5.equals("Any") || color5.equals("None")) {
							view5.setBackgroundResource(R.drawable.slash);
						} else if (color5.startsWith("Val")) {
							resistValue = subSets[5].trim().substring(3);
						} else {
							view5.setBackgroundResource(this.getResources().getIdentifier(color5, "color", this.getPackageName()));
						}
					}

					if (subSets.length > 6) {
						resistValue = subSets[6].trim().substring(3);
					}

					/* Add color bars to row. */
					tr.addView(viewPre);
					tr.addView(view1);
					tr.addView(view2);
					tr.addView(view3);
					tr.addView(view4);
					tr.addView(view5);
					tr.addView(viewEnd);

					/* Set Long Press Listener for row */
					tr.setOnClickListener(new MyOnLongClickListener());

					/* TextView info row */
					TextView viewInfo = new TextView(this);
					viewInfo.setLayoutParams(infoLayout);
					viewInfo.setGravity(Gravity.CENTER);
					viewInfo.setText(resistValue + "\n" + color1 + " | " + color2 + " | " + color3 + " | " + color4);
					if (subSets.length > 6 && color5 != null) {
						viewInfo.setText(viewInfo.getText() + " | " + color5);
					}
					viewInfo.setTextColor(Color.BLACK);

					/* TableRow info row */
					TableRow trInfo = new TableRow(this);
					trInfo.setGravity(Gravity.CENTER);
					trInfo.addView(viewInfo);

					/* Add Separator */
					View seperatorView = new View(this);
					seperatorView.setLayoutParams(seperator);
					seperatorView.setBackgroundColor(Color.DKGRAY);

					/* Add the row to the table */
					tl.setVisibility(View.INVISIBLE);
					tl.addView(tr);
					tl.addView(trInfo);
					tl.addView(seperatorView);

					/* Start Animation for whole row */
					Animation anm = AnimationUtils.loadAnimation(ListActivity.this, android.R.anim.fade_in);
					anm.setStartOffset(300);
					tl.startAnimation(anm);
					anm.setAnimationListener(new Animation.AnimationListener() {
						public void onAnimationStart(Animation animation) {
						}

						public void onAnimationRepeat(Animation animation) {
						}

						public void onAnimationEnd(Animation animation) {
							tl.setVisibility(View.VISIBLE);
						}
					});
				}
			} else {
				TableRow tr = new TableRow(this);
				TextView tv = new TextView(this);
				tv.setText("List is empty");
				tv.setTextSize(20);

				LayoutParams lp = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
				tv.setGravity(Gravity.CENTER);
				tr.setGravity(Gravity.CENTER);
				tr.addView(tv, lp);
				tl.setVisibility(View.INVISIBLE);
				tl.addView(tr);

				Animation anm = AnimationUtils.loadAnimation(ListActivity.this, android.R.anim.fade_in);
				anm.setStartOffset(400);
				tl.startAnimation(anm);
				anm.setAnimationListener(new Animation.AnimationListener() {
					public void onAnimationStart(Animation animation) {
					}

					public void onAnimationRepeat(Animation animation) {
					}

					public void onAnimationEnd(Animation animation) {
						tl.setVisibility(View.VISIBLE);
					}
				});
			}
		} else {
			TableRow tr = new TableRow(this);
			TextView tv = new TextView(this);
			tv.setText("SD Card not found");
			tv.setTextSize(20);

			LayoutParams lp = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
			tv.setGravity(Gravity.CENTER);
			tr.setGravity(Gravity.CENTER);
			tr.addView(tv, lp);
			tl.setVisibility(View.INVISIBLE);
			tl.addView(tr);

			Animation anm = AnimationUtils.loadAnimation(ListActivity.this, android.R.anim.fade_in);
			anm.setStartOffset(400);
			tl.startAnimation(anm);
			anm.setAnimationListener(new Animation.AnimationListener() {

				public void onAnimationStart(Animation animation) {
				}

				public void onAnimationRepeat(Animation animation) {
				}

				public void onAnimationEnd(Animation animation) {
					tl.setVisibility(View.VISIBLE);
				}
			});
		}
	}

	public class MyOnLongClickListener implements OnClickListener {

		public void onClick(final View arg0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
			builder.setMessage("Are you sure you want to delete this resistor?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					try {
						File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.dyang.fourband/files");
						File file = new File(dir, "resistor_list.txt");

						StringBuilder text = new StringBuilder();

						boolean written = false;

						BufferedReader br = new BufferedReader(new FileReader(file));
						String line = null;

						while ((line = br.readLine()) != null) {
							if (!line.trim().split(":")[0].equals(arg0.getTag().toString())) {
								text.append(line + "\n");
								written = true;
							}
						}

						br.close();

						file.delete();

						if (written) {
							FileOutputStream fOut = new FileOutputStream(file);
							String output = text.toString();
							fOut.write(output.getBytes());
							fOut.close();
						}

						final Animation animation = AnimationUtils.loadAnimation(ListActivity.this, android.R.anim.fade_out);
						animation.setAnimationListener(new Animation.AnimationListener() {

							public void onAnimationStart(Animation arg1) {
							}

							public void onAnimationRepeat(Animation arg2) {
							}

							public void onAnimationEnd(Animation arg3) {
								loadTable();
							}
						});
						tl.startAnimation(animation);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}
}
