package smellychiz.projects.ogc.objects.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import smellychiz.projects.ogc.objects.Block;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;
import android.util.Log;

public class Biome implements java.io.Serializable {
	List<Chunk> chunks = new ArrayList<Chunk>();

	public static final int GRASS_LAND = 0, DESERT = 1, SMILEY = 2;
	public static final int SMALL = 0, MEDIUM = 1, LARGE = 2;

	int Width = 0;
	int Height = 0;
	int Rows = 0;
	int Columns = 0;
	public Vector3 pos;

	int indent = 2;

	int x, y;

	private int size;

	transient Camera2D camera;
	Context mContext;

	public Biome(int size, int x, Context c, Camera2D cam) {
		this.camera = cam;
		this.mContext = c;

		setSize(size, x);
		setPos();
		// generateNullFirst();
		generateChunks();
	}

	public Chunk getChunk(float x, float y) {
		return chunks.get(this.getChunkNum(x, y));
	}

	public Block getBlock(float x, float y) {
		return getChunk(x, y).getBlock(x, y);
	}
	
	public Block getBlock(int chunk, int block){
		return chunks.get(chunk).blocks.get(block);
	}

	public void setSize(int s, int x) {
		this.size = s;
		switch (this.size) {
		case SMALL:
			Rows = 2;
			Columns = 2;
			break;
		case MEDIUM:
			Rows = 20;
			Columns = 10;
			break;
		case LARGE:
			Rows = 40;
			Columns = 20;
			break;
		}
		Width = Columns * Chunk.COLUMNS;
		Height = Rows * Chunk.ROWS;
		y = 0;
		this.x = x;
	}

	public void generateNullFirst() {
		for (int r = 0; r < this.Rows; r++) {
			for (int c = 0; c < this.Columns; c++) {
				chunks.add(r * Columns + c, null);
			}
		}
	}

	public void generateChunks() {

		float x1 = camera.pos.getX();
		float y1 = camera.pos.getY();

		int count = 0;

		for (int r = 0; r < this.Rows; r++) {
			for (int c = 0; c < this.Columns; c++) {
				chunks.add(r * Columns + c, new Chunk(camera, mContext, x
						+ (c * Chunk.COLUMNS), y - (r * Chunk.ROWS), r
						* Columns + c));
				Log.d("generating",
						"Chunk # " + count + ", "
								+ chunks.get(r * Columns + c).pos.getX() + ", "
								+ chunks.get(r * Columns + c).pos.getY());
				count++;
			}
		}
	}

	public void drawAll() {
		for (int i = 0; i < chunks.size(); i++) {
			if (chunks.get(i) != null)
				chunks.get(i).drawChunk();
		}
	}

	public void createFirstChunks(Vector3 p) {

		int num = getChunkNum(p.getX(), p.getY());
		float x1 = 0, y1 = 0;

		if (num < this.Columns) {
			x1 = num;
			y1 = 0;
		} else if (num > this.Columns) {
			x1 = (float) (num - (Math.floor(num / this.Columns) * Columns));
			y1 = (float) Math.floor(num / this.Columns);
		}

		Log.d("FirstChunk", "num " + num + ", x1 " + x1 + ", y1 " + y1);

		chunks.set(getChunkNum(p.getX(), p.getY()), new Chunk(camera, mContext,
				x + ((int) x1), y + ((int) y1 * Chunk.ROWS), Chunk.GRASS));
	}

	public int getChunkNum(float x, float y) {

		int output = -1;

		x = x / Chunk.COLUMNS;
		// Log.d("getChunkNum", "x "+x);
		y = y / Chunk.ROWS;
		// Log.d("getChunkNum", "y "+y);
		x = (float) Math.floor(x);
		// Log.d("getChunkNum", "1x "+x);
		y = (float) Math.floor(y);
		// Log.d("getChunkNum", "1y "+y);
		x -= this.pos.getX();
		// Log.d("getChunkNum", "2x "+x);
		y -= this.pos.getY();
		// Log.d("getChunkNum", "2y "+y);
		y = Math.abs(y);
		output = (int) (x + (Columns * y));
		// Log.d("getChunkNum", "output "+output);
		return output;

	}

	public void setPos() {
		this.pos = new Vector3(x, y, Width, Height);
	}

	boolean once = false;

	public void setCamera(Camera2D camera) {
		this.camera = camera;
		for(int i = 0; i<chunks.size();i++){
			chunks.get(i).setCamera(camera);
		}
	}
	
	public void draw(Vector3 pos, float width, float height) {
		// Log.d("Chunk Num", "" + getChunkNum(pos.getX(), pos.getY()));
		// Log.d("Chunk Pos", "" + chunks.get(getChunkNum(pos.getX(),
		// pos.getY())).pos.getX() + ", "+ chunks.get(getChunkNum(pos.getX(),
		// pos.getY())).pos.getY());
		// if (pos.getX() % 1 == 0)
		// pos.addX(.1f);
		// if (pos.getY() % 1 == 0)
		// pos.addY(.1f);
		// int num = getChunkNum(pos.getX(), pos.getY());
		// Log.d("chunk#", "" + num + ", " + chunks.get(num).pos.getY() + ", "
		// + pos.getX());

		// getChunkNum(camera.topLeft().getX(), camera.topLeft().getY());

		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(getChunkNum(camera.topLeft().getX(), camera.topLeft().getY()));
		al.add(getChunkNum(camera.topRight().getX(), camera.topRight().getY()));
		al.add(getChunkNum(camera.botLeft().getX(), camera.botLeft().getY()));
		al.add(getChunkNum(camera.botRight().getX(), camera.botRight().getY()));
		al.add(getChunkNum(camera.center().getX(), camera.center().getY()));
		al.add(getChunkNum(camera.rightMid().getX(), camera.rightMid().getY()));
		al.add(getChunkNum(camera.leftMid().getX(), camera.leftMid().getY()));
		al.add(getChunkNum(camera.topMid().getX(), camera.topMid().getY()));
		al.add(getChunkNum(camera.botMid().getX(), camera.botMid().getY()));
		al.add(getChunkNum(camera.qtr1().getX(), camera.qtr1().getY()));
		al.add(getChunkNum(camera.qtr2().getX(), camera.qtr2().getY()));
		al.add(getChunkNum(camera.qtr3().getX(), camera.qtr3().getY()));
		al.add(getChunkNum(camera.qtr4().getX(), camera.qtr4().getY()));

		// al.add(getChunkNum(camera.botRight().getX(),
		// camera.botRight().getY()));
		// if(!once ){
		// for(int i = 0; i<al.size(); i++){
		// Log.d("once",""+al.get(i).toString());
		// }
		// }
		HashSet hs = new HashSet();
		hs.addAll(al);
		al.clear();
		al.addAll(hs);

		for (int i = 0; i < al.size(); i++) {
			// Log.d("num", "drawing" + al.get(i));
			chunks.get(al.get(i)).drawChunk(camera.pos.getX() - indent,
					camera.pos.getY() - indent, camera.pos.getWidth() + indent,
					camera.pos.getHeight() + indent, i);
		}

	}

}
