package smellychiz.projects.ogc.objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Texture;
import smellychiz.projects.ogc.util.graphics.TextureArea;
import smellychiz.projects.ogc.util.helpers.GLHelper;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

public class GameObject extends GLHelper implements java.io.Serializable {

	transient private ShortBuffer drawListBuffer;
	private static final int FLOAT_SIZE_BYTES = 4;
	private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 5 * FLOAT_SIZE_BYTES;
	private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
	private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
	transient  private FloatBuffer mTriangleVertices;
	private float[] mTriangleVerticesData = new float[20];
	private float[] UVcoord = { 0, 0, 0, 1, 1, 1, 1, 0 };
	private short drawOrder[] = { 0, 1, 2, 0, 2, 3 };

	transient Camera2D camera;

	public Vector3 bound;

	Texture tex;
	TextureArea tArea;

	private int mProgram;
	private int muMVPMatrixHandle;
	private int maPositionHandle;
	private int maTextureHandle;

	private static String TAG = "GLES20TriangleRenderer";
	private final String mVertexShader = "uniform mat4 uMVPMatrix;\n"
			+ "attribute vec4 aPosition;\n" + "attribute vec2 aTextureCoord;\n"
			+ "varying vec2 vTextureCoord;\n" + "void main() {\n"
			+ "  gl_Position = uMVPMatrix * aPosition;\n"
			+ "  vTextureCoord = aTextureCoord;\n" + "}\n";

	private final String mFragmentShader = "precision mediump float;\n"
			+ "varying vec2 vTextureCoord;\n" + "uniform sampler2D sTexture;\n"
			+ "void main() {\n"
			+ "  gl_FragColor = texture2D(sTexture, vTextureCoord);\n"
			+ "gl_FragColor.a *= " + 1f + ";\n " + "}\n";

	Context mContext;

	public GameObject(Context context, Vector3 v, Camera2D c, Texture t) {

		mContext = context;
		this.camera = c;
		this.tex = t;
		initVertices(v);

		initProgram();

	}



	public GameObject(Context context, Vector3 v, Camera2D c, TextureArea t) {

		mContext = context;
		this.camera = c;
		setTexture(t.getTex());
		this.tArea = t;

		UVcoord = t.getUvCoords();

		initVertices(v);

		initProgram();

	}

	public void EchoUv() {
		for (int i = 0; i < UVcoord.length; i++) {
			Log.d("uv " + i, "" + UVcoord[i]);
		}
	}

	public void setUVcoord(float[] uVcoord) {
		UVcoord = uVcoord;
	}

	private void initUVCoords(float[] uvCoords) {
		for (int i = 0; i < UVcoord.length; i++) {
			UVcoord[i] = uvCoords[i];

		}

	}

	public GameObject(Context context, Vector3 v, Camera2D c) {
		super();
		mContext = context;
		this.camera = c;

		initVertices(v);

		initProgram();
	}

	public GameObject(int i) {
	}

	public void putOrder() {
		mTriangleVertices = ByteBuffer
				.allocateDirect(mTriangleVerticesData.length * FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		mTriangleVertices.put(mTriangleVerticesData).position(0);

		ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);

		dlb.order(ByteOrder.nativeOrder());

		drawListBuffer = dlb.asShortBuffer();
		drawListBuffer.put(drawOrder);
		drawListBuffer.position(0);

	}

	public void initVertices() {

		mTriangleVerticesData[0] = bound.getX();
		mTriangleVerticesData[1] = bound.getY() + bound.getHeight();
		mTriangleVerticesData[2] = bound.getZ();

		mTriangleVerticesData[3] = UVcoord[0];
		mTriangleVerticesData[4] = UVcoord[1];

		mTriangleVerticesData[5] = bound.getX();
		mTriangleVerticesData[6] = bound.getY();
		mTriangleVerticesData[7] = bound.getZ();

		mTriangleVerticesData[8] = UVcoord[2];
		mTriangleVerticesData[9] = UVcoord[3];

		mTriangleVerticesData[10] = bound.getX() + bound.getWidth();
		mTriangleVerticesData[11] = bound.getY();
		mTriangleVerticesData[12] = bound.getZ();

		mTriangleVerticesData[13] = UVcoord[4];
		mTriangleVerticesData[14] = UVcoord[5];

		mTriangleVerticesData[15] = bound.getX() + bound.getWidth();
		mTriangleVerticesData[16] = bound.getY() + bound.getHeight();
		mTriangleVerticesData[17] = bound.getZ();

		mTriangleVerticesData[18] = UVcoord[6];
		mTriangleVerticesData[19] = UVcoord[7];
		putOrder();
	}

	public void initVertices(Vector3 v) {
		bound = v;
		initVertices();
	}

	public void setTextureArea(TextureArea tArea) {

		this.tArea = tArea;

		this.setUVcoord(tArea.getUvCoords());

	}

	public TextureArea getTArea() {
		return this.tArea;
	}

	private void initProgram() {
		mProgram = createProgram(mVertexShader, mFragmentShader);
		if (mProgram == 0) {
			return;
		}
		maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");

		if (maPositionHandle == -1) {
			throw new RuntimeException(
					"Could not get attrib location for aPosition");
		}
		maTextureHandle = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");

		if (maTextureHandle == -1) {
			throw new RuntimeException(
					"Could not get attrib location for aTextureCoord");
		}

		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

		if (muMVPMatrixHandle == -1) {
			throw new RuntimeException(
					"Could not get attrib location for uMVPMatrix");
		}

	}

	public boolean contains(float x, float y) {
		if (this.bound.getX() < x
				&& this.bound.getX() + this.bound.getWidth() > x
				&& this.bound.getY() < y
				&& this.bound.getY() + this.bound.getHeight() > y) {

			System.out.println("Contains!");
			return true;
		} else
			return false;
	}

	public void setTexture(Texture t) {
		this.tex = t;
	}

	public void draw() {
		GLES20.glUseProgram(mProgram);

		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex.mTextureID);

		mTriangleVertices.position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
				false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);

		mTriangleVertices.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
		GLES20.glEnableVertexAttribArray(maPositionHandle);

		GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT,
				false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);

		GLES20.glEnableVertexAttribArray(maTextureHandle);

		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				camera.getProjMatrix(), 0);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,
				GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

	}

}
