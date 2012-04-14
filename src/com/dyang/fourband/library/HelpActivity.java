package com.dyang.fourband.library;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpActivity extends Activity {

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
		helpMsg.setText("This page will return a list of resistors whose range cover the value you entered. For example, when 25.1 Ohms and 0% tolerance is entered, there will be no result. Whereas if you change tolerance to 2%, it will return 25 Ohms since 25 +/-2% Ohms covers 25.1 Ohms.");
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
					helpMsg.setText("Hold onto the resistor to add it to the custom list. A custom list is simply a list of user-entered resistors, just like a shopping list.\n\nTo remove a resistor from the custom list, simply click on the resistor.");
					imageView.setImageResource(R.drawable.ss3);
				} else {
					Intent myIntent = new Intent(HelpActivity.this, MainActivity.class);
					HelpActivity.this.startActivity(myIntent);
				}
			}

		});
	}

}