package boldin_zonca.bomber_thing;

import com.jme3.scene.Node;
import com.jme3.asset.AssetManager;

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
}
