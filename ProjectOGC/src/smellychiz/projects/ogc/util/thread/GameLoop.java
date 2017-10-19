package smellychiz.projects.ogc.util.thread;

import smellychiz.projects.ogc.StartPoint;
import smellychiz.projects.ogc.util.ChizRenderer;
import android.util.Log;

public class GameLoop extends Thread{

	private final static int maxFPS = 30;
	private final static int maxFrameSkips = 5;
	private final static double framePeriod = 1000 / maxFPS;

	public static boolean running;
	public final static String TAG = "Chiz";

	public ChizRenderer rend;

	Thread t1,t2;
	
	//FPSCounter fpsCounter;
	
	float deltaTime;
	
	public GameLoop() {
		//this.fpsCounter = new FPSCounter();
		deltaTime = 1.0f;
		
	}

	
	
	@Override
	public void run() {
		running = StartPoint.isRunning();
		long beginTime;
		long timeDiff;
		int sleepTime;
		int framesSkipped;
		
		double last= 0;
		
		
		sleepTime = 0;
		while (running) {

			running = StartPoint.isRunning();
			

			
			beginTime = System.currentTimeMillis();
			final double now = beginTime;
			if(last != 0)
				deltaTime = (float) ((now - last)/framePeriod);
			framesSkipped = 0;
			
			

			timeDiff = System.currentTimeMillis() - beginTime;
			ChizRenderer.setDeltaTime((int) deltaTime);
			StartPoint.mGLView.requestRender();
			sleepTime = (int) (framePeriod - timeDiff);
			
			
			last = now;
			
			//fpsCounter.tick();
			//Log.d("FPS", "FPS: "+fpsCounter.getFPS());
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);

				} catch (InterruptedException e) {
				}
			}

			while (sleepTime < 0 && framesSkipped < maxFrameSkips) {

				sleepTime += framePeriod;
				framesSkipped++;
				Log.d(TAG, "Frames Skipped");
			}
		}

	}
	
	

	
}
