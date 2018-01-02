package ked.pts3g10.Network.packet;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.PacketType;
import ked.pts3g10.Util.CoordinateConverter;
import ked.pts3g10.Util.Pos;

/**
 * Packet envoyé au serveur quand une carte perd des hp
 */

public class PacketUpdateHP {

    public void call(int token, int new_hp, Pos pos) {
        //args[1] : int token
        //args[2] : int new_hp
        //args[3] : int LinearLayoutNumber -> X carte
        //args[4] : int CaseNumber -> Y carte

        Pos p = CoordinateConverter.convert(pos);

        ConnectionActivity.getCom().send(PacketType.UPDATEHP.getId() + ":" + token+ ":" +new_hp+ ":" +p.getPosX()+ ":" +p.getPosY());
    }
}
