package gremlins;

/**
 * @author hzz
 */
public class Powerup extends AbstractObject {
    public Powerup(App app, int x, int y) {
        super(app, app.powerupImage, x, y);
    }
    @Override
    public void tick(App app){
        return;
    }
}

