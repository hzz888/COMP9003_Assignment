package gremlins;

/**
 * @author hzz
 */
public class TransportDoor extends AbstractObject {

    public TransportDoor(App app, int x, int y) {
        super(app, app.transportDoorImage, x, y);
    }

    @Override
    public void tick(App app) {
        this.drawObject(app);
    }

}
