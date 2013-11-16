package com.dyang.fourband;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpActivity extends AbstractActivity {

	private Button nextButton;
	private ImageView imageView;
	private TextView helpMsg;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);

		nextButton = (Button) findViewById(R.id.next);
		imageView = (ImageView) findViewById(R.id.imageView1);
		helpMsg = (TextView) findViewById(R.id.helpMsg);

		helpMsg.setTextColor(Color.BLACK);
		helpMsg.setText("This page will return a list of resistors whose range cover the value you entered. For example, when you input 20 Ohms and set the tolerance to 5%, it will return a list of resistors with a 5% tolerance that has 20 Ohms within its range.");
		imageView.setImageResource(R.drawable.ss1);

		nextButton.setOnClickListener(new OnClickListener() {
			int page = 1;

			public void onClick(View arg0) {
				if (page == 1) {
					page++;
					helpMsg.setText("This page will return you the resistance value based on the color you select.");
					imageView.setImageResource(R.drawable.ss2);
				} else if (page == 2) {
					page++;
					helpMsg.setText("Long press the resistor to add it to the saved list. A saved list is simply a list of user-entered resistors, just like a shopping list.\n\nTo remove a resistor from the saved list, simply click on the resistor.");
					imageView.setImageResource(R.drawable.ss3);
				} else if (page == 3) {
					page++;
					helpMsg.setText("To change to 4 or 5 band mode, simply click on the green/orange bar. Selecting the menu icon on the top right of most screens can also perform the same action.");
					imageView.setImageResource(R.drawable.ss4);
				} else {
					finish();
				}
			}

		});
	}

}