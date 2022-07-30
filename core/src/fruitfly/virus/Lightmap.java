package fruitfly.virus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector2;

import fruitfly.virus.entities.light.ILight;
import fruitfly.virus.tiles.Tile;
import fruitfly.virus.tiles.Wall;
import fruitfly.virus.tiles.WallTile;

public class Lightmap {
	private Pixmap pixmap;
	private Texture texture;
	private int samples;
	private float unitTileSize;
	
	private static int idGen = 0;
	
	public Lightmap(int width, int height, int samples) {
		pixmap = new Pixmap(width, width, Format.RGB888);
		this.samples = samples;
		
		unitTileSize = samples / (float)width;
		
		texture = new Texture(pixmap);
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	}
	
	public void bake() {
		pixmap.fill();
		for (int x=0; x<pixmap.getWidth(); x++) {
			for (int y=0; y<pixmap.getHeight(); y++) {
				//pixmap.drawPixel(x, y, 0xffffffff);
			}
		}
		
		Color color = new Color();
		Color lightColor = new Color();
		
		for (int x=0; x<G.world.getWidth(); x++) {
			for (int y=0; y<G.world.getHeight(); y++) {
				Tile t = G.world.getTile(x, y);
				
				if (t instanceof WallTile) {
					WallTile wt = (WallTile) t;
					Wall[] walls = new Wall[4];
					walls[0] = wt.east;
					walls[1] = wt.south;
					walls[2] = wt.west;
					walls[3] = wt.north;

					for (int i = 0; i<4; i++) {
						Wall w = walls[i];
						float xStep = (w.xEnd - w.xStart) / (float)samples;
						float yStep = (w.yEnd - w.yStart) / (float)samples;
						float zStep = w.height / (float)samples;
						
						for (int ii=0; ii<samples; ii++) {
							
							for (int sz=0; sz<samples; sz++) {
							
								float xx;
								float yy;
								float zz = zStep / 2.0f + zStep * sz;
								if (w == wt.east) {
									xx = w.xStart;
									yy = w.yStart + yStep/2.0f + ii*yStep;
								}
								else if (w == wt.west) {
									xx = w.xStart;
									yy = w.yEnd + yStep/2.0f + ii*yStep;
								}
								else if (w == wt.south) {
									xx = w.xStart + xStep/2.0f + ii*xStep;
									yy = w.yStart;
								}
								else /*if (w == wt.north)*/ {
									xx = w.xEnd + xStep/2.0f + ii*xStep;
									yy = w.yStart;
								}
	
								for (ILight light : G.world.entityManager.getLights()) {
								
									if (G.raycaster.isLineOfSight(G.world, new Vector2(light.getLightPosition().x, light.getLightPosition().y), new Vector2(xx, yy))) {
										int xlmt = (w.lightmapId * samples) % pixmap.getWidth();
										int ylmt = ((w.lightmapId * samples) / pixmap.getWidth()) * samples;
										
										int rgba8888 = pixmap.getPixel(xlmt + ii, ylmt + (samples - 1 - sz));
										Color.rgba8888ToColor(color, rgba8888);
										lightColor.set(0.2f, 0.2f, 0.2f, 1.0f);
										color.add(lightColor);
										
										pixmap.drawPixel(xlmt + ii, ylmt + (samples - 1 - sz), Color.rgba8888(color));
									}
								}
							}
						}
					}
				}
			}
		}
		for (int x=0; x<4; x++) {
			for (int y=0; y<4; y++) {
				pixmap.drawPixel(x, y, 0xffffffff);
			}
		}
		
		//PixmapIO.writePNG(Gdx.files.external("Desktop/lightmap.png"), pixmap);
		//System.out.println("*** Lightmap written ***");
		texture.draw(pixmap, 0, 0);
		//texture = new Texture(Gdx.files.external("Desktop/lightmap1.png"));
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void getTextureCoords(int id, float[] texCoordsArray) {
		if (texCoordsArray.length < 8) throw new RuntimeException("Array too small.");
		
		int i=0;
		int xb = id % (texture.getWidth() / samples);
		int yb = id / (texture.getWidth() / samples);

		final float dd = 0.0f;
		texCoordsArray[i++] = xb * unitTileSize + dd;
		texCoordsArray[i++] = (yb) * unitTileSize + dd;
		
		texCoordsArray[i++] = xb * unitTileSize + dd;
		texCoordsArray[i++] = (yb+1) * unitTileSize - dd;
		
		texCoordsArray[i++] = (xb+1) * unitTileSize - dd;
		texCoordsArray[i++] = (yb+1) * unitTileSize - dd;
		
		texCoordsArray[i++] = (xb+1) * unitTileSize - dd;
		texCoordsArray[i++] = (yb) * unitTileSize + dd;
	}
	
	public static int generateId() {
		return idGen++;
	}
}
