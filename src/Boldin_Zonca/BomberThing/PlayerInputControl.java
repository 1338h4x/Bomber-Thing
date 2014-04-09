/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Boldin_Zonca.BomberThing;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author david
 */
public class PlayerInputControl extends AbstractControl implements ActionListener {
    
    private Player player;
    private Game game;
    private float speed = 25;
    private String playerName; //used to identify which player inputs are for
    private BetterCharacterControl physics;
    
    public PlayerInputControl(Game g, Player p, BetterCharacterControl c, String s) {
        game = g;
        player = p;
        playerName = s;
        physics = c;
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

    public void onAction(String name, boolean isPressed, float tpf) {
        //Vector3f position = player.getLocalTranslation();
        Vector3f velocity = physics.getWalkDirection();
        if (name.equals(playerName + "Up")) {
            if (isPressed) {
                velocity.z = -speed;
            } else if (velocity.z < 0) {
                velocity.z = 0;
            }
        } else if (name.equals(playerName + "Down")) {
            if (isPressed) {
                velocity.z = speed;
            } else if (velocity.z > 0) {
                velocity.z = 0;
            }
        } else if (name.equals(playerName + "Left")) {
            if (isPressed) {
                velocity.x = -speed;
            } else if (velocity.x < 0) {
                velocity.x = 0;
            }
        } else if (name.equals(playerName + "Right")) {
            if (isPressed) {
                velocity.x = speed;
            } else if (velocity.x > 0) {
                velocity.x = 0;
            }
        }
        //player.setLocalTranslation(position);
    }
    
}
