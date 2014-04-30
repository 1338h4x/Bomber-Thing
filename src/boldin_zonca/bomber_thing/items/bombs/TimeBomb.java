
package boldin_zonca.bomber_thing.items.bombs;

import boldin_zonca.bomber_thing.items.bombs.AbstractBomb;
import boldin_zonca.bomber_thing.player.Player;
import boldin_zonca.bomber_thing.IExplosive;
import boldin_zonca.bomber_thing.IUpdatable;
import com.jme3.scene.Node;
import com.jme3.asset.AssetManager;

/**
 *
 * @author dan
 */
public class TimeBomb extends AbstractBomb implements IUpdatable
{
    protected float maxFuseTime;
    protected float currFuseTime;
    public float DEFAULT_FUSE_TIME = 4;
    
    public TimeBomb(AssetManager assetManager, Player player)
    {
        super(assetManager, "/Models/TimeBomb/TimeBomb.mesh.j3o", player);
        currFuseTime = 0;
        maxFuseTime = DEFAULT_FUSE_TIME;
    }
    
    public TimeBomb(AssetManager assetManager, Player player, float fuseTime)
    {
        super(assetManager, "", player);
        currFuseTime = 0;
        maxFuseTime = fuseTime;
    }
    
    public void detonate()
    {
        System.out.println("Detonating TimeBomb!");
    }

    public void onHit(Player assailant)
    {
        detonate();
    }
    
    public void update(float tpf) {
        currFuseTime += tpf;
        if (currFuseTime >= maxFuseTime) {
            detonate();
        }
    }
    
}
