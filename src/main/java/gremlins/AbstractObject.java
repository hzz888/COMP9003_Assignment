package gremlins;

import processing.core.PImage;


/**
 * @author hzz
 */
abstract public class AbstractObject {
    public final int WIDTH = 20;
    public final int HEIGHT = 20;
    protected PImage objectSprite;
    protected int x;
    protected int y;

    public AbstractObject(App app, PImage objectSprite, int x, int y) {
        this.objectSprite = objectSprite;
        this.x=x;
        this.y=y;
        app.image(this.objectSprite, this.x, this.y);
    }

    private void draw(App app) {

    }

    public int getX() {
        return this.x;
    }
    public int getY(){
        return this.y;
    }


}
