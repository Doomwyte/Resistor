package com.dyang.fourband.library.adapter;

import java.util.List;

import com.dyang.fourband.R;
import com.dyang.fourband.helper.UnitConverter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DecimalAdapter extends ArrayAdapter<Integer> {

	private List<Integer> mPlaces;

	public DecimalAdapter(Context context, int textViewResourceId, List<Integer> decimalPlaces) {
		super(context, textViewResourceId, decimalPlaces);
		mPlaces = decimalPlaces;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView v = (TextView) super.getView(position, convertView, parent);
		Integer place = mPlaces.get(position);
		v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, UnitConverter.dpToPixel(30, parent.getContext())));
		v.setText(R.string.decimals);
		v.setText(v.getText() + " (" + place.toString() + ")");
		v.setTextAppearance(parent.getContext(), android.R.style.TextAppearance_Medium);
		return v;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View v = super.getDropDownView(position, convertView, parent);
		Integer place = mPlaces.get(position);
		TextView tv = (TextView) v.findViewById(android.R.id.text1);
		tv.setText(place.toString());
		v.bringToFront();
		return v;
	}
}
