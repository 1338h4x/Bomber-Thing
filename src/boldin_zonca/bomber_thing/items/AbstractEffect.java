package boldin_zonca.bomber_thing.items;

import boldin_zonca.bomber_thing.player.Player;

/**
 *
 * @author David Boldin & Dan Zonca
 */
public abstract class AbstractEffect
{
    protected Player player;
    
    public void addTo(Player target)
    {
        player = target;
        addEffect();
    }
    
    public void remove()
    {
        removeEffect();
        player = null;
    }
    
    protected abstract void addEffect();
    protected abstract void removeEffect();
}
