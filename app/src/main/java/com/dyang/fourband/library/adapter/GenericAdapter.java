package com.dyang.fourband.library.adapter;

import java.util.List;

import com.dyang.fourband.helper.UnitConverter;
import com.dyang.fourband.library.dm.RowDm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GenericAdapter extends ArrayAdapter<RowDm> {

	private List<RowDm> mItems;

	public GenericAdapter(Context context, int textViewResourceId, List<RowDm> band) {
		super(context, textViewResourceId, band);
		mItems = band;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, convertView, parent);
		RowDm item = mItems.get(position);
		v.setBackgroundResource(item.getColorInt());
		v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, UnitConverter.dpToPixel(30, parent.getContext())));
		((TextView) v).setText(null);
		return v;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View v = super.getDropDownView(position, convertView, parent);
		RowDm item = mItems.get(position);
		TextView tv = (TextView) v.findViewById(android.R.id.text1);
		v.setBackgroundResource(item.getColorInt());
		tv.setText(item.getLabel());
		v.bringToFront();
		return v;
	}
}
