package gremlins;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.*;

class BrickWallTest {

    App app;
    BrickWall brickWall;

    @BeforeEach
    void setUp() {
        this.app = new App();
        this.app.loop();
        PApplet.runSketch(new String[]{"App"}, this.app);
        this.app.setup();
        this.app.delay(1000);

        this.brickWall = new BrickWall(this.app, 0, 0);
    }

    @AfterEach
    void tearDown() {
        this.app = null;
        this.brickWall = null;
    }

    @Test
    void tick() {
        assertNotNull(this.brickWall);
    }

    @Test
    void destroyed() {
        this.brickWall.destroyed(this.app);
        assertNull(this.app.map[this.app.getMapX(this.brickWall)][this.app.getMapY(this.brickWall)]);
        assertEquals(1, this.app.brickWallDestructions.size());
    }
}