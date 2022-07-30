package fruitfly.virus.tiles;

import java.nio.FloatBuffer;

import fruitfly.virus.G;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.render.WorldRenderer;

public class TwoWingDoorTile extends DoorTile {
	
	public TwoWingDoorTile(int x, int y, boolean horizontal, SubTexture texture, SubTexture floorTexture) {
		super(x, y, horizontal, texture, floorTexture);
	}

	public void writeVertexData(FloatBuffer buffer) {
		super.writeVertexData(buffer);
		texture.getTextureCoords(WorldRenderer.texCoords0);
		
		if (horizontal) {
			// Eastern door Surface / left halfdoor
			buffer.put(x-0.5f+(0.5f*slide)).put(y+0.4f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+0.5f*slide).put(y+0.4f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]-halfTexWidth).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x-0.5f+(0.5f*slide)).put(y+0.4f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x-0.5f+(0.5f*slide)).put(y+0.4f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+0.5f*slide).put(y+0.4f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]-halfTexWidth).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+0.5f*slide).put(y+0.4f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[6]-halfTexWidth).put(WorldRenderer.texCoords0[7]);
			buffer.put(WorldRenderer.normalEast);
			
			// Eastern door Surface / right halfdoor
			buffer.put(x+1.0f-(0.5f*slide)).put(y+0.4f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]+halfTexWidth).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+1.5f-(0.5f*slide)).put(y+0.4f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+1.0f-(0.5f*slide)).put(y+0.4f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]+halfTexWidth).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+1.0f-(0.5f*slide)).put(y+0.4f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]+halfTexWidth).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+1.5f-(0.5f*slide)).put(y+0.4f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+1.5f-(0.5f*slide)).put(y+0.4f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
			buffer.put(WorldRenderer.normalEast);
			
			// Left rail
			buffer.put(x+1.0f-(0.5f*slide)).put(y+0.6f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalNorth);
			
			buffer.put(x+1.0f-(0.5f*slide)).put(y+0.4f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]-0.0001f).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalNorth);
			
			buffer.put(x+1.0f-(0.5f*slide)).put(y+0.6f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]-0.0001f);
			buffer.put(WorldRenderer.normalNorth);
			
			buffer.put(x+1.0f-(0.5f*slide)).put(y+0.6f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]-0.0001f);
			buffer.put(WorldRenderer.normalNorth);
			
			buffer.put(x+1.0f-(0.5f*slide)).put(y+0.4f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]-0.0001f).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalNorth);
			
			buffer.put(x+1.0f-(0.5f*slide)).put(y+0.4f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[2]-0.0001f).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalNorth);
			
			// Right rail
			buffer.put(x+0.5f*slide).put(y+0.4f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalSouth);
			
			buffer.put(x+0.5f*slide).put(y+0.6f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]-0.0001f).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalSouth);
			
			buffer.put(x+0.5f*slide).put(y+0.4f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]-0.0001f);
			buffer.put(WorldRenderer.normalSouth);
			
			buffer.put(x+0.5f*slide).put(y+0.4f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]-0.0001f);
			buffer.put(WorldRenderer.normalSouth);
			
			buffer.put(x+0.5f*slide).put(y+0.6f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]-0.0001f).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalSouth);
			
			buffer.put(x+0.5f*slide).put(y+0.6f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[2]-0.0001f).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalSouth);
			
	
			// Western door Surface / left halfdoor
			buffer.put(x+1.5f-(0.5f*slide)).put(y+0.6f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+1.0f-(0.5f*slide)).put(y+0.6f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]-halfTexWidth).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+1.5f-(0.5f*slide)).put(y+0.6f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+1.5f-(0.5f*slide)).put(y+0.6f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+1.0f-(0.5f*slide)).put(y+0.6f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]-halfTexWidth).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+1.0f-(0.5f*slide)).put(y+0.6f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[6]-halfTexWidth).put(WorldRenderer.texCoords0[7]);
			buffer.put(WorldRenderer.normalWest);
			
			// Western door Surface / right halfdoor
			buffer.put(x+0.5f*slide).put(y+0.6f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]+halfTexWidth).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x-0.5f+(0.5f*slide)).put(y+0.6f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+0.5f*slide).put(y+0.6f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]+halfTexWidth).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+0.5f*slide).put(y+0.6f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]+halfTexWidth).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x-0.5f+(0.5f*slide)).put(y+0.6f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x-0.5f+(0.5f*slide)).put(y+0.6f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
			buffer.put(WorldRenderer.normalWest);
		}
		else {
			// Eastern door Surface / left halfdoor
			buffer.put(x+0.6f).put(y-0.5f+(0.5f*slide)).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+0.6f).put(y+0.5f*slide).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]-halfTexWidth).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+0.6f).put(y-0.5f+(0.5f*slide)).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+0.6f).put(y-0.5f+(0.5f*slide)).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+0.6f).put(y+0.5f*slide).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]-halfTexWidth).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+0.6f).put(y+0.5f*slide).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[6]-halfTexWidth).put(WorldRenderer.texCoords0[7]);
			buffer.put(WorldRenderer.normalEast);
			
			// Eastern door Surface / right halfdoor
			buffer.put(x+0.6f).put(y+1.0f-(0.5f*slide)).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]+halfTexWidth).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+0.6f).put(y+1.5f-(0.5f*slide)).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+0.6f).put(y+1.0f-(0.5f*slide)).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]+halfTexWidth).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+0.6f).put(y+1.0f-(0.5f*slide)).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]+halfTexWidth).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+0.6f).put(y+1.5f-(0.5f*slide)).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalEast);
			
			buffer.put(x+0.6f).put(y+1.5f-(0.5f*slide)).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
			buffer.put(WorldRenderer.normalEast);
			
			// Left rail
			buffer.put(x+0.6f).put(y+0.5f*slide).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalNorth);
			
			buffer.put(x+0.4f).put(y+0.5f*slide).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]-0.0001f).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalNorth);
			
			buffer.put(x+0.6f).put(y+0.5f*slide).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]-0.0001f);
			buffer.put(WorldRenderer.normalNorth);
			
			buffer.put(x+0.6f).put(y+0.5f*slide).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]-0.0001f);
			buffer.put(WorldRenderer.normalNorth);
			
			buffer.put(x+0.4f).put(y+0.5f*slide).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]-0.0001f).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalNorth);
			
			buffer.put(x+0.4f).put(y+0.5f*slide).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[2]-0.0001f).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalNorth);
			
			// Right rail
			buffer.put(x+0.4f).put(y+1.0f-(0.5f*slide)).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalSouth);
			
			buffer.put(x+0.6f).put(y+1.0f-(0.5f*slide)).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]-0.0001f).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalSouth);
			
			buffer.put(x+0.4f).put(y+1.0f-(0.5f*slide)).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]-0.0001f);
			buffer.put(WorldRenderer.normalSouth);
			
			buffer.put(x+0.4f).put(y+1.0f-(0.5f*slide)).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]-0.0001f);
			buffer.put(WorldRenderer.normalSouth);
			
			buffer.put(x+0.6f).put(y+1.0f-(0.5f*slide)).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]-0.0001f).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalSouth);
			
			buffer.put(x+0.6f).put(y+1.0f-(0.5f*slide)).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[2]-0.0001f).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalSouth);
			
	
			// Western door Surface / left halfdoor
			buffer.put(x+0.4f).put(y+1.5f-(0.5f*slide)).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+0.4f).put(y+1.0f-(0.5f*slide)).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]-halfTexWidth).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+0.4f).put(y+1.5f-(0.5f*slide)).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+0.4f).put(y+1.5f-(0.5f*slide)).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+0.4f).put(y+1.0f-(0.5f*slide)).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]-halfTexWidth).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+0.4f).put(y+1.0f-(0.5f*slide)).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[6]-halfTexWidth).put(WorldRenderer.texCoords0[7]);
			buffer.put(WorldRenderer.normalWest);
			
			// Western door Surface / right halfdoor
			buffer.put(x+0.4f).put(y+0.5f*slide).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]+halfTexWidth).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+0.4f).put(y-0.5f+(0.5f*slide)).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+0.4f).put(y+0.5f*slide).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]+halfTexWidth).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+0.4f).put(y+0.5f*slide).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]+halfTexWidth).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+0.4f).put(y-0.5f+(0.5f*slide)).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.normalWest);
			
			buffer.put(x+0.4f).put(y-0.5f+(0.5f*slide)).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
			buffer.put(WorldRenderer.normalWest);
		}
	}
}
