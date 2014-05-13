package com.example.sportsapp;
import org.json.*;




import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;




import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import com.google.android.gms.location.LocationClient;
import com.model.sportsapp.NHLgame;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Context;
import android.location.Location;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class scoresNHLFragment extends ListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new HttpGetTask().execute();
		
	}
	
	

	private class HttpGetTask extends AsyncTask<Void, Void, List<NHLgame>> {

		private static final String TAG = "HttpGetTask";

		private static final String URL = "http://scores.nbcsports.msnbc.com/ticker/data/gamesMSNBC.js.asp?jsonp=true&sport=NHL&period=20140513";

		AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

		@Override
		protected List<NHLgame> doInBackground(Void... params) {
			
			HttpGet request = new HttpGet(URL);
			JSONResponseHandler responseHandler = new JSONResponseHandler();
			try {
				return mClient.execute(request, responseHandler);
			} catch (ClientProtocolException e) {
				Log.i("error", "client");
				e.printStackTrace();
			} catch (IOException e) {
				Log.i("error", "IO");
				e.printStackTrace();
			}
			return null;
		}
		

 
		@Override
		protected void onPostExecute(List<NHLgame> result) {
			if (null != mClient)
				mClient.close();
			setListAdapter(new MyAdapter(getActivity(),R.layout.row,result));
		}
	}
	class MyAdapter extends ArrayAdapter{
		private List<NHLgame> myList;
		private Context context;

		public MyAdapter(Context context, int resource, List<NHLgame> result) {
			super(context, resource, result);
			myList = result;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView home;
			TextView away;
			
			//reuse converView if you can to speed up scrolling on listview
			if(convertView == null){
				//inflate the listview
				LayoutInflater inflator = ((Activity)context).getLayoutInflater();
				convertView = inflator.inflate(R.layout.row, parent, false);
				convertView.setTag(R.id.homeLabel, convertView.findViewById(R.id.homeLabel));
				convertView.setTag(R.id.awayLabel, convertView.findViewById(R.id.awayLabel));
				
				
			}
			
			home = (TextView) convertView.getTag(R.id.homeLabel);
			away = (TextView) convertView.getTag(R.id.awayLabel);
			
			home.setText(myList.get(position).getHome_team());
			away.setText(myList.get(position).getAway_team());
			
			return convertView;
		}
		
		
		
		
		

	}

	private class JSONResponseHandler implements ResponseHandler<List<NHLgame>> {

		@Override
		public List<NHLgame> handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			List<NHLgame> result = new ArrayList<NHLgame>();
			String JSONResponse = new BasicResponseHandler()
					.handleResponse(response);
			Log.i("info", "starting json handler");
			JSONResponse = JSONResponse.replace("shsMSNBCTicker.loadGamesData(", "");
			JSONResponse = JSONResponse.replace(");", "");
			Log.i("info", JSONResponse);
			try {

				Log.i("info", "Still working");
				JSONObject responseObject = (JSONObject) new JSONTokener(
						JSONResponse).nextValue();
				Log.i("info", "after response object");
		
				// Extract value of "list" key -- a List
				JSONArray games = responseObject
						.getJSONArray("games");

				// Iterate over earthquakes list
				for (int idx = 0; idx < games.length(); idx++) {
					String xml = games.getString(0);
					try{
					JSONObject game = XML.toJSONObject(xml);
					String jsonPrettyPrintString = game.toString(4);
		            Log.i("jsonOject", jsonPrettyPrintString);
					}catch(JSONException je){
						je.printStackTrace();
					}
					

					
					//result.add(new NHLgame(id,home,away,0,0,"ok"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return result;
		}
		
	}


}
