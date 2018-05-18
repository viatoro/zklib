package com.moomanow;

import static com.moomanow.ZkConst.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Zk {

	private SocketAddress address;
	private String port;
	private DatagramSocket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private int session_id = 0;
	private int reply_id = -1 + USHRT_MAX;

	public Zk(String ip, String PortNumber) {
		this.port = PortNumber;
		address = new InetSocketAddress(ip, Integer.parseInt(port));
	}

	// public Zk() {
	// // TODO Auto-generated constructor stub
	// }

	public <T> T executeCmd(int command,String command_string,ZkCallBack<T> zkCallBack) throws IOException {
//		int command = CMD_CONNECT;
//		String command_string = "";

		byte[] buf = createHeader(command, 0, session_id, reply_id, command_string);
//		out.println(buf);
		clientSocket = new DatagramSocket();

//		clientSocket.bind(address );
		DatagramPacket sendDataPack = new DatagramPacket(buf, buf.length, address);
		
		clientSocket.send(sendDataPack);
		byte[] bufRev = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(bufRev, bufRev.length);
		clientSocket.receive(receivePacket);
		String received = new String(receivePacket.getData(), 0, receivePacket.getLength());

		System.out.println(received);
		ByteBuffer buffer = ByteBuffer.allocate(receivePacket.getLength()).order(ByteOrder.LITTLE_ENDIAN).put(receivePacket.getData(),0,receivePacket.getLength());
		session_id = Short.toUnsignedInt(buffer.getShort(4));
		reply_id = Short.toUnsignedInt(buffer.getShort(6));
		System.out.println("session_id :"+ session_id);
		System.out.println("reply_id :"+ reply_id);
		checkValid(buffer);
//		packet = new DatagramPacket(buf, buf.length);
//		        socket.receive(packet);
//		        String received = new String(
//		          packet.getData(), 0, packet.getLength());
//		String resp = in.readLine();
		return zkCallBack.callBack(checkValid(buffer),buffer);
	}
	
	public byte[] executeCmd(int command,String command_string) throws IOException {
		return executeCmd(command, command_string,(status,data)->{
			return data.array();
		});
	}

	private Boolean checkValid (ByteBuffer buffer) {
		 int command = Short.toUnsignedInt(buffer.getShort(0));
		 return command == CMD_ACK_OK;
	}

	private int createChkSum(ByteBuffer p) {

		int chksum = 0;

		for (int i = 0; i < p.remaining(); i += 2) {
			if (i == p.remaining() - 1) {
				chksum += p.getShort(i);
			} else {
				chksum += p.getShort(i);
			}
			chksum %= USHRT_MAX;
		}

		chksum = USHRT_MAX - chksum - 2;

		return chksum;
	}

	public byte[] createHeader(int command, int chksum, int session_id, int reply_id, String command_string) {
		// command_string
		// ByteBuffer

		byte[] bytes_command_string = command_string.getBytes();

		ByteBuffer byteBuffer = ByteBuffer.allocate(8 + bytes_command_string.length).order(ByteOrder.LITTLE_ENDIAN)
				.putChar(0, (char) command).putChar(2, (char) chksum).putChar(4, (char) session_id)
				.putChar(6, (char) reply_id).put(bytes_command_string);

		byte[] bytes = byteBuffer.array();
		for (byte b : bytes) {
			System.out.format("0x%x ", b);
		}
		byteBuffer = byteBuffer.putChar(2, (char) createChkSum(byteBuffer));
		System.out.println();
		bytes = byteBuffer.array();
		for (byte b : bytes) {
			System.out.format("0x%x ", b);
		}
		reply_id = (reply_id + 1) % USHRT_MAX;
		byteBuffer = byteBuffer.putChar(6, (char) reply_id);
		System.out.println();
		bytes = byteBuffer.array();
		for (byte b : bytes) {
			System.out.format("0x%x ", b);
		}
		return bytes;
	}

}
