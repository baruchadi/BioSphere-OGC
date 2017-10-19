package smellychiz.projects.ogc.util;

import android.opengl.Matrix;
import smellychiz.projects.ogc.util.helpers.Vector3;

public class Camera2D implements java.io.Serializable {

	public Vector3 pos;
	private float ratio;
	private float[] mProjMatrix = new float[16];
	private float[] mProjMatrix2 = new float[16];
	private float[] Ratio = new float[16];
	private float[] Ortho = new float[16];

	public Camera2D(float x, float y, int height, int width) {
		pos = new Vector3(x, y, width, height);

	}

	public void init(Vector3 pos1) {
		this.pos = pos1;
		Matrix.orthoM(mProjMatrix2, 0, pos.getX(), pos.getX() + pos.getWidth(),
				pos.getY(), pos.getY() + pos.getHeight(), -5f, 5f);
		// Matrix.setLookAtM(mProjMatrix, 0, 0, 0, 3, 0, 0, 0, 0, 1, 0);
	}

	public void init() {
		Matrix.orthoM(mProjMatrix2, 0, pos.getX(), pos.getX() + pos.getWidth(),
				pos.getY(), pos.getY() + pos.getHeight(), -5f, 5f);
		// Matrix.setLookAtM(mProjMatrix, 0, 0, 0, 3, 0, 0, 0, 0, 1, 0);
	}

	public void cOrtho(int i, float ratio2, float ratio3, float j, float k,
			int l, int m) {
		Matrix.orthoM(Ratio, i, ratio2, ratio3, j, k, l, m);
	}

	public float[] getProjMatrix() {
		// Matrix.multiplyMM(Ortho, 0, mProjMatrix2, 0, mProjMatrix, 0);
		return mProjMatrix2;
	}

	public void centerCam(Vector3 v) {
		pos.setX(v.getX() - pos.getWidth() / 2 + v.getWidth() / 2);
		pos.setY(v.getY() - pos.getHeight() / 2 + v.getHeight() / 2);
		init(pos);
	}

	int counter = 0;
	float speed = .1f;
	private final int MOVE_LEFT = 2;
	private final int MOVE_RIGHT = 0;
	private final int MOVE_BOTTOM = 1;
	private final int MOVE_TOP = 3;
	private int move = 0;

	public Vector3 getPos() {
		return pos;
	}

	public Vector3 topLeft() {
		return new Vector3(this.pos.getX(), this.pos.getY()
				+ this.pos.getHeight(), 1, 1);
	}

	public Vector3 topRight() {
		return new Vector3(this.pos.getX() + pos.getWidth(), this.pos.getY()
				+ this.pos.getHeight(), 1, 1);
	}

	public Vector3 botLeft() {
		return new Vector3(this.pos.getX(), this.pos.getY(), 1, 1);
	}

	public Vector3 botRight() {
		return new Vector3(this.pos.getX() + pos.getWidth(), this.pos.getY(),
				1, 1);
	}

	public Vector3 botMid() {
		return new Vector3(this.pos.getX() + (pos.getWidth() / 2),
				this.pos.getY(), 1, 1);
	}

	public Vector3 topMid() {
		return new Vector3(this.pos.getX() + (pos.getWidth() / 2),
				this.pos.getY() + this.pos.getHeight(), 1, 1);
	}

	public Vector3 leftMid() {
		return new Vector3(this.pos.getX(), this.pos.getY()
				+ (pos.getHeight() / 2), 1, 1);
	}

	public Vector3 rightMid() {
		return new Vector3(this.pos.getX() + pos.getWidth(), this.pos.getY()
				+ (pos.getHeight() / 2), 1, 1);
	}
	
	public Vector3 qtr4() {
		return new Vector3(this.pos.getX()+((pos.getWidth()/4)*3), this.pos.getY()
				+ (pos.getHeight() / 4), 1, 1);
	}

	public Vector3 qtr1() {
		return new Vector3(this.pos.getX()+((pos.getWidth()/4)), this.pos.getY()
				+ ((pos.getHeight() / 4)*3), 1, 1);
	}
	public Vector3 qtr2() {
		return new Vector3(this.pos.getX()+((pos.getWidth()/4)*3), this.pos.getY()
				+ ((pos.getHeight() / 4)*3), 1, 1);
	}
	
	public Vector3 qtr3() {
		return new Vector3(this.pos.getX()+((pos.getWidth()/4)), this.pos.getY()
				+ ((pos.getHeight() / 4)), 1, 1);
	}

	public Vector3 center() {
//		Log.d("CAM",
//				"" + pos.getX() + ", " + pos.getY() + ", " + pos.getWidth()
//						+ ", " + pos.getHeight());
		return new Vector3(this.pos.getX() + (pos.getWidth() / 2),
				this.pos.getY() + (pos.getHeight() / 2), 1, 1);
	}

	public void moveAround() {
		if (counter < 40) {
			switch (move) {
			case MOVE_LEFT:
				pos.addX(-speed);
				break;
			case MOVE_RIGHT:
				pos.addX(speed);
				break;
			case MOVE_BOTTOM:
				pos.addY(-speed);
				break;
			case MOVE_TOP:
				pos.addY(speed);

			}
			counter++;
		} else {
			counter = 0;
			if (move < 3) {
				move++;
			} else {
				move = 0;
			}
		}
	}

}
