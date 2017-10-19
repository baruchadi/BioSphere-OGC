package smellychiz.projects.ogc.shapes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

public class Square {
	private final String vertexShaderCode=	
		"attribute vec4 vPosition;"+
	"uniform mat4 uMVPMatrix;"+

	"void main()"+
	"{"+
	 "   gl_Position = uMVPMatrix * vPosition;"+
	"}";

	private final String fragmentShaderCode = "precision mediump float;"
			+ "uniform vec4 vColor;" + "void main() {"
			+ "  gl_FragColor = vColor;" + "}";

	static final int COORDS_PER_VERTEX = 3;

	static float triangleCoords[] = { // in counterclockwise order:
	20f, 200f, 0.0f, // top left
			20f, 20f, 0.0f, // bottom left
			100f, 20f, 0.0f, // bottom right
			100f, 100f, 0.0f

	};
	private short drawOrder[] = { 0, 1, 2, 0, 2, 3 };
	private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;

	private final int vertexStride = COORDS_PER_VERTEX * 4; // bytes per vertex

	// red-green-blue-alpha
	float color[] = { 0.63f, 0.76f, 0.22f, 1.0f };

	private final int mProgram;
	private int mPositionHandle;
	private int mColorHandle;
	private int mMVPMatrixHandle;
	private int ratioHandle;
	private FloatBuffer vertexBuffer;
	private ShortBuffer drawListBuffer;

	public Square() {
		ByteBuffer bb = ByteBuffer.allocateDirect(
		// # of coords values * 4 bytes per float
				triangleCoords.length * 4);

		// use native byte order
		bb.order(ByteOrder.nativeOrder());

		// create a floating point buffer from the ByteBuffer
		vertexBuffer = bb.asFloatBuffer();
		// add coordination to FloatBuffer
		vertexBuffer.put(triangleCoords);
		// set the buffer to read first coordinate
		vertexBuffer.position(0);

		ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);

		dlb.order(ByteOrder.nativeOrder());

		drawListBuffer = dlb.asShortBuffer();
		drawListBuffer.put(drawOrder);
		drawListBuffer.position(0);

	
//		int vertexShader = ChizRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
//				vertexShaderCode);
//		int fragmentShader = ChizRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
//				fragmentShaderCode);

		mProgram = GLES20.glCreateProgram();
//		GLES20.glAttachShader(mProgram, vertexShader);
//		GLES20.glAttachShader(mProgram, fragmentShader);
		GLES20.glLinkProgram(mProgram);

	}

	public void move(){
		
	}
	
	public void draw(float[] mvpMatrix) {
		// Add program to OpenGL ES environment
		GLES20.glUseProgram(mProgram);

		// get handle to vertex shader's vPosition member
		mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

		// Enable a handle to the triangle vertices
		GLES20.glEnableVertexAttribArray(mPositionHandle);

		// Prepare the triangle coordinate data
		GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
				GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

		// get handle to fragment shader's vColor member
		mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

		// set color for drawing the triangle
		GLES20.glUniform4fv(mColorHandle, 1, color, 0);

		// get handle to shape's transformation matrix
		mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		
		
		// apply the projection and view transformation
		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

		// Draw the triangle
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,
				GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		// dispable vertex array
		GLES20.glDisableVertexAttribArray(mPositionHandle);

	}
}
