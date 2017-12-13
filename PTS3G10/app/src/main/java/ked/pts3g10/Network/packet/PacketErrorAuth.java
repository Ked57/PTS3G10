package ked.pts3g10.Network.packet;

import android.util.Log;

import java.net.DatagramPacket;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;


public class PacketErrorAuth implements ActionInterface {

	@Override
	public void onCall(String message, String[] args, PacketType action, DatagramPacket packet) {
		//Appel en cas d'erreur d'authentification
		//args[0] : INTEGER : id du packet
		Log.i("Network","Received PacketErrorAuth callback");
		ActivityMgr.connectionActivity.connectionCallBack(-1);
	}
}