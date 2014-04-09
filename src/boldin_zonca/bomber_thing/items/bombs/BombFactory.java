
package boldin_zonca.bomber_thing.items.bombs;

import boldin_zonca.bomber_thing.IExplosive;
import boldin_zonca.bomber_thing.player.Player;
import com.jme3.scene.Node;
import com.jme3.asset.AssetManager;



/**
 *
 * @author dan
 */
public class BombFactory
{
    public enum BombType {TIME, REMOTE, SMOKE, FORCE, DUDS};
    
    private AssetManager assetManager;
    
    public BombFactory(AssetManager assetManager)
    {
        this.assetManager = assetManager;
    }
    
    public AbstractBomb getBomb(BombType type, Player owner)
    {
        switch (type)
        {
            case TIME:
                return new TimeBomb(assetManager, owner);
            case REMOTE:
                return new RemoteBomb(assetManager, owner);
            default:
                return null;
        }
    }
}
