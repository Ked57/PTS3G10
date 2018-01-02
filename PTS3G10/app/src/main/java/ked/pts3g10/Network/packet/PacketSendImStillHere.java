package ked.pts3g10.Network.packet;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.PacketType;

/**
 * Packet envoyé au serveur a intervalle régulier pour montrer que le client est toujours présent
 */

public class PacketSendImStillHere {

    public void call(int token){
        ConnectionActivity.getCom().send(PacketType.IMSTILLHERE.getId()+":"+token);
    }
}
