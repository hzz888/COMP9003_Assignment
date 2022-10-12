package gremlins;

import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.event.KeyEvent;

import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;


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
    public PImage wizardLeftImage;
    public PImage wizardRightImage;
    public PImage wizardUpImage;
    public PImage wizardDownImage;
    public PImage slimeImage;
    public PImage fireballImage;
    public PImage powerupImage;
    public PImage[] brickWallDestroyImages;

    public float wizardCooldown;
    public float enemyCooldown;
    public int wizardLife;

    public static final String ROOT_PATH = System.getProperty("user.dir");
    public int level;
    public int totalLevels;
    public JSONObject conf;

    private final int MAP_WIDTH_TILES = 33;
    private final int MAP_HEIGHT_TILES = 36;
    protected AbstractObject[][] map = new AbstractObject[this.MAP_WIDTH_TILES][this.MAP_HEIGHT_TILES];

    public Wizard player;
    public CopyOnWriteArrayList<Gremlin> gremlins;
    public CopyOnWriteArrayList<Fireball> fireballs;

    public int attackTimer;
    public int currentTimer;
    public boolean wizardCooling;

    public App() {
        //construct objects here
        this.configPath = "config.json";
        this.conf = loadJSONObject(new File(this.configPath));
        this.level = 1;
        this.totalLevels = this.conf.getJSONArray("levels").size();
        this.wizardLife = this.conf.getInt("lives");
        this.wizardCooldown = this.conf.getJSONArray("levels").getJSONObject(level - 1).getFloat("wizard_cooldown");
        this.enemyCooldown = this.conf.getJSONArray("levels").getJSONObject(level - 1).getFloat("enemy_cooldown");
        this.player = null;
        this.gremlins = new CopyOnWriteArrayList<>();
        this.fireballs = new CopyOnWriteArrayList<>();
        this.brickWallDestroyImages = new PImage[4];
    }


    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(App.WIDTH, App.HEIGHT);
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
        this.wizardLeftImage = loadImage(Objects.requireNonNull(this.getClass().getResource("wizard0.png")).getPath());
        this.wizardRightImage = loadImage(Objects.requireNonNull(this.getClass().getResource("wizard1.png")).getPath());
        this.wizardUpImage = loadImage(Objects.requireNonNull(this.getClass().getResource("wizard2.png")).getPath());
        this.wizardDownImage = loadImage(Objects.requireNonNull(this.getClass().getResource("wizard3.png")).getPath());
        this.slimeImage = loadImage(Objects.requireNonNull(this.getClass().getResource("slime.png")).getPath());
        this.fireballImage = loadImage(Objects.requireNonNull(this.getClass().getResource("fireball.png")).getPath());
        this.powerupImage = loadImage(Objects.requireNonNull(this.getClass().getResource("powerup.png")).getPath());
        this.brickWallDestroyImages[0] = loadImage(Objects.requireNonNull(this.getClass().getResource("brickwall_destroyed0.png")).getPath());
        this.brickWallDestroyImages[1] = loadImage(Objects.requireNonNull(this.getClass().getResource("brickwall_destroyed1.png")).getPath());
        this.brickWallDestroyImages[2] = loadImage(Objects.requireNonNull(this.getClass().getResource("brickwall_destroyed2.png")).getPath());
        this.brickWallDestroyImages[3] = loadImage(Objects.requireNonNull(this.getClass().getResource("brickwall_destroyed3.png")).getPath());


        if (validMap()) {
            //Load map
            initMap();
        } else {
            stop();
            fill(255, 255, 255);
            text("Invalid map", 350, 350);
        }


    }

    static final int LEFT = 37;
    static final int RIGHT = 39;
    static final int UP = 38;
    static final int DOWN = 40;
    static final int SPACE = 32;

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case App.LEFT:
                this.player.move(this,"left");
                break;
            case App.UP:
                this.player.move(this,"up");
                break;
            case App.RIGHT:
                this.player.move(this,"right");
                break;
            case App.DOWN:
                this.player.move(this,"down");
                break;
            case App.SPACE:
                this.player.attack(this);
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
    }


    /**
     * Draw all elements in the game by current frame.
     */

    @Override
    public void draw() {
        //Main loop here

        background(197, 151, 113);

        this.displayMap();

        //Display lives
        this.displayLife();

        //Display level information
        this.displayLevels();


        // Display wizard each frame
        this.displayPlayer();

        this.displayFireBalls();

        //Display gremlins each frame
        this.displayGremlins();

        if (this.wizardCooling) {
            this.wizardCoolDown();
        }


    }


    public boolean validMap() {
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
                row++;

                for (char c : line) {
                    column++;

                    if (column == 1 || column == 36 || row == 1 || row == 33) {
                        if (c != 'X') {
                            warpRequire = false;
                        }
                    }

                    if (c != 'X' && c != 'B' && c != ' ' && c != 'G' && c != 'W' && c != 'E') {
                        legalRequire = false;
                    }

                    if (c == 'E') {
                        exitNum++;
                    } else if (c == 'G') {
                        gremlinNum++;
                    } else if (c == 'W') {
                        startNum++;
                    }

                }

                if (column != 36) {
                    columnRequire = false;
                }

                column = 0;

            }

            if (row != 33) {
                rowRequire = false;
            }

            numberRequire = exitNum >= 1 && gremlinNum >= 1 && startNum == 1;

            return legalRequire && numberRequire && columnRequire && rowRequire && warpRequire;


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


    private void displayMap() {
        for (int i = 0; i < this.MAP_WIDTH_TILES; i++) {
            for (int j = 0; j < this.MAP_HEIGHT_TILES; j++) {
                if (this.map[i][j] != null) {
                    this.map[i][j].tick(this);
                }
            }
        }
    }


    public void displayLife() {
        fill(255, 255, 255);
        text("Lives:", 10, 700);
        for (int i = 65, j = 0; j < this.wizardLife; i += App.SPRITESIZE, j++) {
            image(this.wizardRightImage, i, 685);
        }
    }

    public void displayFireBalls() {

        for (Fireball fireBall : this.fireballs) {
            fireBall.tick(this);
        }
    }

    public void displayGremlins() {
        for (Gremlin gremlin : this.gremlins) {
            gremlin.tick(this);
        }
    }

    public void displayPlayer() {
        this.player.tick(this);
    }

    public void displayLevels() {
        fill(255, 255, 255);
        text("Level", 300, 700);
        text(this.level, 350, 700);
        text('/', 365, 700);
        text(this.totalLevels, 380, 700);
    }

    public void wizardCoolDown() {
        this.currentTimer = millis();
        if (this.currentTimer - this.attackTimer <= this.wizardCooldown * 1000) {
            stroke(0);
            strokeWeight(2);
            fill(255, 255, 255);
            rect(550, 685, 100, 10);
            fill(0, 0, 0);
            float coolDownBarWidth = (float) ((this.currentTimer - this.attackTimer) / 1000.0 / this.wizardCooldown * 100);
            rect(551, 686, coolDownBarWidth, 8);
        } else {
            this.wizardCooling = false;
            this.attackTimer = 0;
        }
    }

    public int getMapX(AbstractObject object) {
        return object.getY() / App.SPRITESIZE;
    }

    public int getMapY(AbstractObject object) {
        return object.getX() / App.SPRITESIZE;
    }

    public int getImageX(int y) {
        return y * App.SPRITESIZE;
    }

    public int getImageY(int x) {
        return x * App.SPRITESIZE;
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
