package com.moomanow;

import static com.moomanow.ZkConst.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.moomanow.model.User;

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
		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public <T> T executeCmd(int command, String command_string, ZkCallBack<T> zkCallBack) throws IOException {
		if (command == CMD_CONNECT) {
			this.reply_id = -1 + USHRT_MAX;
		}

		byte[] buf = createHeader(command, 0, session_id, reply_id, command_string);

		DatagramPacket sendDataPack = new DatagramPacket(buf, buf.length, address);

		clientSocket.send(sendDataPack);
		byte[] bufRev = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(bufRev, bufRev.length);
		clientSocket.receive(receivePacket);
		String received = new String(receivePacket.getData(), 0, receivePacket.getLength());
		for (byte b : receivePacket.getData()) {
			System.out.format("0x%x ", b);
		}
		System.out.println(received);
		ByteBuffer buffer = ByteBuffer.allocate(receivePacket.getLength()).order(ByteOrder.LITTLE_ENDIAN)
				.put(receivePacket.getData(), 0, receivePacket.getLength());
		session_id = Short.toUnsignedInt(buffer.getShort(4));
		reply_id = Short.toUnsignedInt(buffer.getShort(6));
		System.out.println("session_id :" + session_id);
		System.out.println("reply_id :" + reply_id);
		int comBack = checkValid(buffer);
		if (CMD_ACK_OK == comBack) {
			return zkCallBack.callBack(true, buffer);
		} else if (CMD_PREPARE_DATA == comBack) {
			long size = Integer.toUnsignedLong(buffer.getInt(8));
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			for (int i = 0; i < size; i+=1024) {
				byte[] bufRevApp = new byte[1032];
				DatagramPacket receivePacketApp = new DatagramPacket(bufRevApp, bufRevApp.length);
				clientSocket.receive(receivePacketApp);
//				for (byte b : receivePacket.getData()) {
//					System.out.format("0x%x ", b);
//				}
				
				byteArrayOutputStream.write(receivePacketApp.getData(),8,receivePacketApp.getLength()-8);
			}
			byte[] bufRevAppOut  = byteArrayOutputStream.toByteArray();
			return zkCallBack.callBack(true, ByteBuffer.allocate(bufRevAppOut.length).order(ByteOrder.LITTLE_ENDIAN).put(bufRevAppOut, 0, bufRevAppOut.length));
		} else
			return zkCallBack.callBack(false, buffer);
	}

	// private Map<Integer, ZkCall> map = new HashMap<>();
	// public void executeCmd(int command,String command_string,ZkCall zkCallBack)
	// throws IOException {
	//
	// byte[] buf = createHeader(command, 0, session_id, reply_id, command_string);
	//
	//
	// DatagramPacket sendDataPack = new DatagramPacket(buf, buf.length, address);
	//
	// clientSocket.send(sendDataPack);
	// map.put(reply_id, zkCallBack);
	// }

	// class ThreadReceived extends Thread {
	//
	// @Override
	// public void run() {
	// System.out.println("Thread is running...");
	// while(true) {
	// try {
	// byte[] bufRev = new byte[1024];
	// DatagramPacket receivePacket = new DatagramPacket(bufRev, bufRev.length);
	// clientSocket.receive(receivePacket);
	// String received = new String(receivePacket.getData(), 0,
	// receivePacket.getLength());
	//
	// System.out.println(received);
	// ByteBuffer buffer =
	// ByteBuffer.allocate(receivePacket.getLength()).order(ByteOrder.LITTLE_ENDIAN).put(receivePacket.getData(),0,receivePacket.getLength());
	// int session_id = Short.toUnsignedInt(buffer.getShort(4));
	// int reply_id = Short.toUnsignedInt(buffer.getShort(6));
	// System.out.println("session_id :"+ session_id);
	// System.out.println("reply_id :"+ reply_id);
	// ZkCall zkCallBack = map.get(reply_id);
	// if(zkCallBack==null)
	// continue;
	// zkCallBack.callBack(checkValid(buffer),buffer);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// }

	public byte[] executeCmd(int command, String command_string) throws IOException {
		return executeCmd(command, command_string, (status, data) -> {
			return data.array();
		});
	}

	public boolean connect() throws IOException {
		return executeCmd(CMD_CONNECT, "", (status, data) -> {
			return status;
		});
	}

	public boolean disconnect() throws IOException {
		return executeCmd(CMD_EXIT, "", (status, data) -> {
			return status;
		});
	}

	public String version() throws IOException {
		return executeCmd(CMD_VERSION, "", (status, data) -> {
			byte[] dataByte = data.array();
			ByteBuffer dataout = ByteBuffer.allocate(dataByte.length).put(dataByte, 8, dataByte.length - 8);
			if (status)
				return new String(dataout.array());
			else
				return "fail";
		});
	}

	public String platformVersion() throws IOException {
		return executeCmd(CMD_DEVICE, "~ZKFPVersion", (status, data) -> {
			byte[] dataByte = data.array();
			ByteBuffer dataout = ByteBuffer.allocate(dataByte.length).put(dataByte, 8, dataByte.length - 8);
			if (status)
				return new String(dataout.array());
			else
				return "fail";
		});
	}

	// private String getSizeUser() throws IOException {
	// return executeCmd(CMD_DEVICE, "",(status ,data)-> {
	//// TODO:version
	// return null;
	// });
	// }
	private final int userdata_size = 72;

	public List<User> getUser() throws IOException {
		byte[] bytes = new byte[] { (byte) 0x05 };
		
		return executeCmd(CMD_USERTEMP_RRQ, new String(bytes), (status, data) -> {
			
			System.out.println();
			for (byte b : data.array()) {
				System.out.format("0x%x ", b);
			}
			List<User> users = new ArrayList<>();
			int offset = 3;
			byte[] dataUserByte = data.array();
			int length = dataUserByte.length;
			
			while(offset<length) {
				ByteBuffer userByteBuffer  = ByteBuffer.allocate(userdata_size).put(data.array(), offset, userdata_size);
				System.out.println();
				System.out.println(new String(userByteBuffer.array()));
				User user = User.encodeUser(userByteBuffer);
				System.out.println(user);
				for (byte b : userByteBuffer.array()) {
					System.out.format("0x%X ", b);
				}
				
				offset = offset+userdata_size;
				users.add(user);
			}
			System.out.println(users);
			return users;
		});

		// return null;
	}

	public String delUser() throws IOException {
		return executeCmd(CMD_DEVICE, "", (status, data) -> {
			// TODO:version
			return null;
		});
	}

	public String setUser(int uid, String password, String name, int userId) throws IOException {
		return executeCmd(CMD_DEVICE, "", (status, data) -> {
			// TODO:version
			return null;
		});
	}

	private int checkValid(ByteBuffer buffer) {
		int command = Short.toUnsignedInt(buffer.getShort(0));
		return command;
	}

	private int createChkSum(ByteBuffer p) {

		int chksum = 0;

		// int size = p.remaining();
		// while (size>1) {
		// p.getChar()
		// chksum += unpack('H', pack('BB', p[0], p[1]))[0]
		// type type = (type) it.next();
		// size -= 2;
		// }
		//
		// while (chksum > USHRT_MAX) {
		// chksum -= USHRT_MAX;
		// }
		// chksum = ~chksum;
		// while (chksum < 0) {
		// chksum += USHRT_MAX;
		// }

		// if(size==1) {
		// chksum = chksum + p[-1];
		// }
		for (int i = 0; i < p.remaining(); i += 2) {
			if (i == p.remaining() - 1) {
				chksum += p.get(i);
			} else {
				chksum += p.getChar(i);
			}
			chksum %= USHRT_MAX;
		}

		chksum = USHRT_MAX - chksum - 1;

		return chksum;
	}

	public byte[] createHeader(int command, int chksum, int session_id, int reply_id, String command_string) {
		// command_string
		// ByteBuffer

		byte[] bytes_command_string = command_string.getBytes();

		ByteBuffer byteBuffer = ByteBuffer.allocate(8 + bytes_command_string.length).order(ByteOrder.LITTLE_ENDIAN)
				.putChar(0, (char) command).putChar(2, (char) chksum).putChar(4, (char) session_id)
				.putChar(6, (char) reply_id);
		for (int i = 0; i < bytes_command_string.length; i++) {
			byte b = bytes_command_string[i];
			byteBuffer = byteBuffer.put(i + 8, b);
		}

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
