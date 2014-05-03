package boldin_zonca.bomber_thing;

import boldin_zonca.bomber_thing.player.Player;
import boldin_zonca.bomber_thing.player.HumanPlayerControl;
import boldin_zonca.bomber_thing.player.AbstractPlayerControl;
import boldin_zonca.bomber_thing.level.Level;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.FlyByCamera;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.debug.Grid;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.bullet.collision.shapes.CollisionShape;
import java.util.ArrayList;
import boldin_zonca.bomber_thing.items.ItemFactory;
import boldin_zonca.bomber_thing.items.bombs.BombFactory;
import boldin_zonca.bomber_thing.items.bombs.Explosion;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.font.BitmapText;
import com.jme3.scene.Spatial;

/**
 *
 * @author David Boldin & Dan Zonca
 */
public class Game extends AbstractAppState
{

    private Main app;
    private AppStateManager stateManager;
    private BulletAppState bullet;
    private InputManager inputManager;
    private BombFactory bombFactory;
    private ItemFactory itemFactory;
    private Level level;

    private Material[] playerMats;
    private ArrayList<Player> players;

    @Override
    public void initialize(AppStateManager asm, Application anApp)
    {
        app = (Main) anApp;
        stateManager = asm;
        bullet = new BulletAppState();
        stateManager.attach(bullet);
        inputManager = anApp.getInputManager();
        bombFactory = new BombFactory(app.getAssetManager());
        itemFactory = new ItemFactory(app.getAssetManager());

        initMaterials();
        initLevel();
        initLights();
        initCam();
        initPlayers(2, 0);

        showDebugInfo(Main.DEBUG);
        bullet.setDebugEnabled(Main.DEBUG);
    }

    private void initLevel()
    {
        level = new Level(app.getAssetManager(), "/Models/Level01/Level01.mesh.j3o");
        CollisionShape collLevel = CollisionShapeFactory.createMeshShape(level);
        RigidBodyControl physLevel = new RigidBodyControl(collLevel, 0);
        level.addControl(physLevel);
        app.getRootNode().attachChild(level);
        bullet.getPhysicsSpace().add(physLevel);
    }

    private void initCam()
    {
        FlyByCamera flyCam = app.getFlyByCamera();
        if (Main.DEBUG)
        {
            flyCam.setEnabled(true);
            flyCam.setMoveSpeed(30f);
        } else
        {
            flyCam.setEnabled(false);
        }

        app.getCamera().setLocation(new Vector3f(0, 160f, 140f));
        app.getCamera().lookAt(new Vector3f(0, 0, 10), Vector3f.UNIT_Y);
    }

    private void initMaterials()
    {
        playerMats = new Material[4];
        playerMats[0] = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        playerMats[0].setBoolean("UseMaterialColors", true);
        playerMats[0].setColor("Ambient", new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
        playerMats[0].setColor("Diffuse", ColorRGBA.Red);

        playerMats[1] = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        playerMats[1].setBoolean("UseMaterialColors", true);
        playerMats[1].setColor("Ambient", new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
        playerMats[1].setColor("Diffuse", ColorRGBA.Yellow);

        playerMats[2] = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        playerMats[2].setBoolean("UseMaterialColors", true);
        playerMats[2].setColor("Ambient", new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
        playerMats[2].setColor("Diffuse", ColorRGBA.Blue);

        playerMats[3] = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        playerMats[3].setBoolean("UseMaterialColors", true);
        playerMats[3].setColor("Ambient", new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
        playerMats[3].setColor("Diffuse", ColorRGBA.Orange);
    }

    private void initLights()
    {
        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(0.0f));
        app.getRootNode().addLight(ambient);
        
        AmbientLight ambientGui = new AmbientLight();
        ambientGui.setColor(ColorRGBA.White);
        app.getGuiNode().addLight(ambientGui);
        
        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-2f, -5f, -2f).normalizeLocal()));
        sun.setColor(ColorRGBA.White);
        app.getRootNode().addLight(sun);

        // SHADOW
        // the second parameter is the resolution. Experiment with it! (Must be a power of 2)
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(app.getAssetManager(), 1024, 2);
        dlsr.setLight(sun);
        app.getViewPort().addProcessor(dlsr);
    }

    //For displaying wireframe grid and axis
    private void showDebugInfo(boolean show)
    {
        if (show)
        {
            int lineWidth = 5;
            float axisLength = 150;
            Geometry geom;
            Arrow arrow;
            Material mat;
            Node rootNode = app.getRootNode();
            AssetManager assMan = app.getAssetManager();

            //Grid
            Grid grid = new Grid(((int) (axisLength) / 10) + 1, ((int) (axisLength) / 10) + 1, 10f);
            grid.setLineWidth(lineWidth / 2f);
            geom = new Geometry("Grid", grid);
            mat = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.White);
            geom.setMaterial(mat);
            geom.center().move(Vector3f.ZERO);
            rootNode.attachChild(geom);

            //Axis            
            arrow = new Arrow(new Vector3f(axisLength / 2, 0, 0));
            arrow.setLineWidth(lineWidth);
            mat = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.Blue);
            geom = new Geometry("X Axis", arrow);
            geom.setMaterial(mat);
            rootNode.attachChild(geom);

