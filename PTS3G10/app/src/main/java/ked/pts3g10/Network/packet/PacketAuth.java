package ked.pts3g10.Network.packet;


import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Network.PacketType;

public class PacketAuth {
	
	public void call(String name, String password) {
		ConnectionActivity.getCom().send(PacketType.AUTH.getId() + ConnectionActivity.getSeparator() + name + ConnectionActivity.getSeparator() + password);
	}
}
