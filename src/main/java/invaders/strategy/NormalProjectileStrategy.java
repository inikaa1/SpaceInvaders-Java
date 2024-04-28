package invaders.strategy;

import invaders.factory.Projectile;
import invaders.prototype.Prototype;

public class NormalProjectileStrategy implements ProjectileStrategy {
    @Override
    public void update(Projectile p) {
        double newYPos = p.getPosition().getY() - 2;
        p.getPosition().setY(newYPos);
    }

    @Override
    public Prototype clone() {
        return new NormalProjectileStrategy();
    }
}
