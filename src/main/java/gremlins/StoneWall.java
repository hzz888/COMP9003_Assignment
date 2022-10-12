package gremlins;

/**
 * @author hzz
 */
public class StoneWall extends AbstractObject {
    public StoneWall(App app, int x, int y){
        super(app, app.stoneWallImage, x, y);
    }
    @Override
    public void tick(App app){
       this.draw(app);
    }
}
