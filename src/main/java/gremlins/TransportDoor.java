package gremlins;

import java.util.ArrayList;
import java.util.List;

/**
 * TransportDoor will teleport the wizard, fireballs, gremlins and slimes to a random empty location.
 *
 * @author hzz
 */
public class TransportDoor extends AbstractObject {

    /**
     * Constructor for TransportDoor.
     *
     * @param app the game application
     * @param x   the x coordinate
     * @param y   the y coordinate
     */
    public TransportDoor(App app, int x, int y) {
        super(app, app.transportDoorImage, x, y);
    }

    /**
     * Display and detect collision with wizard, fireballs, gremlins and slimes.
     *
     * @param app The main app.
     */
    @Override
    public void tick(App app) {
        this.getTransportDoor(app);
        this.drawObject(app);
    }

    /**
     * Teleport the wizard to a random empty safe position.
     * A position is safe if its radius distanance to all gremlins and slimes is greater than 100 pixels.
     * @param app the main app
     */
    public void getTransportDoor(App app) {
        if (app.player.collide(this) != null) {

            List<int[]> safeTiles = new ArrayList<>();

            for (int[] coordinate : app.emptyTiles) {

                boolean slimeSafe = true;
                boolean gremlinSafe = true;

                for (Slime slime : app.slimes) {
                    if (Math.sqrt(Math.pow(coordinate[0] - slime.getX(), 2) + Math.pow(coordinate[1] - slime.getY(), 2)) < 100) {
                        slimeSafe = false;
                        break;
                    }
                }

                for (Gremlin gremlin : app.gremlins) {
                    if (Math.sqrt(Math.pow(coordinate[0] - gremlin.getX(), 2) + Math.pow(coordinate[1] - gremlin.getY(), 2)) < 100) {
                        gremlinSafe = false;
                        break;
                    }
                }

                if (slimeSafe && gremlinSafe) {
                    safeTiles.add(coordinate);
                }
            }

            int randomIndex = App.RANDOM_GENERATOR.nextInt(safeTiles.size());
            int newX = safeTiles.get(randomIndex)[0];
            int newY = safeTiles.get(randomIndex)[1];
            app.player.setX(newX);
            app.player.setY(newY);
        }

    }
}


