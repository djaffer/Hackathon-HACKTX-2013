package com.hacktx2013.utcafeteriamenu;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class RestaurantDetailsAdapter extends BaseAdapter {
	private Context _context;
	private final ArrayList<String> _menuName;
 
	public RestaurantDetailsAdapter(Context context, ArrayList<String> menuName) {
		this._context = context;
		this._menuName = menuName;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) _context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if (convertView == null) {
			gridView = new View(_context);
			
			gridView = inflater.inflate(R.layout.restaurant_list_item, null);
 
			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.restaurant_name);
			textView.setText(_menuName.get(position)); 
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
	}
 
	@Override
	public int getCount() {
		return _menuName.size();
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
}