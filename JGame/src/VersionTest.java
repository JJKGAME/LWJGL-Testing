import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;


public class VersionTest {
	public static void main(String[] args) throws LWJGLException{
		Display.create();
		System.out.println(GL11.glGetString(GL11.GL_VERSION));
		Display.destroy();
	}
}
