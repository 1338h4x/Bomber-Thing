package Boldin_Zonca.BomberThing;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
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
import java.util.ArrayList;

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
    
    
    private Material testMat;
    private ArrayList<Player> players;
        
    @Override
    public void initialize(AppStateManager asm, Application anApp)
    {
        app = (Main) anApp;
        stateManager = asm;
        bullet = new BulletAppState();
        stateManager.attach(bullet);
        inputManager = anApp.getInputManager();
        
        initMaterials();
        initTestLevel();
        showDebugInfo(Main.DEBUG);
        initLights();
        initCam();
        initPlayers();
    }
    
    private void initTestLevel()
    {
        Node testLevel = (Node) app.getAssetManager().loadModel("/Models/testLevel/testLevel.mesh.j3o");        
        RigidBodyControl physTestLevel = new RigidBodyControl(0);
        testLevel.addControl(physTestLevel);
        app.getRootNode().attachChild(testLevel);
        bullet.getPhysicsSpace().add(physTestLevel);
    }
    
    private void initCam()
    {
        FlyByCamera flyCam = app.getFlyByCamera();
        /*if (Main.DEBUG)
        {
            flyCam.setEnabled(true);
            flyCam.setMoveSpeed(20f);
        }
        else
        {
            flyCam.setEnabled(false);
        }*/
        flyCam.setEnabled(false);
        
        app.getCamera().setLocation(new Vector3f(0, 160f, 100f));
        app.getCamera().lookAt(new Vector3f(0, 0, 10), Vector3f.UNIT_Y);
    }
    
    private void initMaterials()
    {
        testMat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        testMat.setBoolean("UseMaterialColors", true);
        testMat.setColor("Ambient", new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
        testMat.setColor("Diffuse", ColorRGBA.Red);
    }
    
    private void initLights()
    {
        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        app.getRootNode().addLight(ambient);
        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.3f, -0.4f, -0.5f)).normalizeLocal());
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
        if(show)
        {            
            int lineWidth = 5;
            float axisLength = 130;
            Geometry geom;
            Arrow arrow;
            Material mat;
            Node rootNode = app.getRootNode();
            AssetManager assMan = app.getAssetManager();
            
            //Grid
            Grid grid = new Grid(((int)(axisLength)/10)+1, ((int)(axisLength)/10)+1, 10f);
            grid.setLineWidth(lineWidth/2f);
            geom = new Geometry("Grid", grid);
            mat = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.White);
            geom.setMaterial(mat);
            geom.center().move(Vector3f.ZERO);
            rootNode.attachChild(geom);
            
            //Axis            
            arrow = new Arrow(new Vector3f(axisLength, 0, 0));
            arrow.setLineWidth(lineWidth);
            mat = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.Blue);
            geom = new Geometry("X Axis", arrow);
            geom.setMaterial(mat);
            rootNode.attachChild(geom);
            
            arrow = new Arrow(new Vector3f(0, axisLength, 0));
            arrow.setLineWidth(lineWidth);
            mat = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.Red);
            geom = new Geometry("X Axis", arrow);
            geom.setMaterial(mat);
            rootNode.attachChild(geom);
            
            arrow = new Arrow(new Vector3f(0, 0, axisLength));
            arrow.setLineWidth(lineWidth);
            mat = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.Green);
            geom = new Geometry("X Axis", arrow);
            geom.setMaterial(mat);
            rootNode.attachChild(geom);
        }
    }
    
    private void initPlayers() { //may want to add vars for num of human/com players?
        players = new ArrayList<Player>();
        
        Player p1 = new Player("One", testMat, new Vector3f(-50, 0, 0), Vector3f.UNIT_X);
        BetterCharacterControl physPlayer1 = new BetterCharacterControl(p1.getRadius(), p1.getHeight(), 30);
        p1.addControl(physPlayer1);
        app.getRootNode().attachChild(p1);
        bullet.getPhysicsSpace().add(physPlayer1);
        players.add(p1);
        
        String p1Name = "P1";
        PlayerInputControl p1Input = new PlayerInputControl(this, p1, physPlayer1, p1Name);
        inputManager.addMapping(p1Name + "Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(p1Name + "Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping(p1Name + "Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(p1Name + "Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(p1Input, p1Name + "Up", p1Name + "Down", p1Name + "Left", p1Name + "Right");
        
        Player p2 = new Player("One", testMat, new Vector3f(50, 0, 0), Vector3f.UNIT_X);
        BetterCharacterControl physPlayer2 = new BetterCharacterControl(p2.getRadius(), p2.getHeight(), 30);
        p2.addControl(physPlayer2);
        app.getRootNode().attachChild(p2);
        bullet.getPhysicsSpace().add(physPlayer2);
        players.add(p2);
        
        String p2Name = "P2";
        PlayerInputControl p2Input = new PlayerInputControl(this, p2, physPlayer2, p2Name);
        inputManager.addMapping(p2Name + "Up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping(p2Name + "Down", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping(p2Name + "Left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping(p2Name + "Right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addListener(p2Input, p2Name + "Up", p2Name + "Down", p2Name + "Left", p2Name + "Right");
    }
}