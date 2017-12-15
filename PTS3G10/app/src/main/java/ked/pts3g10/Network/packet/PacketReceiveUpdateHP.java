package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Serveur envoie un update des health point au client
 */

public class PacketReceiveUpdateHP implements ActionInterface {
    @Override
    public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
        //args[1] : int new_hp
        //args[2] : int LinearLayoutNumber -> X carte
        //args[3] : int CaseNumber -> Y carte
    }
}
