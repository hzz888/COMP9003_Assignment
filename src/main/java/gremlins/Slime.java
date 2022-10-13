package gremlins;

/**
 * @author hzz
 */
public class Slime extends AbstractObject {

    public String slimeDirection;
    public int slimeMoveSpeed;

    public Slime(App app, int x, int y, String direction) {
        super(app, app.slimeImage, x, y);
        this.slimeDirection = direction;
        this.slimeMoveSpeed=4;
    }

    @Override
    public void tick(App app) {
        switch (this.slimeDirection){
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
        this.drawObject(app);
    }
}

