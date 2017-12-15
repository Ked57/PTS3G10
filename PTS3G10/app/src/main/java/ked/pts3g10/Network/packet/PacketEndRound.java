package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Packet envoyé en cas de trigger du bouton fin du tour
 */

public class PacketEndRound implements ActionInterface {
    @Override
    public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
        // A envoyer arg[0] : id packet
        //           arg[1] : token client

        int token = ActivityMgr.connectionActivity.token;
        ActivityMgr.connectionActivity.getCom().send(action.getId() + ":" + token);
    }
}
