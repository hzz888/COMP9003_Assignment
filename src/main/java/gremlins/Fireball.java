package gremlins;

/**
 * The fireballs cast by wizard.
 * @author hzz
 */
public class Fireball extends AbstractObject {
    public String direction;
    public int fireBallSpeed;

    /**
     * Constructor of fireball.
     * @param app the main app
     * @param x the x coordinate
     * @param y the y coordinate
     * @param direction the move directin of fireballs
     */
    public Fireball(App app, int x, int y, String direction) {
        super(app, app.fireballImage, x, y);
        this.direction = direction;
        this.fireBallSpeed = 4;
    }

    /**
     * Fireball behaviour.
     * @param app the main app
     */
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
        this.fireballWallCollision(app);
        this.fireballCollideSlime(app);
        this.fireballAttackGremlin(app);
        this.drawObject(app);
    }

    /**
     * Fireball behaviour when hitting walls.
     * @param app the main app
     */
    public void fireballWallCollision(App app) {
        for (AbstractObject[] line : app.map) {
            for (AbstractObject tile : line) {
                if (tile != null && this.collide(tile) != null) {
                    this.fireballAbsorbed(app);
                    if (this.collide(tile) instanceof BrickWall) {
                        BrickWall brickWall = (BrickWall) this.collide(tile);
                        brickWall.destroyed(app);
                    }
                }
            }
        }
    }


    /**
     * Fireball behaviour when hitting slimes.
     * @param app the main app
     */
    public void fireballCollideSlime(App app) {
        for (Fireball fireball : app.fireballs) {
            for (Slime slime : app.slimes) {
                if (fireball.collide(slime) != null) {
                    fireball.fireballAbsorbed(app);
                    slime.slimeVaporized(app);
                }
            }
        }
    }

    /**
     * Fireball behaviour when hitting gremlins.
     * @param app the main app
     */
    public void fireballAttackGremlin(App app) {
        for (Gremlin gremlin : app.gremlins) {
            if (this.collide(gremlin) != null) {
                this.fireballAbsorbed(app);
                gremlin.gremlinRespawn(app);
            }
        }
    }

    /**
     * Make fireballs disappear.
     * @param app the main app
     */
    public void fireballAbsorbed(App app) {
        app.fireballs.remove(this);
    }

}
