package minecraft2D;

import static minecraft2D.World.*;

import java.io.*;
import java.util.Random;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class BlockGrid {
	private Block[][] blocks = new Block[BLOCKS_WIDTH][BLOCKS_HEIGHT];

	public BlockGrid(File loadFile) {

	}

	public BlockGrid() {
		for (int x = 0; x < BLOCKS_WIDTH - 1; x++) {
			for (int y = 0; y < BLOCKS_HEIGHT - 1; y++) {
				blocks[x][y] = new Block(BlockType.AIR, x * BLOCK_SIZE, y * BLOCK_SIZE);
			}
		}
	}

	public void load(File loadFile) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(loadFile);
			Element root = doc.getRootElement();
			for (Element block : root.getChildren()) {
				Element e = (Element) block;
				int x = Integer.parseInt(e.getAttributeValue("x"));
				int y = Integer.parseInt(e.getAttributeValue("y"));
				blocks[x][y] = new Block(BlockType.valueOf(e.getAttributeValue("type")), x * BLOCK_SIZE, y * BLOCK_SIZE);
			}
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}

	}

	public void save(File saveFile) {
		Document doc = new Document();
		Element root = new Element("blocks");
		doc.setRootElement(root);
		for (int x = 0; x < BLOCKS_WIDTH - 1; x++) {
			for (int y = 0; y < BLOCKS_HEIGHT - 1; y++) {
				Element block = new Element("block");
				block.setAttribute("x", String.valueOf((int) (blocks[x][y].getX() / BLOCK_SIZE)));
				block.setAttribute("y", String.valueOf((int) (blocks[x][y].getY() / BLOCK_SIZE)));
				block.setAttribute("type", String.valueOf(blocks[x][y].getType()));
				root.addContent(block);
			}
		}

		XMLOutputter out = new XMLOutputter();
		try {
			out.output(doc, new FileOutputStream(saveFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAt(int x, int y, BlockType b) {
		blocks[x][y] = new Block(b, x * BLOCK_SIZE, y * BLOCK_SIZE);
	}

	public Block getAt(int x, int y) {
		return blocks[x][y];
	}

	public void draw() {
		for (int x = 0; x < BLOCKS_WIDTH - 1; x++) {
			for (int y = 0; y < BLOCKS_HEIGHT - 1; y++) {
				blocks[x][y].draw();
			}
		}
	}

	public void clear() {
		for (int x = 0; x < BLOCKS_WIDTH - 1; x++) {
			for (int y = 0; y < BLOCKS_HEIGHT - 1; y++) {
				blocks[x][y] = new Block(BlockType.AIR, x * BLOCK_SIZE, y * BLOCK_SIZE);
			}
		}
	}

	public void generate() {
		Random rand = new Random();
		for (int x = 0; x < BLOCKS_WIDTH - 1; x++) {
			for (int y = 0; y < BLOCKS_HEIGHT - 1; y++) {
				if (rand.nextBoolean()) {
					blocks[x][y] = new Block(BlockType.AIR, x * BLOCK_SIZE, y * BLOCK_SIZE);
				} else {
					blocks[x][y] = new Block(BlockType.GRASS, x * BLOCK_SIZE, y * BLOCK_SIZE);
				}
				if (y < BLOCKS_HEIGHT * 0.3) {
					blocks[x][y] = new Block(BlockType.AIR, x * BLOCK_SIZE, y * BLOCK_SIZE);
				}
				if (y > BLOCKS_HEIGHT * 0.35) {
					blocks[x][y] = new Block(BlockType.DIRT, x * BLOCK_SIZE, y * BLOCK_SIZE);
				}
				try {
					if (blocks[x][y - 1].getType() != BlockType.AIR) {
						blocks[x][y] = blocks[x][y] = new Block(BlockType.DIRT, x * BLOCK_SIZE, y * BLOCK_SIZE);
					}
					if (blocks[x][y - 1].getType() == BlockType.GRASS && blocks[x][y].getType() == BlockType.GRASS) {
						blocks[x][y] = new Block(BlockType.DIRT, x * BLOCK_SIZE, y * BLOCK_SIZE);
					}
					if (blocks[x][y - 1].getType() == BlockType.AIR && blocks[x][y].getType() == BlockType.DIRT) {
						blocks[x][y] = new Block(BlockType.GRASS, x * BLOCK_SIZE, y * BLOCK_SIZE);
					}
				} catch (Exception e) {

				}
				if (y > BLOCKS_HEIGHT * 0.5) {
					blocks[x][y] = new Block(BlockType.STONE, x * BLOCK_SIZE, y * BLOCK_SIZE);
				}
			}
		}
	}

}
