package com.example.sportsapp;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.XML;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.model.sportsapp.Game;


public class scoresFragment extends ListFragment {
	String sport="";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sport = getArguments().getString("sport");
		new HttpGetTask().execute();
		
	}
	
	

	private class HttpGetTask extends AsyncTask<Void, Void, List<Game>> {

		private static final String TAG = "HttpGetTask";
		
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String todayDate = df.format(today);
		
		
		private String URL = "http://scores.nbcsports.msnbc.com/ticker/data/gamesMSNBC.js.asp?jsonp=true&sport="+sport+"&period="+todayDate;

		AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

		@Override
		protected List<Game> doInBackground(Void... params) {
			Log.i("date",todayDate);
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
		protected void onPostExecute(List<Game> result) {
			if (null != mClient)
				mClient.close();
			setListAdapter(new MyAdapter(getActivity(),R.layout.row,result));
		}
	}
	class MyAdapter extends ArrayAdapter{
		private List<Game> myList;
		private Context context;

		public MyAdapter(Context context, int resource, List<Game> result) {
			super(context, resource, result);
			myList = result;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView home;
			TextView away;
			TextView homeScore;
			TextView awayScore;
			
			
			//reuse converView if you can to speed up scrolling on listview
			if(convertView == null){
				//inflate the listview
				LayoutInflater inflator = ((Activity)context).getLayoutInflater();
				convertView = inflator.inflate(R.layout.row, parent, false);
				convertView.setTag(R.id.homeLabel, convertView.findViewById(R.id.homeLabel));
				convertView.setTag(R.id.awayLabel, convertView.findViewById(R.id.awayLabel));
				convertView.setTag(R.id.homeScore, convertView.findViewById(R.id.homeScore));
				convertView.setTag(R.id.awayScore, convertView.findViewById(R.id.awayScore));
				
			}
			
			home = (TextView) convertView.getTag(R.id.homeLabel);
			away = (TextView) convertView.getTag(R.id.awayLabel);
			homeScore = (TextView) convertView.getTag(R.id.homeScore);
			awayScore = (TextView) convertView.getTag(R.id.awayScore);
			
			home.setText(myList.get(position).getHomeTeam());
			away.setText(myList.get(position).getAwayTeam());
			if(myList.get(position).getHomeScore()==null){
				homeScore.setText("");
			}else{
				homeScore.setText(myList.get(position).getHomeScore().toString());
			}
			if(myList.get(position).getAwayScore()==null){
				awayScore.setText("");
			}else{
				awayScore.setText(myList.get(position).getAwayScore().toString());
			}
			
			return convertView;
		}

	}
	

	private class JSONResponseHandler implements ResponseHandler<List<Game>> {

		@Override
		public List<Game> handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			List<Game> result = new ArrayList<Game>();
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

				// Iterate over game list
				for (int idx = 0; idx < games.length(); idx++) {
					String xml = games.getString(idx);
					JSONObject game = XML.toJSONObject(xml);
					String jsonPrettyPrintString = game.toString(4);
		            Log.i("game", jsonPrettyPrintString);
					JSONObject entry = game.getJSONObject("ticker-entry");
					
					//GET GAME ID
					int id = entry.getInt("gamecode");
					
					//GET GAMESTATE OBJECT
					JSONObject gamestate = entry.getJSONObject("gamestate");
					
					//GET GAME STATUS
					String status = gamestate.getString("status");
					
					
					
					//GET GAME TIME
					String time = gamestate.getString("gametime");
					String date = gamestate.getString("gamedate");
					SimpleDateFormat sdf = new SimpleDateFormat("M/d h:mm a", Locale.US);
					Date gameDate = null;
					try {
						gameDate = sdf.parse(date + " " + time);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//GET TV BROADCAST
					String tv = gamestate.getString("tv");
					
					
					//GET HOME AND AWAY TEAM OBJECTS
					JSONObject homeTeam = entry.getJSONObject("home-team");
					JSONObject awayTeam = entry.getJSONObject("visiting-team");
					
					//GET NICKNAMES
					String homeNickname = homeTeam.getString("nickname");
					String awayNickname = awayTeam.getString("nickname");
					
					//GET TEAM LOGOS
					JSONObject homeLogoObject = homeTeam.getJSONObject("team-logo");
					String homeLogo = homeLogoObject.getString("link");
					
					JSONObject awayLogoObject = awayTeam.getJSONObject("team-logo");
					String awayLogo = awayLogoObject.getString("link");
					Integer homeScore;
					Integer awayScore;
					
					
					//GET SCORES
					if(!status.equalsIgnoreCase("Pre-Game")){
						JSONArray homeScoreArr = homeTeam.getJSONArray("score");
						homeScore = homeScoreArr.getInt(0);
						Log.i("scores", homeScore.toString());
						JSONArray awayScoreArr = awayTeam.getJSONArray("score");
						awayScore = awayScoreArr.getInt(0);
						Log.i("scores", awayScore.toString());
					}else{
						homeScore =null;
						awayScore = null;
					}
		
					

					
					result.add(new Game(id,homeNickname,awayNickname,
							homeScore,awayScore,
							status,gameDate,homeLogo,awayLogo,tv));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return result;
		}
		
	}
	


}
