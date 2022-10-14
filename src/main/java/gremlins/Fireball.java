package gremlins;

/**
 * @author hzz
 */
public class Fireball extends AbstractObject {
    public String direction;
    public int fireBallSpeed;
    public int speedEffect;

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
        this.fireballWallCollision(app);
        this.fireballCollideSlime(app);
        this.fireballAttackGremlin(app);
        this.drawObject(app);
    }

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

    public void fireballAttackGremlin(App app) {
        for (Gremlin gremlin : app.gremlins) {
            if (this.collide(gremlin) != null) {
                this.fireballAbsorbed(app);
                gremlin.gremlinRespawn(app);
            }
        }
    }

    public void fireballAbsorbed(App app) {
        app.fireballs.remove(this);
    }

}
