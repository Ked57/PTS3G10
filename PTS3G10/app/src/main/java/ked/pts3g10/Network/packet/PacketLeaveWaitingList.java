package ked.pts3g10.Network.packet;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.PacketType;

/**
 * Envoyé par le client au serveur quand il quitte la recherche de partie
 * args[0] type
 * args[1] token
 */

public class PacketLeaveWaitingList {

    static PacketType type = PacketType.SENDLEAVEWAITINGLIST;

    public void call(int token){
        ConnectionActivity.getCom().send(type.getId()+":"+token);
    }
}
