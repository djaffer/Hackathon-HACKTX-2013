package com.hacktx2013.utcafeteriamenu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BuildingListAdapter extends ArrayAdapter<String> {
	private static final String TAG = "BuildingListAdapter";
	
	private final Context _context;
	private final int _layoutResourceId;
	private String[] _buildingNames;
	
	static class ViewHolder {
		public TextView _buildingName;
	}
	
	public BuildingListAdapter(Context context, int layoutResourceId, String[] buildingNames) {
		super(context, layoutResourceId, buildingNames);
		_context = context;
		_layoutResourceId = layoutResourceId;
		_buildingNames = buildingNames;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if(rowView == null) {
			LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(_layoutResourceId, parent, false);
			
			ViewHolder viewHolder = new ViewHolder();
			viewHolder._buildingName = (TextView) rowView.findViewById(R.id.building_name);
			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		String buildingName = _buildingNames[position];
		holder._buildingName.setText(buildingName);
		Log.d(TAG, "name: " + buildingName);
		
		return rowView;
	}
}
