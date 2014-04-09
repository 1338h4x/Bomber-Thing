package boldin_zonca.bomber_thing.items;

import com.jme3.asset.AssetManager;

/**
 *
 * @author dan
 */
public class ItemFactory
{
    public enum Type {BIGGER_BOMBS, 
                      MORE_BOMBS, 
                      BOUNCIER_BOMBS, 
                      REMOTE_BOMBS, 
                      HEART, 
                      VIRUS,
                      GLOVES,
                      BOOTS};
    
    private final AssetManager assetManager;
    
    public ItemFactory(AssetManager assetManager)
    {
        this.assetManager = assetManager;
    }
    
    //TODO getItem methods
    public Item getItemOfType(Type type)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Item getItem()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
