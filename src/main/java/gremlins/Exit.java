package gremlins;

/**
 * @author hzz
 */
public class Exit extends AbstractObject {
    public Exit(App app, int x, int y) {
        super(app, app.exitImage, x, y);
    }
    @Override
    public void tick(App app){
        return;
    }
}
