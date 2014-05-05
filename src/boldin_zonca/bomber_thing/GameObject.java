package boldin_zonca.bomber_thing;

import com.jme3.scene.Node;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;

/**
 * @author David Boldin & Dan Zonca
 */
public abstract class GameObject extends Node
{
    public GameObject(AssetManager assetManager, String filename)
    {
        this.attachChild(assetManager.loadModel(filename));
        this.name = "model";
    }
    
    public GameObject(AssetManager assetManager, String filename, Material mat) {
        Spatial model = assetManager.loadModel(filename);
        model.setMaterial(mat);
        this.attachChild(model);
        this.name = "model";
    }
}
