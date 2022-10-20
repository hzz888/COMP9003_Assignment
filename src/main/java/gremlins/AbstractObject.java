package gremlins;


import processing.core.PImage;


/**
 * AbstractObject is the base class for all objects in the game. Every object's width and height are 20 pixels.
 *
 * @author hzz
 */
abstract public class AbstractObject {

    public final int WIDTH = 20;
    public final int HEIGHT = 20;
    protected PImage objectSprite;
    public int x;
    public int y;

    /**
     * The AbstractObject class is the parent class for all objects in the game.
     *
     * @param app          The main App instance to run the game.
     * @param objectSprite The sprite of the object.
     * @param x            The x coordinate of the object.
     * @param y            The y coordinate of the object.
     */
    public AbstractObject(App app, PImage objectSprite, int x, int y) {
        this.objectSprite = objectSprite;
        this.x = x;
        this.y = y;
    }

    /**
     * The draw method draws the object.
     *
     * @param app The main App instance to run the game.
     */
    public void drawObject(App app) {
        app.image(this.objectSprite, this.x, this.y);
    }

    /**
     * Get the x coordinate of the object.
     *
     * @return The x coordinate of the object.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the y coordinate of the object.
     *
     * @return The y coordinate of the object.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Set the x coordinate of the object.
     *
     * @param x The new x coordinate of the object.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the y coordinate of the object.
     *
     * @param y The new y coordinate of the object.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Defines objects behaviors in each frame.
     *
     * @param app The main app.
     */
    protected abstract void tick(App app);

    /**
     * Detect whether this object collides with another object.
     *
     * @param object Another object.
     * @return The object if it collides with this instance.
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
