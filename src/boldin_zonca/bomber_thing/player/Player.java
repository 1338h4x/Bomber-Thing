package boldin_zonca.bomber_thing.player;

import com.jme3.scene.Node;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;
import com.jme3.asset.AssetManager;
import boldin_zonca.bomber_thing.items.AbstractEffect;
import boldin_zonca.bomber_thing.items.bombs.BombFactory.BombType;
import boldin_zonca.bomber_thing.GameObject;

/**
 *
 * @author David Boldin & Dan Zonca
 */
public class Player extends GameObject
{
    private final int MAX_BOMBS = 10;
    private final float MAX_RADIUS = 100f;
    
    public enum State {ALIVE, UNCONCIOUS, DEAD};
    private final Vector3f startPos;
    
    private boolean hasExtraHit;
    private boolean canKick;
    private boolean canPickUp;
    private int maxBombs;
    private int bombCount;
    private float bombRadius;    
    private BombType bombType;
    private AbstractEffect currEffect;
    
    public Player(AssetManager assetManager, String aName, Material mat, Vector3f pos, Vector3f dir)
    {
        super(assetManager, "Models/Player/Player.mesh.j3o");
        name = aName;
//        Geometry geom = (Geometry) this.getChild("model");
//        geom.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
//        geom.setMaterial(mat);
        this.setLocalTranslation(pos);
        startPos = pos;
        
        reset();        
    }
    
    final public void reset()
    {
        hasExtraHit = false;
        canKick = false;
        canPickUp = false;
        maxBombs = 1;
        bombCount = 0;
        bombRadius = 20f;    
        bombType = BombType.TIME;
        currEffect = null;
    }
    
    public boolean getHasExtraHit()
    {
        return hasExtraHit;
    }

    public void setHasExtraHit(boolean hasExtraHit)
    {
        this.hasExtraHit = hasExtraHit;
    }
    
    public boolean getCanKick()
    {
        return canKick;
    }

    public void setCanKick(boolean canKick)
    {
        this.canKick = canKick;
    }
    
    public boolean getCanPickup()
    {
        return canPickUp;
    }

    public void setCanPickup(boolean canPickUp)
    {
        this.canPickUp = canPickUp;
    }

    public int getMaxBombs()
    {
        return maxBombs;
    }

    public void setMaxBombs(int maxBombs)
    {
        if (maxBombs > MAX_BOMBS)
            this.maxBombs = MAX_BOMBS;
        else
            this.maxBombs = maxBombs;
    }

    public int getBombCount()
    {
        return bombCount;
    }

    public void setBombCount(int bombCount)
    {
        this.bombCount = bombCount;
    }

    public float getBombRadius()
    {
        return bombRadius;
    }

    public void setBombRadius(float bombRadius)
    {
        if (bombRadius > MAX_RADIUS)
            this.bombRadius = MAX_RADIUS;
        else
            this.bombRadius = bombRadius;
    }

    public BombType getBombType()
    {
        return bombType;
    }
    
    public void setBombType(BombType type)
    {
        bombType = type;
    }
    
    public AbstractEffect getCurrEffect()
    {
        return currEffect;
    }

    public void setCurrEffect(AbstractEffect currEffect)
    {
        if (this.currEffect != null)
            this.currEffect.remove();
        this.currEffect = currEffect;
    }
}
