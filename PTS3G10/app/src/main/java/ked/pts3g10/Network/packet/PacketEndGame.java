package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Packet envoyé par le client pour finir la partie
 */

public class PacketEndGame{

    public void call(String message) {
        // args[1] : token client
        // args[2] : message raison de déconnexion
        ConnectionActivity.getCom().send(PacketType.ENDGAME.getId()+":"+ConnectionActivity.token+":"+message);
    }
}
