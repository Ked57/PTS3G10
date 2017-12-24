package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

// Le server envoi ce packet au cliet dont ce n'est pas le tour

public class PacketNextRound implements ActionInterface {

    @Override
    public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
        //Passage ou tour suivant
        //A utiliser seulement en cas de fin prématurée du round
        
        ActivityMgr.gameActivity.getBoard().newRound();
    }
}
