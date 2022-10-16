package gremlins;

/**
 * @author hzz
 */
public class Powerup extends AbstractObject {

    public static final int SPEED_UP = 2;
    public static final int POWERUP_PERIOD = 10;
    public boolean powerUpCooling;
    public int powerUpCoolingStartTimer;
    public int powerUpCoolingTime;

    public Powerup(App app, int x, int y) {
        super(app, app.powerupImage, x, y);
        this.powerUpCoolingTime = App.RANDOM_GENERATOR.nextInt(20);
    }

    @Override
    public void tick(App app) {
        this.respawnPowerUps(app);
        this.drawObject(app);
    }

    public void respawnPowerUps(App app) {
        this.powerUpCoolingTime = App.RANDOM_GENERATOR.nextInt(30);
        if (this.powerUpCooling && app.millis() - this.powerUpCoolingStartTimer >= this.powerUpCoolingTime * 1000) {
            this.powerUpCooling = false;
            this.powerUpCoolingStartTimer = 0;
        }
    }


}

