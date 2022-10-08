package gremlins;

import processing.core.PImage;


/**
 * @author hzz
 */
abstract public class AbstractObject {
    public final int WIDTH = 20;
    public final int HEIGHT = 20;
    private PImage objectSprite;
    private int x;
    private int y;

    public AbstractObject(App app, PImage objectSprite, int x, int y) {
        this.objectSprite = objectSprite;
        this.draw(app);
    }

    private void draw(App app) {
        app.image(this.objectSprite, this.x, this.y);
    }

    public int getX() {
        return this.x;
    }
    public int getY(){
        return this.y;
    }


}
