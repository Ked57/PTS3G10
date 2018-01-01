package ked.pts3g10.Network.packet;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.PacketType;
import ked.pts3g10.Util.CoordinateConverter;
import ked.pts3g10.Util.Pos;

/**
 * Envoyé par le client au serveur quand une carte sort est utilisée
 * args[0] type
 * args[1] token
 * args[2] cardIndex
 * args[3] x
 * args[4] y
 */

public class PacketSendSpell {

    static PacketType type = PacketType.SENDSPELL;

    public void call(int token, int cardIndex, Pos p){
        Pos pos = CoordinateConverter.convert(p);
        ConnectionActivity.getCom().send(type.getId()+":"+token+":"+cardIndex+":"+pos.getPosX()+":"+pos.getPosY());
    }
}
