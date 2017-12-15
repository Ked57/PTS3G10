package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Packet envoyé quand le joueur bouge une carte
 */

public class PacketSendMovement implements ActionInterface {
    @Override
    public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
        //args[1] : int token
        //args[2] : int LinearLayoutNumber -> X actuel
        //args[3] : int CaseNumber -> Y actuel
        //args[4] : int LinearLayoutNumber -> X nouveau
        //args[5] : int CaseNumber -> Y nouveau
    }
}
