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
        this.getTransportDoor(app);
        this.drawObject(app);
    }

    public void getTransportDoor(App app) {

            if (app.player.collide(this) != null) {
                int randomIndex = App.RANDOM_GENERATOR.nextInt(app.emptyTiles.size());
                int[] randomTile = app.emptyTiles.get(randomIndex);
                int newX = randomTile[0];
                int newY = randomTile[1];
                app.player.setX(newX);
                app.player.setY(newY);
            }

            for (Fireball fireball : app.fireballs) {
                if (fireball.collide(this) != null) {
                    int randomIndex = App.RANDOM_GENERATOR.nextInt(app.emptyTiles.size());
                    int[] randomTile = app.emptyTiles.get(randomIndex);
                    int newX = randomTile[0];
                    int newY = randomTile[1];
                    fireball.setX(newX);
                    fireball.setY(newY);
                }
            }

            for (Gremlin gremlin : app.gremlins) {
                if (gremlin.collide(this) != null) {
                    int randomIndex = App.RANDOM_GENERATOR.nextInt(app.emptyTiles.size());
                    int[] randomTile = app.emptyTiles.get(randomIndex);
                    int newX = randomTile[0];
                    int newY = randomTile[1];
                    gremlin.setX(newX);
                    gremlin.setY(newY);
                }
            }

            for (Slime slime : app.slimes) {
                if (slime.collide(this) != null) {
                    int randomIndex = App.RANDOM_GENERATOR.nextInt(app.emptyTiles.size());
                    int[] randomTile = app.emptyTiles.get(randomIndex);
                    int newX = randomTile[0];
                    int newY = randomTile[1];
                    slime.setX(newX);
                    slime.setY(newY);
                }
            }
        }
    }


