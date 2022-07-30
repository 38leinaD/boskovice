package fruitfly.virus;

import com.badlogic.gdx.graphics.Texture;



public class BaseTextureMap extends TextureMap {
	// Menus
	public SubTexture slimyDrop = new SubTexture(0, 0, 1, 1);
	public SubTexture slimyGreen = new SubTexture(1, 0, 1, 1);
	
	public SubTexture previousButton = new SubTexture(3, 0, 2, 2);
	public SubTexture nextButton = new SubTexture(5, 0, 2, 2);

	public SubTexture earth = new SubTexture(0, 3, 4, 4);
	public SubTexture logo = new SubTexture(0, 23, 6, 6);
	
	public SubTexture slime_logo = new SubTexture(6, 23, 2, 1);
	public SubTexture signTop_logo = new SubTexture(6, 25, 1, 1);
	public SubTexture signInner_logo = new SubTexture(6, 26, 1, 1);
	public SubTexture[] bosko_logo = new SubTexture[] {
		new SubTexture(10, 23, 4, 3),	
		new SubTexture(14, 23, 4, 3)
	};
	public SubTexture[] secureBot_logo = new SubTexture[] {
		new SubTexture(8, 27, 2, 2),
		new SubTexture(10, 27, 2, 2),
		new SubTexture(12, 27, 2, 2)
	};
	
	public SubTexture laser_logo = new SubTexture(15, 23, 2, 1);
	public SubTexture text_logo = new SubTexture(20, 23, 6, 1);

	
	public SubTexture[] chars = new SubTexture[] {
		new SubTexture(0, 4, 1, 1, 8),
		new SubTexture(1, 4, 1, 1, 8),
		new SubTexture(2, 4, 1, 1, 8),
		new SubTexture(3, 4, 1, 1, 8),
		new SubTexture(4, 4, 1, 1, 8),
		new SubTexture(5, 4, 1, 1, 8),
		new SubTexture(6, 4, 1, 1, 8),
		new SubTexture(7, 4, 1, 1, 8),
		new SubTexture(8, 4, 1, 1, 8),
		new SubTexture(9, 4, 1, 1, 8),
		new SubTexture(10, 4, 1, 1, 8),
		new SubTexture(11, 4, 1, 1, 8),
		new SubTexture(12, 4, 1, 1, 8),
		new SubTexture(13, 4, 1, 1, 8),
		new SubTexture(14, 4, 1, 1, 8),
		new SubTexture(15, 4, 1, 1, 8),
		new SubTexture(16, 4, 1, 1, 8),
		new SubTexture(17, 4, 1, 1, 8),
		new SubTexture(18, 4, 1, 1, 8),
		new SubTexture(19, 4, 1, 1, 8),
		new SubTexture(20, 4, 1, 1, 8),
		new SubTexture(21, 4, 1, 1, 8),
		new SubTexture(22, 4, 1, 1, 8),
		new SubTexture(23, 4, 1, 1, 8),
		new SubTexture(24, 4, 1, 1, 8),
		new SubTexture(25, 4, 1, 1, 8),
		
		new SubTexture(26, 4, 1, 1, 8),
		new SubTexture(27, 4, 1, 1, 8),
		new SubTexture(28, 4, 1, 1, 8),
		new SubTexture(29, 4, 1, 1, 8),
		new SubTexture(30, 4, 1, 1, 8),
		new SubTexture(31, 4, 1, 1, 8),
		new SubTexture(32, 4, 1, 1, 8),
		new SubTexture(33, 4, 1, 1, 8),
		new SubTexture(34, 4, 1, 1, 8),
		new SubTexture(35, 4, 1, 1, 8),
		new SubTexture(36, 4, 1, 1, 8),
		new SubTexture(37, 4, 1, 1, 8),
		new SubTexture(38, 4, 1, 1, 8),
		new SubTexture(39, 4, 1, 1, 8),
		new SubTexture(40, 4, 1, 1, 8),
		new SubTexture(41, 4, 1, 1, 8),
		new SubTexture(42, 4, 1, 1, 8),
		new SubTexture(43, 4, 1, 1, 8),
		new SubTexture(44, 4, 1, 1, 8),
		new SubTexture(45, 4, 1, 1, 8),
		new SubTexture(46, 4, 1, 1, 8),
		new SubTexture(47, 4, 1, 1, 8),
		new SubTexture(48, 4, 1, 1, 8),
		new SubTexture(49, 4, 1, 1, 8),
		new SubTexture(50, 4, 1, 1, 8),
		new SubTexture(51, 4, 1, 1, 8),
	};
	
	public BaseTextureMap(Texture tex) {
		super(tex, 16);
	}
}
