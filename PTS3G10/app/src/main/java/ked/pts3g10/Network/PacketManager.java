package ked.pts3g10.Network;

import android.util.Log;

import java.net.DatagramPacket;

import ked.pts3g10.Network.packet.PacketSuccessAuth;


public class PacketManager {

	private DatagramPacket packet;
	
	public PacketManager(DatagramPacket receivePacket) {
		packet = receivePacket;
		start();
	}

	public void start() {
		String message = new String(packet.getData(), 0, packet.getLength());
		String[] args = message.split(":");
		System.out.println(message);
		PacketType action;
		try {
			action = PacketType.getById(Integer.parseInt(args[0]));
		} catch(Exception ex) {
			Log.e("Network",""+ex.getMessage());
			return; }
		ActionInterface packetI = null;
		switch(action) {
			case SUCCESSAUTH: 
				packetI = new PacketSuccessAuth();
				break;
			case ERRORAUTH: 
				break;
			default: 
				break;
		}
		if(packetI != null && args.length == action.getParamLength()) packetI.onCall(message, args, action, packet);
	}
}