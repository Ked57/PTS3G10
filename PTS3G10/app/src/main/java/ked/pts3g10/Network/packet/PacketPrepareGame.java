package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

public class PacketPrepareGame implements ActionInterface {

	@Override
	public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
		//Event quand serveur a trouv� un partenaire
	}
}