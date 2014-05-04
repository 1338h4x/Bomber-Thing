
package boldin_zonca.bomber_thing.items.bombs;

import boldin_zonca.bomber_thing.player.Player;
import boldin_zonca.bomber_thing.IExplosive;
import boldin_zonca.bomber_thing.IDestructable;
import com.jme3.scene.Node;
import com.jme3.scene.Geometry;
import boldin_zonca.bomber_thing.GameObject;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;

/**
 *
 * @author dan
 */
public abstract class AbstractBomb extends GameObject implements IExplosive, IDestructable
{
    protected Player owner;
    protected AssetManager assetManager;
    private boolean isSolid;

    public AbstractBomb(AssetManager assetManager, String filename, Player owner)
    {
        super(assetManager, filename);
        this.owner = owner;
        this.assetManager = assetManager;
        isSolid = false;
    }
    
    public abstract void detonate();
    
    public void onHit()
    {
        detonate();
    }
    
    public boolean isSolid() {
        return isSolid;
    }
    
    public void addPhysics(PhysicsSpace physics) {
        CollisionShape collBomb = CollisionShapeFactory.createDynamicMeshShape(this);
        RigidBodyControl physBomb = new RigidBodyControl(collBomb, 1);
        this.addControl(physBomb);
        physics.add(physBomb);
        //Lower friction = farther kicks!
        physBomb.setFriction(physBomb.getFriction()/4);
        isSolid = true;
    }
}
