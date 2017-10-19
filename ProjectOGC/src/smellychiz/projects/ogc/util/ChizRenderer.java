package smellychiz.projects.ogc.util;

import javax.microedition.khronos.opengles.GL10;

import smellychiz.projects.ogc.objects.Dpad;
import smellychiz.projects.ogc.objects.GameObject;
import smellychiz.projects.ogc.objects.blocks.Air;
import smellychiz.projects.ogc.objects.mobs.Player;
import smellychiz.projects.ogc.objects.world.Biome;
import smellychiz.projects.ogc.objects.world.Chunk;
import smellychiz.projects.ogc.util.graphics.Texture;
import smellychiz.projects.ogc.util.helpers.Finger;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;
import android.opengl.EGLConfig;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.widget.Toast;

public class ChizRenderer implements Renderer {

	public boolean ready = false;

	public Camera2D camera, KeyCam, splashCam;
	GameObject pause, toolBar, t32;
	Dpad pad;
	Context mContext;
	public Player player;

	public Assets assets;

	public static int deltaTime;

	//Slime slime;

	Biome grassBiome;

	boolean load;

	public ChizRenderer(Context c, boolean load) {
		mContext = c;
		assets = new Assets(mContext);
		this.load = load;
	}
	public ChizRenderer(Context c, boolean load,Biome b) {
		mContext = c;
		assets = new Assets(mContext);
		this.load = load;
		grassBiome = b;
		for(int i = 0; i <Chunk.COLUMNS*Chunk.ROWS; i++){
			grassBiome.getBlock(0, i).putOrder();
			grassBiome.getBlock(1, i).putOrder();
			grassBiome.getBlock(2, i).putOrder();
			grassBiome.getBlock(3, i).putOrder();
		}
		
		//Log.d("biometest", ""+grassBiome.getBlock(10, 10));
	}
	int countt = 0;

	public void onDrawFrame(GL10 gl) {

		if (isReady())
			this.Update(deltaTime);
		GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

		GLES20.glEnable(GLES20.GL_TEXTURE_2D);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		if (this.isReady()) {

			camera.centerCam(player.bound);
			drawObjects();

			KeyCam.init();

			drawHUD();
		} else {
			camera.init(camera.pos);
			splash.draw();
			if (countt == 1) {
				assets.LoadAll();

				if (grassBiome == null)
					grassBiome = new Biome(Biome.SMALL, 0, mContext,
							this.camera);

				initObjects();
				IO.save(grassBiome);
			} else
				countt++;
		}

		GLES20.glDisable(GLES20.GL_BLEND);

	}

