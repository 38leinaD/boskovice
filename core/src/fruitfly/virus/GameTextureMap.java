package fruitfly.virus;

import com.badlogic.gdx.graphics.Texture;

import fruitfly.virus.TextureMap.SubTexture;

public class GameTextureMap extends TextureMap {

	public SubTexture walls[] = {
			// Low Res
			new SubTexture(32, 32, 1, 1),
			new SubTexture(33, 32, 1, 1),
			new SubTexture(34, 32, 1, 1),
			new SubTexture(35, 32, 1, 1),
			new SubTexture(36, 32, 1, 1),
			new SubTexture(37, 32, 1, 1),
			new SubTexture(38, 32, 1, 1),
			new SubTexture(39, 32, 1, 1),
			new SubTexture(40, 32, 1, 1),
			new SubTexture(41, 32, 1, 1),
			new SubTexture(42, 32, 1, 1),
			new SubTexture(43, 32, 1, 1),
			new SubTexture(44, 32, 1, 1),
			new SubTexture(45, 32, 1, 1),
			new SubTexture(46, 32, 1, 1),
			new SubTexture(47, 32, 1, 1),
			new SubTexture(48, 32, 1, 1),
			new SubTexture(49, 32, 1, 1),
			new SubTexture(50, 32, 1, 1),
			new SubTexture(51, 32, 1, 1),
			new SubTexture(52, 32, 1, 1),
			new SubTexture(53, 32, 1, 1),
			new SubTexture(54, 32, 1, 1),
			new SubTexture(55, 32, 1, 1),
			new SubTexture(56, 32, 1, 1),
			new SubTexture(57, 32, 1, 1),
			new SubTexture(58, 32, 1, 1),
			new SubTexture(59, 32, 1, 1),
			new SubTexture(60, 32, 1, 1),
			new SubTexture(61, 32, 1, 1),
			new SubTexture(62, 32, 1, 1),
			new SubTexture(63, 32, 1, 1),
			
			// High Res
			
			// Sewer
			new SubTexture(32, 33, 2, 2, 16, 4),
			new SubTexture(34, 33, 2, 2, 16, 4),
			new SubTexture(36, 33, 2, 2, 16, 4),
			new SubTexture(38, 33, 2, 2, 16, 4),
			new SubTexture(40, 33, 2, 2, 16, 4),
			new SubTexture(42, 33, 2, 2, 16, 4),
			new SubTexture(44, 33, 2, 2, 16, 4),
			new SubTexture(46, 33, 2, 2, 16, 4),
			new SubTexture(48, 33, 2, 2, 16, 4),
			new SubTexture(50, 33, 2, 2, 16, 4),
			new SubTexture(52, 33, 2, 2, 16, 4),
			new SubTexture(54, 33, 2, 2, 16, 4),
			new SubTexture(56, 33, 2, 2, 16, 4),
			new SubTexture(58, 33, 2, 2, 16, 4),
			new SubTexture(60, 33, 2, 2, 16, 4),
			new SubTexture(62, 33, 2, 2, 16, 4),
			
			// Orange
			new SubTexture(32, 35, 2, 2, 16, 4),
			new SubTexture(34, 35, 2, 2, 16, 4),
			new SubTexture(36, 35, 2, 2, 16, 4),
			new SubTexture(38, 35, 2, 2, 16, 4),
			new SubTexture(40, 35, 2, 2, 16, 4),
			new SubTexture(42, 35, 2, 2, 16, 4),
			new SubTexture(44, 35, 2, 2, 16, 4),
			new SubTexture(46, 35, 2, 2, 16, 4),
			new SubTexture(48, 35, 2, 2, 16, 4),
			new SubTexture(50, 35, 2, 2, 16, 4),
			new SubTexture(52, 35, 2, 2, 16, 4),
			new SubTexture(54, 35, 2, 2, 16, 4),
			new SubTexture(56, 35, 2, 2, 16, 4),
			new SubTexture(58, 35, 2, 2, 16, 4),
			new SubTexture(60, 35, 2, 2, 16, 4),
			new SubTexture(62, 35, 2, 2, 16, 4),
			
			// Blue
			new SubTexture(32, 37, 2, 2, 16, 4),
			new SubTexture(34, 37, 2, 2, 16, 4),
			new SubTexture(36, 37, 2, 2, 16, 4),
			new SubTexture(38, 37, 2, 2, 16, 4),
			new SubTexture(40, 37, 2, 2, 16, 4),
			new SubTexture(42, 37, 2, 2, 16, 4),
			new SubTexture(44, 37, 2, 2, 16, 4),
			new SubTexture(46, 37, 2, 2, 16, 4),
			new SubTexture(48, 37, 2, 2, 16, 4),
			new SubTexture(50, 37, 2, 2, 16, 4),
			new SubTexture(52, 37, 2, 2, 16, 4),
			new SubTexture(54, 37, 2, 2, 16, 4),
			new SubTexture(56, 37, 2, 2, 16, 4),
			new SubTexture(58, 37, 2, 2, 16, 4),
			new SubTexture(60, 37, 2, 2, 16, 4),
			new SubTexture(62, 37, 2, 2, 16, 4),
			
			// Hangar
			new SubTexture(32, 39, 2, 2, 16, 4),
			new SubTexture(34, 39, 2, 2, 16, 4),
			new SubTexture(36, 39, 2, 2, 16, 4),
			new SubTexture(38, 39, 2, 2, 16, 4),
			new SubTexture(40, 39, 2, 2, 16, 4),
			new SubTexture(42, 39, 2, 2, 16, 4),
			new SubTexture(44, 39, 2, 2, 16, 4),
			new SubTexture(46, 39, 2, 2, 16, 4),
			new SubTexture(48, 39, 2, 2, 16, 4),
			new SubTexture(50, 39, 2, 2, 16, 4),
			new SubTexture(52, 39, 2, 2, 16, 4),
			new SubTexture(54, 39, 2, 2, 16, 4),
			new SubTexture(56, 39, 2, 2, 16, 4),
			new SubTexture(58, 39, 2, 2, 16, 4),
			new SubTexture(60, 39, 2, 2, 16, 4),
			new SubTexture(62, 33, 2, 2, 16, 4),
	};
	
