package invaders.gameobject;

import invaders.engine.GameEngine;
import invaders.factory.EnemyProjectileFactory;
import invaders.factory.Projectile;
import invaders.factory.ProjectileFactory;
import invaders.physics.Collider;
import invaders.physics.Vector2D;
import invaders.prototype.Prototype;
import invaders.rendering.Renderable;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Enemy implements GameObject, Renderable {
    private Vector2D position;
    private int lives = 1;
    private Image image;
    private int xVel = -1;

    private ArrayList<Projectile> enemyProjectile = new ArrayList<>();
    private ArrayList<Projectile> pendingToDeleteEnemyProjectile = new ArrayList<>();
    private ProjectileStrategy projectileStrategy;
    private ProjectileFactory projectileFactory;
    private Image projectileImage;
    private Random random = new Random();

    public Enemy(Vector2D position, int lives, Image image, int xVel, ArrayList<Projectile> enemyProjectile, ArrayList<Projectile> pendingToDeleteEnemyProjectile, ProjectileStrategy projectileStrategy, ProjectileFactory projectileFactory, Image projectileImage, Random random) {
        this.position = position;
        this.lives = lives;
        this.image = image;
        this.xVel = xVel;
        this.enemyProjectile = enemyProjectile;
        this.pendingToDeleteEnemyProjectile = pendingToDeleteEnemyProjectile;
        this.projectileStrategy = projectileStrategy;
        this.projectileFactory = projectileFactory;
        this.projectileImage = projectileImage;
        this.random = random;
    }

    public Enemy(Vector2D position) {
        this.position = position;
        this.projectileFactory = new EnemyProjectileFactory();
        this.enemyProjectile = new ArrayList<>();
        this.pendingToDeleteEnemyProjectile = new ArrayList<>();
    }

    @Override
    public void start() {}

    @Override
    public void update(GameEngine engine) {
        if(enemyProjectile.size()<3){
            if(this.isAlive() &&  random.nextInt(120)==20){
                Projectile p = projectileFactory.createProjectile(new Vector2D(position.getX() + this.image.getWidth() / 2, position.getY() + image.getHeight() + 2),projectileStrategy, projectileImage);
                enemyProjectile.add(p);
                engine.getPendingToAddGameObject().add(p);
                engine.getPendingToAddRenderable().add(p);
            }
        }else{
            pendingToDeleteEnemyProjectile.clear();
            for(Projectile p : enemyProjectile){
                if(!p.isAlive()){
                    engine.getPendingToRemoveGameObject().add(p);
                    engine.getPendingToRemoveRenderable().add(p);
                    pendingToDeleteEnemyProjectile.add(p);
                }
            }

            for(Projectile p: pendingToDeleteEnemyProjectile){
                enemyProjectile.remove(p);
            }
        }

        if(this.position.getX()<=this.image.getWidth() || this.position.getX()>=(engine.getGameWidth()-this.image.getWidth()-1)){
            this.position.setY(this.position.getY()+25);
            xVel*=-1;
        }

        this.position.setX(this.position.getX() + xVel);

        if((this.position.getY()+this.image.getHeight())>=engine.getPlayer().getPosition().getY()){
            engine.getPlayer().takeDamage(Integer.MAX_VALUE);
        }

        /*
        Logic TBD
         */

    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return this.image.getWidth();
    }

    @Override
    public double getHeight() {
       return this.image.getHeight();
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setProjectileImage(Image projectileImage) {
        this.projectileImage = projectileImage;
    }

    @Override
    public void takeDamage(double amount) {
        this.lives-=1;
    }

    @Override
    public double getHealth() {
        return this.lives;
    }

    @Override
    public String getRenderableObjectName() {
        return "Enemy";
    }

    @Override
    public boolean isAlive() {
        return this.lives>0;
    }

    public void setProjectileStrategy(ProjectileStrategy projectileStrategy) {
        this.projectileStrategy = projectileStrategy;
    }

    @Override
    public Prototype clone(){
        Vector2D position = (Vector2D) this.position.clone();
        int newLives = this.lives;
        Image newImage = this.image;
        int newXVel = this.xVel;

        ArrayList<Projectile> newEnemyProjectile = new ArrayList<>();
        for(Projectile p : this.enemyProjectile){
            newEnemyProjectile.add((Projectile) p.clone());
        }
        ArrayList<Projectile> newPendingToDeleteEnemyProjectile = new ArrayList<>();
        for(Projectile p : this.pendingToDeleteEnemyProjectile){
            newPendingToDeleteEnemyProjectile.add((Projectile) p.clone());
        }
        ProjectileStrategy newProjectileStrategy = (ProjectileStrategy) this.projectileStrategy.clone(); // Assuming it's immutable
        ProjectileFactory newProjectileFactory = this.projectileFactory; // Assuming it's immutable
        Image newProjectileImage = this.projectileImage; // Assuming Image is immutable
        Random newRandom = this.random; // Assuming Random is immutable

        return new Enemy(position, newLives, newImage, newXVel, newEnemyProjectile, newPendingToDeleteEnemyProjectile, newProjectileStrategy, newProjectileFactory, newProjectileImage, newRandom);
    }
}
