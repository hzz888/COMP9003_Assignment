package gremlins;

import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;

import java.io.*;
import processing.event.KeyEvent;


/**
 * @author hzz
 */
public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    // public static final int BOTTOMBAR_HEIGHT = 60;

    public static final int FPS = 60;
    public static final Random RANDOM_GENERATOR = new Random();

    public static int x = 0;
    public static int y = 0;
    public String configPath;
    public String layOutName;

    public PImage exitImage;
    public PImage brickWallImage;
    public PImage stoneWallImage;

    public PImage gremlinImage;
    public PImage wizardImage;
    public PImage slimeImage;
    public PImage fireballImage;

    public double wizardCooldown;
    public double enemyCooldown;
    public int wizardLife;

    public static final String ROOT_PATH = System.getProperty("user.dir");
    public int level;
    public int totalLevels;
    public JSONObject conf;

    protected AbstractObject[][] map = new AbstractObject[33][36];

    public Wizard player;
    public List<Gremlin> gremlins;

    public List<LifeIndicator> lifeIndicators;


    public App() {
        //construct objects here
        this.configPath = "config.json";
        this.conf = loadJSONObject(new File(this.configPath));
        this.level = 1;
        this.layOutName = this.conf.getJSONArray("levels").getJSONObject(this.level - 1).getString("layout");
        this.totalLevels = this.conf.getJSONArray("levels").size();
        this.wizardLife = this.conf.getInt("lives");
        this.wizardCooldown = this.conf.getJSONArray("levels").getJSONObject(level - 1).getDouble("wizard_cooldown");
        this.enemyCooldown = this.conf.getJSONArray("levels").getJSONObject(level - 1).getDouble("enemy_cooldown");
        this.lifeIndicators = new ArrayList<>(this.totalLevels);
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

        background(197, 151, 113);
        textSize(18);
        frameRate(FPS);
        surface.setTitle("Gremlins");
        surface.setResizable(false);

        // Load images during setup
        this.exitImage = loadImage(Objects.requireNonNull(this.getClass().getResource("exit.png")).getPath());
        this.brickWallImage = loadImage(Objects.requireNonNull(this.getClass().getResource("brickwall.png")).getPath());
        this.stoneWallImage = loadImage(Objects.requireNonNull(this.getClass().getResource("stonewall.png")).getPath());
        this.gremlinImage = loadImage(Objects.requireNonNull(this.getClass().getResource("gremlin.png")).getPath());
        this.wizardImage = loadImage(Objects.requireNonNull(this.getClass().getResource("wizard1.png")).getPath());
        this.slimeImage = loadImage(Objects.requireNonNull(this.getClass().getResource("slime.png")).getPath());
        this.fireballImage = loadImage(Objects.requireNonNull(this.getClass().getResource("fireball.png")).getPath());

        //Load map
        generateMap();


        //display bottom bar content
        text("Lives:", 10, 700);
        text("Level", 300, 700);
        text(this.level, 350, 700);
        text('/', 365, 700);

        //display total levels
        text(this.totalLevels, 380, 700);
        initLife();

    }

    static final int LEFT = 37;
    static final int RIGHT = 39;
    static final int UP = 38;
    static final int DOWN = 40;

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

        //display lifes

    }

    public void generateMap() {
        // Load map from config file

        File layOutFile = new File(ROOT_PATH + "/" + this.layOutName);

        try (Scanner layOutScanner = new Scanner(layOutFile)) {
            int i = 0;
            int j = 0;
            char[] line;

            while (layOutScanner.hasNextLine()) {
                line = layOutScanner.nextLine().toCharArray();
                for (char c : line) {
                    AbstractObject tmp;
                    switch (c) {
                        case 'X':
                            tmp = new StoneWall(this, x, y);
                            map[i][j] = tmp;
                            break;
                        case 'W':
                            this.player = new Wizard(this, x, y);
                            map[i][j] = null;
                            break;
                        case 'B':
                            tmp = new BrickWall(this, x, y);
                            map[i][j] = tmp;
                            break;
                        case 'E':
                            tmp = new Exit(this, x, y);
                            map[i][j] = tmp;
                            break;
                        case ' ':
                        default:
                            map[i][j] = null;
                            break;
                    }

                    x += SPRITESIZE;
                    j++;
                }
                // reset x to 0 after each line
                x = 0;
                y += SPRITESIZE;
                j = 0;
                i++;
            }
            //reset x and y after map generation
            x = 0;
            y = 0;
        } catch (IOException e) {
            System.out.println("Config file not found");
            throw new RuntimeException(e);
        }
    }


    public void initLife() {
        for (int i = 65, j = 0; j < this.wizardLife; i += App.SPRITESIZE, j++) {
            this.lifeIndicators.add(new LifeIndicator(this, i, 685));
        }
    }


    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
