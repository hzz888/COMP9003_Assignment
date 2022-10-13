package gremlins;


/**
 * @author hzz
 */
public class BrickWall extends AbstractObject {


    public int destroyPicIndex = 0;


    public BrickWall(App app, int x, int y) {
        super(app, app.brickWallImage, x, y);
    }

    @Override
    public void tick(App app) {
        this.drawObject(app);
    }

    public void destroyed(App app) {
        app.brickWallDestructions.add(new BrickWallDestruction(app, this.getX(), this.getY()));
        app.map[app.getMapX(this)][app.getMapY(this)] = null;
    }

}
