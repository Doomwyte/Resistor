package com.dyang.fourband.library.adapter;

import java.util.ArrayList;

import com.dyang.fourband.library.dm.UnitDm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class UnitAdapter extends ArrayAdapter<UnitDm> {

	private ArrayList<UnitDm> mUnits;

	public UnitAdapter(Context context, int textViewResourceId,
			ArrayList<UnitDm> units) {
		super(context, textViewResourceId, units);
		mUnits = units;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, convertView, parent);
		UnitDm unit = mUnits.get(position);
		((TextView) v).setText(unit.getLabel());
		return v;
	}

	@Override
	public View getDropDownView(int position, View convertView,
			ViewGroup parent) {
		View v = super.getDropDownView(position, convertView, parent);
		UnitDm item = mUnits.get(position);
		TextView tv = (TextView) v.findViewById(android.R.id.text1);
		tv.setText(item.getLabel());
		v.bringToFront();
		return v;
	}
}