	public SubTexture wideDoorWhite = new SubTexture(11, 0, 1, 1);
	
	public SubTexture floors[] = {
			new SubTexture(0, 2, 1, 1),
			new SubTexture(1, 2, 1, 1),
			new SubTexture(2, 2, 1, 1),
			new SubTexture(3, 2, 1, 1),
			new SubTexture(4, 2, 1, 1),
			new SubTexture(5, 2, 1, 1),
			new SubTexture(6, 2, 1, 1),
			new SubTexture(7, 2, 1, 1),
			new SubTexture(8, 2, 1, 1),
			new SubTexture(9, 2, 1, 1),
			new SubTexture(10, 2, 1, 1),
			new SubTexture(11, 2, 1, 1),
			new SubTexture(12, 2, 1, 1),
			new SubTexture(13, 2, 1, 1),
			new SubTexture(14, 2, 1, 1),
			new SubTexture(15, 2, 1, 1)
	};
	
	public SubTexture laserPistol[] = {
			new SubTexture(0, 32, 3, 4),
			new SubTexture(3, 32, 3, 4)
	};
	
	public SubTexture forceWall[] = {
			new SubTexture(0, 1, 1, 1),
			new SubTexture(1, 1, 1, 1),
			new SubTexture(2, 1, 1, 1)
	};

	public SubTexture laserBarrierWall[] = {
			new SubTexture(3, 1, 1, 1),
			new SubTexture(4, 1, 1, 1),
			new SubTexture(5, 1, 1, 1),
			new SubTexture(6, 1, 1, 1)
	};
	
	public SubTexture glassWall = new SubTexture(7, 1, 1, 1);
	public SubTexture[] shetteredGlass = new SubTexture[] {
		new SubTexture(8, 1, 1, 1),
		new SubTexture(9, 1, 1, 1)
	};

	public SubTexture laserBarrierFloor = new SubTexture(2, 2, 1, 1);
	
	public SubTexture slimeMinus90[] = {
			new SubTexture(32, 2, 1, 1),
			new SubTexture(33, 2, 1, 1),
			new SubTexture(34, 2, 1, 1),
	};
	
	public SubTexture slimeMinus60[] = {
			new SubTexture(35, 2, 1, 1),
			new SubTexture(36, 2, 1, 1),
			new SubTexture(37, 2, 1, 1),
	};
	
