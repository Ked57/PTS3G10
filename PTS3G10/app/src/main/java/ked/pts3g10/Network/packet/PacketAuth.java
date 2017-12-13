package ked.pts3g10.Network.packet;


import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.PacketType;

public class PacketAuth {
	
	static PacketType type = PacketType.AUTH;
	
	public void call(String name, String password) {
		ConnectionActivity.getCom().send(type.getId() + ConnectionActivity.getSeparator() + name + ConnectionActivity.getSeparator() + password);
	}
}
