package gremlins;

import java.util.Objects;

/**
 * Wizard is a role the player controls in the game.
 *
 * @author hzz
 */
public class Wizard extends AbstractObject {
    private String wizardDirection;
    public int wizardMoveSpeed;

    /**
     * Whether the wizard is being powered up now.
     */
    public boolean poweredUp;

    /**
     * When the powerup effect starts.
     */
    public int powerUpStartTimer;


    /**
     * Constructor for Wizard.
     * Initial speed is 0, not powered up and the direction facing is right.
     *
     * @param app the main app
     * @param x   the x coordinate
     * @param y   the y coordinate
     */
    public Wizard(App app, int x, int y) {
        super(app, app.wizardRightImage, x, y);
        this.wizardMoveSpeed = 0;
        this.wizardDirection = "right";
        this.poweredUp = false;
    }


    /**
     * Get wizard's current direction.
     *
     * @return current direction
     */
    public String getWizardDirection() {
        return this.wizardDirection;
    }


    /**
     * Set wizard's current direction and change sprite according to the direction.
     *
     * @param app       the main app
     * @param direction the direction to set
     */
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


    /**
     * Wizard behaviour each frame
     *
     * @param app The main app.
     */
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
        if (app.wizardCooling) {
            this.wizardCoolDown(app);
        }
        this.wizardWallCollision(app);
        this.wizardGetPowerUp(app);
        this.wizardPoweredUp(app);
        this.wizardAttacked(app);
        this.drawObject(app);
    }


    /**
     * Control wizard's moving.
     *
     * @param app       the main app
     * @param direction the moving direction of wizard
     */
    public void wizardMove(App app, String direction) {
        this.setWizardDirection(app, direction);
        if (this.poweredUp) {
            this.wizardMoveSpeed = 2 + Powerup.SPEED_UP;
        } else {
            this.wizardMoveSpeed = 2;
        }
    }


    /**
     * Stop the wizard and adjust the location to ensure it is always in a whole tile.
     */
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


    /**
     * Define the behaviour when wizard collides with wall.
     *
     * @param app  the main app
     * @param tile the tile that wizard collides with
     */
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


    /**
     * Detect collision between wizard and wall.
     *
     * @param app the main app
     */
    public void wizardWallCollision(App app) {
        for (AbstractObject[] tiles : app.map) {
            for (AbstractObject tile : tiles) {
                if (tile != null && this.collide(tile) != null) {
                    this.wizardWallObstruct(app, tile);
                }
            }
        }
    }


    /**
     * Define wizard's attack behaviour.
     *
     * @param app the main app
     */
    public void wizardAttack(App app) {
        if (!app.wizardCooling) {
            Fireball fireball = new Fireball(app, this.getX(), this.getY(), this.getWizardDirection());
            app.fireballs.add(fireball);
            app.wizardAttackTimer = app.millis();
            app.wizardCooling = true;
        }
    }


    /**
     * Define wizard's cooldown behaviour after attacking.
     *
     * @param app the main app
     */
    public void wizardCoolDown(App app) {
        app.wizardCoolDownTimer = app.millis();
        if (app.wizardCoolDownTimer - app.wizardAttackTimer <= app.wizardCooldown * 1000) {
            app.stroke(0);
            app.strokeWeight(1.5f);
            app.fill(255, 255, 255);
            app.rect(550, 685, 100, 10);
            app.fill(0, 0, 0);
            float coolDownBarWidth = (float) ((app.wizardCoolDownTimer - app.wizardAttackTimer) / 1000.0 / app.wizardCooldown * 100);
            app.rect(551, 686, coolDownBarWidth, 8);
        } else {
            app.wizardCooling = false;
            app.wizardAttackTimer = 0;
        }
    }


    /**
     * Define wizard's behaviour when getting hit by gremlin or slimes.
     *
     * @param app the main app
     */
    public void wizardAttacked(App app) {
        for (Gremlin gremlin : app.gremlins) {
            if (this.collide(gremlin) != null) {
                app.wizardLife--;
                if (app.wizardLife >= 1) {
                    app.resetLevel();
                } else {
                    this.wizardDie(app);
                }
            }
        }

        for (Slime slime : app.slimes) {
            if (this.collide(slime) != null) {
                app.wizardLife--;
                if (app.wizardLife >= 1) {
                    app.resetLevel();
                } else {
                    this.wizardDie(app);
                }
            }
        }
    }


    /**
     * Define wizard and powerups behaviour when the wizard getting powerups.
     *
     * @param app the main app
     */
    public void wizardGetPowerUp(App app) {
        if (app.millis() - app.levelInitTimer >= app.powerUpSpawnTime * 1000) {
            for (Powerup powerup : app.powerups) {
                if (this.collide(powerup) != null && !powerup.powerUpCooling) {
                    this.poweredUp = true;
                    this.powerUpStartTimer = app.millis();
                    powerup.powerUpCooling = true;
                    powerup.powerUpCoolingStartTimer = app.millis();
                    powerup.powerUpCoolingTime = App.RANDOM_GENERATOR.nextInt(20);
                    break;
                }
            }
        }
    }


    /**
     * Define wizard's behaviour when being powered up.
     *
     * @param app the main app
     */
    public void wizardPoweredUp(App app) {
        if (this.poweredUp) {
            if (app.millis() - this.powerUpStartTimer >= Powerup.POWERUP_PERIOD * 1000) {
                this.poweredUp = false;
            } else {
                app.fill(255, 255, 255);
                app.text("SPEED UP!,", 350, 690);
                app.text("Time Left: " + (Powerup.POWERUP_PERIOD - (app.millis() - this.powerUpStartTimer) / 1000) + " s", 350, 710);
            }
        }
    }


    /**
     * Define program behaviour when wizard is dead.
     *
     * @param app the main app
     */
    public void wizardDie(App app) {
        app.gameOver = true;
        app.gremlins.clear();
        app.fireballs.clear();
        app.slimes.clear();
        app.powerups.clear();
        app.transportDoors.clear();
        app.brickWallDestructions.clear();
        app.gameStopTimer = app.millis();
    }


    /**
     * Define program behaviour when player win.
     *
     * @param app the main app
     */
    public void wizardWin(App app) {
        app.gameWon = true;
        app.gremlins.clear();
        app.fireballs.clear();
        app.slimes.clear();
        app.powerups.clear();
        app.transportDoors.clear();
        app.brickWallDestructions.clear();
        app.gameStopTimer = app.millis();
    }

}
