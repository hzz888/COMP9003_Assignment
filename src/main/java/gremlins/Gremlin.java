package gremlins;

/**
 * @author hzz
 */
public class Gremlin extends AbstractObject {

    public int gremlinMoveSpeed;
    public String direction;
    protected String[] gremlinDirections;

    public Gremlin(App app, int x, int y) {
        super(app, app.gremlinImage, x, y);
        this.gremlinMoveSpeed = 1;
        this.gremlinDirections = new String[]{"up", "down", "left", "right"};
        this.direction = this.gremlinDirections[(int)(Math.random() * this.gremlinDirections.length)];
    }



    @Override
    public void tick(App app){
        switch (this.direction){
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
        this.wallCollision(app);
        this.draw(app);
    }

    private void wallCollision(App app) {

    }
}

