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
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class RestaurantListActivity extends Activity {
	private static final String TAG = "RestaurantListActivity";
	
	private ListView _restaurantListView;
	private ArrayList<Restaurant> _restaurantList;
	private RestaurantDetail _restaurantDetail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "begin onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_list_activity);
		 
		_restaurantList = new ArrayList<Restaurant>();
		_restaurantList = this.getIntent().getParcelableArrayListExtra("RESTAURANT_RESULT");
		
		_restaurantListView = (ListView)this.findViewById(R.id.restaurant_list_view);
		_restaurantListView.setAdapter(new RestaurantListAdapter(this, R.layout.restaurant_list_item, _restaurantList));
		_restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				new NetworkRequestTask().execute(_restaurantList.get(position).getId());
			}
		});
		
		Log.d(TAG, "end onCreate");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant_list, menu);
		return true;
	}
	
	/**
	 * Class to run HTTP network requests in a worker thread. Necessary to
	 * keep the UI interactive.
	 * 
	 * Types specified are <Argument Type, Progress Update Type, Return Type>
	 */
	private class NetworkRequestTask extends AsyncTask<Integer, Integer, RestaurantDetail> {
		private static final String TAG = "MainActivity.NetworkRequestTask";
		private static final String APIBASE = "http://stillopenatx.com/hungryhorns/restaurant/?";
		
		private HttpResponse _response;
		
		@Override
		protected RestaurantDetail doInBackground(Integer... input) {
			Log.d(TAG, "begin doInBackground");
			try {        
				HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet();
				
				StringBuilder urlBuilder = new StringBuilder(APIBASE);
				urlBuilder.append("id="+input[0]);
				
				request.setURI(new URI(urlBuilder.toString()));
				
				_response = client.execute(request);
				InputStream in = new BufferedInputStream(_response.getEntity().getContent());
				java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
				String output = s.hasNext() ? s.next() : "";
				
				JSONObject obj = new JSONObject(output);
				int id = obj.getInt("id");
				String name = obj.getString("name");
				String building = obj.getString("building");
				String hours = obj.getString("hours");
				String menu = obj.getString("items");
				_restaurantDetail = new RestaurantDetail(id, name, building, hours, menu);
				
				Log.d(TAG, id +"\t"+ name +"\t"+ building +"\t"+ hours);
			}
			catch (JSONException e) {
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
			
			return _restaurantDetail;
		}

		/** 
		 * Invoked in asynchronously in MainActivity when the network request 
		 * has finished and doInBackground returns its result.
		 */
		@Override
		protected void onPostExecute(RestaurantDetail result) {
			Log.d(TAG, "begin onPostExecute");
			
			Intent resultIntent = new Intent(RestaurantListActivity.this, RestaurantDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelable("RESTAURANT_DETAIL", _restaurantDetail);
			resultIntent.putExtras(bundle);
			RestaurantListActivity.this.startActivity(resultIntent);
			
			Log.d(TAG, "end onPostExecute");
		}
	}
}
