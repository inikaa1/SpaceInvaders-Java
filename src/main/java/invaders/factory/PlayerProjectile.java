package invaders.factory;

import invaders.engine.GameEngine;
import invaders.physics.Vector2D;
import invaders.prototype.Prototype;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

import javax.management.ValueExp;
import java.io.File;

public class PlayerProjectile extends Projectile {
    private ProjectileStrategy strategy;
    public PlayerProjectile(Vector2D position, ProjectileStrategy strategy) {
        super(position, new Image(new File("src/main/resources/player_shot.png").toURI().toString(), 10, 10, true, true));
        this.strategy = strategy;
    }
    @Override
    public void update(GameEngine model) {
        strategy.update(this);

        if(this.getPosition().getY() <= this.getImage().getHeight()){
            this.takeDamage(1);
        }
    }
    @Override
    public String getRenderableObjectName() {
        return "PlayerProjectile";
    }

    @Override
    public Prototype clone() {
        ProjectileStrategy strategy = (ProjectileStrategy) this.strategy.clone();
        Vector2D position = (Vector2D) this.getPosition().clone();
        return new PlayerProjectile(position, strategy);
    }
}
