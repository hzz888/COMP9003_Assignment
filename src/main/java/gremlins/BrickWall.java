package gremlins;

/**
 * @author hzz
 */
public class BrickWall extends AbstractObject {

    public BrickWall(App app, int x, int y) {
        super(app, app.brickWallImage, x, y);
    }
    @Override
    public void tick(App app){
        return;
    }

    public void destroyed(App app){
         app.map[app.getMapX(this)][app.getMapY(this)] = null;

    }

}
