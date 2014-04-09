
package boldin_zonca.bomber_thing.items;

import boldin_zonca.bomber_thing.IDestructable;
import com.jme3.scene.Node;
import boldin_zonca.bomber_thing.player.Player;

/**
 *
 * @author dan
 */
public class Item extends Node implements IDestructable
{
    private float maxLifeTime;
    private float currLifeTime;
    private AbstractEffect effect;
    
    public Item(AbstractEffect effect)
    {
        this.effect = effect;
    }

    public void onHit(Player assailant)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
