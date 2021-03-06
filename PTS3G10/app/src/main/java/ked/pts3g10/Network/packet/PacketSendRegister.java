package ked.pts3g10.Network.packet;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.PacketType;

/**
 * Le client envoi sa demande d'inscription au serveur
 * args[0] type
 * args[1] pseudo
 * args[2] email
 * args[3] password
 */

public class PacketSendRegister {

    public void call(String pseudo, String email, String password){
        ConnectionActivity.getCom().send(PacketType.SENDREGISTER.getId()+":"+pseudo+":"+email+":"+password);
    }
}
