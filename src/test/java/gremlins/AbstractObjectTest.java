package gremlins;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PImage;
import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import static org.junit.jupiter.api.Assertions.*;

class AbstractObjectTest {
    App app;
    TestObject testObject;
    @BeforeEach
    void setUp() {
        app = new App();
        PApplet.runSketch(new String[]{"App"}, app);
        app.setup();
        app.gameStarted = true;
        app.loop();
        testObject = new TestObject(app,app.stoneWallImage,0,0);
    }

    @AfterEach
    void tearDown() {
        app = null;
    }

    @Test
    void drawObject() {

    }

    @Test
    void getX() {
    }

    @Test
    void getY() {
    }

    @Test
    void setX() {
    }

    @Test
    void setY() {
    }

    @Test
    void tick() {
    }

    @Test
    void collide() {
    }
}

class TestObject extends AbstractObject{
    public TestObject(App app, PImage image, int x, int y){
        super(app,image,x,y);
    }

    @Override
    public void tick(App app){
        app.image(this.objectSprite,x,y);
    }
}