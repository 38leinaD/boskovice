package fruitfly.virus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;

public class Audio {
	public Sound laserShot = Gdx.audio.newSound(Gdx.files.internal("data/audio/lasershot.wav"));
	public Sound laserHit = Gdx.audio.newSound(Gdx.files.internal("data/audio/laserhit.wav"));
	public Sound laserHitSlime = Gdx.audio.newSound(Gdx.files.internal("data/audio/laserhitslime.wav"));
	public Sound consoleChar = Gdx.audio.newSound(Gdx.files.internal("data/audio/consolechar.wav"));
	public Sound slimeDies = Gdx.audio.newSound(Gdx.files.internal("data/audio/slimedies.wav"));
	public Sound secureBotDies = Gdx.audio.newSound(Gdx.files.internal("data/audio/securebotdies.wav"));
	public Sound turretDies = Gdx.audio.newSound(Gdx.files.internal("data/audio/turretdies.wav"));

	public Sound pickUp = Gdx.audio.newSound(Gdx.files.internal("data/audio/pickup.wav"));
	public Sound healthPickUp = Gdx.audio.newSound(Gdx.files.internal("data/audio/healthpickup.wav"));
	public Sound pressSwitch = Gdx.audio.newSound(Gdx.files.internal("data/audio/pressSwitch.wav"));

	public Sound disableForceField = Gdx.audio.newSound(Gdx.files.internal("data/audio/disableforcefield.wav"));
	
	public Sound doorOpening = Gdx.audio.newSound(Gdx.files.internal("data/audio/dooropening.wav"));
	public Sound doorClosing = Gdx.audio.newSound(Gdx.files.internal("data/audio/dooropening.wav"));
	public Sound doorShut = Gdx.audio.newSound(Gdx.files.internal("data/audio/doorshut.wav"));

	public Sound click = Gdx.audio.newSound(Gdx.files.internal("data/audio/click.wav"));
	public Sound acidHit = Gdx.audio.newSound(Gdx.files.internal("data/audio/acidhit.wav"));
	public Sound noEffectHit = Gdx.audio.newSound(Gdx.files.internal("data/audio/noeffecthit.wav"));
	public Sound explosion = Gdx.audio.newSound(Gdx.files.internal("data/audio/explosion.wav"));

	
	public float getVolume(Vector3 position, Vector3 audioSource) {
		return (float) Math.pow(1.0f - Math.min(position.dst(audioSource), 20.0f) / 20.0f,2) * 0.5f;
	}
}
