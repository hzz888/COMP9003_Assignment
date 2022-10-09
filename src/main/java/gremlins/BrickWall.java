package gremlins;

/**
 * @author hzz
 */
public class BrickWall extends AbstractObject {

    public BrickWall(App app, int x, int y) {
        super(app, app.brickWallImage, x, y);
    }
    @Override
    public void tick(){
        return;
    }

}
