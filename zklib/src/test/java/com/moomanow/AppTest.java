package com.moomanow;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	
//    	int x = 5769;
//    	byte[] s = ByteBuffer.allocate(20).order(ByteOrder.LITTLE_ENDIAN).putChar((char) x).array();
//		for (byte b : s) {
//			System.out.format("0x%x ", b);
//		}
//		
//		final byte assoc_resp_msg_int[] = new byte[] {
//		        (byte)0xd0, 0x07, 
//		        (byte) 0xa6,(byte) 0xe1, 
//		        (byte)0x89, 0x16, 
//		        0x00, 0x00 
//		       
//		};
//		System.out.println();
//		for (byte b : assoc_resp_msg_int) {
//			System.out.format("0x%x ", b);
//		}
//		System.out.println();
//		ByteBuffer buffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).put(assoc_resp_msg_int);
//		for (byte b : buffer.array()) {
//			System.out.format("0x%x ", b);
//		}
//		int session_id = Short.toUnsignedInt(buffer.getShort(4));
//		System.out.println(session_id);
        assertTrue( true );
    }
}
