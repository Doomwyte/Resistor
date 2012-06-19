package com.dyang.fourband.library.adapter;

import java.util.ArrayList;

import com.dyang.fourband.library.dm.RowDm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ToleranceAdapter extends ArrayAdapter<RowDm> {

	private ArrayList<RowDm> mItems;

	public ToleranceAdapter(Context context, int textViewResourceId,
			ArrayList<RowDm> toleranceBand) {
		super(context, textViewResourceId, toleranceBand);
		mItems = toleranceBand;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, convertView, parent);
		RowDm item = mItems.get(position);
		v.setBackgroundResource(item.getColorInt());
		((TextView) v).setText((int)item.getResisInt() + "%");
		return v;
	}

	@Override
	public View getDropDownView(int position, View convertView,
			ViewGroup parent) {
		View v = super.getDropDownView(position, convertView, parent);
		RowDm item = mItems.get(position);
		TextView tv = (TextView) v.findViewById(android.R.id.text1);
		v.setBackgroundResource(item.getColorInt());
		tv.setText(item.getLabel());
		return v;
	}
}
