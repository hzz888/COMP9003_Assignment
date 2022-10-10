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
        this.x = x;
        this.y = y;
        this.draw(app);
    }

    public void draw(App app) {
        app.image(this.objectSprite, this.x, this.y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Defines objects behaviors.
     */
    abstract protected void tick(App app);

    /**
     * Detect whether this object collides with another object.
     *
     * @return object if it collides with the instance
     */
    public AbstractObject collide(AbstractObject object) {
        boolean collision = (this.getX() < object.getX() + App.SPRITESIZE && this.getX() + App.SPRITESIZE > object.getX() && this.getY() < object.getY() + App.SPRITESIZE && this.getY() + App.SPRITESIZE > object.getY());
        if (collision) {
            return object;
        } else {
            return null;
        }
    }




}
