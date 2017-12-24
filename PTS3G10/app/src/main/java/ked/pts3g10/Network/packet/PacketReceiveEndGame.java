package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Serveur envoie au client la fin de la partie
 */

public class PacketReceiveEndGame {

    public void call(int reason) {
        // args[1] : String raison déconnexion
        //ActivityMgr.gameActivity.endGame();
    }
}
