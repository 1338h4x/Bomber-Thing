/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boldin_zonca.bomber_thing.items.bombs;

import boldin_zonca.bomber_thing.IUpdatable;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author david
 */
public class Explosion extends Node implements IUpdatable {
    private float maxDuration;
    private float maxRadius;
    private float time;
    private Sphere sphere;
    
    private final float MIN_RADIUS = 5f;
    
    public Explosion(float duration, float radius, AssetManager assetManager) {
        time = 0f;
        maxDuration = duration;
        maxRadius = radius;
        //System.out.println("Duration: " + maxDuration + ", radius: " + maxRadius);
        
        sphere = new Sphere(32, 32, MIN_RADIUS);
        Geometry geo = new Geometry("ExplosionSphere", sphere);
        
        //There should be an better way to actually pass this in from where Game
        //defines mats, but seems like I'd need a lot of refactoring to reach it...
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
        mat.setColor("Diffuse", ColorRGBA.Red);
        geo.setMaterial(mat);
        this.attachChild(geo);
    }

    public void update(float tpf) {
        time += tpf;
        if (time <= maxDuration) {
            float newRadius = MIN_RADIUS + (maxRadius - MIN_RADIUS) * (time / maxDuration);
            //System.out.println("Radius is " + newRadius);
            sphere.updateGeometry(32, 32, newRadius);
        } else {
            this.removeFromParent();
        }
    }
    
    public float getRadius() {
        return sphere.getRadius();
    }
    
    public float getMaxRadius() {
        return maxRadius;
    }
    
}
