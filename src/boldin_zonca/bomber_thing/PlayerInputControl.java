/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Boldin_Zonca.BomberThing;

import com.jme3.input.controls.AnalogListener;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author david
 */
public class PlayerInputControl extends AbstractControl implements AnalogListener {
    
    private Player player;
    private Game game;
    private float speed = 25;
    private String playerName; //used to identify which player inputs are for
    
    public PlayerInputControl(Game g, Player p, String s) {
        game = g;
        player = p;
        playerName = s;
    }
    
    public String getPlayerName() {
        return playerName;
    }

    @Override
    protected void controlUpdate(float tpf) {
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    public void onAnalog(String name, float value, float tpf) {
        Vector3f position = player.getLocalTranslation();
        if (name.equals(playerName + "Up")) {
            position.z -= tpf * speed;
        } else if (name.equals(playerName + "Down")) {
            position.z += tpf * speed;
        } else if (name.equals(playerName + "Left")) {
            position.x -= tpf * speed;
        } else if (name.equals(playerName + "Right")) {
            position.x += tpf * speed;
        }
        player.setLocalTranslation(position);
    }
    
}
