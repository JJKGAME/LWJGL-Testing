package examples;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class LWJGLHelloWorld {

	public LWJGLHelloWorld() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Hello, LWJGL");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		// initialization code for OpenGL

		while (!Display.isCloseRequested()) {
			// Render
			Display.update();
			Display.sync(60);
		}

	}
	
	public static void main(String[] args) {
		new LWJGLHelloWorld();
	}
}