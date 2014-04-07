package boldin_zonca.bomber_thing;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

/**
 * test
 *
 * @author David Boldin & Dan Zonca
 */
public class Main extends SimpleApplication
{
    static final boolean DEBUG = true;

    public static void main(String[] args)
    {
        Main app = new Main();
        initAppScreen(app);
        app.start();
    }

    @Override
    public void simpleInitApp()
    {
        Game game = new Game();
        stateManager.attach(game);
    }

    private static void initAppScreen(SimpleApplication app)
    {
        AppSettings aps = new AppSettings(true);
        aps.setResolution(1024, 768);
        aps.setVSync(true);
        app.setSettings(aps);
        app.setShowSettings(false);
    }
    
    @Override
    public void simpleUpdate(float tpf)
    {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm)
    {
        //TODO: add render code
    }
}
