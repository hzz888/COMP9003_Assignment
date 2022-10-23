package gremlins;

/**
 * Exit is the entrance to the next level or winning.
 *
 * @author hzz
 */
public class Exit extends AbstractObject {

    /**
     * Constructor of exit.
     *
     * @param app the main app
     * @param x   the x coordinate
     * @param y   the y coordinate
     */
    public Exit(App app, int x, int y) {
        super(app, app.exitImage, x, y);
    }

    /**
     * Define exit behaviour in each frame.
     *
     * @param app The main app.
     */
    @Override
    public void tick(App app) {
        this.getExit(app);
        this.drawObject(app);
    }

    /**
     * Define behaviour when the player gets to the exit.
     *
     * @param app the main app
     */
    public void getExit(App app) {
        if (app.player.collide(app.exit) != null) {
            if (app.level < app.totalLevels) {
                app.level++;
                app.resetLevel();
            } else {
                app.player.wizardWin(app);
                app.exit = new Exit(app, 40, 20);
            }
        }
    }
}
