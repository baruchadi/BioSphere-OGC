package smellychiz.projects.ogc.objects;

import smellychiz.projects.ogc.objects.world.Biome;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Animation;
import smellychiz.projects.ogc.util.graphics.Texture;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public abstract class Mob extends GameObject {
	public float stateTime;
	Texture tex;

	Animation a;

	public boolean onGround = false;

	public int actX, actY;

	public float dX, dY;

	public double energyloss = 1, gravity = -.13, gameDy = .96, dt = .3;

	public Mob(Context context, Vector3 v, Camera2D c) {
		super(context, v, c);
		// TODO Auto-generated constructor stub
	}

	public boolean canMoveLeft(Biome biome) {
		float x1 = this.bound.getX() + bound.getWidth();
		//System.out.println(bound.getWidth());

		float[] y = new float[(int) this.bound.getHeight()];

		for (int h = 0; h < this.bound.getHeight(); h++) {
			y[h] = this.bound.getY() + h;
			if (y[h] % 1 == 0)
				y[h] += .1f;
		}

		for (int i = 0; i < y.length; i++) {
			if (!biome.getBlock(x1, y[i]).isAir()) {
				return false;
			}

		}

		return true;

	}

	public boolean canMoveRight(Biome biome) {
		float x1 = this.bound.getX() + bound.getWidth();

		float[] y = new float[(int) this.bound.getHeight()];

		for (int h = 0; h < this.bound.getHeight(); h++) {
			y[h] = this.bound.getY() + h;
			if (y[h] % 1 == 0)
				y[h] += .1f;
		}

		for (int i = 0; i < y.length; i++) {
			if (!biome.getBlock(x1, y[i]).isAir()) {
				return false;
			}

		}

		return true;
	}

	public void jump() {
		if (onGround) {
			this.bound.setY(this.bound.getY() + .2f);
			this.dY = (float) gameDy;
			onGround = false;
		}
	}

	public void testOnGround(Biome biome, int width) {
		float x = bound.getRealX(), y = bound.getY();
		int mult;
		if (width < 0) {
			mult = -1;
		} else {
			mult = 1;
		}

		int n = 0;
		if (width == 1 || width == -1) {
			if (!biome.getBlock(x, y - .1f).isAir()
					|| !biome.getBlock(x + width, y - .1f).isAir()
					|| !biome.getBlock(x + (width / 2), y - .1f).isAir()) {
				n++;
			}
		} else {

			for (int i = 0; i < Math.abs(width); i++) {

				if (!biome.getBlock(x + (i * mult), y - .1f).isAir()) {
					n++;
				}
			}
		}

		if (n > 0) {
			if (!onGround) {
				onGround = true;
				dY = 0;
				this.bound
						.setY((float) Math.floor((double) this.bound.getY() + 1) + .01f);
			}

		} else {
			onGround = false;
			dY += gravity;

		}

	}

	public void update(int time, Biome biome) {
		beforUpdate(time, biome);
		if (isGravity())
			testOnGround(biome, (int) this.bound.getWidth());
		afterUpdate(time, biome);

	}

	public abstract void beforUpdate(int time, Biome biome);

	public abstract void afterUpdate(int time, Biome biome);

	public abstract boolean isGravity();

	// soon to be - MOB CLASS!
}
