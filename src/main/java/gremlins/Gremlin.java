package gremlins;

import java.util.Objects;


/**
 * @author hzz
 */
public class Gremlin extends AbstractObject {

    public int gremlinMoveSpeed;
    public String gremlinDirection;
    protected String[] gremlinDirections;

    public Gremlin(App app, int x, int y) {
        super(app, app.gremlinImage, x, y);
        this.gremlinMoveSpeed = 1;
        this.gremlinDirections = new String[]{"up", "down", "left", "right"};
        this.gremlinDirection = this.gremlinDirections[App.RANDOM_GENERATOR.nextInt(this.gremlinDirections.length)];
    }



    @Override
    public void tick(App app){
        switch (this.gremlinDirection){
            case "left" :
                this.x -= this.gremlinMoveSpeed;
                break;
            case "right" :
                this.x += this.gremlinMoveSpeed;
                break;
            case "up" :
                this.y -= this.gremlinMoveSpeed;
                break;
            case "down" :
                this.y += this.gremlinMoveSpeed;
                break;
            default:
                break;
        }
        this.gremlinWallCollision(app);
        this.draw(app);
    }

    private void gremlinWallCollision(App app) {
        for(AbstractObject[] line : app.map){
            for (AbstractObject object: line){
                if(object!=null && this.collide(object)!=null){
                    AbstractObject obstacle = this.collide(object);
                    this.gremlinWallObstruct(app, obstacle);
                }
            }
        }
    }


    public void gremlinWallObstruct(App app, AbstractObject obstacle){
        this.gremlinMoveSpeed=0;
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
                case "up" :
                    overlap = obstacle.y+App.SPRITESIZE-this.y;
                    this.y += overlap;
                    break;
                case "down":
                    overlap = this.y+App.SPRITESIZE-obstacle.y;
                    this.y -= overlap;
                    break;
                default:
                    break;
            }
            this.gremlinChangeDirection();
        }
    }

    public void gremlinChangeDirection(){
        String newDirection = this.gremlinDirection;
        while(newDirection.equals(this.gremlinDirection)){
            newDirection = this.gremlinDirections[App.RANDOM_GENERATOR.nextInt(this.gremlinDirections.length)];
        }
        this.gremlinDirection = newDirection;
        this.gremlinMoveSpeed = 1;
    }
}

