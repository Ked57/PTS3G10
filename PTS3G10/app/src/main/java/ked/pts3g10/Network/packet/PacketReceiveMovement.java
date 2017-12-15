package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Reception des infos d'un mouvement de carte du server
 */

public class PacketReceiveMovement implements ActionInterface {
    @Override
    public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
        //args[1] : int LinearLayoutNumber -> X actuel
        //args[2] : int CaseNumber -> Y actuel
        //args[3] : int LinearLayoutNumber -> X nouveau
        //args[4] : int CaseNumber -> Y nouveau
    }
}
