package gremlins;


import java.util.ArrayList;
import java.util.List;

/**
 * Gremlins are enemies of the player.
 *
 * @author hzz
 */
public class Gremlin extends AbstractObject {

    public int gremlinMoveSpeed;
    public String gremlinDirection;
    protected String[] gremlinDirections;

    /**
     * Whether the gremlin is cooling down.
     */
    public boolean gremlinCooling;

    /**
     * Gremlin's cooldown time.
     */
    public float gremlinCoolDown;

    /**
     * When the gremlin attacks.
     */
    public int gremlinAttackTimer;

    /**
     * Constructor for the Gremlin class.
     *
     * @param app the main app
     * @param x   the x coordinate
     * @param y   the y coordinate
     */
    public Gremlin(App app, int x, int y) {
        super(app, app.gremlinImage, x, y);
        this.gremlinMoveSpeed = 1;
        this.gremlinDirections = new String[]{"up", "down", "left", "right"};
        this.gremlinDirection = this.gremlinDirections[App.RANDOM_GENERATOR.nextInt(this.gremlinDirections.length)];
        this.gremlinCooling = false;
        this.gremlinCoolDown = app.enemyCooldown;
        this.gremlinAttackTimer = 0;
    }


    /**
     * Gremlins behaviour each frame.
     *
     * @param app The main app.
     */
    @Override
    public void tick(App app) {
        switch (this.gremlinDirection) {
            case "left":
                this.x -= this.gremlinMoveSpeed;
                break;
            case "right":
                this.x += this.gremlinMoveSpeed;
                break;
            case "up":
                this.y -= this.gremlinMoveSpeed;
                break;
            case "down":
                this.y += this.gremlinMoveSpeed;
                break;
            default:
                break;
        }
        this.gremlinWallCollision(app);
        this.gremlinAttack(app);
        this.drawObject(app);
    }

    /**
     * Gremlins behaviour when hitting the wall.
     *
     * @param app the main app
     */
    private void gremlinWallCollision(App app) {
        for (AbstractObject[] line : app.map) {
            for (AbstractObject object : line) {
                if (object != null && this.collide(object) != null) {
                    AbstractObject obstacle = this.collide(object);
                    this.gremlinWallObstruct(app, obstacle);
                }
            }
        }
    }


    /**
     * Gremlins adjust position when hitting the wall.
     *
     * @param app      the main app
     * @param obstacle the obstacle that the gremlin hits
     */
    public void gremlinWallObstruct(App app, AbstractObject obstacle) {
        this.gremlinMoveSpeed = 0;
        int overlap;
        if (obstacle instanceof BrickWall || obstacle instanceof StoneWall) {
            switch (this.gremlinDirection) {
                case "left":
                    overlap = obstacle.x + App.SPRITESIZE - this.x;
                    this.x += overlap;
                    break;
                case "right":
                    overlap = this.x + App.SPRITESIZE - obstacle.x;
                    this.x -= overlap;
                    break;
                case "up":
                    overlap = obstacle.y + App.SPRITESIZE - this.y;
                    this.y += overlap;
                    break;
                case "down":
                    overlap = this.y + App.SPRITESIZE - obstacle.y;
                    this.y -= overlap;
                    break;
                default:
                    break;
            }
            this.gremlinChangeDirection(app, obstacle);
        }
    }

    /**
     * Gremlins change direction when hitting the wall, it won't continue go along current direction.
     * If there are more than zero new ways to go, it will choose one randomly, rather than going back.
     * If gremlin in a dead way, it will go back.
     *
     * @param app      the main app
     * @param obstacle the obstacle that gremlin hit on
     */
    public void gremlinChangeDirection(App app, AbstractObject obstacle) {
        String newDirection = this.gremlinDirection;
        List<String> possibleDirections = new ArrayList<>();

        if ("left".equals(this.gremlinDirection) || "right".equals(this.gremlinDirection)) {

            if (app.map[(this.getY() - App.SPRITESIZE) / App.SPRITESIZE][this.getX() / App.SPRITESIZE] == null) {
                possibleDirections.add("up");
            }
            if (app.map[(this.getY() + App.SPRITESIZE) / App.SPRITESIZE][this.getX() / App.SPRITESIZE] == null) {
                possibleDirections.add("down");
            }

            if (possibleDirections.size() != 0) {
                newDirection = possibleDirections.get(App.RANDOM_GENERATOR.nextInt(possibleDirections.size()));
            } else {
                if ("left".equals(this.gremlinDirection)) {
                    newDirection = "right";
                } else {
                    newDirection = "left";
                }
            }
        }

        if ("up".equals(this.gremlinDirection) || "down".equals(this.gremlinDirection)) {
            if (app.map[this.getY() / App.SPRITESIZE][(this.getX() - App.SPRITESIZE) / App.SPRITESIZE] == null) {
                possibleDirections.add("left");
            }
            if (app.map[this.getY() / App.SPRITESIZE][(this.getX() + App.SPRITESIZE) / App.SPRITESIZE] == null) {
                possibleDirections.add("right");
            }

            if (possibleDirections.size() != 0) {
                newDirection = possibleDirections.get(App.RANDOM_GENERATOR.nextInt(possibleDirections.size()));
            } else {
                if ("up".equals(this.gremlinDirection)) {
                    newDirection = "down";
                } else {
                    newDirection = "up";
                }
            }
        }

        this.gremlinDirection = newDirection;
        this.gremlinMoveSpeed = 1;
    }


    /**
     * Gremlins attack periodically.
     *
     * @param app the main app
     */
    public void gremlinAttack(App app) {
        if (!this.gremlinCooling) {
            Slime newSlime = new Slime(app, this.x, this.y, this.gremlinDirection);
            app.slimes.add(newSlime);
            this.gremlinCooling = true;
            this.gremlinAttackTimer = app.millis();
        } else {
            if (app.millis() - this.gremlinAttackTimer > this.gremlinCoolDown * 1000) {
                this.gremlinCooling = false;
                this.gremlinAttackTimer = 0;
            }
        }
    }

    /**
     * Gremlins die and respawn when hit by the player.
     *
     * @param app the main app
     */
    public void gremlinRespawn(App app) {
        app.gremlins.remove(this);

        int index = App.RANDOM_GENERATOR.nextInt(app.emptyTiles.size());
        int[] coordinates = app.emptyTiles.get(index);
        int newX = coordinates[0];
        int newY = coordinates[1];
        double radiusDistance = Math.sqrt(Math.pow(newX - app.player.getX(), 2) + Math.pow(newY - app.player.getY(), 2));
        while (radiusDistance < 10 * App.SPRITESIZE) {
            index = App.RANDOM_GENERATOR.nextInt(app.emptyTiles.size());
            coordinates = app.emptyTiles.get(index);
            newX = coordinates[0];
            newY = coordinates[1];
            radiusDistance = Math.sqrt(Math.pow(newX - app.player.getX(), 2) + Math.pow(newY - app.player.getY(), 2));
        }
        Gremlin newGremlin = new Gremlin(app, newX, newY);
        app.gremlins.add(newGremlin);
    }


}

