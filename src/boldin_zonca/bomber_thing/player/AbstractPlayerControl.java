package boldin_zonca.bomber_thing.player;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.jme3.math.Vector3f;
import boldin_zonca.bomber_thing.IDestructable;

/**
 * @author David Boldin & Dan Zonca
 */
public abstract class AbstractPlayerControl extends AbstractControl implements IDestructable
{
    //public enum Direction {UP, DOWN, LEFT, RIGHT};
    
    public void move(Vector3f position)
    {
        spatial.setLocalTranslation(position);
    }

    public void placeBomb()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void kick()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void pickUp()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void reset()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    //TODO Fix Player onHit()
    public void onHit(Player assailant)
    {
        reset();
    }
}
