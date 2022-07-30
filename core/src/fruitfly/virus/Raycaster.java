package fruitfly.virus;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.tiles.DoorTile;
import fruitfly.virus.tiles.Tile;
import fruitfly.virus.tiles.TransparentTile;
import fruitfly.virus.tiles.VoidTile;
import fruitfly.virus.tiles.Wall;
import fruitfly.virus.tiles.WallTile;

public class Raycaster {
		
	private final float fovx;
	private final int screenColumns;
	private final float anglePerScreenColumn;
	
	private static final int MAX_VIS_TILES = 1000;
	private static final int MAX_VIS_SPECIAL_TILES = 20;
	private static final int MAX_VIS_TRANSPARENT_TILES = 200;

	private static final int MAX_VIS_WALLS = 2000;
	
	public Tile[] visibleTiles = new Tile[MAX_VIS_TILES];
	public int visibleTileCount = 0;
	public Wall[] visibleWalls = new Wall[MAX_VIS_WALLS];
	public int visibleWallCount = 0;
	public TransparentTile[] transparentTiles = new TransparentTile[MAX_VIS_TRANSPARENT_TILES];
	public int transparentTileCount = 0;
	
	public Tile selectedTile = null;
	public Wall selectedWall = null;
	
	public List<Tile> debugTiles = new LinkedList<Tile>();
	
	public class RaycastResult {
		Tile tile;
		Wall wall;
		Vector2 wallCoordinates;
		
		public RaycastResult(Tile tile) {
			this.tile = tile;
		}
	}
	
	public Raycaster(float fovx, int screenColumns) {
		this.fovx = fovx;
		this.screenColumns = screenColumns;
		this.anglePerScreenColumn = (this.fovx)  / screenColumns;
	}
	
	public void calculateVisibleTilesAndWalls(World level, Vector2 position, float angle) {
		
		for (int i=0; i<visibleTileCount; i++) {
			visibleTiles[i].resetVisibility();
			visibleTiles[i] = null;
		}
		visibleTileCount = 0;

		for (int i=0; i<transparentTileCount; i++) {
			transparentTiles[i].resetVisibility();
			transparentTiles[i] = null;
		}
		transparentTileCount = 0;

		for (int i=0; i<visibleWallCount; i++) {
			visibleWalls[i].setVisible(false);
			visibleWalls[i] = null;
		}
		visibleWallCount = 0;

		for (int x=0; x<G.world.getWidth(); x++) {
			for (int y=0; y<G.world.getHeight(); y++) {
				Tile tile = G.world.getTile(x, y);
				if (tile != null) {
					addVisibleTile(G.world, tile);

					if (tile instanceof WallTile) {
						WallTile wt = (WallTile) tile;
						wt.south.setVisible(true);
						wt.west.setVisible(true);
						wt.north.setVisible(true);
						wt.east.setVisible(true);
						visibleWalls[visibleWallCount++] = wt.south;
						visibleWalls[visibleWallCount++] = wt.west;
						visibleWalls[visibleWallCount++] = wt.north;
						visibleWalls[visibleWallCount++] = wt.east;
					}
				}
			}
		}
	}

	public void calculateVisibleTilesAndWalls_REAL(World level, Vector2 position, float angle) {

		for (int i=0; i<visibleTileCount; i++) {
			visibleTiles[i].resetVisibility();
			visibleTiles[i] = null;
		}
		visibleTileCount = 0;

		for (int i=0; i<transparentTileCount; i++) {
			transparentTiles[i].resetVisibility();
			transparentTiles[i] = null;
		}
		transparentTileCount = 0;

		for (int i=0; i<visibleWallCount; i++) {
			visibleWalls[i].setVisible(false);
			visibleWalls[i] = null;
		}
		visibleWallCount = 0;

		selectedTile = null;
		selectedWall = null;

		for (int i=0; i<screenColumns; i++) {
			float j = i - screenColumns/2 + 0.5f;

			float phi = angle + j * anglePerScreenColumn;

			Wall hitWall = cast(level, position, phi, i == screenColumns/2);
			if (hitWall != null && !hitWall.isVisible()) {
				hitWall.setVisible(true);
				visibleWalls[visibleWallCount++] = hitWall;
			}
		}

		// Also add tile I am standing on
		Tile t = G.world.getTile((int)position.x, (int)position.y);
		if (t != null && !t.isVisible()) {
			addVisibleTile(G.world, t);
		}
	}
	
