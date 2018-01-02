package ked.pts3g10.Network.packet;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.PacketType;

/**
 * Packet envoyé en cas de trigger du bouton fin du tour
 */

public class PacketEndRound {

    public void call(int token) {
        // A envoyer arg[0] : id packet
        //           arg[1] : token client

        ConnectionActivity.getCom().send(PacketType.ENDROUND.getId() + ":" + token);
    }
}
