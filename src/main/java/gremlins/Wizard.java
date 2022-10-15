package gremlins;

import java.util.Objects;

/**
 * @author hzz
 */
public class Wizard extends AbstractObject {
    private String wizardDirection;
    public int wizardMoveSpeed;
    public boolean poweredUp;
    public int powerUpStartTimer;


    public Wizard(App app, int x, int y) {
        super(app, app.wizardRightImage, x, y);
        this.wizardMoveSpeed = 0;
        this.wizardDirection = "right";
    }

    public String getWizardDirection() {
        return this.wizardDirection;
    }

    public void setWizardDirection(App app, String direction) {
        switch (direction) {
            case "up":
                this.wizardDirection = "up";
                this.objectSprite = app.wizardUpImage;
                break;
            case "down":
                this.wizardDirection = "down";
                this.objectSprite = app.wizardDownImage;
                break;
            case "left":
                this.wizardDirection = "left";
                this.objectSprite = app.wizardLeftImage;
                break;
            case "right":
                this.wizardDirection = "right";
                this.objectSprite = app.wizardRightImage;
                break;
            default:
                break;
        }
    }

    @Override
    public void tick(App app) {
        switch (this.wizardDirection) {
            case "up":
                this.y -= this.wizardMoveSpeed;
                break;
            case "down":
                this.y += this.wizardMoveSpeed;
                break;
            case "left":
                this.x -= this.wizardMoveSpeed;
                break;
            case "right":
                this.x += this.wizardMoveSpeed;
                break;
            default:
                break;
        }
        this.wizardWallCollision(app);
        this.wizardGetPowerUp(app);
        this.wizardPoweredUp(app);
        this.drawObject(app);
    }

    public void wizardMove(App app, String direction) {
        this.setWizardDirection(app, direction);
        if (this.poweredUp){
            this.wizardMoveSpeed = 2 + Powerup.SPEED_UP;
        }else {
            this.wizardMoveSpeed = 2;
        }

    }

    public void wizardStop() {
        this.wizardMoveSpeed = 0;

        if (this.getX() % App.SPRITESIZE != 0) {
            if (Objects.equals(this.getWizardDirection(), "right")) {
                this.setX(this.getX() + App.SPRITESIZE - this.getX() % App.SPRITESIZE);
            } else if (Objects.equals(this.getWizardDirection(), "left")) {
                this.setX(this.getX() - this.getX() % App.SPRITESIZE);
            }
        }
        if (this.getY() % App.SPRITESIZE != 0) {
            if (Objects.equals(this.getWizardDirection(), "down")) {
                this.setY(this.getY() + App.SPRITESIZE - this.getY() % App.SPRITESIZE);
            } else if (Objects.equals(this.getWizardDirection(), "up")) {
                this.setY(this.getY() - this.getY() % App.SPRITESIZE);
            }
        }
    }


    public void wizardWallObstruct(App app, AbstractObject tile) {
        AbstractObject obstruction = this.collide(tile);
        if (obstruction instanceof StoneWall || obstruction instanceof BrickWall) {
            int overlap;
            switch (this.getWizardDirection()) {
                case "right":
                    overlap = this.getX() + App.SPRITESIZE - obstruction.getX();
                    this.setX(this.getX() - overlap);
                    break;
                case "left":
                    overlap = obstruction.getX() + App.SPRITESIZE - this.getX();
                    this.setX(this.getX() + overlap);
                    break;
                case "up":
                    overlap = obstruction.getY() + App.SPRITESIZE - this.getY();
                    this.setY(this.getY() + overlap);
                    break;
                case "down":
                    overlap = this.getY() + App.SPRITESIZE - obstruction.getY();
                    this.setY(this.getY() - overlap);
                    break;
                default:
                    break;
            }

        }
    }


    public void wizardWallCollision(App app) {
        for (AbstractObject[] tiles : app.map) {
            for (AbstractObject tile : tiles) {
                if (tile != null && this.collide(tile) != null) {
                    this.wizardWallObstruct(app, tile);
                }
            }
        }
    }

    public void wizardAttack(App app) {
        if (!app.wizardCooling) {
            Fireball fireball = new Fireball(app, this.getX(), this.getY(), this.getWizardDirection());
            app.fireballs.add(fireball);
            app.wizardAttackTimer = app.millis();
            app.wizardCooling = true;
        }
    }

    public void wizardAttacked(App app) {
        for (Gremlin gremlin : app.gremlins) {
            if (this.collide(gremlin) != null) {
                app.wizardLife -= 1;
                app.resetLevel();
            }
        }

        for (Slime slime : app.slimes) {
            if (this.collide(slime) != null) {
                app.wizardLife -= 1;
                app.resetLevel();
            }
        }
    }

    public void wizardGetPowerUp(App app) {
        if (app.millis() - app.levelInitTimer >= app.powerUpSpawnTime * 1000) {
            for (Powerup powerup : app.powerups) {
                if (this.collide(powerup) != null) {
                    this.poweredUp = true;
                    this.powerUpStartTimer = app.millis();
                    powerup.powerUpCooling = true;
                    powerup.powerUpCoolingStartTimer = app.millis();
                    break;
                }
            }
        }
    }

    public void wizardPoweredUp(App app){
        if(this.poweredUp){
            if(app.millis() - this.powerUpStartTimer >= Powerup.POWERUP_PERIOD * 1000){
                this.poweredUp = false;
            }else {
                app.text("SPEED UP!,", 350,690);
                app.text("Time Left: " + (Powerup.POWERUP_PERIOD - (app.millis() - this.powerUpStartTimer) / 1000) + " s", 350, 710);
            }
        }
    }

}
