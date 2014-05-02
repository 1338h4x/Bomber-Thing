
package boldin_zonca.bomber_thing.items.bombs;

import boldin_zonca.bomber_thing.player.Player;
import boldin_zonca.bomber_thing.IExplosive;
import boldin_zonca.bomber_thing.IDestructable;
import com.jme3.scene.Node;
import com.jme3.scene.Geometry;
import boldin_zonca.bomber_thing.GameObject;
import com.jme3.asset.AssetManager;

/**
 *
 * @author dan
 */
public abstract class AbstractBomb extends GameObject implements IExplosive, IDestructable
{
    protected Player owner;
    protected AssetManager assetManager;

    public AbstractBomb(AssetManager assetManager, String filename, Player owner)
    {
        super(assetManager, filename);
        this.owner = owner;
        this.assetManager = assetManager;
    }
    
    public abstract void detonate();
    
    public void onHit()
    {
        detonate();
    }
}
