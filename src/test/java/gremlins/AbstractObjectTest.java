package gremlins;

import org.junit.jupiter.api.*;
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

public class AbstractObjectTest {
    App app;
    TestObject testObject;

    @BeforeEach
    void setUp() {
        this.app = new App();
        this.app.loop();
        PApplet.runSketch(new String[]{"App"}, this.app);
        this.app.setup();
        // this.app.delay(1000);
        this.app.gameStarted = true;
        this.testObject = new TestObject(app,app.stoneWallImage,0,0);
    }

    @AfterEach
    void tearDown() {
        this.app = null;
        this.testObject = null;
    }

    @Test
    void drawObject() {
        this.testObject.drawObject(this.app);
        assertNotNull(this.testObject.objectSprite);
    }

    @Test
    void getX() {
        assertEquals(0,this.testObject.getX());
    }

    @Test
    void getY() {
        assertEquals(0,this.testObject.getY());
    }

    @Test
    void setX() {
        this.testObject.setX(1);
        assertEquals(1,this.testObject.getX());
    }

    @Test
    void setY() {
        this.testObject.setX(1);
        assertEquals(1,this.testObject.getX());
    }

    @Test
    void tick() {
        this.testObject.tick(app);
        assertNotNull(this.testObject.objectSprite);
    }

    @Test
    void collide() {
        TestObject testObject2 = new TestObject(this.app,this.app.stoneWallImage,0,0);
        assertNotNull(this.testObject.collide(testObject2));
        TestObject testObject3 = new TestObject(this.app,this.app.stoneWallImage,2,2);
        assertNotNull(this.testObject.collide(testObject3));
    }
}

