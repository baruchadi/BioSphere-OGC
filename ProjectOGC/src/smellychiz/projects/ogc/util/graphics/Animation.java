package smellychiz.projects.ogc.util.graphics;

public class Animation {

	public static final int ANIMATION_LOOPING = 0;
	public static final int ANIMATION_NONLOOPING = 1;

	final TextureArea[] keyFrames;
	final float frameDuration;

	public Animation(float frameDuration, TextureArea... keyFrames) {
		this.frameDuration = frameDuration;
		this.keyFrames = keyFrames;
	}

	public TextureArea getKeyFrame(float stateTime, int mode) {
		int frameNumber = (int) (stateTime / frameDuration);

		if (mode == ANIMATION_NONLOOPING) {
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
		} else {
			frameNumber = frameNumber % keyFrames.length;

		}
		return keyFrames[frameNumber];
	}

}
