package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Serveur envoie au client la fin du round
 */

public class PacketReceiveEndGame implements ActionInterface{
    @Override
    public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
        // args[1] : int id raison déconnexion
    }
}
