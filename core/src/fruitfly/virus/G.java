package fruitfly.virus;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.render.Renderer2D;
import fruitfly.virus.render.WorldRenderer;
import fruitfly.virus.timer.Timer;

public class G {
	
	public static int screenWidth, screenHeight;
	public static int windowWidth, windowHeight;
	public static float aspect; // width/height

	public static Renderer2D renderer2d;
	public static OrthographicCamera orthoCam;
	public static BitmapFont font;
	public static BitmapFont fontSmall;
	public static BitmapFont fontBig;

	public static SpriteBatch batch;
	public static Sprite logo;
	
	public static BaseTextureMap baseTextureMap;
	public static GameTextureMap textureMap;
	public static Player player;
	public static World world;
	public static Camera camera;
	public static Raycaster raycaster;
	public static Audio audio;
	public static Timer timer;
	public static Console console;
	public static WorldRenderer renderer3d;
	public static Gameloop game;
	public static Virus container;

	public static int statVisisbleTiles = 0;
	public static int statTransparentTiles = 0;
	public static int statVisisbleWalls = 0;
	public static int statVisisbleDecals = 0;
	public static int statVisibleLights = 0;
	public static int statVisibleEntities = 0;

	
	public static boolean debugGhost = false;
	
	public static boolean noClip = true;
	public static Object dRef = null;
	public static Vector3 dRefPos1 = null;
	public static Vector3 dRefPos2 = null;

}
