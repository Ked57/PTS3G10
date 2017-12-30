package ked.pts3g10.Network.packet;


import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.PacketType;

/**
 * Appelé par le client quand il joue une carte
 * args[1] : Token
 * args[2] : numéro de la carte dans le deck
 * args[3] : position x où la carte est placée
 * args[4] : position y où la carte est placée
 */

public class PacketPlayCard {

    static PacketType type = PacketType.WAITINGGAME;

    public void call(int token, int cardIndex, int posx, int posy){
        ConnectionActivity.getCom().send(token+":"+cardIndex+":"+posx+":"+posy);
    }
}
