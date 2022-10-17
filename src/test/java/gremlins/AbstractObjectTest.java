package gremlins;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PImage;
import processing.core.PApplet;
import static org.junit.jupiter.api.Assertions.*;


class TestObject extends AbstractObject{
    public TestObject(App app, PImage image, int x, int y){
        super(app,image,x,y);
    }
    @Override
    public void tick(App app){
        app.image(this.objectSprite,x,y);
    }
}

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
        testObject.drawObject(app);
        assertNotNull(testObject.objectSprite);
    }

    @Test
    void getX() {
        assertEquals(0,testObject.getX());
    }

    @Test
    void getY() {
        assertEquals(0,testObject.getY());
    }

    @Test
    void setX() {
        testObject.setX(1);
        assertEquals(1,testObject.getX());
    }

    @Test
    void setY() {
        testObject.setX(1);
        assertEquals(1,testObject.getX());
    }

    @Test
    void tick() {
        testObject.tick(app);
        assertNotNull(testObject.objectSprite);
    }

    @Test
    void collide() {
        TestObject testObject2 = new TestObject(app,app.stoneWallImage,0,0);
        assertNotNull(testObject.collide(testObject2));
        TestObject testObject3 = new TestObject(app,app.stoneWallImage,2,2);
        assertNotNull(testObject.collide(testObject3));
    }
}

