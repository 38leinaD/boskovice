package fruitfly.virus;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.C.Orientation;
import fruitfly.virus.Episode.Level;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.EntityManager;
import fruitfly.virus.entities.GenericSprite;
import fruitfly.virus.entities.Gibs;
import fruitfly.virus.entities.damager.GreenBarrel;
import fruitfly.virus.entities.damager.RedBarrel;
import fruitfly.virus.entities.damager.StaticEntity;
import fruitfly.virus.entities.damager.TriggerableRedBarrel;
import fruitfly.virus.entities.decals.Poster;
import fruitfly.virus.entities.decals.Switch;
import fruitfly.virus.entities.decals.Terminal;
import fruitfly.virus.entities.light.FlickerLight;
import fruitfly.virus.entities.light.ILight;
import fruitfly.virus.entities.light.StaticLight;
import fruitfly.virus.entities.npc.NPC;
import fruitfly.virus.entities.npc.SecureBot;
import fruitfly.virus.entities.npc.Slime;
import fruitfly.virus.entities.npc.Turret;
import fruitfly.virus.particles.ParticleSystem;
import fruitfly.virus.particles.ParticleSystemManager;
import fruitfly.virus.pickups.BlueKeyPickUp;
import fruitfly.virus.pickups.CoinPickUp;
import fruitfly.virus.pickups.DiamondPickup;
import fruitfly.virus.pickups.GreenKeyPickUp;
import fruitfly.virus.pickups.HealthPickUp;
import fruitfly.virus.pickups.LaserAmmoPickUp;
import fruitfly.virus.pickups.LaserPistolPickUp;
import fruitfly.virus.pickups.PickUp;
import fruitfly.virus.pickups.RedKeyPickUp;
import fruitfly.virus.tiles.DoorTile;
import fruitfly.virus.tiles.FireTile;
import fruitfly.virus.tiles.ForceFieldTile;
import fruitfly.virus.tiles.GlassTile;
import fruitfly.virus.tiles.LaserBarrierTile;
import fruitfly.virus.tiles.Tile;
import fruitfly.virus.tiles.TriggerTile;
import fruitfly.virus.tiles.TwoWingDoorTile;
import fruitfly.virus.tiles.VoidTile;
import fruitfly.virus.tiles.Wall;
import fruitfly.virus.tiles.WallTile;
import fruitfly.virus.tiles.WideDoorTile;

public class World {
	private final static int TILE_VOID_WITH_CONTENT = 255;
	private final static int TILE_VOID = 254;
	private final static int TILE_WALL = 1;
	private final static int TILE_FORCE_FIELD = 50;
	private static final int TILE_LASER_BARRIER = 51;
	private static final int TILE_FIRE = 52;
	private final static int TILE_GLASS = 53;
	private final static int TILE_TRIGGER = 54;
	private final static int TILE_BLOCK = 55;


	private final static int TILE_WALL_WITH_LIGHT_EAST = 100;
	private final static int TILE_WALL_WITH_LIGHT_NORTH = 101;
	private final static int TILE_WALL_WITH_LIGHT_WEST = 102;
	private final static int TILE_WALL_WITH_LIGHT_SOUTH = 103;

	private final static int TILE_WALL_WITH_SWITCH_EAST = 104;
	private final static int TILE_WALL_WITH_SWITCH_NORTH = 105;
	private final static int TILE_WALL_WITH_SWITCH_WEST = 106;
	private final static int TILE_WALL_WITH_SWITCH_SOUTH = 107;

	private final static int TILE_WALL_WITH_TERMINAL = 108;
	private static final int TILE_WALL_WITH_POSTER = 109;

	private final static int TILE_DOOR_VERTICAL = 120;
	private final static int TILE_DOOR_HORIZONTAL = 121;
	
	private final static int TILE_WIDE_DOOR_VERTICAL = 122;
	private final static int TILE_WIDE_DOOR_HORIZONTAL = 123;
	
	private final static int LIGHT_TYPE_STATIC_WHITE = 0;
	private final static int LIGHT_TYPE_FLICKER_WHITE = 1;
	private final static int LIGHT_TYPE_FLICKER_BLUE = 2;

