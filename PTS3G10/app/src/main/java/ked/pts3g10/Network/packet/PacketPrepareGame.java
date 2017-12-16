package ked.pts3g10.Network.packet;

import android.content.Intent;

import java.net.DatagramPacket;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.ChargementActivity;
import ked.pts3g10.LaunchActivity;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

public class PacketPrepareGame implements ActionInterface {

	@Override
	public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
		//Event quand serveur a trouv� un partenaire
		//args[1] : string pseudo adversaire
        //args[2] : boolean commence ou pas

        boolean starting;
        if(args[2].equals("true"))
            starting = true;
        else starting = false;

        ActivityMgr.launchActivity.newGame(args[1], starting);
	}
}