package ked.pts3g10.Network.packet;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.PacketType;

/**
 * Le client envoi sa déconnexion au serveur
 * args[0] type
 * args[1] token
 */

public class PacketSendLogOut {

    public void call(int token){
        ConnectionActivity.getCom().send(PacketType.SENDLOGOUT.getId()+":"+token);
    }
}
