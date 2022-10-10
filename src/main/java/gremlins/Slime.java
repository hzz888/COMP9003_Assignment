package gremlins;

/**
 * @author hzz
 */
public class Slime extends AbstractObject {

    public Slime(App app, int x, int y) {
        super(app, app.slimeImage, x, y);
    }
    @Override
    public void tick(App app){
        return;
    }
}

