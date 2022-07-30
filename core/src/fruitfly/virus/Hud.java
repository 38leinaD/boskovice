package fruitfly.virus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;

import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.light.ILight;
import fruitfly.virus.render.Renderer2D;
import fruitfly.virus.weapons.LaserPistol;

public class Hud {
	private BitmapFont font;
	
	public Hud() {
		font = new BitmapFont(Gdx.files.internal("data/fonts/consolas.fnt"), false);
	}
	
	private final static Color minLight = new Color(0.5f, 0.5f, 0.5f, 1.0f);
	
	float tWalk = 180.0f;
	float tTurn = 0.0f;
	public void renderWeapon() {
		G.renderer2d.begin();

		ILight prominentLight = G.player.getProminentLight();
		
		if (prominentLight != null) {
			float distance = prominentLight.getPosition().dot(G.player.position);
			Color c = prominentLight.getLightColor().cpy();
			c.mul(10.0f/distance * prominentLight.getLightIntensity());
			c.add(minLight);
			c.a = 1.0f;

			G.renderer2d.setLightColor(c);
		}
		else {
			G.renderer2d.setLightColor(minLight);
		}
		
		// Jiggle weapon while walking
		if (G.player.getMovements().contains(Movement.FORWARD) || G.player.getMovements().contains(Movement.BACKWARD)) {
			tWalk += 10.0f;
		}
		else if (tWalk < 170.0f || tWalk > 190.0f) {
			tWalk += 15.0f;
		}
		
		if (tWalk >= 360.0f) tWalk -= 360.0f;

		final float walkDisplacementX = 2 * MathUtils.sinDeg(tWalk);
		final float walkDisplacementY = 6 * MathUtils.cosDeg(tWalk);
		
		// Weapon lags while turning
		if (G.player.getMovements().contains(Movement.TURN_LEFT)) {
			if (tTurn < 1.0f) tTurn += 0.1f;
		}
		else if (G.player.getMovements().contains(Movement.TURN_RIGHT)) {
			if (tTurn > -1.0f) tTurn -= 0.1f;
		}
		else {
			if (tTurn > -0.0001f && tTurn < 0.001f) tTurn = 0.0f; // for numerical inaccuracies of float
			else if (tTurn > 0.0f) tTurn -= 0.1f;
			else if (tTurn < 0.0f) tTurn += 0.1f;
		}
		final float turnDisplacement = 10.0f * MathUtils.sin(tTurn * 2.0f);
		
		if (G.player.activeWeapon != null) {
			SubTexture tex = G.player.activeWeapon.getSprite();
			
			final float scale = 1.5f;
			G.renderer2d.render(tex, G.screenWidth/2 -  scale * tex.getWidth()/2 + (int)(walkDisplacementX + turnDisplacement) + 20, 100.0f * (1 - G.player.activeWeapon.getAimingAdvance()) + G.screenHeight- scale * tex.getHeight() + (int)(walkDisplacementY) + 10, scale);
			
			G.renderer2d.end();
		
		}
	}
	
	public void render() {
		renderWeapon();
		
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc (GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		G.renderer2d.begin();
		G.renderer2d.render(G.textureMap.hudBackground, 0, 0, 320, 16);
		//G.renderer2d.render(G.textureMap.hudBackButton, 1, 0, 1);
		
		if (G.player.activeWeapon != null && G.player.activeWeapon instanceof LaserPistol) {
			LaserPistol laserPistol = (LaserPistol) G.player.activeWeapon;
			G.renderer2d.render(G.textureMap.laserPistolPickup, 170, 1, 1);
			G.renderer2d.render(G.textureMap.hudLaserBar, 190, 0, 50 * laserPistol.getCapacity(), 16);
			G.renderer2d.render(G.textureMap.hudLaserBar, 190, 0, 50 * laserPistol.getCharge(), 16);
		}
		
		int i=0;
		for (Key key : G.player.getInventory().getKeys()) {
			SubTexture tex = null;
			if (key == Key.redKey) {
				tex = G.textureMap.redKeyPickUp;
			}
			else if (key == Key.greenKey) {
				tex = G.textureMap.greenKeyPickUp;
			}
			else if (key == Key.blueKey) {
				tex = G.textureMap.blueKeyPickUp;
			}
			G.renderer2d.render(tex, G.screenWidth - 20 - (i*15) , 1, 1.0f);
			i++;
		}

		G.renderer2d.end();
		
		G.batch.begin();
		G.fontSmall.draw(G.batch, "Health: " + G.player.health, 400, 5);
		G.fontSmall.draw(G.batch, "Score: " + G.player.score, 400, 25);
		G.fontSmall.draw(G.batch, "Time: " + G.world.time, 400, 45);
		G.batch.end();
	}
}
