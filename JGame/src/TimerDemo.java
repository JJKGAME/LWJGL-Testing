import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class TimerDemo {

	private long lastFrame;

	private long getTime() {
		return (Sys.getTime() * 1000 / Sys.getTimerResolution());
	}

	private int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = currentTime;
		return delta;
	}

	public TimerDemo() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Timer Demo");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		int x = 100;
		int y = 100;
		int dx = 1;
		int dy = 1;

		// initialization code for OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		lastFrame = getTime();

		while (!Display.isCloseRequested()) {
			// Render

			glClear(GL_COLOR_BUFFER_BIT);

			int delta = getDelta();
			x += delta * dx * .1;
			y += delta * dy * .1;

			glRecti(x, y, x + 30, y + 30);

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}

	public static void main(String[] args) {
		new TimerDemo();
	}

}