	// http://lodev.org/cgtutor/raycasting.html
	// faster then my own one...
	public Wall cast(World world, Vector2 position, float angle, boolean isCenterRay) {		
		
		angle = angle * MathUtils.degreesToRadians;
		
		angle = MUtil.normalizeAngle(angle);
		
		boolean right = false, up = false;
		if (angle >= 1.5f * MathUtils.PI || angle < 0.5f * MathUtils.PI) {
			right = true;
		}
		if (angle >= 0 && angle < MathUtils.PI) {
			up = true;
		}
		
		float rayPosX = position.x;
		float rayPosY = position.y;
		
		float rayDirX = MathUtils.cos(angle);
		float rayDirY = MathUtils.sin(angle);
		
		//which box of the map we're in  
		int mapX = (int)rayPosX;
		int mapY = (int)rayPosY;
       
		//length of ray from current position to next x or y-side
		double sideDistX;
		double sideDistY;
       
		//length of ray from one x or y-side to next x or y-side
		float deltaDistX = (float) Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
		float deltaDistY = (float) Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
		float perpWallDist;
       
		//what direction to step in x or y-direction (either +1 or -1)
		int stepX;
		int stepY;

		int hit = 0; //was there a wall hit?
		int side = 0; //was a NS or a EW wall hit?
      
		//calculate step and initial sideDist
		if (rayDirX < 0)
		{
			stepX = -1;
			sideDistX = (rayPosX - mapX) * deltaDistX;
		}
		else
		{
			stepX = 1;
			sideDistX = (mapX + 1.0 - rayPosX) * deltaDistX;
		}
		if (rayDirY < 0)
		{
			stepY = -1;
			sideDistY = (rayPosY - mapY) * deltaDistY;
		}
		else
		{
			stepY = 1;
			sideDistY = (mapY + 1.0 - rayPosY) * deltaDistY;
		}
      
		Tile t = null;
		Wall wall = null;
		//perform DDA
		while (hit == 0 && mapX >= 0 && mapX < world.getWidth() && mapY >= 0 && mapY < world.getHeight())
		{
			//jump to next map square, OR in x-direction, OR in y-direction
			if (sideDistX < sideDistY)
			{
				sideDistX += deltaDistX;
				mapX += stepX;
				side = 0;
			}
			else
			{
				sideDistY += deltaDistY;
				mapY += stepY;
				side = 1;
			}
			//Check if ray has hit a wall
			t = world.getTile(mapX, mapY);
			if (t == null) {

			}
			else {
				if (t.isBlockingVisibility()) {
					hit = 1;
				}
				
				if (!t.isVisible()) {
					addVisibleTile(world, t);
				}
				
				if (t instanceof WallTile) {
					WallTile wt = (WallTile) t;
					if (side == 1 && up) {
						wall = wt.south;
					}
					else if (side == 1 && !up) {
						wall = wt.north;
					}
					else if (side == 0 && right) {
						wall = wt.west;
					}
					else if (side == 0 && !right) {
						wall = wt.east;
					}
					
					if (isCenterRay && selectedWall == null) selectedWall = wall; 
				}
				
				if (isCenterRay && selectedTile == null && !(t instanceof VoidTile)) selectedTile = t;

			}
		} 
		
		//Calculate distance projected on camera direction (oblique distance will give fisheye effect!)
		if (side == 0) {
			perpWallDist = Math.abs((mapX - rayPosX + (1 - stepX) / 2) / rayDirX);
		}
		else {
			perpWallDist = Math.abs((mapY - rayPosY + (1 - stepY) / 2) / rayDirY);
		}
		
		return wall;
	}
	
	public boolean isLineOfSight(World world, Vector2 position, Vector2 position2) {		

		float angle = MathUtils.atan2(position2.y - position.y, position2.x - position.x);
		
		angle = MUtil.normalizeAngle(angle);
		boolean right = false, up = false;
		if (angle >= 1.5f * MathUtils.PI || angle < 0.5f * MathUtils.PI) {
			right = true;
		}
		if (angle >= 0 && angle < MathUtils.PI) {
			up = true;
		}
		
		float rayPosX = position.x;
		float rayPosY = position.y;
		
		float rayDirX = MathUtils.cos(angle);
		float rayDirY = MathUtils.sin(angle);
		
		//which box of the map we're in  
		int mapX = (int)rayPosX;
		int mapY = (int)rayPosY;
       
		//length of ray from current position to next x or y-side
		double sideDistX;
		double sideDistY;
       
		//length of ray from one x or y-side to next x or y-side
		float deltaDistX = (float) Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
		float deltaDistY = (float) Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
       
		//what direction to step in x or y-direction (either +1 or -1)
		int stepX;
		int stepY;

		int hit = 0; //was there a wall hit?
      
		//calculate step and initial sideDist
		if (rayDirX < 0)
		{
			stepX = -1;
			sideDistX = (rayPosX - mapX) * deltaDistX;
		}
		else
		{
			stepX = 1;
			sideDistX = (mapX + 1.0 - rayPosX) * deltaDistX;
		}
		if (rayDirY < 0)
		{
			stepY = -1;
			sideDistY = (rayPosY - mapY) * deltaDistY;
		}
		else
		{
			stepY = 1;
			sideDistY = (mapY + 1.0 - rayPosY) * deltaDistY;
		}
      
		Tile t = null;

		//perform DDA
		while (hit == 0 && mapX >= 0 && mapX < world.getWidth() && mapY >= 0 && mapY < world.getHeight())
		{
			//jump to next map square, OR in x-direction, OR in y-direction
			if (sideDistX < sideDistY)
			{
				sideDistX += deltaDistX;
				mapX += stepX;
				
				if (right && mapX >= position2.x) {
					return true;
				}
				else if (!right && (mapX + 1) <= position2.x) {
					return true;
				}
			}
			else
			{
				sideDistY += deltaDistY;
				mapY += stepY;
				
				if (up && mapY >= position2.y) {
					return true;
				}
				else if (!up && (mapY + 1) <= position2.y + 1) {
					return true;
				}
			}

			// Check if we are past our reference point

			//Check if ray has hit a wall
			t = world.getTile(mapX, mapY);
			if (t == null) {

			}
			else {
				if (t.isBlockingVisibility()) {
					return false;
				}
			}
		} 
		return true;
	}
	
