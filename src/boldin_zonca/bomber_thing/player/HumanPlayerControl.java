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

/**
 *
 * @author david
 */
public class HumanPlayerControl extends AbstractPlayerControl implements AnalogListener, ActionListener
{
    private Game game;
    private float speed = 25;
    private boolean PICK_UP_PRESSED = false;

    public HumanPlayerControl(Game aGame, String name, KeyTrigger[] mappings)
    {
        game = aGame;
        
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
        String playerName = spatial.getName();
        Vector3f position = spatial.getLocalTranslation();
        if (name.equals(playerName + "Up"))
        {
            position.z -= tpf * speed;
            move(position);
        } 
        else if (name.equals(playerName + "Down"))
        {
            position.z += tpf * speed;
            move(position);
        } 
        else if (name.equals(playerName + "Left"))
        {
            position.x -= tpf * speed;
            move(position);
        } 
        else if (name.equals(playerName + "Right"))
        {
            position.x += tpf * speed;
            move(position);
        }
    }

    public void onAction(String name, boolean isPressed, float tpf)
    {
        String playerName = spatial.getName();
        if (name.equals(playerName + "PlaceBomb") && !isPressed)
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