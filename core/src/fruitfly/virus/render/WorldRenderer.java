package fruitfly.virus.render;

import java.nio.FloatBuffer;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.VertexBufferObject;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.C;
import fruitfly.virus.G;
import fruitfly.virus.MUtil;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.IEntity;
import fruitfly.virus.entities.IPointSpriteEntity;
import fruitfly.virus.entities.ISpriteEntity;
import fruitfly.virus.entities.decals.Decal;
import fruitfly.virus.entities.light.ILight;
import fruitfly.virus.entities.npc.NPC;
import fruitfly.virus.particles.Particle;
import fruitfly.virus.particles.ParticleSystem;
import fruitfly.virus.pickups.PickUp;
import fruitfly.virus.tiles.BlockingEntityTile;
import fruitfly.virus.tiles.DoorTile;
import fruitfly.virus.tiles.Tile;
import fruitfly.virus.tiles.TransparentTile;
import fruitfly.virus.tiles.VoidTile;
import fruitfly.virus.tiles.Wall;
import fruitfly.virus.tiles.WallTile;

public class WorldRenderer {
	
	private ShaderProgram surfaceShader;
	private ShaderProgram entityShader;
	private ShaderProgram pointSpriteTextureShader;
	private ShaderProgram pointSpriteColorShader;
	private ShaderProgram lineShader;
	private ShaderProgram shadowShader;

	private VertexBufferObject surfaceVBO;
	private VertexBufferObject entityVBO;
	private VertexBufferObject pointSpriteTextureVBO;
	private VertexBufferObject pointSpriteColorVBO;
	private VertexBufferObject lineVBO;
	private VertexBufferObject shadowVBO;

	static final int NUM_FLOATS_PER_VERTEX = 3 + 2;
	static final int NUM_VERTICIES_PER_WALL = 6;
	static final int NUM_FLOATS_PER_WALL = NUM_FLOATS_PER_VERTEX * NUM_VERTICIES_PER_WALL;
	
	private boolean forceVisibilityRecalculation = true;
	
	public static float[] texCoords0 = new float[8];
	public static float[] texCoords1 = new float[8];

	public static float[] normalNorth = new float[] {0.0f, 1.0f, 0.0f};
	public static float[] normalSouth = new float[] {0.0f, -1.0f, 0.0f};
	public static float[] normalEast = new float[] {1.0f, 0.0f, 0.0f};
	public static float[] normalWest = new float[] {-1.0f, 0.0f, 0.0f};
	public static float[] normalUp = new float[] {0.0f, 0.0f, 1.0f};

	public WorldRenderer() {
		this.surfaceVBO = new VertexBufferObject(false, 6 * 4 * 500, VertexAttribute.Position(), VertexAttribute.TexCoords(0), VertexAttribute.TexCoords(1), VertexAttribute.Normal());
		this.entityVBO = new VertexBufferObject(false, 6 * 4 * 50, VertexAttribute.Position(), VertexAttribute.TexCoords(0));
		this.pointSpriteTextureVBO = new VertexBufferObject(false, 200, VertexAttribute.Position(), new VertexAttribute(9, 4, "a_color"), new VertexAttribute(10, 1, "a_size"), new VertexAttribute(11, 1, "a_distance"), new VertexAttribute(12, 1, "a_s"), new VertexAttribute(13, 1, "a_t"));
		this.pointSpriteColorVBO = new VertexBufferObject(false, 200, VertexAttribute.Position(), new VertexAttribute(9, 4, "a_color"), new VertexAttribute(10, 1, "a_size"), new VertexAttribute(11, 1, "a_distance"));
		this.lineVBO = new VertexBufferObject(false, 100, VertexAttribute.Position());
		this.shadowVBO = new VertexBufferObject(false, 6 * 4, VertexAttribute.Position());
		compileShaders(true);
	}

