package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.LaunchActivity;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Packet envoyé au client en cas de time out serveur
 * args[0] type
 * args[1] message du timeout
 */

public class PacketReceiveTimeOut implements ActionInterface {
    @Override
    public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
        LaunchActivity.timeoutMessage = args[1];
        LaunchActivity.timeout = true;
    }
}
