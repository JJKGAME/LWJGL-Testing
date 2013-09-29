import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.*;
import org.lwjgl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class TextureDemo {

	public Texture	wood, stone;

	public TextureDemo() throws FileNotFoundException {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Texure Demo");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		wood = loadTexture("wood");
		stone = loadTexture("stone");

		// initialization code for OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);

		while (!Display.isCloseRequested()) {
			// Render

			glClear(GL_COLOR_BUFFER_BIT);

			//wood.bind();  // <-- bad method
			glBindTexture(GL_TEXTURE_2D, 1);  // <-- good method

			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 0); // binding the upper left corner of the
									// texture to the upper left corner cord
				glVertex2i(400, 400); // Upper Left
				glTexCoord2f(1, 0);
				glVertex2i(450, 400); // Upper Right
				glTexCoord2f(1, 1);
				glVertex2i(450, 450); // Bottom Right
				glTexCoord2f(0, 1);
				glVertex2i(400, 450); // Bottom Left
			}
			glEnd();

			glBindTexture(GL_TEXTURE_2D, 0);

			glBegin(GL_LINES);
			{
				glVertex2i(100, 100);
				glVertex2i(200, 200);
			}
			glEnd();
			
			glBindTexture(GL_TEXTURE_2D, 2);

			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 0); // binding the upper left corner of the
									// texture to the upper left corner cord
				glVertex2i(200, 250); // Upper Left
				glTexCoord2f(1, 0);
				glVertex2i(350, 250); // Upper Right
				glTexCoord2f(1, 1);
				glVertex2i(350, 400); // Bottom Right
				glTexCoord2f(0, 1);
				glVertex2i(200, 400); // Bottom Left
			}
			glEnd();

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}

	private Texture loadTexture(String key) {
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream(new File("res/" + key + ".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			new TextureDemo();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
