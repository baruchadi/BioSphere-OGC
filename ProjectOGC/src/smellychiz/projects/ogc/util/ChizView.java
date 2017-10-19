package smellychiz.projects.ogc.util;

import java.util.LinkedList;
import java.util.List;

import smellychiz.projects.ogc.objects.world.Biome;
import smellychiz.projects.ogc.util.helpers.Finger;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

public class ChizView extends GLSurfaceView {
	public ChizRenderer c;
	int Width, Height;

	public ChizView(Context context, int width, int height,boolean load) {
		super(context);
		this.Width = width;
		this.Height = height;
		System.out.println("Width: " + Width + ", Height: " + Height);
		// Create an OpenGL ES 2.0 context.
		setEGLContextClientVersion(2);

		c = new ChizRenderer(context,load);
		// Set the Renderer for drawing on the GLSurfaceView
		setRenderer(c);

		// Render the view only when there is a change in the drawing data
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		fingers.add(0, null);
		fingers.add(1, null);
		this.setOnTouchListener(listener);
	}

	public ChizView(Context context, int width, int height,boolean load,Biome b) {
		super(context);
		this.Width = width;
		this.Height = height;
		System.out.println("Width: " + Width + ", Height: " + Height);
		// Create an OpenGL ES 2.0 context.
		setEGLContextClientVersion(2);

		c = new ChizRenderer(context,load,b);
		// Set the Renderer for drawing on the GLSurfaceView
		setRenderer(c);

		// Render the view only when there is a change in the drawing data
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		fingers.add(0, null);
		fingers.add(1, null);
		this.setOnTouchListener(listener);
	}
	
	List<Finger> fingers = new LinkedList<Finger>();

	private View.OnTouchListener listener = new View.OnTouchListener() {

		public boolean onTouch(View view, MotionEvent event) {
			if (c.isReady()) {
				int action = event.getAction() & MotionEvent.ACTION_MASK;

				switch (action) {

				case MotionEvent.ACTION_DOWN: {
					int id = event.getPointerId(0);
					if (fingers.get(0) == null)
						fingers.add(
								0,
								new Finger(event.getX(0) / Width
										* c.camera.pos.getWidth(),
										(Height - event.getY(0)) / Height
												* c.camera.pos.getHeight(), id));

					System.out.println(id + " is down");
					break;
				}
				case MotionEvent.ACTION_POINTER_DOWN: {
					int id = event.getPointerId(getIndex(event));

					if (fingers.get(1) == null)
						fingers.add(
								1,
								new Finger(event.getX(1) / Width
										* c.camera.pos.getWidth(),
										(Height - event.getY(1)) / Height
												* c.camera.pos.getHeight(), id));

					System.out.println(id + " is down");
					break;
				}
				case MotionEvent.ACTION_POINTER_UP: {
					int id = event.getPointerId(getIndex(event));
					if (fingers.get(1) != null) {
						if (fingers.get(1).getId() == id) {
							if (fingers.get(1).type == Finger.DPAD_FINGER)
								c.DpadRelease();

							fingers.set(1, null);
						}
					}
					System.out.println(id + " is up");
					break;
				}
				case MotionEvent.ACTION_UP: {

					int id = event.getPointerId(0);
					if (fingers.get(0) != null) {
						if (fingers.get(0).getId() == id) {
							if (fingers.get(0).type == Finger.DPAD_FINGER)
								c.DpadRelease();

							fingers.set(0, null);
						}
					}
					System.out.println(id + " is up");

					break;

				}
				case MotionEvent.ACTION_MOVE: {

					int pointerCount = event.getPointerCount();

					if (pointerCount > 2) {
						pointerCount = 2;

					}

					for (int t = 0; t < pointerCount; t++) {

						float x = event.getX(t) / Width;
						float y = (Height - event.getY(t)) / Height;

						x *= c.camera.pos.getWidth();

						y *= c.camera.pos.getHeight();
						float x1 = x + c.camera.pos.getX();

						float y1 = y + c.camera.pos.getY();
						int id = event.getPointerId(t);
						if (fingers.get(0) != null) {
							if (fingers.get(0).getId() == id) {
								fingers.get(0).setPos(x, y);

								if (fingers.get(0).type == Finger.DPAD_FINGER)
									c.touchDpad(fingers.get(0));
								else if (fingers.get(0).type == Finger.SCREEN_FINGER) {
									fingers.get(0).setPos(x1, y1);
									c.touchUp(fingers.get(0).getX(), fingers
											.get(0).getY());
								}
								// System.out.println(fingers.get(0).getX() +
								// ", "
								// + fingers.get(0).getY());

							}
						}
						if (fingers.get(1) != null) {
							if (fingers.get(1).getId() == id) {
								fingers.get(1).setPos(event.getX(t),
										event.getY(t));

								if (fingers.get(1).type == Finger.DPAD_FINGER)
									c.touchDpad(fingers.get(1));
								else if (fingers.get(1).type == Finger.SCREEN_FINGER) {
									fingers.get(1).setPos(x1, y1);
									c.touchUp(fingers.get(1).getX(), fingers
											.get(1).getY());
								}
								// System.out.println(fingers.get(1).getX() +
								// ", "
								// + fingers.get(1).getY());

							} 
						}
					}

				}

				}
				if (fingers.get(1) == null && fingers.get(0) == null) {
					c.DpadRelease();
				} else if (fingers.get(1) != null && fingers.get(0) != null) {
					if (fingers.get(1).type != Finger.DPAD_FINGER
							&& fingers.get(0).type != Finger.DPAD_FINGER) {
						c.DpadRelease();
					}
				}
				return true;
			}else
				return true;
		}

	};

	private int getIndex(MotionEvent event) {

		int idx = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

		return idx;
	}
}
