package ked.pts3g10.Network.packet;


import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.PacketType;
import ked.pts3g10.Util.CoordinateConverter;
import ked.pts3g10.Util.Pos;

/**
 * Packet envoyé quand le joueur bouge une carte
 */

public class PacketSendMovement  {

    static PacketType type = PacketType.SENDMOVEMENT;

    public void call(int token, int base_x, int base_y, int new_x, int new_y) {
        //args[1] : int token
        //args[2] : int LinearLayoutNumber -> X actuel
        //args[3] : int CaseNumber -> Y actuel
        //args[4] : int LinearLayoutNumber -> X nouveau
        //args[5] : int CaseNumber -> Y nouveau

        Pos base = CoordinateConverter.convert(new Pos(base_x,base_y));
        Pos new_case = CoordinateConverter.convert(new Pos(new_x,new_y));

        ConnectionActivity.getCom().send(type.getId() + ":" + token+ ":" +base.getPosX()+ ":" +base.getPosY()+ ":" +new_case.getPosX()+ ":" +new_case.getPosY());

    }
}
