package gremlins;

import java.util.*;
import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.event.KeyEvent;


/**
 * The main class for the Gremlins game.
 * @author hzz
 */
public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;

    // public static final int BOTTOMBAR_HEIGHT = 60;


    public static final int FPS = 60;

    public static final Random RANDOM_GENERATOR = new Random();

    public int x = 0;
    public int y = 0;
    public int playerStartX;
    public int playerStartY;
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
    public PImage eliteEnemyImage;
    public PImage transportDoorImage;
    public PImage[] brickWallDestroyImages;

    public float wizardCooldown;
    public float enemyCooldown;
    public int wizardLife;

    public static final String ROOT_PATH = System.getProperty("user.dir");
    public int level;
    public int totalLevels;
    public JSONObject conf;

    public static final int MAP_WIDTH_TILES = 33;
    public static final int MAP_HEIGHT_TILES = 36;
    public boolean gameStarted;
    public boolean gameOver;
    public boolean gameWon;

    protected AbstractObject[][] map;

    public Wizard player;
    public CopyOnWriteArrayList<Gremlin> gremlins;
    public CopyOnWriteArrayList<Fireball> fireballs;

    public int wizardAttackTimer;
    public int levelInitTimer;
    public int wizardCoolDownTimer;
    public boolean wizardCooling;
    public Exit exit;
    public List<Powerup> powerups;

    public List<Slime> slimes;
    public List<BrickWallDestruction> brickWallDestructions;
    public List<TransportDoor> transportDoors;
    public List<int[]> emptyTiles;
    public int powerUpSpawnTime;
    public int gameStopTimer;
    public int restartTimer;

    /**
     * The constructor of the App class.
     * Initiate basic variables.
     */
    public App() {
        //construct objects here
        this.x = 0;
        this.y = 0;
        this.configPath = "config.json";
        this.conf = loadJSONObject(new File(this.configPath));
        this.level = 1;
        this.totalLevels = this.conf.getJSONArray("levels").size();
        this.map = new AbstractObject[App.MAP_WIDTH_TILES][App.MAP_HEIGHT_TILES];
        this.wizardLife = this.conf.getInt("lives");
        this.player = null;
        this.exit = null;
        this.gremlins = new CopyOnWriteArrayList<>();
        this.slimes = new CopyOnWriteArrayList<>();
        this.fireballs = new CopyOnWriteArrayList<>();
        this.brickWallDestroyImages = new PImage[4];
        this.powerups = new CopyOnWriteArrayList<>();
        this.brickWallDestructions = new CopyOnWriteArrayList<>();
        this.transportDoors = new CopyOnWriteArrayList<>();
        this.emptyTiles = new CopyOnWriteArrayList<>();
        this.powerUpSpawnTime = 8;
        this.gameStarted = false;
        this.gameOver = false;
        this.gameWon = false;
    }


    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(App.WIDTH, App.HEIGHT);
    }

    /**
     * Load all resources such as images.
     * Initialise the elements such as the player, enemies and map elements.
     */
    @Override
    public void setup() {

        textSize(18);
        frameRate(App.FPS);
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
        this.eliteEnemyImage = loadImage(Objects.requireNonNull(this.getClass().getResource("eliteEnemy.png")).getPath());
        this.transportDoorImage = loadImage(Objects.requireNonNull(this.getClass().getResource("transportDoor.png")).getPath());

        this.loadMap();

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
        if (this.gameStarted) {
            if (!this.gameWon && !this.gameOver) {
                switch (keyCode) {
                    case App.LEFT:
                        this.player.wizardMove(this, "left");
                        break;
                    case App.UP:
                        this.player.wizardMove(this, "up");
                        break;
                    case App.RIGHT:
                        this.player.wizardMove(this, "right");
                        break;
                    case App.DOWN:
                        this.player.wizardMove(this, "down");
                        break;
                    case App.SPACE:
                        this.player.wizardAttack(this);
                        break;
                    default:
                        break;
                }
            } else {
                if (keyCode >= 0) {
                    this.restartTimer = millis();
                    if (this.restartTimer - this.gameStopTimer >= 1000) {
                        this.restart();
                    } else {
                        text("Please wait 1 second.", 250, 600);
                    }
                }
            }
        } else {
            if (keyCode >= 0) {
                this.gameStarted = true;
                this.levelInitTimer = millis();
            }
        }
    }


    /**
     * Receive key released signal from the keyboard.
     */

    @Override
    public void keyReleased() {
        this.player.wizardStop();
    }


    /**
     * Draw all elements in the game each frame.
     */
    @Override
    public void draw() {
        //Main loop here, execute per frame.
        background(197, 151, 113);

        if (!this.gameStarted) {

            this.displayWelcome();

        } else {

            this.displayMap();

            this.updateEmptyTiles();

            this.displayLife();

            this.displayLevels();

            this.displayPlayer();

            this.displayFireBalls();

            this.displayGremlins();

            this.displaySlimes();

            this.displayExit();

            this.displayTransportDoors();

            this.displayDestructions();

            this.displayPowerups();

            this.displayWinOrLose();
        }
    }

    /**
     * Display the welcome screen.
     */
    private void displayWelcome() {
        fill(255);
        text("Welcome!", 300, 300);
        text("USYD COMP-9003 Project.", 230, 350);
        fill(30, 130, 60);
        text("Press any key to start.", 250, 400);
    }

    /**
     * Check if the map layout is valid.
     * @return true if the map layout is valid, false otherwise.
     */
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

                    if (c != 'X' && c != 'B' && c != ' ' && c != 'G' && c != 'W' && c != 'E' && c != 'P' && c != 'S' && c != 'T') {
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


    /**
     * Initiate map from config file.
     * @exception RuntimeException if the map layout is invalid.
     */
    public void initMap() {
        // Load map from config file
        this.wizardCooldown = this.conf.getJSONArray("levels").getJSONObject(this.level - 1).getFloat("wizard_cooldown");
        this.enemyCooldown = this.conf.getJSONArray("levels").getJSONObject(this.level - 1).getFloat("enemy_cooldown");
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
                            tmp = new StoneWall(this, this.x, this.y);
                            map[i][j] = tmp;
                            break;
                        case 'W':
                            this.player = new Wizard(this, this.x, this.y);
                            this.playerStartX = this.x;
                            this.playerStartY = this.y;
                            map[i][j] = null;
                            break;
                        case 'B':
                            tmp = new BrickWall(this, this.x, this.y);
                            map[i][j] = tmp;
                            break;
                        case 'E':
                            this.exit = new Exit(this, this.x, this.y);
                            map[i][j] = null;
                            break;
                        case 'G':
                            this.gremlins.add(new Gremlin(this, this.x, this.y));
                            map[i][j] = null;
                            break;
                        case 'P':
                            this.powerups.add(new Powerup(this, this.x, this.y));
                            map[i][j] = null;
                            break;
                        case 'S':
                            this.gremlins.add(new EliteEnemy(this, this.x, this.y));
                            map[i][j] = null;
                            break;
                        case 'T':
                            this.transportDoors.add(new TransportDoor(this, this.x, this.y));
                            map[i][j] = null;
                            break;
                        case ' ':
                        default:
                            map[i][j] = null;
                            break;
                    }

                    this.x += SPRITESIZE;
                    j++;
                }
                // reset x to 0 after each line
                this.x = 0;
                this.y += SPRITESIZE;
                j = 0;
                i++;
            }
            //reset x and y after map generation
            this.x = 0;
            this.y = 0;
            this.levelInitTimer = millis();
        } catch (IOException e) {
            System.out.println("Config file not found");
            throw new RuntimeException(e);
        }
    }


    /**
     * Load the map if it is valid.
     * Otherwise, stop program and display error message.
     */
    public void loadMap() {
        if (validMap()) {
            //Load map
            initMap();
        } else {
            fill(255, 255, 255);
            text("Invalid map", 350, 350);
            stop();
        }
    }

    /**
     * Display the map each frame.
     */
    private void displayMap() {
        for (int i = 0; i < App.MAP_WIDTH_TILES; i++) {
            for (int j = 0; j < App.MAP_HEIGHT_TILES; j++) {
                if (this.map[i][j] != null) {
                    this.map[i][j].tick(this);
                }
            }
        }
    }


    /**
     * Display current player lifes each frame.
     */
    public void displayLife() {
        fill(255, 255, 255);
        text("Lives:", 10, 700);
        for (int i = 65, j = 0; j < this.wizardLife; i += App.SPRITESIZE, j++) {
            image(this.wizardRightImage, i, 685);
        }
    }

    /**
     * Display all fireballs currently in the game each frame.
     */
    public void displayFireBalls() {
        for (Fireball fireBall : this.fireballs) {
            fireBall.tick(this);
        }
    }

    /**
     * Display all gremlins currently in the game each frame.
     */
    public void displayGremlins() {
        for (Gremlin gremlin : this.gremlins) {
            gremlin.tick(this);
        }
    }

    /**
     * Display all slimes currently in the game each frame.
     */
    public void displaySlimes() {
        for (Slime slime : this.slimes) {
            slime.tick(this);
        }
    }

    /**
     * Display the wizard each frame.
     */
    public void displayPlayer() {
        this.player.tick(this);
    }

    /**
     * Display the exit each frame.
     */
    public void displayExit() {
        this.exit.tick(this);
    }

    /**
     * Display level information each frame.
     */
    public void displayLevels() {
        fill(255, 255, 255);
        text("Level", 200, 700);
        text(this.level, 250, 700);
        text('/', 265, 700);
        text(this.totalLevels, 280, 700);
    }


    /**
     * Display brickwall destruction animations.
     */
    public void displayDestructions() {
        for (BrickWallDestruction destruction : this.brickWallDestructions) {
            destruction.tick(this);
        }
    }

    /**
     * Display all transport doors.
     */
    public void displayTransportDoors() {
        for (TransportDoor transportDoor : this.transportDoors) {
            transportDoor.tick(this);
        }
    }


    /**
     * Get the first index in the map array of an object.
     * @param object The object to get the index of.
     * @return The first index of the object in the map array.
     */
    public int getMapX(AbstractObject object) {
        return object.getY() / App.SPRITESIZE;
    }

    /**
     * Get the second index in the map array of an object.
     * @param object The object to get the index of.
     * @return The second index of the object in the map array.
     */
    public int getMapY(AbstractObject object) {
        return object.getX() / App.SPRITESIZE;
    }

    /**
     * Get the actual x coordinate in the gui of an object by the second index of the map array.
     * @param y The second index of the map array.
     * @return The actual x coordinate in the gui of an object.
     */
    public int getImageX(int y) {
        return y * App.SPRITESIZE;
    }

    /**
     * Get the actual y coordinate in the gui of an object by the first index of the map array.
     * @param x The first index of the map array.
     * @return The actual y coordinate in the gui of an object.
     */
    public int getImageY(int x) {
        return x * App.SPRITESIZE;
    }

    /**
     * Update the empty tiles list each frame.
     */
    public void updateEmptyTiles() {
        this.emptyTiles.clear();
        for (int i = 0; i < App.MAP_WIDTH_TILES; i++) {
            for (int j = 0; j < App.MAP_HEIGHT_TILES; j++) {
                if (this.map[i][j] == null) {
                    this.emptyTiles.add(new int[]{this.getImageX(j), this.getImageY(i)});
                }
            }
        }
    }


    /**
     * Display all powerups after a certain delay since level loading.
     */
    public void displayPowerups() {
        if (millis() - this.levelInitTimer >= this.powerUpSpawnTime * 1000) {
            for (Powerup powerup : this.powerups) {
                powerup.tick(this);
            }
        }
    }


    /**
     * Reset current level to original state.
     */
    public void resetLevel() {
        this.gremlins.clear();
        this.slimes.clear();
        this.fireballs.clear();
        this.brickWallDestructions.clear();
        this.powerups.clear();
        this.transportDoors.clear();
        this.loadMap();
    }


    /**
     * Display game over screen or winning screen.
     */
    public void displayWinOrLose() {
        if (this.gameOver) {
            background(197, 151, 113);
            text("Game Over", 300, 300);
            text("Press any key to restart", 250, 400);
        }

        if (this.gameWon) {
            background(197, 151, 113);
            text("You Win!", 300, 300);
            text("Press any key to restart", 250, 400);
        }
    }

    /**
     * Restart the game.
     */
    private void restart() {
        this.level = 1;
        this.wizardLife = this.conf.getInt("lives");
        this.gremlins.clear();
        this.slimes.clear();
        this.fireballs.clear();
        this.brickWallDestructions.clear();
        this.powerups.clear();
        this.transportDoors.clear();
        this.loadMap();
        this.gameOver = false;
        this.gameWon = false;
    }


    /**
     * Main method.
     */
    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
