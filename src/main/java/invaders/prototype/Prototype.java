/**
 * class created for cloning the undo function
 * most classes that now implement/ extend renderables will also implement and extend prototype because they will need to be cloned and saved for undo function
 */

package invaders.prototype;

public interface Prototype {
    Prototype clone();
}
