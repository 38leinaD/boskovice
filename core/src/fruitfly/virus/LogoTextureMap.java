package fruitfly.virus;

import com.badlogic.gdx.graphics.Texture;

public class LogoTextureMap extends TextureMap {

	public SubTexture logo = new SubTexture(0, 0, 1, 1);
	
	public LogoTextureMap(Texture tex) {
		super(tex, 64);
	}
}
