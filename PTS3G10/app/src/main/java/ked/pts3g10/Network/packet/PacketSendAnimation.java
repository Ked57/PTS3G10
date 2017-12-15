package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Client envoie animation au serveur
 */

public class PacketSendAnimation implements ActionInterface {
    @Override
    public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
        //args[1] int : token
        //args[2] int : idAnimation
        //args[3] String[] argsAnimation -> les arguments de l'animation
    }
}

