package fruitfly.virus;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.OrderedMap;

import fruitfly.virus.TextureMap.SubTexture;

public class Episode {
	private String name;
	private SubTexture logo;
	
	public class Level {
		private String name;
		private FileHandle file;
		private Color skyLight;
		
		public Level() {
			
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public FileHandle getFile() {
			return file;
		}

		public void setFile(FileHandle file) {
			this.file = file;
		}
		
		public Color getSkyLight() {
			return skyLight;
		}
		
		public void setSkyLight(Color l) {
			this.skyLight = l;
		}
	}
	
	public Episode() {
		
	}
	
	public void load(FileHandle folder) {
		
		FileHandle jsonfh = folder.child("episode.json");
		FileHandle logofh = folder.child("logo.png");
		
		JsonReader reader = new JsonReader();
		JsonValue jsonconfig = reader.parse(jsonfh);


		Float version = (Float) jsonconfig.getFloat("version");
		String name = (String) jsonconfig.getString("name");
		this.name = name;
		
		JsonValue levels =  jsonconfig.get("levels");
		for (JsonValue jsonLevel : levels) {
			Level l = new Level();
			l.setName(jsonLevel.getString("name"));
			l.setFile(Gdx.files.internal(folder.path() + "/" + jsonLevel.getString("file")));
			JsonValue skyLightJson = jsonLevel.get("skyLight");
			l.setSkyLight(new Color(skyLightJson.getFloat(0), skyLightJson.getFloat(1), skyLightJson.getFloat(2), 0.0f));
			this.levels.add(l);
		}
		
		Texture texture = new Texture(logofh);
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		LogoTextureMap texMap = new LogoTextureMap(texture);
		this.logo = texMap.logo;
	}
	
	private List<Level> levels = new LinkedList<Level>();
	
	public String getName() {
		return name;
	}
	
	public List<Level> getLevels() {
		return levels;
	}

	public SubTexture getLogo() {
		return logo;
	}
	
	public static List<Episode> getEpisodes() {
		List<Episode> episodes = new LinkedList<Episode>();
		
		JsonReader jsonReader = new JsonReader();
		JsonValue episodeKeys = jsonReader.parse(Gdx.files.internal("data/episodes/episodes.json"));
		for (int i=0; i<episodeKeys.size; i++) {
			String key = episodeKeys.getString(i);
			System.out.println("" + key);
			Episode e = new Episode();
			e.load(Gdx.files.internal("data/episodes/" + key));
			episodes.add(e);
		}
		
		return episodes;
	}
}
