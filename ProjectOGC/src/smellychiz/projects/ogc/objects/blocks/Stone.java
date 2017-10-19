package smellychiz.projects.ogc.objects.blocks;

import smellychiz.projects.ogc.objects.Block;
import smellychiz.projects.ogc.objects.world.Chunk;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.TextureArea;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Stone extends Block {

	public Stone(Context context, Vector3 v, Camera2D c, Chunk ch) {
		super(context, v, c, ch);

	}

	@Override
	public TextureArea getTextureArea() {

		return Assets.stone;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 5;
	}

}
