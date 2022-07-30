package fruitfly.virus;

import com.badlogic.gdx.graphics.Texture;

public class TextureMap {
	
	public class SubTexture {
		public int x, y;
		public int tileWidth, tileHeight;
		public int pixelWidth, pixelHeight;
		public TextureMap textureMap;
		
		public final float unitTileSize;
		public int repeat = 1;
		
		public SubTexture(int x, int y, int width, int height, int tileSize) {
			this.x = x;
			this.y = y;
			this.tileWidth = width;
			this.tileHeight = height;
			this.pixelWidth = width * tileSize;
			this.pixelHeight = height * tileSize;
			
			unitTileSize = tileSize / (float)texture.getWidth();
			
			this.textureMap = TextureMap.this;
		}
		
		public SubTexture(int x, int y, int width, int height) {
			this(x, y, width, height, defaultPixelTileSize);
		}
		
		public SubTexture(int x, int y, int width, int height, int tileSize, int repeat) {
			this(x, y, width, height, tileSize);
			this.repeat = repeat;
		}
		
		public int getWidth() {
			return pixelWidth;
		}
		
		public int getHeight() {
			return pixelHeight;
		}
		
		public void getTextureCoords(float[] texCoordsArray) {
			if (texCoordsArray.length < 8) throw new RuntimeException("Array too small.");
			
			int i=0;
			texCoordsArray[i++] = x * unitTileSize;
			texCoordsArray[i++] = (y) * unitTileSize;
			
			texCoordsArray[i++] = x * unitTileSize;
			texCoordsArray[i++] = (y+tileHeight) * unitTileSize;
			
			texCoordsArray[i++] = (x+tileWidth) * unitTileSize;
			texCoordsArray[i++] = (y+tileHeight) * unitTileSize;
			
			texCoordsArray[i++] = (x+tileWidth) * unitTileSize;
			texCoordsArray[i++] = (y) * unitTileSize;
		}
	}
	
	public final Texture texture;
	
	private int defaultPixelTileSize;
	
	public TextureMap(Texture tex, int defaultSize) {
		this.texture = tex;
		this.defaultPixelTileSize = defaultSize;
	}
}