	public SubTexture slimeMinus30[] = {
			new SubTexture(38, 2, 1, 1),
			new SubTexture(39, 2, 1, 1),
			new SubTexture(40, 2, 1, 1),
	};
	
	public SubTexture slimeFront[] = {
			new SubTexture(41, 2, 1, 1),
			new SubTexture(42, 2, 1, 1),
			new SubTexture(43, 2, 1, 1),
	};
	
	public SubTexture slimePlus30[] = {
			new SubTexture(44, 2, 1, 1),
			new SubTexture(45, 2, 1, 1),
			new SubTexture(46, 2, 1, 1),
	};
	
	public SubTexture slimePlus60[] = {
			new SubTexture(47, 2, 1, 1),
			new SubTexture(48, 2, 1, 1),
			new SubTexture(49, 2, 1, 1),
	};
	
	public SubTexture slimePlus90[] = {
			new SubTexture(50, 2, 1, 1),
			new SubTexture(51, 2, 1, 1),
			new SubTexture(52, 2, 1, 1),
	};
	
	public SubTexture slimeBack[] = {
			new SubTexture(53, 2, 1, 1),
			new SubTexture(54, 2, 1, 1),
			new SubTexture(55, 2, 1, 1),
	};
	
	public SubTexture slimeGibs[] = {
			new SubTexture(61, 2, 1, 1),
			new SubTexture(62, 2, 1, 1),
			new SubTexture(63, 2, 1, 1)
	};
	
	public SubTexture secureBotMinus90[] = {
			new SubTexture(32, 0, 1, 2),
			new SubTexture(33, 0, 1, 2),
			new SubTexture(34, 0, 1, 2),
	};
	
	public SubTexture secureBotMinus60[] = {
			new SubTexture(35, 0, 1, 2),
			new SubTexture(36, 0, 1, 2),
			new SubTexture(37, 0, 1, 2),
	};
	
	public SubTexture secureBotMinus30[] = {
			new SubTexture(38, 0, 1, 2),
			new SubTexture(39, 0, 1, 2),
			new SubTexture(40, 0, 1, 2),
	};
	
	public SubTexture secureBotFront[] = {
			new SubTexture(41, 0, 1, 2),
			new SubTexture(42, 0, 1, 2),
			new SubTexture(43, 0, 1, 2),
	};
	
	public SubTexture secureBotPlus30[] = {
			new SubTexture(44, 0, 1, 2),
			new SubTexture(45, 0, 1, 2),
			new SubTexture(46, 0, 1, 2),
	};
	
	public SubTexture secureBotPlus60[] = {
			new SubTexture(47, 0, 1, 2),
			new SubTexture(48, 0, 1, 2),
			new SubTexture(49, 0, 1, 2),
	};
	
	public SubTexture secureBotPlus90[] = {
			new SubTexture(50, 0, 1, 2),
			new SubTexture(51, 0, 1, 2),
			new SubTexture(52, 0, 1, 2),
	};
	
	public SubTexture secureBotBack[] = {
			new SubTexture(53, 0, 1, 2),
			new SubTexture(54, 0, 1, 2),
			new SubTexture(55, 0, 1, 2),
	};
	
	public SubTexture secureBotGibs[] = {
			new SubTexture(61, 0, 1, 1),
			new SubTexture(62, 0, 1, 1),
			new SubTexture(63, 0, 1, 1)
	};
	
	public SubTexture turretMinus135[] = {
			new SubTexture(32, 3, 2, 2)
	};
	
	public SubTexture turretMinus90[] = {
			new SubTexture(34, 3, 2, 2)
	};
	
	public SubTexture turretMinus45[] = {
			new SubTexture(36, 3, 2, 2)
	};
	
	public SubTexture turretFront[] = {
			new SubTexture(38, 3, 2, 2)
	};
	
	public SubTexture turretPlus45[] = {
			new SubTexture(40, 3, 2, 2)
	};
	
	public SubTexture turretPlus90[] = {
			new SubTexture(42, 3, 2, 2)
	};
	
	public SubTexture turretPlus135[] = {
			new SubTexture(44, 3, 2, 2)
	};
	
	public SubTexture turretBack[] = {
			new SubTexture(46, 3, 2, 2)
	};
	
	public SubTexture turretGibs[] = {
			new SubTexture(61, 3, 1, 1),
			new SubTexture(62, 3, 1, 1),
			new SubTexture(63, 3, 1, 1)
	};
	
