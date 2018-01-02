package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Gameplay.CardPackage.BoardCard;
import ked.pts3g10.Gameplay.Player;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Envoyé par le serveur quand l'adversaire joue une carte
 * args[1] : numéro de la carte dans le deck
 * args[2] : position x où la carte est placée
 * args[3] : position y où la carte est placée
 */

public class PacketReceivePlayCard implements ActionInterface {
    @Override
    public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
        Player adversary = GameActivity.getBoard().getAdversary();
        Board board = GameActivity.getBoard();
        int cardIndex = Integer.parseInt(args[1]);
        BoardCard card = (BoardCard)adversary.getDeck().getCardList().get(cardIndex);
        int posx = Integer.parseInt(args[2]);
        int posy = Integer.parseInt(args[3]);
        adversary.getPlayerAction().setCaseToPlaceIt(board.getCaseWithLinearLayoutNumber(posx,posy));
        adversary.getPlayerAction().setToBePlaced(card);
        adversary.getPlayerAction().setPlaceBoardCardNext(true);
    }
}
