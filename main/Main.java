package main;
import engine.Window;

public class Main implements Runnable{
    public Thread game;
    public Window window;
    public final int WIDTH = 1080, HEIGHT = 720;

    
    public void start(){
        game = new Thread(this, "game");
        game.start();
    }
    
    public void init(){
        System.out.println("Initializing Game!");
        window = new Window(WIDTH, HEIGHT, "Game");
        window.create();
    }
    
    public void run(){
        init();
        while(!window.shouldClose()){
            update();
            render();
        }
    }
    
    public  void update(){
        //System.out.println("Updating the Game!");
        window.update();
    }
    
    public  void render(){
        //System.out.println("Rendering the Game!");
        window.swapBuffers();
    }
    
    public static void main(String[] args){
        new Main().start();
    }
}
