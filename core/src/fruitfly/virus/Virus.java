package fruitfly.virus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fruitfly.virus.menu.MainScreen;
import fruitfly.virus.render.Renderer2D;

public class Virus extends Game {

	@Override
	public void create() {
		System.out.println("OpenGL version: " + Gdx.gl.glGetString(GL20.GL_VERSION));
		
		G.container = this;
		
		G.windowWidth = Gdx.graphics.getWidth();
		G.windowHeight = Gdx.graphics.getHeight();

		G.screenWidth = Gdx.graphics.getWidth() / C.screenScale;
		G.screenHeight = Gdx.graphics.getHeight() / C.screenScale;
		
		G.aspect = G.screenWidth / (float)G.screenHeight;
		
		G.console = new Console();
		
		G.orthoCam = new OrthographicCamera(Gdx.graphics.getWidth(), -Gdx.graphics.getHeight());
		G.orthoCam.setToOrtho(true);
		G.batch = new SpriteBatch();
		
		Texture texture = new Texture(Gdx.files.internal("data/textures.menu.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		G.baseTextureMap = new BaseTextureMap(texture);
		
		G.renderer2d = new Renderer2D();
		
		G.font = new BitmapFont(Gdx.files.internal("data/fonts/consolas.fnt"), true);
		G.font.getData().setScale(2.0f);
		G.fontSmall = new BitmapFont(Gdx.files.internal("data/fonts/consolas.fnt"), true);
		G.fontSmall.getData().setScale(0.7f);
		G.fontBig = new BitmapFont(Gdx.files.internal("data/fonts/consolas.fnt"), true);
		G.fontBig.getData().setScale(3.0f);
		C.buildNumber = Integer.parseInt(Gdx.files.internal("data/buildnumber.txt").readString());
		
		Texture logo = new Texture(Gdx.files.internal("data/fruitfly.png"));
		logo.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		G.logo = new Sprite(logo);
		G.logo.flip(false, true);
		G.logo.setSize(2*50.0f, 2*55.0f);
		G.logo.setPosition(G.windowWidth - 100 - 10, G.windowHeight - 100 - 20);
		MainScreen mainScreen = new MainScreen();
		Gameloop gameloop = new Gameloop();
		Episode e1 = Episode.getEpisodes().get(1);
		gameloop.startEpisode(e1);
		setScreen(gameloop);
	}
	
	@Override
	public void render() {
		super.render();
		Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);
		
		G.batch.setProjectionMatrix(G.orthoCam.combined);
		G.batch.begin();
		G.fontSmall.draw(G.batch, "fps: " + Gdx.graphics.getFramesPerSecond(), G.windowWidth - 230, 10);
		G.batch.end();
	}
}
