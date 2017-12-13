package ked.pts3g10.Network;

import java.net.DatagramPacket;

public interface ActionInterface {

	public void onCall(String message, String[] args, PacketType action, DatagramPacket packet);
}
