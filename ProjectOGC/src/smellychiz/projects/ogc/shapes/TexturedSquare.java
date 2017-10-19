package smellychiz.projects.ogc.shapes;

public class TexturedSquare {

//	private final String mVertexShader = "uniform mat4 uMVPMatrix;"
//			+ "attribute vec4 aPosition;" + "attribute vec2 aTextureCoord;"
//			+ "varying vec2 vTextureCoord;" + "void main()" + "{"
//			+ "gl_Position = uMVPMatrix * aPosition;"
//			+ "vTextureCoord = aTextureCoord;" + "}";
//
//	private final String mFragmentShader = "precision mediump float;"
//			+ "varying vec2 vTextureCoord;" + "uniform sampler2D sTexture;"
//			+ " void main()" + "{"
//			+ "gl_FragColor = texture2D(sTexture, vTextureCoord);" + "}";
//
//	static float squareVertices[] = { -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f,
//			1.0f, 1.0f, };
//
//	static float textureVertices[] = { 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
//			0.0f, 0.0f, };
//
//	static final int COORDS_PER_VERTEX = 3;
//	private final int mTextureCoordinateDataSize = 2;
//	static float[] triangleCoords = new float[12];
//	/*
//	 * { // in counterclockwise order: 20f, 100f, 0.0f, // top left 20f, 20f,
//	 * 0.0f, // bottom left 100f, 20f, 0.0f, // bottom right 100f, 100f, 0.0f
//	 * 
//	 * };
//	 */
//	private short drawOrder[] = { 0, 1, 2, 0, 2, 3 };
//	private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
//
//	private final int vertexStride = COORDS_PER_VERTEX * 4; // bytes per vertex
//	private int mTextureDataHandle;
//
//	private int res;
//
//	// red-green-blue-alpha
//	float color[] = { 0.63f, 0.76f, 0.22f, 1.0f };
//
//	Vector3 bound;
//
//	private int mProgram;
//	private int mPositionHandle;
//	private int mColorHandle;
//	private int mMVPMatrixHandle;
//	private int ratioHandle;
//	private FloatBuffer vertexBuffer;
//	private FloatBuffer mCubeTextureCoordinates;
//	private ShortBuffer drawListBuffer;
//
//	public void initBounds(Vector3 v) {
//		bound = v;
//		triangleCoords[0] = v.getX();
//		triangleCoords[1] = v.getY() + v.getHeight();
//		triangleCoords[2] = 0.0f;
//
//		triangleCoords[3] = v.getX();
//		triangleCoords[4] = v.getY();
//		triangleCoords[5] = 0.0f;
//
//		triangleCoords[6] = v.getX() + v.getWidth();
//		triangleCoords[7] = v.getY();
//		triangleCoords[8] = 0.0f;
//
//		triangleCoords[9] = v.getX() + v.getWidth();
//		triangleCoords[10] = v.getY() + v.getHeight();
//		triangleCoords[11] = 0.0f;
//
//	}
//
//	final float[] cubeTextureCoordinateData = {
//			// Front face
//			0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,
//			0.0f, };
//
//	private int mTextureCoordinateHandle;
//
//	private int mTextureUniformHandle;
//
//	public void moveBounds(float x, float y) {
//		bound.addX(x);
//		bound.addY(y);
//		setBounds(bound);
//
//	}
//
//	public static int loadTexture(final Context context, final int resourceId) {
//		final int[] textureHandle = new int[1];
//
//		GLES20.glGenTextures(1, textureHandle, 0);
//
//		if (textureHandle[0] != 0) {
//			final BitmapFactory.Options options = new BitmapFactory.Options();
//			options.inScaled = false; // No pre-scaling
//
//			// Read in the resource
//			final Bitmap bitmap = BitmapFactory.decodeResource(
//					context.getResources(), resourceId, options);
//
//			// Bind to the texture in OpenGL
//			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
//
//			// Set filtering
//			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
//					GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
//			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
//					GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
//
//			// Load the bitmap into the bound texture.
//			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
//
//			// Recycle the bitmap, since its data has been loaded into OpenGL.
//			bitmap.recycle();
//		}
//
//		if (textureHandle[0] == 0) {
//			throw new RuntimeException("Error loading texture.");
//		}
//
//		return textureHandle[0];
//	}
//
//	public void setBounds(Vector3 v) {
//		initBounds(v);
//
//		ByteBuffer bb = ByteBuffer.allocateDirect(
//		// # of coords values * 4 bytes per float
//				triangleCoords.length * 4);
//
//		// use native byte order
//		bb.order(ByteOrder.nativeOrder());
//
//		// create a floating point buffer from the ByteBuffer
//		vertexBuffer = bb.asFloatBuffer();
//		// add coordination to FloatBuffer
//		vertexBuffer.put(triangleCoords);
//		// set the buffer to read first coordinate
//		vertexBuffer.position(0);
//
//		mCubeTextureCoordinates = ByteBuffer
//				.allocateDirect(cubeTextureCoordinateData.length * 4)
//				.order(ByteOrder.nativeOrder()).asFloatBuffer();
//		mCubeTextureCoordinates.put(cubeTextureCoordinateData).position(0);
//
//		ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
//
//		dlb.order(ByteOrder.nativeOrder());
//
//		drawListBuffer = dlb.asShortBuffer();
//		drawListBuffer.put(drawOrder);
//		drawListBuffer.position(0);
//
//		createAndAttach();
//
//	}
//
//	public TexturedSquare(Vector3 v, int res, Context c) {
//		mProgram = createProgram(mVertexShader, mFragmentShader); // 1
//		if (mProgram == 0) {
//			return;
//		}
//		maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition"); // 2
//		if (maPositionHandle == -1) {
//			throw new RuntimeException(
//					"Could not get attrib location for aPosition");
//		}
//		maTextureHandle = GLES20.glGetAttribLocation(mProgram, "aTextureCoord"); // 3
//		if (maTextureHandle == -1) {
//			throw new RuntimeException(
//					"Could not get attrib location for aTextureCoord");
//		}
//		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix"); // 4
//		if (muMVPMatrixHandle == -1) {
//			throw new RuntimeException(
//					"Could not get attrib location for uMVPMatrix");
//		}
//
//	}
//
//	public void draw(float[] mvpMatrix) {
//		GLES20.glUseProgram(mProgram); // 3
//		GLES20.glActiveTexture(GLES20.GL_TEXTURE0); // 4
//		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID); // 5
//		mTriangleVertices.position(TRIANGLE_VERTICES_DATA_POS_OFFSET); // 6
//		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
//				false, // 7
//				TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);
//		GLES20.glEnableVertexAttribArray(maPositionHandle); // 8
//		mTriangleVertices.position(TRIANGLE_VERTICES_DATA_UV_OFFSET); // 9
//		GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT,
//				false,// 10
//				TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);
//		GLES20.glEnableVertexAttribArray(maTextureHandle); // 11
//		long time = SystemClock.uptimeMillis() % 4000L;
//		float angle = 0.090f * ((int) time);
//		Matrix.setRotateM(mMMatrix, 0, angle, 0, 0, 1.0f); // 12
//		Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);
//		Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
//		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0); // 13
//		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
//
//	}
}
