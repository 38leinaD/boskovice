package fruitfly.virus;

import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.Episode.Level;
import fruitfly.virus.entities.light.ILight;
import fruitfly.virus.entities.light.StaticLight;
import fruitfly.virus.render.WorldRenderer;
import fruitfly.virus.tiles.Tile;
import fruitfly.virus.tiles.Wall;
import fruitfly.virus.timer.ITimeoutListener;
import fruitfly.virus.timer.Timer;
import fruitfly.virus.weapons.LaserPistol;

public class Gameloop implements Screen, ITimeoutListener {

	private FrameBuffer frameBuffer;
	private Hud hud;
	
	public long ticker;
	
	private Color postEffectColor = null;
	private Episode episode = null;
	private int levelIndex = 0;
	private Level level;
	
	public Gameloop() {
		frameBuffer = new FrameBuffer(Format.RGBA8888, G.screenWidth, G.screenHeight, true);

		G.game = this;
		G.audio = new Audio();

		Texture texture = new Texture(Gdx.files.internal("data/textures.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		G.textureMap = new GameTextureMap(texture);
		
		G.timer = new Timer();
				
		G.player = new Player(0.0f, -2.0f, 90.0f);

		G.camera = new Camera();		

		// we need the fovx here...
		// TODO: quickfix here...
		G.raycaster = new Raycaster(C.fov * G.aspect, G.screenWidth);
		G.renderer3d = new WorldRenderer();

//		G.console.output("Boscovice [alpha release]", 600);
//		G.console.output("Developed for the Ludum Dare October Challenge 2012", 600);
//		G.console.output("(c) fruitfly 2012", 600);
//
//		G.console.output(" ", 600);
//
//		G.console.output("Controls:", 600);
//		G.console.output("Use the left half of the screen as a virtual joystick.", 600);
//		G.console.output("Tap the right half of the screen to fire.", 600);
//		G.console.output("Tap the center of the screen to interact.", 600);
//		G.console.output(" i.e. flip switches, open doors, etc.", 600);

		hud = new Hud();
	}
	
	public void startEpisode(Episode e) {
		this.episode = e;
		this.levelIndex = 0;
		this.level = episode.getLevels().get(levelIndex);
		ticker = 0;
		G.world = new World(level);
		G.world.init();
		
		//loadState();
	}
	
	public void startNextLevel() {
		levelIndex++;
		this.level = episode.getLevels().get(levelIndex);
		ticker = 0;
		G.world = new World(level);
		G.world.init();
	}
	
	@Override
	public void show() {

	}
	
	Random r = new Random(System.currentTimeMillis());
	
	private int joystickIndex = -1;
	private float joystickCenterX, joystickCenterY;
	private float joystickX, joystickY;
	
	private float tickAccumulator = 0.0f;
	
	@Override
	public void render(float delta) {

		tickAccumulator += delta;
		while (tickAccumulator >= C.secondsPerTick) {
			this.tick(ticker);
			tickAccumulator-=C.secondsPerTick;
			ticker++;
		}
		
		frameBuffer.begin();
		renderLowRes(delta);
		frameBuffer.end();
		
		Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);

		Texture frameBufferTexture = frameBuffer.getColorBufferTexture();
		frameBufferTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		Sprite s = new Sprite(frameBufferTexture);
		s.setSize(G.windowWidth, G.windowHeight);
		s.setOrigin(0, 0);
		s.setPosition(0, 0);
		
		G.batch.setProjectionMatrix(G.orthoCam.combined);
		G.batch.begin();
		s.draw(G.batch);
		G.batch.end();
		
		renderDebug();
		
		hud.render();
		
		G.batch.setProjectionMatrix(G.orthoCam.combined);
		G.batch.begin();
		G.console.render(G.batch);
		G.batch.end();
		
		renderLevelName();
	}
	
	int _levelNameLength = 0;
	Color levelNameColor = Color.WHITE.cpy();
	int levelNameDisplayDelay = 120;
	boolean levelNameFaded = false;
	
	private void renderLevelName() {
		if (levelNameFaded) return;
		if (_levelNameLength == level.getName().length()) {
			if (levelNameDisplayDelay <= 0) {
				levelNameColor.a -= 0.01f;
				if (levelNameColor.a <= 0) {
					levelNameColor.a = 0.0f;
					levelNameFaded = true;
				}
			}
			levelNameDisplayDelay--;
		}
		
		G.batch.setProjectionMatrix(G.orthoCam.combined);
		G.batch.begin();
		
		int newLevelNameLength = (int) Math.min(ticker / 5, level.getName().length());
		if (newLevelNameLength != _levelNameLength) {
			_levelNameLength = newLevelNameLength;
			G.audio.consoleChar.play(0.5f);
		}
		

		String str = level.getName().substring(0, _levelNameLength);
		GlyphLayout gl = new GlyphLayout(G.fontBig, str);

		G.fontBig.setColor(levelNameColor);
		G.fontBig.draw(G.batch, str, G.windowWidth/2 - gl.width/2 , G.windowHeight/2 - gl.height/2);
		G.batch.end();
		
	}

	Pixmap pixmap = null;
	
	private void renderDebug() {
		
		G.batch.setProjectionMatrix(G.orthoCam.combined);
		G.batch.begin();
		G.fontSmall.draw(G.batch, "vis_tiles: " + G.statVisisbleTiles, G.windowWidth - 230, 70);
		G.fontSmall.draw(G.batch, "vis_trans_tiles: " + G.statTransparentTiles, G.windowWidth - 230, 90);
		G.fontSmall.draw(G.batch, "vis_walls: " + G.statVisisbleWalls, G.windowWidth - 230, 110);
		G.fontSmall.draw(G.batch, "vis_decals: " + G.statVisisbleDecals, G.windowWidth - 230, 130);
		G.fontSmall.draw(G.batch, "vis_entities: " + G.statVisibleEntities, G.windowWidth - 230, 150);
		G.fontSmall.draw(G.batch, "vis_lights: " + G.statVisibleLights, G.windowWidth - 230, 170);
		G.batch.end();
		
		if (pixmap == null) pixmap = new Pixmap( G.world.getWidth(), G.world.getHeight(), Format.RGBA8888 );
		pixmap.setColor(new Color(1.0f, 1.0f, 1.0f, 0.5f));
		pixmap.fill();
		for (int x=0; x<G.world.getWidth(); x++) {
			for (int y=0; y<G.world.getHeight(); y++) {
				Tile t = G.world.getTile(x, y);
				if (t != null && t.isVisible()) {
					if (t.isBlockingVisibility()) pixmap.drawPixel(x, y, 0xff000099);
					if (!t.isBlockingVisibility()) pixmap.drawPixel(x, y, 0x00ff0099);
				}
			}
		}
		
		Texture tex = new Texture(pixmap);
		
		Sprite s = new Sprite(tex);
		float aspect = G.world.getHeight() / (float)G.world.getWidth();
		s.setSize(G.world.getWidth() * 5, G.world.getHeight() * 5);
		s.setOrigin(0, 0);
		s.setPosition(0, 0);

		G.batch.begin();
		s.draw(G.batch);
		G.batch.end();
		
		tex.dispose();
	}
	
	private void renderLowRes(float delta) {
		renderSky();
		
		G.renderer3d.render();
		
		if (postEffectColor != null) {
			G.renderer2d.begin();
			G.renderer2d.setLightColor(postEffectColor);
			G.renderer2d.render(G.textureMap.particle2, 0, 0, G.screenWidth, G.screenHeight);
			G.renderer2d.end();
		}
		
		if (joystickIndex != -1) {
			G.renderer2d.begin();
			G.renderer2d.render(G.textureMap.joystickButton, joystickCenterX, joystickCenterY, 1);
			G.renderer2d.render(G.textureMap.joystickButton, joystickX, joystickY, 1);
			G.renderer2d.end();
		}
	}

	private void renderSky() {
		Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);
		
		float fov = C.fov * G.aspect;
		Texture skyTexture = G.world.getSkyTexture();
		
		float texWindow = fov/360.0f;
		float rot = G.player.rotation/360.0f;
		G.renderer2d.begin();
		G.renderer2d.render(skyTexture, 0.0f, 0.0f, G.screenWidth, G.screenHeight/2.0f, -rot, 0.0f, texWindow, 1.0f);
		G.renderer2d.render(skyTexture, 0.0f, G.screenHeight/2.0f, G.screenWidth, G.screenHeight, -rot, 0.9f, texWindow, 0.001f);
		G.renderer2d.end();
	}

