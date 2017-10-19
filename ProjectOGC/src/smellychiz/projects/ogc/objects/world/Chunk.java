package smellychiz.projects.ogc.objects.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import smellychiz.projects.ogc.objects.Block;
import smellychiz.projects.ogc.objects.blocks.Air;
import smellychiz.projects.ogc.objects.blocks.Dirt;
import smellychiz.projects.ogc.objects.blocks.Grass;
import smellychiz.projects.ogc.objects.blocks.Stone;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Chunk implements java.io.Serializable {
	public static final int ROWS = 10;
	public static final int COLUMNS = 25;
	public List<Block> blocks = new ArrayList<Block>();
	private final int BLOCKS_TYPES = 4;
	public static int SerialNum = 0;
	private int ChunkID;
	public Vector3 pos;

	public static final int AIR = 0, STONE = 1, GRASS = 2, DIRT = 3;
	private Random r;
	transient Camera2D camera;
	Context mContext;

	public Chunk(Camera2D cam, Context c, int x, int y, int id) {
		this.pos = new Vector3(x, y, COLUMNS, ROWS);

		ChunkID = id;
		r = new Random();
		this.camera = cam;
		this.mContext = c;
		generateChunk();

	}

	public Chunk(Camera2D cam, Context c, int x, int y, int Type, int id) {
		this.pos = new Vector3(x, y, COLUMNS, ROWS);

		ChunkID = id;
		r = new Random();
		this.camera = cam;
		this.mContext = c;
		generateChunk(Type);

	}

	public int getChunkID() {
		return ChunkID;
	}

	private void generateChunk(int type) {
		for (int l = 0; l < ROWS; l++) {
			for (int i = 0; i < COLUMNS; i++) {

				blocks.add(i + (l * 50), getBlock(type, i, l));

			}
		}

	}

	public void setCamera(Camera2D camera) {
		this.camera = camera;
	}

	boolean once = false;

	public void drawChunk() {
		for (int i = 0; i < blocks.size(); i++) {
			if (!blocks.get(i).isAir()) {
				blocks.get(i).draw(camera);

			}
		}
	}

	boolean t = false;

	public void drawChunk(float x, float y, float width, float height, int m) {

		for (int i = 0; i < ROWS; i++) {
			// Log.d("test", "" + ((pos.getY() + i) - y));

			if (y + height - (pos.getY() + i) < height
					&& y + height - (pos.getY() + i) > 0) {

				for (int c = 0; c < COLUMNS; c++) {
					if (x + width - (pos.getX() + c) < width
							&& x + width - (pos.getX() + c) > 0) {
						int num = c + (i * COLUMNS);
						if (!blocks.get(num).isAir()) {
							blocks.get(num).draw(camera);
							// Log.d("block", "" + (i * ROWS + c));
						}
					}
				}
			}

		}

	}

	public void generateChunk() {
		for (int l = 0; l < ROWS; l++) {
			for (int i = 0; i < COLUMNS; i++) {
				int n = r.nextInt(BLOCKS_TYPES);
				blocks.add(i + (l * COLUMNS), getBlock(n, i, l));

			}
		}

	}

	private Block getBlock(int n, int x1, int y1) {
		Block o = null;
		Vector3 bPos = new Vector3(this.pos.getX() + x1, this.pos.getY() + y1,
				1, 1);
		switch (n) {
		default:
		case AIR:
			o = new Air(0);
			break;
		case DIRT:
			o = new Dirt(mContext, bPos, camera, this);
			break;
		case GRASS:
			o = new Grass(mContext, bPos, camera, this);
			break;
		case STONE:
			o = new Stone(mContext, bPos, camera, this);
		}
		return o;
	}

	public int getBlockNum(float x, float y) {

		int output = 1;
		x = (float) Math.floor(x);

		y = (float) Math.floor(y);

		x -= this.pos.getX();

		y -= this.pos.getY();

		y = Math.abs(y);

		x = Math.abs(x);
		output = (int) (x + (this.pos.getWidth() * y));
		// Log.d("output", "" + output);

		return output;
	}

	public void addBlock(float tX, float tY) {

	}

	public Block getBlock(float x, float y) {

		return this.blocks.get(getBlockNum(x, y));

	}

	public static Boolean insideChunk(Chunk c, float x, float y) {

		if (c.pos.getX() < x && c.pos.getX() + c.pos.getWidth() > x
				&& c.pos.getY() < y && c.pos.getY() + c.pos.getHeight() > y)

			return true;
		else
			return false;

	}
}
