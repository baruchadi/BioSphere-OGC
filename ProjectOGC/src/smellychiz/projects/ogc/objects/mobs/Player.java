package smellychiz.projects.ogc.objects.mobs;

import smellychiz.projects.ogc.objects.Mob;
import smellychiz.projects.ogc.objects.world.Biome;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Animation;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Player extends Mob {

	public final static int IDEL = 0;
	public final static int MOVE_RIGHT = 1;
	public final static int MOVE_LEFT = 2;
	public final static int MOVE_UP = 1;
	public final static int MOVE_DOWN = 2;

	public float xSpeed = .2f;
	public float ySpeed = .3f;

	public Player(Context context, Vector3 v, Camera2D c) {
		super(context, v, c);

		this.setTexture(Assets.tPlayer);
		this.setTextureArea(Assets.PlayerIdel);
		actX = IDEL;
		actY = IDEL;
		stateTime = 0;
	}

	public void setActionX(int action) {
		if (this.actX != action) {
			setAnimationProp(action);
			this.actX = action;
		}
	}

	public void setActionY(int action) {
		this.actY = action;
	}

	public void setAnimationProp(int action) {
		if (onGround) {
			if (action == MOVE_RIGHT) {
				this.setTextureArea(Assets.Player_WR.getKeyFrame(stateTime,
						Animation.ANIMATION_LOOPING));

				this.bound.setFlipped(false);

				stateTime = 0;
			} else if (action == MOVE_LEFT) {
				this.setTextureArea(Assets.Player_WR.getKeyFrame(stateTime,
						Animation.ANIMATION_LOOPING));

				this.bound.setFlipped(true);

				stateTime = 0;

			} else if (action == IDEL) {
				this.setTextureArea(Assets.PlayerIdel);
				stateTime = 0;
			}

		} else if (!onGround) {
			this.setTextureArea(Assets.PlayerJump);
			if (action == MOVE_LEFT) {
				this.bound.setFlipped(true);
				stateTime = 0;
			} else if (action == MOVE_RIGHT) {
				this.bound.setFlipped(false);
				stateTime = 0;
			}
		}
	}

	@Override
	public void beforUpdate(int time, Biome biome) {
		switch (actX) {
		case IDEL:
			dX = 0;
			break;
		case MOVE_RIGHT:
			if (canMoveRight(biome))
				dX = xSpeed;
			else
				dX = 0;
			break;
		case MOVE_LEFT:
			if (canMoveLeft(biome))
				dX = -xSpeed;
			else
				dX = 0;
			break;
		}

	}

	@Override
	public void afterUpdate(int time, Biome biome) {
		if (onGround) {
			if (this.actX == MOVE_RIGHT) {
				this.setTextureArea(Assets.Player_WR.getKeyFrame(stateTime,
						Animation.ANIMATION_LOOPING));

			} else if (this.actX == MOVE_LEFT) {
				this.setTextureArea(Assets.Player_WR.getKeyFrame(stateTime,
						Animation.ANIMATION_LOOPING));

			}
		}

		this.bound.addX(dX);

		this.bound.addY(dY);
		this.initVertices();
		this.stateTime += time;
	}

	@Override
	public boolean isGravity() {
		// TODO Auto-generated method stub
		return true;
	}

}