            arrow = new Arrow(new Vector3f(0, axisLength / 2, 0));
            arrow.setLineWidth(lineWidth);
            mat = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.Red);
            geom = new Geometry("X Axis", arrow);
            geom.setMaterial(mat);
            rootNode.attachChild(geom);

            arrow = new Arrow(new Vector3f(0, 0, axisLength / 2));
            arrow.setLineWidth(lineWidth);
            mat = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.Green);
            geom = new Geometry("X Axis", arrow);
            geom.setMaterial(mat);
            rootNode.attachChild(geom);
        }
    }

    private void initPlayers(int numHumans, int numComputers)
    {
        players = new ArrayList<Player>();

        String name;
        Player tempPlayer;
        Vector3f[] startPos = level.getStartPositions();
        AssetManager assetManager = app.getAssetManager();

        //Temporary control mappings until a proper solution is found
        KeyTrigger[][] mappings = new KeyTrigger[][]
        {
            {
                new KeyTrigger(KeyInput.KEY_W),
                new KeyTrigger(KeyInput.KEY_S),
                new KeyTrigger(KeyInput.KEY_A),
                new KeyTrigger(KeyInput.KEY_D),
                new KeyTrigger(KeyInput.KEY_E),
                new KeyTrigger(KeyInput.KEY_Q)
            },
            {
                new KeyTrigger(KeyInput.KEY_I),
                new KeyTrigger(KeyInput.KEY_K),
                new KeyTrigger(KeyInput.KEY_J),
                new KeyTrigger(KeyInput.KEY_L),
                new KeyTrigger(KeyInput.KEY_U),
                new KeyTrigger(KeyInput.KEY_O)
            }
        };

        for (int i = 0; i < numHumans; i++)
        {
            name = "P" + (i + 1);
            tempPlayer = new Player(assetManager, name, playerMats[i], startPos[i], Vector3f.ZERO);
            app.getRootNode().attachChild(tempPlayer);

            //RigidBodyControl physPlayer = new RigidBodyControl(30);
            BetterCharacterControl physPlayer = new BetterCharacterControl(4, 10, 30);
            tempPlayer.addControl(physPlayer);
            bullet.getPhysicsSpace().add(physPlayer);
            //physPlayer.setKinematic(true);
            
            HumanPlayerControl playerControl = new HumanPlayerControl(this, mappings[i]);
            
            inputManager.addMapping(name + "Up", mappings[i][0]);
            inputManager.addMapping(name + "Down", mappings[i][1]);
            inputManager.addMapping(name + "Left", mappings[i][2]);
            inputManager.addMapping(name + "Right", mappings[i][3]);
            inputManager.addMapping(name + "PlaceBomb", mappings[i][4]);
            inputManager.addMapping(name + "PickUp", mappings[i][5]);
            inputManager.addListener(playerControl, name + "Up", name + "Down", 
                    name + "Left", name + "Right", name + "PlaceBomb", name + "PickUp");
            
            tempPlayer.addControl(playerControl);
            
            BitmapText hudText = tempPlayer.getHudText();
            hudText.setSize(hudText.getFont().getCharSet().getRenderedSize() * 5);
            if (i == 0) {
                hudText.setColor(ColorRGBA.Red);
                //can't figure out how to get AppSettings, so for the time being just gonna hardcode resolution
                hudText.setLocalTranslation(25, 768 - 25, 0);
            } else if (i == 1) {
                hudText.setColor(ColorRGBA.Blue);
                hudText.setLocalTranslation(1024 - 25 - hudText.getLineWidth(), 768 - 25, 0);
            } else if (i == 2) {
                hudText.setColor(ColorRGBA.Green);
                hudText.setLocalTranslation(25, 25 + hudText.getLineHeight(), 0);
            } else if (i == 3) {
                hudText.setColor(ColorRGBA.Yellow);
                hudText.setLocalTranslation(1024 - 25 - hudText.getLineWidth(), 25 + hudText.getLineHeight(), 0);
            }
            app.getGuiNode().attachChild(hudText);
            
            System.out.println(hudText.getText());
            System.out.println(hudText.getColor());
            System.out.println(hudText.getLocalTranslation());
            
            players.add(tempPlayer);
        }
    }

    public Main getApplication()
    {
        return app;
    }
    
    public BulletAppState getBulletAppState()
    {
        return bullet;
    }
    
    public BombFactory getBombFactory()
    {
        return bombFactory;
    }
    
    public ItemFactory getItemFactory()
    {
            return itemFactory;
    }

    @Override
    public void update(float tpf) {
        //update ubdatables
        for (Spatial s: app.getRootNode().getChildren()) {
            if (s instanceof IUpdatable) {
                ((IUpdatable)s).update(tpf);
            }
        }
        
        //check explosion collisions
        //they're spheres, and I don't want them to have physics, so this is ultimately easier than setting up collision shapes.
        //doesn't take into account player mesh shape however, but that's no biggie consider it an approximation
        for (Spatial s: app.getRootNode().getChildren()) {
            if (s instanceof Explosion) {
                Explosion explosion = (Explosion)s;
                for (Player p: players) {
                    float distance = explosion.getLocalTranslation().distance(p.getLocalTranslation());
                    if (distance <= explosion.getRadius()) {
                        //System.out.println(p.getName() + " is hit!");
                        p.takeDamage();
                    }
                    
                }
            }
        }
    }
}
