package smellychiz.projects.ogc.util;

import smellychiz.projects.ogc.util.graphics.Animation;
import smellychiz.projects.ogc.util.graphics.Texture;
import smellychiz.projects.ogc.util.graphics.TextureArea;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Assets {

	public static Context mContext;

	public Assets(Context c) {
		mContext = c;

	}

	public void LoadAll() {
		loadTextures();

		initTextureAreas();
		loadAnimation();
	}

	// Textures

	// HUD
	public static Texture tToolBar, tDpad, key;

	// PLAYER RELATED
	public static Texture tPlayer;

	// NPC
	public static Texture tLurker, tSlime;

	// ETC
	public static Texture Grass, smiley;

	// Blocks
	public static Texture tBlocks;

	public void loadTextures() {
		loadHUDTextures();
		loadPlayerTextures();
		loadETCTextures();
		loadNPCTextures();
		loadBlocksTextures();
	}

	public void loadHUDTextures() {
		key = new Texture(mContext, "textures/pause.png");
		tToolBar = new Texture(mContext, "textures/toolbar.png", 64, 512);
		tDpad = new Texture(mContext, "textures/dpad.png", 128, 128);

	}

	public void loadPlayerTextures() {
		tPlayer = new Texture(mContext, "textures/player_sprite.png", 512, 512);
	}

	public void loadNPCTextures() {
		tLurker = new Texture(mContext, "textures/lurker_sprite.png", 512, 512);
		tSlime = new Texture(mContext, "textures/slime.png", 64, 128);
	}

	public void loadETCTextures() {
		Grass = new Texture(mContext, "textures/block.png");
		smiley = new Texture(mContext, "textures/ch0.png");
	}

	public void loadBlocksTextures() {
		tBlocks = new Texture(mContext, "textures/blocks.png", 512, 512);
	}

	// ================================================

	// Texture Areas

	// HUD

	public static TextureArea toolBar, t32, dpad, ball;

	// PLAYER RELATED
	public static TextureArea PlayerIdel, PlayerJump;

	// NPC
	public static TextureArea slimeIdel;

	// Blocks
	public static TextureArea dirt, grass, stone, gold;

	public void initTextureAreas() {
		loadHUDTextureArea();
		loadPlayerTextureAreas();
		loadNPCTextureAreas();
		loadBlocksTextureAreas();
	}

	public void loadHUDTextureArea() {
		toolBar = new TextureArea(tToolBar, new Vector3(0, 0, 320, 32));
		t32 = new TextureArea(tToolBar, new Vector3(0, 32, 32, 32));
		dpad = new TextureArea(tDpad, new Vector3(0, 0, 96, 96));
		ball = new TextureArea(tDpad, new Vector3(96, 0, 32, 32));

	}

	public void loadPlayerTextureAreas() {
		PlayerIdel = new TextureArea(tPlayer, new Vector3(0, 0, 36, 54));
		PlayerJump = new TextureArea(tPlayer, new Vector3(36, 0, 36, 54));
	}

	public void loadNPCTextureAreas() {
		slimeIdel = new TextureArea(tSlime, new Vector3(0, 0, 30, 32));
	}

	public void loadBlocksTextureAreas() {
		dirt = new TextureArea(tBlocks, new Vector3(0, 0, 18, 18));
		grass = new TextureArea(tBlocks, new Vector3(18, 0, 18, 18));
		stone = new TextureArea(tBlocks, new Vector3(36, 0, 18, 18));
		gold = new TextureArea(tBlocks, new Vector3(54, 0, 18, 18));

	}

	// =======================

	// Animation

	// NPC

	public static Animation Lurker_WR;

	public static Animation Slime_WR;

	// Player Related

	public static Animation Player_WR, Player_Whip_ATT;

	public void loadAnimation() {

		loadPlayerAnimation();

		loadNPCAnimation();

	}

	public void loadPlayerAnimation() {
		Player_WR = new Animation(4f, 

		new TextureArea(tPlayer, new Vector3(36, 0, 36, 54)),

		new TextureArea(tPlayer, new Vector3(72, 0, 36, 54)),

		new TextureArea(tPlayer, new Vector3(108, 0, 36, 54)),

		new TextureArea(tPlayer, new Vector3(0, 0, 36, 54))

		);

		Player_Whip_ATT = new Animation(3f,

		new TextureArea(tPlayer, new Vector3(0, 168, 42, 56)),

		new TextureArea(tPlayer, new Vector3(42, 168, 42, 56)),

		new TextureArea(tPlayer, new Vector3(84, 168, 42, 56))

		);
	}

	public void loadNPCAnimation() {

		Lurker_WR = new Animation(3f,

		new TextureArea(tLurker, new Vector3(42, 0, 42, 56)),

		new TextureArea(tLurker, new Vector3(84, 0, 42, 56)),

		new TextureArea(tLurker, new Vector3(126, 0, 42, 56))

		);

		Slime_WR = new Animation(3f,

		new TextureArea(tSlime, new Vector3(0, 32, 30, 32)),

		new TextureArea(tSlime, new Vector3(30, 32, 30, 32)),

		new TextureArea(tSlime, new Vector3(60, 32, 30, 32)),

		new TextureArea(tSlime, new Vector3(90, 32, 30, 32))

		);

	}

}