	public void tick(long tick) {
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			G.player.move(Movement.FORWARD, 1.0f);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			G.player.move(Movement.BACKWARD, 1.0f);
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			G.player.move(Movement.TURN_LEFT, 1.0f);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			G.player.move(Movement.TURN_RIGHT, 1.0f);
		}
		
		if (Gdx.input.isKeyPressed(Keys.R)) {
			G.renderer3d.compileShaders(false);
		}
		
		if (Gdx.input.isKeyPressed(Keys.D)) {
			G.dRefPos1 = new Vector3(G.player.position);
			G.dRefPos1.z = 0.5f;
			System.out.println("*** RefPos1 set to " + G.dRefPos1);
		}
		
		if (Gdx.input.isKeyPressed(Keys.F)) {
			if (G.raycaster.selectedWall != null) {
				Wall w = G.raycaster.selectedWall;
				
				G.dRefPos2 = new Vector3(w.xStart + (w.xEnd - w.xStart) / 2.0f, w.yStart + (w.yEnd - w.yStart) / 2.0f, w.height/2.0f);
				System.out.println("*** RefPos2 set to " + G.dRefPos2);
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.L)) {
			Vector3 lpos = new Vector3(G.player.position);
			lpos.z = 0.5f;
			ILight l = new StaticLight(lpos, Color.WHITE, 0.5f);
			G.world.entityManager.addEntity(l);
			System.out.println("*** NEW LIGHT");
		}
		
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || (Gdx.app.getType() != ApplicationType.Android && Gdx.input.isTouched())) {
			if (G.player.activeWeapon != null && G.player.activeWeapon.canFire()) G.player.activeWeapon.fire();
		}
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			G.player.use();
		}
		
		if (Gdx.app.getType() == ApplicationType.Android) {
			//G.player.rot = filterCompass(G.player.rot, -Gdx.input.getRoll());
			//G.player.rot = -Gdx.input.getAzimuth();
		}
	
		if (Gdx.app.getType() == ApplicationType.Android) {
			if (joystickIndex != -1 && Gdx.input.isTouched(joystickIndex)) {
				joystickX = Gdx.input.getX(joystickIndex) / (float)C.screenScale;
				joystickY = Gdx.input.getY(joystickIndex) / (float)C.screenScale;
				
				float xIntensity = Math.min(Math.abs(joystickX - joystickCenterX), 20.0f) / 20.0f;
				float yIntensity = Math.min(Math.abs(joystickY - joystickCenterY), 20.0f) / 20.0f;
				if (joystickX >= joystickCenterX && xIntensity >= 0.1f) {
					G.player.move(Movement.TURN_RIGHT, xIntensity);
				}
				else if (joystickX <= joystickCenterX && xIntensity >= 0.1f) {
					G.player.move(Movement.TURN_LEFT, xIntensity);
				}
				
				if (joystickY >= joystickCenterY && yIntensity >= 0.1f) {
					G.player.move(Movement.BACKWARD, yIntensity);
				}
				else if (joystickY <= joystickCenterY && yIntensity >= 0.1f) {
					G.player.move(Movement.FORWARD, yIntensity);
				}
			}
			else {
				joystickIndex = -1;
				
				for (int i=0; i<2; i++) {
					if (Gdx.input.isTouched(i)) {
						float x = Gdx.input.getX(i) / (float)C.screenScale;
						if (x < G.screenWidth/3) {
							joystickCenterX = x;
							joystickCenterY = Gdx.input.getY(i) / (float)C.screenScale;
							joystickX = joystickCenterX;
							joystickY = joystickCenterY;
							joystickIndex = i;
							break;
						}
					}
				}
			}
			
			if (Gdx.input.isTouched() && joystickIndex == -1 || Gdx.input.isTouched(0) && joystickIndex == 1 || Gdx.input.isTouched(1) && joystickIndex == 0) {

				int actionIndex = joystickIndex == -1 ? 0 : joystickIndex+1%2;
				float x = Gdx.input.getX(actionIndex) / (float)C.screenScale;
				if (x >= G.screenWidth*(1.0f/3.0f) && x < G.screenWidth*(2.0f/3.0f)) {
					G.player.use();
				}
				else if (x >= G.screenWidth*(2.0f/3.0f)) {
					if (G.player.activeWeapon.canFire()) G.player.activeWeapon.fire();
				}
			}
		}
		
		G.camera.follow(G.player);
		G.camera.tick(tick);
		G.player.tick(ticker);
		G.world.tick(ticker);
		G.timer.tick(ticker);
		G.console.tick(ticker);
		
		if (G.player.isHit()) {
			postEffectColor = Color.RED.cpy();
		}
		
		if (postEffectColor != null) postEffectColor.a -= 0.02f;
	}
	
	@Override
	public void resize(int width, int height) {

	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		saveState();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		G.textureMap.texture.dispose();
	}

	
	public void saveState() {
		System.out.println("Saving state...");
		Preferences prefs = Gdx.app.getPreferences("level");
		prefs.putFloat("player.pos.x", G.player.position.x);
		prefs.putFloat("player.pos.y", G.player.position.y);
		prefs.putFloat("player.rot", G.player.rotation);
		prefs.flush();
	}
	
	public void loadState() {
		System.out.println("Loading state...");
		Preferences prefs = Gdx.app.getPreferences("level");
		if (prefs.contains("player.pos.x")) G.player.position.x = prefs.getFloat("player.pos.x");
		if (prefs.contains("player.pos.y")) G.player.position.y = prefs.getFloat("player.pos.y");
		if (prefs.contains("player.rot")) G.player.rotation = prefs.getFloat("player.rot");
	}

	@Override
	public void timeout(Object data) {
		// TODO Auto-generated method stub
		
	}
}
