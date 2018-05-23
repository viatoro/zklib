package com.moomanow.model;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.moomanow.utils.ByteUtils;

public class User {
	
	private int uid;
	private int role;
	private String password;
	private String name;
	private long cardno;
	private String userid;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCardno() {
		return cardno;
	}
	public void setCardno(long cardno) {
		this.cardno = cardno;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	public static User encodeUser(ByteBuffer buffer) {
		User user = new User();
//		buffer.get
		user.setUid(Short.toUnsignedInt(buffer.getShort(0)));
		user.setRole(Short.toUnsignedInt(buffer.getShort(2)));
		byte[] data = buffer.array();
		byte[] passwordByte =  Arrays.copyOfRange(data, 4, 12 );
		byte[] nameByte =  Arrays.copyOfRange(data, 12, 40 );
		byte[] userIdByte =  Arrays.copyOfRange(data, 40, 71 );
		byte[] pattern = new byte[] {0x00};
		passwordByte = ByteUtils.split(pattern , passwordByte).get(0);
		nameByte = ByteUtils.split(pattern , nameByte).get(0);
		user.setPassword(new String(passwordByte));
		user.setName(new String(nameByte));
//		long cardno = Integer.toUnsignedLong(buffer.getInt(36));
//		user.setCardno(cardno);
		user.setUserid(new String(userIdByte));
		return user;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", role=" + role + ", password=" + password + ", name=" + name + ", cardno="
				+ cardno + ", userid=" + userid + "]";
	}
	
	
	
	

}
