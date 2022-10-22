package gremlins;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.*;

class EliteEnemyTest {
    App app;
    EliteEnemy eliteEnemy;


    @BeforeEach
    void setUp() {
        this.app = new App();
        this.app.loop();
        PApplet.runSketch(new String[]{"App"}, this.app);
        this.app.setup();
        this.app.delay(1000);

        this.eliteEnemy = new EliteEnemy(this.app, 0, 0);
        this.app.player = new Wizard(this.app, 100, 100);
    }

    @AfterEach
    void tearDown() {
        this.app = null;
        this.eliteEnemy = null;
    }

    @Test
    void gremlinAttack() {
        this.eliteEnemy.gremlinAttack(this.app);
        assertEquals(4, this.app.slimes.size());
    }

    @Test
    void gremlinRespawn() {
        this.app.gremlins.clear();
        this.app.updateEmptyTiles();
        this.eliteEnemy.gremlinRespawn(this.app);
        assertEquals(1, this.app.gremlins.size());
        double distance = Math.sqrt(Math.pow(this.app.player.getX() - this.app.gremlins.get(0).getX(), 2) + Math.pow(this.app.player.getY() - this.app.gremlins.get(0).getY(), 2));
        assert distance > App.SPRITESIZE * 10;
    }
}