	public SubTexture laserBeam = new SubTexture(0, 4, 1, 1);
	public SubTexture laserParticle = new SubTexture(1, 4, 1, 1);
	public SubTexture barrelGreen = new SubTexture(2, 4, 1, 1);
	public SubTexture greenBubble = new SubTexture(3, 4, 1, 1);
	public SubTexture barrelRed = new SubTexture(4, 4, 1, 1);

	
	public SubTexture joystickButton = new SubTexture(0, 10, 1, 1);
	public SubTexture lightIndicator = new SubTexture(1, 10, 1, 1);
	public SubTexture laserHitDecal = new SubTexture(0, 12, 1, 1);
	
	public SubTexture[] fire = new SubTexture[] {
			new SubTexture(0, 14, 1, 1),
			new SubTexture(1, 14, 1, 1),
			new SubTexture(2, 14, 1, 1),
			new SubTexture(3, 14, 1, 1)
			
	};
	
	public SubTexture[] explosion = new SubTexture[] {
			new SubTexture(0, 15, 1, 1),
			new SubTexture(1, 15, 1, 1),
			new SubTexture(2, 15, 1, 1),
			new SubTexture(3, 15, 1, 1),
			new SubTexture(4, 15, 1, 1),
			new SubTexture(5, 15, 1, 1),
			new SubTexture(6, 15, 1, 1),
			new SubTexture(7, 15, 1, 1)
	};

	// Pickups
	public SubTexture laserPistolPickup = new SubTexture(0, 20, 1, 1);
	public SubTexture laserAmmoPickup = new SubTexture(1, 20, 1, 1);
	public SubTexture healthPickUp = new SubTexture(2, 20, 1, 1);
	public SubTexture redKeyPickUp = new SubTexture(3, 20, 1, 1);
	public SubTexture greenKeyPickUp = new SubTexture(4, 20, 1, 1);
	public SubTexture blueKeyPickUp = new SubTexture(5, 20, 1, 1);

	public SubTexture diamondPickUp = new SubTexture(6, 20, 1, 1);
	public SubTexture coinPickUp = new SubTexture(7, 20, 1, 1);
	
	public SubTexture particle0 = new SubTexture(0, 21, 1, 1);
	public SubTexture particle1 = new SubTexture(1, 21, 1, 1);
	public SubTexture particle2 = new SubTexture(2, 21, 1, 1);

	public SubTexture limbGib = new SubTexture(0, 19, 1, 1);
	public SubTexture head1Gib = new SubTexture(1, 19, 1, 1);
	public SubTexture head2Gib = new SubTexture(2, 19, 1, 1);
	public SubTexture armGib = new SubTexture(3, 19, 1, 1);

	public SubTexture[] marsStones = new SubTexture[] {
		new SubTexture(0, 18, 1, 1),
		new SubTexture(1, 18, 1, 1),
		new SubTexture(2, 18, 1, 1)
	};
	
	public SubTexture[] marsRocks = new SubTexture[] {
		new SubTexture(3, 17, 3, 2),
		new SubTexture(6, 17, 3, 2)
	};
	
	public SubTexture wallRocks = new SubTexture(0, 16, 3, 2);

	public SubTexture spaceship = new SubTexture(10, 32, 5, 3);
	
	public SubTexture poster0 = new SubTexture(37, 32, 3, 4);

	
	// Decals
	public SubTexture buttonSwitch[] = {
			new SubTexture(0, 3, 1, 1),
			new SubTexture(1, 3, 1, 1)
	};
	
	public SubTexture terminal[] = {
			new SubTexture(2, 3, 1, 1),
			new SubTexture(3, 3, 1, 1),
			new SubTexture(4, 3, 1, 1)
	};
	
	public SubTexture moon = new SubTexture(11, 3, 2, 2);
	
	// Hud
	public SubTexture hudBackground = new SubTexture(0, 27, 1, 1);
	public SubTexture hudButton = new SubTexture(1, 27, 1, 1);
	public SubTexture hudBackButton = new SubTexture(3, 27, 1, 1);
	public SubTexture hudLaserBar = new SubTexture(2, 27, 1, 1);

	public GameTextureMap(Texture tex) {
		super(tex, 16);
		// TODO Auto-generated constructor stub
	}
}
