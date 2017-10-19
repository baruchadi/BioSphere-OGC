package smellychiz.projects.ogc.objects;

import smellychiz.projects.ogc.objects.world.Chunk;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.TextureArea;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public abstract class Block extends GameObject {

	public Block(Context context, Vector3 v, Camera2D c, Chunk ch) {
		super(context, v, c);
		this.setTexture(Assets.tBlocks);
		this.setTextureArea(getTextureArea());
		this.initVertices();
	}

	int id;

	public abstract int getId();

	public boolean isAir() {
		if (getId() == 0)
			return true;
		else
			return false;

	}

	public void setId(int id) {
		this.id = id;
	}
	public Block(int id) {
		super(id);
		this.setId(id);
	}

	public abstract TextureArea getTextureArea();

	public void draw(Camera2D camera) {
		if (this.camera == null) {
			this.camera = camera;
		}
		this.draw();
	}

}
