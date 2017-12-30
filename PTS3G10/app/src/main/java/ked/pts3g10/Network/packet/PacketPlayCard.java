package ked.pts3g10.Network.packet;


import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.PacketType;
import ked.pts3g10.Util.CoordinateConverter;
import ked.pts3g10.Util.Pos;

/**
 * Appelé par le client quand il joue une carte
 * args[0] : packet type
 * args[1] : Token
 * args[2] : numéro de la carte dans le deck
 * args[3] : position x où la carte est placée
 * args[4] : position y où la carte est placée
 */

public class PacketPlayCard {

    static PacketType type = PacketType.PLAYCARD;

    public void call(int token, int cardIndex, Pos p){
        Pos pos = CoordinateConverter.convert(p);
        ConnectionActivity.getCom().send(type.getId()+":"+token+":"+cardIndex+":"+pos.getPosX()+":"+pos.getPosY());
    }
}
