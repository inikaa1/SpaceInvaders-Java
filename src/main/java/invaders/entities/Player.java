package invaders.entities;

import invaders.factory.PlayerProjectile;
import invaders.factory.PlayerProjectileFactory;
import invaders.factory.Projectile;
import invaders.factory.ProjectileFactory;
import invaders.physics.Collider;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.prototype.Prototype;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;

import invaders.strategy.NormalProjectileStrategy;
import javafx.scene.image.Image;
import org.json.simple.JSONObject;

import java.io.File;

public class Player implements Moveable, Renderable {

    private final Vector2D position;
    private double health;
    private double velocity;

    private final double width = 20;
    private final double height = 20;
    private final Image image;
    private ProjectileFactory playerProjectileFactory = new PlayerProjectileFactory();

    public Player(Vector2D position, double health, double velocity, Image image, ProjectileFactory playerProjectileFactory) {
        this.position = position;
        this.health = health;
        this.velocity = velocity;
        this.image = image;
        this.playerProjectileFactory = playerProjectileFactory;
    }

    public Player(JSONObject playerInfo){
        int x = ((Long)((JSONObject)(playerInfo.get("position"))).get("x")).intValue();
        int y = ((Long)((JSONObject)(playerInfo.get("position"))).get("y")).intValue();

        this.image = new Image(new File("src/main/resources/player.png").toURI().toString(), width, height, true, true);
        this.position = new Vector2D(x,y);
        this.health = ((Long) playerInfo.get("lives")).intValue();
        this.velocity = ((Long) playerInfo.get("speed")).intValue();

    }

    public Prototype clone(){
        Vector2D position = (Vector2D) this.position.clone();
        double health = this.health;
        double velocity = this.velocity;
        Image image = this.image;
        ProjectileFactory playerProjectileFactory =  this.playerProjectileFactory;

        return new Player(position, health, velocity, image, playerProjectileFactory);
    }

    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    @Override
    public void up() {
        return;
    }

    @Override
    public void down() {
        return;
    }

    @Override
    public void left() {
        this.position.setX(this.position.getX() - this.velocity);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getX() + this.velocity);
    }

    public Projectile shoot(){
        return playerProjectileFactory.createProjectile(new Vector2D(this.position.getX() + 5 ,this.position.getY() - 10),new NormalProjectileStrategy(),null);
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    @Override
    public String getRenderableObjectName() {
        return "Player";
    }

}
