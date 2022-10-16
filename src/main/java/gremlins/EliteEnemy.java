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

    @Override
    public void gremlinRespawn(App app) {
        app.gremlins.remove(this);

        int index = App.RANDOM_GENERATOR.nextInt(app.emptyTiles.size());
        int[] coordinates = app.emptyTiles.get(index);
        int newX = coordinates[0];
        int newY = coordinates[1];
        double radiusDistance = Math.sqrt(Math.pow(newX - app.player.getX(), 2) + Math.pow(newY - app.player.getY(), 2));
        while (radiusDistance < 10 * App.SPRITESIZE) {
            index = App.RANDOM_GENERATOR.nextInt(app.emptyTiles.size());
            coordinates = app.emptyTiles.get(index);
            newX = coordinates[0];
            newY = coordinates[1];
            radiusDistance = Math.sqrt(Math.pow(newX - app.player.getX(), 2) + Math.pow(newY - app.player.getY(), 2));
        }
        EliteEnemy newElite = new EliteEnemy(app, newX, newY);
        app.gremlins.add(newElite);
    }


}
