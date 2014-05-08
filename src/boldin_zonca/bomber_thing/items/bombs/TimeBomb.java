
package boldin_zonca.bomber_thing.items.bombs;

import boldin_zonca.bomber_thing.items.bombs.AbstractBomb;
import boldin_zonca.bomber_thing.player.Player;
import boldin_zonca.bomber_thing.IExplosive;
import boldin_zonca.bomber_thing.IUpdatable;
import com.jme3.scene.Node;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;

/**
 *
 * @author dan
 */
public class TimeBomb extends AbstractBomb implements IUpdatable
{
    public final float DEFAULT_FUSE_TIME = 3;
    protected float maxFuseTime;
    protected float currFuseTime;
    protected float explosionRadius;
    private static Material redTexture;
    private boolean isRed;
    
    public TimeBomb(AssetManager assetManager, Player player)
    {
        super(assetManager, "/Models/TimeBomb/TimeBomb.mesh.j3o", player);
        currFuseTime = 0;
        maxFuseTime = DEFAULT_FUSE_TIME;
        explosionRadius = owner.getBombRadius();
        isRed = false;
    }
    
    public TimeBomb(AssetManager assetManager, float radius, float time)
    {
        super(assetManager, "/Models/TimeBomb/TimeBomb.mesh.j3o", null);
        currFuseTime = 0;
        maxFuseTime = time;
        explosionRadius = radius;
        isRed = false;
    }
    
    public void detonate()
    {
        //System.out.println("Detonating TimeBomb!");
        Explosion e = new Explosion(3f, explosionRadius, super.assetManager);
        Vector3f pos = this.getLocalTranslation();
        e.setLocalTranslation(pos);
        this.getParent().attachChild(e);
        if(owner != null) {
            super.owner.setBombCount(super.owner.getBombCount() - 1);
        }
        RigidBodyControl control = this.getControl(RigidBodyControl.class);
        if (control != null) {
            control.setEnabled(false);
            control.getPhysicsSpace().remove(control);
            this.removeControl(control);
            control.destroy();
            //is this thorough enough? pls no lag pls
        }
        this.removeFromParent();
    }

    public void onHit(Player assailant)
    {
        //detonate();
    }
    
    public void update(float tpf) {
        currFuseTime += tpf;
        if (currFuseTime >= maxFuseTime) {
            detonate();
        } else if (!isRed && currFuseTime >= maxFuseTime - 1f) {
            model.setMaterial(redTexture);
        }
    }
    
    public static void setRedTexture(Material mat) {
        redTexture = mat;
    }
    
}
