package gremlins;

/**
 * @author hzz
 */
public class Powerup extends AbstractObject {

    public static final int SPEED_UP = 2;
    public static final int POWERUP_PERIOD = 10;
    public Powerup(App app, int x, int y) {
        super(app, app.powerupImage, x, y);
    }
    @Override
    public void tick(App app){
        this.drawObject(app);
    }

    public void powerUpRespawn(App app) {
    }
}

