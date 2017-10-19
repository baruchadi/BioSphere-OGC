package smellychiz.projects.ogc.objects;

import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.TextureArea;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Dpad {

	public static final int LEFT_ARROW = 0;
	public static final int RIGHT_ARROW = 1;
	public static final int TOP_ARROW = 2;
	public static final int TOP_RIGHT_ARROW = 3;
	public static final int TOP_LEFT_ARROW = 4;
	public static final int BOTTOM_ARROW = 5; 
	public static final int CENTER = 6;
	public static final int KEY_UP = 10;

	public int button = KEY_UP;

	public boolean touched = false;

	public final static float Width = 4.5f;
	public final static float Height = 4.5f;
	public static final float x = 0, y = 0;

	public static Vector3 bound;

	public GameObject pad, circle;

	public Dpad(Context mContext, TextureArea p, TextureArea c, Camera2D cam) {

		pad = new GameObject(mContext, new Vector3(x, y, Width, Height), cam, p);
		circle = new GameObject(mContext, new Vector3(x, y, 1.5f, 1.5f), cam, c);
	}

	public void setTouch(float x, float y) {

		if (x > pad.bound.getX() + pad.bound.getWidth())
			x = pad.bound.getX() + pad.bound.getWidth();
		if (y > pad.bound.getY() + pad.bound.getHeight())
			y = pad.bound.getY() + pad.bound.getHeight();

		x -= circle.bound.getWidth() / 2;
		y -= circle.bound.getHeight() / 2;

		circle.bound.setX(x);
		circle.bound.setY(y);

		circle.initVertices();
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public boolean isTouched() {
		return touched;
	}

	public void Draw() {
		pad.draw();
		if (touched)
			circle.draw();
	}

	public int getButton() {
		float x = circle.bound.getX() + circle.bound.getWidth() / 2;
		float y = circle.bound.getY() + circle.bound.getHeight() / 2;
		if (touched) {
			if (x > pad.bound.getX()
					&& x < pad.bound.getX() + pad.bound.getWidth()
					&& y > pad.bound.getY()
					&& y < pad.bound.getY() + pad.bound.getHeight() / 3)
				button = BOTTOM_ARROW;
			else if (x > pad.bound.getX()
					&& x < pad.bound.getX() + pad.bound.getWidth() / 3
					&& y > pad.bound.getY() + pad.bound.getHeight() / 3
					&& y < pad.bound.getY() + ((pad.bound.getHeight() / 3) * 2))
				button = LEFT_ARROW;
			else if (x > pad.bound.getX()
					&& x < pad.bound.getX() + pad.bound.getWidth() / 3
					&& y > pad.bound.getY() + ((pad.bound.getHeight() / 3) * 2)
					&& y < pad.bound.getY() + pad.bound.getHeight())
				button = TOP_LEFT_ARROW;
			else if (x > pad.bound.getX() + pad.bound.getWidth() / 3
					&& x < pad.bound.getX() + ((pad.bound.getWidth() / 3) * 2)
					&& y > pad.bound.getY() + ((pad.bound.getHeight() / 3) * 2)
					&& y < pad.bound.getY() + pad.bound.getHeight())
				button = TOP_ARROW;
			else if (x > pad.bound.getX() + ((pad.bound.getWidth() / 3) * 2)
					&& x < pad.bound.getX() + pad.bound.getWidth()
					&& y > pad.bound.getY() + ((pad.bound.getHeight() / 3) * 2)
					&& y < pad.bound.getY() + pad.bound.getHeight())
				button = TOP_RIGHT_ARROW;
			else if (x > pad.bound.getX() + ((pad.bound.getWidth() / 3) * 2)
					&& x < pad.bound.getX() + pad.bound.getWidth()
					&& y > pad.bound.getY() + pad.bound.getHeight() / 3
					&& y < pad.bound.getY() + ((pad.bound.getHeight() / 3) * 2))
				button = RIGHT_ARROW;
			else if (x > pad.bound.getX() + pad.bound.getWidth() / 3
					&& x < pad.bound.getX() + ((pad.bound.getWidth() / 3) * 2)
					&& y > pad.bound.getY() + pad.bound.getHeight() / 3
					&& y < pad.bound.getY() + ((pad.bound.getHeight() / 3) * 2))
				button = CENTER;
			else
				button = KEY_UP;
		} else
			button = KEY_UP;
		return button;
	}
}
