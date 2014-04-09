package boldin_zonca.bomber_thing;

import boldin_zonca.bomber_thing.player.Player;

/**
 *
 * @author dan
 */
public interface IDestructable
{
    void onHit(Player assailant);
}
