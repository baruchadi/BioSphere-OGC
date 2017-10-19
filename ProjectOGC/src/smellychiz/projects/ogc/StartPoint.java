package smellychiz.projects.ogc;

import smellychiz.projects.ogc.objects.world.Biome;
import smellychiz.projects.ogc.util.ChizView;
import smellychiz.projects.ogc.util.IO;
import smellychiz.projects.ogc.util.thread.GameLoop;
import android.app.Activity;
import android.app.Dialog;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class StartPoint extends Activity implements java.io.Serializable  {

	public static ChizView mGLView;
	public static GameLoop loop;
	private static boolean running = true;
	public Thread mSplashThread;
	public static Dialog splash;

	public static boolean load = false;
	Biome b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout rel = new RelativeLayout(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		   
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		splash = new Dialog(this,
				android.R.style.Theme_Black_NoTitleBar_Fullscreen);

		if(load){
			IO io = new IO("",this);
			 b = IO.loadBiome();
			 if(b == null)
				 System.out.println("CHUNK IS NULL");
			 mGLView = new ChizView(this, width, height,load,b);
		}else{
			mGLView = new ChizView(this, width, height,load);
		}
		
		
		
		
	
		
		setContentView(mGLView);		
		System.out.println(running);
		//splash.show();
		loop = new GameLoop();
		mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

	}

	public static boolean isRunning() {
		return running;
	}

	public static void setRunning(boolean run) {
		running = run;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		running = true;
		// loop.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// The following call pauses the rendering thread.
		// If your OpenGL application is memory intensive,
		// you should consider de-allocating objects that
		// consume significant memory here.
		mGLView.c.exit();
		mGLView.onPause();
		running = false;

	}

	@Override
	protected void onResume() {
		super.onResume();
		// The following call resumes a paused rendering thread.
		// If you de-allocated graphic objects for onPause()
		// this is a good place to re-allocate them.
		mGLView.onResume();
		running = true;
		loop.start();

	}

}
