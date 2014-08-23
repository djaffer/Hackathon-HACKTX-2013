package com.hacktx2013.utcafeteriamenu;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class BuildingListActivity extends Activity {
	private static final String TAG = "BuildingListActivity";
	
	// Associate each building to respective building code
	// Note: the order needs to be the same as arrays.xml
	private static String[] _buildingCodes = new String[]{"jcd", "kin", "ltd", "sac", "sjh", "unb"};
	
	private ListView _buildingListView;
	private String[] _buildingNames;
	private ArrayList<Restaurant> _restaurantList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "begin onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.building_list_activity);
		
		// Get list of building from arrays.xml
		_buildingNames = BuildingListActivity.this.getResources().getStringArray(R.array.list_building_name);
		
		_restaurantList = new ArrayList<Restaurant>();
		
		// Initializing ListView in the activity
		_buildingListView = (ListView)this.findViewById(R.id.building_list_view);
		_buildingListView.setAdapter(new BuildingListAdapter(this, R.layout.building_list_item, _buildingNames));
		// Send request to server
		_buildingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				new NetworkRequestTask().execute(_buildingCodes[position]);
			}
		});
		
		Log.d(TAG, "end onCreate");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Class to run HTTP network requests in a worker thread. Necessary to
	 * keep the UI interactive.
	 * 
	 * Types specified are <Argument Type, Progress Update Type, Return Type>
	 */
	private class NetworkRequestTask extends AsyncTask<String, Integer, ArrayList<Restaurant>> {
		private static final String TAG = "BuildingListActivity.NetworkRequestTask";
		private static final String APIBASE = "http://stillopenatx.com/hungryhorns/building/?";
		
		@Override
		protected ArrayList<Restaurant> doInBackground(String... input) {
			Log.d(TAG, "begin doInBackground");
			try {
				// Connect to server
				HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet();
				HttpResponse response;
				
				// Build URL
				StringBuilder urlBuilder = new StringBuilder(APIBASE);
				urlBuilder.append("building="+input[0]);
				request.setURI(new URI(urlBuilder.toString()));
				
				// Parsing response received
				response = client.execute(request);
				InputStream in = new BufferedInputStream(response.getEntity().getContent());
				java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
				String output = s.hasNext() ? s.next() : "";
				Log.d(TAG, "output: " + output.toString());
				
				JSONArray obj_array = new JSONArray(output);
				for(int i=0; i<obj_array.length(); ++i) {
					JSONObject obj_i = obj_array.getJSONObject(i);
					int id = Integer.parseInt(obj_i.getString("id"));
					String name = obj_i.getString("name");
					Log.d(TAG, id +"\t\t"+ name);
					_restaurantList.add(new Restaurant(id, name));
				}
			}
			catch (JSONException e)
	        {
	            Log.e(TAG, "Error parsing response", e);
	        }
			catch (URISyntaxException e) {
	            Log.e(TAG, "Error parsing response", e);
			}
			catch (ClientProtocolException e) {
	            Log.e(TAG, "Error parsing response", e);
			}
			catch (IOException e) {
	            Log.e(TAG, "Error parsing response", e);
			}
			Log.d(TAG, "end doInBackground");
			return _restaurantList;
		}

		/** 
		 * Invoked in asynchronously in MainActivity when the network request 
		 * has finished and doInBackground returns its result.
		 */
		@Override
		protected void onPostExecute(ArrayList<Restaurant> result) {
			Log.d(TAG, "begin onPostExecute");
			Intent resultIntent = new Intent(BuildingListActivity.this, RestaurantListActivity.class);
			resultIntent.putParcelableArrayListExtra("RESTAURANT_RESULT", (ArrayList<? extends Parcelable>) result);
			BuildingListActivity.this.startActivity(resultIntent);
			Log.d(TAG, "end onPostExecute");
		}
	}
}
