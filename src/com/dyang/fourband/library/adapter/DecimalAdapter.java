package com.dyang.fourband.library.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DecimalAdapter extends ArrayAdapter<Integer> {

	private ArrayList<Integer> mPlaces;

	public DecimalAdapter(Context context, int textViewResourceId,
			ArrayList<Integer> decimalPlaces) {
		super(context, textViewResourceId, decimalPlaces);
		mPlaces = decimalPlaces;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, convertView, parent);
		Integer place = mPlaces.get(position);
		((TextView) v).setText(place.toString());
		return v;
	}

	@Override
	public View getDropDownView(int position, View convertView,
			ViewGroup parent) {
		View v = super.getDropDownView(position, convertView, parent);
		Integer place = mPlaces.get(position);
		TextView tv = (TextView) v.findViewById(android.R.id.text1);
		tv.setText(place.toString());
		v.bringToFront();
		return v;
	}
}
