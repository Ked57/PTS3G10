package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Packet envoyé en cas de trigger du bouton fin du tour
 */

public class PacketEndRound {

    static PacketType type = PacketType.ENDROUND;

    public void call(int token) {
        // A envoyer arg[0] : id packet
        //           arg[1] : token client

        ActivityMgr.connectionActivity.getCom().send(type.getId() + ":" + token);
    }
}
