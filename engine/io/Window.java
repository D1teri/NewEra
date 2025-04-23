package engine.io;

import engine.maths.Vector3f;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
    private int width, height;
    private String title;
    private long window;
    private int frames;
    private static long time;
    private Input input;
    private Vector3f background = new Vector3f(0,0,0);

    public Window(int width, int height, String title) { 
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() {
        if (!GLFW.glfwInit()) {
            System.err.println("ERROR: GLFW wasn't initializied");
            return;
        }
        //error catch

        input = new Input();
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0); //creates an actual window

        if (window == 0) {
            System.err.println("ERROR: Window wasn't created");
            return;
        }
        //eror catch

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()); 
        //reads monitor characteristics and sets mode for the videoMode
        GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        //setting the window's position on monitor
        GLFW.glfwMakeContextCurrent(window);
        //tieing window to the thread
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        //tests the depth of every pixel for render, to make things behind not being drawn

        createCallbacks();

        GLFW.glfwShowWindow(window);

        GLFW.glfwSwapInterval(1);

        time = System.currentTimeMillis();
    }

    private void createCallbacks() {
        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
    }

    public void update() {
        GL11.glClearColor(background.getX(), background.getY(), background.getZ(), 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        
        //clear color and depth buffers
        GLFW.glfwPollEvents();
        //function to process all pending events
        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(window, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
        //FPS counter
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
        //updates buffers to update the frames, prevent artifacts
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void destroy() {
        input.destroy();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public void setBackgroundColor(float r, float g, float b) {
        background.set(r, g, b);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindow() {
        return window;
    }
}