	private final static int PLAYER_SPAWN = 0;
	private final static int ENTITY_SLIME = 100;
	private final static int ENTITY_ROBO = 101;
	private final static int ENTITY_TURRET = 102;
	private final static int ENTITY_GREEN_BARREL = 103;
	private final static int ENTITY_RED_BARREL = 104;
	private final static int ENTITY_RED_BARREL_TRIGGERABLE = 105;

	private final static int PICKUP_LASER_PISTOL = 1;
	private final static int PICKUP_LASER_AMMO = 2;
	private final static int PICKUP_HEALTH = 3;
	private final static int PICKUP_RED_KEY = 4;
	private final static int PICKUP_GREEN_KEY = 5;
	private final static int PICKUP_BLUE_KEY = 6;
	private final static int PICKUP_DIAMOND = 7;
	private final static int PICKUP_COIN = 8;
	
	private final static int DECORATION_GIB_LIMB = 50;
	private final static int DECORATION_GIB_HEAD1 = 51;
	private final static int DECORATION_GIB_HEAD2 = 52;
	private final static int DECORATION_GIB_ARM = 53;
	private final static int DECORATION_SPACESHIP = 54;
	private final static int DECORATION_MARS_STONES = 55;
	private final static int DECORATION_MARS_ROCKS = 56;
	private static final int DECORATION_WALL_ROCKS = 57;
	
	private final static int ORIENTATION_EAST = 0;
	private final static int ORIENTATION_NORTH = 1;
	private final static int ORIENTATION_WEST = 2;
	private final static int ORIENTATION_SOUTH = 3;
	
	private final static int REQUIRES_NO_KEY = 0;
	private final static int REQUIRES_RED_KEY = 1;
	private final static int REQUIRES_GREEN_KEY = 2;
	private final static int REQUIRES_BLUE_KEY = 3;
	
	public EntityManager entityManager;
	public ParticleSystemManager particleManager;
	public int time = 0;
	private int width, height;
	public Tile[] tiles;

	private Texture skyTexture;
	private Lightmap lightmap;
	private Level levelConfig;
	
	public World(Level levelConfig) {
		this.levelConfig = levelConfig;
		entityManager = new EntityManager();
		particleManager = new ParticleSystemManager(500);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public boolean isInBounds(float x, float y) {
		if (x < 0.0 || x >= getWidth() || y < 0.0 || y >= getHeight()) return false;
		return true;
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) return null;
		return tiles[y * getWidth() + x];
	}
	
	public Tile setTile(int x, int y, Tile t) {
		return tiles[y * getWidth() + x] = t;
	}
	
	public void tick(long tick) {
		entityManager.tick(tick);
		particleManager.tick(tick);
		
		if (tick % 60 == 0) time++;
	}
	
	private Map<Integer, ITrigger> triggers = new HashMap<Integer, ITrigger>();
	private Map<Integer, ITriggerTarget> triggerTargets = new HashMap<Integer, ITriggerTarget>();

