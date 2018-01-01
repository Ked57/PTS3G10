package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.InscriptionActivity;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Packet reçu quand l'inscription est un succes
 * args[0] type
 * args[1] token
 */

public class PacketReceiveSuccessRegister implements ActionInterface {
    @Override
    public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
        ConnectionActivity.token = Integer.parseInt(args[1]);
        InscriptionActivity.registered = true;
    }
}
