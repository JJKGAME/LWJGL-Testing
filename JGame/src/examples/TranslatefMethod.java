package examples;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class TranslatefMethod {

	public TranslatefMethod() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Translate F");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		// initialization code for OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		float mx = 0, my = 0, dx = 0, dy = 0;

		while (!Display.isCloseRequested()) {
			// Render

			glClear(GL_COLOR_BUFFER_BIT);

			glPushMatrix();

			glTranslatef(dx, dy, 0);

			mx = Mouse.getX() - dx;
			my = 480 - Mouse.getY() - 1;

		//	System.out.println(mx + "," + my);

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && Mouse.isButtonDown(0)) {
				dx += Mouse.getDX();
				dy += -Mouse.getDY();
			}

			glBegin(GL_QUADS);
			glVertex2i(400, 400); // Upper Left
			glVertex2i(450, 400); // Upper Right
			glVertex2i(450, 450); // Bottom Right
			glVertex2i(400, 450); // Bottom Left
			glEnd();

			glBegin(GL_LINES);
			glVertex2i(100, 100);
			glVertex2i(200, 200);
			glEnd();

			glPopMatrix();

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}

	public static void main(String[] args) {
		new TranslatefMethod();
	}

}
