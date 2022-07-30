package fruitfly.virus.render;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.VertexBufferObject;
import com.badlogic.gdx.math.Vector2;

import fruitfly.virus.BaseTextureMap;
import fruitfly.virus.G;
import fruitfly.virus.TextureMap;
import fruitfly.virus.TextureMap.SubTexture;

public class Renderer2D {
	
	OrthographicCamera ortho;
	
	ShaderProgram shader;
	VertexBufferObject vbo;
	
	private Map<String, SubTexture> charTable;
	static final String CHARS = 	"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,:;'!?+-=/\\()>#";
	
	private TextureMap currentTextureMap = null;
	
	public Renderer2D() {
		
		charTable = new HashMap<String, SubTexture>();
		for (int i=0; i<CHARS.length(); i++) {
			String c = CHARS.substring(i, i+1);
			charTable.put(c, G.baseTextureMap.chars[i]);
		}
		
		ortho = new OrthographicCamera(G.screenWidth, -G.screenHeight);
		
		this.vbo = new VertexBufferObject(true, 6 * 4, VertexAttribute.Position(), VertexAttribute.TexCoords(0));
		
		shader = new ShaderProgram(Gdx.files.internal("data/shaders/color.vsh"), Gdx.files.internal("data/shaders/color.fsh"));
		if (!shader.isCompiled()) {
			throw new RuntimeException("Shader not compiled: " + shader.getLog());
		}
	}
	
	public void begin() {
		currentTextureMap = null;
		shader.begin();

		shader.setUniformMatrix("u_worldView", ortho.combined);
		shader.setUniformi("u_texture", 0);
		shader.setUniformf("u_lightColor", Color.WHITE);
	}
	
	public void end() {
		shader.end();		
	}
	
	static float[] TEX_COORDS = new float[8];
	
	public void setLightColor(Color c) {
		shader.setUniformf("u_lightColor", c);
	}
	
	public void render(SubTexture texture, float x, float y, float scale) {
		if (currentTextureMap != texture.textureMap) {
			currentTextureMap = texture.textureMap;
			currentTextureMap.texture.bind();
		}
		
		x -= G.screenWidth/2;
		y -= G.screenHeight/2;
		
		FloatBuffer fb = vbo.getBuffer();
		fb.position(0);
		fb.limit(fb.capacity());
		texture.getTextureCoords(TEX_COORDS);
		
		fb.put(x).put(y).put(0.0f);
		fb.put(TEX_COORDS[0]).put(TEX_COORDS[1]);

		fb.put(x).put(y + texture.getHeight() * scale).put(0.0f);
		fb.put(TEX_COORDS[2]).put(TEX_COORDS[3]);
		
		fb.put(x + texture.getWidth() * scale).put(y + texture.getHeight() * scale).put(0.0f);
		fb.put(TEX_COORDS[4]).put(TEX_COORDS[5]);
		
		fb.put(x).put(y).put(0.0f);
		fb.put(TEX_COORDS[0]).put(TEX_COORDS[1]);
		
		fb.put(x + texture.getWidth() * scale).put(y + texture.getHeight() * scale).put(0.0f);
		fb.put(TEX_COORDS[4]).put(TEX_COORDS[5]);
		
		fb.put(x +  texture.getWidth() * scale).put(y).put(0.0f);
		fb.put(TEX_COORDS[6]).put(TEX_COORDS[7]);
				
		vbo.bind(shader);
		
		Gdx.gl20.glDrawArrays(GL20.GL_TRIANGLES, 0, 6);
		
		vbo.unbind(shader);
		
	}
	
	public void render(SubTexture texture, float x, float y,  float width, float height) {
		if (currentTextureMap != texture.textureMap) {
			currentTextureMap = texture.textureMap;
			currentTextureMap.texture.bind();
		}
		
		x -= G.screenWidth/2;
		y -= G.screenHeight/2;
		
		FloatBuffer fb = vbo.getBuffer();
		fb.position(0);
		fb.limit(fb.capacity());
		texture.getTextureCoords(TEX_COORDS);
		
		fb.put(x).put(y).put(0.0f);
		fb.put(TEX_COORDS[0]).put(TEX_COORDS[1]);

		fb.put(x).put(y + height).put(0.0f);
		fb.put(TEX_COORDS[2]).put(TEX_COORDS[3]);
		
		fb.put(x + width).put(y + height).put(0.0f);
		fb.put(TEX_COORDS[4]).put(TEX_COORDS[5]);
		
		fb.put(x).put(y).put(0.0f);
		fb.put(TEX_COORDS[0]).put(TEX_COORDS[1]);
		
		fb.put(x + width).put(y + height).put(0.0f);
		fb.put(TEX_COORDS[4]).put(TEX_COORDS[5]);
		
		fb.put(x +  width).put(y).put(0.0f);
		fb.put(TEX_COORDS[6]).put(TEX_COORDS[7]);
				
		vbo.bind(shader);
		
		Gdx.gl20.glDrawArrays(GL20.GL_TRIANGLES, 0, 6);
		
		vbo.unbind(shader);
		
	}
	
	public void render(Texture texture, float x, float y, float width, float height, float u, float v, float usize, float vsize) {
		x -= G.screenWidth/2;
		y -= G.screenHeight/2;
		
		texture.bind();
		
		FloatBuffer fb = vbo.getBuffer();
		fb.position(0);
		fb.limit(fb.capacity());
		
		fb.put(x).put(y).put(0.0f);
		fb.put(u).put(v);

		fb.put(x).put(y + height).put(0.0f);
		fb.put(u).put(v+vsize);
		
		fb.put(x + width).put(y + height).put(0.0f);
		fb.put(u+usize).put(v+vsize);
		
		fb.put(x).put(y).put(0.0f);
		fb.put(u).put(v);
		
		fb.put(x + width).put(y + height).put(0.0f);
		fb.put(u+usize).put(v+vsize);
		
		fb.put(x + width).put(y).put(0.0f);
		fb.put(u+usize).put(v);
				
		vbo.bind(shader);
		
		Gdx.gl20.glDrawArrays(GL20.GL_TRIANGLES, 0, 6);
		
		vbo.unbind(shader);
	}
	
	public void renderString(String str, float x, float y) {
		if (str == null) return;
		str = str.toUpperCase();
		int x0 = (int)x;
		String lines[] = str.split("\n");
		boolean firstLine = true;
		for (String line : lines) {
			x = x0;
			
			if (firstLine) firstLine = false;
			else y += 4;
			
			for (int i=0; i<line.length(); i++) {
				String c = line.substring(i, i+1);
				if  (c.equals(" ")) {
					x += 6;
					continue;
				}
				SubTexture charTex = charTable.get(c);
				if (charTex == null) charTex = charTable.get("#");
				render(charTex, x, y, 1.0f);
				x += 8;
			}
			y += 8;
		}
	}
	
	public void getStringSize(String str, Vector2 size) {
		if (str == null) return;
		str = str.toUpperCase();
		int x = 0, xMax = 0;
		int y = 0;
		String lines[] = str.split("\n");
		boolean firstLine = true;
		for (String line : lines) {
			x = 0;
			
			if (firstLine) firstLine = false;
			else y += 4;
			
			for (int i=0; i<line.length(); i++) {
				String c = line.substring(i, i+1);
				if  (c.equals(" ")) {
					x += 6;
					continue;
				}
				SubTexture charTex = charTable.get(c);
				if (charTex == null) charTex = charTable.get("#");
				x += 8;
			}
			y += 8;
			
			if (x > xMax) xMax = x;
		}
	
		size.x = xMax;
		size.y = y;
	}
}
