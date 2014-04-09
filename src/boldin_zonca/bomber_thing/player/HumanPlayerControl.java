/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boldin_zonca.bomber_thing.player;

import boldin_zonca.bomber_thing.Game;
import boldin_zonca.bomber_thing.Main;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.input.controls.ActionListener;
import boldin_zonca.bomber_thing.items.bombs.BombFactory;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.bullet.control.BetterCharacterControl;

/**
 *
 * @author david
 */
public class HumanPlayerControl extends AbstractPlayerControl implements AnalogListener, ActionListener
{
    private float speed = 25;
    private boolean PICK_UP_PRESSED = false;

    public HumanPlayerControl(Game theGame, KeyTrigger[] mappings)
    {        
        super(theGame);
        //initControls(mappings);
    }
    
    @Override
    protected void controlUpdate(float tpf)
    {

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp){}

    public void onAnalog(String name, float value, float tpf)
    {
//        String playerName = spatial.getName();
//        //Vector3f position = spatial.getLocalTranslation();
//        Vector3f velocity = spatial.getControl(BetterCharacterControl.class)
//                                   .getWalkDirection();
//        if (name.equals(playerName + "Up"))
//        {
////            position.z -= tpf * speed;
////            move(position);
//        } 
//        else if (name.equals(playerName + "Down"))
//        {
////            position.z += tpf * speed;
////            move(position);
//        } 
//        else if (name.equals(playerName + "Left"))
//        {
////            position.x -= tpf * speed;
////            move(position);
//        } 
//        else if (name.equals(playerName + "Right"))
//        {
////            position.x += tpf * speed;
////            move(position);
//        }
    }

    public void onAction(String name, boolean isPressed, float tpf)
    {
        String playerName = spatial.getName();
        
        Vector3f velocity = spatial.getControl(BetterCharacterControl.class)
                                   .getWalkDirection();
        if (name.equals(playerName + "Up"))
        {
            if (isPressed)
            {
                velocity.z = -speed;
            }
            else if (velocity.z < 0)
            {
                velocity.z = 0;
            }
        } 
        else if (name.equals(playerName + "Down"))
        {
            if (isPressed)
            {
                velocity.z = speed;
            }
            else if (velocity.z > 0)
            {
                velocity.z = 0;
            }
        } 
        else if (name.equals(playerName + "Left"))
        {
            if (isPressed)
            {
                velocity.x = -speed;
            }
            else if (velocity.x < 0)
            {
                velocity.x = 0;
            }
        } 
        else if (name.equals(playerName + "Right"))
        {
            if (isPressed)
            {
                velocity.x = speed;
            }
            else if (velocity.z > 0)
            {
                velocity.x = 0;
            }
        }
        else if (name.equals(playerName + "PlaceBomb") && !isPressed)
        {
            placeBomb();
        }
        else if (name.equals(playerName + "pickUp") && !isPressed)
        {
            PICK_UP_PRESSED = false;
            pickUp();
        }
        if (name.equals(playerName + "pickUp") && isPressed)
        {
            PICK_UP_PRESSED = true;
        }
    }
}
