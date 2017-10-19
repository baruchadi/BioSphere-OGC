package smellychiz.projects.ogc.objects;

import java.util.Random;

import smellychiz.projects.ogc.objects.world.Biome;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public abstract class MovingMob extends Mob {

	Random r;

	int BlocksToMove = 0;

	int blocksMoved = 0;

	int maxBlockMovement = 10;

	public final int IDEL = 0, LEFT = 1, RIGHT = 2;

	public int direction;

	float OriginalX;

	int counter = 0;

	int countTo = 0;
	
	boolean stuck = false;

	public MovingMob(Context context, Vector3 v, Camera2D c) {
		super(context, v, c);

		r = new Random();
		decideDirection(r.nextInt(120));
		OriginalX = v.getX();
	}

	public void decideDirection(int i) {
		//Log.d("direction", "RESETTING");
		if (i <= 50) {
			direction = LEFT;
			BlocksToMove = r.nextInt(maxBlockMovement);
		} else if (i <= 100) {
			direction = RIGHT;
			BlocksToMove = r.nextInt(maxBlockMovement);
		} else {
			direction = IDEL;
			countTo = r.nextInt(100);
		}
	}

	public void move(Biome biome) {
		if (direction != IDEL) {
			if (blocksMoved >= BlocksToMove || stuck) {
				decideDirection(r.nextInt(120));
				OriginalX = this.bound.getX();
				blocksMoved = 0;
				BlocksToMove = 0;
				counter=0;
				countTo=0;
				stuck = false;
			} else if (blocksMoved < BlocksToMove) {
				switch (direction) {
				case LEFT:
					if (canMoveLeft(biome))
						dX = -getXSpeed();
					else{
						dX = 0;
						stuck = true;
					}
					break;
				case RIGHT:
					if (canMoveRight(biome))
						dX = getXSpeed();
					else{
						dX = 0;
						stuck = true;
						
					}
				}
			}
		} else {
			dX = 0;
			if (counter >= countTo) {
				counter = 0;
				countTo = 0;
				OriginalX = this.bound.getX();
				blocksMoved = 0;
				decideDirection(r.nextInt(120));
				stuck = false;
			}else
				counter++;
		}
		
		blocksMoved = (int) Math.abs(this.bound.getX()-OriginalX);
//		
//		if(BlocksToMove>1)
//		Log.d("blocksMoved", ""+blocksMoved+"/"+BlocksToMove);
//		
//		
//		if(countTo>0){
//			Log.d("counter", counter+"/"+countTo);
//		}
	}



	@Override
	public void beforUpdate(int time, Biome biome) {
		move(biome);
		midUpdate(time,biome);	
	}
	@Override
	public void afterUpdate(int time,Biome biome) {
	finish();	
	}
	
	public void finish() {
		this.bound.addX(dX);

		this.bound.addY(dY);

		this.initVertices();
	}

	/* abstract vars */

	public abstract float getXSpeed();

	/* abstract voids */

	public abstract void midUpdate(int time, Biome biome);

}
