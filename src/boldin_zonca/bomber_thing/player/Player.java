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
import boldin_zonca.bomber_thing.IUpdatable;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;

/**
 *
 * @author David Boldin & Dan Zonca
 */
public class Player extends GameObject implements IUpdatable
{
    private final int INITIAL_MAX_BOMBS = 2;
    private final int MAX_MAX_BOMBS = 10;
    private final float INITIAL_RADIUS = 25f;
    private final float MAX_RADIUS = 100f;
    private final int START_LIVES = 5;
    private final float TIME_TO_RESPAWN = 3f;
    
    public enum State {ALIVE, UNCONSCIOUS, DEAD};
    private State curState;
    private final Vector3f startPos;
    
    private boolean hasExtraHit;
    private boolean canKick;
    private boolean canPickUp;
    private int maxBombs;
    private int bombCount;
    private float bombRadius;    
    private BombType bombType;
    private AbstractEffect currEffect;
    private int lives;
    private float respawnTimer;
    
    private BitmapText hudText;
    
    public Player(AssetManager assetManager, String aName, Material mat, Vector3f pos, Vector3f dir)
    {
        super(assetManager, "Models/Player/Player.mesh.j3o", mat);
        name = aName;
//        Geometry geom = (Geometry) this.getChild("model");
//        geom.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
//        geom.setMaterial(mat);
        startPos = pos;
        this.setLocalTranslation(startPos);
        lives = START_LIVES;
        BitmapFont myFont = assetManager.loadFont("Interface/Fonts/Arial.fnt");
        hudText = new BitmapText(myFont, false);
        hudText.setText(name + ": " + lives);
        
        reset();        
    }
    
    final public void reset()
    {
        hasExtraHit = false;
        canKick = false;
        canPickUp = false;
        maxBombs = INITIAL_MAX_BOMBS;
        bombCount = 0;
        bombRadius = INITIAL_RADIUS;    
        bombType = BombType.TIME;
        currEffect = null;
        curState = State.ALIVE;
        respawnTimer = 0;
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
        if (maxBombs > MAX_MAX_BOMBS)
            this.maxBombs = MAX_MAX_BOMBS;
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
    
    public void takeDamage() {
        if (curState == State.ALIVE) {
            lives--;
            hudText.setText(name + ": " + lives);
            if (lives > 0) {
                curState = State.UNCONSCIOUS;
                respawnTimer = TIME_TO_RESPAWN;
            } else {
                curState = State.DEAD;
            }
            //Quick and dirty shortcut to get the player offscreen and not interacting with physics?
            //Just hide them far away!
            //...terrible, I know. But it'll do the trick.
            this.getControl(BetterCharacterControl.class).warp(new Vector3f(100000f, 100000f, 100000f));
        }
    }
    
    public State getState() {
        return curState;
    }
    
    public void update(float tpf) {
        if (curState == State.UNCONSCIOUS) {
            respawnTimer -= tpf;
            if (respawnTimer <= 0) {
                reset();
                this.getControl(BetterCharacterControl.class).warp(startPos);
            }
        }
    }
    
    public BitmapText getHudText() {
        return hudText;
    }
}
