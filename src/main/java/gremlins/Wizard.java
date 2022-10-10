package gremlins;

import java.util.Objects;

/**
 * @author hzz
 */
public class Wizard extends AbstractObject {
    private String direction;
    public int moveSpeed;

    public Wizard(App app, int x, int y) {
        super(app, app.wizardRightImage, x, y);
        this.moveSpeed = 0;
        this.direction = "right";
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(App app, String direction) {
        switch (direction) {
            case "up":
                this.direction = "up";
                this.objectSprite = app.wizardUpImage;
                break;
            case "down":
                this.direction = "down";
                this.objectSprite= app.wizardDownImage;
                break;
            case "left":
                this.direction = "left";
                this.objectSprite=app.wizardLeftImage;
                break;
            case "right":
                this.direction = "right";
                this.objectSprite=app.wizardRightImage;
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
        if (this.getX() % App.SPRITESIZE != 0) {
            if (Objects.equals(this.getDirection(), "right")) {
                this.setX(this.getX() + App.SPRITESIZE - this.getX() % App.SPRITESIZE);
            }else if(Objects.equals(this.getDirection(), "left")){
                this.setX(this.getX() - this.getX() % App.SPRITESIZE);
            }
        }
        if (this.getY() % App.SPRITESIZE != 0){
            if (Objects.equals(this.getDirection(), "down")) {
                this.setY(this.getY() + App.SPRITESIZE - this.getY() % App.SPRITESIZE);
            }else if (Objects.equals(this.getDirection(), "up")){
                this.setY(this.getY() - this.getY() % App.SPRITESIZE);
            }
        }
    }

}