	public void compileShaders(boolean initial) {
		ShaderProgram _dynamicLightShader = new ShaderProgram(Gdx.files.internal("data/shaders/surface.vsh"), Gdx.files.internal("data/shaders/surface.fsh"));
		if (!_dynamicLightShader.isCompiled()) {
			if (initial) throw new RuntimeException("Shader not compiled: " + _dynamicLightShader.getLog());
			else System.out.println("Shader not compiled: " + _dynamicLightShader.getLog());
		}
		else {
			System.out.println(">>> Shader dynamicLightShader reloaded.");
			surfaceShader = _dynamicLightShader;
		}

		ShaderProgram _entityShader = new ShaderProgram(Gdx.files.internal("data/shaders/entity.vsh"), Gdx.files.internal("data/shaders/entity.fsh"));
		if (!_entityShader.isCompiled()) {
			if (initial) throw new RuntimeException("Shader not compiled: " + _entityShader.getLog());
			else System.out.println("Shader not compiled: " + _entityShader.getLog());
		}
		else {
			System.out.println(">>> Shader colorShader reloaded.");
			entityShader = _entityShader;
		}

		ShaderProgram _pointSpriteTextureShader = new ShaderProgram(Gdx.files.internal("data/shaders/pointsprite.texture.vsh"), Gdx.files.internal("data/shaders/pointsprite.texture.fsh"));
		if (!_pointSpriteTextureShader.isCompiled()) {
			if (initial) throw new RuntimeException("Shader not compiled: " + _pointSpriteTextureShader.getLog());
			else System.out.println("Shader not compiled: " + _pointSpriteTextureShader.getLog());
		}
		else {
			System.out.println(">>> Shader pointSpriteTextureShader reloaded.");
			pointSpriteTextureShader = _pointSpriteTextureShader;
		}

		ShaderProgram _pointSpriteColorShader = new ShaderProgram(Gdx.files.internal("data/shaders/pointsprite.color.vsh"), Gdx.files.internal("data/shaders/pointsprite.color.fsh"));
		if (!_pointSpriteColorShader.isCompiled()) {
			if (initial) throw new RuntimeException("Shader not compiled: " + _pointSpriteColorShader.getLog());
			else System.out.println("Shader not compiled: " + _pointSpriteColorShader.getLog());
		}
		else {
			System.out.println(">>> Shader pointSpriteColorShader reloaded.");
			pointSpriteColorShader = _pointSpriteColorShader;
		}

		ShaderProgram _lineShader = new ShaderProgram(Gdx.files.internal("data/shaders/lines.vsh"), Gdx.files.internal("data/shaders/lines.fsh"));
		if (!_lineShader.isCompiled()) {
			if (initial) throw new RuntimeException("Shader not compiled: " + _lineShader.getLog());
			else System.out.println("Shader not compiled: " + _lineShader.getLog());
		}
		else {
			System.out.println(">>> Shader lineShader reloaded.");
			lineShader = _lineShader;
		}

		ShaderProgram _shadowShader = new ShaderProgram(Gdx.files.internal("data/shaders/shadow.vsh"), Gdx.files.internal("data/shaders/shadow.fsh"));
		if (!_shadowShader.isCompiled()) {
			if (initial) throw new RuntimeException("Shader not compiled: " + _shadowShader.getLog());
			else System.out.println("Shader not compiled: " + _shadowShader.getLog());
		}
		else {
			System.out.println(">>> Shader shadowShader reloaded.");
			shadowShader = _shadowShader;
		}

	}
	
	public void render() {
		Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL20.GL_CULL_FACE);
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc (GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl20.glEnable(GL20.GL_VERTEX_PROGRAM_POINT_SIZE);
		Gdx.gl20.glEnable(0x8861); // GL11.GL_POINT_SPRITE_OES; Important but no idea why needed...
		Gdx.gl.glPolygonOffset(-1.0f, -2.0f);

		Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);

		if (G.game.ticker % 60 == 0) G.world.getLightmap().bake();
		
		renderWorld();
		
