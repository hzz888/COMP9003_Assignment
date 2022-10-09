package gremlins;

/**
 * @author hzz
 */
public class Wizard extends AbstractObject{
    private String direction;
    public int moveSpeed = 0;

    public Wizard(App app, int x, int y) {
        super(app, app.wizardImage, x, y);
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
    public void tick(){
        switch (this.direction) {
            case "up":
                this.y -= moveSpeed;
                break;
            case "down":
                this.y += moveSpeed;
                break;
            case "left":
                this.x -= moveSpeed;
                break;
            case "right":
                this.x += moveSpeed;
                break;
            default:
                break;
        }
    }


}
