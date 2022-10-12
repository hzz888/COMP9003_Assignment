package gremlins;

/**
 * @author hzz
 */
public class Fireball extends AbstractObject {
    public String direction;
    public int fireBallSpeed;

    public Fireball(App app, int x, int y, String direction) {
        super(app, app.fireballImage, x, y);
        this.direction = direction;
        this.fireBallSpeed = 4;
    }

    @Override
    public void tick(App app) {
        switch (this.direction) {
            case "up":
                this.y -= this.fireBallSpeed;
                break;
            case "down":
                this.y += this.fireBallSpeed;
                break;
            case "left":
                this.x -= this.fireBallSpeed;
                break;
            case "right":
                this.x += this.fireBallSpeed;
                break;
            default:
                break;
        }
        this.wallCollision(app);
        // todo: add enemy collision
        this.draw(app);
    }

    private void wallCollision(App app) {
        for (AbstractObject[] line : app.map) {
            for (AbstractObject tile : line) {
                if (tile != null && this.collide(tile) != null) {
                    this.absorbed(app);
                    if (this.collide(tile) instanceof BrickWall) {
                        BrickWall brickWall = (BrickWall) this.collide(tile);
                        brickWall.destroyed(app);
                    }
                }
            }
        }
    }


    private void absorbed(App app) {
        app.fireballs.remove(this);
    }
}
