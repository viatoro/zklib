package com.moomanow;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static com.moomanow.ZkConst.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import com.moomanow.model.Attendance;
import com.moomanow.model.User;

/**
 * Unit test for simple App.
 */
public class ZkTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public ZkTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ZkTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		Zk zk = new Zk("192.168.1.201", "4370");
//		int command = CMD_CONNECT;
//		String command_string = "";
//		int chksum = 0;
//		int session_id = 6579;
//		int reply_id = -1 + USHRT_MAX;
//		byte[] s1 = zk.createHeader(CMD_CONNECT, 0, 0, reply_id,command_string);
//		System.out.println();
////		for (byte b : s1) {
////			System.out.format("0x%x ", b);
////		}
//		byte[] s2 = zk.createHeader(CMD_VERSION, 0, session_id, 1,command_string);
//		System.out.println();
//		for (byte b : s2) {
//			System.out.format("0x%x ", b);
//		}
		try {
			System.out.println(zk.connect());
//			System.out.println(zk.version());
//			System.out.println(zk.platformVersion());
//			System.out.println(zk.getUser());
//			System.out.println("get User");
//			List<User> users = zk.getUser();
//			for (User user : users) {
//				String level = user.getRole()==14?"Admin":"User";
//				System.out.println("[UID " + user.getUid()+"]: ID:"+user.getUserid()+", Name: "+user.getName()+", Level: "+level+", Password:"+user.getPassword());
//			}
//			System.out.println(zk.getAttendance());
			List<Attendance> attendances = zk.getAttendance();
			for (Attendance attendance : attendances) {
				String state = attendance.getState()==0?"Check In":"Check Out";
				System.out.println(" [UID " + attendance.getUid()+"]: Time :"+attendance.getTime()+", Status: "+state);
			}
//			String bytes = zk.executeCmd(CMD_CONNECT, "",(status,data)->{
//				for (byte b : data.array()) {
//					System.out.format("0x%x ", b);
//				}
//				return "";
//			});
//			for (byte b : bytes) {
//				System.out.format("0x%x ", b);
//			}
		} catch (IOException e) {
//			 TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		System.out.println(s);
		assertTrue(true);
	}
//	byte[][] data = new byte[][] {
//		{
//			0x33,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,0x7e,0x68,(byte)0xEA,0x21,0x1,0x0,0x0,0x0,0x0,(byte)0xff,0x0,0x0,0x0,0x8,0x0 }
//		,{
//			0x37,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,(byte)0xec,0x6b,(byte)0xea,0x21,0x1,0x0,0x0,0x0,0x0,(byte)0xff,0x0,0x0,0x0,0xa,0x0 }
//		,{
//			0x39,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,(byte)0xf1,0x6b,(byte)0xea,0x21,0x1,0x0,0x0,0x0,0x0,(byte)0xff,0x0,0x0,0x0,0xe,0x0 }
//		,{
//			0x31,0x37,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,0x35,0x72,(byte)0xea,0x21,0x1,0x0,0x0,0x0,0x0,(byte)0xff,0x0,0x0,0x0,0x18,0x0 }
//		,{
//			0x32,0x37,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,0x5f,0x7e,(byte)0xea,0x21,0x1,0x0,0x0,0x0,0x0,(byte)0xff,0x0,0x0,0x0,0x9,0x0 }
//		,{
//			0x38,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,(byte)0xef,(byte)0x80,(byte)0xea,0x21,0x1,0x0,0x0,0x0,0x0,(byte)0xff,0x0,0x0,0x0,0x9,0x0 }
//		,{
//			0x38,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,0x44,(byte)0x81,(byte)0xea,0x21,0x1,0x0,0x0,0x0,0x0,(byte)0xff,0x0,0x0,0x0,0x18,0x0 }
//		,{
//			0x32,0x37,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,(byte)0xec,0x2e,(byte)0xeb,0x21,0x0,0x0,0x0,0x0,0x0,(byte)0xff,0x0,0x0,0x0,0xd,0x0 }
//		,{
//			0x31,0x34,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,(byte)0xa9,0x2f,(byte)0xeb,0x21,0x0,0x0,0x0,0x0,0x0,(byte)0xff,0x0,0x0,0x0,0x9,0x0 }
//    };
//	0x31,0x31,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,0xfd,0xfb,0x3c,0x23,0x0,0x0,0x0,0x0,0x0,0xff,0x0,0x0,0x0,0x5,0x0 
//	0x34,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,0xa1,0xfe,0x3c,0x23,0x0,0x0,0x0,0x0,0x0,0xff,0x0,0x0,0x0,0xe,0x0 
//	0x31,0x37,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,0x7d,0xff,0x3c,0x23,0x0,0x0,0x0,0x0,0x0,0xff,0x0,0x0,0x0,0xa,0x0 
//	0x39,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,0x8e,0x1,0x3d,0x23,0x0,0x0,0x0,0x0,0x0,0xff,0x0,0x0,0x0,0x1e,0x0 
//	date 2018-05-23, Jam 14:00:29: b'11', Status: Undefined
//	date 2018-05-23, Jam 14:11:45: b'4', Status: Undefined
//	date 2018-05-23, Jam 14:15:25: b'17', Status: Undefined
//	date 2018-05-23, Jam 14:24:14: b'9', Status: Undefined
	public void testAttendanceEncode() {
//		byte[][] data = new byte[][] {
//			{
//				0x31,0x31,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0
//				,0x1
//				,(byte)0xfd,(byte)0xfb,0x3c,0x23
//				,0x0,0x0,0x0,0x0,0x0,(byte)0xff,0x0,0x0,0x0,0x5,0x0  }
//			,{
//				0x34,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0
//				,0x1
//				,(byte)0xa1,(byte)0xfe,0x3c,0x23
//				,0x0,0x0,0x0,0x0,0x0,(byte)0xff,0x0,0x0,0x0,0xe,0x0 }
//			,{
//				0x31,0x37,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0
//				,0x1
//				,0x7d,(byte)0xff,0x3c,0x23
//				,0x0,0x0,0x0,0x0,0x0,(byte)0xff,0x0,0x0,0x0,0xa,0x0 }
//			,{
//				0x39,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0
//				,0x1
//				,(byte)0x8e,0x1,0x3d,0x23
//				,0x0,0x0,0x0,0x0,0x0,(byte)0xff,0x0,0x0,0x0,0x1e,0x0 }
//	    };
//	    for (byte[] bs : data) {
//	    	ByteBuffer buffer = ByteBuffer.allocate(bs.length).order(ByteOrder.LITTLE_ENDIAN).put(bs);
//			Attendance attendance = Attendance.encodeAttendance(buffer);
//			System.out.println(attendance);
//		}
		
	}
}
