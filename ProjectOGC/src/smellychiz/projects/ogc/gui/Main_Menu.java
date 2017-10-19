package smellychiz.projects.ogc.gui;

import smellychiz.projects.ogc.R;
import smellychiz.projects.ogc.StartPoint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class Main_Menu extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		this.setContentView(R.layout.menu);
		
		StartPoint.load = false;
		
		Button newGame = (Button) findViewById(R.id.new_game);
		Button loadGame = (Button) findViewById(R.id.LoadGame);
		Button quitGame = (Button) findViewById(R.id.quit_game);

		newGame.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				StartPoint.load = false;		
				startActivity(new Intent("android.intent.action.GAME"));
				Toast.makeText(Main_Menu.this, "please wait, this might take a while...", Toast.LENGTH_SHORT).show();
			}
		});

		loadGame.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				StartPoint.load = true;
				startActivity(new Intent("android.intent.action.GAME"));
				Toast.makeText(Main_Menu.this, "LOADING SAVED DATA, PLEASE WAIT... \nOR DIE.", Toast.LENGTH_SHORT).show();
			}
		});

		quitGame.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();
				System.exit(0);
				
			}
		});
		
	}

}
