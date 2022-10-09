package gremlins;

import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;

import java.io.*;


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
    public String layOutName = "";

    public PImage exitImage;
    public PImage brickWallImage;
    public PImage stoneWallImage;

    public PImage gremlinImage;
    public PImage wizardImage;
    public PImage slimeImage;
    public PImage fireballImage;
    public PImage powerupImage;

    public double wizardCooldown;
    public double enemyCooldown;
    public int wizardLife;

    public static final String ROOT_PATH = System.getProperty("user.dir");
    public int level;
    public int totalLevels;
    public JSONObject conf;

    private final int MAP_WIDTH_TILES = 33;
    private final int MAP_HEIGHT_TILES = 36;
    protected AbstractObject[][] map = new AbstractObject[this.MAP_WIDTH_TILES][this.MAP_HEIGHT_TILES];

    public Wizard player;
    public List<Gremlin> gremlins;


    public App() {
        //construct objects here
        this.configPath = "config.json";
        this.conf = loadJSONObject(new File(this.configPath));
        this.level = 1;

        this.totalLevels = this.conf.getJSONArray("levels").size();
        this.wizardLife = this.conf.getInt("lives");
        this.wizardCooldown = this.conf.getJSONArray("levels").getJSONObject(level - 1).getDouble("wizard_cooldown");
        this.enemyCooldown = this.conf.getJSONArray("levels").getJSONObject(level - 1).getDouble("enemy_cooldown");
        this.player = null;
        this.gremlins = new ArrayList<>();
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


        textSize(18);
        frameRate(FPS);
        smooth();
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
        this.powerupImage = loadImage(Objects.requireNonNull(this.getClass().getResource("powerup.png")).getPath());

        //Load map
        initMap();


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

        this.player.moveSpeed = 2;

        switch (this.keyCode) {
            case App.LEFT:
                this.player.setDirection("left");
                break;
            case App.UP:
                this.player.setDirection("up");
                break;
            case App.RIGHT:
                this.player.setDirection("right");
                break;
            case App.DOWN:
                this.player.setDirection("down");
                break;
            default:
                break;
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */

    @Override
    public void keyReleased() {
        this.player.stop();
        if (this.player.getX() % SPRITESIZE != 0 || this.player.getY() % SPRITESIZE != 0) {

        }

    }


    /**
     * Draw all elements in the game by current frame.
     */

    @Override
    public void draw() {
        //Main loop here

        background(197, 151, 113);

        displayMap();

        //Display lives
        text("Lives:", 10, 700);
        displayLife();

        //Display level information
        text("Level", 300, 700);
        text(this.level, 350, 700);
        text('/', 365, 700);
        text(this.totalLevels, 380, 700);

        // Display wizard each frame
        this.player.tick();
        this.player.draw(this);

        //Display gremlins each frame
        for (Gremlin gremlin : this.gremlins) {
            gremlin.tick();
            gremlin.draw(this);
        }

    }


    private void displayMap() {
        for (int i = 0; i < this.MAP_WIDTH_TILES; i++) {
            for (int j = 0; j < this.MAP_HEIGHT_TILES; j++) {
                if (this.map[i][j] != null) {
                    this.map[i][j].draw(this);
                }
            }
        }
    }


    public boolean validMap1() {
        // Load map from config file
        this.layOutName = this.conf.getJSONArray("levels").getJSONObject(this.level - 1).getString("layout");
        File layOutFile = new File(App.ROOT_PATH + "/" + this.layOutName);
        int exitNum = 0;
        int gremlinNum = 0;
        int startNum = 0;
        int column = 0;
        int row = 0;
        boolean numberRequire = true;
        boolean columnRequire = true;
        boolean rowRequire = true;
        boolean legalRequire = true;
        boolean warpRequire = true;
        try (Scanner validScanner = new Scanner(layOutFile)) {
            char[] line;
            while (validScanner.hasNextLine()) {
                line = validScanner.nextLine().toCharArray();

                for (char c : line) {
                    if (c != 'X' && c != 'B' && c != ' ' && c != 'G' && c != 'W' && c != 'E') {
                        legalRequire = false;
                    } else {
                        if (c == 'E') {
                            exitNum++;
                        } else if (c == 'G') {
                            gremlinNum++;
                        } else if (c == 'W') {
                            startNum++;
                        }

                    }
                    column++;
                }

                if (column != 36) {
                    columnRequire = false;
                }

                column = 0;
                row++;
            }

            if (row != 33) {
                rowRequire = false;
            }

            numberRequire = exitNum >= 1 && gremlinNum >= 1 && startNum == 1;
            return legalRequire && numberRequire && columnRequire && rowRequire;


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    public void initMap() {
        // Load map from config file
        this.layOutName = this.conf.getJSONArray("levels").getJSONObject(this.level - 1).getString("layout");
        File layOutFile = new File(App.ROOT_PATH + "/" + this.layOutName);

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
                        case 'G':
                            tmp = new Gremlin(this, x, y);
                            this.gremlins.add((Gremlin) tmp);
                            map[i][j] = null;
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


    public void displayLife() {
        for (int i = 65, j = 0; j < this.wizardLife; i += App.SPRITESIZE, j++) {
            new LifeIndicator(this, i, 685);
        }
    }


    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
