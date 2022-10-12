package gremlins;

import processing.core.PImage;


/**
 * @author hzz
 */
public class BrickWall extends AbstractObject {


    public int destroyPicIndex = 0;


    public BrickWall(App app, int x, int y) {
        super(app, app.brickWallImage, x, y);
    }

    @Override
    public void tick(App app) {
        this.draw(app);
    }

    public void destroyed(App app) {

        // Display destruction animation
        // error :动画帧数不对

        app.frameCount=0;
        while (this.destroyPicIndex < app.brickWallDestroyImages.length) {
            PImage destroyPic = app.brickWallDestroyImages[this.destroyPicIndex];
            app.image(destroyPic, this.getX(), this.getY());
            if (app.frameCount % 4 == 0) {
                this.destroyPicIndex++;
            }
        }

        app.map[app.getMapX(this)][app.getMapY(this)] = null;
    }

}
