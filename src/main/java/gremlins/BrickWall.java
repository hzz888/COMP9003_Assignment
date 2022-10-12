package gremlins;

import processing.core.PImage;

import java.util.List;

/**
 * @author hzz
 */
public class BrickWall extends AbstractObject {

    public BrickWall(App app, int x, int y) {
        super(app, app.brickWallImage, x, y);
    }

    @Override
    public void tick(App app) {
        this.draw(app);
    }

    public void destroyed(App app) {

        // Display destruction animation
        int destroyPicIndex = 0;
        app.frameCount = 0;
        while (destroyPicIndex < app.brickWallDestroyImages.length) {
            if (app.frameCount % 4 == 0) {
                PImage destroyPic = app.brickWallDestroyImages[destroyPicIndex];
                app.image(destroyPic, this.getX(), this.getY());
                destroyPicIndex++;
            }
        }
        app.map[app.getMapX(this)][app.getMapY(this)] = null;
    }

}
