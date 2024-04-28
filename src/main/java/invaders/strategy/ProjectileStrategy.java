package invaders.strategy;

import invaders.factory.Projectile;
import invaders.physics.Vector2D;
import invaders.prototype.Prototype;

public interface ProjectileStrategy extends Prototype {
   public void update(Projectile p);
}
