package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.LaunchActivity;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

public class PacketPrepareGame implements ActionInterface {

	@Override
	public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
		//Event quand serveur a trouv� un partenaire
		//args[1] : string pseudo adversaire
		//args[2] : token adversaire
        //args[3] : boolean commence ou pas

		LaunchActivity.notifyStartGame(args[1],Integer.parseInt(args[2]),args[3]);
	}
}