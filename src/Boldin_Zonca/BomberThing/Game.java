package Boldin_Zonca.BomberThing;

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

/**
 *
 * @author David Boldin & Dan Zonca
 */
public class Game extends AbstractAppState
{
    private Main app;
    private AppStateManager stateManager;
    private BulletAppState bullet;
    
    
    private Material testMat;
    private Player testPlayer;
        
    @Override
    public void initialize(AppStateManager asm, Application anApp)
    {
        app = (Main) anApp;
        stateManager = asm;
        bullet = new BulletAppState();
        stateManager.attach(bullet);
        
        initMaterials();
        initTestLevel();
        showDebugInfo(Main.DEBUG);
        initLights();
        initCam();
        
        testPlayer = new Player("One", testMat, new Vector3f(0, 0, 0), Vector3f.UNIT_X);
        RigidBodyControl physPlayer = new RigidBodyControl(30);
        testPlayer.addControl(physPlayer);
        app.getRootNode().attachChild(testPlayer);
        bullet.getPhysicsSpace().add(physPlayer);
        
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
        if (Main.DEBUG)
        {
            flyCam.setEnabled(true);
            flyCam.setMoveSpeed(20f);
        }
        else
        {
            flyCam.setEnabled(false);
        }
        
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
}