		renderDecals();
		renderEntities();
		renderPickUps();
		renderTileResidentParticles();
		renderFadingParticles();
		renderTransparentTiles();
		//if (C.isDebug) renderDebug();
		//if (C.isDebug) renderDebug2();
	}
	
	private static final int MAX_VIS_LIGHTS = 10;
	public ILight[] visibleLights = new ILight[MAX_VIS_LIGHTS];
	public int visibleLightCount = 0;
	
	static float[] lightPositions = new float[MAX_VIS_LIGHTS * 3];
	static float[] lightColors = new float[MAX_VIS_LIGHTS * 3];
	static float[] lightIntensities = new float[MAX_VIS_LIGHTS];

	public void forceVisibilityRecalculation() {
		this.forceVisibilityRecalculation = true;
	}

	private void renderWorld() {
		if (forceVisibilityRecalculation) {
			forceVisibilityRecalculation = false;
			G.raycaster.calculateVisibleTilesAndWalls(G.world, new Vector2(G.player.position.x, G.player.position.y), G.player.rotation);
			G.statVisisbleTiles = G.raycaster.visibleTileCount;
			G.statVisisbleWalls = G.raycaster.visibleWallCount;
			G.statTransparentTiles = G.raycaster.transparentTileCount;
		}
		
		G.statVisibleEntities = 0;
		G.statVisisbleDecals = 0;
		
		surfaceShader.begin();

		G.textureMap.texture.bind(0);
		G.world.getLightmap().getTexture().bind(1);
		
		surfaceShader.setUniformMatrix("u_modelViewMatrix", G.camera.camera.view);
		surfaceShader.setUniformMatrix("u_projectionMatrix", G.camera.camera.projection);
		surfaceShader.setUniformi("u_texture", 0);
		surfaceShader.setUniformi("u_lightmap", 1);
		
		// Calculate visible lights
		// We don't iterate over visibleTiles but over all lights because...
		//  - We need to do this every frame even if standing (moving lights); so, raytracer optimization from above cannot be turned on
		//  - We need to also enable lights that are "close to" visible due to effect on surrounding tiles
		
		for (int i=0; i<visibleLightCount; i++) {
			visibleLights[i] = null;
		}
		visibleLightCount = 0;

		for (ILight l : G.world.entityManager.getLights()) {
			int xPosLight = (int) l.getLightPosition().x;
			int yPosLight = (int) l.getLightPosition().y;
			
			if (isVicinityVisible(xPosLight, yPosLight, 2)) {
				if (visibleLightCount+1 < MAX_VIS_LIGHTS) {
					visibleLights[visibleLightCount++] = l;
				}
				else {
					//System.out.println("*** LIGHT LIMIT REACHED ***");
				}
			}
		}
		
		G.statVisibleLights = this.visibleLightCount;
		
		// Render Walls
		FloatBuffer fb = surfaceVBO.getBuffer();
		fb.position(0);
		fb.limit(fb.capacity());
		
		for (int i=0; i<G.raycaster.visibleWallCount; i++) {
			Wall w = G.raycaster.visibleWalls[i];
			w.writeVertexData(fb, false);
		}
		
		surfaceVBO.bind(surfaceShader);
		Gdx.gl20.glDrawArrays(GL20.GL_TRIANGLES, 0, fb.position() / (surfaceVBO.getAttributes().vertexSize / 4));
		surfaceVBO.unbind(surfaceShader);
		
		// Render visible tiles
		fb = surfaceVBO.getBuffer();
		fb.position(0);
		fb.limit(fb.capacity());
		for (int i=0; i<G.raycaster.visibleTileCount; i++) {
			Tile t = G.raycaster.visibleTiles[i];
			if (!(t instanceof WallTile) && !(t instanceof DoorTile)) {
				t.writeVertexData(fb);
			}
		}
		
		surfaceVBO.bind(surfaceShader);
		Gdx.gl20.glDrawArrays(GL20.GL_TRIANGLES, 0, fb.position() / (surfaceVBO.getAttributes().vertexSize / 4));
		surfaceVBO.unbind(surfaceShader);
		
		surfaceShader.end();
	}
	
	private boolean isVicinityVisible(int x, int y, int radius) {
		Tile t = null;
		for (int xx=x-2; xx<x+2; xx++) {
			for (int yy=y-2; yy<y+2; yy++) {
				t = G.world.getTile(xx, yy);
				if (t != null && t.isVisible()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void renderShadows() {
		shadowShader.begin();

		Gdx.gl.glEnable(GL20.GL_POLYGON_OFFSET_FILL);
		Gdx.gl.glDisable(GL20.GL_CULL_FACE);
		//Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);

		shadowShader.setUniformMatrix("u_worldView", G.camera.camera.combined);
		
		// Render Walls
		for (int i=0; i<G.raycaster.visibleTileCount; i++) {
			Tile t = G.raycaster.visibleTiles[i];
			
			if (t instanceof WallTile) {
				WallTile wt = (WallTile)t;
				
				FloatBuffer fb = shadowVBO.getBuffer();
				fb.position(0);
				fb.limit(fb.capacity());
				wt.north.writeVertexData(fb, true);
				wt.east.writeVertexData(fb, true);
				wt.south.writeVertexData(fb, true);
				wt.west.writeVertexData(fb, true);

				shadowVBO.bind(shadowShader);
				Gdx.gl20.glDrawArrays(GL20.GL_TRIANGLES, 0, 6 * 4);
				shadowVBO.unbind(shadowShader);
			}
		}
		
		Gdx.gl.glDisable(GL20.GL_POLYGON_OFFSET_FILL);
		Gdx.gl.glEnable(GL20.GL_CULL_FACE);
		//Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);


		shadowShader.end();
	}
	
	private void renderDecals() {
		surfaceShader.begin();
		
		surfaceShader.setUniformMatrix("u_modelViewMatrix", G.camera.camera.view);
		surfaceShader.setUniformMatrix("u_projectionMatrix", G.camera.camera.projection);
		surfaceShader.setUniformi("u_texture", 0);
		
		G.statVisisbleDecals = 0;
		
		Gdx.gl.glEnable(GL20.GL_POLYGON_OFFSET_FILL);
		Gdx.gl.glPolygonOffset(-1.0f, -2.0f);

		for (int i=0; i<G.raycaster.visibleWallCount; i++) {
			Wall wall = G.raycaster.visibleWalls[i];
			for (Decal d : wall.getDecals()) {
				G.statVisisbleDecals++;
				FloatBuffer fb = surfaceVBO.getBuffer();
				fb.position(0);
				fb.limit(fb.capacity());

				d.writeVertexData(fb);
				
				surfaceVBO.bind(surfaceShader);
				Gdx.gl20.glDrawArrays(GL20.GL_TRIANGLE_STRIP, 0, 4);
				
				surfaceVBO.unbind(surfaceShader);
			}
		}
		
		Gdx.gl.glDisable(GL20.GL_POLYGON_OFFSET_FILL);
		
		surfaceShader.end();

	}
	
	private IPointSpriteEntity[] pointSpriteEntities  = new IPointSpriteEntity[20];
	private int numPointSpriteEntities = 0;
	
	private void renderEntities() {
		Gdx.gl.glDisable(GL20.GL_CULL_FACE);
		
		entityShader.begin();
		for (int i=G.raycaster.visibleTileCount-1; i>=0; i--) {
			Tile t = G.raycaster.visibleTiles[i];
			
			for (IEntity e : t.getResidentEntities()) {
				if (e instanceof IPointSpriteEntity) {
					G.statVisibleEntities++;

					IPointSpriteEntity psEntity = (IPointSpriteEntity) e;
					
					pointSpriteEntities[numPointSpriteEntities++] = psEntity;
				}
				else if (e instanceof ISpriteEntity) {
					G.statVisibleEntities++;

					ISpriteEntity se = (ISpriteEntity) e;
					Matrix4 m = G.camera.camera.combined.cpy();
					m.translate(se.getPosition().x, se.getPosition().y, 0.0f);
					m.rotate(0.0f, 0.0f, 1.0f, (float) G.player.rotation-90);
					
					entityShader.setUniformMatrix("u_worldView", m);
					entityShader.setUniformi("u_texture", 0);
					entityShader.setUniformf("u_lightColor", Color.WHITE);
					entityShader.setUniformi("u_useAlpha", 1);
					entityShader.setUniformf("u_alpha", se.getAlpha());
	
					FloatBuffer fb = entityVBO.getBuffer();
					fb.position(0);
					fb.limit(fb.capacity());
	
					se.writeVertexData(fb);
					
					entityVBO.bind(entityShader);
					Gdx.gl20.glDrawArrays(GL20.GL_TRIANGLE_STRIP, 0, 4);
					entityVBO.unbind(entityShader);
				}
			}
		}
		
		entityShader.end();
		
		Gdx.gl.glEnable(GL20.GL_CULL_FACE);
		
		for (int i=0; i<numPointSpriteEntities; i++) {
			IPointSpriteEntity e = pointSpriteEntities[i];
			renderParticleAt(e.getPosition(), e.getSpriteTexture(), e.getSpriteSize(), e.getSpriteColor());
			pointSpriteEntities[i] = null;
		}
		numPointSpriteEntities = 0;

	}
	
	private void renderFadingParticles() {
		
		//ShaderProgram.pedantic = false;
		pointSpriteTextureShader.begin();
		
		G.textureMap.texture.bind(0);
		
		pointSpriteTextureShader.setUniformMatrix("u_worldView", G.camera.camera.combined);
		pointSpriteTextureShader.setUniformf("u_viewportWidth", G.screenWidth);
		pointSpriteTextureShader.setUniformi("u_texture", 0);
		pointSpriteTextureShader.setUniformf("u_texSize", 1.0f/64.0f);

		for (ParticleSystem ps : G.world.particleManager.getParticleSystems()) {
			renderParticleBatch(ps.getParticles());
		}
		pointSpriteTextureShader.end();
	}
	
	private void renderTileResidentParticles() {
		
		//ShaderProgram.pedantic = false;
		pointSpriteTextureShader.begin();
		
		G.textureMap.texture.bind(0);
		
		pointSpriteTextureShader.setUniformMatrix("u_worldView", G.camera.camera.combined);
		pointSpriteTextureShader.setUniformf("u_viewportWidth", G.screenWidth);
		pointSpriteTextureShader.setUniformi("u_texture", 0);
		pointSpriteTextureShader.setUniformf("u_texSize", 1.0f/64.0f);

		for (int i=0; i<G.raycaster.visibleTileCount; i++) {
			Tile t = G.raycaster.visibleTiles[i];
			if (t.getResidentParticles().size() > 0) renderParticleBatch(t.getResidentParticles());
		}
		pointSpriteTextureShader.end();
	}
	
	private void renderParticleBatch(List<Particle> particles) {
		
		pointSpriteTextureShader.begin();
		
		G.textureMap.texture.bind(0);
		
		pointSpriteTextureShader.setUniformMatrix("u_worldView", G.camera.camera.combined);
		pointSpriteTextureShader.setUniformf("u_viewportWidth", G.screenWidth);
		pointSpriteTextureShader.setUniformi("u_texture", 0);
		pointSpriteTextureShader.setUniformf("u_texSize", 1.0f/64.0f);

		FloatBuffer fb = pointSpriteTextureVBO.getBuffer();
		fb.position(0);
		fb.limit(fb.capacity());
		
		for (Particle p : particles) {
			p.textures[p.currentTexture].getTextureCoords(texCoords0);
			
			fb.put(p.position.x).put(p.position.y).put(p.position.z);
			fb.put(p.color.r).put(p.color.g).put(p.color.b).put(p.color.a);
			fb.put(p.size); // size
			fb.put(p.position.dst(G.camera.camera.position)); // distance
			fb.put(texCoords0[0]).put(texCoords0[1]);
		}
		
		pointSpriteTextureVBO.bind(pointSpriteTextureShader);
		Gdx.gl20.glDrawArrays(GL20.GL_POINTS, 0, fb.position() / (pointSpriteTextureVBO.getAttributes().vertexSize / 4));
		pointSpriteTextureVBO.unbind(pointSpriteTextureShader);
		
		pointSpriteTextureShader.end();
	}

	private void renderParticleAt(Vector3 v, SubTexture tex, float size, Color c) {
		
		ShaderProgram.pedantic = true;
		pointSpriteTextureShader.begin();
		
		pointSpriteTextureShader.setUniformMatrix("u_worldView", G.camera.camera.combined);
		pointSpriteTextureShader.setUniformf("u_viewportWidth", G.screenWidth);
		pointSpriteTextureShader.setUniformi("u_texture", 0);
		pointSpriteTextureShader.setUniformf("u_texSize", 1.0f/64.0f);

		FloatBuffer fb = pointSpriteTextureVBO.getBuffer();
		fb.position(0);
		fb.limit(fb.capacity());
		
		tex.getTextureCoords(texCoords0);
		
		fb.put(v.x).put(v.y).put(v.z);
		fb.put(c.r).put(c.g).put(c.b).put(c.a);
		fb.put(size); // size
		fb.put(v.dst(G.camera.camera.position)); // distance
		fb.put(texCoords0[0]).put(texCoords0[1]);

		
		pointSpriteTextureVBO.bind(pointSpriteTextureShader);
		Gdx.gl20.glDrawArrays(GL20.GL_POINTS, 0, 1);
		pointSpriteTextureVBO.unbind(pointSpriteTextureShader);
		
		pointSpriteTextureShader.end();
	}
	
	private void renderParticleAt(Particle p) {
		
		ShaderProgram.pedantic = true;
		pointSpriteTextureShader.begin();
		
		pointSpriteTextureShader.setUniformMatrix("u_worldView", G.camera.camera.combined);
		pointSpriteTextureShader.setUniformf("u_viewportWidth", G.screenWidth);
		pointSpriteTextureShader.setUniformi("u_texture", 0);
		pointSpriteTextureShader.setUniformf("u_texSize", 1.0f/64.0f);

		FloatBuffer fb = pointSpriteTextureVBO.getBuffer();
		fb.position(0);
		fb.limit(fb.capacity());
		
		p.textures[p.currentTexture].getTextureCoords(texCoords0);
		
		fb.put(p.position.x).put(p.position.y).put(p.position.z);
		fb.put(p.color.r).put(p.color.g).put(p.color.b).put(p.color.a);
		fb.put(p.size); // size
		fb.put(p.position.dst(G.camera.camera.position)); // distance
		fb.put(texCoords0[0]).put(texCoords0[1]);

		
		pointSpriteTextureVBO.bind(pointSpriteTextureShader);
		Gdx.gl20.glDrawArrays(GL20.GL_POINTS, 0, 1);
		pointSpriteTextureVBO.unbind(pointSpriteTextureShader);
		
		pointSpriteTextureShader.end();
	}
	
	private void renderPickUps() {
		
		pointSpriteTextureShader.begin();
		
		G.textureMap.texture.bind(0);
		
		pointSpriteTextureShader.setUniformMatrix("u_worldView", G.camera.camera.combined);
		pointSpriteTextureShader.setUniformf("u_viewportWidth", G.screenWidth);
		pointSpriteTextureShader.setUniformi("u_texture", 0);
		pointSpriteTextureShader.setUniformf("u_texSize", 1.0f/64.0f);
		
		for (int i=0; i<G.raycaster.visibleTileCount; i++) {
			Tile t = G.raycaster.visibleTiles[i];
			if (!(t instanceof VoidTile)) {
				continue;
			}
			
			VoidTile vt = (VoidTile) t;
			if (vt.getPickUp() == null) {
				continue;
			}
			
			PickUp pu = vt.getPickUp();
			
			pu.getTexture().getTextureCoords(texCoords0);

			FloatBuffer fb = pointSpriteTextureVBO.getBuffer();
			fb.position(0);
			fb.limit(fb.capacity());
			
			fb.put(pu.getPosition().x).put(pu.getPosition().y).put(pu.getPosition().z + pu.getSize()/2.0f);
			Color c = Color.WHITE;
			fb.put(c.r).put(c.g).put(c.b).put(c.a);
			fb.put(pu.getSize()); // size
			fb.put(pu.getPosition().dst(G.camera.camera.position)); // distance
			fb.put(texCoords0[0]).put(texCoords0[1]);

			pointSpriteTextureVBO.bind(pointSpriteTextureShader);
			Gdx.gl20.glDrawArrays(GL20.GL_POINTS, 0, 1);
			pointSpriteTextureVBO.unbind(pointSpriteTextureShader);
		}
		
		pointSpriteTextureShader.end();
	}
	
	private void renderLines(Vector3[] points, int num, Color color, float lineWidth, int type) {
	
		lineShader.begin();
		
		lineShader.setUniformMatrix("u_worldView", G.camera.camera.combined);
		lineShader.setUniformf("u_color", color);
		
		Gdx.gl.glLineWidth(lineWidth);
		
		FloatBuffer fb = lineVBO.getBuffer();
		fb.position(0);
		fb.limit(fb.capacity());
		
		for (Vector3 p : points) {
			fb.put(p.x).put(p.y).put(p.z);
		}
		
		lineVBO.bind(lineShader);
		Gdx.gl20.glDrawArrays(type, 0, num);
		
		lineVBO.unbind(lineShader);
		
		lineShader.end();
	}

	private void renderTransparentTiles() {
		Gdx.gl.glDisable(GL20.GL_CULL_FACE);

		entityShader.begin();
		
		entityShader.setUniformMatrix("u_worldView", G.camera.camera.combined);
		entityShader.setUniformi("u_texture", 0);
		entityShader.setUniformf("u_lightColor", Color.WHITE);
		entityShader.setUniformi("u_useAlpha", 0);
		
		FloatBuffer fb = entityVBO.getBuffer();
		fb.position(0);
		fb.limit(fb.capacity());

		for (int i=0; i<G.raycaster.transparentTileCount; i++) {
			TransparentTile t = G.raycaster.transparentTiles[i];
			
			t.writeVertexDataTransparent(fb);
		}
		
		entityVBO.bind(entityShader);
		Gdx.gl20.glDrawArrays(GL20.GL_TRIANGLES, 0, fb.position() / (entityVBO.getAttributes().vertexSize / 4));
		
		entityVBO.unbind(entityShader);
		
		entityShader.end();
		
		Gdx.gl.glEnable(GL20.GL_CULL_FACE);

	}

	private static Vector3[] points = new Vector3[20];
	static {
		for (int i=0; i<points.length; i++) {
			points[i] = new Vector3();
		}
	}
	
	private void markWall(Wall w) {
		points[0].set(w.xStart, w.yStart, 0.0f);
		points[1].set(w.xStart, w.yStart, w.height);
		points[2].set(w.xEnd, w.yEnd, w.height);
		points[3].set(w.xEnd, w.yEnd, 0.0f);
		
		renderLines(points, 4, Color.RED, 1.0f, GL20.GL_LINE_LOOP);
	}
	
	private void markTile(Tile t, Color c) {
		if (c == null) c = Color.GREEN;
		
		points[0].set(t.x, t.y, 0.0f);
		points[1].set(t.x, t.y, 1.0f);
		points[2].set(t.x+1, t.y, 1.0f);
		points[3].set(t.x+1, t.y, 0.0f);
		renderLines(points, 4, c, 1.0f, GL20.GL_LINE_LOOP);
		
		points[0].set(t.x, t.y+1, 0.0f);
		points[1].set(t.x, t.y+1, 1.0f);
		points[2].set(t.x+1, t.y+1, 1.0f);
		points[3].set(t.x+1, t.y+1, 0.0f);
		renderLines(points, 4, c, 1.0f, GL20.GL_LINE_LOOP);
		
		points[0].set(t.x, t.y, 0.0f);
		points[1].set(t.x, t.y, 1.0f);
		points[2].set(t.x, t.y+1, 1.0f);
		points[3].set(t.x, t.y+1, 0.0f);
		renderLines(points, 4, c, 1.0f, GL20.GL_LINE_LOOP);
		
		points[0].set(t.x+1, t.y, 0.0f);
		points[1].set(t.x+1, t.y, 1.0f);
		points[2].set(t.x+1, t.y+1, 1.0f);
		points[3].set(t.x+1, t.y+1, 0.0f);
		renderLines(points, 4, c, 1.0f, GL20.GL_LINE_LOOP);
	}

	private void renderDebug2() {
		for (ILight l : G.world.entityManager.getLights()) {
			
			Particle p = new Particle();
			p.textures = new SubTexture[] { G.textureMap.lightIndicator };
			p.size = 0.1f;
			p.position = l.getLightPosition();
			p.color = Color.WHITE;
			
			renderParticleAt(p);
		}
		
		if (G.raycaster.selectedWall != null) {
			markWall(G.raycaster.selectedWall);
		}
		if (G.dRefPos1 != null && G.dRefPos2 != null) {
			points[0].set(G.dRefPos1);
			points[1].set(G.dRefPos2);
		
			boolean lineOfSight = G.raycaster.isLineOfSight(G.world, new Vector2(G.dRefPos1.x, G.dRefPos1.y), new Vector2(G.dRefPos2.x, G.dRefPos2.y));
			
			renderLines(points, 2, lineOfSight ? Color.GREEN : Color.RED, 2.0f, GL20.GL_LINES);
		}
	}
	
	private void renderDebug() {
		// Mark tiles
		for (int i=0; i<G.raycaster.visibleTileCount; i++) {
			Tile t = G.raycaster.visibleTiles[i];
			//if (t.getResidentEntities().size() > 0) markTile(t);
		}
		
		// Show direction of entities
		for (int i=0; i<G.raycaster.visibleTileCount; i++) {
			Tile t = G.raycaster.visibleTiles[i];
			
			for (IEntity e : t.getResidentEntities()) {
				if (e instanceof NPC) {
					NPC npc = (NPC) e;
					points[0].set(npc.getPosition());
					points[0].z = 0.01f;
					Vector3 v = new Vector3();
					MUtil.setDirectionFromAngle(npc.angleToPlayer, v);
					points[1].set(npc.getPosition()).add(npc.getDirection());
					points[1].z = 0.01f;
						
					renderLines(points, 2, Color.RED, 2.0f, GL20.GL_LINES);
				
					Tile entityTile = G.world.getTile((int)e.getPosition().x, (int)e.getPosition().y);
					if (entityTile.isVisible()) {
						markTile(entityTile, null);
						G.dRef = e;
					}

				}
			}
		}
		for (Tile t : G.raycaster.debugTiles) {
			markTile(t, Color.ORANGE);
		}

		if (C.isDebug) {
			G.raycaster.debugTiles.clear();
		}
		
//		Particle p = new Particle();
//		p.color = Color.WHITE;
//		p.currentTexture = 0;
//		p.position = new Vector3(7.6f, 35.0f, 0.5f);
//		p.size = 0.2f;
//		p.textures = G.textureMap.fire;
		

		
	}
}
