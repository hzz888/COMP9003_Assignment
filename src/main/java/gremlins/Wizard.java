package gremlins;

/**
 * @author hzz
 */
public class Wizard extends AbstractObject{
    private int direction;
    /**
     * direction: 0 = up, 1 = down, 2 = left, 3 = right
     */
    public Wizard(App app, int x, int y) {
        super(app, app.wizardImage, x, y);
    }
    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }


}