	public boolean isTileVisible(World world, Vector3 position, float angle, Tile tile, float fov, float samples) {
		float angleStep = fov/samples;
		float curAngle = angle - fov/2.0f + angleStep/2.0f;
		for (int i=0; i<samples; i++) {
			if (isTileVisible(world, position, MUtil.normalizeAngleDeg(curAngle), tile)) return true;
			curAngle += angleStep;
		}
		return false;
	}
	
	public boolean isTileVisible(World world, Vector3 position, float angle, Tile tile) {		
		
		angle = MUtil.normalizeAngle(angle * MathUtils.degreesToRadians);
		
		boolean right = false, up = false;
		if (angle >= 1.5f * MathUtils.PI || angle < 0.5f * MathUtils.PI) {
			right = true;
		}
		if (angle >= 0 && angle < MathUtils.PI) {
			up = true;
		}
		
		float rayPosX = position.x;
		float rayPosY = position.y;
		
		float rayDirX = MathUtils.cos(angle);
		float rayDirY = MathUtils.sin(angle);
		
		//which box of the map we're in  
		int mapX = (int)rayPosX;
		int mapY = (int)rayPosY;
       
		//length of ray from current position to next x or y-side
		double sideDistX;
		double sideDistY;
       
		//length of ray from one x or y-side to next x or y-side
		float deltaDistX = (float) Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
		float deltaDistY = (float) Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
       
		//what direction to step in x or y-direction (either +1 or -1)
		int stepX;
		int stepY;

		//calculate step and initial sideDist
		if (rayDirX < 0)
		{
			stepX = -1;
			sideDistX = (rayPosX - mapX) * deltaDistX;
		}
		else
		{
			stepX = 1;
			sideDistX = (mapX + 1.0 - rayPosX) * deltaDistX;
		}
		if (rayDirY < 0)
		{
			stepY = -1;
			sideDistY = (rayPosY - mapY) * deltaDistY;
		}
		else
		{
			stepY = 1;
			sideDistY = (mapY + 1.0 - rayPosY) * deltaDistY;
		}
      
		Tile t = null;
		//perform DDA
		while (mapX >= 0 && mapX < world.getWidth() && mapY >= 0 && mapY < world.getHeight())
		{
			//jump to next map square, OR in x-direction, OR in y-direction
			if (sideDistX < sideDistY)
			{
				sideDistX += deltaDistX;
				mapX += stepX;
			}
			else
			{
				sideDistY += deltaDistY;
				mapY += stepY;
			}
			//Check if ray has hit the tile
			t = world.getTile(mapX, mapY);
			if (C.isDebug && t != null) debugTiles.add(t);
			
			if (t == null) {
				continue;
			}
			else if (tile == t) {
				return true;
			}
			else if (t.isBlockingVisibility()) {
				return false;
			}
		} 
		
		return false;
	}
	
	private void addVisibleTile(World world, Tile t) {
		t.setVisible();
		visibleTiles[visibleTileCount++] = t;
		
		if (!(t instanceof WallTile) && !(t instanceof VoidTile)) {
			if (t instanceof TransparentTile) {
				transparentTiles[transparentTileCount++] = (TransparentTile) t;
			}
			else if (t instanceof DoorTile) {
				DoorTile dt = (DoorTile) t;
				if (dt.horizontal) {
					WallTile east =  (WallTile) world.getTile(t.x+1, t.y);
					WallTile west =  (WallTile) world.getTile(t.x-1, t.y);
					
					visibleTiles[visibleTileCount++] = east;
					visibleWalls[visibleWallCount++] = east.west;
					visibleTiles[visibleTileCount++] = west;
					visibleWalls[visibleWallCount++] = west.east;
				}
				else {
					// Vertical Door, so show northern and southern walls/tiles
					WallTile north =  (WallTile) world.getTile(t.x, t.y+1);
					WallTile south =  (WallTile) world.getTile(t.x, t.y-1);
					
					visibleTiles[visibleTileCount++] = north;
					visibleWalls[visibleWallCount++] = north.south;
					visibleTiles[visibleTileCount++] = south;
					visibleWalls[visibleWallCount++] = south.north;
				}
			}
		}
	}
}
