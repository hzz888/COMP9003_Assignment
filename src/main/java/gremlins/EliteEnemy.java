package gremlins;


/**
 * @author hzz
 */
public class EliteEnemy extends Gremlin {
    public EliteEnemy(App app, int x, int y) {
        super(app, x, y);
        this.objectSprite = app.eliteEnemyImage;
        this.gremlinMoveSpeed = 2;
        this.gremlinCoolDown = app.enemyCooldown / 2;
    }

    @Override
    public void gremlinAttack(App app) {
        if (!this.gremlinCooling) {
            Slime newSlime1= new Slime(app,this.x,this.y,"left");
            Slime newSlime2= new Slime(app,this.x,this.y,"right");
            Slime newSlime3= new Slime(app,this.x,this.y,"up");
            Slime newSlime4= new Slime(app,this.x,this.y,"down");
            app.slimes.add(newSlime1);
            app.slimes.add(newSlime2);
            app.slimes.add(newSlime3);
            app.slimes.add(newSlime4);
            this.gremlinCooling = true;
            this.gremlinAttackTimer = app.millis();
        } else {
            if (app.millis() - this.gremlinAttackTimer > this.gremlinCoolDown * 1000) {
                this.gremlinCooling = false;
                this.gremlinAttackTimer = 0;
            }
        }
    }


}
