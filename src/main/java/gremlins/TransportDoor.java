package gremlins;

/**
 * TransportDoor will teleport the wizard, fireballs, gremlins and slimes to a random empty location.
 * @author hzz
 */
public class TransportDoor extends AbstractObject {

    /**
     * Constructor for TransportDoor.
     * @param app the game application
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public TransportDoor(App app, int x, int y) {
        super(app, app.transportDoorImage, x, y);
    }

    /**
     * Display and detect collision with wizard, fireballs, gremlins and slimes.
     * @param app The main app.
     */
    @Override
    public void tick(App app) {
        this.getTransportDoor(app);
        this.drawObject(app);
    }

    /**
     * Teleport the wizard back to the initial position.
     * @param app the main app
     */
    public void getTransportDoor(App app) {
            if (app.player.collide(this) != null) {
                app.player.setX(app.playerStartX);
                app.player.setY(app.playerStartY);
                app.player.setWizardDirection(app,"right");
            }
        }
    }


