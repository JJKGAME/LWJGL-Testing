package examples;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;

import utility.Camera;
import utility.EulerCamera;
import utility.Model;
import utility.OBJLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

/**
 * Loads the Stanford bunny .obj file and draws it.
 * 
 * @author Oskar Veerhoek
 */
public class ModelDemo {

	private static Camera camera;
	private static int bunnyDisplayList, cubeDisplayList, octDisplayList;

	private static final String BUNNY = "res/models/bunny.obj";
	private static final String CUBE = "res/models/cube.obj";
	private static final String OCT = "res/models/octogon.obj";

	private static boolean displayBunny = false, displayCube = false, displayOct = true;

	public static void main(String[] args) {
		setUpDisplay();
		setUpDisplayLists();
		setUpCamera();
		while (!Display.isCloseRequested()) {
			render();
			checkInput();
			Display.update();
			Display.sync(60);
		}
		cleanUp();
		System.exit(0);
	}

	private static void setUpDisplayLists() {
		bunnyDisplayList = glGenLists(1);
		genLists(bunnyDisplayList, BUNNY, "Bunny");
		cubeDisplayList = glGenLists(1);
		genLists(cubeDisplayList, CUBE, "Cube");
		octDisplayList = glGenLists(1);
		genLists(octDisplayList, OCT, "Octogon");

	}

	private static void genLists(int list, String location, String name) {
		glNewList(list, GL_COMPILE);
		{
			Model m = null;
			try {
				m = OBJLoader.loadModel(new File(location));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Display.destroy();
				System.exit(1);
			} catch (IOException e) {
				e.printStackTrace();
				Display.destroy();
				System.exit(1);
			}

			loadFaces(m, name);

		}
		glEndList();
	}

	private static void loadFaces(Model m, String name) {
		if (name == "Cube") {
			glColor3f(1, 0, 0);
		} else if (name == "Bunny") {
			glColor3f(1, 1, 1);
		} else if (name == "Octogon") {
			glColor3f(0, 1, 0);
		//	glColor3f(1, 1, 1);
		}
		glBegin(GL_TRIANGLES);
		for (Model.Face face : m.getFaces()) {
			Vector3f n1 = m.getNormals().get(face.getNormalIndices()[0] - 1);
			glNormal3f(n1.x, n1.y, n1.z);
			Vector3f v1 = m.getVertices().get(face.getVertexIndices()[0] - 1);
			glVertex3f(v1.x, v1.y, v1.z);
			Vector3f n2 = m.getNormals().get(face.getNormalIndices()[1] - 1);
			glNormal3f(n2.x, n2.y, n2.z);
			Vector3f v2 = m.getVertices().get(face.getVertexIndices()[1] - 1);
			glVertex3f(v2.x, v2.y, v2.z);
			Vector3f n3 = m.getNormals().get(face.getNormalIndices()[2] - 1);
			glNormal3f(n3.x, n3.y, n3.z);
			Vector3f v3 = m.getVertices().get(face.getVertexIndices()[2] - 1);
			glVertex3f(v3.x, v3.y, v3.z);
			/*
			 * if (MODEL_LOCATION == "res/models/cube.obj") {
			 * System.out.println(v1 + " " + v2 + " " + v3);
			 * System.out.println(n1 + " " + n2 + " " + n3); }
			 */
		}
		glEnd();
	}

	private static void checkInput() {
		camera.processMouse(1, 80, -80);
		camera.processKeyboard(16, 1, 1, 1);
		if (Mouse.isButtonDown(0)) {
			Mouse.setGrabbed(true);
		} else if (Mouse.isButtonDown(1)) {
			Mouse.setGrabbed(false);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Mouse.setGrabbed(false);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
			displayBunny = true;
			displayCube = false;
			displayOct = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
			displayBunny = false;
			displayCube = true;
			displayOct = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
			displayOct = true;
			displayBunny = false;
			displayCube = false;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_4)){
			
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
			displayBunny = false;
			displayCube = false;
			displayOct = false;
		}
	}

	private static void cleanUp() {
		glDeleteLists(bunnyDisplayList, 1);
		Display.destroy();
	}

	private static void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		camera.applyTranslations();
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		if (displayBunny) {
			glCallList(bunnyDisplayList);
		}
		if (displayCube) {
			glCallList(cubeDisplayList);
		}
		if(displayOct){
			glCallList(octDisplayList);
		}
	}

	private static void setUpCamera() {
		camera = new EulerCamera.Builder().setAspectRatio((float) Display.getWidth() / Display.getHeight()).setRotation(-1.12f, 0.16f, 0f).setPosition(-1.38f, 1.36f, 7.95f).setFieldOfView(60).build();
		camera.applyOptimalStates();
		camera.applyPerspectiveMatrix();
	}

	private static void setUpDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setVSyncEnabled(true);
			Display.setTitle("Happy Easter!");
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("The display wasn't initialized correctly. :(");
			Display.destroy();
			System.exit(1);
		}
	}
}
