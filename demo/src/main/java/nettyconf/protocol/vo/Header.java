package nettyconf.protocol.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息头中的参数
 */
public class Header {
	//消息验证码 3部分组成
	//0xabef：固定值，表示netty的协议消息，2个字节
	//主版本号：1~255 1个字节
	//次版本号：1~255 1个字节
	private int crcCode = 0xabef0101;
	private int length;//消息长度
	private long sessionID;//会话id
	private byte type;//消息类型
	private byte priority;//消息的优先级
	private Map<String, Object> attachment = new HashMap<String, Object>();//附件

	public int getCrcCode() {
		return crcCode;
	}

	public void setCrcCode(int crcCode) {
		this.crcCode = crcCode;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public long getSessionID() {
		return sessionID;
	}

	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
	}

	public byte getType() {
		return type;
	}

	/*public void setType(byte type) {
		this.type = type;
	}*/
	//重构的。
	public Header setType(byte type){
		this.type=type;
		return  this;
	}
	public byte getPriority() {
		return priority;
	}

	public void setPriority(byte priority) {
		this.priority = priority;
	}

	public Map<String, Object> getAttachment() {
		return attachment;
	}

	public void setAttachment(Map<String, Object> attachment) {
		this.attachment = attachment;
	}
	
	public String toString(){
		return "Header [crcCode=" + crcCode + ", length=" + length + ", sessionID=" + sessionID
				+ ", type=" + type + ", priority=" + priority + ", attachment=" + attachment + "]";
	}
}
