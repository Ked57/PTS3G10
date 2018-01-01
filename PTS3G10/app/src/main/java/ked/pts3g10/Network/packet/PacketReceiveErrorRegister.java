package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.InscriptionActivity;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Packet reçu quand l'inscription a échoué
 * args[0] type
 * args[1] message d'erreur
 */

public class PacketReceiveErrorRegister implements ActionInterface {
    @Override
    public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
        InscriptionActivity.registerMessage = args[1];
    }
}
