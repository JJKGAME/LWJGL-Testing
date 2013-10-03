package minecraft2D;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Boot {

	BlockGrid grid;
	
	public Boot() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Minecraft 2D");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		 grid = new BlockGrid();
		
		grid.setAt(10, 10, BlockType.STONE);

		// initialization code for OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);

		while (!Display.isCloseRequested()) {
			// Render
			glClear(GL_COLOR_BUFFER_BIT);

			input();
			grid.draw();

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}

	private void input() {
		int mousex = Mouse.getX();
		int mousey = 480 - Mouse.getY() - 1;
		boolean mouseClicked = Mouse.isButtonDown(0);
		if (mouseClicked) {
			int grid_x = Math.round(mousex / World.BLOCK_SIZE);
			int grid_y = Math.round(mousey / World.BLOCK_SIZE);
			grid.setAt(grid_x, grid_y, BlockType.STONE);
		}
		
		while(Keyboard.next()){
			if(Keyboard.getEventKey() == Keyboard.KEY_S){
				grid.save(new File("save.xml"));
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_L){
				grid.load(new File("save.xml"));
			}
		}
	}

	public static void main(String[] args) {
		new Boot();
	}

}
