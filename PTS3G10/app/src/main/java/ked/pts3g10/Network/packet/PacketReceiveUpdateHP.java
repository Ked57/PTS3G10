package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;
import ked.pts3g10.Util.Pos;

/**
 * Serveur envoie un update des health point au client
 */

public class PacketReceiveUpdateHP implements ActionInterface {
    @Override
    public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
        //args[1] : int new_hp
        //args[2] : int LinearLayoutNumber -> X carte
        //args[3] : int CaseNumber -> Y carte

        Board board = ActivityMgr.gameActivity.getBoard();
        int new_hp = Integer.parseInt(args[1]);
        Pos pos = new Pos(Integer.parseInt(args[2]),Integer.parseInt(args[3]));
        Case c = board.getCaseWithLinearLayoutNumber(pos.getPosX(),pos.getPosY());

        if(new_hp <= 0){
            c.resetCard();
        }else {
            c.getCard().setHealthPoints(new_hp);
            c.updateHp(new_hp);
        }
    }
}
