package main;

import org.lwjgl.glfw.GLFW;

import engine.maths.Vector3f;
import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Vertex;
import engine.io.Window;
import engine.io.Input;

public class Main implements Runnable{
    public Thread game;
    public Window window;
    public Renderer renderer;
    public final int WIDTH = 1080, HEIGHT = 720;
    
    public Mesh mesh = new Mesh(new Vertex[] {
        new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f)),
        new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f)),
        new Vertex(new Vector3f(0.5f, 0.5f, 0.0f)),
        new Vertex(new Vector3f(0.5f, -0.5f, 0.0f)),
    }, new int[]{
        0, 1, 2,
        2, 3, 0
    });

    public void start(){
        game = new Thread(this, "game");
        game.start();
    }

    public void init(){
        System.out.println("Initializing Game!");
        window = new Window(WIDTH, HEIGHT, "Game");
        renderer = new Renderer();
        window.setBackgroundColor(1.0f, 0, 0);
        window.create();
        mesh.create();
    }

    public void run(){
        init();
        while(!window.shouldClose()){
            update();
            render();
            if(Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) return;
        }
    }

    public  void update(){
        //System.out.println("Updating the Game!");
        window.update();
    }

    public  void render(){
        //System.out.println("Rendering the Game!");
        renderer.renderMesh(mesh);
        window.swapBuffers();
    }

    public static void main(String[] args){
        new Main().start();
    }
}