	public void init() {
		Pixmap image = null;
		image = new Pixmap(levelConfig.getFile());
		
		this.width = image.getWidth();
		this.height = image.getHeight();

		this.tiles = new Tile[image.getWidth() * image.getHeight()];
		
		for (int y=0; y<image.getHeight(); y++) {
			for (int x=0; x<image.getWidth(); x++) {
				int tileCode = image.getPixel(x, image.getHeight() - 1 - y) >> 8;
				int tileType = (tileCode & 0xff0000) >> 16;
				int data0 = (tileCode & 0x00ff00) >> 8;
				int data1 = (tileCode & 0x0000ff) >> 0;

				this.tiles[y * image.getWidth() + x] = null;

				if (tileType == TILE_VOID) {
					SubTexture floor = G.textureMap.floors[255 - data0];
					// data1 could  become ceiling
					VoidTile vt = new VoidTile(x, y, floor);
					this.tiles[y * image.getWidth() + x] = vt;
				}
			}
		}
		
		for (int y=0; y<image.getHeight(); y++) {
			for (int x=0; x<image.getWidth(); x++) {
				
				int tileCode = image.getPixel(x, image.getHeight() - 1 - y) >> 8;
				int tileType = (tileCode & 0xff0000) >> 16;
				int data0 = (tileCode & 0x00ff00) >> 8;
				int data1 = (tileCode & 0x0000ff) >> 0;
				int alpha = ((image.getPixel(x, image.getHeight() - 1 - y)) & 0x000000ff) >> 0;
				
				if (alpha == 0) {
					this.tiles[y * image.getWidth() + x] = null;
				}
				else {
					if (tileType == TILE_WALL) {
						this.tiles[y * image.getWidth() + x] = new WallTile(x, y, G.textureMap.walls[data0]);
					}
					else if (	tileType == TILE_WALL_WITH_LIGHT_EAST ||
								tileType == TILE_WALL_WITH_LIGHT_NORTH ||
								tileType == TILE_WALL_WITH_LIGHT_WEST ||
								tileType == TILE_WALL_WITH_LIGHT_SOUTH) {
						this.tiles[y * image.getWidth() + x] = new WallTile(x, y, G.textureMap.walls[data0]);
						
						float intensity = 0.6f;
						ILight l = null;
						Vector3 lightPos = null;
						if (tileType == TILE_WALL_WITH_LIGHT_EAST) {
							lightPos = new Vector3(x + 1.4f, y + 0.5f, 0.5f);
						}
						else if (tileType == TILE_WALL_WITH_LIGHT_NORTH) {
							lightPos = new Vector3(x + 0.5f, y + 1.4f, 0.5f);
						}
						else if (tileType == TILE_WALL_WITH_LIGHT_WEST) {
							lightPos = new Vector3(x - 0.4f, y + 0.5f, 0.5f);
						}
						else if (tileType == TILE_WALL_WITH_LIGHT_SOUTH) {
							lightPos = new Vector3(x + 0.5f, y - 0.4f, 0.5f);
						}
						
						if (data1 == LIGHT_TYPE_STATIC_WHITE)	l = new StaticLight(lightPos, Color.WHITE, intensity);
						else if (data1 == LIGHT_TYPE_FLICKER_WHITE) l = new FlickerLight(lightPos, Color.WHITE, intensity);
						else if (data1 == LIGHT_TYPE_FLICKER_BLUE) l = new FlickerLight(lightPos, Color.BLUE, intensity);
						else {
							throw new RuntimeException("Unknown ligh-type "+ data1);
						}
						
						entityManager.addEntity(l);
					}
					else if (	tileType == TILE_WALL_WITH_SWITCH_EAST ||
							tileType == TILE_WALL_WITH_SWITCH_NORTH ||
							tileType == TILE_WALL_WITH_SWITCH_WEST ||
							tileType == TILE_WALL_WITH_SWITCH_SOUTH) {
						WallTile wt = new WallTile(x, y, G.textureMap.walls[data0]);
						this.tiles[y * image.getWidth() + x] = wt;
	
						Orientation ori = null;
						Wall w = null;
						if (tileType == TILE_WALL_WITH_SWITCH_EAST) {
							w = wt.east;
							ori = Orientation.East;
						}
						else if (tileType == TILE_WALL_WITH_SWITCH_WEST) {
							w = wt.west;
							ori = Orientation.West;
						}
						else if (tileType == TILE_WALL_WITH_SWITCH_NORTH) {
							w = wt.north;
							ori = Orientation.North;
						}
						else if (tileType == TILE_WALL_WITH_SWITCH_SOUTH) {
							w = wt.south;
							ori = Orientation.South;
						}
						Switch s = new Switch(w, ori);
						this.triggers.put(data1, s);
					}
					else if (tileType == TILE_FORCE_FIELD) {
						SubTexture floor = inferTextures(x, y)[0];
						ForceFieldTile fft = new ForceFieldTile(x, y, data0 == 0, floor);
						triggerTargets.put(data1, fft);
						this.tiles[y * image.getWidth() + x] = fft;
					}
					else if (tileType == TILE_GLASS) {
						SubTexture floor = inferTextures(x, y)[0];
						GlassTile glass = new GlassTile(x, y, floor, data0 == 0);
						this.tiles[y * image.getWidth() + x] = glass;
					}
					else if (tileType == TILE_LASER_BARRIER) {
						LaserBarrierTile b = new LaserBarrierTile(x, y, data1);
						this.tiles[y * image.getWidth() + x] = b;
					}
					else if (tileType == TILE_FIRE) {
						SubTexture floor = inferTextures(x, y)[0];
						FireTile t = new FireTile(x, y, floor);
						this.tiles[y * image.getWidth() + x] = t;
					}
					else if (tileType == TILE_TRIGGER) {
						SubTexture floor = inferTextures(x, y)[0];
						TriggerTile t = new TriggerTile(x, y, floor);
						this.tiles[y * image.getWidth() + x] = t;
						
						triggers.put(data1, t);
					}
					else if (tileType == TILE_BLOCK) {
						SubTexture floor = inferTextures(x, y)[0];
						VoidTile t = new VoidTile(x, y, floor);
						this.tiles[y * image.getWidth() + x] = t;
						
						t.setBlockingMovement(true);
					}
					else if (tileType == TILE_WALL_WITH_TERMINAL) {
						WallTile wt = new WallTile(x, y, G.textureMap.walls[data0]);
						this.tiles[y * image.getWidth() + x] = wt;
						
						Terminal t = null;
						if (data1 == ORIENTATION_EAST) {
							t = new Terminal(wt.east, Orientation.East);
						}
						else if (data1 == ORIENTATION_NORTH) {
							t = new Terminal(wt.north, Orientation.North);
						}
						else if (data1 == ORIENTATION_WEST) {
							t = new Terminal(wt.west, Orientation.West);
						}
						else if (data1 == ORIENTATION_SOUTH) {
							t = new Terminal(wt.south, Orientation.South);
						}
					}
					else if (tileType == TILE_WALL_WITH_POSTER) {
						WallTile wt = new WallTile(x, y, G.textureMap.walls[data0]);
						this.tiles[y * image.getWidth() + x] = wt;
						
						Vector2 pos = new Vector2(0.5f, 0.5f);
						
						Poster d = null;
						if (data1 == ORIENTATION_EAST) {
							d = new Poster(wt.east, pos, Orientation.East, G.textureMap.poster0);
						}
						else if (data1 == ORIENTATION_NORTH) {
							d = new Poster(wt.north, pos, Orientation.North, G.textureMap.poster0);
						}
						else if (data1 == ORIENTATION_WEST) {
							d = new Poster(wt.west, pos, Orientation.West, G.textureMap.poster0);
						}
						else if (data1 == ORIENTATION_SOUTH) {
							d = new Poster(wt.south, pos, Orientation.South, G.textureMap.poster0);
						}
					}
					else if (tileType >= TILE_DOOR_VERTICAL && tileType <= TILE_WIDE_DOOR_HORIZONTAL) {
						DoorTile dt = null;
						SubTexture floor = inferTextures(x, y)[0];
						SubTexture wall = G.textureMap.walls[data0];
						if (tileType == TILE_DOOR_VERTICAL) {
							dt = new TwoWingDoorTile(x, y, false, wall, floor);
						}
						else if (tileType == TILE_DOOR_HORIZONTAL) {
							dt = new TwoWingDoorTile(x, y, true, wall, floor);
						}
						else if (tileType == TILE_WIDE_DOOR_VERTICAL) {
							dt = new WideDoorTile(x, y, false, wall, floor);
						}
						else if (tileType == TILE_WIDE_DOOR_HORIZONTAL) {
							dt = new WideDoorTile(x, y, true, wall, floor);
						}
						
						if (data1 == REQUIRES_RED_KEY) {
							dt.setRequiredKey(Key.redKey);
						}
						else if (data1 == REQUIRES_GREEN_KEY) {
							dt.setRequiredKey(Key.greenKey);
						}
						else if (data1 == REQUIRES_BLUE_KEY) {
							dt.setRequiredKey(Key.blueKey);
						}
						
						this.tiles[y * image.getWidth() + x] = dt;
					}
					else if (tileType == TILE_VOID_WITH_CONTENT){
						SubTexture floor = inferTextures(x, y)[0];
						VoidTile vt = new VoidTile(x, y, floor);
						this.tiles[y * image.getWidth() + x] = vt;
						
						PickUp pickUp = null;
						
						if (data0 == PICKUP_LASER_PISTOL) {
							vt.setPickup(new LaserPistolPickUp(x + 0.5f, y + 0.5f));
						}
						else if (data0 == PICKUP_LASER_AMMO) {
							vt.setPickup(new LaserAmmoPickUp(x + 0.5f, y + 0.5f));
						}
						else if (data0 == PICKUP_HEALTH) {
							vt.setPickup(new HealthPickUp(x + 0.5f, y + 0.5f));
						}
						else if (data0 == PICKUP_RED_KEY) {
							vt.setPickup(new RedKeyPickUp(x + 0.5f, y + 0.5f));
						}
						else if (data0 == PICKUP_GREEN_KEY) {
							vt.setPickup(new GreenKeyPickUp(x + 0.5f, y + 0.5f));
						}
						else if (data0 == PICKUP_BLUE_KEY) {
							vt.setPickup(new BlueKeyPickUp(x + 0.5f, y + 0.5f));
						}
						else if (data0 == PICKUP_DIAMOND) {
							vt.setPickup(new DiamondPickup(x + 0.5f, y + 0.5f));
						}
						else if (data0 == PICKUP_COIN) {
							vt.setPickup(new CoinPickUp(x + 0.5f, y + 0.5f));
						}
						else if (data0 == DECORATION_GIB_LIMB) {
							entityManager.addEntity(new Gibs(x + 0.1f + MathUtils.random(0.8f), y + 0.1f + MathUtils.random(0.8f), G.textureMap.limbGib));
						}
						else if (data0 == DECORATION_GIB_HEAD1) {
							entityManager.addEntity(new Gibs(x + 0.1f + MathUtils.random(0.8f), y + 0.1f + MathUtils.random(0.8f), G.textureMap.head1Gib));
						}
						else if (data0 == DECORATION_GIB_HEAD2) {
							entityManager.addEntity(new Gibs(x + 0.1f + MathUtils.random(0.8f), y + 0.1f + MathUtils.random(0.8f), G.textureMap.head2Gib));
						}
						else if (data0 == DECORATION_GIB_ARM) {
							entityManager.addEntity(new Gibs(x + 0.1f + MathUtils.random(0.8f), y + 0.1f + MathUtils.random(0.8f), G.textureMap.armGib));
						}
						else if (data0 == DECORATION_SPACESHIP) {
							entityManager.addEntity(new GenericSprite(new Vector3(x + 0.5f, y + 0.5f, 0.0f), 1.5f, G.textureMap.spaceship));
							vt.setBlockingMovement(true);
						}
						else if (data0 == DECORATION_MARS_STONES) {
							ParticleSystem ps = new ParticleSystem(4, false, 0.2f, new Vector3(x+0.5f, y+0.5f, 0.1f), null, G.textureMap.marsStones, Color.WHITE, -1);
							G.world.particleManager.addParticleSystem(ps);						
						}
						else if (data0 == DECORATION_MARS_ROCKS) {
							entityManager.addEntity(new GenericSprite(new Vector3(x + 0.5f, y + 0.5f, 0.0f), 1.0f, G.textureMap.marsRocks[MathUtils.random(G.textureMap.marsRocks.length - 1)]));
							vt.setBlockingMovement(true);
						}
						else if (data0 == DECORATION_WALL_ROCKS) {
							entityManager.addEntity(new GenericSprite(new Vector3(x + 0.5f, y + 0.5f, 0.0f), 0.8f, G.textureMap.wallRocks));
							//vt.setBlockingMovement(true);
						}
						
						
						
						float orientation;
						
						if (data1 == ORIENTATION_EAST) {
							orientation = 0;
						}
						else if (data1 == ORIENTATION_NORTH) {
							orientation = 90.0f;
						}
						else if (data1 == ORIENTATION_WEST) {
							orientation = 180.0f;
						}
						else {
							orientation = 270.0f;
						}
						
						if (data0 == PLAYER_SPAWN) {
							G.player.position.x = x + 0.5f;
							G.player.position.y = y + 0.5f;
							
							G.player.rotation = orientation;
						}
						
						if (data0 == ENTITY_SLIME) {
							NPC npc = new Slime(new Vector3(x + 0.5f, y + 0.5f, 0), orientation);
							entityManager.addEntity(npc);
						}
						else if (data0 == ENTITY_ROBO) {
							NPC npc = new SecureBot(new Vector3(x + 0.5f, y + 0.5f, 0.4f), orientation);
							entityManager.addEntity(npc);
						}
						else if (data0 == ENTITY_TURRET) {
							NPC npc = new Turret(new Vector3(x + 0.5f, y + 0.5f, 0.0f), orientation);
							entityManager.addEntity(npc);
						}
						else if (data0 == ENTITY_GREEN_BARREL) {
							StaticEntity b = new GreenBarrel(new Vector3(x + 0.5f, y + 0.5f, 0.0f));
							entityManager.addEntity(b);
						}
						else if (data0 == ENTITY_RED_BARREL) {
							StaticEntity b = new RedBarrel(new Vector3(x + 0.5f, y + 0.5f, 0.0f));
							entityManager.addEntity(b);
						}
						else if (data0 == ENTITY_RED_BARREL_TRIGGERABLE) {
							TriggerableRedBarrel b = new TriggerableRedBarrel(new Vector3(x + 0.5f, y + 0.5f, 0.0f));
							entityManager.addEntity(b);
							
							triggerTargets.put(data1, b);
						}
					}
				}
			}
		}
		
		for (Integer tId : triggers.keySet()) {
			ITrigger trigger = triggers.get(tId);
			ITriggerTarget target = triggerTargets.get(tId);
			System.out.println("* Setting up trigger " + tId + ": " + trigger + " -> " + target);
			trigger.setTarget(target);
		}
		
		this.triggers = null;
		this.triggerTargets = null;
		
		skyTexture = new Texture(Gdx.files.internal("data/skys/marsnight.png"));
		skyTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

		System.out.println("* player spawn set to " + G.player);
		
		entityManager.tick(G.game.ticker); // Tick to make sure newly added entities/lights are avail
		
		lightmap = new Lightmap(1024, 1024, 4);
		lightmap.bake();
	}

