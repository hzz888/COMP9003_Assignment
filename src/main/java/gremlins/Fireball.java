package gremlins;

/**
 * @author hzz
 */
public class Fireball extends AbstractObject {
    public Fireball(App app, int x, int y) {
        super(app, app.fireballImage, x, y);
    }
    @Override
    public void tick(){
        return;
    }
}
