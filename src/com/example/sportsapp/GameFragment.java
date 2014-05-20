package com.example.sportsapp;

import com.model.sportsapp.Game;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameFragment extends Fragment{
	Game game;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		game = (Game) getArguments().getSerializable("game");
		
		
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View gameView = inflater.inflate(R.layout.game_fragment, container,false);
		TextView home = (TextView)gameView.findViewById(R.id.homeTeam_single);
		TextView away = (TextView)gameView.findViewById(R.id.awayTeam_single);
		
		TextView homeScore = (TextView)gameView.findViewById(R.id.awayScore_single);
		TextView awayScore = (TextView)gameView.findViewById(R.id.homeScore_single);
		
		TextView tv = (TextView)gameView.findViewById(R.id.tv_single);
		
		TextView status = (TextView)gameView.findViewById(R.id.status_single);
		
		home.setText(game.getHomeTeam());
		away.setText(game.getAwayTeam());
		
		homeScore.setText(game.getHomeScore().toString());
		awayScore.setText(game.getAwayScore().toString()																																			);
		
		tv.setText(game.getTv());
		
		status.setText(game.getStatus());
		
		
		return gameView;
		
	}


	
	
	

}
