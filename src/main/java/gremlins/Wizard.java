package gremlins;

/**
 * @author hzz
 */
public class Wizard extends AbstractObject {
    private String direction;
    public int moveSpeed;

    public Wizard(App app, int x, int y) {
        super(app, app.wizardImage, x, y);
        this.moveSpeed = 0;
        this.direction = "right";
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        switch (direction) {
            case "up":
                this.direction = "up";
                break;
            case "down":
                this.direction = "down";
                break;
            case "left":
                this.direction = "left";
                break;
            case "right":
                this.direction = "right";
                break;
            default:
                break;
        }
    }

    @Override
    public void tick() {
        switch (this.direction) {
            case "up":
                this.y -= this.moveSpeed;
                break;
            case "down":
                this.y += this.moveSpeed;
                break;
            case "left":
                this.x -= this.moveSpeed;
                break;
            case "right":
                this.x += this.moveSpeed;
                break;
            default:
                break;
        }
    }
    public void stop(){
        this.moveSpeed=0;
    }

}
