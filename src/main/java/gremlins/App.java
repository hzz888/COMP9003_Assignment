package gremlins;

import java.util.Objects;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;

import java.util.Random;
import java.io.*;
import java.util.Scanner;


/**
 * @author hzz
 */
public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR_HEIGHT = 60;
    public static final int FPS = 60;
    public static final Random RANDOM_GENERATOR = new Random();

    public static int x = 0;
    public static int y = 0;
    public String configPath;

    public PImage exit;
    public PImage brickWall;
    public PImage stoneWall;

    public PImage gremlin;
    public PImage wizard;
    public PImage slime;
    public PImage fireball;

    AbstractTile[][] map = new AbstractTile[33][36];

    public App() {
        //construct objects here
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map
     * elements.
     */
    @Override
    public void setup() {
        //load images here

        frameRate(FPS);
        surface.setTitle("Gremlins-by hzz");
        surface.setResizable(false);

        // Load images during setup
        this.exit = loadImage(Objects.requireNonNull(this.getClass().getResource("/exit.png")).getPath());
        this.brickWall = loadImage(Objects.requireNonNull(this.getClass().getResource("/brickwall.png")).getPath());
        this.stoneWall = loadImage(Objects.requireNonNull(this.getClass().getResource("/stonewall.png")).getPath());
        this.gremlin = loadImage(Objects.requireNonNull(this.getClass().getResource("/gremlin.png")).getPath());
        this.wizard = loadImage(Objects.requireNonNull(this.getClass().getResource("/wizard.png")).getPath());
        this.slime = loadImage(Objects.requireNonNull(this.getClass().getResource("/slime.png")).getPath());
        this.fireball = loadImage(Objects.requireNonNull(this.getClass().getResource("/fireball.png")).getPath());

        // Load map from config file
        JSONObject conf = loadJSONObject(new File(this.configPath));
        int level = 0;
//        String layOutPath = "../../" + conf.getJSONArray("levels").getJSONObject(level).getString("layout");
//        File layOutFile = new File(Objects.requireNonNull(this.getClass().getResource(layOutPath)).getFile());
//        try {
//            Scanner layOutScanner = new Scanner(layOutFile);
//            while (layOutScanner.hasNextLine()){
//                char[] line = layOutScanner.nextLine().toCharArray();
//                for(char c : line){
//
//                }
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }


    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed() {

    }

    /**
     * Receive key released signal from the keyboard.
     */

    @Override
    public void keyReleased() {

    }


    /**
     * Draw all elements in the game by current frame.
     */

    @Override
    public void draw() {
        //Main loop here
        background(197, 151, 113);
        textSize(40);

    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
