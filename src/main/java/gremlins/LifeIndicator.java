package gremlins;

/**
 * @author hzz
 */
public class LifeIndicator extends AbstractObject {

    public LifeIndicator(App app, int x, int y) {
        super(app, app.wizardRightImage, x, y);
    }
    @Override
    public void tick(App app){
        return;
    }
}

