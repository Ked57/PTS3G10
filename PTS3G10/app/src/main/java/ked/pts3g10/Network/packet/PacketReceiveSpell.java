package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Gameplay.CardPackage.Spell;
import ked.pts3g10.Gameplay.Player;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

/**
 * Envoyé par le serveur quand l'adversaire utilise une carte spell
 * args[0] type
 * args[1] cardIndex
 * args[2] x
 * args[3] y
 */

public class PacketReceiveSpell implements ActionInterface {
    @Override
    public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
        Board board = GameActivity.getBoard();
        Player adversary = board.getAdversary();
        Spell s = (Spell)adversary.getDeck().getCardList().get(Integer.parseInt(args[1]));
        Case c = board.getCaseWithLinearLayoutNumber(Integer.parseInt(args[2]),Integer.parseInt(args[3]));
        adversary.getPlayerAction().setCaseCard(s);
        adversary.getPlayerAction().setCaseToUseSpellOn(c);
        adversary.getPlayerAction().setUseSpell(true);
    }
}
