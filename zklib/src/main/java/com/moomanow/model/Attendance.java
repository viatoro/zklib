package com.moomanow.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;

import com.moomanow.utils.ByteUtils;
import com.moomanow.utils.TimeUtils;

public class Attendance {
	private int uid;
	private int state;
	private Date time;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	public static Attendance encodeAttendance(ByteBuffer buffer) {
		Attendance attendance = new Attendance();
//		buffer.get
		attendance.setUid(Short.toUnsignedInt(buffer.getShort(0)));
		byte state = buffer.get(29);
		attendance.setState(state);
//		attendance.setTime(time);
		byte[] data = buffer.array();
		
//		for (byte b : data) {
//			System.out.format("0x%x ", b);
//		}
		System.out.println(ByteUtils.byteToHexString(data));
		byte[] userid =  Arrays.copyOfRange(data, 0, 4 );
		String userIdStr = new String(userid);
		userIdStr = userIdStr.split(new String("\0"))[0];
//		System.out.println(userIdStr);
		attendance.setUid(Integer.parseInt(userIdStr));
		byte[] timeByte =  Arrays.copyOfRange(data, 25, 29 );
//		System.out.println(ByteUtils.byteToHexString(timeByte));
		ArrayUtils.reverse(timeByte);
//		System.out.println(ByteUtils.byteToHexString(timeByte));
//		System.out.println(ByteUtils.bytesToHex(timeByte));
		Long timeLong =Long.parseLong(ByteUtils.bytesToHex(timeByte), 16);
//		int timeLong = ByteUtils.getUnsignedInt16(ByteBuffer.allocate(timeByte.length).order(ByteOrder.LITTLE_ENDIAN).put(timeByte));
//		System.out.println(timeLong);
//		Instant instant = Instant.ofEpochSecond(timeLong);
//		System.out.println(instant);
		Date date = TimeUtils.decodeTime(timeLong);
//		System.out.println(date);
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
//		System.out.println(sf.format(date));
		attendance.setTime(date);
		return attendance;
	}
	
	@Override
	public String toString() {
		return "Attendance [uid=" + uid + ", state=" + state + ", time=" + time + "]";
	}

	
	
}
