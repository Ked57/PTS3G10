package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Packet envoyé quand le joueur bouge une carte
 */

public class PacketSendMovement  {

    static PacketType type = PacketType.SENDMOVEMENT;

    public void call(int token, int base_x, int base_y, int new_x, int new_y) {
        //args[1] : int token
        //args[2] : int LinearLayoutNumber -> X actuel
        //args[3] : int CaseNumber -> Y actuel
        //args[4] : int LinearLayoutNumber -> X nouveau
        //args[5] : int CaseNumber -> Y nouveau

        ActivityMgr.connectionActivity.getCom().send(type.getId() + ":" + token+ ":" +base_x+ ":" +base_y+ ":" +new_x+ ":" +new_y);

    }
}
