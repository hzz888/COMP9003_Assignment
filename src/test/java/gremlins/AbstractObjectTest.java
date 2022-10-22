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
        this.app = new App();
        this.testObject = new TestObject(this.app, null, 0, 0);
    }

    @AfterEach
    void tearDown() {
        this.app = null;
        this.testObject = null;
    }


    @Test
    void getX() {
        assertEquals(0, this.testObject.getX());
    }

    @Test
    void getY() {
        assertEquals(0, this.testObject.getY());
    }

    @Test
    void setX() {
        this.testObject.setX(1);
        assertEquals(1, this.testObject.getX());
    }

    @Test
    void setY() {
        this.testObject.setY(2);
        assertEquals(2, this.testObject.getY());
    }

    @Test
    void tick() {
        this.testObject.tick(app);
        assertEquals(0, this.testObject.getX());
        assertEquals(0, this.testObject.getY());
    }

    @Test
    void collide() {
        TestObject testObject1 = new TestObject(app, null, 10, 10);
        assertNotNull(this.testObject.collide(testObject1));
    }
}

