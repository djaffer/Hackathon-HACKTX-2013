package com.hacktx2013.utcafeteriamenu;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RestaurantListAdapter extends ArrayAdapter<Restaurant> {
	private static final String TAG = "RestaurantListAdapter";
	
	private final Context _context;
	private final int _layoutResourceId;
	private ArrayList<Restaurant> _itemArray;
	private Restaurant _data;
	
	static class ViewHolder {
		public TextView _name;
	}
	
	public RestaurantListAdapter(Context context, int layoutResourceId, ArrayList<Restaurant> itemArray) {
		super(context, layoutResourceId, itemArray);
		_context = context;
		_layoutResourceId = layoutResourceId;
		_itemArray = itemArray;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if(rowView == null) {
			LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(_layoutResourceId, parent, false);
			
			ViewHolder viewHolder = new ViewHolder();
			viewHolder._name = (TextView) rowView.findViewById(R.id.restaurant_name);
			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		_data = _itemArray.get(position);
		holder._name.setText(_data.getName());
		Log.d(TAG, "name: " + _data.getName());
		
		return rowView;
	}
}
