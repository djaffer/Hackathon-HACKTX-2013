package com.hacktx2013.utcafeteriamenu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

public class RestaurantDetailActivity extends Activity {
	private static final String TAG = "RestaurantDetailActivity";
	private TextView _name;
	private TextView _hours;
	private GridView _menu;
	private RestaurantDetail _restaurantDetail;
	private ArrayList<String> _menu_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "begin onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_detail_activity);
		
		_name = (TextView)this.findViewById(R.id.restaurant_name);
		_hours = (TextView)this.findViewById(R.id.restaurant_hours);
		_menu = (GridView)this.findViewById(R.id.restaurant_menu);
		_menu_name = new ArrayList<String>();
		Bundle bundle = RestaurantDetailActivity.this.getIntent().getExtras();
		_restaurantDetail = bundle.getParcelable("RESTAURANT_DETAIL");
		
		_name.setText(_restaurantDetail.getName());
		_hours.setText(_restaurantDetail.getHours());		

		JSONArray obj_array;
		try {
			obj_array = new JSONArray(_restaurantDetail.getMenu());
			for(int i=0; i<obj_array.length(); ++i) {
				JSONObject obj_i = obj_array.getJSONObject(i);
				_menu_name.add(obj_i.getString("name"));
//				String menu_description = obj_i.getString("desc");
//				Log.d(TAG, menu_description);
			}
		}
		catch (JSONException e) {
			Log.e(TAG, "Error parsing JSON", e);
		}
		
		_menu.setAdapter(new RestaurantDetailsAdapter(this, _menu_name));
		Log.d(TAG, "begin onCreate");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant_detail, menu);
		return true;
	}
}
