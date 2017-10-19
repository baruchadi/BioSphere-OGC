package smellychiz.projects.ogc.objects.mobs.passive;

import smellychiz.projects.ogc.objects.MovingMob;
import smellychiz.projects.ogc.objects.world.Biome;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Animation;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Slime extends MovingMob {
	static float WIDTH = 2f;
	static float HEIGHT = 2f;

	public Slime(Context context, float x, float y, Camera2D c) {
		super(context, new Vector3(x, y, WIDTH, HEIGHT), c);
		this.setTexture(Assets.tSlime);
		this.setTextureArea(Assets.slimeIdel);
		initVertices();
	}

	int d1;
	int stateTime = 0;

	@Override
	public void midUpdate(int time, Biome biome) {
		if (this.direction == LEFT)
			this.bound.setFlipped(true);

		else
			this.bound.setFlipped(false);

		if (direction != d1) {
			stateTime = 0;
		}
		if (direction == IDEL)
			this.setTextureArea(Assets.slimeIdel);
		else if (direction == LEFT || direction == RIGHT)
			this.setTextureArea(Assets.Slime_WR.getKeyFrame(stateTime,
					Animation.ANIMATION_LOOPING));

		stateTime += time;
		d1 = direction;
	}

	@Override
	public float getXSpeed() {

		return .1f;

	}

	@Override
	public boolean isGravity() {

		return true;
	}

}
