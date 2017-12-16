package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Packet envoyé au serveur quand une carte perd des hp
 */

public class PacketUpdateHP {

    static PacketType type = PacketType.UPDATEHP;

    public void call(int token, int new_hp, int x, int y) {
        //args[1] : int token
        //args[2] : int new_hp
        //args[3] : int LinearLayoutNumber -> X carte
        //args[4] : int CaseNumber -> Y carte

        ActivityMgr.connectionActivity.getCom().send(type.getId() + ":" + token+ ":" +new_hp+ ":" +x+ ":" +y);
    }
}
