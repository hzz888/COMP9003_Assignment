package gremlins;

/**
 * The powerups that can be collected by the player to increse player's move speed.
 *
 * @author hzz
 */
public class Powerup extends AbstractObject {

    public static final int SPEED_UP = 2;
    public static final int POWERUP_PERIOD = 10;
    public boolean powerUpCooling;
    public int powerUpCoolingStartTimer;
    public int powerUpCoolingTime;

    /**
     * Constructor for the powerup.
     *
     * @param app the main app
     * @param x   the x coordinate
     * @param y   the y coordinate
     */
    public Powerup(App app, int x, int y) {
        super(app, app.powerupImage, x, y);
        this.powerUpCoolingTime = App.RANDOM_GENERATOR.nextInt(20);
    }

    /**
     * Display and update the powerup.
     *
     * @param app The main app.
     */
    @Override
    public void tick(App app) {
        this.respawnPowerUps(app);
        if (!this.powerUpCooling) {
            this.drawObject(app);
        }
    }

    /**
     * Powerups respawn after being collected by the player.
     *
     * @param app the main app
     */
    public void respawnPowerUps(App app) {
        if (this.powerUpCooling && app.millis() - this.powerUpCoolingStartTimer >= this.powerUpCoolingTime * 1000) {
            this.powerUpCooling = false;
            this.powerUpCoolingStartTimer = 0;
        }
    }


}

