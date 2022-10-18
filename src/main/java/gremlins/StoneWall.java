package gremlins;

/**
 * StoneWall is a kind of tile that cannot be broken.
 * @author hzz
 */
public class StoneWall extends AbstractObject {

    /**
     * Constructor of StoneWall.
     * @param app the main app
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public StoneWall(App app, int x, int y){
        super(app, app.stoneWallImage, x, y);
    }

    /**
     * Display stonewalls.
     * @param app The main app.
     */
    @Override
    public void tick(App app){
       this.drawObject(app);
    }
}
