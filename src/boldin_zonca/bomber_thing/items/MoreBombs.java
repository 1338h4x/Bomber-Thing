
package boldin_zonca.bomber_thing.items;

/**
 *
 * @author dan
 */
public class MoreBombs extends AbstractEffect
{
    @Override
    protected void addEffect()
    {
        player.setBombCount(player.getBombCount() + 1);
    }

    @Override
    public void removeEffect(){}    
}
