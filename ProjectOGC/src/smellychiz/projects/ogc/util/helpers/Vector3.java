package smellychiz.projects.ogc.util.helpers;

public class Vector3 implements java.io.Serializable{

	private float x, y, z;
	private float width, height;
	private boolean flipped = false;

	public Vector3(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.z = 0;
		this.width = width;
		this.height = height;

	}

	public Vector3(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.z = 0;
		this.width = width;
		this.height = height;

	}

	public Vector3(float x, float y, float z, int width, int height) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;

	}

	public static boolean VectorInsideVector(Vector3 host, Vector3 point) {
		// System.out.println(point.getX() +", " + point.getY() +
		// ", "+host.getX() + ", " + host.getY());
		if (point.getX() > host.getX()
				&& point.getX() < host.getX() + host.getWidth()
				&& point.getY() < host.getY()
				&& point.getY() > host.getY() + host.width)
			return true;
		else
			return false;
	}

	Vector3 getVector() {

		return this;
	}

	public boolean isFlipped() {
		return flipped;
	}

	public float getX() {

		if (!flipped)
			return x;
		else
			return x + width;
	}

	public float getRealX() {

		
			return x;
	}
	
	
	public void setX(float x) {
		this.x = x;
	}

	public float getY() {

		return y;

	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getWidth() {
		if (!flipped)
			return width;
		else
			return -width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
 
	public float getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setVector(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

	}

	public void addY(Vector3 v) {
		this.y += v.y;
	}

	public void addX(Vector3 v) {
		this.x += v.x;
	}

	public void addY(float y) {
		this.y += y;
	}

	public void addX(float x) {
		this.x += x;
	}

	public void addXY(Vector3 v) {
		this.x += v.x;
		this.y += v.y;
	}

	Vector3 setVector(Vector3 v) {
		this.x = v.x;
		this.y = v.y;
		this.width = v.width;
		this.height = v.height;
		return this;
	}

	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}

}
