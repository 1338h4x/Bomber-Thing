
package boldin_zonca.bomber_thing.items;

/**
 *
 * @author dan
 */
public class BiggerBombs extends AbstractEffect
{
    @Override
    protected void addEffect()
    {
        player.setBombRadius(player.getBombRadius() + 1);
    }

    @Override
    protected void removeEffect(){}
}
