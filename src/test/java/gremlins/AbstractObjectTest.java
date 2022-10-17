package gremlins;

import org.junit.jupiter.api.*;
import processing.core.PImage;
import static org.junit.jupiter.api.Assertions.*;


class TestObject extends AbstractObject {
    public TestObject(App app, PImage image, int x, int y) {
        super(app, image, x, y);
    }

    @Override
    public void tick(App app) {
    }
}

class AbstractObjectTest {
    App app;
    TestObject testObject;


    @BeforeEach
    void setUp() {
        app = new App();
        testObject = new TestObject(app, null, 0, 0);

    }

    @AfterEach
    void tearDown() {
        app = null;
        testObject = null;
    }


    @Test
    void getX() {
        assertEquals(0, testObject.getX());
    }

    @Test
    void getY() {
        assertEquals(0, testObject.getY());
    }

    @Test
    void setX() {
        testObject.setX(1);
        assertEquals(1, testObject.getX());
    }

    @Test
    void setY() {
        testObject.setY(1);
        assertEquals(1, testObject.getY());
    }

    @Test
    void tick() {
        testObject.tick(app);
        assertEquals(0, testObject.getX());
        assertEquals(0, testObject.getY());
    }

    @Test
    void collide() {
        TestObject testObject1 = new TestObject(app, null, 0, 0);
        assertNotNull(testObject.collide(testObject1));
    }
}

