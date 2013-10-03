package minecraft2D;

public enum BlockType {
	STONE("res/images/stone.png"), AIR("res/images/air.png"), GRASS("res/images/grass.png"), DIRT("res/images/dirt.png");

	public final String location;

	BlockType(String loc) {
		location = loc;
	}
}
