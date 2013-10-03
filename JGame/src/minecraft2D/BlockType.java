package minecraft2D;

public enum BlockType {
	STONE("res/stone.png"), AIR("res/air.png"), GRASS("res/grass.png"),DIRT("res/dirt.png");
	
	public final String location;
	BlockType(String loc){
		location = loc;
	}
}
