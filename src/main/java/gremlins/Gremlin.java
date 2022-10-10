package gremlins;

/**
 * @author hzz
 */
public class Gremlin extends AbstractObject {

    public Gremlin(App app, int x, int y) {
        super(app, app.gremlinImage, x, y);
    }
    @Override
    public void tick(App app){
        this.draw(app);
        return;
    }
}

