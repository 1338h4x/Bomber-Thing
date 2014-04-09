package boldin_zonca.bomber_thing.level;

import com.jme3.math.Vector3f;
import boldin_zonca.bomber_thing.GameObject;
import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;

/**
 * @author David Boldin & Dan Zonca
 */
public class Level extends GameObject
{   

    public Level(AssetManager assetManager, String filename)
    {
        super(assetManager, filename);
    }
    
    //TODO Make getStartPositions() abstract again && make sure world scaling & rotation considered.
    public Vector3f[] getStartPositions()
    {
        return new Vector3f[]{
            new Vector3f(-60, 0, -60),
            new Vector3f(60, 0, 60),
            new Vector3f(60, 0, -60),
            new Vector3f(-60, 0, 60),
        };
    }
}
