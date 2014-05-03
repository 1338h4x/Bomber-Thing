package boldin_zonca.bomber_thing.player;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.jme3.math.Vector3f;
import boldin_zonca.bomber_thing.IDestructable;
import boldin_zonca.bomber_thing.Game;
import boldin_zonca.bomber_thing.items.bombs.BombFactory.BombType;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;

/**
 * @author David Boldin & Dan Zonca
 */
public abstract class AbstractPlayerControl extends AbstractControl implements IDestructable
{
    //public enum Direction {UP, DOWN, LEFT, RIGHT};
    
    protected Game game;
    
    public AbstractPlayerControl(Game theGame)
    {
        game = theGame;
    }
    
    public void move(Vector3f position)
    {
        spatial.setLocalTranslation(position);
    }

    public void placeBomb()
    {
        Player player = (Player) spatial;
        if (player.getState() == Player.State.ALIVE) {

            int bombCount = player.getBombCount();
            if (bombCount < player.getMaxBombs())
            {
                Node bombNode = game.getBombFactory().getBomb(BombType.TIME, (Player) spatial);
                bombNode.setLocalTranslation(spatial.getLocalTranslation());
                game.getApplication().getRootNode().attachChild(bombNode);

                //RigidBodyControl physBomb = new RigidBodyControl(100);
                //bombNode.addControl(physBomb);
                //game.getBulletAppState().getPhysicsSpace().add(physBomb);
                player.setBombCount(bombCount + 1);
            }
        }
    }

    public void kick()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void pickUp()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void reset()
    {
        Player player = (Player) spatial;
        
        player.reset();
    }
    //TODO Fix Player onHit()
    public void onHit(Player assailant)
    {
        reset();
    }
}
