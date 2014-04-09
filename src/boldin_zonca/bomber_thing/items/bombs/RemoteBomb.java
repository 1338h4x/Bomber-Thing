
package boldin_zonca.bomber_thing.items.bombs;

import boldin_zonca.bomber_thing.player.Player;
import boldin_zonca.bomber_thing.items.bombs.AbstractBomb;
import com.jme3.asset.AssetManager;
import com.jme3.input.controls.ActionListener;

/**
 *
 * @author dan
 */
public class RemoteBomb extends AbstractBomb implements ActionListener
{
    public RemoteBomb(AssetManager assetManager, Player player)
    {
        super(assetManager, "", player);
    }
    
    @Override
    public void detonate()
    {
        
    }

    public void onAction(String string, boolean isPressed, float tpf)
    {
        if (string.equals(owner.getName() + "Detonate") && !isPressed)
        {
            detonate();
        }
            
    }
    
    public void onHit(Player assailant)
    {
        detonate();
    }
    
}
