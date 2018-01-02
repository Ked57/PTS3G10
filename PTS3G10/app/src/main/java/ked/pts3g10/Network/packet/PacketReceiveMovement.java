package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Gameplay.PlayerAction;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;
import ked.pts3g10.Util.Pos;

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

        Board board = GameActivity.getBoard();
        PlayerAction adversaryAction = board.getAdversary().getPlayerAction();
        Pos basePos = new Pos(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
        Pos newPos = new Pos(Integer.parseInt(args[3]),Integer.parseInt(args[4]));

        Case base = board.getCaseWithLinearLayoutNumber(basePos.getPosX(),basePos.getPosY());
        Case new_case = board.getCaseWithLinearLayoutNumber(newPos.getPosX(),newPos.getPosY());

        adversaryAction.setCaseItComesFrom(base);
        adversaryAction.setCaseItsGoingTo(new_case);
        adversaryAction.setMoveBoardCardNext(true);
    }
}