	boolean ttw = false;

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		float ratio = (float) width / height;
		camera.cOrtho(0, -ratio, ratio, -ratio, ratio, -5, 5);
	}

	boolean xld = false;

	public void drawHUD() {
		// pause.Draw();
		toolBar.draw();
		pad.Draw();
		// t32.Draw();
		// if (!xld) {
		// t32.showVertices();
		// xld = true;
		// }
	}

	GameObject splash;
	public IO io;

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		io = new IO("save1.txt", mContext);

		splashCam = new Camera2D(0, 0, 10, 20);
		splashCam.init(splashCam.pos);
		splash = new GameObject(mContext, new Vector3(0, 0, 20, 10), splashCam,
				new Texture(mContext, "logo/logo.png"));
		splash.draw();
		camera = new Camera2D(0, 0, 10, 20);
		camera.init(camera.pos);
		KeyCam = new Camera2D(0, 0, 10, 20);
		KeyCam.init(KeyCam.pos);

	}

	public boolean isReady() {
		return ready;
	}

	public void initObjects() {
		grassBiome.setCamera(camera);
	//	slime = new Slime(mContext, 50, 4, this.camera);
		player = new Player(mContext, new Vector3(grassBiome.pos.getX()
				+ (grassBiome.pos.getWidth() / 2), grassBiome.pos.getY()
				+ (grassBiome.pos.getHeight() / 4), 2, 3), this.camera);
		// grassBiome.createFirstChunks(player.bound);
		pause = new GameObject(mContext, new Vector3(2, 2, 0, 2, 2),
				this.KeyCam, Assets.key);

		toolBar = new GameObject(mContext, new Vector3(
				(camera.pos.getWidth() - 15) / 2, 8.5f, 15, 1.5f), this.KeyCam,
				Assets.toolBar);
		t32 = new GameObject(mContext, new Vector3(0, 0, 4.5f, 4.5f),
				this.KeyCam, Assets.dpad);
		pad = new Dpad(mContext, Assets.dpad, Assets.ball, this.KeyCam);
		ready = true;

	}

	public void drawObjects() {
		// if(blocks.get(i).bound.getX()<camera.pos.getX()+camera.pos.getWidth()+5
		// && blocks.get(i).bound.getX() > camera.pos.getX() - 5 &&
		// blocks.get(i).bound.getY()<camera.pos.getY()+camera.pos.getHeight()+5
		// && blocks.get(i).bound.getY()>camera.pos.getY()-5)

		// grassBiome.draw(player.bound);
		grassBiome.draw(camera.getPos(), camera.getPos().getWidth(), camera
				.getPos().getHeight());
		player.draw();
	//	slime.Draw();
		// Log.d("pos",
		// ""+slimes.get(0).bound.getX()+", "+slimes.get(0).bound.getY()+", "
		// +player.bound.getX()+", "+player.bound.getY());
		// Log.d("tArea:", "TArea:");
		// slime.getTArea().EchoUv();
		// Log.d("x", "======================");
		// slime.EchoUv();
	}

	public void touchUp(float x, float y) {
		DownCounter = 0;

		if (!grassBiome.getChunk(x, y).getBlock(x, y).isAir()) {
			grassBiome.getChunk(x, y).blocks.set(grassBiome.getChunk(x, y)
					.getBlockNum(x, y), new Air(0));
		}
		// if (Chunk.insideChunk(chunks.get(0), x, y)) {
		// // System.out.println("inside Chunk!");
		// // System.out.println(chunks.get(0).getBlockNum(x, y));
		// Log.d("TOUCH", "Y2 Succed");
		// chunks.get(0).blocks.set(chunks.get(0).getBlockNum(x, y), null);
		//
		// } else if (Chunk.insideChunk(chunks.get(1), x, y)) {
		//
		// // System.out.println("inside Chunk!");
		// // System.out.println(chunks.get(0).getBlockNum(x, y));
		// chunks.get(1).blocks.set(chunks.get(1).getBlockNum(x, y), null);
		// } else {
		// System.out.println("outside Chunk!");

		// }

		// player.setActionY(Player.IDEL);
		// player.setActionX(Player.IDEL);
	}

	int DownCounter = 0;
	boolean once = true;

	public void Update(int deltaTime) {
		if (isReady()) {
			if (pad.touched) {
				if (pad.getButton() != 10) {
					// System.out.println("Button " + pad.getButton() +
					// " was pressed");
					switch (pad.getButton()) {
					case Dpad.BOTTOM_ARROW:

						break;
					case Dpad.LEFT_ARROW:
						player.setActionX(Player.MOVE_LEFT);
						break;
					case Dpad.RIGHT_ARROW:
						player.setActionX(Player.MOVE_RIGHT);
						break;
					case Dpad.TOP_LEFT_ARROW:
						player.setActionX(Player.MOVE_LEFT);
						player.jump();
						// jump action
						break;
					case Dpad.TOP_RIGHT_ARROW:
						player.setActionX(Player.MOVE_RIGHT);
						player.jump();
						// jump action
						break;
					case Dpad.TOP_ARROW:
						player.jump();
						break;
					case Dpad.KEY_UP:
					default:
						player.setActionX(Player.IDEL);
						player.setActionY(Player.IDEL);

						break;

					}
				}
			} else {

				player.setActionX(Player.IDEL);
				player.setActionY(Player.IDEL);
			}

			player.update(deltaTime, grassBiome);
			//slime.update(deltaTime, grassBiome);
		}
	}

	public void touchDpad(Finger finger) {

		pad.setTouch(finger.getX(), finger.getY());
		pad.setTouched(true);

		// if (finger.getX() > player.bound.getX() + player.bound.getWidth())
		// player.setActionX(Player.MOVE_RIGHT);
		// else if (finger.getX() < player.bound.getX())
		// player.setActionX(Player.MOVE_LEFT);
		// else
		// player.setActionX(Player.IDEL);
		//
		// if (finger.getY() > player.bound.getY() + player.bound.getHeight())
		// player.setActionY(Player.MOVE_UP);
		// else if (finger.getY() < player.bound.getY())
		// player.setActionY(Player.MOVE_DOWN);
		// else
		// player.setActionY(Player.IDEL);

		// System.out.println("actX: " + player.actX + ", actY:" + player.actY);
	}

	public void DpadRelease() {
		pad.setTouched(false);
	}

	public static void setDeltaTime(int deltaTime) {
		ChizRenderer.deltaTime = deltaTime;

	}

	public void exit() {
		IO.save(grassBiome);
		Toast.makeText(mContext, "saving...", Toast.LENGTH_SHORT).show();
	}
	public void onSurfaceCreated(GL10 arg0,
			javax.microedition.khronos.egl.EGLConfig arg1) {
		// TODO Auto-generated method stub
		
	}

}
