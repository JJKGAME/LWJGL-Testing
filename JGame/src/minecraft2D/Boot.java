package minecraft2D;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Boot {

	private BlockGrid grid;
	private BlockType selection = BlockType.STONE;
	private int selectorX = 0;
	private int selectorY = 0;
	private boolean mouseEnabled = true;

	public Boot() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Minecraft 2D");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		grid = new BlockGrid();

		// initialization code for OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		while (!Display.isCloseRequested()) {
			// Render
			glClear(GL_COLOR_BUFFER_BIT);

			input();
			grid.draw();
			drawSelectionBox();

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
		System.exit(0);
	}

	private void drawSelectionBox() {
		int x = selectorX * World.BLOCK_SIZE;
		int y = selectorY * World.BLOCK_SIZE;
		int x2 = x + World.BLOCK_SIZE;
		int y2 = y + World.BLOCK_SIZE;
		if (grid.getAt(selectorX, selectorY).getType() != BlockType.AIR || selection == BlockType.AIR) {
			glBindTexture(GL_TEXTURE_2D, 0);
			glColor4f(1f, 1f, 1f, .5f);
			glBegin(GL_QUADS);
			{
				glVertex2i(x, y);
				glVertex2i(x2, y);
				glVertex2i(x2, y2);
				glVertex2i(x, y2);
			}
			glEnd();
			glColor4f(1f, 1f, 1f, 1f);
		} else {
			glColor4f(1f, 1f, 1f, .5f);
			new Block(selection, selectorX * World.BLOCK_SIZE, selectorY * World.BLOCK_SIZE).draw();
			glColor4f(1f, 1f, 1f, 1f);
		}
	}

	boolean mouseClicked;

	private void input() {
		mouseClicked = Mouse.isButtonDown(0);
		if (mouseClicked)
			mouseEnabled = true;
		if (mouseEnabled) {
			int mousex = Mouse.getX();
			int mousey = 480 - Mouse.getY() - 1;
			selectorX = Math.round(mousex / World.BLOCK_SIZE);
			selectorY = Math.round(mousey / World.BLOCK_SIZE);
			if (mouseClicked) {
				grid.setAt(selectorX, selectorY, selection);
			}
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				if (!(selectorX + 1 > World.BLOCKS_WIDTH - 2)) {
					mouseEnabled = false;
					selectorX += 1;
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				if (!(selectorX - 1 < 0)) {
					mouseEnabled = false;
					selectorX -= 1;
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_UP && Keyboard.getEventKeyState()) {
				if (!(selectorY - 1 < 0)) {
					mouseEnabled = false;
					selectorY -= 1;
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_DOWN && Keyboard.getEventKeyState()) {
				if (!(selectorY + 1 > World.BLOCKS_HEIGHT - 2)) {
					mouseEnabled = false;
					selectorY += 1;
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_S) {
				grid.save(new File("save.xml"));
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_L) {
				grid.load(new File("save.xml"));
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_1) {
				selection = BlockType.STONE;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_2) {
				selection = BlockType.DIRT;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_3) {
				selection = BlockType.GRASS;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_4) {
				selection = BlockType.AIR;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_C) {
				grid.clear();
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_R) {
				grid.generate();
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
				Display.destroy();
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {
		new Boot();
	}

}
