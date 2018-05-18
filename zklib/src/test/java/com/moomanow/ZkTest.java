package com.moomanow;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static com.moomanow.ZkConst.*;

import java.io.IOException;

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
//		int session_id = 0;
//		int reply_id = -1 + USHRT_MAX;
		// byte[] s = zk.createHeader(command, chksum, session_id, reply_id,
		// command_string);
		try {
			String bytes = zk.executeCmd(CMD_CONNECT, "",(status,data)->{
				for (byte b : data.array()) {
					System.out.format("0x%x ", b);
				}
				return "";
			});
//			for (byte b : bytes) {
//				System.out.format("0x%x ", b);
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(s);
		assertTrue(true);
	}
}
