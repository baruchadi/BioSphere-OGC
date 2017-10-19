package smellychiz.projects.ogc.util.graphics;

import android.util.Log;
import smellychiz.projects.ogc.util.helpers.Vector3;

public class TextureArea  implements java.io.Serializable {

	Texture tex;
	Vector3 uv;
	Vector3 v;
	float[] uvCoords = new float[8];

	public TextureArea(Texture t, Vector3 v3) {
		this.tex = t;
		this.v = v3;
		initUV();
		setUV();
	}

	public void initUV() {

		double x = v.getX() / tex.getWidth();
		double y = v.getY() / tex.getHeight();
		double height = v.getHeight() / tex.getHeight();
		double width = v.getWidth() / tex.getWidth();
		

		
		uv = new Vector3((float) x, (float) y, (float) width, (float) height);
	}
	
	public void echoWidthNHeight(){
		Log.d("height", ""+v.getHeight() +", "+ tex.getHeight());
		Log.d("width", ""+v.getWidth() +", "+ tex.getWidth());
	}

	public void setUV() {

//		uvCoords[0] = 0;
//		uvCoords[1] = 0.5f;
//
//		uvCoords[2] = 0;
//		uvCoords[3] = 1f;
//
//		uvCoords[4] = 0.0625f;
//		uvCoords[5] = 1f;
//
//		uvCoords[6] = 0.0625f;
//		uvCoords[7] = .5f;
		
		uvCoords[0] = uv.getX();
		uvCoords[1] = uv.getY();

		uvCoords[2] = uv.getX();
		uvCoords[3] = uv.getY()+uv.getHeight();

		uvCoords[4] = uv.getX()+uv.getWidth();
		uvCoords[5] = uv.getY()+uv.getHeight();

		uvCoords[6] = uv.getX()+uv.getWidth();
		uvCoords[7] = uv.getY();
		
//		for (int i = 0; i < uvCoords.length; i++) {
//			System.out.println(uvCoords[i]);
//		}
//		System.out.println("=====================");

	}

	public Texture getTex() {
		return tex;
	}

	public float[] getUvCoords() {
		return uvCoords;
	}
	public void EchoUv(){
		for(int i = 0; i<uvCoords.length; i++){
			Log.d("uv "+i, ""+uvCoords[i]);
		}
	}
}
