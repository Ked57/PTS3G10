package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;


public class PacketSuccessAuth implements ActionInterface {

	@Override
	public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
		//Appel en cas d'authentification r�ussie
		//args[0] : INTEGER : id du packet
		//args[1] : INTEGER : token d'identification du client aupr�s du serveur

		int token = Integer.parseInt(args[1]);

		ConnectionActivity.connectionCallBack(token, null);
				
	}
}