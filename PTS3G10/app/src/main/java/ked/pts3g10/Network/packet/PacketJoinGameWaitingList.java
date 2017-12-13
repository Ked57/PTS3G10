package ked.pts3g10.Network.packet;

import java.net.DatagramPacket;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.Network.ActionInterface;
import ked.pts3g10.Network.PacketType;

public class PacketJoinGameWaitingList implements ActionInterface {

	@Override
	public void onCall(String message, String[] args, PacketType type, DatagramPacket packet) {
		int token = ActivityMgr.connectionActivity.token;
		ActivityMgr.connectionActivity.getCom().send(type.getId() + ":" + token);
	}
}