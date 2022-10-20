package gremlins;

/**
 * Slime is gremlin's projectile.
 *
 * @author hzz
 */
public class Slime extends AbstractObject {

    public String slimeDirection;
    public int slimeMoveSpeed;

    /**
     * Constructor of Slime.
     *
     * @param app       the main app
     * @param x         the x coordinate
     * @param y         the y coordinate
     * @param direction the move direction of the slime
     */
    public Slime(App app, int x, int y, String direction) {
        super(app, app.slimeImage, x, y);
        this.slimeDirection = direction;
        this.slimeMoveSpeed = 4;
    }

    /**
     * Define slimes behaviour.
     *
     * @param app The main app.
     */
    @Override
    public void tick(App app) {
        switch (this.slimeDirection) {
            case "left":
                this.x -= this.slimeMoveSpeed;
                break;
            case "right":
                this.x += this.slimeMoveSpeed;
                break;
            case "up":
                this.y -= this.slimeMoveSpeed;
                break;
            case "down":
                this.y += this.slimeMoveSpeed;
                break;
            default:
                break;
        }
        this.slimeWallCollision(app);
        this.drawObject(app);
    }

    /**
     * Check if slime hits the wall, if so, slime will disappear.
     *
     * @param app the main app
     */
    public void slimeWallCollision(App app) {
        for (AbstractObject[] line : app.map) {
            for (AbstractObject tile : line) {
                if (tile != null && this.collide(tile) != null) {
                    this.slimeVaporized(app);
                }
            }
        }
    }

    /**
     * Slime disappear.
     *
     * @param app the main app
     */
    public void slimeVaporized(App app) {
        app.slimes.remove(this);
    }
}

