package gremlins;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BrickWallDestructionTest {
    App app;
    BrickWallDestruction brickWallDestruction;

    @BeforeEach
    void setUp() {
        this.app = new App();
        this.app.setup();
        this.app.delay(1000);
        this.app.loop();
        PApplet.runSketch(new String[]{"App"}, this.app);

        this.brickWallDestruction = new BrickWallDestruction(this.app, 0, 0);
    }

    @AfterEach
    void tearDown() {
        this.app = null;
        this.brickWallDestruction = null;
    }

    @Test
    void tick() {
        this.brickWallDestruction.frames = 5;
        this.brickWallDestruction.tick(this.app);
        assertEquals(1, this.brickWallDestruction.destroyPicIndex);

        this.brickWallDestruction.frames = 13;
        this.brickWallDestruction.tick(this.app);
        assertEquals(3, this.brickWallDestruction.destroyPicIndex);
    }
}