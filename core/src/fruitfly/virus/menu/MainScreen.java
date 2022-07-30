package fruitfly.virus.menu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import fruitfly.virus.Episode;
import fruitfly.virus.G;
import fruitfly.virus.Gameloop;
import fruitfly.virus.TextureMap.SubTexture;

public class MainScreen implements Screen, IActionTarget {
	
	private Sprite logoSprite;
	
	private Menu menu;
	
	private List<Episode> episodes = new LinkedList<Episode>();
	private BitmapFont font;
	
	public MainScreen() {
		slimeQueue = new LinkedList<MainScreen.Slime>();
		for (int i=0; i<21; i++) {
			slimeQueue.add(new Slime(i * 16-16,  MathUtils.random(1.0f, 3.0f)));
		}
		slimes = new LinkedList<MainScreen.Slime>();
		
		episodes = Episode.getEpisodes();
		
		font = new BitmapFont(Gdx.files.internal("data/fonts/main.fnt"), true);
		font.getData().setScale(0.7f);
		
		createMenu();
	}

	private void createMenu() {
		menu = new Menu();

		HorizontalList list = new HorizontalList(0, 66, G.screenWidth, 80);
		list.setText("Select an episode:");
		
		Button b = null;
		int i=0;
		for (Episode e : episodes) {
			b = new Button(0, 0, e.getLogo(), null);
			b.setText(e.getName());
			b.setId(e.getName());
			b.setTarget(this);
			list.addItem(b);
			i++;
		}
		
		b = new Button(0, 0, G.baseTextureMap.earth, null);
		b.setText("Get more episodes online");
		b.setTarget(this);
		b.setId("more");
		
		list.addItem(b);
		
		menu.addView(list);
	}

	private class Slime {
		Slime(int x, float v) {
			this.x = x;
			this.y = -32.0f;
			this.v = v;
		}
		float x, y;
		float v;
		float d;
		
		void update(float delta) {
			d += delta;
			while (d >= 0.01f) {
				d -= 0.01f;
				y += v;
			}
		}
	}
	
	private List<Slime> slimeQueue;
	private List<Slime> slimes;
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}
	
	float slimeDelta = 0;
	float boskoDelta = 0;
	float secureBotDelta = 0;
	float ticker = 0;
	boolean slimeReverse = false;
	boolean secureBotReverse = false;

	@Override
	public void render(float delta) {
		ticker += delta;
		Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc (GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (slimeQueue.size() > 0) {
			slimes.add(slimeQueue.remove(MathUtils.random(slimeQueue.size()-1)));
		}
		
		boolean dropping = false;
		for (Slime s : slimes) {
			
			s.update(delta);
			if (s.y < G.screenHeight) {
				dropping = true;
			}
			G.renderer2d.begin();
			if (s.y > -32.0f) {
				G.renderer2d.render(G.baseTextureMap.slimyGreen, s.x, 0.0f, 32.0f, s.y);
			}
			G.renderer2d.render(G.baseTextureMap.slimyDrop, s.x, s.y, 32.0f, 32.0f);
			G.renderer2d.end();
		}
		dropping = false;
		if (!dropping) {
			//Gdx.gl20.glClearColor(0.18f, 0.62f, 0.0f, 1.0f);
			Gdx.gl20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
			Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
					
			
			boskoDelta += delta;
			
			if (!slimeReverse) {
				slimeDelta += delta;
				if (slimeDelta >= 1) {
					slimeDelta = 1;
					slimeReverse = true;
				}
			}
			else {
				slimeDelta -= delta;
				if (slimeDelta <= 0) {
					slimeDelta = 0;
					slimeReverse = false;
				}
			}
			
			if (!secureBotReverse) {
				secureBotDelta += delta;
				if (secureBotDelta >= 1) {
					secureBotDelta = 1;
					secureBotReverse = true;
				}
			}
			else {
				secureBotDelta -= delta;
				if (secureBotDelta <= 0) {
					secureBotDelta = 0;
					secureBotReverse = false;
				}
			}
			
			if (boskoDelta >= 1) {
				boskoDelta -= 1;
			}
			

			G.renderer2d.begin();
			G.renderer2d.setLightColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
			G.renderer2d.render(G.baseTextureMap.slime_logo, 30, 29 - Interpolation.fade.apply(slimeDelta) * 1, 1);
			G.renderer2d.render(G.baseTextureMap.secureBot_logo[(int) ((ticker * 10) % 3)], G.screenWidth - 50 + Interpolation.sine.apply(secureBotDelta) * 2, 10 - Interpolation.sine.apply(secureBotDelta) * 5, 1);
			
			G.renderer2d.render(G.baseTextureMap.signTop_logo, 0, 37, G.screenWidth, 16);
			G.renderer2d.render(G.baseTextureMap.signInner_logo, 0, 53, G.screenWidth, G.screenHeight);
			SubTexture bosko = G.baseTextureMap.bosko_logo[(int)(ticker % 10) == 0 ? 1 : 0];
			G.renderer2d.render(bosko, G.screenWidth/2 - bosko.getWidth()/2, 5, 1);
			G.renderer2d.render(G.baseTextureMap.text_logo, 110, 55, 1);
			Vector2 textSize = new Vector2();
			G.renderer2d.getStringSize("Version 1.0 / (c) 2012 by fruitfly", textSize);
			G.renderer2d.renderString("Version 1.0 / (c) 2012 by fruitfly", G.screenWidth/2 - textSize.x/2, G.screenHeight - 12);
			G.renderer2d.end();

			menu.render();
			menu.handleInput();
		}

	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {

	}

	@Override
	public void handleAction(View source) {
		for (Episode e : episodes) {
			if (e.getName().equals(source.getId())) {
				Gameloop gameloop = new Gameloop();
				gameloop.startEpisode(e);
				G.container.setScreen(gameloop);
			}
		}
	}

}