	// Infer tiles floor/ceil textures from neighboring tiles
	private SubTexture[] inferTextures(int x, int y) {
		Map<SubTexture, Integer> texCount = new HashMap<TextureMap.SubTexture, Integer>();
		for (int xx=x-1; xx<=x+1; xx++) {
			for (int yy=y-1; yy<=y+1; yy++) {
				if (xx == x && yy == y) continue;
				
				Tile t = getTile(xx, yy);
				if (t instanceof VoidTile) {
					VoidTile vt = (VoidTile) t;
					
					SubTexture floor = vt.getFloorTexture();
					
					Integer count = texCount.get(floor);
					if (count == null) {
						texCount.put(floor, 1);
					}
					else {
						texCount.put(floor, count + 1);
					}
				}
			}
		}
		
		int max = 0;
		SubTexture inferedFloor = null;
		for (SubTexture tex : texCount.keySet()) {
			int count = texCount.get(tex);
			if (count > max) {
				max = count;
				inferedFloor = tex;
			}
		}
		
		if (inferedFloor == null) {
			System.out.println("!!! UNABLE TO INFER TEXTURE FOR TILE (" + x + "," + y + ")");
			inferedFloor = G.textureMap.floors[0];
		}
		return new SubTexture[] { inferedFloor };
	}
	
	public Vector3 getGlobalLight() {
		return new Vector3(20.0f, 20.0f ,20.0f);
	}	
	
	public Texture getSkyTexture() {
		return skyTexture;
	}
	
	public Color getSkyLight() {
		return levelConfig.getSkyLight();
	}
	
	public Lightmap getLightmap() {
		return lightmap;
	}
}
