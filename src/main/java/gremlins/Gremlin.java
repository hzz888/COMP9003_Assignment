package gremlins;


/**
 * @author hzz
 */
public class Gremlin extends AbstractObject {

    public int gremlinMoveSpeed;
    public String gremlinDirection;
    protected String[] gremlinDirections;

    public boolean gremlinCooling;
    public float gremlinCoolDown;

    public int gremlinAttackTimer;

    public Gremlin(App app, int x, int y) {
        super(app, app.gremlinImage, x, y);
        this.gremlinMoveSpeed = 1;
        this.gremlinDirections = new String[]{"up", "down", "left", "right"};
        this.gremlinDirection = this.gremlinDirections[App.RANDOM_GENERATOR.nextInt(this.gremlinDirections.length)];
        this.gremlinCooling = false;
        this.gremlinCoolDown = app.enemyCooldown;
        this.gremlinAttackTimer = 0;
    }


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
            this.gremlinChangeDirection();
        }
    }

    public void gremlinChangeDirection() {
        String newDirection = this.gremlinDirection;
        while (newDirection.equals(this.gremlinDirection)) {
            newDirection = this.gremlinDirections[App.RANDOM_GENERATOR.nextInt(this.gremlinDirections.length)];
        }
        this.gremlinDirection = newDirection;
        this.gremlinMoveSpeed = 1;
    }

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

