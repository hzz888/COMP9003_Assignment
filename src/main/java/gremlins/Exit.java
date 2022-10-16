package gremlins;

/**
 * @author hzz
 */
public class Exit extends AbstractObject {
    public Exit(App app, int x, int y) {
        super(app, app.exitImage, x, y);
    }
    @Override
    public void tick(App app){
        this.getExit(app);
        this.drawObject(app);
    }

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
