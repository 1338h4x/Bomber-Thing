package Boldin_Zonca.BomberThing;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.scene.Node;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;
import com.jme3.input.controls.ActionListener;


/**
 *
 * @author David Boldin & Dan Zonca
 */
public class Player extends Node implements ActionListener
{
    private final float headRadius = 4;
    private final float bodyRadius = 3.5f;
    float height = 10;
    Geometry geomHead;
    Geometry geomBody;
    //CapsuleCollisionShape collision;
    
    public Player(String name, Material mat, Vector3f pos, Vector3f dir)
    {
        this.name = name;
        this.setLocalTranslation(pos);
        
        Cylinder body = new Cylinder(20, 10, bodyRadius, height, true);
        geomBody = new Geometry("Body", body);
        geomBody.rotate(90f * FastMath.DEG_TO_RAD, 0, 0);
        geomBody.setLocalTranslation(0f, height / 2f, 0f);
        geomBody.setShadowMode(RenderQueue.ShadowMode.Cast);

        Sphere head = new Sphere(20, 20, headRadius);
        geomHead = new Geometry("Head", head);
        geomHead.rotate(90f * FastMath.DEG_TO_RAD, 0, 0);
        geomHead.setLocalTranslation(0f, height + headRadius, 0f);
        geomHead.setShadowMode(RenderQueue.ShadowMode.Cast);

        geomHead.setMaterial(mat);
        geomBody.setMaterial(mat);
        this.attachChild(geomBody);
        this.attachChild(geomHead);
        //collision = new CapsuleCollisionShape(5,6,1);
    }

    public void onAction(String string, boolean bln, float f)
    {
        
    }
    
    public float getHeight() {
        return height;
    }
    
    public float getRadius() {
        if (headRadius > bodyRadius) {
            return headRadius;
        }else {
            return bodyRadius;
        }
    }
}
