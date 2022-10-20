package gremlins;


/**
 * A brick wall is a kind of tile that can be broken by wizard's attack.
 *
 * @author hzz
 */
public class BrickWall extends AbstractObject {

    public int destroyPicIndex = 0;

    /**
     * Constructor for BrickWall.
     *
     * @param app the game application
     * @param x   the x coordinate
     * @param y   the y coordinate
     */
    public BrickWall(App app, int x, int y) {
        super(app, app.brickWallImage, x, y);
    }

    /**
     * Define the behaviour of brickwalls in each frame.
     *
     * @param app The main app.
     */
    @Override
    public void tick(App app) {
        this.drawObject(app);
    }

    /**
     * Define the behaviour of brickwalls when they are attacked by fireballs.
     *
     * @param app The main app.
     */
    public void destroyed(App app) {
        app.brickWallDestructions.add(new BrickWallDestruction(app, this.getX(), this.getY()));
        app.map[app.getMapX(this)][app.getMapY(this)] = null;
    }

}
