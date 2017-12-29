package ked.pts3g10.Network.packet;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.Network.PacketType;

public class PacketJoinGameWaitingList {

	static PacketType type = PacketType.AUTH;

	public void call() {
		int token = ActivityMgr.connectionActivity.token;
		ActivityMgr.connectionActivity.getCom().send(type.getId() + ":" + token);
	}
}