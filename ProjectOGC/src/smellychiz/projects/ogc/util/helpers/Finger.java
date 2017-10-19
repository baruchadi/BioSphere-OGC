package smellychiz.projects.ogc.util.helpers;

import smellychiz.projects.ogc.objects.Dpad;

public class Finger {
	public final static int DPAD_FINGER = 0;
	public final static int SCREEN_FINGER = 1;

	public float x, y;
	public int type;
	public int id;

	public Finger(float x, float y, int id) {
		this.id = id;
		checkType(x, y);
	}

	public void checkType(float x, float y) {
		if (x > Dpad.x && x < Dpad.x + Dpad.Width && y > Dpad.y
				&& y < Dpad.y + Dpad.Height) {
			System.out.println("inside DPAD");
			type = DPAD_FINGER;
		} else {
			System.out.println("Outside DPAD");
			type = SCREEN_FINGER;
		}

	}

	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}
}
