package invaders.factory;

import invaders.engine.GameEngine;
import invaders.physics.Collider;
import invaders.physics.Vector2D;
import invaders.prototype.Prototype;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

import java.io.File;

public class EnemyProjectile extends Projectile{
    private ProjectileStrategy strategy;

    public EnemyProjectile(Vector2D position, ProjectileStrategy strategy, Image image) {
        super(position,image);
        this.strategy = strategy;
    }

    @Override
    public void update(GameEngine model) {
        strategy.update(this);

        if(this.getPosition().getY()>= model.getGameHeight() - this.getImage().getHeight()){
            this.takeDamage(1);
        }

    }
    @Override
    public String getRenderableObjectName() {
        return "EnemyProjectile";
    }

    @Override
    public Prototype clone() {
        ProjectileStrategy strategy = (ProjectileStrategy) this.strategy.clone();
        Vector2D position = (Vector2D) this.getPosition().clone();
        Image image = this.getImage();

        return new EnemyProjectile(position, strategy, image);
    }
}
