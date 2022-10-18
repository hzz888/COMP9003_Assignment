package gremlins;

/**
 * This class is used to define animation of brickwall destruction.
 * @author hzz
 */
public class BrickWallDestruction extends AbstractObject {
    public int destroyPicIndex;
    public int frames;

    /**
     * Constructor for BrickWallDestruction.
     * @param app The main app.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public BrickWallDestruction(App app, int x, int y) {
        super(app, app.brickWallDestroyImages[0], x, y);
        this.destroyPicIndex = 0;
        this.frames = 0;
    }

    /**
     * Display the animation, totally 16 frames, each picture will be displayed for 4 frames.
     */
    @Override
    public void tick(App app) {

        if (this.frames<=16) {
            if (this.destroyPicIndex < app.brickWallDestroyImages.length) {
                this.objectSprite = app.brickWallDestroyImages[this.destroyPicIndex];
                this.drawObject(app);
                this.frames++;
                if (this.frames %4 ==0) {
                    this.destroyPicIndex++;
                }
            }
        } else {
            app.brickWallDestructions.remove(this);
        }
    }
}
