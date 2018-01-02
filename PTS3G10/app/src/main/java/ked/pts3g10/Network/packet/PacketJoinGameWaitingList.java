package ked.pts3g10.Network.packet;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.PacketType;

public class PacketJoinGameWaitingList {

	static PacketType type = PacketType.WAITINGGAME;

	public void call() {
		int token = ConnectionActivity.token;
		ConnectionActivity.getCom().send(type.getId() + ":" + token);
	}
}