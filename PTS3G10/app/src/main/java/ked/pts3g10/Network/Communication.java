package ked.pts3g10.Network;

import android.os.AsyncTask;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Communication extends AsyncTask {

	private Integer serverPort;
	private InetAddress serverIp;
	private Integer port;
	private DatagramSocket clientSocket;
	private DatagramPacket receivePacket;
	private byte[] buffer;

	public Communication() {
		buffer = new byte[2048];
		//port = 20000 + new Random().nextInt(10);
		port = 20000;
		receivePacket = new DatagramPacket(buffer, buffer.length);
		serverPort = 25565;
		try { serverIp = InetAddress.getByName("149.91.80.135");//127.0.0.1
		} catch (Exception e) {
			Log.e("Network",Log.getStackTraceString(e));}
	}

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            clientSocket = new DatagramSocket(port);
            Log.i("Network","Init listening on port " + port);
            send("-1");
            while (true) {
                clientSocket.receive(receivePacket);
                new PacketManager(receivePacket);
                receivePacket.setLength(buffer.length);
            }
        } catch (Exception ex) {
            Log.e("Network",Log.getStackTraceString(ex));
        }
        Log.i("Network","gone out of the while");
        return null;
    }

    public DatagramSocket getSrvSocket() {
		return clientSocket;
	}

	public synchronized void send(String message) {
		try {
			Log.i("Network","Trying to send " + message);
			byte[] dataToSend = message.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(dataToSend, dataToSend.length, serverIp, serverPort);
			clientSocket.send(sendPacket);
			Log.i("Network","Message sent");
		} catch(Exception ex) { Log.e("Network",Log.getStackTraceString(ex)); }
	}
}