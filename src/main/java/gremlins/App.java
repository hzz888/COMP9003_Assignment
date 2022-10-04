package gremlins;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import gremlins.tileset.AbstractTile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;

import java.util.Random;
import java.io.*;
import java.util.Scanner;
import java.util.stream.Stream;


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

    public int wizardCooldown;
    public int enemyCooldown;
    public int wizardLife;

    public static String rootPath = System.getProperty("user.dir");

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
        surface.setTitle("Gremlins");
        surface.setResizable(false);
        background(197, 151, 113);
        textSize(18);

        // Load images during setup
        this.exit = loadImage(Objects.requireNonNull(this.getClass().getResource("exit.png")).getPath());
        this.brickWall = loadImage(Objects.requireNonNull(this.getClass().getResource("brickwall.png")).getPath());
        this.stoneWall = loadImage(Objects.requireNonNull(this.getClass().getResource("stonewall.png")).getPath());
        this.gremlin = loadImage(Objects.requireNonNull(this.getClass().getResource("gremlin.png")).getPath());
        this.wizard = loadImage(Objects.requireNonNull(this.getClass().getResource("wizard1.png")).getPath());
        this.slime = loadImage(Objects.requireNonNull(this.getClass().getResource("slime.png")).getPath());
        this.fireball = loadImage(Objects.requireNonNull(this.getClass().getResource("fireball.png")).getPath());

        //Load map
        generateMap();
        text("Lives:",10,700);
        text("Level",200,700);



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

    }

    public void generateMap() {
        // Load map from config file
        JSONObject conf = loadJSONObject(new File(this.configPath));
        int level = 0;
        String layOutName = conf.getJSONArray("levels").getJSONObject(level).getString("layout");
        File layOutFile = new File(rootPath + "/" + layOutName);
        try (Scanner layOutScanner = new Scanner(layOutFile)) {

            char[] line = new char[36];
            while (layOutScanner.hasNextLine()) {
                line = layOutScanner.nextLine().toCharArray();
                for (char c : line) {
                    switch (c) {
                        case 'X':
                            image(this.stoneWall, x, y);
                            break;
                        case 'W':
                            image(this.wizard, x, y);
                            break;
                        case 'B':
                            image(this.brickWall, x, y);
                            break;
                        case 'E':
                            image(this.exit, x, y);
                            break;
                        case ' ':
                        default:
                            break;
                    }

                    x += SPRITESIZE;
                }
                x = 0;
                // reset x to 0 after each line
                y += SPRITESIZE;
            }
            //reset x and y after map generation
            x = 0;
            y = 0;
        } catch (IOException e) {
            System.out.println("Config file not found");
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
